package com.selfsell.investor.scheduler;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfsell.common.util.G;
import com.selfsell.investor.bean.ActTransactionListBean;
import com.selfsell.investor.bean.ActTransactionListBean.Data;
import com.selfsell.investor.bean.ActTransactionListBean.Result;
import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.bean.TradeType;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.mybatis.domain.ScanTradeRecord;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.service.InvestorService;
import com.selfsell.investor.service.ParamSetService;
import com.selfsell.investor.service.ScanTradeRecordService;
import com.selfsell.investor.service.TradeRecordService;
import com.selfsell.investor.share.Constants;
import com.selfsell.investor.share.WBinout;

@Component
public class ACTTradeScanScheduler implements InitializingBean {
	Logger log = LoggerFactory.getLogger(getClass());

	RestTemplate restTemplate;

	@Autowired
	InvestorService investorService;

	@Autowired
	ParamSetService paramSetService;

	@Autowired
	ScanTradeRecordService scanTradeRecordService;

	@Autowired
	TradeRecordService tradeRecordService;

	@Autowired
	ObjectMapper objectMapper;

	@Value("${act.ssc.address}")
	String mainSscAddress;// ACT主地址

	private static final String BASE_URL = "https://browser.achain.com/wallets/api/browser/act/TransactionList.Query?block_num={0}&acct_address={1}&page={2}&per_page=50";

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Scheduled(fixedDelay = 1 * 60 * 1000)
	public void execute() throws Exception {

		log.info("开始扫描ACT交易");

		Integer blockNum = 0;
		Integer page = 1;
		while (true) {
			String url = MessageFormat.format(BASE_URL, G.str(blockNum), mainSscAddress, G.str(page));
			ActTransactionListBean actTransactionListBean = restTemplate.getForObject(url,
					ActTransactionListBean.class);
			if (actTransactionListBean == null || !"OK".equals(actTransactionListBean.getMsg())) {
				break;
			}

			Result result = actTransactionListBean.getResult();
			
			if(result.getDataList()==null||result.getDataList().isEmpty()) {
				break;
			}

			for (Data data : result.getDataList()) {
				if (!"0".equals(data.getIs_completed())) {
					continue;
				}

				if (!"SSC".equals(data.getCoinType())) {
					continue;
				}

				Investor investor = investorService.queryBySscAddress(data.getTo_addr());

				if (investor == null && !mainSscAddress.equals(data.getFrom_addr())) {// 不是存入看看是否为取出
					continue;
				}

				if (scanTradeRecordService.existsRecords(data.getTrx_id())) {
					continue;
				}

				log.info("处理交易记录【{}】【{}】【{}】", data.getTrx_id(), data.getFrom_addr(), data.getTo_addr());

				processData(data, investor);

			}

			if (result.getCurrentPage() <= result.getTotalPage()) {
				page = page + 1;
			}
		}

	}

	@Transactional(rollbackFor = Throwable.class)
	public void processData(Data data, Investor investor) throws Exception {
		ScanTradeRecord scanTradeRecord = new ScanTradeRecord();
		scanTradeRecord.setAddress(data.getTo_addr());
		scanTradeRecord.setTxIndex(data.getTrx_id());
		scanTradeRecord.setFromAddress(data.getFrom_addr());
		if (investor != null)
			scanTradeRecord.setUserId(investor.getId());
		scanTradeRecord
				.setAmount(G.bd(data.getAmount(), 8).divide(Constants.ACT_PRECISION, 8, BigDecimal.ROUND_HALF_DOWN));
		Date time = sdf.parse(data.getTrx_time());
		LocalDateTime localDateTime = LocalDateTime.ofInstant(time.toInstant(), ZoneId.of("GMT+8"));
		localDateTime = localDateTime.plusHours(8);
		scanTradeRecord.setTime(Date.from(localDateTime.atZone(ZoneId.of("GMT+8")).toInstant()));
		scanTradeRecordService.insert(scanTradeRecord);

		// 插入交易记录
		TradeRecord tradeRecord = new TradeRecord();
		if (investor != null) {
			tradeRecord.setInoutFlag(WBinout.IN);
			tradeRecord.setAmount(scanTradeRecord.getAmount());
			tradeRecord.setCreateTime(new Date());
			tradeRecord.setInvestorId(investor.getId());
			tradeRecord.setType(TradeType.transfer_in);
			tradeRecord.setsAddress(scanTradeRecord.getFromAddress());
			tradeRecord.settAddress(scanTradeRecord.getAddress());
			tradeRecord.setStatus(TradeRecordStatus.success);
			tradeRecord.setTxId(scanTradeRecord.getTxIndex());
			tradeRecordService.insert(tradeRecord);

			investorService.updateAssets(investor.getId(), WBinout.IN, tradeRecord.getAmount(), true);

		} else {
			tradeRecordService.updateTranserStatus(scanTradeRecord.getTxIndex(), TradeRecordStatus.success);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		SSLContext sslcontext = createIgnoreVerifySSL();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(80000).setConnectTimeout(8000)
				.setConnectionRequestTimeout(8000).build();

		HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(connectionManager).build();
		restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));

	}

	/**
	 * 绕过验证
	 */
	private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}
}

package com.selfsell.investor.scheduler;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.selfsell.investor.bean.FinancialStatus;
import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.bean.TradeType;
import com.selfsell.investor.mybatis.domain.FinancialRecord;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.service.FinancialService;
import com.selfsell.investor.service.InvestorService;
import com.selfsell.investor.service.TradeRecordService;
import com.selfsell.investor.share.WBinout;

/**
 * 理财跑批结算
 * 
 * @author breeze
 *
 */
@Component
public class FinancialScheduler {
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	FinancialService financialService;
	@Autowired
	InvestorService investorService;
	@Autowired
	TradeRecordService tradeRecordService;

	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional(rollbackFor = Throwable.class)
	public void execute() throws Exception {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		log.info("理财跑批结算开始");
		List<FinancialRecord> records = financialService.queryIngRecords();
		if (records != null && !records.isEmpty()) {
			for (FinancialRecord record : records) {
				if (record.getStartTime().compareTo(now.getTime()) == 0) {
					continue;
				}
				// 计息
				if (record.getStartTime().compareTo(now.getTime()) < 0
						&& record.getFinishTime().compareTo(now.getTime()) >= 0) {
					long days = TimeUnit.MILLISECONDS.toDays(now.getTimeInMillis() - record.getStartTime().getTime());
					BigDecimal dayRate = record.getAnnualRate().divide(new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP)
							.divide(new BigDecimal(360), 8, BigDecimal.ROUND_HALF_UP);
					BigDecimal interest = record.getAmount().multiply(dayRate).multiply(new BigDecimal(days));
					log.info("记录【{}】天数【{}】日利率【{}】利息【{}】", record.getId(), days, dayRate, interest);
					record.setInterest(interest);
				}
				// 到期
				if (record.getFinishTime().compareTo(now.getTime()) <= 0) {
					record.setStatus(FinancialStatus.NOVER);
					record.setEndTime(record.getFinishTime());

					// 返回本金利息
					investorService.updateAssets(record.getInvestorId(), WBinout.IN, record.getAmount(), false);
					TradeRecord tradeRecordCapital = new TradeRecord();
					tradeRecordCapital.setAmount(record.getAmount());
					tradeRecordCapital.setCreateTime(new Date());
					tradeRecordCapital.setInoutFlag(WBinout.IN);
					tradeRecordCapital.setInvestorId(record.getInvestorId());
					tradeRecordCapital.setRemark("理财本金");
					tradeRecordCapital.setStatus(TradeRecordStatus.success);
					tradeRecordCapital.setType(TradeType.capital);
					tradeRecordService.insert(tradeRecordCapital);
					// 新增利息
					investorService.updateAssets(record.getInvestorId(), WBinout.IN, record.getInterest(), true);
					TradeRecord tradeRecordInterest = new TradeRecord();
					tradeRecordInterest.setAmount(record.getInterest());
					tradeRecordInterest.setCreateTime(new Date());
					tradeRecordInterest.setInoutFlag(WBinout.IN);
					tradeRecordInterest.setInvestorId(record.getInvestorId());
					tradeRecordInterest.setRemark("理财利息");
					tradeRecordInterest.setStatus(TradeRecordStatus.success);
					tradeRecordInterest.setType(TradeType.interest);
					tradeRecordService.insert(tradeRecordInterest);
				}

				financialService.update(record);
			}
		}
		log.info("理财跑批结算结束");
	}

}

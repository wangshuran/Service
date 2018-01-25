package com.selfsell.investor.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.selfsell.common.util.G;
import com.selfsell.investor.bean.CurrencyType;
import com.selfsell.investor.service.TokenPriceService;

/**
 * OKEX 信息
 * 
 * @author breeze
 *
 */
@Service("tokenPriceServiceOkex")
public class TokenPriceServiceOkex implements TokenPriceService {
	@Autowired
	RestTemplate restTemplate;

	static final String BASE_USDT_URL = "https://www.okex.com/api/v1/ticker.do?symbol= {0}_usdt";
	static final String EXCHANGE_RATE_URL = "https://www.okex.com/api/v1/exchange_rate.do";
	static final BigDecimal DEFAULT_RATE = new BigDecimal("6.5");

	@Override
	public BigDecimal queryPrice(String token, CurrencyType currencyType) {
		String url = MessageFormat.format(BASE_USDT_URL, token);
		TickerRes tickerRes = restTemplate.getForObject(url, TickerRes.class);

		if (tickerRes != null) {
			BigDecimal usdPrice = G.bd(tickerRes.getTicker().getLast(), 8);
			if (currencyType.equals(CurrencyType.CNY)) {
				ExchangeRate exchangeRate = restTemplate.getForObject(EXCHANGE_RATE_URL, ExchangeRate.class);
				if (exchangeRate != null) {
					return usdPrice.multiply(G.bd(exchangeRate.getRate(), 8));
				} else {
					return usdPrice.multiply(DEFAULT_RATE);
				}
			}

			return usdPrice;
		}
		return BigDecimal.ZERO;
	}

	public static class ExchangeRate {
		private String rate;

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}
	}

	public static class TickerRes {
		private String date;
		private Ticker ticker;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public Ticker getTicker() {
			return ticker;
		}

		public void setTicker(Ticker ticker) {
			this.ticker = ticker;
		}
	}

	public static class Ticker {
		private String buy;
		private String hight;
		private String last;
		private String low;
		private String sell;
		private String vol;

		public String getBuy() {
			return buy;
		}

		public void setBuy(String buy) {
			this.buy = buy;
		}

		public String getHight() {
			return hight;
		}

		public void setHight(String hight) {
			this.hight = hight;
		}

		public String getLast() {
			return last;
		}

		public void setLast(String last) {
			this.last = last;
		}

		public String getLow() {
			return low;
		}

		public void setLow(String low) {
			this.low = low;
		}

		public String getSell() {
			return sell;
		}

		public void setSell(String sell) {
			this.sell = sell;
		}

		public String getVol() {
			return vol;
		}

		public void setVol(String vol) {
			this.vol = vol;
		}
	}

}

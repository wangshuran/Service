package com.selfsell.investor.bean;

import java.io.Serializable;
import java.util.List;

public class ActTransactionListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private Result result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public static class Result {
		private Long totalRecords;
		private Integer currentPage;
		private Integer totalPage;
		private Integer pageSize;
		private List<Data> dataList;

		public Long getTotalRecords() {
			return totalRecords;
		}

		public void setTotalRecords(Long totalRecords) {
			this.totalRecords = totalRecords;
		}

		public Integer getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}

		public Integer getTotalPage() {
			return totalPage;
		}

		public void setTotalPage(Integer totalPage) {
			this.totalPage = totalPage;
		}

		public Integer getPageSize() {
			return pageSize;
		}

		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}

		public List<Data> getDataList() {
			return dataList;
		}

		public void setDataList(List<Data> dataList) {
			this.dataList = dataList;
		}
	}

	public static class Data {
		private Long id;
		private String trx_id;
		private String amount;
		private String trx_type;
		private String coinType;
		private String trade_Describe;
		private String from_addr;
		private Long block_num;
		private String from_acct;
		private String to_addr;
		private String sub_addr;
		private String to_acct;
		private String called_abi;
		private String trx_time;
		private String is_completed;
		private String contractId;
		private String eventType;
		private String eventParam;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTrx_id() {
			return trx_id;
		}

		public void setTrx_id(String trx_id) {
			this.trx_id = trx_id;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getTrx_type() {
			return trx_type;
		}

		public void setTrx_type(String trx_type) {
			this.trx_type = trx_type;
		}

		public String getCoinType() {
			return coinType;
		}

		public void setCoinType(String coinType) {
			this.coinType = coinType;
		}

		public String getTrade_Describe() {
			return trade_Describe;
		}

		public void setTrade_Describe(String trade_Describe) {
			this.trade_Describe = trade_Describe;
		}

		public String getFrom_addr() {
			return from_addr;
		}

		public void setFrom_addr(String from_addr) {
			this.from_addr = from_addr;
		}

		public Long getBlock_num() {
			return block_num;
		}

		public void setBlock_num(Long block_num) {
			this.block_num = block_num;
		}

		public String getFrom_acct() {
			return from_acct;
		}

		public void setFrom_acct(String from_acct) {
			this.from_acct = from_acct;
		}

		public String getTo_addr() {
			return to_addr;
		}

		public void setTo_addr(String to_addr) {
			this.to_addr = to_addr;
		}

		public String getSub_addr() {
			return sub_addr;
		}

		public void setSub_addr(String sub_addr) {
			this.sub_addr = sub_addr;
		}

		public String getTo_acct() {
			return to_acct;
		}

		public void setTo_acct(String to_acct) {
			this.to_acct = to_acct;
		}

		public String getCalled_abi() {
			return called_abi;
		}

		public void setCalled_abi(String called_abi) {
			this.called_abi = called_abi;
		}

		public String getTrx_time() {
			return trx_time;
		}

		public void setTrx_time(String trx_time) {
			this.trx_time = trx_time;
		}

		public String getIs_completed() {
			return is_completed;
		}

		public void setIs_completed(String is_completed) {
			this.is_completed = is_completed;
		}

		public String getContractId() {
			return contractId;
		}

		public void setContractId(String contractId) {
			this.contractId = contractId;
		}

		public String getEventType() {
			return eventType;
		}

		public void setEventType(String eventType) {
			this.eventType = eventType;
		}

		public String getEventParam() {
			return eventParam;
		}

		public void setEventParam(String eventParam) {
			this.eventParam = eventParam;
		}
	}
}

package com.alacriti.p2p.model.vo;

import java.util.Date;

public class TransactionSummaryVO {
	private String merchant;
	private Date dateTime;
	private float withdrawl;
	private float deposit;
	private String description;
	private String merchantEmail;
	private int mailStatus;
	private int transactionId;
	private String email;
	private String username;
	private int noOfInbound;
	private float totalDeposit;
	private int noOfOutbound;
	private float totalWithdrawl;

	public TransactionSummaryVO() {
		super();
	}

	public TransactionSummaryVO(int noOfInbound, float totalDeposit) {
		super();
		this.noOfInbound = noOfInbound;
		this.totalDeposit = totalDeposit;
	}

	public TransactionSummaryVO(float totalWithdrawl, int noOfOutbound) {
		super();
		this.totalWithdrawl = totalWithdrawl;
		this.noOfOutbound = noOfOutbound;
	}

	public TransactionSummaryVO(String merchant, String merchantEmail,
			Date dateTime, float withdrawl, float deposit, String description) {
		super();
		this.merchant = merchant;
		this.merchantEmail = merchantEmail;
		this.dateTime = dateTime;
		this.withdrawl = withdrawl;
		this.deposit = deposit;
		this.description = description;
	}

	public TransactionSummaryVO(String merchant, Date dateTime,
			float withdrawl, float deposit, String description) {
		super();
		this.merchant = merchant;
		this.dateTime = dateTime;
		this.withdrawl = withdrawl;
		this.deposit = deposit;
		this.description = description;
	}

	public TransactionSummaryVO(int transactionId, String username,
			String merchant, String email, Date dateTime, float withdrawl,
			String description) {
		super();
		this.transactionId = transactionId;
		this.username = username;
		this.merchant = merchant;
		this.email = email;
		this.dateTime = dateTime;
		this.withdrawl = withdrawl;
		this.description = description;
	}

	public TransactionSummaryVO(String merchant, float withdrawl,
			float deposit, String description) {
		super();
		this.merchant = merchant;
		this.withdrawl = withdrawl;
		this.deposit = deposit;
		this.description = description;
	}

	public TransactionSummaryVO(String merchant, float withdrawl, float deposit) {
		super();
		this.merchant = merchant;
		this.withdrawl = withdrawl;
		this.deposit = deposit;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getNoOfOutbound() {
		return noOfOutbound;
	}

	public void setNoOfOutbound(int noOfOutbound) {
		this.noOfOutbound = noOfOutbound;
	}

	public float getTotalWithdrawl() {
		return totalWithdrawl;
	}

	public void setTotalWithdrawl(float totalWithdrawl) {
		this.totalWithdrawl = totalWithdrawl;
	}

	public int getNoOfInbound() {
		return noOfInbound;
	}

	public void setNoOfInbound(int noOfInbound) {
		this.noOfInbound = noOfInbound;
	}

	public float getTotalDeposit() {
		return totalDeposit;
	}

	public void setTotalDeposit(float totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(int mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getMerchantEmail() {
		return merchantEmail;
	}

	public void setMerchantEmail(String merchantEmail) {
		this.merchantEmail = merchantEmail;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public float getWithdrawl() {
		return withdrawl;
	}

	public void setWithdrawl(float withdrawl) {
		this.withdrawl = withdrawl;
	}

	public float getDeposit() {
		return deposit;
	}

	public void setDeposit(float deposit) {
		this.deposit = deposit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

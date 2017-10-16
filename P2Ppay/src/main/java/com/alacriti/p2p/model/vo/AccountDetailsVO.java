package com.alacriti.p2p.model.vo;

public class AccountDetailsVO {
	private int accountUser;
	private String accountHolderName;
	private String accountNumber;

	public AccountDetailsVO() {
		super();
	}

	public AccountDetailsVO(int accountUser, String accountHolderName,
			String accountNumber) {
		super();
		this.accountUser = accountUser;
		this.accountHolderName = accountHolderName;
		this.accountNumber = accountNumber;
	}

	public int getAccountUser() {
		return accountUser;
	}

	public void setAccountUser(int accountUser) {
		this.accountUser = accountUser;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}

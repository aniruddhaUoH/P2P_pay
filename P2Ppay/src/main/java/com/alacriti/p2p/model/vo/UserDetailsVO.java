package com.alacriti.p2p.model.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDetailsVO {
	private String username;
	private String email;
	private int status;
	private float balance;
	private String phone;
	private String gender;
	private String idToken;

	public UserDetailsVO() {
		super();
	}

	public UserDetailsVO(float balance, String phone, String gender) {
		super();
		this.balance = balance;
		this.phone = phone;
		this.gender = gender;
	}

	public UserDetailsVO(String username) {
		super();
		this.username = username;
	}

	public UserDetailsVO(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

	public UserDetailsVO(String username, int status) {
		super();
		this.username = username;
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}

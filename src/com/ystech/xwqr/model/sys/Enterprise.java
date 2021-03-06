package com.ystech.xwqr.model.sys;

// Generated 2013-6-2 23:21:11 by Hibernate Tools 4.0.0

/**
 * Enterprise generated by hbm2java
 */
public class Enterprise implements java.io.Serializable {

	private Integer dbid;
	private String name;
	private String phone;
	private String fax;
	private String zipCode;
	private String address;
	private String webAddress;
	private String email;
	private String bank;
	private String account;
	private String content;

	public Enterprise() {
	}

	public Enterprise(String name, String phone, String fax, String zipCode,
			String address, String webAddress, String email, String bank,
			String account, String content) {
		this.name = name;
		this.phone = phone;
		this.fax = fax;
		this.zipCode = zipCode;
		this.address = address;
		this.webAddress = webAddress;
		this.email = email;
		this.bank = bank;
		this.account = account;
		this.content = content;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebAddress() {
		return this.webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBank() {
		return this.bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

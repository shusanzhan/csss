package com.ystech.weixin.model;

// Generated 2015-6-7 9:29:46 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * WeixinAccesstoken generated by hbm2java
 */
public class WeixinAccesstoken implements java.io.Serializable {

	private Integer dbid;
	private String accessToken;
	private Date addtime;
	private Integer expiresIb;
	private String jsapiTicket;

	public WeixinAccesstoken() {
	}


	public WeixinAccesstoken(String id, String accessToken, Date addtime,
			Integer expiresIb) {
		this.accessToken = accessToken;
		this.addtime = addtime;
		this.expiresIb = expiresIb;
	}


	public Integer getDbid() {
		return dbid;
	}


	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}


	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getExpiresIb() {
		return this.expiresIb;
	}

	public void setExpiresIb(Integer expiresIb) {
		this.expiresIb = expiresIb;
	}


	public String getJsapiTicket() {
		return jsapiTicket;
	}


	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

}
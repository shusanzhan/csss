package com.ystech.weixin.model;

// Generated 2015-6-7 21:57:03 by Hibernate Tools 4.0.0

/**
 * WeixinTexttemplate generated by hbm2java
 */
public class WeixinTexttemplate implements java.io.Serializable {

	private Integer dbid;
	private String addtime;//添加时间
	private String content;//添加内容
	private String templatename;//标题
	private String accountid;

	public WeixinTexttemplate() {
	}

	public WeixinTexttemplate(String id) {
	}

	public WeixinTexttemplate(String id, String addtime, String content,
			String templatename, String accountid) {
		this.addtime = addtime;
		this.content = content;
		this.templatename = templatename;
		this.accountid = accountid;
	}



	public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplatename() {
		return this.templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	public String getAccountid() {
		return this.accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

}
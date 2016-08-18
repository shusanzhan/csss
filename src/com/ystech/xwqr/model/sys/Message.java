package com.ystech.xwqr.model.sys;

// Generated 2014-3-19 19:13:05 by Hibernate Tools 4.0.0

/**
 * Message generated by hbm2java
 */
public class Message implements java.io.Serializable {

	private Integer dbid;
	private String title;
	private String content;
	private Boolean isNotice;
	private Boolean isRead;
	private Integer userId;
	private String url;

	public Message() {
	}

	public Message(String title, String content, Boolean isNotice,
			Boolean isRead, Integer userId, String url) {
		this.title = title;
		this.content = content;
		this.isNotice = isNotice;
		this.isRead = isRead;
		this.userId = userId;
		this.url = url;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsNotice() {
		return this.isNotice;
	}

	public void setIsNotice(Boolean isNotice) {
		this.isNotice = isNotice;
	}

	public Boolean getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
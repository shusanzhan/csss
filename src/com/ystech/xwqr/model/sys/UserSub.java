package com.ystech.xwqr.model.sys;


// Generated 2014-2-27 20:13:32 by Hibernate Tools 4.0.0

/**
 * Usersub generated by hbm2java
 */
public class UserSub implements java.io.Serializable {

	private Integer dbid;
	private Integer userId;
	private User user;

	public UserSub() {
	}

	public UserSub(Integer userId, Integer userUnderId) {
		this.userId = userId;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
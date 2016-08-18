package com.ystech.xwqr.model.sys;

// Generated 2015-10-3 16:53:04 by Hibernate Tools 4.0.0

/**
 * SysSysteminfo generated by hbm2java
 */
public class SystemInfo implements java.io.Serializable {

	private Integer dbid;
	private String name;
	private String nameImage;
	private String loginLogo;
	private String infoLogo;

	public SystemInfo() {
	}

	public SystemInfo(String name, String nameImage, String ss,
			String infoLogo) {
		this.name = name;
		this.nameImage = nameImage;
		this.infoLogo = infoLogo;
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

	public String getNameImage() {
		return this.nameImage;
	}

	public void setNameImage(String nameImage) {
		this.nameImage = nameImage;
	}


	public String getLoginLogo() {
		return loginLogo;
	}

	public void setLoginLogo(String loginLogo) {
		this.loginLogo = loginLogo;
	}

	public String getInfoLogo() {
		return this.infoLogo;
	}

	public void setInfoLogo(String infoLogo) {
		this.infoLogo = infoLogo;
	}

}
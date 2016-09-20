package com.ystech.csss.model;

public class ReportData
{
	  private Integer dbid;
	  //总公司编号
	  private String psNo;
	  //总公司名称
	  private String psName;
	  //分公司编号
	  private String csNo;
	  //分公司名册
	  private String csName;
	  //店号
	  private String shopNo;
	  //店名
	  private String shopName;
	  //统计总数
	  private Integer num;
	  //员工名册
	  private String staffName;
	  
	  public Integer getDbid() {
		return dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getPsNo()
	  {
	    return this.psNo;
	  }
	  
	  public void setPsNo(String psNo)
	  {
	    this.psNo = psNo;
	  }
	  
	  public String getPsName()
	  {
	    return this.psName;
	  }
	  
	  public void setPsName(String psName)
	  {
	    this.psName = psName;
	  }
	  
	  public String getCsNo()
	  {
	    return this.csNo;
	  }
	  
	  public void setCsNo(String csNo)
	  {
	    this.csNo = csNo;
	  }
	  
	  public String getCsName()
	  {
	    return this.csName;
	  }
	  
	  public void setCsName(String csName)
	  {
	    this.csName = csName;
	  }
	  
	  public String getShopNo()
	  {
	    return this.shopNo;
	  }
	  
	  public void setShopNo(String shopNo)
	  {
	    this.shopNo = shopNo;
	  }
	  
	  public String getShopName()
	  {
	    return this.shopName;
	  }
	  
	  public void setShopName(String shopName)
	  {
	    this.shopName = shopName;
	  }
	  
	  public Integer getNum()
	  {
	    return this.num;
	  }
	  
	  public void setNum(Integer num)
	  {
	    this.num = num;
	  }

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	  
}

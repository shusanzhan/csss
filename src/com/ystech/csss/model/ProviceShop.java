package com.ystech.csss.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class ProviceShop
  implements Serializable
{
  private Integer dbid;
  private String no;
  private String name;
  private String provice;
  private String city;
  private String areaStr;
  private String address;
  private String pointx;
  private String pointy;
  private String note;
  private Date createDate;
  private Date modifyDate;
  private Integer num;
  
  public ProviceShop() {}
  
  public ProviceShop(String name, String provice, String city, String areaStr, String address, String pointx, String pointy, String note, Date createDate, Date modifyDate, Integer num, Set csssStaffs)
  {
    this.name = name;
    this.provice = provice;
    this.city = city;
    this.areaStr = areaStr;
    this.address = address;
    this.pointx = pointx;
    this.pointy = pointy;
    this.note = note;
    this.createDate = createDate;
    this.modifyDate = modifyDate;
    this.num = num;
  }
  
  public Integer getDbid()
  {
    return this.dbid;
  }
  
  public void setDbid(Integer dbid)
  {
    this.dbid = dbid;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getProvice()
  {
    return this.provice;
  }
  
  public void setProvice(String provice)
  {
    this.provice = provice;
  }
  
  public String getCity()
  {
    return this.city;
  }
  
  public void setCity(String city)
  {
    this.city = city;
  }
  
  public String getAreaStr()
  {
    return this.areaStr;
  }
  
  public void setAreaStr(String areaStr)
  {
    this.areaStr = areaStr;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getPointx()
  {
    return this.pointx;
  }
  
  public void setPointx(String pointx)
  {
    this.pointx = pointx;
  }
  
  public String getPointy()
  {
    return this.pointy;
  }
  
  public void setPointy(String pointy)
  {
    this.pointy = pointy;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }
  
  public Date getModifyDate()
  {
    return this.modifyDate;
  }
  
  public void setModifyDate(Date modifyDate)
  {
    this.modifyDate = modifyDate;
  }
  
  public Integer getNum()
  {
    return this.num;
  }
  
  public void setNum(Integer num)
  {
    this.num = num;
  }
  
  public String getNo()
  {
    return this.no;
  }
  
  public void setNo(String no)
  {
    this.no = no;
  }
}

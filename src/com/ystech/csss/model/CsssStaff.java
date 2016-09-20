package com.ystech.csss.model;

import java.io.Serializable;
import java.util.Date;

public class CsssStaff
  implements Serializable
{
  public static Integer COMM = Integer.valueOf(1);
  public static Integer YEAS = Integer.valueOf(2);
  private Integer dbid;
  private CsssShop csssShop;
  private String name;
  private String no;
  private String sex;
  private String mobilePhone;
  private String email;
  private Date createDate;
  private Date modifyDate;
  private Integer qrCodeStatus;
  private String sceneStr;
  private String ticket;
  private String qrCode;
  private Date qrCodeDate;
  private Integer scannNum;
  private Integer leaderNum;
  private String note;
  
  public CsssStaff() {}
  
  public CsssStaff(int dbid) {}
  
  public CsssStaff(int dbid, CsssShop csssShop, String name, String sex, String mobilePhone, String email, Date createDate, Date modifyDate, Integer towDimenCodeStatus, String dimenCode, Integer dimenCodeImage, Integer scannNum, Integer leaderNum, String note)
  {
    this.csssShop = csssShop;
    this.name = name;
    this.sex = sex;
    this.mobilePhone = mobilePhone;
    this.email = email;
    this.createDate = createDate;
    this.modifyDate = modifyDate;
    this.scannNum = scannNum;
    this.leaderNum = leaderNum;
    this.note = note;
  }
  
  public Integer getDbid()
  {
    return this.dbid;
  }
  
  public void setDbid(Integer dbid)
  {
    this.dbid = dbid;
  }
  
  public CsssShop getCsssShop()
  {
    return this.csssShop;
  }
  
  public void setCsssShop(CsssShop csssShop)
  {
    this.csssShop = csssShop;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getSex()
  {
    return this.sex;
  }
  
  public void setSex(String sex)
  {
    this.sex = sex;
  }
  
  public String getMobilePhone()
  {
    return this.mobilePhone;
  }
  
  public void setMobilePhone(String mobilePhone)
  {
    this.mobilePhone = mobilePhone;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
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
  
  public Integer getQrCodeStatus()
  {
    return this.qrCodeStatus;
  }
  
  public void setQrCodeStatus(Integer qrCodeStatus)
  {
    this.qrCodeStatus = qrCodeStatus;
  }
  
  public String getQrCode()
  {
    return this.qrCode;
  }
  
  public void setQrCode(String qrCode)
  {
    this.qrCode = qrCode;
  }
  
  public Date getQrCodeDate()
  {
    return this.qrCodeDate;
  }
  
  public void setQrCodeDate(Date qrCodeDate)
  {
    this.qrCodeDate = qrCodeDate;
  }
  
  public Integer getScannNum()
  {
    return this.scannNum;
  }
  
  public void setScannNum(Integer scannNum)
  {
    this.scannNum = scannNum;
  }
  
  public Integer getLeaderNum()
  {
    return this.leaderNum;
  }
  
  public void setLeaderNum(Integer leaderNum)
  {
    this.leaderNum = leaderNum;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public String getSceneStr()
  {
    return this.sceneStr;
  }
  
  public void setSceneStr(String sceneStr)
  {
    this.sceneStr = sceneStr;
  }
  
  public String getTicket()
  {
    return this.ticket;
  }
  
  public void setTicket(String ticket)
  {
    this.ticket = ticket;
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

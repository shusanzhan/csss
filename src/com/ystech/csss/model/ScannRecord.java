package com.ystech.csss.model;

import com.ystech.weixin.model.WeixinGzuserinfo;
import java.io.Serializable;
import java.util.Date;

public class ScannRecord
  implements Serializable
{
  private Integer dbid;
  private WeixinGzuserinfo weixinGzuserinfo;
  private CsssShop csssShop;
  private CsssStaff csssStaff;
  private String staffName;
  private Date scannDate;
  private Integer scannType;
  private String note;
  private Integer num;
  
  public ScannRecord() {}
  
  public ScannRecord(Integer gzUserId, Integer shopId, Integer staffId, Date scannDate, Integer scannType, String note)
  {
    this.scannDate = scannDate;
    this.scannType = scannType;
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
  
  public Date getScannDate()
  {
    return this.scannDate;
  }
  
  public void setScannDate(Date scannDate)
  {
    this.scannDate = scannDate;
  }
  
  public Integer getScannType()
  {
    return this.scannType;
  }
  
  public void setScannType(Integer scannType)
  {
    this.scannType = scannType;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public WeixinGzuserinfo getWeixinGzuserinfo()
  {
    return this.weixinGzuserinfo;
  }
  
  public void setWeixinGzuserinfo(WeixinGzuserinfo weixinGzuserinfo)
  {
    this.weixinGzuserinfo = weixinGzuserinfo;
  }
  
  public CsssShop getCsssShop()
  {
    return this.csssShop;
  }
  
  public void setCsssShop(CsssShop csssShop)
  {
    this.csssShop = csssShop;
  }
  
  public CsssStaff getCsssStaff()
  {
    return this.csssStaff;
  }
  
  public void setCsssStaff(CsssStaff csssStaff)
  {
    this.csssStaff = csssStaff;
  }
  
  public String getStaffName()
  {
    return this.staffName;
  }
  
  public void setStaffName(String staffName)
  {
    this.staffName = staffName;
  }
  
  public Integer getNum()
  {
    return this.num;
  }
  
  public void setNum(Integer num)
  {
    this.num = num;
  }
}

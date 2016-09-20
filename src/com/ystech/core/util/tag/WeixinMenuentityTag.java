package com.ystech.core.util.tag;

import com.ystech.weixin.model.WeixinMenuentity;
import com.ystech.weixin.service.WeixinMenuentityManageImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WeixinMenuentityTag
  extends TagSupport
{
  private Integer dbid;
  
  public Integer getDbid()
  {
    return this.dbid;
  }
  
  public void setDbid(Integer dbid)
  {
    this.dbid = dbid;
  }
  
  public int doEndTag()
    throws JspException
  {
    try
    {
      JspWriter out = this.pageContext.getOut();
      if (this.dbid != null)
      {
        HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
        
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        WeixinMenuentityManageImpl weixinMenuentityManageImpl = (WeixinMenuentityManageImpl)webApplicationContext.getBean("weixinMenuentityManageImpl");
        WeixinMenuentity weixinMenuentity = (WeixinMenuentity)weixinMenuentityManageImpl.get(this.dbid);
        String tableTr = weixinMenuentityManageImpl.getTableTr(weixinMenuentity, "&nbsp;&nbsp;&nbsp;&nbsp;");
        out.print(tableTr);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 6;
  }
  
  public int doStartTag()
    throws JspException
  {
    return 6;
  }
}

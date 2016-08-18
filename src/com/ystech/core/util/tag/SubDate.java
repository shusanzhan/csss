package com.ystech.core.util.tag;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ystech.core.util.DateUtil;


/**
 * @author 作者 舒三战
 * @version 创建时间：2013-5-7 上午9:52:38 类说明
 **/
public class SubDate extends TagSupport {
	private Date startTime;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			if(null!=startTime) {
				Date nowDate = new Date();
				out.print(DateUtil.getTimeDifference(nowDate, startTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE; // 不包含主体内容
	}

	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

}

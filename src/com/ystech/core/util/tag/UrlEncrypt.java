package com.ystech.core.util.tag;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ystech.core.util.UrlParamEncryptUtile;

/**
 * @author Administrator
 *
 */
public class UrlEncrypt extends TagSupport{
	private String enCode;

	public String getEnCode() {
		return enCode;
	}

	public void setEnCode(String enCode) {
		this.enCode = enCode;
	}

	/**
	 * 只要拥有了页面的pageContext内置对象那么就可以获取页面上所有的内置对象
	 */
	public int doEndTag() throws JspException {

		JspWriter out=pageContext.getOut();  
        try  
        {  
        	//enCode="hello world";
        	if(null!=enCode&&enCode.trim().length()>0){
        		enCode=UrlParamEncryptUtile.decrypt(enCode);
        	}else{
        		enCode="0";
        	}
        	DecimalFormat myformat = new DecimalFormat();
        	Double valueOf = Double.valueOf(enCode);
        	myformat.applyPattern("##,###.00");
            out.print(myformat.format(valueOf)); //页面中显示的内容  
        }catch(Exception e)  
        {  
            e.printStackTrace();
        }  
        return EVAL_PAGE; //不包含主体内容 
	}

	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	
}

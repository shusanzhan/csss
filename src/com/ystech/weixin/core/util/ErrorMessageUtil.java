package com.ystech.weixin.core.util;

import net.sf.json.JSONObject;

public class ErrorMessageUtil
{
  public static ErrorMessage paraseErrorMessage(JSONObject jsonObject)
  {
    try
    {
      if (jsonObject != null)
      {
        ErrorMessage errorMessage = new ErrorMessage();
        Object object = jsonObject.get("errcode");
        String errcode=new String();
        try {
        	Integer errcodeInt =(Integer) object;
        	errcode=errcodeInt+"";
		} catch (Exception e) {
			errcode=(String) object;
		}
        errorMessage.setErrcode(errcode+"");
        String errmsg = jsonObject.getString("errmsg");
        errorMessage.setErrmsg(errmsg);
        return errorMessage;
      }
      return null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }
}

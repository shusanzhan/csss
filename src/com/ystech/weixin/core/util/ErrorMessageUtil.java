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
        String errcode = (String)jsonObject.get("errcode");
        String errmsg = (String)jsonObject.get("errmsg");
        errorMessage.setErrcode(errcode);
        errorMessage.setErrmsg(errmsg);
        return errorMessage;
      }
      return null;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
}

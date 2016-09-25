package com.ystech.csss.service;

import com.ystech.core.img.FileNameUtil;
import com.ystech.core.util.LogUtil;
import com.ystech.core.util.PathUtil;
import com.ystech.weixin.core.util.ErrorMessage;
import com.ystech.weixin.core.util.ErrorMessageUtil;
import com.ystech.weixin.core.util.WeixinUtil;
import com.ystech.weixin.model.WeixinAccesstoken;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONObject;

public class CsssStaffQrcodeUtil
{
  public static String getQrCodeTicket(WeixinAccesstoken accessToken, String sceneStr)
  {
    try
    {
      String createQrCodeUrl = WeixinUtil.CREATEQRCODE.replace("ACCESS_TOKEN", accessToken.getAccessToken());
      String json = "{\"action_name\":\"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneStr + "\"}}}";
      JSONObject jsonObject = WeixinUtil.httpRequest(createQrCodeUrl, "POST", json);
      ErrorMessage errorMessage = ErrorMessageUtil.paraseErrorMessage(jsonObject);
      if (errorMessage != null)
      {
        LogUtil.error("生成二维码发生错误，错误类型" + jsonObject);
        return null;
      }
      if (jsonObject != null) {
        return jsonObject.getString("ticket");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String getQrCodePath(String ticketUrl, String sceneStr)
  {
    try
    {
      String showCodeUrl = WeixinUtil.SHOWQRCODE.replace("TICKET", ticketUrl);
      File resourceFile = FileNameUtil.getResourceFile(sceneStr + ".jpg");
      saveUrlFile(showCodeUrl, resourceFile);
      String filePath = resourceFile.getAbsolutePath();
      return filePath.replaceAll("\\\\", "/").replace(PathUtil.getWebRootPath(), "");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void saveUrlFile(String fileUrl, File toFile)
    throws Exception
  {
    if (toFile.exists()) {
      return;
    }
    toFile.createNewFile();
    FileOutputStream outImgStream = new FileOutputStream(toFile);
    outImgStream.write(getUrlFileData(fileUrl));
    outImgStream.close();
  }
  
  public static byte[] getUrlFileData(String fileUrl)
    throws Exception
  {
    URL url = new URL(fileUrl);
    HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
    httpConn.connect();
    InputStream cin = httpConn.getInputStream();
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[] buffer = new byte['?'];
    int len = 0;
    while ((len = cin.read(buffer)) != -1) {
      outStream.write(buffer, 0, len);
    }
    cin.close();
    byte[] fileData = outStream.toByteArray();
    outStream.close();
    return fileData;
  }
  
  public static String getUrlDetail(String urlStr, boolean withSep)
    throws Exception
  {
    URL url = new URL(urlStr);
    HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
    httpConn.connect();
    InputStream cin = httpConn.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(cin, "UTF-8"));
    StringBuffer sb = new StringBuffer();
    String rl = null;
    while ((rl = reader.readLine()) != null) {
      if (withSep) {
        sb.append(rl).append(System.getProperty("line.separator"));
      } else {
        sb.append(rl);
      }
    }
    return sb.toString();
  }
}

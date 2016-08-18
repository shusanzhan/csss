package com.ystech.weixin.service;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.core.security.SecurityUserHolder;
import com.ystech.weixin.core.util.WeixinUtil;
import com.ystech.weixin.model.WeixinAccount;
import com.ystech.xwqr.model.sys.User;

@Component("weixinAccountManageImpl")
public class WeixinAccountManageImpl extends HibernateEntityDao<WeixinAccount>{
	public String getAccessToken() {
		String token = "";
		WeixinAccount account = SecurityUserHolder.getWeixinAccount();
		token = account.getAccountaccesstoken();
		if (token != null && !"".equals(token)) {
			// 判断有效时间 是否超过2小时
			java.util.Date end = new java.util.Date();
			java.util.Date start = new java.util.Date(account.getAddtoekntime()
					.getTime());
			if ((end.getTime() - start.getTime()) / 1000 / 3600 >= 2) {
				// 失效 重新获取
				String requestUrl = WeixinUtil.access_token_url.replace(
						"APPID", account.getAccountappid()).replace(
						"APPSECRET", account.getAccountappsecret());
				JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,
						"GET", null);
				if (null != jsonObject) {
					try {
						token = jsonObject.getString("access_token");
						// 重置token
						account.setAccountaccesstoken(token);
						// 重置事件
						account.setAddtoekntime(new Date());
						this.save(account);
					} catch (Exception e) {
						token = null;
						// 获取token失败
						String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
								+ jsonObject.getInt("errcode")
								+ jsonObject.getString("errmsg");
						System.out.println(""+wrongMessage);
					}
				}
			} else {
				return account.getAccountaccesstoken();
			}
		} else {
			String requestUrl = WeixinUtil.access_token_url.replace("APPID",
					account.getAccountappid()).replace("APPSECRET",
					account.getAccountappsecret());
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET",
					null);
			if (null != jsonObject) {
				try {
					token = jsonObject.getString("access_token");
					// 重置token
					account.setAccountaccesstoken(token);
					// 重置事件
					account.setAddtoekntime(new Date());
					this.save(account);
				} catch (Exception e) {
					token = null;
					// 获取token失败
					String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
							+ jsonObject.getInt("errcode")
							+ jsonObject.getString("errmsg");
					System.out.println(""+wrongMessage);
				}
			}
		}
		return token;
	}
	
	public String getAccessToken(String accountId) {
		WeixinAccount weixinAccountEntity = findUniqueBy("weixinAccountid", accountId);
		String token = weixinAccountEntity.getAccountaccesstoken();
		if (token != null && !"".equals(token)) {
			// 判断有效时间 是否超过2小时
			java.util.Date end = new java.util.Date();
			java.util.Date start = new java.util.Date(weixinAccountEntity.getAddtoekntime()
					.getTime());
			if ((end.getTime() - start.getTime()) / 1000 / 3600 >= 2) {
				// 失效 重新获取
				String requestUrl = WeixinUtil.access_token_url.replace(
						"APPID", weixinAccountEntity.getAccountappid()).replace(
						"APPSECRET", weixinAccountEntity.getAccountappsecret());
				JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,
						"GET", null);
				if (null != jsonObject) {
					try {
						token = jsonObject.getString("access_token");
						// 重置token
						weixinAccountEntity.setAccountaccesstoken(token);
						// 重置事件
						weixinAccountEntity.setAddtoekntime(new Date());
						save(weixinAccountEntity);
					} catch (Exception e) {
						token = null;
						// 获取token失败
						String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
								+ jsonObject.getInt("errcode")
								+ jsonObject.getString("errmsg");
						System.out.println(""+wrongMessage);
					}
				}
			} else {
				return weixinAccountEntity.getAccountaccesstoken();
			}
		} else {
			String requestUrl = WeixinUtil.access_token_url.replace("APPID",
					weixinAccountEntity.getAccountappid()).replace("APPSECRET",
							weixinAccountEntity.getAccountappsecret());
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET",
					null);
			if (null != jsonObject) {
				try {
					token = jsonObject.getString("access_token");
					// 重置token
					weixinAccountEntity.setAccountaccesstoken(token);
					// 重置事件
					weixinAccountEntity.setAddtoekntime(new Date());
					save(weixinAccountEntity);
				} catch (Exception e) {
					token = null;
					// 获取token失败
					String wrongMessage = "获取token失败 errcode:{} errmsg:{}"
							+ jsonObject.getInt("errcode")
							+ jsonObject.getString("errmsg");
					System.out.println(""+wrongMessage);
				}
			}
		}
		return token;
	}
}

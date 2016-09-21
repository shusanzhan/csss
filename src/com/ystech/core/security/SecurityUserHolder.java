/**
 * 
 */
package com.ystech.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ystech.weixin.model.WeixinAccount;
import com.ystech.xwqr.model.sys.User;



/**
 * @author shusanzhan
 * @date 2012-11-23
 */
public class SecurityUserHolder {
	public static User getCurrentUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(null==authentication){
			return null;
		}
		if(authentication.getPrincipal() instanceof User){
			return (User) authentication.getPrincipal();
		}
		return null;
	}
	public static WeixinAccount getWeixinAccount(){
		User currentUser = SecurityUserHolder.getCurrentUser();
		if(null==currentUser){
			return null;
		}
		WeixinAccount weixinAccountEntity =currentUser.getWeixinAccount();
		if (weixinAccountEntity != null) {
			return weixinAccountEntity;
		} else {
			weixinAccountEntity = new WeixinAccount();
			// 返回个临时对象，防止空指针
			weixinAccountEntity.setWeixinAccountid("-1");
			weixinAccountEntity.setDbid(3);
			return weixinAccountEntity;
		}
	}
}

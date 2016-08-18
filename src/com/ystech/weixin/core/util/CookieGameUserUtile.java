package com.ystech.weixin.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class CookieGameUserUtile {
	public static final String USER_COOKIE = "gameUser";  
	public static final String GZUI_COOKIE = "openid";  
    
 // 删除cookie  
    public Cookie delCookie(HttpServletRequest request) {  
        Cookie[] cookies = request.getCookies();  
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                if (USER_COOKIE.equals(cookie.getName())) {  
                    cookie.setValue("");  
                    cookie.setMaxAge(0);  
                    return cookie;  
                }  
            }  
        }  
        return null;  
    }  
}

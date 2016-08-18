/**
 * 
 */
package com.ystech.core.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ystech.core.web.BaseController;
import com.ystech.xwqr.model.sys.Message;
import com.ystech.xwqr.model.sys.ReceiveMessageUser;
import com.ystech.xwqr.model.sys.User;
import com.ystech.xwqr.service.sys.MessageManageImpl;
import com.ystech.xwqr.service.sys.ReceiveMessageUserManageImpl;

/**
 * @author shusanzhan
 * @date 2014-3-19
 */
public class MessageUtile extends BaseController{
	public void sendMessage(String title,String content,String url){
		try{
			HttpServletRequest request = getRequest();
			Integer[] receiveMessageUserIds = receiveMessageUserIds();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			MessageManageImpl messageManageImpl  =(MessageManageImpl)webApplicationContext.getBean("messageManageImpl");
			if(null!=receiveMessageUserIds&&receiveMessageUserIds.length>0){
				for (Integer userId : receiveMessageUserIds) {
					if(userId!=0){
						Message message=new Message();
						message.setTitle(title);
						message.setContent(content);
						message.setUrl(url);
						message.setIsNotice(false);
						message.setIsRead(false);
						message.setUserId(userId);
						messageManageImpl.save(message);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Integer[] receiveMessageUserIds(){
		//获取spring的环境信息
		HttpServletRequest request = getRequest();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		ReceiveMessageUserManageImpl receiveMessageUserManageImpl  =(ReceiveMessageUserManageImpl)webApplicationContext.getBean("receiveMessageUserManageImpl");
		List<ReceiveMessageUser> receiveMessageUsers = receiveMessageUserManageImpl.getAll();
		if(null!=receiveMessageUsers&&receiveMessageUsers.size()>0){
			Integer[] integers=new Integer[receiveMessageUsers.size()];
			for (int i=0;i<receiveMessageUsers.size() ;i++) {
				ReceiveMessageUser receiveMessageUser = receiveMessageUsers.get(i);
				if(null!=receiveMessageUser){
					User user = receiveMessageUser.getUser();
					if(null!=user){
						integers[i]=user.getDbid();
					}else{
						integers[i]=0;
					}
				}else{
					integers[i]=0;
				}
			}
			return integers;
		}else{
			return null;
		}
	}
}

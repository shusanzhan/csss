package com.ystech.weixin.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.ystech.core.util.DateUtil;
import com.ystech.core.util.LogUtil;
import com.ystech.core.util.PathUtil;
import com.ystech.weixin.core.resp.Article;
import com.ystech.weixin.core.resp.NewsMessageResp;
import com.ystech.weixin.core.resp.TextMessageResp;
import com.ystech.weixin.core.util.FreemarkerHelper;
import com.ystech.weixin.core.util.MessageUtil;
import com.ystech.weixin.model.WechatSendMessage;
import com.ystech.weixin.model.WeixinAccesstoken;
import com.ystech.weixin.model.WeixinAccount;
import com.ystech.weixin.model.WeixinAutoresponse;
import com.ystech.weixin.model.WeixinExpandconfig;
import com.ystech.weixin.model.WeixinGzuserinfo;
import com.ystech.weixin.model.WeixinMenuentity;
import com.ystech.weixin.model.WeixinNewsitem;
import com.ystech.weixin.model.WeixinReceivetext;
import com.ystech.weixin.model.WeixinSubscribe;
import com.ystech.weixin.model.WeixinTexttemplate;


@Component("wechatService")
public class WechatService {
	private WeixinAutoresponseManageImpl weixinAutoresponseManageImpl;
	private WeixinSubscribeManageImpl weixinSubscribeManageImpl;
	private WeixinAccountManageImpl weixinAccountManageImpl;
	private WeixinReceivetextManageImpl weixinReceivetextManageImpl;
	private WeixinTexttemplateManageImpl weixinTexttemplateManageImpl;
	private WeixinNewstemplateManageImpl weixinNewstemplateManageImpl;
	private WeixinNewsitemManageImpl weixinNewsitemManageImpl;
	private WeixinExpandconfigManageImpl weixinExpandconfigManageImpl;
	private WeixinMenuentityManageImpl weixinMenuentityManageImpl;
	private WeixinGzuserinfoManageImpl weixinGzuserinfoManageImpl;
	private WeixinAccesstokenManageImpl weixinAccesstokenManageImpl;
	//群发信息返回结果处理
	private WechatSendMessageManageImpl wechatSendMessageManageImpl;
	@Resource
	public void setWeixinAutoresponseManageImpl(
			WeixinAutoresponseManageImpl weixinAutoresponseManageImpl) {
		this.weixinAutoresponseManageImpl = weixinAutoresponseManageImpl;
	}

	@Resource
	public void setWeixinSubscribeManageImpl(
			WeixinSubscribeManageImpl weixinSubscribeManageImpl) {
		this.weixinSubscribeManageImpl = weixinSubscribeManageImpl;
	}

	@Resource
	public void setWeixinAccountManageImpl(
			WeixinAccountManageImpl weixinAccountManageImpl) {
		this.weixinAccountManageImpl = weixinAccountManageImpl;
	}

	@Resource
	public void setWeixinReceivetextManageImpl(
			WeixinReceivetextManageImpl weixinReceivetextManageImpl) {
		this.weixinReceivetextManageImpl = weixinReceivetextManageImpl;
	}

	@Resource
	public void setWeixinTexttemplateManageImpl(
			WeixinTexttemplateManageImpl weixinTexttemplateManageImpl) {
		this.weixinTexttemplateManageImpl = weixinTexttemplateManageImpl;
	}

	@Resource
	public void setWeixinNewstemplateManageImpl(
			WeixinNewstemplateManageImpl weixinNewstemplateManageImpl) {
		this.weixinNewstemplateManageImpl = weixinNewstemplateManageImpl;
	}
	@Resource
	public void setWeixinAccesstokenManageImpl(WeixinAccesstokenManageImpl weixinAccesstokenManageImpl) {
		this.weixinAccesstokenManageImpl = weixinAccesstokenManageImpl;
	}

	@Resource
	public void setWeixinNewsitemManageImpl(
			WeixinNewsitemManageImpl weixinNewsitemManageImpl) {
		this.weixinNewsitemManageImpl = weixinNewsitemManageImpl;
	}

	@Resource
	public void setWeixinExpandconfigManageImpl(
			WeixinExpandconfigManageImpl weixinExpandconfigManageImpl) {
		this.weixinExpandconfigManageImpl = weixinExpandconfigManageImpl;
	}

	@Resource
	public void setWeixinMenuentityManageImpl(
			WeixinMenuentityManageImpl weixinMenuentityManageImpl) {
		this.weixinMenuentityManageImpl = weixinMenuentityManageImpl;
	}

	@Resource
	public void setWeixinGzuserinfoManageImpl(
			WeixinGzuserinfoManageImpl weixinGzuserinfoManageImpl) {
		this.weixinGzuserinfoManageImpl = weixinGzuserinfoManageImpl;
	}
	@Resource
	public void setWechatSendMessageManageImpl(WechatSendMessageManageImpl wechatSendMessageManageImpl) {
		this.wechatSendMessageManageImpl = wechatSendMessageManageImpl;
	}

	/**
	 * 功能描述：获取微信端发送请求数据，数据分类为文本、图片、视频、语音、事件；
	 * 操作步骤：
	 * 1、获取微信发送的数据流；
	 * 2、获取数据流信息明细；
	 * 3、通过toUserName （openId）查询获取关注用户，如果系统中存在关注用户，查询出数据。如果系统中不存在那么保存数据
	 * 4、保存微信发送的数据
	 * 5、根据发送的的数据类型，采用不同的处理流程处理数据--text
	 * 5.1 文本消息 关键词回复；单图文-多图文
	 * 5.2 文本消息 外部操作插件
	 * 6、根据发送的的数据类型，采用不同的处理流程处理数据--event
	 * 6.1 关注事件 关注时回复响应
	 * 6.2 取消关注事件 取消关注时响应
	 * 6.3 
	 * @param request
	 * @return
	 */
	public String coreService(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号 原始Id
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			String msgId = requestMap.get("MsgId");
			//消息内容
			String content = requestMap.get("Content");
			LogUtil.info("微信客户端发送请求  fromUserName:"+fromUserName+"   |   ToUserName:"+toUserName+"   |   msgType:"+msgType+"   |   msgId:"+msgId+"   |   content:"+content);
			//根据微信ID,获取配置的全局的数据权限ID
			WeixinAccount weixinAccount = weixinAccountManageImpl.findUniqueBy("weixinAccountid", toUserName);
			String accountId = "";
			if(null!=weixinAccount){
				accountId=weixinAccount.getDbid()+"";
			}else{
				LogUtil.error("-----------===========toUserName："+toUserName+"weixinAccount:查询数据错误!");
			}
			//先取session中的weixinGzuserinfo 如果没有在获取通过数据库获取
			//先取session中的weixinGzuserinfo 如果没有在获取通过数据库获取
			WeixinGzuserinfo weixinGzuserinfo = (WeixinGzuserinfo) session.getAttribute("weixinGzuserinfo");
			if(null==weixinGzuserinfo||null==weixinGzuserinfo.getDbid()){
				WeixinAccesstoken accessToken = com.ystech.weixin.core.util.WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
				//设置关注用户信息
				weixinGzuserinfo = weixinGzuserinfoManageImpl.saveWeixinGzuserinfo(fromUserName, accessToken.getAccessToken(),Integer.parseInt(accountId));
				session.setAttribute("weixinGzuserinfo", weixinGzuserinfo);
			}
			
			// 默认回复此文本消息
			TextMessageResp textMessage = new TextMessageResp();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			//默认回复消息
			textMessage.setContent(getMainMenu());
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);
			
			//保存微信端发送的信息（文本信息）
			saveWeixinReceiveText(content, toUserName, fromUserName, msgId, msgType);
			
			//【微信触发类型】文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respMessage = doTextResponse(content,toUserName,textMessage,accountId,respMessage,fromUserName,request);
			}
			//【微信触发类型】图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				//respContent = "您发送的是图片消息！";
				textMessage.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			//【微信触发类型】地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
				textMessage.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			//【微信触发类型】链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				textMessage.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			//【微信触发类型】音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
				textMessage.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			//【微信触发类型】事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅 关注时回复
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respMessage = doDingYueEventResponse(requestMap, textMessage, respMessage, toUserName, fromUserName, respContent, accountId,request,weixinGzuserinfo);
				}
				// 取消订阅// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息;取消关注获取用户信息
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					weixinGzuserinfoManageImpl.updateByOpenId(fromUserName);
				}
				else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
					respMessage = doScanEventResponse(requestMap, textMessage, respMessage, toUserName, fromUserName, respContent, accountId, request);
				}
				//事件推送群发结果 此处为MASSSENDJOBFINISH
				else if (eventType.equals(MessageUtil.EVENT_TYPE_MASSSENDJOBFINISH)) {
					updateWechatSendMessage(requestMap, msgId);
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)||eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					respMessage = doMyMenuEvent(requestMap, textMessage,  respMessage, toUserName, fromUserName, respContent, accountId, request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}


	/**
	 * Q译通使用指南
	 * 
	 * @return
	 */
	public static String getTranslateUsage() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("微译使用指南").append("\n\n");
		buffer.append("微译为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
		buffer.append("    中 -> 英").append("\n");
		buffer.append("    英 -> 中").append("\n");
		buffer.append("    日 -> 中").append("\n\n");
		buffer.append("使用示例：").append("\n");
		buffer.append("    翻译我是中国人").append("\n");
		buffer.append("    翻译dream").append("\n");
		buffer.append("    翻译さようなら").append("\n\n");
		buffer.append("回复“?”显示主菜单");
		return buffer.toString();
	}

	/**
	 * 功能描述：遍历关键字管理中是否存在用户输入的关键字信息
	 * 
	 * @param content
	 * @return
	 */
	private WeixinAutoresponse findKey(String content, String toUsername) {
		LogUtil.info("---------sys_accountId--------"+toUsername+"|");
		//获取全局的数据权限ID
		String sys_accountId = weixinAccountManageImpl.findUniqueBy("weixinAccountid",toUsername).getDbid()+"";
		LogUtil.info("---------sys_accountId--------"+sys_accountId);
		// 获取关键字管理的列表，匹配后返回信息
		List<WeixinAutoresponse> autoResponses = weixinAutoresponseManageImpl.findBy("accountid", sys_accountId);
		LogUtil.info("---------sys_accountId----关键字查询结果条数：----"+autoResponses!=null?autoResponses.size():0);
		for (WeixinAutoresponse r : autoResponses) {
			// 如果包含关键字
			String kw = r.getKeyword();
			String[] allkw = kw.split(",");
			for (String k : allkw) {
				System.out.println("==============Key:"+k);
				if (k.equals(content)) {
					LogUtil.info("---------sys_accountId----查询结果----"+r);
					return r;
				}
			}
		}
		return null;
	}

	/**
	 * 功能描述：针对文本消息
	 * @param content
	 * @param toUserName
	 * @param textMessage
	 * @param bundler
	 * @param sys_accountId
	 * @param respMessage
	 * @param fromUserName
	 * @param request
	 * @throws Exception 
	 */
	String doTextResponse(String content,String toUserName,TextMessageResp textMessage,
			String sys_accountId,String respMessage,String fromUserName,HttpServletRequest request) throws Exception{
		//=================================================================================================================
		//Step.1 判断关键字信息中是否管理该文本内容。有的话优先采用数据库中的回复
		LogUtil.info("------------微信客户端发送请求--------------Step.1 判断关键字信息中是否管理该文本内容。有的话优先采用数据库中的回复:"+content);
		WeixinAutoresponse autoResponse = findKey(content, toUserName);
		// 根据系统配置的关键字信息，返回对应的消息
		if (autoResponse != null) {
			String resMsgType = autoResponse.getMsgtype();
			if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(resMsgType)) {
				//根据返回消息key，获取对应的文本消息返回给微信客户端
				WeixinTexttemplate textTemplate = weixinTexttemplateManageImpl.get(autoResponse.getTemplateId());
				textMessage.setContent(textTemplate.getContent());
				respMessage = MessageUtil.textMessageToXml(textMessage);
			} else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(resMsgType)) {
				//获取图文消息
				respMessage=getNewsResp(toUserName, fromUserName, request, autoResponse.getTemplateId());
			}
		} else {
			LogUtil.info("------------微信客户端发送请求--------------Step.2  通过微信扩展接口（支持二次开发，例如：翻译，天气）---");
			String accountId = weixinAccountManageImpl.findUniqueBy("weixinAccountid",toUserName).getDbid()+"";
			List<WeixinExpandconfig> weixinExpandconfigEntityLst = weixinExpandconfigManageImpl.find("FROM WeixinExpandconfig where accountid=? and keyword=?",new Object[]{accountId,content});
			if (weixinExpandconfigEntityLst.size() != 0) {
				for (WeixinExpandconfig wec : weixinExpandconfigEntityLst) {
					boolean findflag = false;// 是否找到关键字信息
					// 如果已经找到关键字并处理业务，结束循环。
					if (findflag) {
						break;// 如果找到结束循环
					}
					String[] keys = wec.getKeyword().split(",");
					for (String k : keys) {
						if (content.indexOf(k) != -1) {
							String className = wec.getClassname();
							//KeyServiceI keyService = (KeyServiceI) Class.forName(className).newInstance();
							//respMessage = keyService.excute(content,textMessage, request);
							findflag = true;// 改变标识，已经找到关键字并处理业务，结束循环。
							break;// 当前关键字信息处理完毕，结束当前循环
						}
					}
				}
			}else{
				//转发至多客服
				textMessage.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

		}
		return respMessage;
	}
	
	/**
	 * 功能描述：关注时回复针对事件消息
	 * @param requestMap
	 * @param textMessage
	 * @param bundler
	 * @param respMessage
	 * @param toUserName
	 * @param fromUserName
	 */
	String doDingYueEventResponse(Map<String, String> requestMap,TextMessageResp textMessage ,String respMessage
			,String toUserName,String fromUserName,String respContent,String sys_accountId,HttpServletRequest request,WeixinGzuserinfo weixinGzuserinfo){
		WeixinAccount weixinAccount = weixinAccountManageImpl.get(Integer.parseInt(sys_accountId));
		respContent = "谢谢您的关注,";
		if(null!=weixinAccount){
			respContent=respContent+""+weixinAccount.getAccountname()+" 公众号！";
		}
		List<WeixinSubscribe> weixinSubscribes = weixinSubscribeManageImpl.findBy("accountid", sys_accountId);
		if (null!=weixinSubscribes&&weixinSubscribes.size() != 0) {
			WeixinSubscribe subscribe = weixinSubscribes.get(0);
			String typeParams = subscribe.getMsgType();
			if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(typeParams)) {
				//文本模板信息 通过文本模板信息Id查询模板信息
				WeixinTexttemplate textTemplate = weixinTexttemplateManageImpl.get(subscribe.getTemplateId());
				String content = textTemplate.getContent();
				textMessage.setContent(content);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			} else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(typeParams)) {
				//图文消息，通过图文模板消息的ID查询图文信息
				respMessage = getNewsResp(toUserName, fromUserName, request, subscribe.getTemplateId());
			}
		}
		
		return respMessage;
	}
	/**
	 * 功能描述：扫描关注时回复（已经关注公众号） 更新参数二维码的扫描记录信息
	 * @param requestMap
	 * @param textMessage
	 * @param bundler
	 * @param respMessage
	 * @param toUserName
	 * @param fromUserName
	 */
	String doScanEventResponse(Map<String, String> requestMap,TextMessageResp textMessage ,String respMessage
			,String toUserName,String fromUserName,String respContent,String sys_accountId,HttpServletRequest request){
		
		WeixinAccount weixinAccount = weixinAccountManageImpl.get(Integer.parseInt(sys_accountId));
		respContent = "谢谢您的关注,";
		if(null!=weixinAccount){
			respContent=respContent+""+weixinAccount.getAccountname()+" 公众号！";
		}
		List<WeixinSubscribe> weixinSubscribes = weixinSubscribeManageImpl.findBy("accountid", sys_accountId);
		if (null!=weixinSubscribes&&weixinSubscribes.size() != 0) {
			WeixinSubscribe subscribe = weixinSubscribes.get(0);
			String typeParams = subscribe.getMsgType();
			if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(typeParams)) {
				//文本模板信息 通过文本模板信息Id查询模板信息
				WeixinTexttemplate textTemplate = weixinTexttemplateManageImpl.get(subscribe.getTemplateId());
				String content = textTemplate.getContent();
				textMessage.setContent(content);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			} else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(typeParams)) {
				//图文消息，通过图文模板消息的ID查询图文信息
				respMessage = getNewsResp(toUserName, fromUserName, request, subscribe.getTemplateId());
			}
		}
		return respMessage;
	}

	
	/**
	 * 功能描述：微信端点击事件响应所有事件都在此方法中进行处理
	 * 1、通过eventKey获取操作连接，eventKey是在发布菜单栏菜单栏中的连接地址
	 * 2、通过eventKey获取操作菜单中的操作menuEntity
	 * 3、根据menuEntity的相应菜单类型 （text、news、
	 * @param requestMap
	 * @param textMessage
	 * @param bundler
	 * @param respMessage
	 * @param toUserName
	 * @param fromUserName
	 * @param respContent
	 * @param sys_accountId
	 * @param request
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	String doMyMenuEvent(Map<String, String> requestMap,TextMessageResp textMessage ,String respMessage
			,String toUserName,String fromUserName,String respContent,String sys_accountId,HttpServletRequest request) throws Exception{
		LogUtil.error("自定义菜单点击事件doMyMenuEvent：");
		String key = requestMap.get("EventKey");
		//自定义菜单CLICK类型
		WeixinMenuentity menuEntity = weixinMenuentityManageImpl.findUniqueBy("url",key);
		//自定义菜单VIEW类型
		if(null==menuEntity){
			LogUtil.error("自定义菜单点击事件menuEntity 为空："+key);
			if(key.contains("P")){
				String dbid = key.replace("P", "");
				if(null!=dbid){
					int parseInt = Integer.parseInt(dbid);
					menuEntity = weixinMenuentityManageImpl.get(parseInt);
				}
			}
		}
		if (menuEntity != null) {
			String type = menuEntity.getMsgtype();
			if((type==null||type.trim().length()<=0)&&null==menuEntity.getTemplateid()){
				LogUtil.error("自定义菜单点击事件menuEntity 为空： typeweinull"+key);
				String content = menuEntity.getUrl();
				respMessage = doTextResponse(content, toUserName, textMessage, sys_accountId, respMessage, fromUserName, request);
			}
			if(null!=menuEntity.getTemplateid()&&menuEntity.getTemplateid().trim().length()>0){
				if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(type)) {
					WeixinTexttemplate textTemplate = weixinTexttemplateManageImpl.get(Integer.parseInt(menuEntity.getTemplateid()));
					String content = textTemplate.getContent();
					textMessage.setContent(content);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(type)) {
					//获取多图文信息构造多图文回复
					String templateid = menuEntity.getTemplateid();
					if(null!=templateid){
						respMessage = getNewsResp(toUserName, fromUserName, request,Integer.parseInt(templateid));
				}
				} else if ("expand".equals(type)) {
					//WeixinExpandconfig expandconfigEntity = weixinExpandconfigManageImpl. getEntity(WeixinExpandconfigEntity.class,menuEntity.getTemplateId());
					WeixinExpandconfig expandconfigEntity = weixinExpandconfigManageImpl.get(Integer.parseInt(menuEntity.getTemplateid()));
					String className = expandconfigEntity.getClassname();
					//KeyServiceI keyService = (KeyServiceI) Class.forName(className).newInstance();
					//respMessage = keyService.excute("", textMessage,request);
	
				}
			}
		}
		return respMessage;
	}
	/**
	 * //获取多图文信息构造多图文回复
	 * @param toUserName
	 * @param fromUserName
	 * @param request
	 * @param menuEntity
	 * @return
	 */
	private String getNewsResp(String toUserName, String fromUserName,
			HttpServletRequest request, Integer weixinNewstemplateId) {
		String respMessage;
		//图文消息，通过图文模板消息的ID查询图文信息
		List<WeixinNewsitem> newsList = weixinNewsitemManageImpl.findBy("weixinNewstemplate.dbid",weixinNewstemplateId);
		List<Article> articleList = new ArrayList<Article>();
		String domain = PathUtil.getDomain(request);
		for (WeixinNewsitem news : newsList) {
			Article article = new Article();
			article.setTitle(news.getTitle());
			article.setPicUrl(domain + news.getImagepath());
			String url = "";
			if (null==news.getUrl()||news.getUrl().trim().length()<=0) {
				url = domain+ "/newsItemWechat/readNewsItem?dbid="+ news.getDbid();
			} else {
				url = news.getUrl();
			}
			article.setUrl(url);
			article.setDescription(news.getDescription());
			articleList.add(article);
		}
		//组装成 发送图文消息
		NewsMessageResp newsResp = new NewsMessageResp();
		newsResp.setCreateTime(new Date().getTime());
		newsResp.setFromUserName(toUserName);
		newsResp.setToUserName(fromUserName);
		newsResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsResp.setArticleCount(newsList.size());
		newsResp.setArticles(articleList);
		respMessage = MessageUtil.newsMessageToXml(newsResp);
		return respMessage;
	}
	//保存公众号发送的任何信息
	private void saveWeixinReceiveText(String content, String toUserName,
			String fromUserName, String msgId, String msgType) {
		// 保存接收到的信息
		WeixinReceivetext receiveText = new WeixinReceivetext();
		receiveText.setContent(content);
		Timestamp temp = Timestamp.valueOf(DateUtil.format2(new Date()));
		receiveText.setCreatetime(temp);
		receiveText.setFromusername(fromUserName);
		receiveText.setTousername(toUserName);
		receiveText.setMsgid(msgId);
		receiveText.setMsgtype(msgType);
		receiveText.setResponse("0");
		receiveText.setAccountid(toUserName);
		WeixinGzuserinfo weixinGzuserinfo = weixinGzuserinfoManageImpl.findUniqueBy("openid", fromUserName);
		if(null!=weixinGzuserinfo){
			receiveText.setNickname(weixinGzuserinfo.getNickname());
		}
		this.weixinReceivetextManageImpl.save(receiveText);
	}
	/**
	 * 欢迎语
	 * @return
	 */
	public static String getMainMenu() {
		String html = new FreemarkerHelper().parseTemplate("/weixin/welcome.ftl", null);
		return html;
	}
	/**
	 * 功能描述：更新发送信息返回结果
	 * @param requestMap
	 * @param MsgID
	 */
	private void updateWechatSendMessage(Map<String, String> requestMap,String MsgID){
		if(null==MsgID||MsgID.trim().length()<=0){
			return ;
		}
		List<WechatSendMessage> wechatSendMessages = wechatSendMessageManageImpl.findBy("msgId", MsgID);
		//当存在群发信息时 更新数据
		if(null!=wechatSendMessages&&wechatSendMessages.size()>0){
			WechatSendMessage wechatSendMessage = wechatSendMessages.get(0);
			//群发的结构
			String Status = requestMap.get("Status");
			if(null!=Status){
				wechatSendMessage.setStatus(Status);
			}
			//粉丝数
			String TotalCount = requestMap.get("TotalCount");
			if(null!=TotalCount){
				wechatSendMessage.setTotalCount(TotalCount);
			}
			//过滤
			String FilterCount = requestMap.get("FilterCount");
			if(null!=FilterCount){
				wechatSendMessage.setFilterCount(FilterCount);
			}
			//发送成功的粉丝数
			String SentCount = requestMap.get("SentCount");
			if(null!=SentCount){
				wechatSendMessage.setSentCount(SentCount);
			}
			//发送失败的粉丝数
			String ErrorCount = requestMap.get("ErrorCount");
			if(null!=ErrorCount){
				wechatSendMessage.setErrorCount(ErrorCount);
			}
			wechatSendMessageManageImpl.save(wechatSendMessage);
		}
	}
}

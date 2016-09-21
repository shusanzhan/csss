package com.ystech.wechat.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.weixin.model.WeixinNewsitem;
import com.ystech.weixin.service.WeixinNewsitemManageImpl;

@Component("newsItemWechatAction")
@Scope("prototype")
public class NewsItemWechatAction extends BaseController{
	private WeixinNewsitemManageImpl weixinNewsitemManageImpl;

	@Resource
	public void setWeixinNewsitemManageImpl(
			WeixinNewsitemManageImpl weixinNewsitemManageImpl) {
		this.weixinNewsitemManageImpl = weixinNewsitemManageImpl;
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public String readNewsItem() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		try {
			WeixinNewsitem weixinNewsitem = weixinNewsitemManageImpl.get(dbid);
			request.setAttribute("weixinNewsitem", weixinNewsitem);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return "readNewsItem";
	}
}
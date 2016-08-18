package com.ystech.weixin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.Page;
import com.ystech.core.security.SecurityUserHolder;
import com.ystech.core.util.DateUtil;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.weixin.model.WeixinAccount;
import com.ystech.weixin.model.WeixinAutoresponse;
import com.ystech.weixin.model.WeixinNewstemplate;
import com.ystech.weixin.model.WeixinTexttemplate;
import com.ystech.weixin.service.WeixinAccountManageImpl;
import com.ystech.weixin.service.WeixinAutoresponseManageImpl;
import com.ystech.weixin.service.WeixinNewstemplateManageImpl;
import com.ystech.weixin.service.WeixinTexttemplateManageImpl;

@Component("weixinAutoresponseAction")
@Scope("prototype")
public class WeixinAutoresponseAction extends BaseController{
	private WeixinAutoresponse weixinAutoresponse;
	private WeixinAutoresponseManageImpl weixinAutoresponseManageImpl;
	private WeixinAccountManageImpl weixinAccountManageImpl;
	private WeixinTexttemplateManageImpl weixinTexttemplateManageImpl;
	private WeixinNewstemplateManageImpl weixinNewstemplateManageImpl;
	public WeixinAutoresponse getWeixinAutoresponse() {
		return weixinAutoresponse;
	}
	public void setWeixinAutoresponse(WeixinAutoresponse weixinAutoresponse) {
		this.weixinAutoresponse = weixinAutoresponse;
	}
	public WeixinAutoresponseManageImpl getWeixinAutoresponseManageImpl() {
		return weixinAutoresponseManageImpl;
	}
	@Resource
	public void setWeixinAutoresponseManageImpl(
			WeixinAutoresponseManageImpl weixinAutoresponseManageImpl) {
		this.weixinAutoresponseManageImpl = weixinAutoresponseManageImpl;
	}
	@Resource
	public void setWeixinAccountManageImpl(
			WeixinAccountManageImpl weixinAccountManageImpl) {
		this.weixinAccountManageImpl = weixinAccountManageImpl;
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
	/**
	 * 功能描述：
	 * 参数描述： 
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryList() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer pageSize = ParamUtil.getIntParam(request, "pageSize", 10);
		Integer pageNo = ParamUtil.getIntParam(request, "currentPage", 1);
		try{
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			 Page<WeixinAutoresponse> page= weixinAutoresponseManageImpl.pagedQuerySql(pageNo, pageSize, WeixinAutoresponse.class,  "select * from weixin_autoresponse where accountid=?", new Object[]{weixinAccount.getDbid()+""});
			 request.setAttribute("page", page);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return "list";
	}

	/**
	 * 功能描述： 
	 * 参数描述： 
	 * 逻辑描述：
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		HttpServletRequest request = this.getRequest();
		try{
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			List<WeixinTexttemplate> weixinTexttemplates = weixinTexttemplateManageImpl.findBy("accountid", weixinAccount.getDbid()+"");
			request.setAttribute("weixinTexttemplates", weixinTexttemplates);
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		return  "edit";
	}

	/**
	 * 功能描述： 
	 * 参数描述： 
	 * 逻辑描述：
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			WeixinAutoresponse weixinAutoresponse2 = weixinAutoresponseManageImpl.get(dbid);
			request.setAttribute("weixinAutoresponse", weixinAutoresponse2);
			if(weixinAutoresponse2.getMsgtype().equals("text")){
				List<WeixinTexttemplate> weixinTexttemplates = weixinTexttemplateManageImpl.findBy("accountid", weixinAccount.getDbid()+"");
				request.setAttribute("weixinTexttemplates", weixinTexttemplates);
			}
			if(weixinAutoresponse2.getMsgtype().equals("news")){
				 List<WeixinNewstemplate> weixinTexttemplates = weixinNewstemplateManageImpl.findBy("accountid", weixinAccount.getDbid()+"");
				request.setAttribute("weixinTexttemplates", weixinTexttemplates);
			}
		}
		return "edit";
	}

	/**
	 * 功能描述： 
	 * 参数描述： 
	 * 逻辑描述：
	 * 
	 * @return
	 * @throws Exception
	 */
	public void save() throws Exception {
		HttpServletRequest request = getRequest();
		String msgtype = request.getParameter("weixinAutoresponse.msgtype");
		Integer templateId = ParamUtil.getIntParam(request, "templateId", -1);
		try{
				Integer dbid = weixinAutoresponse.getDbid();
				WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
				weixinAutoresponse.setAccountid(weixinAccount.getDbid()+"");
				if(msgtype.equals("text")){
					  WeixinTexttemplate weixinTexttemplate = weixinTexttemplateManageImpl.get(templateId);
					  if(null!=weixinTexttemplate){
						  weixinAutoresponse.setTemplateId(weixinTexttemplate.getDbid());
						  weixinAutoresponse.setTemplatename(weixinTexttemplate.getTemplatename());
					  }
				 }
				 if(msgtype.equals("news")){
					 WeixinNewstemplate weixinNewstemplate = weixinNewstemplateManageImpl.get(templateId);
					  if(null!=weixinNewstemplate){
						  weixinAutoresponse.setTemplateId(weixinNewstemplate.getDbid());
						  weixinAutoresponse.setTemplatename(weixinNewstemplate.getTemplatename());
					  }
				 }
				 if(dbid==null||dbid<=0){
					 weixinAutoresponse.setAddtime(DateUtil.format2(new Date()));
						weixinAutoresponseManageImpl.save(weixinAutoresponse);
				 }else{
					 weixinAutoresponseManageImpl.save(weixinAutoresponse);
				 }
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/weixinAutoresponse/queryList", "保存数据成功！");
		return ;
	}

	/**
	 * 功能描述： 参数描述： 逻辑描述：
	 * 
	 * @return
	 * @throws Exception
	 */
	public void delete() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		if(null!=dbids&&dbids.length>0){
			try {
				for (Integer dbid : dbids) {
					weixinAutoresponseManageImpl.deleteById(dbid);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				renderErrorMsg(e, "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("未选中数据！"), "");
			return ;
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/weixinAutoresponse/queryList"+query, "删除数据成功！");
		return;
	}
}

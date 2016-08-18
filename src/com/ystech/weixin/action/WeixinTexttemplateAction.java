package com.ystech.weixin.action;

import java.util.Date;

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
import com.ystech.weixin.model.WeixinTexttemplate;
import com.ystech.weixin.service.WeixinAccountManageImpl;
import com.ystech.weixin.service.WeixinTexttemplateManageImpl;

@Component("weixinTexttemplateAction")
@Scope("prototype")
public class WeixinTexttemplateAction extends BaseController{
	private WeixinTexttemplate weixinTexttemplate;
	private WeixinTexttemplateManageImpl weixinTexttemplateManageImpl;
	private WeixinAccountManageImpl weixinAccountManageImpl;
	public WeixinTexttemplate getWeixinTexttemplate() {
		return weixinTexttemplate;
	}
	public void setWeixinTexttemplate(WeixinTexttemplate weixinTexttemplate) {
		this.weixinTexttemplate = weixinTexttemplate;
	}
	@Resource
	public void setWeixinTexttemplateManageImpl(
			WeixinTexttemplateManageImpl weixinTexttemplateManageImpl) {
		this.weixinTexttemplateManageImpl = weixinTexttemplateManageImpl;
	}
	@Resource
	public void setWeixinAccountManageImpl(
			WeixinAccountManageImpl weixinAccountManageImpl) {
		this.weixinAccountManageImpl = weixinAccountManageImpl;
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
		String name = request.getParameter("name");
		try{
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			Page<WeixinTexttemplate> page=null;
			if(null!=name&&name.trim().length()>0){
				page = weixinTexttemplateManageImpl.pagedQuerySql(pageNo, pageSize, WeixinTexttemplate.class,  "select * from Weixin_Texttemplate where accountid=? and templatename like ?", new Object[]{weixinAccount.getDbid()+"","%"+name+"%"});
			}else{
				page = weixinTexttemplateManageImpl.pagedQuerySql(pageNo, pageSize, WeixinTexttemplate.class,  "select * from Weixin_Texttemplate where accountid=?", new Object[]{weixinAccount.getDbid()+""});
			}
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
	public String edit() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			WeixinTexttemplate weixinTexttemplate2 = weixinTexttemplateManageImpl.get(dbid);
			request.setAttribute("weixinTexttemplate", weixinTexttemplate2);
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
		try{
			Integer dbid = weixinTexttemplate.getDbid();
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			weixinTexttemplate.setAccountid(weixinAccount.getDbid()+"");
			if(dbid==null||dbid<=0){
				weixinTexttemplate.setAddtime(DateUtil.format2(new Date()));
				weixinTexttemplateManageImpl.save(weixinTexttemplate);
			}else{
				weixinTexttemplateManageImpl.save(weixinTexttemplate);
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/weixinTexttemplate/queryList", "保存数据成功！");
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
					weixinTexttemplateManageImpl.deleteById(dbid);
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
		renderMsg("/weixinTexttemplate/queryList"+query, "删除数据成功！");
		return;
	}

	
}

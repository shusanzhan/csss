package com.ystech.weixin.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.security.SecurityUserHolder;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.weixin.core.common.Button;
import com.ystech.weixin.core.common.CommonButton;
import com.ystech.weixin.core.common.ComplexButton;
import com.ystech.weixin.core.common.Menu;
import com.ystech.weixin.core.common.ViewButton;
import com.ystech.weixin.core.util.WeixinUtil;
import com.ystech.weixin.model.WeixinAccesstoken;
import com.ystech.weixin.model.WeixinAccount;
import com.ystech.weixin.model.WeixinMenuentity;
import com.ystech.weixin.service.WeixinAccesstokenManageImpl;
import com.ystech.weixin.service.WeixinAccountManageImpl;
import com.ystech.weixin.service.WeixinMenuentityManageImpl;
import com.ystech.xwqr.model.sys.User;

import net.sf.json.JSONObject;
@Component("weixinMenuentityAction")
@Scope("prototype")
public class WeixinMenuentityAction extends BaseController{
	private String message;
	private WeixinMenuentityManageImpl weixinMenuentityManageImpl;
	private WeixinAccesstokenManageImpl weixinAccesstokenManageImpl;
	private WeixinMenuentity weixinMenuentity;
	private WeixinAccountManageImpl weixinAccountManageImpl;

	public WeixinMenuentity getWeixinMenuentity() {
		return weixinMenuentity;
	}
	public void setWeixinMenuentity(WeixinMenuentity weixinMenuentity) {
		this.weixinMenuentity = weixinMenuentity;
	}
	@Resource
	public void setWeixinMenuentityManageImpl(
			WeixinMenuentityManageImpl weixinMenuentityManageImpl) {
		this.weixinMenuentityManageImpl = weixinMenuentityManageImpl;
	}
	
	public WeixinAccountManageImpl getWeixinAccountManageImpl() {
		return weixinAccountManageImpl;
	}
	@Resource
	public void setWeixinAccountManageImpl(
			WeixinAccountManageImpl weixinAccountManageImpl) {
		this.weixinAccountManageImpl = weixinAccountManageImpl;
	}
	public WeixinMenuentityManageImpl getWeixinMenuentityManageImpl() {
		return weixinMenuentityManageImpl;
	}
	@Resource
	public void setWeixinAccesstokenManageImpl(
			WeixinAccesstokenManageImpl weixinAccesstokenManageImpl) {
		this.weixinAccesstokenManageImpl = weixinAccesstokenManageImpl;
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public String queryList() throws Exception {
		HttpServletRequest request = this.getRequest();
		try{
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			List<WeixinMenuentity> weixinMenuentities = weixinMenuentityManageImpl.find("from WeixinMenuentity where accountid=? and weixinMenuentity.dbid IS NULL order by orders", new Object[]{weixinAccount.getDbid()+""});
			request.setAttribute("weixinMenuentities", weixinMenuentities);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "list";
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		HttpServletRequest request = this.getRequest();
		try{
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			request.setAttribute("weixinAccount", weixinAccount);
			String productCateGorySelect = weixinMenuentityManageImpl.getProductCateGorySelect(weixinAccount.getDbid(),null);
			request.setAttribute("productCateGorySelect", productCateGorySelect);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "edit";
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		try{
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			Integer currentBussi = weixinAccount.getDbid();
			if(dbid>0){
				WeixinMenuentity weixinMenuentity2 = weixinMenuentityManageImpl.get(dbid);
				String productCateGorySelect = weixinMenuentityManageImpl.getProductCateGorySelect(currentBussi,weixinMenuentity2.getWeixinMenuentity());
				request.setAttribute("productCateGorySelect", productCateGorySelect);
				request.setAttribute("weixinMenuentity", weixinMenuentity2);
			}else{
				String productCateGorySelect = weixinMenuentityManageImpl.getProductCateGorySelect(currentBussi,null);
				request.setAttribute("productCateGorySelect", productCateGorySelect);
			}
			request.setAttribute("weixinAccount", weixinAccount);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "edit";
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void save() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer parendId = ParamUtil.getIntParam(request, "parentId", -1);
		if(parendId>-1){
			try{
				User currentUser = SecurityUserHolder.getCurrentUser();
				WeixinAccount weixinAccount = weixinAccountManageImpl.findUniqueBy("userDbid", currentUser.getDbid());
				WeixinMenuentity parent=null;
				if(parendId>0){
					parent = weixinMenuentityManageImpl.get(parendId);
					weixinMenuentity.setWeixinMenuentity(parent);
				}
				weixinMenuentity.setAccountid(weixinAccount.getDbid()+"");
				//第一添加数据 保存
				weixinMenuentityManageImpl.save(weixinMenuentity);
				
			}catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				renderErrorMsg(e, "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("系统异常！"), "");
			return;
		}
		renderMsg("/weixinMenuentity/queryList?parentMenu=1", "保存数据成功！");
		return ;
	}
	
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void delete() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(null!=dbid&&dbid>0){
			try {
					List<WeixinMenuentity> childs = weixinMenuentityManageImpl.findBy("weixinMenuentity.dbid", dbid);
					if(null!=childs&&childs.size()>0){
						renderErrorMsg(new Throwable("该数据有子级分类，请先删除子级分类在删除数据！"), "");
						return ;
					}else{
						weixinMenuentityManageImpl.deleteById(dbid);
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
		renderMsg("/weixinMenuentity/queryList"+query+"&parentMenu=1", "删除数据成功！");
		return;
	}
	/**
	 * 功能描述：同步微信菜单
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void sameMenu()  {
		HttpServletRequest request = this.getRequest();
		try {
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			WeixinAccesstoken weixinAccesstoken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			String hql = "from WeixinMenuentity where fatherid is null and accountId = '"
					+ weixinAccount.getDbid() + "'  order by  orders asc";
			List<WeixinMenuentity> menuList = weixinMenuentityManageImpl.find(hql, null);
			Menu menu = new Menu();
			Button firstArr[] = new Button[menuList.size()];
			for (int a = 0; a < menuList.size(); a++) {
				WeixinMenuentity entity = menuList.get(a);
				System.out.println("=========="+a);
				String hqls = "from WeixinMenuentity where fatherid = '" + entity.getDbid()
						+ "' and accountId = '" + weixinAccount.getDbid()
						+ "'  order by  orders asc";
				List<WeixinMenuentity> childList = weixinMenuentityManageImpl.find(hqls, null);
				if (childList.size() == 0) {
					if("view".equals(entity.getType())){
						ViewButton viewButton = new ViewButton();
						viewButton.setName(entity.getName());
						viewButton.setType(entity.getType());
						viewButton.setUrl(entity.getUrl());
						firstArr[a] = viewButton;
					}else if("click".equals(entity.getType())){
						CommonButton cb = new CommonButton();
						cb.setKey(entity.getDbid()+"P");
						cb.setName(entity.getName());
						cb.setType(entity.getType());
						firstArr[a] = cb;
					}
				} else {
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(entity.getName());
					Button[] secondARR = new Button[childList.size()];
					for (int i = 0; i < childList.size(); i++) {
						WeixinMenuentity children = childList.get(i);
						String type = children.getType();
						if ("view".equals(type)) {
							ViewButton viewButton = new ViewButton();
							viewButton.setName(children.getName());
							viewButton.setType(children.getType());
							viewButton.setUrl(children.getUrl());
							secondARR[i] = viewButton;

						} else if ("click".equals(type)) {
							CommonButton cb1 = new CommonButton();
							cb1.setName(children.getName());
							cb1.setType(children.getType());
							cb1.setKey(children.getDbid()+"P");
							secondARR[i] = cb1;
						}

					}
					complexButton.setSub_button(secondARR);
					firstArr[a] = complexButton;
				}
			}
			menu.setButton(firstArr);
			JSONObject jsonMenu = JSONObject.fromObject(menu);
			System.out.println("=========="+jsonMenu.toString());
			String accessToken = weixinAccesstoken.getAccessToken();
			if(null==accessToken){
				renderErrorMsg(new Throwable("同步菜单失败！"), "");
				return ;
			}
			String url = WeixinUtil.menu_create_url.replace("ACCESS_TOKEN",accessToken);
			JSONObject jsonObject= new JSONObject();
			try {
				jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu.toString());
				System.out.println("==========="+jsonObject);
				if(jsonObject!=null){
					if (0 == jsonObject.getInt("errcode")) {
						message = "同步菜单信息数据成功！";
						renderMsg("",message);
						return ;
					}
					else {
						message = "同步菜单信息数据失败！错误码为："+jsonObject.getInt("errcode")+"错误信息为："+jsonObject.getString("errmsg");
						renderMsg("",message);
						return ;
					}
				}else{
					message = "同步菜单信息数据失败！同步自定义菜单URL地址不正确。";
					renderMsg("",message);
					return ;
				}
			} catch (Exception e) {
				message = "同步菜单信息数据失败！";
				e.printStackTrace();
			}finally{
				System.out.println(""+message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		renderErrorMsg(new Throwable("同步菜单失败！"), "");
		return ;
	}
	/**
	 * 功能描述：删除微信菜单
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void deleteWechatMenu() throws Exception {
		try {
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			if(null==accessToken){
				renderErrorMsg(new Throwable("同步菜单失败！"), "");
				return ;
			}
			String url = WeixinUtil.menu_delete_url.replace("ACCESS_TOKEN",accessToken.getAccessToken());
			JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
			System.out.println("==========="+jsonObject.toString());
			if(null!=jsonObject){
				if (0 == jsonObject.getInt("errcode")) {
					message = "同步菜单信息数据成功！";
					renderMsg("",message);
					return ;
				}else{
					message = "同步菜单信息数据失败！错误码为："+jsonObject.getInt("errcode")+"错误信息为："+jsonObject.getString("errmsg");
					renderMsg("",message);
					return ;
				}
			}else{
				message = "同步菜单信息数据失败！同步自定义菜单URL地址不正确。";
				renderMsg("",message);
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			renderErrorMsg(e, "");
			return ;
		}
	}
}

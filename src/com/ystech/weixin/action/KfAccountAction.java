package com.ystech.weixin.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.Page;
import com.ystech.core.security.SecurityUserHolder;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.util.PathUtil;
import com.ystech.core.web.BaseController;
import com.ystech.weixin.core.util.ErrorMessage;
import com.ystech.weixin.core.util.ErrorMessageUtil;
import com.ystech.weixin.core.util.WeixinUploadMidea;
import com.ystech.weixin.core.util.WeixinUtil;
import com.ystech.weixin.model.KfAccount;
import com.ystech.weixin.model.WeixinAccesstoken;
import com.ystech.weixin.model.WeixinAccount;
import com.ystech.weixin.service.KfAccountManageImpl;
import com.ystech.weixin.service.WeixinAccesstokenManageImpl;
import com.ystech.weixin.service.WeixinAccountManageImpl;
import com.ystech.xwqr.model.sys.User;

@Component("kfAccountAction")
@Scope("prototype")
public class KfAccountAction extends BaseController{
	private KfAccount kfAccount;
	private KfAccountManageImpl kfAccountManageImpl;
	private WeixinAccountManageImpl weixinAccountManageImpl;
	private WeixinAccesstokenManageImpl weixinAccesstokenManageImpl;
	public KfAccount getKfAccount() {
		return kfAccount;
	}
	public void setKfAccount(KfAccount kfAccount) {
		this.kfAccount = kfAccount;
	}
	@Resource
	public void setKfAccountManageImpl(KfAccountManageImpl kfAccountManageImpl) {
		this.kfAccountManageImpl = kfAccountManageImpl;
	}
	@Resource
	public void setWeixinAccountManageImpl(
			WeixinAccountManageImpl weixinAccountManageImpl) {
		this.weixinAccountManageImpl = weixinAccountManageImpl;
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
	@SuppressWarnings("unchecked")
	public String queryList() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer pageSize = ParamUtil.getIntParam(request, "pageSize", 10);
		Integer pageNo = ParamUtil.getIntParam(request, "currentPage", 1);
		try{
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			 Page<KfAccount> page = kfAccountManageImpl.pagedQuerySql(pageNo, pageSize, KfAccount.class, "select * from weixin_KfAccount ",null);
			 request.setAttribute("page", page);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	/**
	 * 功能描述： 参数描述： 逻辑描述：
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		HttpServletRequest request = this.getRequest();
		try{
			 User currentUser = SecurityUserHolder.getCurrentUser();
			 WeixinAccount weixinAccount = weixinAccountManageImpl.findUniqueBy("userDbid", currentUser.getDbid());
			 request.setAttribute("weixinAccount", weixinAccount);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return "edit";
	}
	/**
	* 功能描述：绑定微信跳转页面
	* 参数描述：
	* 逻辑描述：
	* @return
	* @throws Exception
	*/
	public String inviteworker() throws Exception {
		HttpServletRequest request = getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		try {
			KfAccount kfAccount2 = kfAccountManageImpl.get(dbid);
			request.setAttribute("kfAccount", kfAccount2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "inviteworker";
	}
	/**
	 * 功能描述：保存邀请申请
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void saveInviteworker() throws Exception {
		HttpServletRequest request = getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "kfAccountId", -1);
		String wx = request.getParameter("kfWx");
		try {
			KfAccount kfAccount2 = kfAccountManageImpl.get(dbid);
			kfAccount2.setInviteExpireTime(new Date());
			kfAccount2.setInviteWx(wx);
			kfAccountManageImpl.save(kfAccount2);
			
			String message = inviteworkerWx(kfAccount2);
			if(message.equals("0")){
				renderMsg("/kfAccount/queryList", "保存数据成功");
			}else{
				renderErrorMsg(new Throwable(message), "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		return;
	}
	/**
	* 功能描述：同步客户列表
	* 参数描述：
	* 逻辑描述：
	* @return
	* @throws Exception
	*/
	public void sysUpload() throws Exception {
		HttpServletRequest request = this.getRequest();
		try{
			 User currentUser = SecurityUserHolder.getCurrentUser();
			 WeixinAccount weixinAccount = weixinAccountManageImpl.findUniqueBy("userDbid", currentUser.getDbid());
			 WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			 String getKfList = WeixinUtil.GETKFLIST.replace("ACCESS_TOKEN", accessToken.getAccessToken());
			 JSONObject jsonObject = WeixinUtil.httpRequest(getKfList, "GET", null);
			 if(null!=jsonObject){
				JSONArray fromObject= jsonObject.getJSONArray("kf_list");
				if(null!=fromObject){
					for (Object object : fromObject) {
						JSONObject json=(JSONObject) object;
						saveOrUpdate(json);
					}
				}
			}else{
				renderErrorMsg(new Throwable("同步数据失败！"), "");
				return ;
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderMsg("/kfAccount/queryList", "同步数据成功！");
		return ;
	}
	/**
	 * 功能描述：保存或同步更新客服信息
	 * @param json
	 */
	private void saveOrUpdate(JSONObject json){
		String kf_account = json.getString("kf_account");
		try {
			if(null!=kf_account&&kf_account.trim().length()>0){
				KfAccount kfAccount = kfAccountManageImpl.findUniqueBy("kfAccount", kf_account);
				if(null!=kfAccount){
					kfAccount.setKfAccount(kf_account);
					String kf_headimgurl = json.getString("kf_headimgurl");
					kfAccount.setKfHeadimgurl(kf_headimgurl);;
					String kf_wx = json.getString("kf_wx");
					kfAccount.setKfWx(kf_wx);
					String kf_nick = json.getString("kf_nick");
					kfAccount.setKfNick(kf_nick);
					String invite_status=json.getString("invite_status");
					kfAccount.setInviteStatus(invite_status);
					String kf_id=json.getString("kf_id");
					kfAccount.setKfId(kf_id);
					kfAccountManageImpl.save(kfAccount);
				}else{
					KfAccount kfAccount2=new KfAccount();
					kfAccount2.setKfAccount(kf_account);
					String kf_headimgurl = json.getString("kf_headimgurl");
					kfAccount2.setKfHeadimgurl(kf_headimgurl);;
					String kf_wx = json.getString("kf_wx");
					kfAccount2.setKfWx(kf_wx);
					String kf_nick = json.getString("kf_nick");
					kfAccount2.setKfNick(kf_nick);
					String kf_id=json.getString("kf_id");
					kfAccount2.setKfId(kf_id);
					kfAccount2.setInviteStatus("waiting");
					kfAccountManageImpl.save(kfAccount2);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
			KfAccount kfAccount2 = kfAccountManageImpl.get(dbid);
			request.setAttribute("kfAccount", kfAccount2);
			
			User currentUser = SecurityUserHolder.getCurrentUser();
			 WeixinAccount weixinAccount = weixinAccountManageImpl.findUniqueBy("userDbid", currentUser.getDbid());
			 request.setAttribute("weixinAccount", weixinAccount);
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
			 boolean kfAccountAdd=false;
			 User currentUser = SecurityUserHolder.getCurrentUser();
			 WeixinAccount weixinAccount = weixinAccountManageImpl.findUniqueBy("userDbid", currentUser.getDbid());
			 String kfAccount2 = kfAccount.getKfAccount();
			 kfAccount.setKfAccount(kfAccount2+"@"+weixinAccount.getAccountnumber());
			 kfAccount.setKfId(kfAccount2);
			 Integer dbid = kfAccount.getDbid();
			 //密码md5加密
			 if(null==dbid){
				 kfAccount.setInviteStatus("waiting");
				 kfAccountManageImpl.save(kfAccount);
				 //更新数据到微信公众平台
				 kfAccountAdd = kfAccountAdd(kfAccount);
				 uploadImage(kfAccount);
			 }else{
				 KfAccount kfAccount3 = kfAccountManageImpl.get(dbid);
				 kfAccount3.setKfHeadimgurl(kfAccount.getKfHeadimgurl());
				 kfAccount3.setKfNick(kfAccount.getKfNick());
				 kfAccountManageImpl.save(kfAccount3);
				 //更新客服资料到微信公众平台
				 kfAccountAdd = kfAccountUpdate(kfAccount3);
				 uploadImage(kfAccount3);
			 }
			 if(kfAccountAdd==true){
				 renderMsg("/kfAccount/queryList", "保存数据成功！");
			 }else{
				 renderMsg("/kfAccount/queryList", "保存数据成功,同步至微信公众平台发生错误！");
			 }
			 
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		
		return ;
	}

	/**
	 * 功能描述： 
	 * 参数描述：
	 * 逻辑描述：
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
					KfAccount kfAccount2 = kfAccountManageImpl.get(dbid);
					kfAccountManageImpl.deleteById(dbid);
					boolean kfAccountDelete = kfAccountDelete(kfAccount2);
					 if(kfAccountDelete==true){
						 renderMsg("/kfAccount/queryList", "删除数据成功！");
						 return ;
					 }else{
						 renderMsg("/kfAccount/queryList", "删除数据成功,同步至微信公众平台发生错误！");
						 return ;
					 }
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
		renderMsg("/kfAccount/queryList"+query, "删除数据成功！");
		return;
	}
	/**
	 * 功能描述：同步客户信息至微信公众平台
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public boolean kfAccountAdd(KfAccount kfAccount) throws Exception {
		try {
			JSONObject fromObject = JSONObject.fromObject(kfAccount);
			String targetJson = fromObject.toString().replace("kfAccount", "kf_account");
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			if(null==weixinAccount){
				User user = SecurityUserHolder.getCurrentUser();
				weixinAccount = weixinAccountManageImpl.findUniqueBy("username", user.getUserId());
				if (weixinAccount != null) {
				} else {
					weixinAccount = new WeixinAccount();
					// 返回个临时对象，防止空指针
					weixinAccount.setWeixinAccountid("-1");
					weixinAccount.setDbid(-1);
				}
			}
			WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			String kfaccountAdd = WeixinUtil.KFACCOUNTADD.replace("ACCESS_TOKEN", accessToken.getAccessToken());
			JSONObject jsonObject = WeixinUtil.httpRequest(kfaccountAdd,"POST", targetJson);
			System.err.println("======"+jsonObject);
			if(null!=jsonObject){
				String errcode = jsonObject.getString("errcode");
				if(errcode.equals("0")){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return false;
	}
	/**
	 * 功能描述：同步客户信息至微信公众平台
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public boolean kfAccountUpdate(KfAccount kfAccount) throws Exception {
		try {
			JSONObject fromObject = new JSONObject();
			if(null==kfAccount){
				return false;
			}
			fromObject.put("kf_account", kfAccount.getKfAccount());
			fromObject.put("nickname", kfAccount.getKfNick());
			String targetJson = fromObject.toString().replace("kfAccount", "kf_account");
			System.out.println(targetJson);
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			if(null==weixinAccount){
				User user = SecurityUserHolder.getCurrentUser();
				weixinAccount = weixinAccountManageImpl.findUniqueBy("username", user.getUserId());
				if (weixinAccount != null) {
				} else {
					weixinAccount = new WeixinAccount();
					// 返回个临时对象，防止空指针
					weixinAccount.setWeixinAccountid("-1");
					weixinAccount.setDbid(-1);
				}
			}
			WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			String kfaccountAdd = WeixinUtil.KFACCOUNTUPDATE.replace("ACCESS_TOKEN", accessToken.getAccessToken());
			JSONObject jsonObject = WeixinUtil.httpRequest(kfaccountAdd,"POST", targetJson);
			System.err.println("========="+jsonObject.toString());
			if(null!=jsonObject){
				String errcode = jsonObject.getString("errcode");
				if(errcode.equals("0")){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return false;
	}
	/**
	 * 功能描述：删除客户信息至微信公众平台
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public boolean kfAccountDelete(KfAccount kfAccount) throws Exception {
		try {
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			if(null==weixinAccount){
				User user = SecurityUserHolder.getCurrentUser();
				weixinAccount = weixinAccountManageImpl.findUniqueBy("username", user.getUserId());
				if (weixinAccount != null) {
				} else {
					weixinAccount = new WeixinAccount();
					// 返回个临时对象，防止空指针
					weixinAccount.setWeixinAccountid("-1");
					weixinAccount.setDbid(-1);
				}
			}
			WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			String kfaccountDelete = WeixinUtil.KFACCOUNTDELETE.replace("ACCESS_TOKEN", accessToken.getAccessToken()).replace("KFACCOUNT", kfAccount.getKfAccount());
			JSONObject jsonObject = WeixinUtil.httpRequest(kfaccountDelete,"GET", null);
			if(null!=jsonObject){
				String errcode = jsonObject.getString("errcode");
				if(errcode.equals("0")){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return false;
	}
	/**
	 * 功能描述：上传客服头像
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void uploadImage(KfAccount kfAccount) throws Exception {
		try {
			 if(null!=kfAccount.getKfHeadimgurl()&&kfAccount.getKfHeadimgurl().trim().length()>0){
					User user = SecurityUserHolder.getCurrentUser();
				 WeixinAccount weixinAccount = weixinAccountManageImpl.findUniqueBy("userDbid", user.getDbid());
				 WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
				 String headImg = kfAccount.getKfHeadimgurl();
				 String webRootPath = PathUtil.getWebRootPath();
				 File fileMedia=new File(webRootPath+headImg);
				 JSONObject kfAccountUploadImage = WeixinUploadMidea.kfAccountUploadImage(accessToken.getAccessToken(), fileMedia, kfAccount.getKfAccount());
				 System.out.println("============="+kfAccountUploadImage.toString());
			 }
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return;
	}
	/**
	 * 验证客户是否存在系统
	 */
	public void validateKfAccount() {
		HttpServletRequest request = this.getRequest();
		try {
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			String kfAccount =request.getParameter("kfAccount.kfAccount");
			List<KfAccount> kfAccounts=null;
			if(null!=kfAccount&&kfAccount.trim().length()>0){
				kfAccount=kfAccount+"@"+weixinAccount.getAccountnumber();
				kfAccounts = kfAccountManageImpl.findBy("kfAccount", kfAccount);
			}else{
				renderText("系统已经存在该工号了!请换一个工号!");//输入的账户类型不匹配！
				return ;
			}
			
			if (null!=kfAccounts&&kfAccounts.size()>0) {
				renderText("系统已经存在该工号了!请换一个工号!");//已经注册，请直接登录！
			}else{
				renderText("success");//
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private String inviteworkerWx(KfAccount kfAccount){
		String messg="0";
		try {
				if(null==kfAccount){
					messg="客户信息为空";
				}
				User user = SecurityUserHolder.getCurrentUser();
				WeixinAccount weixinAccount = weixinAccountManageImpl.findUniqueBy("userDbid", user.getDbid());
				WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
				String inviteworderUrl = WeixinUtil.INVITEWORKER.replace("ACCESS_TOKEN", accessToken.getAccessToken());
				JSONObject object=new JSONObject();
				object.put("kf_account", kfAccount.getKfAccount());
				object.put("invite_wx", kfAccount.getInviteWx());
				JSONObject jsonObject = WeixinUtil.httpRequest(inviteworderUrl, "POST", object.toString());
				if(null!=jsonObject){
					ErrorMessage errorMessage = ErrorMessageUtil.paraseErrorMessage(jsonObject);
					if(null!=errorMessage){
						String errcode = errorMessage.getErrcode();
						if(errcode.equals("65400")){
							messg="API不可用，即没有开通/升级到新版客服";
						}
						if(errcode.equals("65401")){
							messg=kfAccount.getKfAccount()+"无效客服帐号";
						}
						if(errcode.equals("65407")){
							messg=kfAccount.getKfWx()+"邀请对象已经是本公众号客服";
						}
						if(errcode.equals("65408")){
							messg="本公众号已发送邀请给该微信号";
						}
						if(errcode.equals("65409")){
							messg="无效的微信号";
						}
						if(errcode.equals("65410")){
							messg="邀请对象绑定公众号客服数量达到上限";
						}
						if(errcode.equals("65411")){
							messg="该帐号已经有一个等待确认的邀请，不能重复邀请";
						}
						if(errcode.equals("65412")){
							messg="该帐号已经绑定微信号，不能进行邀请";
						}
					}else{
						messg="邀请绑定客服帐号 INVITEWORKER 接口发生错误。";
					}
				}else{
					messg="邀请绑定客服帐号 INVITEWORKER 接口发生错误。";
				}
		} catch (Exception e) {
			messg="邀请绑定客服帐号 发生错误";
		}
		return messg;
	}
}

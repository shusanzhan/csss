package com.ystech.weixin.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.Page;
import com.ystech.core.security.SecurityUserHolder;
import com.ystech.core.util.DatabaseUnitHelper;
import com.ystech.core.util.LogUtil;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.weixin.core.util.Configure;
import com.ystech.weixin.core.util.WeixinUtil;
import com.ystech.weixin.model.WeixinAccesstoken;
import com.ystech.weixin.model.WeixinAccount;
import com.ystech.weixin.model.WeixinGroup;
import com.ystech.weixin.model.WeixinGzuserinfo;
import com.ystech.weixin.service.WeixinAccesstokenManageImpl;
import com.ystech.weixin.service.WeixinAccountManageImpl;
import com.ystech.weixin.service.WeixinGroupManageImpl;
import com.ystech.weixin.service.WeixinGzuserinfoManageImpl;

@Component("weixinGzUserInfoAction")
@Scope("prototype")
public class WeixinGzUserInfoAction extends BaseController{
	private WeixinGzuserinfoManageImpl weixinGzuserinfoManageImpl;
	private WeixinAccountManageImpl weixinAccountManageImpl;
	private WeixinGzuserinfo weixinGzuserinfo;
	private WeixinGroupManageImpl weixinGroupManageImpl;
	private WeixinAccesstokenManageImpl weixinAccesstokenManageImpl;
	private boolean status=true;
	public WeixinGzuserinfo getWeixinGzuserinfo() {
		return weixinGzuserinfo;
	}
	public void setWeixinGzuserinfo(WeixinGzuserinfo weixinGzuserinfo) {
		this.weixinGzuserinfo = weixinGzuserinfo;
	}
	@Resource
	public void setWeixinGzuserinfoManageImpl(
			WeixinGzuserinfoManageImpl weixinGzuserinfoManageImpl) {
		this.weixinGzuserinfoManageImpl = weixinGzuserinfoManageImpl;
	}
	@Resource
	public void setWeixinAccountManageImpl(
			WeixinAccountManageImpl weixinAccountManageImpl) {
		this.weixinAccountManageImpl = weixinAccountManageImpl;
	}
	@Resource
	public void setWeixinGroupManageImpl(WeixinGroupManageImpl weixinGroupManageImpl) {
		this.weixinGroupManageImpl = weixinGroupManageImpl;
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
		Integer eventStatus = ParamUtil.getIntParam(request, "eventStatus", -1);
		Integer groupId = ParamUtil.getIntParam(request, "groupId", -1);
		try{
			 WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			 List params=new ArrayList();
			 String sql="select * from Weixin_Gzuserinfo where 1=1 ";
			 sql=sql+" and accountid=?";
			 params.add(weixinAccount.getDbid());
			if(null!=eventStatus&&eventStatus>0){
				sql=sql+" and eventStatus=?";
				 params.add(eventStatus);
			}
			if(null!=groupId&&groupId>0){
				sql=sql+" and groupId=?";
				params.add(groupId);
			}
			Page<WeixinGzuserinfo> page= weixinGzuserinfoManageImpl.pagedQuerySql(pageNo, pageSize,WeixinGzuserinfo.class,sql, params.toArray());
			request.setAttribute("page", page);
			
			List<WeixinGroup> totalWeixinGroups = weixinGroupManageImpl.getTotalWeixinGroups();
			request.setAttribute("totalWeixinGroups", totalWeixinGroups);
			
			List<WeixinGroup> weixinGroups = weixinGroupManageImpl.findBy("accountId", weixinAccount.getDbid());
			request.setAttribute("weixinGroups", weixinGroups);
		}catch(Exception e){
			
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
	public String view() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			WeixinGzuserinfo weixinGzuserinfo2 = weixinGzuserinfoManageImpl.get(dbid);
			request.setAttribute("weixinGzUserInfo", weixinGzuserinfo2);
		}
		return "view";
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
			WeixinGzuserinfo weixinGzuserinfo2 = weixinGzuserinfoManageImpl.get(dbid);
			request.setAttribute("weixinGzuserinfo", weixinGzuserinfo2);
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
		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", 5);
		try{
			/*Integer dbid = slide.getDbid();
			if(dbid==null||dbid<=0){
				slide.setCreateTime(new Date());
				slide.setModifyTime(new Date());
				slideManageImpl.save(slide);
			}else{
				slideManageImpl.save(slide);
			}*/
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/slide/queryList?parentMenu="+parentMenu, "保存数据成功！");
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
		Integer parentMenu = ParamUtil.getIntParam(request, "parentMenu", 5);
		if(null!=dbids&&dbids.length>0){
			try {
				for (Integer dbid : dbids) {
					weixinGzuserinfoManageImpl.deleteById(dbid);
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
		renderMsg("/weixinGzuserinfo/queryList"+query+"&parentMenu="+parentMenu, "删除数据成功！");
		return;
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void updateGroup() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer groupid = ParamUtil.getIntParam(request, "groupId", -1);
		Integer userInfoId = ParamUtil.getIntParam(request, "userInfoId", -1);
		try {
				WeixinGzuserinfo weixinGzuserinfo2 = weixinGzuserinfoManageImpl.get(userInfoId);
				if(null==weixinGzuserinfo2){
					 renderErrorMsg(new Throwable("同步组错误，关注用户信息为空！"), "");
					 return ;
				}
				WeixinGroup weixinGroup = weixinGroupManageImpl.get(groupid);
				if(null==weixinGroup){
					 renderErrorMsg(new Throwable("同步组错误，用户组信息为空！"), "");
					 return ;
				}
				weixinGzuserinfo2.setGroupId(weixinGroup.getWechatGroupId());
				weixinGzuserinfoManageImpl.save(weixinGzuserinfo2);
				
				WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
				WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
				 String createApi = Configure.GROUPS_MEMEBERS_API.replace("ACCESS_TOKEN", accessToken.getAccessToken());
				 String outputStr="{\"openid\":\""+weixinGzuserinfo2.getOpenid()+"\",\"to_groupid\":"+weixinGroup.getWechatGroupId()+"}}";
				 JSONObject jsonObject = WeixinUtil.httpRequest(createApi, "POST", outputStr);
				 if(null!=jsonObject){
					 String result = jsonObject.toString();
					 if(result.contains("ok")){
						 renderErrorMsg(new Throwable("更新数据成功!"), "");
						 return ;
					 }else{
						 renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
						 return ;
					 }
				 }else{
					 renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
					 return;
				 } 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			renderErrorMsg(new Throwable("同步组错误，系统抛出异常"), "");
		}
		return;
	}
	/**
	 * 功能描述：批量移动微信用户到用户组
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void updateGroupBatch() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer groupid = ParamUtil.getIntParam(request, "groupId", -1);
		Integer[] userInfoIds = ParamUtil.getIntArrayByIds(request, "userInfoIds");
		try {
			if(null==userInfoIds&&userInfoIds.length<=0){
				 renderErrorMsg(new Throwable("同步组错误，关注用户信息为空！"), "");
				 return ;
			}
			if(userInfoIds.length>50){
				renderErrorMsg(new Throwable("同步组错误，粉丝数量不能超过50！"), "");
				 return ;
			}
			WeixinGroup weixinGroup = weixinGroupManageImpl.get(groupid);
			if(null==weixinGroup){
				 renderErrorMsg(new Throwable("同步组错误，用户组信息为空！"), "");
				 return ;
			}
			StringBuffer openid_list=new StringBuffer();
			for (Integer dbid : userInfoIds) {
				WeixinGzuserinfo weixinGzuserinfo2 = weixinGzuserinfoManageImpl.get(dbid);
				weixinGzuserinfo2.setGroupId(weixinGroup.getWechatGroupId());
				weixinGzuserinfoManageImpl.save(weixinGzuserinfo2);
				openid_list.append("\""+weixinGzuserinfo2.getOpenid()+"\",");
			}
			String openid_list_string = openid_list.toString();
			if(openid_list_string.length()<=0){
				 renderErrorMsg(new Throwable("同步组错误，关注用户信息为空！"), "");
				 return ;
			}
			openid_list_string=openid_list_string.substring(0, openid_list_string.length()-1);
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			 String createApi = Configure.GROUPS_MEMEBERS_BATCH_API.replace("ACCESS_TOKEN", accessToken.getAccessToken());
			 String outputStr="{\"openid_list\":["+openid_list_string+"],\"to_groupid\":"+weixinGroup.getWechatGroupId()+"}";
			 JSONObject jsonObject = WeixinUtil.httpRequest(createApi, "POST", outputStr);
			 if(null!=jsonObject){
				 String result = jsonObject.toString();
				 if(result.contains("ok")){
					 renderErrorMsg(new Throwable("更新数据成功!"), "");
					 return ;
				 }else{
					 renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
					 return ;
				 }
			 }else{
				 renderErrorMsg(new Throwable("同步组错误，远程微信服务端返回结果为空！"), "");
				 return;
			 } 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			renderErrorMsg(new Throwable("同步组错误，系统抛出异常"), "");
		}
		return;
	}
	
	/**
	 * 功能描述：同步关注用户
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void sysBathGzuserinfo() throws Exception {
		try {
			Map<String, String> maps = getOpenIdMap();
			WeixinAccount weixinAccount = SecurityUserHolder.getWeixinAccount();
			WeixinAccesstoken accessToken = WeixinUtil.getAccessToken(weixinAccesstokenManageImpl, weixinAccount.getAccountappid(), weixinAccount.getAccountappsecret());
			int i=0;
			
			sysGzuserInfo(maps, weixinAccount, accessToken,"",i,status);
			if(status==true){
				renderMsg("", "同步数据成功！");
				return ;
			}else{
				renderErrorMsg(new Throwable("同步数据失败"), "");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			renderErrorMsg(e, "");
			return ;
		}
	}
	private void sysGzuserInfo(Map<String, String> maps,WeixinAccount weixinAccount, WeixinAccesstoken accessToken,String nextOpenId,int i,Boolean status) {
		i=i+1;
		String usergets="";
		if(null!=nextOpenId&&nextOpenId.trim().length()>0){
			usergets= WeixinUtil.usergets.replace("ACCESS_TOKEN",accessToken.getAccessToken()).replace("NEXT_OPENID",nextOpenId);
		}else{
			usergets= WeixinUtil.usergets.replace("ACCESS_TOKEN",accessToken.getAccessToken()).replace("NEXT_OPENID", "");
		}
		JSONObject object = WeixinUtil.httpRequest(usergets, "GET", "");
		if(null!=object){
			if(object.toString().contains("invalid")){
				LogUtil.error(object.toString());
				status=false;
				return ;
			}else{
				String total = object.getString("total");
				String count = object.getString("count");
				String openids = object.getJSONObject("data").getString("openid");
				if(null!=openids){
					openids=openids.replace("[", "").replace("]", "");
					String[] openIds = openids.split(",");
					for (String openId : openIds) {
						String opId = openId.replace("\"", "");
						String map = maps.get(opId);
						if(null==map||map.trim().length()<=0){
							weixinGzuserinfoManageImpl.saveGzuserinfo(opId, accessToken.getAccessToken(),weixinAccount.getDbid());
						}
					}
				}
				LogUtil.error("第"+i+"拉取关注用户，总用户："+total+",拉取格式："+count+",nextOpenId "+nextOpenId);
				i=i+1;
				String next_openid = object.getString("next_openid");
				if(null!=next_openid&&next_openid.trim().length()>0&&count.equals("10000")){
					sysGzuserInfo(maps, weixinAccount, accessToken, nextOpenId,i,status);
				}else{
					return ;
				}
			}
		}
	}
	/**
	 * 功能描述：群发给所用人员时，获取所有人员的OPenId
	 * @return
	 */
	public Map<String, String> getOpenIdMap(){
		Map<String, String> maps=new HashMap<String, String>();
		try {
			String sql="select openId from weixin_gzuserinfo where eventStatus=1";
			DatabaseUnitHelper databaseUnitHelper = new DatabaseUnitHelper();
			Connection jdbcConnection = databaseUnitHelper.getJdbcConnection();
			PreparedStatement createStatement = jdbcConnection.prepareStatement(sql);
			ResultSet resultSet = createStatement.executeQuery();
			if(null==resultSet){
				return null;
			}
			while (resultSet.next()) {
				String openId=resultSet.getString("openid");
				maps.put(openId, openId);
			}
			resultSet.close();
			createStatement.close();
			jdbcConnection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}
}

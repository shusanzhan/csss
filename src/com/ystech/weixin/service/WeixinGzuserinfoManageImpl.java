package com.ystech.weixin.service;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.core.util.LogUtil;
import com.ystech.weixin.core.util.WeixinUtil;
import com.ystech.weixin.model.WeixinGzuserinfo;

@Component("weixinGzuserinfoManageImpl")
public class WeixinGzuserinfoManageImpl extends HibernateEntityDao<WeixinGzuserinfo>{
	//点击事件获取微信用户信息
	public WeixinGzuserinfo saveWeixinGzuserinfo(String openId,String accessToken,Integer accountId){
		WeixinGzuserinfo weixinGzuserinfo = findUniqueBy("openid", openId);
		if(null==weixinGzuserinfo){ 
			String requestUrl = WeixinUtil.userMsgInfo_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,"GET", null);
			System.out.println("================================"+jsonObject.toString());
			if (null != jsonObject) {
				if(jsonObject.toString().contains("invalid")){
					return null;
				}
				try {
					weixinGzuserinfo=new WeixinGzuserinfo();
					String subscribe = jsonObject.getString("subscribe");
					if(null!=subscribe)
						weixinGzuserinfo.setSubscribe(subscribe);
					String openid = jsonObject.getString("openid");
					if(null!=openId)
						weixinGzuserinfo.setOpenid(openid);
					String nickname = jsonObject.getString("nickname");
					if(null!=nickname){
						String filterEmoji = filterEmoji(nickname);
						weixinGzuserinfo.setNickname(filterEmoji);
					}
					String sex = jsonObject.getString("sex");
					if(null!=sex)
						weixinGzuserinfo.setSex(sex);
					String language = jsonObject.getString("language");
					if(null!=language)
						weixinGzuserinfo.setLanguage(language);
					String city = jsonObject.getString("city");
					if(null!=city)
						weixinGzuserinfo.setCity(city);
					String province = jsonObject.getString("province");
					if(null!=province)
						weixinGzuserinfo.setProvince(province);
					String country = jsonObject.getString("country");
					if(null!=country)
						weixinGzuserinfo.setCountry(country);
					String headimgurl = jsonObject.getString("headimgurl");
					if(null!=headimgurl)
						weixinGzuserinfo.setHeadimgurl(headimgurl);
					String subscribe_time = jsonObject.getString("subscribe_time");
					if(null!=subscribe_time)
						weixinGzuserinfo.setSubscribeTime(subscribe_time);
					/*String unionid = jsonObject.getString("unionid");
					if(null!=unionid)
						weixinGzuserinfo.setUnionid(unionid);*/
					String remark = jsonObject.getString("remark");
					if(null!=remark)
						weixinGzuserinfo.setRemark(remark);
					String groupid = jsonObject.getString("groupid");
					if(null!=groupid)
						weixinGzuserinfo.setGroupId(groupid);
					weixinGzuserinfo.setEventStatus(1);
					weixinGzuserinfo.setAddtime(new Date());
					weixinGzuserinfo.setAccountid(accountId);
					save(weixinGzuserinfo);
					//删除重复数据
					deleteDub(openid);
				} catch (Exception e) {
					e.printStackTrace();
					weixinGzuserinfo = null;
					// 获取token失败
					String wrongMessage = "获取weixinGzuserinfo失败 errcode:{} errmsg:{}"
							+ jsonObject.getString("errcode")
							+ jsonObject.getString("errmsg");
					System.out.println(""+wrongMessage);
				}
			}
		}
		return weixinGzuserinfo;
	}
	//批量同步数据
	public void saveGzuserinfo(String openId,String accessToken,Integer accountId){
		try {
			System.out.println("openId:"+openId+",accessToken:"+accessToken);
			String requestUrl = WeixinUtil.userMsgInfo_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,"GET", null);
			System.out.println("================================"+jsonObject.toString());
			if (null != jsonObject) {
				if(jsonObject.toString().contains("invalid")){
					String wrongMessage = "获取weixinGzuserinfo失败 errcode:{} errmsg:{}"
							+ jsonObject.getString("errcode")
							+ jsonObject.getString("errmsg");
					LogUtil.error(wrongMessage);
					return ;
				}
				
				WeixinGzuserinfo weixinGzuserinfo=new WeixinGzuserinfo();
				String subscribe = jsonObject.getString("subscribe");
				if(null!=subscribe)
					weixinGzuserinfo.setSubscribe(subscribe);
				String openid = jsonObject.getString("openid");
				if(null!=openId)
					weixinGzuserinfo.setOpenid(openid);
				String nickname = jsonObject.getString("nickname");
				if(null!=nickname){
					String filterEmoji = filterEmoji(nickname);
					weixinGzuserinfo.setNickname(filterEmoji);
				}
				String sex = jsonObject.getString("sex");
				if(null!=sex)
					weixinGzuserinfo.setSex(sex);
				String language = jsonObject.getString("language");
				if(null!=language)
					weixinGzuserinfo.setLanguage(language);
				String city = jsonObject.getString("city");
				if(null!=city)
					weixinGzuserinfo.setCity(city);
				String province = jsonObject.getString("province");
				if(null!=province)
					weixinGzuserinfo.setProvince(province);
				String country = jsonObject.getString("country");
				if(null!=country)
					weixinGzuserinfo.setCountry(country);
				String headimgurl = jsonObject.getString("headimgurl");
				if(null!=headimgurl)
					weixinGzuserinfo.setHeadimgurl(headimgurl);
				String subscribe_time = jsonObject.getString("subscribe_time");
				if(null!=subscribe_time)
					weixinGzuserinfo.setSubscribeTime(subscribe_time);
				/*String unionid = jsonObject.getString("unionid");
				if(null!=unionid)
					weixinGzuserinfo.setUnionid(unionid);*/
				String remark = jsonObject.getString("remark");
				if(null!=remark)
					weixinGzuserinfo.setRemark(remark);
				String groupid = jsonObject.getString("groupid");
				if(null!=groupid)
					weixinGzuserinfo.setGroupId(groupid);
				weixinGzuserinfo.setEventStatus(1);
				weixinGzuserinfo.setAddtime(new Date());
				weixinGzuserinfo.setAccountid(accountId);
				save(weixinGzuserinfo);
				//删除重复数据
				deleteDub(openid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 获取token失败
		}
	}
	//取消关注更新数据
	public void updateByOpenId(String openId){
		WeixinGzuserinfo weixinGzuserinfo = findUniqueBy("openid", openId);
		if(null!=weixinGzuserinfo){
			weixinGzuserinfo.setEventStatus(2);
			weixinGzuserinfo.setCancelDate(new Date());
			save(weixinGzuserinfo);
		}
	}
	  /** 
     * 将emoji表情替换成* 
     *  
     * @param source 
     * @return 过滤后的字符串 
     */  
    public static String filterEmoji(String source) {  
        if(StringUtils.isNotBlank(source)){  
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");  
        }else{  
            return source;  
        }  
    }  
	/**
	 * 删除重复数据
	 * @param openId
	 */
	public void deleteDub(String openId){
		List<WeixinGzuserinfo> weixinGzuserinfos = findBy("openid", openId);
		if(null!=weixinGzuserinfos&&weixinGzuserinfos.size()>1){
			int j=weixinGzuserinfos.size();
			for (int i = 1; i < j; i++) {
				WeixinGzuserinfo weixinGzuserinfo = weixinGzuserinfos.get(i);
				delete(weixinGzuserinfo);
			}
		}
	}
}

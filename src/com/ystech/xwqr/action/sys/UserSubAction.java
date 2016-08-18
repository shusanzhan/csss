/**
 * 
 */
package com.ystech.xwqr.action.sys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.Page;
import com.ystech.core.security.SecurityUserHolder;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.xwqr.model.sys.User;
import com.ystech.xwqr.model.sys.UserSub;
import com.ystech.xwqr.service.sys.UserManageImpl;
import com.ystech.xwqr.service.sys.UserSubManageImpl;

/**
 * @author shusanzhan
 * @date 2014-2-27
 */
@Component("userSubAction")
@Scope("prototype")
public class UserSubAction extends BaseController{
	private UserManageImpl userManageImpl;
	private UserSubManageImpl userSubManageImpl;
	@Resource
	public void setUserManageImpl(UserManageImpl userManageImpl) {
		this.userManageImpl = userManageImpl;
	}
	@Resource
	public void setUserSubManageImpl(UserSubManageImpl userSubManageImpl) {
		this.userSubManageImpl = userSubManageImpl;
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
		User currentUser = SecurityUserHolder.getCurrentUser();
		try{
			Page<UserSub> page= userSubManageImpl.pagedQuerySql(pageNo, pageSize,UserSub.class, "select * from sys_UserSub where userId=?", new Object[]{currentUser.getDbid()});
			request.setAttribute("page", page);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
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
	public String edit() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			UserSub userSub = userSubManageImpl.get(dbid);
			request.setAttribute("slide", userSub);
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
			User currentUser = SecurityUserHolder.getCurrentUser();
			Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
			if(null!=dbids&&dbids.length>0){
				for (Integer dbid : dbids) {
					User user = userManageImpl.get(dbid);
					UserSub userSub=new UserSub();
					userSub.setUserId(currentUser.getDbid());
					userSub.setUser(user);
					userSubManageImpl.save(userSub);
				}
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/userSub/queryList", "保存数据成功！");
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
					userSubManageImpl.deleteById(dbid);
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
		renderMsg("/userSub/queryList"+query, "删除数据成功！");
		return;
	}

}

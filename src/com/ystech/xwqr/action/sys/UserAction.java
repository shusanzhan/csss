package com.ystech.xwqr.action.sys;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.Page;
import com.ystech.core.security.SecurityUserHolder;
import com.ystech.core.security.service.UserDetailsManageImpl;
import com.ystech.core.util.Md5;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.xwqr.model.sys.Department;
import com.ystech.xwqr.model.sys.Role;
import com.ystech.xwqr.model.sys.Staff;
import com.ystech.xwqr.model.sys.User;
import com.ystech.xwqr.service.sys.DepartmentManageImpl;
import com.ystech.xwqr.service.sys.RoleManageImpl;
import com.ystech.xwqr.service.sys.StaffManageImpl;
import com.ystech.xwqr.service.sys.UserManageImpl;
@Component("userAction")
@Scope("prototype")
public class UserAction extends BaseController{
	private User user;
	private Staff staff;
	private UserManageImpl userManageImpl;
	private RoleManageImpl roleManageImpl;
	private StaffManageImpl staffManageImpl;
	private UserDetailsManageImpl userDetailsManageImpl;
	private DepartmentManageImpl departmentManageImpl;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Resource
	public void setUserManageImpl(UserManageImpl userManageImpl) {
		this.userManageImpl = userManageImpl;
	}
	@Resource
	public void setRoleManageImpl(RoleManageImpl roleManageImpl) {
		this.roleManageImpl = roleManageImpl;
	}
	@Resource
	public void setDepartmentManageImpl(DepartmentManageImpl departmentManageImpl) {
		this.departmentManageImpl = departmentManageImpl;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	@Resource
	public void setStaffManageImpl(StaffManageImpl staffManageImpl) {
		this.staffManageImpl = staffManageImpl;
	}
	@Resource
	public void setUserDetailsManageImpl(UserDetailsManageImpl userDetailsManageImpl) {
		this.userDetailsManageImpl = userDetailsManageImpl;
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		return "add";
	}
	/**
	 * 功能描述：用户管理列表
	 * @return
	 * @throws Exception
	 */
	public String queryList() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer pageSize = ParamUtil.getIntParam(request, "pageSize", 10);
		Integer pageNo = ParamUtil.getIntParam(request, "currentPage", 1);
		String userId = request.getParameter("userName");
		Page<User> page=null;
		if(null!=userId&&userId.trim().length()>0){
			page = userManageImpl.pagedQuerySql(pageNo, pageSize,User.class,"select * from sys_User where userId like '%"+userId+"%'", new Object[]{});
		}else{
			page = userManageImpl.pagedQuerySql(pageNo, pageSize,User.class,"select * from sys_User", new Object[]{});
		}
		List<User> result = page.getResult();
		for (User user : result) {
			System.out.println(user.getEmail());
		}
		request.setAttribute("page", page);
		return "list";
	}
	/**
	 * 功能描述：保存用户信息
	 * @throws Exception
	 */
	public void save() throws Exception {
		HttpServletRequest request = this.getRequest();
		String[] departmentIds = request.getParameterValues("departmentIds");
		if(null!=departmentIds&&departmentIds.length>0){
			if(departmentIds.length>1){
				renderErrorMsg(new Throwable("只能选择一个部门，请选择部门"), "");
				return ;
			}
		}else{
			renderErrorMsg(new Throwable("请选择部门信息"), "");
			return ;
		}
		
		try{
			//保存用户信息
			String calcMD5 = Md5.calcMD5("123456{"+user.getUserId()+"}");
			Integer dbid = user.getDbid();
			String depIs = departmentIds[0];
			if(null!=depIs){
				Department department = departmentManageImpl.get(Integer.parseInt(depIs));
				user.setDepartment(department);
			}
			
			if(null!=dbid&&dbid>0){
				User user2 = userManageImpl.get(dbid);
				user2.setEmail(user.getEmail());
				user2.setMobilePhone(user.getMobilePhone());
				user2.setPhone(user.getPhone());
				user2.setRealName(user.getRealName());
				user2.setUserId(user.getUserId());
				user2.setDepartment(user.getDepartment());
				user2.setQq(user.getQq());
				userManageImpl.save(user2);
				
				Integer dbid2 = staff.getDbid();
				if(null!=dbid2&&dbid2>0){
					Staff staff2 = staffManageImpl.get(staff.getDbid());
					staff2.setUser(user2);
					staff2.setName(user2.getRealName());
					staff2.setEducationalBackground(staff.getEducationalBackground());
					staff2.setBirthday(staff.getBirthday());
					staff2.setGraduationSchool(staff.getGraduationSchool());
					staff2.setPhoto(staff.getPhoto());
					staff2.setSex(staff.getSex());
					staff2.setFamilyAddress(staff.getFamilyAddress());
					staff2.setNowAddress(staff.getNowAddress());
					staffManageImpl.save(staff2);
				}else{
					//保存饲养员信息
					staff.setUser(user);
					staff.setName(user.getRealName());
					staffManageImpl.save(staff);
				}
			}else{
				user.setPassword(calcMD5);
				userManageImpl.save(user);
				//保存饲养员信息
				staff.setUser(user);
				user.setUserState(1);
				staff.setName(user.getRealName());
				staffManageImpl.save(staff);
			}
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return; 
		}
		renderMsg("/user/queryList", "保存数据成功！");
		return ;
	}
	/**
	 * 功能描述：编辑用户信息
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			User user = userManageImpl.get(dbid);
			request.setAttribute("user", user);
			request.setAttribute("staff", user.getStaff());
		}
		return "edit";
	}
	/**
	 * 功能描述：删除用户信息
	 * @throws Exception
	 */
	public void delete() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer[] dbids = ParamUtil.getIntArraryByDbids(request,"dbids");
		if(null!=dbids&&dbids.length>0){
			try {
				for (Integer dbid : dbids) {
					User user = userManageImpl.get(dbid);
					if(user.getUserId().equals("admin")){
						renderErrorMsg(new Throwable("管理员不能删除！"), "");
						return ;
					}
					userManageImpl.deleteById(dbid);
				}
			} catch (Exception e) {
				e.printStackTrace();
				renderErrorMsg(e, "");
				return ;
			}
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/user/queryList"+query, "删除数据成功！");
		return;
	}
	/**
	 * 功能描述：个人设置-设置用户信息
	 * @return
	 * @throws Exception
	 */
	public String editSelf() throws Exception {
		User user = SecurityUserHolder.getCurrentUser();
		HttpServletRequest request = this.getRequest();
		if(null!=user&&user.getDbid()>0){
			User user2 = userManageImpl.get(user.getDbid());
			request.setAttribute("user", user2);
			request.setAttribute("staff", user2.getStaff());
		}
		return "editSelf";
	}
	/**
	 * 功能描述：保存用户信息
	 * @throws Exception
	 */
	public void saveEditSelf() throws Exception {
		try{
			//保存用户信息
			Integer dbid = user.getDbid();
			User user2 = userManageImpl.get(dbid);
			user2.setEmail(user.getEmail());
			user2.setMobilePhone(user.getMobilePhone());
			user2.setPhone(user.getPhone());
			user2.setRealName(user.getRealName());
			user2.setQq(user.getQq());
			user2.setUserId(user.getUserId());
			userManageImpl.save(user2);
			
			Staff staff2 = staffManageImpl.get(staff.getDbid());
			staff2.setUser(user2);
			staff2.setName(user2.getRealName());
			staff2.setEducationalBackground(staff.getEducationalBackground());
			staff2.setBirthday(staff.getBirthday());
			staff2.setGraduationSchool(staff.getGraduationSchool());
			staff2.setPhoto(staff.getPhoto());
			staff2.setSex(staff.getSex());
			staff2.setNowAddress(staff.getNowAddress());
			staff2.setFamilyAddress(staff.getFamilyAddress());
			staffManageImpl.save(staff2);
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		renderMsg("/user/editSelf", "保存数据成功！");
		return ;
	}
	/**
	 * 功能描述：修改密码
	 * @return
	 * @throws Exception
	 */
	public String modifyPassword() throws Exception {
		User user = SecurityUserHolder.getCurrentUser();
		HttpServletRequest request = this.getRequest();
		if(null!=user&&user.getDbid()>0){
			User user2 = userManageImpl.get(user.getDbid());
			request.setAttribute("user", user2);
		}
		return "modifyPassword";
	}
	/**
	 * 功能描述：修改密码
	 * @return
	 * @throws Exception
	 */
	public void updateModifyPassword() throws Exception {
		HttpServletRequest request = getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		String oldPassword = request.getParameter("oldPassword");
		String password = request.getParameter("password");
		if(null==oldPassword||oldPassword.trim().length()<=0){
			renderErrorMsg(new Throwable("输入旧密码错误！"), "");
			return ;
		}
		if(null==password||password.trim().length()<=0){
			renderErrorMsg(new Throwable("密码输入错误！"), "");
			return ;
		}
		try{
			if (dbid>0) {
				User user2 = userManageImpl.get(dbid);
				String password2 = user2.getPassword();
				String calcMD5 = Md5.calcMD5(oldPassword+"{"+user2.getUserId()+"}");
				if(password2.equals(calcMD5)){
					user2.setPassword(Md5.calcMD5(password+"{"+user2.getUserId()+"}"));
					userManageImpl.save(user2);
				}else{
					renderErrorMsg(new Throwable("旧密码输入错误！"), "");
					return ;	
				}
			} else {
				renderErrorMsg(new Throwable("操作数据错误！"), "");
				return ;
			}	
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(new Throwable("操作数据错误！"), "");
			return ;
		}
		renderMsg("/user/modifyPassword", "修改密码成功！");
		return ;
	}
	/**系统配置角色**/
	public String userRole() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			user=userManageImpl.get(dbid);
		}
		return "userRole";
	}
	/**
	 * 功能描述：启用或者停用用户
	 */
	public void stopOrStartUser() {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		try{
			if(dbid>0){
				User user2 = userManageImpl.get(dbid);
				Integer userState = user2.getUserState();
				if(null!=userState){
					if(userState==1){
						user2.setUserState(2);
					}else if(userState==2){
						user2.setUserState(1);
					}
				}
				userManageImpl.save(user2);
			}else{
				renderErrorMsg(new Throwable("请选择操作数据"), "");
				return ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			renderErrorMsg(e, "");
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/user/queryList"+query, "设置成功！");
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void resetPassword() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		try{
			if(dbid>0){
				User user2 = userManageImpl.get(dbid);
				String password = Md5.calcMD5("123456{"+user2.getUserId()+"}");
				user2.setPassword(password);
				userManageImpl.save(user2);
			}else{
				renderErrorMsg(new Throwable("请选择操作数据"), "");
				return ;
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			renderErrorMsg(e, "");
		}
		String query = ParamUtil.getQueryUrl(request);
		renderMsg("/user/queryList"+query, "设置成功！");
	}
	public void validateUser() {
		HttpServletRequest request = this.getRequest();
		String userId =null;
		userId=request.getParameter("user.userId");
		List<User> users=null;
		if(null!=userId&&userId.trim().length()>0){
			users = userManageImpl.findBy("userId", userId);
		}else{
			renderText("系统已经存在该用户了!请换一个用户ID!");//输入的账户类型不匹配！
			return ;
		}
		
		if (null!=users&&users.size()>0) {
			renderText("系统已经存在该用户了!请换一个用户ID!");//已经注册，请直接登录！
		}else{
			renderText("success");//
		}
		
	}
	
	public void saveUserRole() throws Exception {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		Integer[] roleIds = ParamUtil.getIntArray(request, "id");
		Set<Role> roles=new HashSet<Role>();
		try{
			if(dbid>0){
				user = userManageImpl.get(dbid);
				roles.clear();
				if(null!=roleIds&&roleIds.length>0){
					for (Integer roId : roleIds) {
						Role role = roleManageImpl.get(roId);
						roles.add(role);
					}
				}
				user.setRoles(roles);
				userManageImpl.save(user);
				userDetailsManageImpl.loadUserByUsername(user.getUserId());
			}
			renderMsg("/user/queryList", user.getUserId()+"分配角色成功！");
		}catch (Exception e) {
			e.printStackTrace();
			renderErrorMsg(e, "");
			return ;
		}
		return ;
	}
	public void userRoleJson() throws JSONException {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		if(dbid>0){
			user=userManageImpl.get(dbid);
			Set<Role> roles = user.getRoles();
			JSONArray makeJson = makeJson(roles);
			renderJson(makeJson.toString());
			return ;
		}
		renderJson("1");
		return ;
	}
	private JSONArray makeJson(Set<Role> roles) throws JSONException{
		List<Role> roList = roleManageImpl.findBy("state", 1);
		JSONArray jsonArray=null;
		if(null!=roList&&roList.size()>0){
			jsonArray=new JSONArray();
			for (Role role : roList) {
				JSONObject object=new JSONObject();
				object.put("dbid", role.getDbid());
				object.put("name", role.getName());
				if(null!=roles&&roles.size()>0){
					for (Role role2 : roles) {
						if(role.getDbid()==role2.getDbid()){
							object.put("checked", true);
							break;
						}
					}
				}
				jsonArray.put(object);
			}
		}
		return jsonArray;
	}
}

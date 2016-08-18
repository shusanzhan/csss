package com.ystech.xwqr.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.core.dao.Page;
import com.ystech.xwqr.model.sys.User;
@Component("userManageImpl")
public class UserManageImpl extends HibernateEntityDao<User>{

	/**
	 * @param userName
	 * @param pa
	 */
	public boolean checkUser(String userName, String pa) {
		return true;
	}
	/**
	 * 功能描述：获取人员选择器，所有人员的下来框
	 * @return
	 */
	public String getAllPerson(){
		List<User> users = find("from User ", new Object[]{});
		String userSelect="";
		if (null!=users&&users.size()>0) {
			for (User user : users) {
				userSelect+="<option value='"+user.getDbid()+"us'>"+user.getRealName()+"</option>";
			}
		}
		return userSelect;
	}
	
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public List<User> queryUsersReadNew(String userIds) throws Exception {
		String sql="SELECT * FROM user WHERE dbid in ("+userIds+")";
		List<User> list = getSession().createSQLQuery(sql).addEntity(User.class).list();
		return list;
	}
	/**
	 * @param realName
	 * @param departmentId
	 * @return
	 */
	public Page<User> queryContact(Integer pageNo,Integer pageSize,String realName, Integer departmentId) {
		String sql="select * from user where 1=1 ";
		List param=new ArrayList();
		if(null!=realName&&realName.trim().length()>0){
			sql=sql+" and realName like ?";
			param.add("%"+realName+"%");
		}
		if(departmentId>1){
			sql=sql+" and  FIND_IN_SET(?,departmentIds)";
			param.add(departmentId);
		}
		Page pagedQuerySql = pagedQuerySql(pageNo, pageSize, User.class, sql, param.toArray());
		return pagedQuerySql;
	}
	/**
	 * 功能描述：查询导出数据
	 * @param realName 查询人姓名
	 * @param departmentId 查询部门名称
	 * @return
	 */
	public List<User> queryContactExcel(String realName, Integer departmentId) {
		String sql="select * from user where 1=1 ";
		List param=new ArrayList();
		if(null!=realName&&realName.trim().length()>0){
			sql=sql+" and realName like ?";
			param.add("%"+realName+"%");
		}
		if(departmentId>1){
			sql=sql+" and  FIND_IN_SET(?,departmentIds)";
			param.add(departmentId);
		}
		List<User> user = executeSql(sql, param.toArray());
		return user;
	}
}

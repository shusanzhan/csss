package com.ystech.xwqr.service.sys;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.xwqr.model.sys.Resource;
@Component("resourceManageImpl")
public class ResourceManageImpl extends HibernateEntityDao<Resource>{
	@SuppressWarnings("unchecked")
	public List<Resource> queryResourceByUserId(Integer userId) {
		String sql="SELECT * FROM sys_resource WHERE dbid IN (SELECT resourceId from sys_roleresource where roleId IN (SELECT roleId FROM sys_userroles where sys_userroles.userId="+userId+")) and menu=0 and parentId!=0 ORDER BY orderNo";
		List<Resource> resources = executeSql(sql, new Object[]{});
		return resources;
	}
	public List<Resource> queryResourceByUserId(Integer userId,Integer parentId,Integer menu) {
		String sql="SELECT * FROM sys_resource WHERE dbid IN (SELECT resourceId from sys_roleresource where roleId IN (SELECT roleId FROM sys_userroles where sys_userroles.userId="+userId+")) and parentId="+parentId+" and menu="+menu+" ORDER BY orderNo";
		List<Resource> resources = executeSql(sql, new Object[]{});
		return resources;
	}
}

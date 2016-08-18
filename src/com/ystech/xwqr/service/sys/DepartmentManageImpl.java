/**
 * 
 */
package com.ystech.xwqr.service.sys;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.ystech.core.dao.HibernateEntityDao;
import com.ystech.xwqr.model.sys.Department;

/**
 * @author shusanzhan
 * @date 2013-5-23
 */
@Component("departmentManageImpl")
public class DepartmentManageImpl extends HibernateEntityDao<Department>{

	/**
	 * @return
	 */
	public String getDepartmentSelect() {
		List<Department> departments =executeSql("select * from department where  ISNULL(parentId) ",new Object[] {});
		String select="";
		if (null!=departments&&departments.size()>0) {
			for (Department department : departments) {
				String lest = getListDep(department, "-");
				select=select+lest;
			}
		}
		return select;
	}
	public String getListDep(Department department,String indent){
		try{
			StringBuilder sb = new StringBuilder();
			if (null!=department) {
				sb.append("<option value='"+department.getDbid()+"'>"+department.getName()+"</option>");
				List<Department> children = findBy("parent.dbid",department.getDbid());
				if (null!=children&&children.size()>0) {
					for (Department department2 : children) {
						sb.append("<option value='"+department2.getDbid()+"'>"+indent+""+department2.getName()+"</option>");
						List<Department> findBy = findBy("parent.dbid",department2.getDbid());
						if (null!=findBy&&findBy.size()>0.) {
							sb.append(getListDep(department2, indent+"-"));
						}
					}
				}
			}
			return sb.toString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public String getDepartmentIds(Department department) {
		StringBuilder dbids = new StringBuilder("");
		if(null!=department){
			dbids=dbids.append(department.getDbid()+",");
			Set<Department> children = department.getChildren();
			if(null!=children&&children.size()>0){
				for (Department department2 : children) {
					dbids=dbids.append(department2.getDbid()+",");
					List<Department> findBy = findBy("parent.dbid",department2.getDbid());
					if (null!=findBy&&findBy.size()>0) {
						dbids.append(getDepartmentIds(department2)+",");
					}
				}
			}
			String string = dbids.toString();
			string=string.substring(0, string.length()-1);
			return string;
		}else{
			return null;
		}
	}
	public void save() {
	}
}

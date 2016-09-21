/**
 * 
 */
package com.ystech.xwqr.action.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ystech.core.security.SecurityUserHolder;
import com.ystech.core.util.ParamUtil;
import com.ystech.core.web.BaseController;
import com.ystech.csss.service.ScannRecordManageImpl;
import com.ystech.xwqr.model.sys.SystemInfo;
import com.ystech.xwqr.model.sys.User;
import com.ystech.xwqr.service.sys.DepartmentManageImpl;
import com.ystech.xwqr.service.sys.ResourceManageImpl;
import com.ystech.xwqr.service.sys.SystemInfoMangeImpl;

/**
 * @author shusanzhan
 * @date 2014-2-11
 */
@Component("mainAction")
@Scope("prototype")
public class MainAction extends BaseController{
	private String[] iocns={"fa-tachometer","fa-desktop","fa-list","fa-pencil-square-o","fa-list-alt","fa-calendar","fa-picture-o"," fa-tag","fa-file-o"};
	private ResourceManageImpl resourceManageImpl;
	private HttpServletRequest request=getRequest();
	private DepartmentManageImpl departmentManageImpl;
	private SystemInfoMangeImpl systemInfoMangeImpl;
	private ScannRecordManageImpl scannRecordManageImpl;
	@Resource
	public void setResourceManageImpl(ResourceManageImpl resourceManageImpl) {
		this.resourceManageImpl = resourceManageImpl;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	@Resource
	public void setDepartmentManageImpl(DepartmentManageImpl departmentManageImpl) {
		this.departmentManageImpl = departmentManageImpl;
	}
	@Resource
	public void setSystemInfoMangeImpl(SystemInfoMangeImpl systemInfoMangeImpl) {
		this.systemInfoMangeImpl = systemInfoMangeImpl;
	}
	@Resource
	public void setScannRecordManageImpl(ScannRecordManageImpl scannRecordManageImpl) {
		this.scannRecordManageImpl = scannRecordManageImpl;
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		try {
			List<SystemInfo> systemInfos = systemInfoMangeImpl.getAll();
			if(null!=systemInfos&&systemInfos.size()>0){
				request.setAttribute("systemInfo", systemInfos.get(0));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "login";
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		HttpServletRequest request = this.getRequest();
		try{
			User user = SecurityUserHolder.getCurrentUser();
			List<com.ystech.xwqr.model.sys.Resource> resources = resourceManageImpl.queryResourceByUserId(user.getDbid());
			if(null!=resources&&resources.size()>0){
				com.ystech.xwqr.model.sys.Resource resource = resources.get(0);
				String menu = menu(resource.getDbid());
				request.setAttribute("menu", menu);
			}
			request.setAttribute("resources", resources);
			
			List<SystemInfo> systemInfos = systemInfoMangeImpl.getAll();
			if(null!=systemInfos&&systemInfos.size()>0){
				request.setAttribute("systemInfo", systemInfos.get(0));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
	/**
	 * 功能描述：系统管理员
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String adminContent() throws Exception {
		try{
			 String gzSql = "SELECT COUNT(*) num FROM weixin_gzuserinfo";
			 Object gzCount = scannRecordManageImpl.count(gzSql, null);
			 request.setAttribute("gzCount", gzCount);
			 String staffSql = "SELECT COUNT(*) num FROM csss_staff";
			 Object staffCount = scannRecordManageImpl.count(staffSql, null);
			 request.setAttribute("staffCount", staffCount);
			 String scTypeScannSql = "SELECT COUNT(*) num FROM csss_scannrecord WHERE scannType=1";
			 Object scannCount = scannRecordManageImpl.count(scTypeScannSql, null);
			 request.setAttribute("scannCount", scannCount);
			 String scTypeLeaderSql = "SELECT COUNT(*) num FROM csss_scannrecord WHERE scannType=2";
			 Object leadCount = scannRecordManageImpl.count(scTypeLeaderSql, null);
			request.setAttribute("leadCount", leadCount);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "adminContent";
	}
	public void submenu() {
		HttpServletRequest request = this.getRequest();
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		User user = SecurityUserHolder.getCurrentUser();
		try{
			List<com.ystech.xwqr.model.sys.Resource> resources = resourceManageImpl.queryResourceByUserId(user.getDbid(),dbid,1);
			JSONArray resourceJson=new JSONArray();
			for (com.ystech.xwqr.model.sys.Resource resource : resources) {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", resource.getDbid());
				jsonObject.put("target", "contentUrl");
				jsonObject.put("url", resource.getContent());
				jsonObject.put("name", resource.getTitle());
				resourceJson.put(jsonObject);
			}
			renderJson(resourceJson.toString());
		}catch (Exception e) {
			e.printStackTrace();
			renderText("error");
			return ;
		}
	}
	/**
	 * 功能描述：
	 * 参数描述：
	 * 逻辑描述：
	 * @return
	 * @throws Exception
	 */
	public void submenu2() throws Exception {
		Integer dbid = ParamUtil.getIntParam(request, "dbid", -1);
		try{
			String menu = menu(dbid);
			renderText(menu);
		}catch (Exception e) {
			e.printStackTrace();
			renderText("error");
			return ;
		}
	}
	private String menu(Integer parnentId){
		StringBuffer buffer=new StringBuffer();
		User user = SecurityUserHolder.getCurrentUser();
		List<com.ystech.xwqr.model.sys.Resource> resources = resourceManageImpl.queryResourceByUserId(user.getDbid(),parnentId,1);
		if(null!=resources){
			int i=0;
			for (com.ystech.xwqr.model.sys.Resource parent : resources) {
				List<com.ystech.xwqr.model.sys.Resource> childResources = resourceManageImpl.queryResourceByUserId(user.getDbid(),parent.getDbid(),2);
				if(null!=childResources&&childResources.size()>0){
					//如果还有子集菜单
					if(i==0){
						buffer.append("<li class=\"active open hsub\">");
					}else{
						buffer.append("<li class=\"hsub\">");
					}
					buffer.append("<a href='#' class=\"dropdown-toggle\">");
					if(i<=iocns.length){
						buffer.append("<i class='menu-icon fa "+iocns[i]+"'></i>");
					}else{
						buffer.append("<i class='menu-icon fa "+iocns[i-iocns.length]+"'></i>");
					}
					buffer.append("<span class=\"menu-text\">"+parent.getTitle()+"</span>");
					buffer.append("<b class=\"arrow fa fa-angle-down\"></b>");
					buffer.append("</a>");
					buffer.append("<b class=\"arrow\"></b>");
					if(i==0){
						buffer.append("<ul class=\"submenu nav-show\" style=\"display: block;\">");
					}
					else{
						buffer.append("<ul class=\"submenu\">");
					}
					int j=0;
					for (com.ystech.xwqr.model.sys.Resource  child : childResources) {
						if(i==0&&j==0){
							buffer.append("<li class=\"active\">");
						}else{
							buffer.append("<li class=\"\">");
						}
						buffer.append("<a href='"+child.getContent()+"' target='contentUrl'>");
						buffer.append("<i class=\"menu-icon fa fa-caret-right\"></i>");
						buffer.append(child.getTitle());
						buffer.append("</a>");
						buffer.append("<b class=\"arrow\"></b>");
						buffer.append("</li>");
						j++;
					}
					buffer.append("</ul>");
					buffer.append("</li>");
				}else{
					//如果是独立的链接
					if(i==0){
						buffer.append("<li class=\"active open\">");
					}else{
						buffer.append("<li class=\"\">");
					}
					if(null!=parent.getContent()&&parent.getContent().trim().length()>0){
						buffer.append("<a href='"+parent.getContent()+"' target='contentUrl'>");
					}
					if(i<=iocns.length){
						buffer.append("<i class='menu-icon fa "+iocns[i]+"'></i>");
					}else{
						buffer.append("<i class='menu-icon fa "+iocns[i-iocns.length]+"'></i>");
					}
					buffer.append("<span class=\"menu-text\">"+parent.getTitle()+"</span>");
					buffer.append("</a>");
					buffer.append("<b class=\"arrow\"></b>");
					buffer.append("</li>");
				}
				i++;
			}
		}
		return buffer.toString();
	}
}

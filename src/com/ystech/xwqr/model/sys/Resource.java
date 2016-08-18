package com.ystech.xwqr.model.sys;

import java.util.Set;

// Generated 2012-11-21 17:13:09 by Hibernate Tools 3.4.0.CR1

/**
 * Resource generated by hbm2java
 */
public class Resource implements java.io.Serializable {

	private Integer dbid;
	private String type;
	private String content;
	private String title;
	private Integer parentId;
	private Integer menu;
	private Integer orderNo;
	//一个资源对应一个父节点1:1
	private Resource parent;
	//一个父节点对应多个资源：1：n
	private Set<Resource> children;
	
	//资源与角色关系是：n:n
	private Set<Role> roles;
	
	public Resource() {
	}

	public Resource(String type, String content, String title,
			Integer parentId,  Integer orderNo) {
		this.type = type;
		this.content = content;
		this.title = title;
		this.parentId = parentId;
		this.orderNo = orderNo;
	}

	public Integer getDbid() {
		return this.dbid;
	}

	public void setDbid(Integer dbid) {
		this.dbid = dbid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getMenu() {
		return menu;
	}

	public void setMenu(Integer menu) {
		this.menu = menu;
	}

	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public Set<Resource> getChildren() {
		return children;
	}

	public void setChildren(Set<Resource> children) {
		this.children = children;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
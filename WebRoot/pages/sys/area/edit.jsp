<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>账号添加</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet">
<link  href="${ctx }/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/easy_validator.pack.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body class="bodycolor">
<div class="location">
      	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
      	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
		<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/area/queryList'">区域管理</a>
</div>
<div class="line"></div>
<div class="frmContent">
<form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
	<input type="hidden" value="${area.dbid }" id="dbid" name="area.dbid">
	<input type="hidden" value="${area.createDate }" id="createDate" name="area.createDate">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
	<c:if var="status" test="${!empty(parent) }">
		<tr>
			<td class="formTableTdLeft">上级地区：</td>
			<td>
				<input type="hidden" class="largeX text" name="parentId"	id="parentId" value="${parent.dbid }"  />
				${parent.name }
			</td>
		</tr>
	</c:if>
	<c:if test="${status==false }">
	<tr>
			<td class="formTableTdLeft">上级地区：</td>
			<td>
				顶级地区
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="formTableTdLeft">名称：</td>
		<td >
			<input type="text" class="largeX text" name="area.name"	id="name" value="${area.name }" checkType="string,1,20"  tip="请输入品牌名称！"  />
		</td>
	</tr>
	<tr>
		<td class="formTableTdLeft">排序：</td>
		<td >
			<input type="text" class="large text" name="area.orders" id="orders"  value="${area.orders }" checkType="integer" canEmpty="Y" tip="请输入排序号！"/>
		</td>
	</tr>
	</table>
	</form>
	<div class="formButton">
		<a href="javascript:void(-1)" class="but butSave"	onclick="$.utile.submitForm('frmId','${ctx}/area/save')">
			保存
		</a> 
		<a  href="javascript:void(-1)"  class="but butCancle" onclick="window.history.go(-1)">
			返回</a>
	</div>
</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/easy_validator.pack.js"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
<title>省公司管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">省公司管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/proviceShop/edit'">添加</a>
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/proviceShop/importExcel'">批量导入省公司</a>
		<a class="but butCancle" href="javascript:void(-1);" onclick="$.utile.deleteIds('${ctx }/proviceShop/delete','searchPageForm')">删除</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/proviceShop/queryList" method="get">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
   		</form>
   	</div>
   	<div style="clear: both;"></div>
</div>
<c:if test="${empty(page.result)||fn:length(page.result)<=0 }" var="status">
	<div class="alert alert-error">
		<strong>无省公司数据，请点击【添加】按钮添加</strong>
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%"  cellpadding="0" cellspacing="0" class="mainTable" border="0">
	<thead  class="TableHeader">
		<tr>
			<td class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span2">编号</td>
			<td class="span2">省公司名称</td>
			<td class="span3">省-市-区</td>
			<td class="span3">地址</td>
			<td class="span1">排序</td>
			<td class="span4">备注</td>
			<td class="span2">操作</td>
		</tr>
	</thead>
	<c:forEach var="proviceShop" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${proviceShop.dbid }"/></td>
			<td>
				${proviceShop.no }
			</td>
			<td>
				${proviceShop.name }
			</td>
			<td>
				${proviceShop.provice }-
				${proviceShop.city }-
				${proviceShop.areaStr }
			</td>
			<td>
				${proviceShop.address }
			</td>
			<td>
				${proviceShop.num }
			</td>
			<td style="text-align: left;">
				${proviceShop.note}
			 </td>
			<td><a href="#" class="aedit"
				onclick="window.location.href='${ctx }/proviceShop/edit?dbid=${proviceShop.dbid}'">编辑</a>
				<a href="#" class="aedit"
				onclick="$.utile.deleteById('${ctx }/proviceShop/delete?dbids=${proviceShop.dbid}')">删除</a>
		</tr>
	</c:forEach>
</table>
</c:if>
<div id="fanye">
	<%@ include file="../../commons/commonPagination.jsp" %>
</div>
</body>
</html>
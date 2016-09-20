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
<title>门店管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">门店管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/csssShop/edit'">添加</a>
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/csssShop/importExcel'">批量导入门店</a>
		<a class="but butCancle" href="javascript:void(-1);" onclick="$.utile.deleteIds('${ctx }/csssShop/delete','searchPageForm')">删除</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/csssShop/queryList" method="get">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
   		</form>
   	</div>
   	<div style="clear: both;"></div>
</div>
<c:if test="${empty(page.result)||fn:length(page.result)<=0 }" var="status">
	<div class="alert alert-error">
		<strong>无门店数据，请点击【添加】按钮添加</strong>
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%"  cellpadding="0" cellspacing="0" class="mainTable" border="0">
	<thead  class="TableHeader">
		<tr>
			<td class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span1">编号</td>
			<td class="span3">店面</td>
			<td class="span3">地市公司</td>
			<td class="span2">省-市-区</td>
			<td class="span2">地址</td>
			<td class="span1">排序</td>
			<td class="span2">操作</td>
		</tr>
	</thead>
	<c:forEach var="csssShop" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${csssShop.dbid }"/></td>
			<td>
				${csssShop.no }
			</td>
			<td>
				${csssShop.name }
			</td>
			<td>
				${csssShop.cityShop.name }
			</td>
			<td>
				${csssShop.provice }-
				${csssShop.city }-
				${csssShop.areaStr }
			</td>
			<td>
				${csssShop.address }
			</td>
			<td>
				${csssShop.num }
			</td>
			<td><a href="#" class="aedit"
				onclick="window.location.href='${ctx }/csssShop/edit?dbid=${csssShop.dbid}'">编辑</a>
				<a href="#" class="aedit"
				onclick="$.utile.deleteById('${ctx }/csssShop/delete?dbids=${csssShop.dbid}')">删除</a>
		</tr>
	</c:forEach>
</table>
</c:if>
<div id="fanye">
	<%@ include file="../../commons/commonPagination.jsp" %>
</div>
</body>
</html>
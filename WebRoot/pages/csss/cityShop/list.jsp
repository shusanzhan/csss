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
<title>地市公司管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">地市公司管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/cityShop/edit'">添加</a>
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/cityShop/importExcel'">批量导入地市公司</a>
		<a class="but butCancle" href="javascript:void(-1);" onclick="$.utile.deleteIds('${ctx }/cityShop/delete','searchPageForm')">删除</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/cityShop/queryList" method="get">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
   		</form>
   	</div>
   	<div style="clear: both;"></div>
</div>
<c:if test="${empty(page.result)||fn:length(page.result)<=0 }" var="status">
	<div class="alert alert-error">
		<strong>无地市公司数据，请点击【添加】按钮添加</strong>
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%"  cellpadding="0" cellspacing="0" class="mainTable" border="0">
	<thead  class="TableHeader">
		<tr>
			<td class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span1">编号</td>
			<td class="span3">省公司名称</td>
			<td class="span3">地市公司名称</td>
			<td class="span3">省-市-区</td>
			<td class="span3">地址</td>
			<td class="span1">排序</td>
			<td class="span4">备注</td>
			<td class="span2">操作</td>
		</tr>
	</thead>
	<c:forEach var="cityShop" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${cityShop.dbid }"/></td>
			<td>
				${cityShop.no }
			</td>
			<td>
				${cityShop.proviceShop.name }
			</td>
			<td>
				${cityShop.name }
			</td>
			<td>
				${cityShop.provice }-
				${cityShop.city }-
				${cityShop.areaStr }
			</td>
			<td>
				${cityShop.address }
			</td>
			<td>
				${cityShop.num }
			</td>
			<td style="text-align: left;">
				${cityShop.note}
			 </td>
			<td><a href="#" class="aedit"
				onclick="window.location.href='${ctx }/cityShop/edit?dbid=${cityShop.dbid}'">编辑</a>
				<a href="#" class="aedit"
				onclick="$.utile.deleteById('${ctx }/cityShop/delete?dbids=${cityShop.dbid}')">删除</a>
		</tr>
	</c:forEach>
</table>
</c:if>
<div id="fanye">
	<%@ include file="../../commons/commonPagination.jsp" %>
</div>
</body>
</html>
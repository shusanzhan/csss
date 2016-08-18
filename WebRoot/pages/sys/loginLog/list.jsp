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
<title>登录日志管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >登录日志管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="javascript:void(-1);" onclick="$.utile.deleteIds('${ctx }/loginLog/delete','searchPageForm')">删除</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/loginLog/queryList" method="post">
	     <input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
	     <input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
		<%--  <table>
			<tr>
				<td>登录用户：</td>
				<td><input type="text" id="userName" name="userName" class="input-small field" value="${param.userName}"></input></td>
				<td><input type="submit" value="查询"></input></td>
			</tr>
		 </table> --%>
		</form>
   	</div>
   	<div style="clear: both;"></div>
</div>
<c:if test="${empty(page.result)||page.result==null }" var="status">
	<div id="result" class="alert alert-info">
		无操作数据！请点击“添加”按钮进行添加数据操作
	</div>
</c:if>
<c:if test="${status==false }">
	<table width="100%"  border="0" class="mainTable" cellpadding="0" cellspacing="0">
	<thead class="TableHeader">
		<tr>
			<td class="span1" style="width: 30px;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span1">登录ID</td>
			<td class="span2">登录人</td>
			<td class="span2">登录时间</td>
			<td class="span2">登录IP</td>
			<td class="span4">登录地点</td>
			<td class="span3">sessionId</td>
		</tr>
	</thead>
	<c:forEach var="loginLog" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${loginLog.dbid }"/></td>
			<td>${loginLog.userId }</td>
			<td>${loginLog.userName }</td>
			<td>${loginLog.loginDate }</td>
			<td>${loginLog.ipAddress }</td>
			<td>${loginLog.loginAddress }</td>
			<td>${loginLog.sessionId }</td>
		</tr>
	</c:forEach>
	</table>
	<div id="fanye">
		<%@ include file="../../commons/commonPagination.jsp" %>
	</div>
</div>
</c:if>
</body>
</html>
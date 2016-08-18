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
<title>操作管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" >操作日志管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="javascript:void(-1);" onclick="	$.utile.deleteIds('${ctx }/operateLog/delete','searchPageForm')">删除</a>
   </div>
  	<div class="seracrhOperate">
  		 <form name="searchPageForm" id="searchPageForm" action="${ctx}/operateLog/queryList" method="post">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
  		<table cellpadding="0" cellspacing="0" class="searchTable" >
  			<tr>
  				<td><label>操作人：</label></td>
  				<td><input type="text" id="userName" name="userName" class="text midea" value="${param.userName}"></input></td>
  				<td><div href="javascript:void(-1)" onclick="$('#searchPageForm')[0].submit()" class="searchIcon"></div></td>
   			</tr>
   		</table>
   		</form>
   	</div>
   	<div style="clear: both;"></div>
</div>
<c:if test="${empty(page.result)||page.result==null }" var="status">
	<div class="alert alert-info" >
		无操作数据！请点击“添加”按钮进行添加数据操作
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%" border="0" class="mainTable" cellpadding="0" cellspacing="0">
	<thead class="TableHeader">
		<tr>
			<td class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span2">操作人</td>
			<td class="span2">操作时间</td>
			<td class="span2">操作对象</td>
			<td class="span1">操作类型</td>
			<td class="span2">操作数据</td>
			<td class="span2">IP地址</td>
		</tr>
	</thead>
	<c:forEach var="operateLog" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${operateLog.dbid }"/></td>
			<td>${operateLog.operator }</td>
			<td>
				<fmt:formatDate value="${operateLog.operatedate }" pattern="yyyy-MM-dd HH:mm:ss"/>	</td>
			<td>${operateLog.operateobj }</td>
			<td>${operateLog.operatetype }</td>
			<td>${operateLog.operatefeild }</td>
			<td>${operateLog.ipAddress }</td>
		</tr>
	</c:forEach>
	</table>
	<div id="fanye">
		<%@ include file="../../commons/commonPagination.jsp" %>
	</div>
	</c:if>
</div>
</body>
</html>
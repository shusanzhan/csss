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
<title>店员管理</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">店员管理</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/csssStaff/edit'">添加</a>
		<a class="but button" href="javascript:void();" onclick="window.location.href='${ctx}/csssStaff/importExcel'">批量导入店员</a>
		<a class="but butCancle" href="javascript:void(-1);" onclick="$.utile.deleteIds('${ctx }/csssStaff/delete','searchPageForm')">删除</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/csssStaff/queryList" method="post">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
		<table cellpadding="0" cellspacing="0" class="searchTable" >
  			<tr>
  				<td><label>店名：</label></td>
  				<td>
  					<select class="midea text" id="csssShopId" name="csssShopId" onchange="$('#searchPageForm')[0].submit()">
  						<option value="-1">请选择...</option>
  						<c:forEach var="csssShop" items="${csssShops }">
	  						<option value="${csssShop.dbid }" ${csssShop.dbid==param.csssShopId?'selected="selected"':''} >${csssShop.name }</option>
  						</c:forEach>
  					</select>
  				</td>
  				<td><div href="javascript:void(-1)" onclick="$('#searchPageForm')[0].submit()" class="searchIcon"></div></td>
   			</tr>
   		</table>
   		</form>
   	</div>
   	<div style="clear: both;"></div>
</div>
<c:if test="${empty(page.result)||fn:length(page.result)<=0 }" var="status">
	<div class="alert alert-error">
		<strong>无店员数据，请点击【添加】按钮添加</strong>
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%"  cellpadding="0" cellspacing="0" class="mainTable" border="0">
	<thead  class="TableHeader">
		<tr>
			<td class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span2">店编号</td>
			<td class="span2">名称</td>
			<td class="span2">编号</td>
			<td class="span2">联系电话</td>
			<td class="span1">二维码状态</td>
			<td class="span2">二维码</td>
			<td class="span1">扫描次数</td>
			<td class="span1">导流次数</td>
			<td class="span3">备注</td>
			<td class="span2">操作</td>
		</tr>
	</thead>
	<c:forEach var="csssStaff" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${csssStaff.dbid }"/></td>
			<td>
				${csssStaff.csssShop.no }
			</td>
			<td>
				${csssStaff.name }  ${csssStaff.sex }
			</td>
			<td>
				${csssStaff.no}
			</td>
			<td>
				${csssStaff.mobilePhone }
			</td>
			<td>
				<c:if test="${csssStaff.qrCodeStatus==1 }">
					<span style="color: red">待生成</span>
				</c:if>
				<c:if test="${csssStaff.qrCodeStatus==2 }">
					<span style="color: green">已生成</span>
				</c:if>
			</td>
			<td>
				<c:if test="${csssStaff.qrCodeStatus==1 }">
					<a href="#" class="aedit" style="color: #2b7dbc"	onclick="$.utile.operatorDataByDbid('${ctx }/csssStaff/createQrCodeByDbid?csssStaffDbid=${csssStaff.dbid}','searchPageForm','确定生成二网码吗？')">生成二维码</a>
				</c:if>
				<c:if test="${csssStaff.qrCodeStatus==2 }">
					<img alt="" src="${ csssStaff.qrCode}" width="60px;">
				</c:if>
			</td>
			<td>
				${csssStaff.scannNum }
			</td>
			<td>
				${csssStaff.leaderNum }
			</td>
			<td style="text-align: left;">
				${csssStaff.note}
			 </td>
			<td><a href="#" class="aedit"
				onclick="window.location.href='${ctx }/csssStaff/edit?dbid=${csssStaff.dbid}'">编辑</a>
				<a href="#" class="aedit"
				onclick="$.utile.operatorDataByDbid('${ctx }/csssStaff/delete?dbids=${csssStaff.dbid}','searchPageForm','您确定删除该信息吗')">删除</a>
		</tr>
	</c:forEach>
</table>
</c:if>
<div id="fanye">
	<%@ include file="../../commons/commonPagination.jsp" %>
</div>
</body>
</html>
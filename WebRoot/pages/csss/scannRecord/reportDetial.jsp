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
<script type="text/javascript" src="${ctx }/widgets/My97DatePicker/WdatePicker.js"></script>
<title>扫描记录明细</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/scannRecord/report'">销售统计</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx }/scannRecord/reportStaff?csssShopId=${param.csssShopId}&startTime=${param.startTime }&endTime=${param.endTime}'">扫描记录（店）明细</a>-
	<a href="javascript:void(-1);" onclick="">扫描记录明细</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but butCancle" href="javascript:void(-1);" onclick="window.history.go(-1)">返回</a>
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/scannRecord/reportDetial" method="post">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
		<table cellpadding="0" cellspacing="0" class="searchTable" >
  			<tr>
  				<%-- <td><label>店员：</label></td>
  				<td>
  					<input class="text small" id="staffName" name="staffName"  value="${param.staffName }">
  				</td> --%>
  				<td><label>扫描时间：</label></td>
  				<td>
  					<input class="text small" id="startTime" name="startTime" onFocus="WdatePicker({isShowClear:true,readOnly:true})" value="${param.startTime }" >~
  					<input class="text small" id="endTime" name="endTime" onFocus="WdatePicker({isShowClear:true,readOnly:true})" value="${param.endTime }">
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
		<strong>无扫描记录</strong>
	</div>
</c:if>
<c:if test="${status==false }">
<table width="100%"  cellpadding="0" cellspacing="0" class="mainTable" border="0">
	<thead  class="TableHeader">
		<tr>
			<td class="sn"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" /></td>
			<td class="span3">头像</td>
			<td class="span3">昵称</td>
			<td class="span2">店名</td>
			<td class="span3">店员</td>
			<td class="span3">扫描时间</td>
			<td class="span3">类型</td>
		</tr>
	</thead>
	<c:forEach var="scannRecord" items="${page.result }">
		<tr height="32" align="center">
			<td><input type='checkbox' name="id" id="id1" value="${scannRecord.dbid }"/></td>
			<td>
				<img alt="" src="${scannRecord.weixinGzuserinfo.headimgurl}" width="50">
				
			</td>
			<td>
				${scannRecord.weixinGzuserinfo.nickname}
			</td>
			<td>
				${scannRecord.csssShop.name}
			</td>
			<td>
				${scannRecord.csssStaff.name}
			</td>
			<td>
				${scannRecord.scannDate}
			</td>
			<td >
				<c:if test="${scannRecord.scannType==1 }">
					关注扫描
				</c:if>
				<c:if test="${scannRecord.scannType==2 }">
					计数扫描
				</c:if>
			 </td>
		</tr>
	</c:forEach>
</table>
</c:if>
<div id="fanye">
	<%@ include file="../../commons/commonPagination.jsp" %>
</div>
<script type="text/javascript">
function exportExcel(searchFrm){
 	var params;
 	if(null!=searchFrm&&searchFrm!=undefined&&searchFrm!=''){
 		params=$("#"+searchFrm).serialize();
 	}
 	window.location.href='${ctx}/scannRecord/downExportExcel?'+params;
 }
</script>
</body>
</html>
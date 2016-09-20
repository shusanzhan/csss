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
<style type="text/css">
#shopNumber{
	border-top: 1px solid  #cccccc;
	border-left: 1px solid  #cccccc;
	font-size: 12px;
	width: 560px;
}
#shopNumber th,#shopNumber td{
	border-bottom: 1px solid  #cccccc;
	border-right: 1px solid  #cccccc;
	height: 30px;
	font-size: 14px;
}
#shopNumber thead{
background-color: #eeeeee; border-color:#eeeeee;font-size: 16px;font-weight: bold;
}
.frmContent form table tr td {
    padding-left: 5px;
}
#noLine{
	border-top: 0;
	border-left: 0;
}
#noLine td{
	border: 0;
}
[class^="icon-"], [class*=" icon-"] {
    background-image: url("../images/bootstrap/glyphicons-halflings.png");
    background-position: 14px 14px;
    background-repeat: no-repeat;
    display: inline-block;
    height: 14px;
    line-height: 14px;
    margin-top: 1px;
    vertical-align: text-top;
    width: 14px;
}
[class^="icon-"], [class*=" icon-"] {
    background-image: url("../images/bootstrap/glyphicons-halflings.png");
    background-position: 14px 14px;
    background-repeat: no-repeat;
    display: inline-block;
    height: 14px;
    line-height: 14px;
    margin-top: 1px;
    vertical-align: text-top;
    width: 14px;
}
.icon-remove {
    background-position: -312px 0;
}

.icon-ok {
    background-position: -288px 0;
}
</style>
<title>扫描记录统计</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/scannRecord/report'">销售统计</a>-
	<a href="javascript:void(-1);" onclick="">扫描记录（店）明细</a>
</div>
 <!--location end-->
<div class="line"></div>
<div class="listOperate">
	<div class="operate">
		<a class="but butCancle" href="javascript:void(-1);" onclick="window.history.go(-1)">返回</a>
		<!-- <a class="but button" href="javascript:void(-1);" onclick="exportExcel('searchPageForm')">导出excel</a> -->
   </div>
  	<div class="seracrhOperate">
  		<form name="searchPageForm" id="searchPageForm" action="${ctx}/scannRecord/reportStaff" method="post">
		<input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
		<input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
		<table cellpadding="0" cellspacing="0" class="searchTable" >
  			<tr>
  				<td><label>店员：</label></td>
  				<td>
  					<input class="text small" id="staffName" name="staffName"  value="${param.staffName }">
  				</td>
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
<c:if test="${empty(reportDatas)||fn:length(reportDatas)<=0 }" var="status">
	<div class="alert alert-error">
		<strong>无扫描记录</strong>
	</div>
</c:if>
<c:if test="${status==false }">
	<table id="shopNumber" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px;margin-right: 12px;width: 100%;">
		<tr height="32" style="background-color: #eeeeee; border-color:#eeeeee;font-size: 14px;font-weight: bold;">
			<th style="width: 30px;text-align: center;" rowspan="1">序号</th>
			<th style="width: 40px;text-align: center;"  rowspan="1">省市公司</th>
			<th style="width: 60px;text-align: center;"  rowspan="1">省市公司描述</th>
			<th style="width: 70px;text-align: center;"  rowspan="1">地市公司</th>
			<th style="width: 70px;text-align: center;"  rowspan="1">地市公司名称</th>
			<th style="width: 40px;text-align: center;"  rowspan="1">便利店</th>
			<th style="width: 80px;text-align: center;"  rowspan="1">便利店名称</th>
			<th style="width: 40px;text-align: center;"  rowspan="1">店员</th>
			<th style="width: 60px;text-align: center;"  rowspan="1">销售数量</th>
		</tr>
		<c:forEach var="reportData" items="${reportDatas }" varStatus="i">
			<tr id="tr" height="24">
				<td style="text-align: center;">
					${i.index+1 }
				</td>
				<td style="text-align: center;">
					${reportData.psNo}
				</td>
				<td style="text-align: center;">
					${reportData.psName }
				</td>
				<td style="text-align: center;">
					${reportData.csNo}
				</td>
				<td style="text-align: center;">
					${reportData.csName}
				</td>
				<td style="text-align: center;">
					${reportData.shopNo }			
				</td>
				<td style="text-align: center;padding-left: 0px;" >
					${reportData.shopName }
				</td>
				<td style="text-align: center;padding-left: 0px;" >
					<a style="color: #2b7dbc" href="${ctx }/scannRecord/reportDetial?staffId=${reportData.dbid}&startTime=${param.startTime }&endTime=${param.endTime}">${reportData.staffName }</a>
				</td>
				<td style="text-align: center;">
					${reportData.num}
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
</body>
<script type="text/javascript">
function exportExcel(searchFrm){
 	var params;
 	if(null!=searchFrm&&searchFrm!=undefined&&searchFrm!=''){
 		params=$("#"+searchFrm).serialize();
 	}
 	window.location.href='${ctx}/scannRecord/downReportExcel?'+params;
 }
</script>
</html>
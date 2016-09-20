<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet"/>
<link  href="${ctx }/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/easy_validator.pack.js"></script>
<title>门店管理</title>
</head>
<body class="bodycolor">
<div class="location">
     	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
     	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/csssShop/queryList'">门店管理</a>-
<a href="javascript:void(-1);">
	<c:if test="${empty(csssShop) }">添加门店</c:if>
	<c:if test="${!empty(csssShop) }">编辑门店</c:if>
</a>
</div>
<div class="line"></div>
<div class="frmContent">
	<form action="" name="frmId" id="frmId"  target="_self">
		<s:token></s:token>
		<input type="hidden" name="csssShop.dbid" id="dbid" value="${csssShop.dbid }">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="32">
				<td class="formTableTdLeft">省公司:&nbsp;</td>
				<td>
					<select class="largeX text" id="cityShopId" name="cityShopId" checkType="integer,1" tip="请选地市公司">
						<option value="-1">请选择...</option>
						<c:forEach var="cityShop" items="${ cityShops}">
							<option value="${cityShop.dbid }" ${csssShop.cityShop.dbid==cityShop.dbid?'selected="selected"':'' } >${cityShop.name }</option>
						</c:forEach>
					</select>
					<span style="color: red;">*</span>
				</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">编号:&nbsp;</td>
				<td ><input type="text" name="csssShop.no" id="no"
					value="${csssShop.no }" class="largeX text" title="编号"	checkType="string,1,50" tip="编号不能为空"><span style="color: red;">*</span></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">名称:&nbsp;</td>
				<td ><input type="text" name="csssShop.name" id="name"
					value="${csssShop.name }" class="largeX text" title="名称"	checkType="string,1,50" tip="名称不能为空"><span style="color: red;">*</span></td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft">省:&nbsp;</td>
				<td>
					<select class="largeX text" id="proviceId" name="proviceId" checkType="integer,1" tip="请选择省" onchange="ajaxCity(this)">
						<c:forEach var="provice" items="${ provices}">
							<option value="${provice.dbid }" ${csssShop.provice==provice.name?'selected="selected"':'' } >${provice.name }</option>
						</c:forEach>
					</select>
					<span style="color: red;">*</span>
				</td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft">市:&nbsp;</td>
				<td id="cityTr">
					<select class="largeX text" id="cityId" name="cityId" checkType="integer,1" tip="请选择市" onchange="ajaxArea(this)">
						<c:forEach var="provice" items="${ cityAreas}">
							<option value="${provice.dbid }" ${csssShop.city==provice.name?'selected="selected"':'' } >${provice.name }</option>
						</c:forEach>
					</select>
					<span style="color: red;">*</span>
				</td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft">区/县:&nbsp;</td>
				<td id="areaStrTr">
					<select class="largeX text" id="areaId" name="areaId"  checkType="string,1" tip="请选择区/县">
						<c:forEach var="provice" items="${ areas}">
							<option value="${provice.dbid }" ${csssShop.areaStr==provice.name?'selected="selected"':'' } >${provice.name }</option>
						</c:forEach>
					</select>
					<span style="color: red;">*</span>
				</td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft">地址:&nbsp;</td>
				<td>
					<input type="text" name="csssShop.address" id="address"
					value="${csssShop.address }" class="largeX text" title="地址"	>
				</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">排序:&nbsp;</td>
				<td ><input type="text" name="csssShop.num" id="num"
					value="${csssShop.num }" class="largeX text" title="排序"	checkType="integer" tip="排序不能为空"><span style="color: red;">*</span></td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft">备注:&nbsp;</td>
				<td>
					<textarea class="text textarea" rows="8" cols="60" name="csssShop.note" id="note">${csssShop.note }</textarea>
				</td>
			</tr>
		</table>
	</form>
	<div class="formButton">
		<a href="javascript:void(-1)"	onclick="$.utile.submitForm('frmId','${ctx}/csssShop/save')"	class="but butSave">保&nbsp;&nbsp;存</a> 
	    <a href="javascript:void(-1)"	onclick="window.history.go(-1)" class="but butCancle">取&nbsp;&nbsp;消</a>
	</div>
	</div>
</body>
<script type="text/javascript">
function ajaxCity(sel){
	var value=$(sel).val();
	$.post("${ctx}/csssShop/ajaxCity?parentId="+value+"&dateTime="+new Date(),{},function (data){
		if(data!="error"){
			var da=$.parseJSON(data);
			$("#cityTr").text("");
			$("#cityTr").append(data.city);
			$("#areaStrTr").text("");
			$("#areaStrTr").append(data.area);
		}
	});
}
function ajaxArea(sel){
	var value=$(sel).val();
	$.post("${ctx}/csssShop/ajaxArea?parentId="+value+"&dateTime="+new Date(),{},function (data){
		if(data!="error"){
			var da=$.parseJSON(data);
			$("#areaStrTr").text("");
			$("#areaStrTr").append(data.area);
		}
	});
}
</script>
</html>
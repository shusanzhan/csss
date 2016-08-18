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
<title>粉丝组</title>
</head>
<body class="bodycolor">
<div class="location">
     	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
     	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/weixinGroup/queryList'">粉丝组</a>-
<a href="javascript:void(-1);">
	<c:if test="${empty(weixinGroup) }">粉丝组</c:if>
	<c:if test="${!empty(weixinGroup) }">粉丝组</c:if>
</a>
</div>
<div class="line"></div>
<div class="frmContent">
	<form action="" name="frmId" id="frmId"  target="_self">
		<s:token></s:token>
		<input type="hidden" name="weixinGroup.dbid" id="dbid" value="${weixinGroup.dbid }">
		<input type="hidden" name="weixinGroup.accountId" id="accountId" value="${weixinGroup.accountId }">
		<input type="hidden" name="weixinGroup.wechatGroupId" id="wechatGroupId" value="${weixinGroup.wechatGroupId }">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft">名称:&nbsp;</td>
				<td ><input type="text" name="weixinGroup.name" id="name"
					value="${weixinGroup.name }" class="largex text" title="名称"	checkType="string,1,50" tip="名称不能为空"><span style="color: red;">*</span></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">是否默认</td>
				<td >
				<c:if test="${empty(weixinGroup) }" var="status">
					<label for="isCommon" style="float: left;padding-left:8px;width: 120px;line-height: 20px;">
						<input type="checkbox" name="weixinGroup.isCommon" id="isCommon"   value="1" >是否默认
					</label>
				</c:if>
				<c:if test="${status==false }" var="status">
					<label for="isCommon" style="float: left;padding-left:8px;width: 120px;line-height: 20px;">
						<input type="checkbox" name="weixinGroup.isCommon" id="isCommon"   ${memberShipLevel.isCommon==1?'checked="checked"':'' }  value="1" >是否默认
					</label>
				</c:if>
				<span style="color: red;">*</span></td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft">备注:&nbsp;</td>
				<td>
					<textarea class="text textarea" rows="8" cols="60" name="weixinGroup.dis" id="note">${weixinGroup.dis }</textarea>
				</td>
			</tr>
		</table>
	</form>
	<div class="formButton">
		<a href="javascript:void(-1)"	onclick="$.utile.submitForm('frmId','${ctx}/weixinGroup/save')"	class="but butSave">保&nbsp;&nbsp;存</a> 
	    <a href="javascript:void(-1)"	onclick="window.history.go(-1)" class="but butCancle">取&nbsp;&nbsp;消</a>
	</div>
	</div>
</body>
</html>
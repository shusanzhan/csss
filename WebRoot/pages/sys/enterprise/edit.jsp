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
<script type="text/javascript" src="${ctx }/widgets/ckeditor/ckeditor.js"></script>
<title>用户添加</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">企业信息管理</a>
</div>
<div class="line"></div>
<div class="frmContent" style="margin-bottom: 50px;">
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" target="_parent">
		<input type="hidden" name="enterprise.dbid" value="${enterprise.dbid }" id="dbid">
		<s:token></s:token>
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft">单位名称:&nbsp;</td>
				<td ><input type="text" name="enterprise.name" id="name"
					value="${enterprise.name }" class="largeX text" title="用户名"	checkType="string" tip="标题不能为空"><span style="color: red;">*</span></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">电话:&nbsp;</td>
				<td ><input type="text" name="enterprise.phone" id="phone"
					value="${enterprise.phone }" class="largeX text" title="电话"	checkType="phone" tip="电话不能为空"><span style="color: red;">*</span></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">传真:&nbsp;</td>
				<td ><input type="text" name="enterprise.fax" id="fax"
					value="${enterprise.fax }" class="largeX text" title="传真" checkType="phone" canEmpty="Y" tip="请输入正确的传真格式"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">邮编:&nbsp;</td>
				<td ><input type="text" name="enterprise.zipCode" id="zipCode"
					value="${enterprise.zipCode }" class="largeX text" checkType="zipCode" canEmpty="Y" tip="邮编" title="邮编"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">地址:&nbsp;</td>
				<td ><input type="text" name="enterprise.address" id="address"
					value="${enterprise.address }" class="largeX text" title="地址"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">网址:&nbsp;</td>
				<td ><input type="text" name="enterprise.webAddress" id="webAddress"
					value="${enterprise.webAddress }" class="largeX text" checkType="string" canEmpty="Y" tip="必须输入数字" title="网址"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">email:&nbsp;</td>
				<td ><input type="text" name="enterprise.email" id="email"
					value="${enterprise.email }" class="largeX text" checkType="email" canEmpty="Y" tip="请输入正确的email" title="email"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">开户银行:&nbsp;</td>
				<td ><input type="text" name="enterprise.bank" id="bank"
					value="${enterprise.bank }" class="largeX text" checkType="string" canEmpty="Y" tip="开户银行必须输入数字" title="开户银行"></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">账号:&nbsp;</td>
				<td ><input type="text" name="enterprise.account" id="account"
					value="${enterprise.account }" class="largeX text" checkType="integer" canEmpty="Y" tip="账号必须输入数字" title="账号"></td>
			</tr>
			<tr height="32">
				<td colspan="2">
						<textarea cols="59" rows="10" id="content" name="enterprise.content">${enterprise.content }</textarea>
				</td>
			</tr>
		</table>
	</form>
	<div class="formButton">
		<a href="javascript:void(-1)"	onclick="$.utile.submitForm('frmId','${ctx}/enterprise/saveEnterprise',true)"	class="but butSave">保存</a> 
	</div>
	</div>
</body>
<script type="text/javascript">
	var editor=CKEDITOR.replace("content");
</script>
</html>
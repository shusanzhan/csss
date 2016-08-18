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
<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/easy_validator.pack.js"></script>
<script type="text/javascript" src="${ctx }/widgets/ckeditor/ckeditor.js"></script>
<title>系统设置</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">系统设置</a>
</div>
<div class="line"></div>
<div class="frmContent" style="margin-bottom: 50px;">
	<form action="" name="frmId" id="frmId" style="margin-bottom: 40px;" target="_parent">
		<input type="hidden" name="systemInfo.dbid" value="${systemInfo.dbid }" id="dbid">
		<s:token></s:token>
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td class="formTableTdLeft" style='width:180px'>系统名称:&nbsp;</td>
				<td ><input type="text" name="systemInfo.name" id="name"
					value="${systemInfo.name }" class="largeX text" title="用户名"	checkType="string,1" tip="系统名称"><span style="color: red;">*系统名称用于显示在登录页面</span></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style='width:180px'>
					系统名称图片:&nbsp;<br>
					<span style="font-size: 12px;">图片大小:370*28</span>
				</td>
				<td >
					<input class="input-medium"  type="hidden" readonly="readonly" name="systemInfo.nameImage" id="nameImage" value="${systemInfo.nameImage }" />
					<img  title="" id="imgNameImage" src="${systemInfo.nameImage  }" width="120" height="58"></img>
					<a  href="javascript:void(-1)" class="aedit" style="color: green;" onclick="uploadFile('nameImage','imgNameImage')">传图片</a>
					<a  href="#modal-add-event" style="color: red;" onclick="deleteImage('nameImage','imgNameImage')">清空</a>
				</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style='width:180px'>
					登录Logo:&nbsp;
					<span style="font-size: 12px;">大小:230*128</span>
				</td>
				<td >
					<input class="input-medium"  type="hidden" readonly="readonly" name="systemInfo.loginLogo" id="loginLogo" value="${systemInfo.loginLogo}" />
					<img alt="" title="" id="imgLoginLogo" src="${systemInfo.loginLogo }" width="230" height="128"></img>
					<a  href="#modal-add-event" style="color: green;" onclick="uploadFile('loginLogo','imgLoginLogo')">上传图片</a>
					<a  href="#modal-add-event" style="color: red;" onclick="deleteImage('loginLogo','imgLoginLogo')">清空</a>
				</td>
			</tr>
			<tr height="60">
				<td class="formTableTdLeft" style='width:180px'>
					后台logo:&nbsp;
					<span style="font-size: 12px;">大小:100*58</span>
				</td>
				<td>
					<input class="input-medium"  type="hidden" readonly="readonly" name="systemInfo.infoLogo" id="infoLogo" value="${systemInfo.infoLogo}" />
					<img alt="" title="" id="imgInfoLogo" src="${systemInfo.infoLogo }" width="100" height="58"></img>
					<a  href="#modal-add-event" style="color: green;" onclick="uploadFile('infoLogo','imgInfoLogo')">上传图片</a>
					<a  href="#modal-add-event" style="color: red;" onclick="deleteImage('infoLogo','imgInfoLogo')">清空</a>
				</td>
			</tr>
		</table>
	</form>
	<div class="formButton">
		<a href="javascript:void(-1)"	onclick="$.utile.submitForm('frmId','${ctx}/systemInfo/save')"	class="but butSave">保存</a> 
	</div>
	</div>
</body>
<script type="text/javascript">
	function deleteImage(input,imgeUrl){
		$("#"+input).val("");
		$("#"+imgeUrl).attr("src","");
	}
</script>
</html>
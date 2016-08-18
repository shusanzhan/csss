<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet">
<link  href="${ctx }/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/easy_validator.pack.js"></script>
<script type="text/javascript" src="${ctx }/widgets/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=default"></script>
	<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
<title>关注粉丝查看明细</title>
</head>
<body class="bodycolor">
<div class="line"></div>
<div class="frmContent">
	<form action="" name="frmId" id="frmId"  target="_self">
		<s:token></s:token>
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="42">
				<td rowspan="4" colspan="2">
				<table  border="0" cellpadding="0" cellspacing="0" style="width: 200px;" height="200">
					<tr>
						<td height="140" width="200">
							<img alt="" width="200" src="${weixinGzUserInfo.headimgurl }">
						</td>
					</tr>
				</table>
				 </td>
				<td class="formTableTdLeft" style="width: 120px;">昵称:&nbsp;</td>
				<td >${weixinGzUserInfo.nickname }</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft" style="width: 120px;">称呼:&nbsp;</td>
				<td >
					<c:if test="${weixinGzUserInfo.sex==1 }">
						先生
					</c:if>
					<c:if test="${weixinGzUserInfo.sex==2 }">
						女士
					</c:if>
				</td>
			</tr>
			
			<tr height="42">
				<td class="formTableTdLeft" style="width: 120px;">所在区域:&nbsp;</td>
				<td>
					${weixinGzUserInfo.country }${weixinGzUserInfo.province }${weixinGzUserInfo.city }
				</td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft" style="width: 120px;">关注状态:&nbsp;</td>
				<td >
					<c:if test="${weixinGzUserInfo.eventStatus==1 }">
						<span style="color:green;">已关注</span>
					</c:if>
					<c:if test="${weixinGzUserInfo.eventStatus==2 }">
						<span style="color:red;">取消关注</span>
					</c:if>
				</td>
			</tr>
			 <tr height="32">
				<td class="formTableTdLeft" style="width: 120px;">语言:&nbsp;</td>
				<td>${weixinGzUserInfo.language }</td>
				<td class="formTableTdLeft" style="width: 120px;">关注时间:&nbsp;</td>
				<td><fmt:formatDate value="${weixinGzUserInfo.addtime }" pattern="yyyy-MM-dd HH:ss"/></td>
			</tr>
			<tr height="42">
			    <td class="formTableTdLeft" style="width: 120px;">标识符:&nbsp;</td>
				<td >${ weixinGzUserInfo.openid}</td>
			</tr> 
			
		</table>
	</form>
	<div class="formButton">
	    <a href="javascript:void(-1)"	onclick="art.dialog.close();" class="but butCancle">关&nbsp;&nbsp;闭</a>
	</div>
	</div>
</body>
</html>
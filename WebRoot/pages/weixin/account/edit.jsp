<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>公众账号设置添加</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link href="${ctx }/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"	class="skin-color" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
		<div id="breadcrumb">
			<a href="${ctx }/main/index" title="微商城中心" class="tip-bottom"><i
				class="icon-home"></i>微商城中心</a> 
				<a href="javascript:void(-1)" class="current">
					<c:if test="${weixinAccount.dbid>0 }" var="status">编辑公众账号</c:if>
					<c:if test="${status==false }">添加公众账号</c:if>
				</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-content nopadding">
							<form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
								<input type="hidden" value="${weixinAccount.dbid }" id="dbid" name="weixinAccount.dbid">
								<input type="hidden" value="${weixinAccount.userDbid }" id="userDbid" name="weixinAccount.userDbid">
								<input type="hidden" value="${weixinAccount.addtoekntime }" id="addtoekntime" name="weixinAccount.addtoekntime">
								<div class="control-group">
									<label class="control-label">名称：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinAccount.accountname"	id="accountname" value="${weixinAccount.accountname }" checkType="string,1,50"  tip="请输入公众号名称！"  />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">帐号TOKEN：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinAccount.accounttoken"	id="accounttoken" value="${weixinAccount.accounttoken }" checkType="string,1,500"  tip="请输入公招TOKEN！"  />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">公众微信号：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinAccount.accountnumber"	id="accountnumber" value="${weixinAccount.accountnumber }" checkType="string,1,500"  tip="请输入公众微信号！"  />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">原始ID：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinAccount.weixinAccountid"	id="weixinAccountid" value="${weixinAccount.weixinAccountid }" checkType="string,1,500"  tip="请输入公众原始ID！"  />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">公众号类型：</label>
									<div class="controls">
										<select id="accounttype" name="weixinAccount.accounttype" checkType="string,1,20"  tip="请选择公众号类型！">
											<option value="1" ${weixinAccount.accounttype==1?'selected="selected"':'' }>服务号</option>
											<option value="2" ${weixinAccount.accounttype==2?'selected="selected"':'' }>订阅号</option>
										</select>
									</div>
								</div>
								  <div class="control-group">
									<label class="control-label">电子邮箱：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinAccount.accountemail"	id="accountemail" value="${weixinAccount.accountemail }" checkType="string,1,500"  tip="请输入电子邮箱！"  />
									</div>
								</div>
								  <div class="control-group">
									<label class="control-label">公众帐号描述：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinAccount.accountdesc"	id="accountdesc" value="${weixinAccount.accountdesc }" />
									</div>
								</div>
								  <div class="control-group">
									<label class="control-label">公众帐号APPID：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinAccount.accountappid"	id="accountappid" value="${weixinAccount.accountappid }" checkType="string,1,500"  tip="请输入公众帐号APPID！"  />
										<span style="color: red">AppID(应用ID)</span>
									</div>
								</div>
								  <div class="control-group">
									<label class="control-label">公众帐号APPSECRET：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinAccount.accountappsecret"	id="accountappsecret" value="${weixinAccount.accountappsecret }" checkType="string,1,500"  tip="请输入公众帐号APPSECRET！"  />
										<span style="color: red">AppSecret(应用密钥)</span>
									</div>
								</div>
								<c:if test="${empty(weixinAccount) }">
								  <div class="control-group">
									<label class="control-label">Code：</label>
									<div class="controls">
										<input readonly="readonly" class="input-xlarge" name="weixinAccount.code"	id="code" value="${code }" checkType="string,1,500"  />
									</div>
								</div>
								</c:if>
								<c:if test="${!empty(weixinAccount) }">
								  <div class="control-group">
									<label class="control-label">Code：</label>
									<div class="controls">
										<input readonly="readonly" class="input-xlarge" name="weixinAccount.code"	id="code" value="${weixinAccount.code }" checkType="string,1,500"  />
										<span style="color: red">用于配置微信公众平台服务器连接地址：http://www.XXX.com/wechatAuth/auth?code=XXX</span>
									</div>
								</div>
								</c:if>
								<div class="form-actions">
									<a href="javascript:void(-1)" class="btn btn-primary"	onclick="$.utile.submitForm('frmId','${ctx}/weixinAccount/save')">
										<i class="icon-ok-sign icon-white"></i>保存
									</a> <a class="btn btn-inverse" onclick="window.history.go(-1)">
									<i	class="icon-hand-left icon-white"></i>返回</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
	</div>

<script src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script src="${ctx }/widgets/bootstrap/jquery.ui.custom.js"></script>
<script src="${ctx }/widgets/bootstrap/bootstrap.min.js"></script>
<script src="${ctx }/widgets/bootstrap/jquery.uniform.js"></script>
<script src="${ctx }/widgets/bootstrap/select2.min.js"></script>
<script src="${ctx }/widgets/bootstrap/unicorn.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/easy_validator.pack2.js"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript" src="${ctx }/widgets/ckeditor/ckeditor.js"></script>
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/swfupload.js"></script>
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/plugins/swfupload.queue.js"></script>
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/plugins/swfupload.speed.js"></script>
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/js/fileupload.handlers.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript">
$().ready(function() {
	var message="${message}";
	if(null!=message&&message!=""){
		alert(message);
		window.history.go(-1);
		return false;
	}
});
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>文本消息添加</title>
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
					<c:if test="${weixinTexttemplate.dbid>0 }" var="status">编辑文本消息</c:if>
					<c:if test="${status==false }">添加文本消息</c:if>
				</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-content nopadding">
							<form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
								<input type="hidden" value="${weixinTexttemplate.dbid }" id="dbid" name="weixinTexttemplate.dbid">
								<input type="hidden" value="${weixinTexttemplate.addtime }" id="addtime" name="weixinTexttemplate.addtime">
								<div class="control-group">
									<label class="control-label">模板名称：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="weixinTexttemplate.templatename"	id="templatename" value="${weixinTexttemplate.templatename }" checkType="string,1,50"  tip="请输入模板名称"  />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">模板内容：</label>
									<div class="controls">
										<textarea  class="input-xlarge" style="width: 60%;min-height: 320px;" name="weixinTexttemplate.content"	>${weixinTexttemplate.content }</textarea>
									</div>
								</div>
								<div class="form-actions">
									<a href="javascript:void(-1)" class="btn btn-primary"	onclick="$.utile.submitForm('frmId','${ctx}/weixinTexttemplate/save')">
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
	//var editor=CKEDITOR.replace("content");
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

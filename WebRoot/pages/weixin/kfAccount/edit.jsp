<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>客服管理设置添加</title>
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
					<c:if test="${kfAccount.dbid>0 }" var="status">编辑客服管理</c:if>
					<c:if test="${status==false }">添加客服管理</c:if>
				</a>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-content nopadding">
							<form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
								<input type="hidden" value="${kfAccount.dbid }" id="dbid" name="kfAccount.dbid">
								<div class="control-group">
									<label class="control-label">头像：</label>
									<div class="controls">
										<c:if test="${empty(kfAccount) }">
											<img src="${ctx }/images/weixin/avatar-medium.png" alt="" height="80" width="80" id="headimage_id1" tip="请上传头像">
										</c:if> 
										<c:if test="${!empty(kfAccount) }">
											<c:if test="${empty(kfAccount.headImg) }">
												<img src="${ctx }/images/weixin/avatar-medium.png" alt="" height="80" width="80" id="headimage_id1" tip="请上传头像">
											</c:if>
											<c:if test="${!empty(kfAccount.headImg) }">
												<img src="${kfAccount.headImg}" alt="" height="80" width="80" id="headimage_id1" tip="请上传头像">
											</c:if>
										</c:if> 
										<span id="spanButtonPlaceholder1"></span> <br />
										<input id="fileUpload" type="hidden" name="kfAccount.headImg" value="${kfAccount.headImg}"  >
										<br>
										<p>(jpg格式，建议大小:640*640，24小时后生效)</p>
										<div id="uploadFileContent" class="uploadFileContent" style="width: 200px"></div>
									</div>
									
								</div>
								<div class="control-group">
									<label class="control-label">工号：</label>
									<c:set value="${fn:indexOf(kfAccount.kfAccount,'@') }" var="index"></c:set>
									<c:set value="${fn:substring(kfAccount.kfAccount,0,index) }" var="kfAccou"></c:set>
									<c:if test="${empty(kfAccount) }">
										<div class="controls">
											<input type="text" class="input-xlarge" name="kfAccount.kfAccount"	id="kfAccount" value="${kfAccou}" url="${ctx }/kfAccount/validateKfAccount"  checkType="string,1,50"  tip="工号不能为空！"  />@${weixinAccount.accountnumber }
											<p>工号不能重复，一旦输入不能修改，由字母、数字组成</p>
										</div>
									</c:if>	
									<c:if test="${!empty(kfAccount) }">
										<div class="controls">
											<input type="hidden" class="input-xlarge" name="kfAccount.kfAccount"	id="kfAccount" value="${kfAccou}" checkType="string,1,50"  tip="工号不能为空！"  />
											${kfAccount.kfAccount }
										</div>
									</c:if>	
								</div>
								<div class="control-group">
									<label class="control-label">昵称：</label>
									<div class="controls">
										<input type="text" class="input-xlarge" name="kfAccount.nickname"	id="nickname" value="${kfAccount.nickname }" checkType="string,1,500"  tip="请输入昵称，昵称不能为空"  />
									</div>
								</div>
								<c:if test="${empty(kfAccount) }">
									<div class="control-group">
										<label class="control-label">密码：</label>
										<div class="controls">
											<input type="password" class="input-xlarge" name="kfAccount.password"	id="password" value="${kfAccount.password }" checkType="string,6,16"  tip="请输入密码!"  />
											<p>请输入6-16位的密码</p>
										</div>
									</div>
								</c:if>
								<c:if test="${!empty(kfAccount) }">
									<div class="control-group">
										<label class="control-label">新密码：</label>
										<div class="controls">
											<input type="password" class="input-xlarge" name="kfAccount.password"	id="password" value="123456" checkType="string,6,16"  tip="请输入密码!"  />
											<p>请输入6-16位的密码</p>
										</div>
									</div>
								</c:if>
								<div class="form-actions">
									<a href="javascript:void(-1)" class="btn btn-primary"	onclick="submitAjaxForm('frmId','${ctx}/kfAccount/save')">
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
var upload1;
window.onload = function() {
	upload1 = new SWFUpload(
			{
				// Backend Settings
				upload_url : "${ctx}/swfUpload/uploadFileCoupon",
				post_params : {
					"PHPSESSID" : "6a95034fff6ba3a6aa8a990ca3af42ee","userId":"${sessionScope.user.dbid}"
				},
				//上传文件的名称
				file_post_name : "file",

				// File Upload Settings
				file_size_limit : "5242880", // 200MB
				file_types : "*.jpg",
				file_types_description : "请上传jpg的图片",
				file_upload_limit : "100",
				file_queue_limit : "0",

				// Event Handler Settings (all my handlers are in the Handler.js file)
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueErrorHandler,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,

				// Button Settings
				button_image_url : "${ctx}/widgets/SWFUpload/images/XPButtonUploadText_61x22.png",
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width : 61,
				button_height : 22,

				// Flash Settings
				flash_url : "${ctx}/widgets/SWFUpload/Flash/swfupload.swf",

				custom_settings : {
					progressTarget : "uploadFileContent",
					cancelButtonId : "btnCancel1",
					titlePicture : "fileUpload",
					fileUploadImage : "fileUploadImage"
				},
				// Debug Settings
				debug : false
			});

}
//上传文件成功，修改状态、同时修改删除方法；获取后台传过来的url
function uploadSuccess(file, serverData) {
	var swfUpload = this;
	var customSettings=swfUpload.customSettings;
	try {
		//第一步serverData返回数据
		var fileSize=SWFUpload.speed.formatBytes(file.size);
		if(null!=serverData&&undefined!=serverData){
			var serData=serverData.split("|");
			var src=$("#headimage_id1").attr("src");
			if(serData[0]=="success"){//上传文件成功
				$("#fileUpload").val(serData[1]);
				$("#headimage_id1").attr("src",serData[1]);
				
				var uploadState="<span id='fileSize'>文件大小："+fileSize+"</span><span id='state' class='stateSuccess'>上传成功</span>";
				$("#uploadState"+file.id).text("");
				$("#uploadState"+file.id).append(uploadState);
				$("#method"+file.id).show("fast");
				//从新绑定删除方法
				$("#method"+file.id).unbind("click");
				$("#method"+file.id).bind("click",function(e){
					$.post('${ctx}/swfUpload/deleteFile?fileUrl='+encodeURI(encodeURI(serData[1]))+"&timeStamp="+new Date(),function(data){
						if(data=="success"){
							var event=e;
							var obj=upload1;
							obj.cancelQueue();
							$("#fileUpload").val("");
							$("#headimage_id1").attr("src",src);
							totalNum=totalNum-1;
							if(totalNum<=0){//没有文件时，清空总的统计数据
								$("#fileContent" + file.id).slideUp('fast');
							}else{//从新更新数据
								$("#uploadTotalProgress").text();
								$("#fileContent" + file.id).slideUp('fast');
							}
						}if(data=="failed"){
							var uploadState="<span id='fileSize'>文件大小："+fileSize+"</span><span id='state' class='stateSuccess'>文件删除失败</span>";
							$("#uploadState"+file.id).text("");
							$("#uploadState"+file.id).append(uploadState);
							//绑定上传文件队列删除方法
							$("#method"+file.id).unbind("click");
							$("#method"+file.id).bind("click",function(e){
								if(totalNum<=0){//没有文件时，清空总的统计数据
									$("#fileContent" + file.id).slideUp('fast');
								}else{//从新更新数据
									$("#fileContent" + file.id).slideUp('fast');
								}
							});
						}
					});
				});
			}if(serData[0]=="failed"){//上传文件失败
				var uploadState="<span id='fileSize'>文件大小："+fileSize+"</span><span id='state' class='stateSuccess'>上传失败</span>";
				$("#uploadState"+file.id).text("");
				$("#uploadState"+file.id).append(uploadState);
				//绑定上传文件队列删除方法
				$("#method"+file.id).unbind("click");
				$("#method"+file.id).bind("click",function(e){
					if(totalNum<=0){//没有文件时，清空总的统计数据
						$("#fileContent" + file.id).slideUp('fast');
					}else{//从新更新数据
						$("#fileContent" + file.id).slideUp('fast');
					}
				});
			}
		}else{
			this.debug("文件上传错误！返回位置错误！");
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function submitAjaxForm(frmId, url, state) {
	var target = $("#" + frmId).attr("target") || "_self";
	try {
		if (undefined != frmId && frmId != "") {
			var validata = validateForm(frmId);
			if (validata == true) {
				var params = getParam(frmId, state);
				var url2="";
				$.ajax({	
					url : url, 
					data : params, 
					async : false, 
					timeout : 20000, 
					dataType : "json",
					type:"post",
					success : function(data, textStatus, jqXHR){
						//alert(data.message);
						var obj;
						if(data.message!=undefined){
							obj=$.parseJSON(data.message);
						}else{
							obj=data;
						}
						if(obj[0].mark==1){
							//错误
							$.utile.tips(obj[0].message);
							$(".btn-primary").attr("onclick",url2);
							return ;
						}else if(obj[0].mark==0){
							$.utile.tips(data[0].message);
							if (target == "_self") {
								setTimeout(
										function() {
											window.location.href = obj[0].url
										}, 1000);
							}
							if (target == "_parent") {
								// 同时关闭弹出窗口
								var parent = window.parent;
								window.parent.frames["contentUrl"].location=obj[0].url;
							}
							// 保存数据成功时页面需跳转到列表页面
						}
					},
					complete : function(jqXHR, textStatus){
						$(".btn-primary").attr("onclick",url2);
						var jqXHR=jqXHR;
						var textStatus=textStatus;
					}, 
					beforeSend : function(jqXHR, configs){
						url2=$(".btn-primary").attr("onclick");
						$(".btn-primary").attr("onclick","");
						var jqXHR=jqXHR;
						var configs=configs;
					}, 
					error : function(jqXHR, textStatus, errorThrown){
							$.utile.tips("系统请求超时");
							$(".btn-primary").attr("onclick",url2);
					}
				});
			} else {
				return;
			}
		} else {
			return;
		}
	} catch (e) {
		$.utile.tips(e);
		return;
	}
}
</script>
</body>
</html>

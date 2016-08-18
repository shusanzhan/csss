<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.ystech.weixin.model.WechatMedia"%>
<%@page import="java.io.File"%>
<%@page import="com.ystech.core.util.PathUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp" %>
<%@ page language="java" import="java.util.*,java.io.File" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx }/css/imageUpload.css"/>
<link rel="stylesheet" href="${ctx }/widgets/bootstrap3/css/bootstrap.min.css"/>
	<script src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<title>文件上传组件</title>
</head>
<style>
 .imagea{
 	display: block;margin: 6px;border: 3px solid #FFF;cursor: pointer;
 }
 .imagea:HOVER {
	border:3px solid #1094fa;
}

#online {
    padding: 10px 0 0;
    width: 100%;
}
#online #imageList {
    height: 100%;
    overflow-x: hidden;
    overflow-y: auto;
    position: relative;
    width: 100%;
}

#online ul {
    display: block;
    list-style: outside none none;
    margin: 0;
    padding: 0;
}

#online li {
    background-color: #eee;
    cursor: pointer;
    display: block;
    float: left;
    height: 120px;
    list-style: outside none none;
    margin: 0 0 9px 9px;
    overflow: hidden;
    padding: 0;
    position: relative;
    width: 120px;
}
#online li{
	border: 3px solid #FFF;cursor: pointer;
}
#online li:HOVER{
	border:3px solid #1094fa;
}
#online li.selected .icon {
    background-image: url("${ctx}/pages/compoent/success.png");
    background-position: 80px 80px;
}
#online li .icon {
    background-repeat: no-repeat;
    border: 0 none;
    cursor: pointer;
    height: 120px;
    left: 0;
    position: absolute;
    top: 0;
    width: 120px;
    z-index: 2;
}
</style>
<body>

<div class="bs-docs-section">
  <div class="bs-example bs-example-tabs">
    <ul id="myTab" class="nav nav-tabs" role="tablist">
      	<li class="active"><a href="#home" role="tab" data-toggle="tab">上传本地文件</a></li>
    </ul>
    <div id="myTabContent" class="tab-content">
	      <div class="tab-pane fade in active" id="home">
	        <div  class="upload">
					<div style="padding-left: 5px;">
						<span id="spanButtonPlaceholder1"></span> <br />
					</div>
					<div id="uploadFileContent" class="uploadFileContent" style="width: 200px"></div>
				</div>
				<div style="">
					<input type="hidden"  name="brand.logo" id="fileUpload" readonly="readonly"	>
					<img alt="" id="fileUploadImage" width="80" height="60">
				</div>
	      </div>
      </div>
    </div>
  </div><!-- /example -->
<script src="${ctx }/widgets/bootstrap3/jquery.min.js"></script>
<script src="${ctx }/widgets/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/swfupload.js"></script>
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/plugins/swfupload.queue.js"></script>
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/plugins/swfupload.speed.js"></script>
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/js/fileupload.handlers.js"></script>
<script type="text/javascript">
var upload1;
window.onload = function() {
	upload1 = new SWFUpload(
			{
				// Backend Settings
				upload_url : "${ctx}/wechatMedia/uploadFileMidia",
				post_params : {
					"PHPSESSID" : "6a95034fff6ba3a6aa8a990ca3af42ee","userId":"${sessionScope.user.dbid}"
				},
				//上传文件的名称
				file_post_name : "file",

				// File Upload Settings
				file_size_limit : "5242880", // 200MB
				file_types : "*.*",
				file_types_description : "All Files",
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
$('#myTab a').click(function (e) {
  e.preventDefault()
  $(this).tab('show')
})
//上传文件成功，修改状态、同时修改删除方法；获取后台传过来的url
function uploadSuccess(file, serverData) {
	var swfUpload = this;
	var customSettings=swfUpload.customSettings;
	try {
		//第一步serverData返回数据
		var fileSize=SWFUpload.speed.formatBytes(file.size);
		var serverData2=$.parseJSON(serverData);
		if(null!=serverData2&&undefined!=serverData2){
			if(serverData2.result=="success"){//上传文件成功
				$("#fileUpload").val(serverData);
				$("#fileUploadImage").attr("src",serverData2.thumbUrl);
				var uploadState="<span id='fileSize'>文件大小："+fileSize+"</span><span id='state' class='stateSuccess'>上传成功</span>";
				$("#uploadState"+file.id).text("");
				$("#uploadState"+file.id).append(uploadState);
				$("#method"+file.id).show("fast");
				//从新绑定删除方法
				$("#method"+file.id).unbind("click");
				$("#method"+file.id).bind("click",function(e){
					$.post('${ctx}/swfUpload/deleteFile?fileUrl='+encodeURI(encodeURI(returnResult[1]))+"&timeStamp="+new Date(),function(data){
						if(data=="success"){
							var event=e;
							var obj=upload1;
							obj.cancelQueue();
							$(".default-tip").show();
							$("#imgArea").hide();
							$("#fileUpload").val("");
							$(".cover .i-img").hide()
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
			}if(serverData2.result=="failed"){//上传文件失败
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
</script>	
</body>
</html>
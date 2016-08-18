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
<link rel="stylesheet" href="${ctx }/wxcss/css/block.css"/>
<link rel="stylesheet" href="${ctx }/wxcss/css/block-mul.css"/>
<link rel="stylesheet" href="${ctx }/wxcss/css/global.css?da=${now}"/>
<link rel="stylesheet" href="${ctx }/wxcss/css/font-awesome.min.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<h4 class="headline"><i class="fa fa-home"></i> 新增单图文 <span class="line"></span> </h4>
<div class="block-wrap">
	<div class="pull-left msg-preview" style="width:330px;">
      <div class="msg-item-wrapper">
        <div id="appmsgItem1" class="msg-item">
          <h4 class="msg-t"> <span class="i-title">${weixinNewsitem.title }</span> </h4>
          <p class="msg-meta"> <span class="msg-date">${weixinNewsitem.createDate }</span> </p>
          <div class="cover">
          		<c:if test="${empty(weixinNewsitem.imagepath) }">
	            	<p class="default-tip" style="">封面图片</p>
	            </c:if>
          		<c:if test="${!empty(weixinNewsitem.imagepath) }">
	            	<p class="default-tip" style="display: none;">封面图片</p>
	            </c:if>
	            <img class="i-img" src="${weixinNewsitem.imagepath }" id="fileUploadImage"> </div>
	          <p class="i-summary"></p>
        </div>
        <div class="msg-hover-mask"></div>
        <div class="msg-mask"> <span class="dib msg-selected-tip"></span> </div>
      </div>
    </div>
    <div class="pull-left" style="width: 60%;">
      <div class="msg-editer-wrapper" style="width: 100%;">
        <div class="msg-editer" style="width: 100%;">
          <form id="frmId" name="frmid" method="post">
          <input type="hidden" id="dbid" name="weixinNewsitem.dbid" value="${weixinNewsitem.dbid }"> 
          <input type="hidden" id="dbid" name="weixinNewsitem.createDate" value="${weixinNewsitem.createDate }"> 
          <input type="hidden" id="weixinNewstemplateDbid" name="weixinNewstemplateDbid" value="${weixinNewstemplate.dbid }"> 
          <input type="hidden" id="type" name="type" value="1"> 
          <div class="msg-content" style="width: 100%;">
	          <div class="control-group">
				<label class="control-label">
	            标题<span class="maroon">*</span><span class="help-inline">(不能超过64个字)</span></label>
	            <div class="controls">
	                <input type="text" id="title" name="weixinNewsitem.title" class="ui-input" value="${ weixinNewsitem.title}" checkType="string,1" tip="输入标题"/>
	            </div>
	          </div>
            <label>作者<span class="help-inline">(选填)</span></label>
            <div class="msg-item msg-input">
                <input type="text" id="author" name="weixinNewsitem.author" value="${weixinNewsitem.author }" class="ui-input" />
            </div>
            <label>封面<span class="maroon">*</span><span class="help-inline">(大图片建议尺寸：900像素 * 500像素)</span></label>
            <div class="msg-item msg-input">
                <div class="cover-area">
                  <div class="cover-hd">
                  		<span id="spanButtonPlaceholder1"></span> <br />
                     	<input id="fileUpload" type="hidden" name="weixinNewsitem.imagepath" value="${weixinNewsitem.imagepath }">
                  </div>
                  <div id="uploadFileContent" class="uploadFileContent" style="width: 200px"></div>
                  <!-- <p id="upload-tip" class="upload-tip">大图片建议尺寸：700像素 * 300像素</p> -->
                 <c:if test="${empty(weixinNewsitem.imagepath) }">
	                  <p id="imgArea" class="cover-bd" style="display: none;">
	                    <img src="${weixinNewsitem.imagepath }" id="cover_view">
	                  </p>
                  </c:if>
                 <c:if test="${!empty(weixinNewsitem.imagepath) }">
	                  <p id="imgArea" class="cover-bd" >
	                    <img src="${weixinNewsitem.imagepath }" id="cover_view">
	                    <a href="javascript:;" class="vb cover-del" id="delImg">删除</a>
	                  </p>
                  </c:if>
                  <c:if test="${empty(weixinNewsitem) }">
        		  	<input type="checkbox" name="weixinNewsitem.coverShow" value="1" id="coverShow" checked="checked" ><span class="help-inline" style="line-height: 32px;">封面图片显示在正文中 </span> 
                  </c:if>
                  <c:if test="${!empty(weixinNewsitem) }">
	        		  <label class="checkbox inline">
	        		  	<input type="checkbox" name="weixinNewsitem.coverShow" value="1" id="coverShow" ${weixinNewsitem.coverShow==1?'checked="checked"':'' } ><span class="help-inline" style="line-height: 32px;">封面图片显示在正文中 </span> 
	        		  </label>
        		  </c:if>
                </div>
            </div>
            <c:if test="${empty(weixinNewsitem.description) }">
	            <a id="desc-block-link" class="block mt10 mb10">添加摘要</a>
	            <div id="desc-block" style="display:none;">
	              <label>摘要<span class="help-inline">(建议不要超过120个字)</span></label>
	              <div class="msg-item msg-text">
	                  <textarea id="summary" class="ui-input" name="weixinNewsitem.description" rows="3"></textarea>
	              </div>
	            </div>
            </c:if>
            <c:if test="${!empty(weixinNewsitem.description) }">
	            <div id="desc-block" >
	              <label>摘要<span class="help-inline">(建议不要超过120个字)</span></label>
	              <div class="msg-item msg-text">
	                  <textarea id="summary" class="ui-input" name="weixinNewsitem.description" rows="3">${weixinNewsitem.description }</textarea>
	              </div>
	            </div>
            </c:if>
             <div class="control-group">
					<label class="control-label">
            		正文<span class="maroon">*</span><span class="help-inline">(不能超过20000个字)</span></label>
		            <div class="msg-item msg-text">
		                <textarea id="content" name="contentArea" checkType="string,1" tip="请输入正文">
		                	<c:if test="${empty(weixinNewsitem.content) }">
				                <p>这是正文</p>
		                	</c:if>
		                	<c:if test="${!empty(weixinNewsitem.content) }">
				                ${weixinNewsitem.content }
		                	</c:if>
		               	</textarea>
		            </div>
		      </div>
            <a id="url-block-link" class="block mt10 mb10">添加原文链接</a>
            <c:if test="${empty(weixinNewsitem.url) }">
	            <div id="url-block" style="display:none;">
	              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
	              <div class="msg-item msg-input">
	                  <input type="text" class="ui-input" id="url" name="weixinNewsitem.url" value="${weixinNewsitem.url }"/>
	              </div>
	            </div>
            </c:if>
            <c:if test="${!empty(weixinNewsitem.url) }">
	            <div id="url-block" >
	              <label>原文链接<span class="help-inline">(请输入正确的URL链接格式)</span></label>
	              <div class="msg-item msg-input">
	                  <input type="text" class="ui-input" name="weixinNewsitem.url" value="${weixinNewsitem.url }"/>
	              </div>
	            </div>
            </c:if>
             <div id="url-block" >
	              <label>排序<span class="help-inline">(请输入排序号)</span></label>
	              <div class="msg-item msg-input">
	                  <input type="text" class="ui-input" id="orders" name="weixinNewsitem.orders" value="${weixinNewsitem.orders }"/>
	              </div>
	            </div>
            <div class="msg-item msg-button">
                <a type="submit" class="btn btn-primary" onclick="smbFm()" >保  存</a>
                 <a class="btn btn-inverse" onclick="window.history.go(-1)">
				<i	class="icon-hand-left icon-white"></i>返回</a>
            </div>
          </div>
          </form>
        </div>  
        <span class="abs msg-arrow a-out" style="margin-top: 0px;"></span> 
        <span class="abs msg-arrow a-in" style="margin-top: 0px;"></span>          
      </div>
    </div>
</div>

<script src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script src="${ctx }/widgets/bootstrap/jquery.ui.custom.js"></script>
<script src="${ctx }/widgets/bootstrap/bootstrap.min.js"></script>
<script src="${ctx }/widgets/bootstrap/jquery.cookie.js"></script>
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
<script type="text/javascript"	src="${ctx }/widgets/SWFUpload/js/fileupload.handlers2.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script src="${ctx }/wxcss/js/global.js"></script>
<script src="${ctx }/wxcss/js/weixin.block.js"></script>
<script type="text/javascript">
//上传文件成功，修改状态、同时修改删除方法；获取后台传过来的url
function uploadSuccess(file, serverData) {
	var swfUpload = this;
	var customSettings=swfUpload.customSettings;
	try {
		//第一步serverData返回数据
		var fileSize=SWFUpload.speed.formatBytes(file.size);
		if(null!=serverData&&undefined!=serverData&&serverData.length>0){
			var returnResult=serverData.split("|");
			if(returnResult[0]=="success"){//上传文件成功
				$("#imgArea").show();
				$(".default-tip").hide();
				$("#fileUploadImage").show()
				$("#fileUpload").val(returnResult[1]);
				$("#fileUploadImage").attr("src",returnResult[1]);
				$("#cover_view").attr("src",returnResult[1]);
				
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
			}if(returnResult[0]=="failed"){//上传文件失败
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
$(document).ready(function(){
	//富文本插件
	var editor=CKEDITOR.replace("content");
});
var upload1;
window.onload = function() {
	upload1 = new SWFUpload(
			{
				// Backend Settings
				upload_url : "${ctx}/swfUpload/uploadFile",
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
function smbFm(){
	var fileUpload=$("#fileUpload").val();
	var url=$("#url").val();
	if(null==fileUpload||fileUpload==""){
		alert("请先上传封面图片！");
		return false;
	}
	if(null!=url&&url!=""){
		var stu=checkWebUrl(url);
		if(stu==false){
			alert("请输正确的超链接格式！");
			return false;
		}
	}
	$.utile.submitForm('frmId','${ctx}/weixinNewsItem/save',true);
}
function checkWebUrl(obj){
	var value = obj.attr("value");
	if (obj.attr('canEmpty') == "Y" && value.length == 0)
		return true;
	var strRegex = "^((https|http|ftp|rtsp|mms)?://)"  
		        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
		        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
		        + "|" // 允许IP和DOMAIN（域名） 
		       + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.  
		        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名  
		        + "[a-z]{2,6})" // first level domain- .com or .museum  
		        + "(:[0-9]{1,4})?" // 端口- :80  
		        + "((/?)|" // a slash isn't required if there is no file name  
		        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
		       var re=new RegExp(strRegex); 
       if (!re.test(value)) {
   		return false;
       }
}
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>绑定微信</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link href="${ctx }/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"	class="skin-color" />
<link rel="stylesheet" href="${ctx}/css/common.css"	class="skin-color" />
<link rel="stylesheet" href="${ctx }/wxcss/css/block.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">

</style>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-content nopadding">
					<form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId" target="_parent">
						<input type="hidden" value="${kfAccount.dbid }" id="kfAccountId" name="kfAccountId">
						<div class="control-group">
							<label class="control-label">客户号：</label>
								<div class="controls">
									${kfAccount.kfAccount}
								</div>
						</div>
						<div class="control-group">
							<label class="control-label">绑定微信：</label>
							<div class="controls">
								<input type="text" class="input-large" name="kfWx"	id="kfWx" value="${kfAccount.kfWx }" checkType="string,1,500"  tip="请输入绑定微信，微信不能为空"  style="width: 50%;"/>
							</div>
						</div>
						<div class="form-actions">
							<a href="javascript:void(-1)" class="btn btn-primary"	onclick="if(valid()){submitAjaxForm('frmId','${ctx}/kfAccount/saveInviteworker')}">
								<i class="icon-ok-sign icon-white"></i>绑定微信
							</a> 
							<a class="btn btn-inverse" onclick="art.dialog.close()">
								<i	class="icon-hand-left icon-white"></i>取消</a>
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
function valid(){
	var content=$("#content").val();
	if(null!=content&&content==''){
		alert("请填日志介绍!");
		return false;
	}
	return true;
}
function sendText(v){
	var content=$(v).text();
	var length=content.length;
	if(600-length>0){
		var html=$(".js_editorTip").html();
		if(html.indexOf("超过")!=-1){
			html=html.replace("已经超过","还可以输入");
			$(".js_editorTip").text("");
			$(".js_editorTip").append(html);
		}
		$(".js_editorTip").find("em").css("color","").text(600-length);
	}else{
		var html=$(".js_editorTip").html();
		if(html.indexOf("输入")!=-1){
			html=html.replace("还可以输入","已经超过");
			$(".js_editorTip").text("");
			$(".js_editorTip").append(html);
		}
		$(".js_editorTip").find("em").css("color","red").text(length-600);
	}
	$("#content").val("");
	$("#content").val(content);
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
							// 同时关闭弹出窗口
							var parent = window.parent;
							window.parent.frames["contentUrl"].location=obj[0].url;
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

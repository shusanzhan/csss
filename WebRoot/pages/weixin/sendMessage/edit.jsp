<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>群发消息</title>
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
		<div id="breadcrumb">
			<a href="${ctx }/main/index" title="微商城中心" class="tip-bottom"><i
				class="icon-home"></i>微商城中心</a> 
				<a href="javascript:void(-1)" class="current">
					群发消息
				</a>
		</div>
		<br>
		<div id="js_msgSender" class="msg_sender" style="margin: 0 30px; border: 0px  ">
			<div class="control-group" style="float: left;">
				<label class="control-label">群发对象</label>
				<div class="controls">
					<div class="btn-group">
					  <button id="addToGrop" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">请选择群发对象... <span class="caret"></span></button>
					  <ul class="dropdown-menu">
					  	<li><a href="javascript:void(-1)" onclick="selectGroup(0,'全部用户')">全部用户</a></li>
					  	<c:forEach var="weixinGroup" items="${weixinGroups }">
							<li><a href="javascript:void(-1)" onclick="selectGroup(${weixinGroup.dbid},'${weixinGroup.name }')">${weixinGroup.name }</a></li>
						</c:forEach>
					  </ul>
					</div><!-- /btn-group -->
				</div>
			</div>
			<div class="control-group" style="float: left;margin-left: 12px;">
				<label class="control-label">推广渠道</label>
				<div class="controls">
					<div class="btn-group">
					  <button id="addToSpreadDetail" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">请选推广渠道对象... <span class="caret"></span></button>
					  <ul class="dropdown-menu">
					  	<li><a href="javascript:void(-1)" onclick="selectSpreadDetail(0,'全部用户')">全部用户</a></li>
					  	<c:forEach var="spreadDetail" items="${spreadDetails }">
							<li><a href="javascript:void(-1)" onclick="selectSpreadDetail(${spreadDetail.dbid},'${spreadDetail.name }')">${spreadDetail.name }</a></li>
						</c:forEach>
					  </ul>
					</div><!-- /btn-group -->
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="js_msgSender" class="msg_sender" style="margin: 0 30px; border: 1px solid #e7e7eb; ">
			<div class="msg_tab">
				 <ul class="tab_navs">
				 	 <li class="tab_nav tab_appmsg width5 selected" style=" background-color: #fff;">
					    <a onclick="return false;" href="javascript:void(0);">&nbsp;<i class="icon_msg_sender"></i><span class="msg_tab_title">图文消息</span></a>
				    </li>
				    <li class="tab_nav tab_text width5 ">
					    <a onclick="return false;" href="javascript:void(0);">&nbsp;<i class="icon_msg_sender"></i><span class="msg_tab_title">文字</span></a>
				    </li>
				 </ul>
				 <div class="tab_panel">
				 	<div class="tab_content inner">
				 		<div class="js_appmsgArea inner" >
					 		<div class="tab_cont_cover jsMsgSendTab" data-index="0" id="tab_cont_cover" style="${empty(wechatNewsTemplate)==true?'':'display: none;' }">
					 			<div class="media_cover">
					 				<span class="create_access">
					 					<a class="add_gray_wrp jsMsgSenderPopBt" data-index="0" data-type="10" href="javascript:;" onclick="selectNewsItem('2332')">
					 						<i class="icon36_common add_gray"></i>
					 						<strong>从素材库中选择</strong>
					 					</a>
					 				</span>
					 			</div>
					 			<div class="media_cover">
					 				<span class="create_access">
					 					<a class="add_gray_wrp jsMsgSenderPopBt" onclick="window.location.href='${ctx }/wechatNewsItem/addMore'" data-index="0" data-type="10" href="javascript:;">
					 						<i class="icon36_common add_gray"></i>
					 						<strong>新建图文消息</strong>
					 					</a>
					 				</span>
					 			</div>
					 		</div>
					 		<c:if test="${!empty(wechatNewsTemplate)}">
					 			<c:if test="${wechatNewsTemplate.type==1 }">
							 	  <div class="msg-item-wrapper" id="msg-item-wrapper">
						               <div class="msg-item">
						                 <h4 class="msg-t"><a href="javascript:void(-1)" class="i-title" >${wechatNewsTemplate.title }</a></h4>
						                 <p class="msg-meta"><span class="msg-date">${fn:substring(wechatNewsTemplate.addtime,0,10) }</span></p>
						                 <c:forEach items="${wechatNewsTemplate.wechatNewsitems }" var="weixinNewsitem">
						                	 <div class="cover">
						                    <p class="default-tip" style="display:none">封面图片</p>
						                   	 	<img src="${weixinNewsitem.thumbUrl }" class="i-img" style=""> </div>
						                  	<p class="msg-text">${weixinNewsitem.digest }</p>
						                 </c:forEach>
						               </div>
						                <div class="appmsg_mask" style="display: none;"></div>
										<i class="icon_card_selected" style="display: none;">已选择</i>
						           </div>
						         </c:if>
						         <c:if test="${wechatNewsTemplate.type==2 }">
						         	<div class="msg-item-wrapper" id="msg-item-wrapper">
							                <div class="msg-item multi-msg">
							                	<c:forEach var="weixinNewsitem" items="${wechatNewsTemplate.wechatNewsitems }" varStatus="i">
							                		<c:if test="${i.index==0 }" var="status">
									                  <div class="appmsgItem">
									                    <h4 class="msg-t"><a href="javascript:void(-1)" class="i-title" >${weixinNewsitem.title }</a></h4>
									                     <p class="msg-meta"><span class="msg-date">${fn:substring(wechatNewsTemplate.addtime,0,10) }</span></p>
									                    <div class="cover">
									                      <p class="default-tip" style="display:none">封面图片</p>
									                      <img src="${weixinNewsitem.thumbUrl }" class="i-img" style=""> </div>
									                    <p class="msg-text"></p>
									                  </div>
								                  </c:if>
								                  <c:if test="${status==false }">
									                  <div class="rel sub-msg-item appmsgItem"> 
									                  	<span class="thumb"> <img src="${weixinNewsitem.thumbUrl }" class="i-img" style=""> </span>
									                    <h4 class="msg-t"> <a href="javascript:void(-1)"  class="i-title">${weixinNewsitem.title }</a> </h4>
									                  </div>
							                      </c:if>                                  	   
								               </c:forEach>
							               </div>
							              <div class="appmsg_mask" style="display: none;"></div>
							             <i class="icon_card_selected" style="display: none;">已选择</i>
						              </div>
						         </c:if>
						         <a  href="javascript:void(-1)" id="deleteMedia">删除</a>
					 		</c:if>
					 		<c:if test="${empty(wechatNewsTemplate) }">
						 		<div class="msg-item-wrapper" id="msg-item-wrapper" style="display: none;">
						 		</div>
					 			<a style="display: none;" href="javascript:void(-1)" id="deleteMedia">删除</a>
				 			</c:if>
				 		</div>
				 	</div>
				 	
				 	<div class="tab_content inner" style="display:none; padding: 0;">
				    	<div class="js_textArea inner no_extra">
					    	<div class="emotion_editor">
						    	<div contenteditable="true" onkeyup="sendText(this)" class="edit_area js_editorArea" style="overflow-y: auto; overflow-x: hidden;"></div>
							    <div class="editor_toolbar">
							        <!-- <a class="icon_emotion emotion_switch js_switch" href="javascript:void(0);">表情</a> -->
							        <p class="editor_tip js_editorTip" style="margin: 0">还可以输入<em>600</em>字</p>
							    </div>
							</div>
						</div>
				    </div>
				 </div>
			</div>
		</div>
		<div style="display: none; width: 540px;" id="templateId">
			<table id="noLine" border="0" align="center" cellpadding="0" cellspacing="0" style="width: 320px;margin-top: 5px;">
				<tr style="height: 40px;" height="20">
					<td id="messageError" colspan="3" style="text-align: left;color: red;" width="280">
						关注公众号后，才能接收图文消息预览
					</td>
				</tr>
				<tr style="height: 40px;" height="30" id="imageTr">
					<td colspan="3" style="text-align: left;" width="280">
						<input type="text" id="wechatId" name="wechatId" value="" placeholder="请输入微信号/QQ号/手机号" style="width: 320px;">
					</td>
				</tr>
				<tr style="height: 20px;" height="20">
					<td id="messageError" colspan="3" style="text-align: left;display: none;color: red;" width="280">
						填写名称错误！
					</td>
				</tr>
			</table>
		</div>
		<div id="js_msgSender" class="msg_sender" style="margin: 0 30px;margin-top:30px; border: 0px  ">
				<a href="javascript:void(-1)" class="btn btn-primary"	onclick="if(valid()){$.utile.submitForm('frmId','${ctx}/wechatSendMessage/save')}">
					<i class="icon-ok-sign icon-white"></i>群发</a>
				<a class="btn btn-success" onclick="preview()">
					<i class="icon-leaf icon-white"></i>预览
				</a> 
				<a class="btn btn-inverse" onclick="window.history.go(-1)">
				<i	class="icon-hand-left icon-white"></i>返回</a>
		</div>
	<form class="form-horizontal" method="post" action="" 	name="frmId" id="frmId">
		<c:if test="${empty(wechatNewsTemplate) }">
			<input type="hidden" value="" id="newsItemTemplateId" name="newsItemTemplateId">
		</c:if>
		<c:if test="${!empty(wechatNewsTemplate) }">
			<input type="hidden" value="${wechatNewsTemplate.dbid }" id="newsItemTemplateId" name="newsItemTemplateId">
		</c:if>
		<input type="hidden" value="" id="groupId" name="groupId">
		<input type="hidden" value="" id="spreadDetailId" name="spreadDetailId">
		<input type="hidden" value="mpnews" id="msgType" name="msgType" >
		<input type="hidden" value="" id="content" name="content" >
	</form>

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
	var groupId=$("#groupId").val();
	var spreadDetailId=$("#spreadDetailId").val();
	var msgType=$("#msgType").val();
	var newsItemTemplateId=$("#newsItemTemplateId").val();
	var content=$("#content").val();
	if((null==spreadDetailId||spreadDetailId=='')&&(null==groupId||groupId=='')){
		alert("请选择用户组或推广渠道");
		return false;
	}
	/* if(null!=groupId&&groupId==''){
		alert("请选择用户组");
		return false;
	} */
	if(msgType=='mpnews'){
		if(null!=newsItemTemplateId&&newsItemTemplateId==''){
			alert("请选择发送内容");
			return false;
		}
	}
	if(msgType=='text'){
		if(null!=content&&content==''){
			alert("请填写发送内容!");
			return false;
		}
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
$().ready(function() {
	var message="${message}";
	if(null!=message&&message!=""){
		alert(message);
		window.history.go(-1);
		return false;
	}
	$("#deleteMedia").bind("click",function(){
		 $("#tab_cont_cover").show();
        $("#deleteMedia").hide();
        $("#msg-item-wrapper").hide();
        $("#msg-item-wrapper").text("");
	})
	$(".tab_navs").find("li").each(function(i){
		$(this).bind("click",function(){
			$(".selected").removeClass("selected");
			if(i==0){
				$("#msgType").val("");
				$("#msgType").val("mpnews");
			}
			if(i==1){
				$("#msgType").val("");
				$("#msgType").val("text");
			}
			$(this).addClass("selected");
			$(".tab_content").each(function(j){
				if(i==j){
					$(this).show();
				}else{
					$(this).hide();
				}
			})
		})
	})
});
function selectGroup(groupid,name){
	var html=name+"<span class=\"caret\"></span>";
	$("#addToGrop").text("");
	$("#addToGrop").append(html);
	$("#groupId").val("");
	$("#groupId").val(groupid);
}
function selectSpreadDetail(spreadDetailId,name){
	var html=name+"<span class=\"caret\"></span>";
	$("#addToSpreadDetail").text("");
	$("#addToSpreadDetail").append(html);
	$("#spreadDetailId").val("");
	$("#spreadDetailId").val(spreadDetailId);
}
function selectNewsItem(tempId){
	var path="";
	art.dialog.open('${ctx}/wechatSendMessage/selectNewsItem', {
	    title: '选择素材',
	    width:"1024px",
	    height:"520px",
	    lock : true,
		fixed : true,
	    init: function () {
	    	var iframe = this.iframe.contentWindow;
	    	var top = art.dialog.top;// 引用顶层页面window对象
	    },
	    ok: function () {
	    	var iframe = this.iframe.contentWindow;
	    	if (!iframe.document.body) {
	        	alert('iframe还没加载完毕呢')
	        	return false;
	        };
	       var newsItem= iframe.document.getElementById('newsItem').value;
	       var newsItemTemplateId= iframe.document.getElementById('newsItemTemplateId').value;
	       if(null!=newsItemTemplateId&&newsItemTemplateId.length>0){
		        $("#tab_cont_cover").hide();
		        $("#deleteMedia").show();
		        $("#msg-item-wrapper").show();
		        $("#msg-item-wrapper").append(newsItem);
		        $("#newsItemTemplateId").val(newsItemTemplateId);
		    	$(".msg-item-wrapper").find(".appmsg_mask").remove();
				$(".msg-item-wrapper").find(".icon_card_selected").remove();
		       	return true;
	       }else{
	    	   alert("请选择素材后点击【确定】按钮");
	    	   return false;
	       }
	    },
	    cancel: true
	});
}
function preview(){
	if(validPreview()==false){
		return ;
	}
	top.art.dialog({
	    title: '设置图片名称',
	    content: document.getElementById('templateId'),
	    lock : true,
		fixed : true,
	    ok: function () {
	    	var wechatId=window.parent.document.getElementById("wechatId").value;
	    	var newsItemTemplateId=window.document.getElementById("newsItemTemplateId").value;
	    	var msgType=window.document.getElementById("msgType").value;
	    	var content=window.document.getElementById("content").value;
	    	if(null==wechatId||wechatId==''){
	    		$(window.parent.document.getElementById("imageTr")).css("color","red");
	    		$(window.parent.document.getElementById("imageTr")).find("input").css("border-color","red");
	    		$(window.parent.document.getElementById("messageError")).show();
	    		return false;
	    	}
	    	var url='${ctx}/wechatSendMessage/preview';
	    	var params={"wechatId":wechatId,"newsItemTemplateId":newsItemTemplateId,"msgType":msgType,"content":content};
	    	$.post(url,params,function callBack(data) {
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					$.utile.tips(data[0].message);
					return true;
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					$.utile.tips(data[0].message);
					// 保存失败时页面停留在数据编辑页面
				}
			});
    		
			
	    },
	    cancel:function(){
			return true;
	    }
	});
}
function validPreview(){
	var msgType=$("#msgType").val();
	var newsItemTemplateId=$("#newsItemTemplateId").val();
	var content=$("#content").val();
	if(msgType=='mpnews'){
		if(null!=newsItemTemplateId&&newsItemTemplateId==''){
			alert("请选择发送内容");
			return false;
		}
	}
	if(msgType=='text'){
		if(null!=content&&content==''){
			alert("请填写发送内容!");
			return false;
		}
	}
	return true;
}
</script>
</body>
</html>

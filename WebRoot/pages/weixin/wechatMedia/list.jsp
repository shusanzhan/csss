<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>微信图片库</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx }/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx }/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx }/css/uniform.css" />
<link rel="stylesheet" href="${ctx }/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx }/css/unicorn.grey.css"	class="skin-color" />
<link rel="stylesheet" href="${ctx }/css/weixin/media.css"	/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">

</style>
</head>
<body>
		<div id="breadcrumb">
			<a href="${ctx }/main/index" title="主页" class="tip-bottom"><i
				class="icon-home"></i>主页</a> <a href="javascript:void(-1)"
				class="current">微信图片库</a>
		</div>

		<div class="container-fluid">
			<div style="width: 100%;">
				<div style="float: left;margin-top: 10px;">
					<p>
						<a class="btn btn-inverse" href="javascript:void(-1)" onclick="uploadLocalImage()">
							<i	class="icon-plus-sign icon-white"></i>上传本地素材</a>
						<a class="btn btn-danger" href="javascript:void(-1)" onclick="$.utile.deleteIds('${ctx }/wechatMedia/delete','searchPageForm')">
							<i class="icon-remove icon-white"></i>删除
						</a>
					</p>
				</div>
				<div style="float: right;margin-top: 10px;line-height: 30px;">
					<form name="searchPageForm" id="searchPageForm" class="form-horizontal" action="${ctx}/wechatMedia/queryList" method="post">
				     <input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
				     <input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
					</form>
				</div>
				<div  style="clear: both;"></div>
			</div>
			<div class="widget-content">
				<c:if test="${empty(page.result)||page.result==null }" var="status">
					<div class="alert">
						<strong>提示!</strong> 当前未添加数据.
					</div>
				</c:if>
				<c:if test="${status==false }">
					<div class="group img_pick" id="js_imglist">
						<ul class="group">
							<c:forEach var="wechatMedia" items="${page.result }">
								<li class="img_item js_imgitem" data-id="210819047">
									<div class="img_item_bd">
						                <img class="pic" width="169" height="169" src="${wechatMedia.thumbUrl }" data-previewsrc="" data-id="${wechatMedia.dbid }">
										<span class="check_content">
											<label class="frm_checkbox_label selected" >
											<input type="checkbox" class="frm_checkbox" name="id" id="id1" value="${wechatMedia.dbid }">
												<span class="lbl_content">${fn:length(wechatMedia.name)>10==true?fn:substring(wechatMedia.name,0,18):wechatMedia.name}</span>
											</label>
										</span>
									</div>
									<div class="msg_card_ft">
						                <ul class="grid_line msg_card_opr_list">
											<li class="grid_item size1of3 msg_card_opr_item">
						                        <a class="js_edit js_tooltip js_popover" onclick="editImage('${wechatMedia.name }','${wechatMedia.dbid }')"  href="javascript:;" >
													<span class="msg_card_opr_item_inner">
														<i class="icon18_common edit_gray">编辑</i>
													</span>
													<span class="vm_box"></span>
												</a>
											</li>
											<li class="grid_item size1of3 no_extra msg_card_opr_item">
												<a class="js_del js_tooltip js_popover" onclick="$.utile.operatorDataByDbid('${ctx }/wechatMedia/delete?dbids=${wechatMedia.dbid }','searchPageForm','您确定删除【${wechatMedia.name }】吗？')"  title="删除">
													<span class="msg_card_opr_item_inner">
														<i class="icon18_common del_gray">删除</i>
													</span>
													<span class="vm_box"></span>
												</a>
											</li>
										</ul>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			<div id="fanye">
				<%@ include file="../../commons/commonPagination.jsp" %>
			</div>
		</div>

	</div>
	<div style="display: none; width: 340px;" id="templateId">
		<table id="noLine" border="0" align="center" cellpadding="0" cellspacing="0" style="width: 320px;margin-top: 5px;">
			<tr style="height: 30px;" height="30" id="imageTr">
				<td class="formTableTdLeft" width="40"  style="text-align: right;padding-right: 20px;">名称:&nbsp;</td>
				<td colspan="3" style="text-align: left;" width="280">
					<input type="hidden" id="wechatMediaId" name="wechatMediaId" value="">
					<input type="text" id="imageName" name="imageName" value="" style="width: 200px;">
				</td>
			</tr>
			<tr style="height: 20px;" height="20">
				<td class="formTableTdLeft" width="40"  style="text-align: right;padding-right: 20px;">&nbsp;</td>
				<td id="messageError" colspan="3" style="text-align: left;display: none;color: red;" width="280">
					填写名称错误！
				</td>
			</tr>
		</table>
	</div>
	<script src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
	<script src="${ctx }/widgets/bootstrap/jquery.ui.custom.js"></script>
	<script src="${ctx }/widgets/bootstrap/bootstrap.min.js"></script>
	<script src="${ctx }/widgets/bootstrap/jquery.uniform.js"></script>
	<script src="${ctx }/widgets/bootstrap/jquery.dataTables.min.js"></script>
	<script src="${ctx }/widgets/bootstrap/unicorn.js"></script>
	<script src="${ctx }/widgets/bootstrap/unicorn.tables.js"></script>
	<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
	<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=default"></script>
	<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
	<script type="text/javascript">
		function editImage(v,wechatMediaId){
			$("#imageName").val(v);
			$("#wechatMediaId").val(wechatMediaId);
			top.art.dialog({
			    title: '设置图片名称',
			    content: document.getElementById('templateId'),
			    lock : true,
				fixed : true,
			    ok: function () {
			    	var imageName=window.parent.document.getElementById("imageName").value;
			    	var wechatMediaId=window.parent.document.getElementById("wechatMediaId").value;
			    	if(null==imageName||imageName==''){
			    		$(window.parent.document.getElementById("imageTr")).css("color","red");
			    		$(window.parent.document.getElementById("imageTr")).find("input").css("border-color","red");
			    		$(window.parent.document.getElementById("messageError")).show();
			    		return false;
			    	}
			    	var url='${ctx}/wechatMedia/saveEdit';
			    	var params={"name":imageName,"wechatMediaId":wechatMediaId};
			    	$.post(url,params,function callBack(data) {
						if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
							$.utile.tips(data[0].message);
								setTimeout(
										function() {
											window.location.href = data[0].url
										}, 1000);
						}
						if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
							$.utile.tips(data[0].message);
							// 保存失败时页面停留在数据编辑页面
						}
						return;
					});
		    		
					return true;
			    },
			    cancel:function(){
					return true;
			    }
			});
		}
		function uploadLocalImage(){
			var path="";
			art.dialog.open(path+'/wechatMedia/uploadLocalImage', {
				title: '上传图片',
				width:"690px",height:"400px",
				init: function () {
					var iframe = this.iframe.contentWindow;
					var top = art.dialog.top;// 引用顶层页面window对象
				},
				ok: function () {
					var iframe = this.iframe.contentWindow;
					if (!iframe.document.body) {
						alert('iframe还没加载完毕呢');
						return false;
					};
					/* var json= iframe.document.getElementById('fileUpload').value;
					
					if(null!=json&&json.length>0){
						var obj=$.parseJSON(json);
						$("#thumbUrl").val(obj.thumbUrl);
						$("#thumb_media_id").val(obj.mediaId);
						$("#thumbWechatUrl").val(obj.thumbWechatUrl);
						$("#cboxClose").show();
		    			 $(".i-img", window.appmsg).attr("src", obj.thumbUrl).show();
		    			 $(".default-tip", window.appmsg).hide();
		    			$(".cover_url", window.appmsg).val(obj.thumbUrl); 
		    			$(".thumb_media_id", window.appmsg).val(obj.mediaId);
		    			$(".thumbWechatUrl", window.appmsg).val(obj.thumbWechatUrl); 
		    			$("#imgArea").show().find("#imgShowPciture").attr("src", obj.thumbUrl);
						return true;
					}else{
						alert("请选择图片后点击【确定】按钮");
						return false;
					} */
					window.location.reload();
					return true;
				},
				cancel: true
			});
		}
	</script>
</body>
</html>

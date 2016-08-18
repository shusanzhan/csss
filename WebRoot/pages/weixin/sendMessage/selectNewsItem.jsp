<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>关注时回复</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx }/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx }/css/bootstrap-responsive.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx }/css/uniform.css" />
<link rel="stylesheet" href="${ctx }/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx }/css/unicorn.grey.css"/>
<link rel="stylesheet" href="${ctx }/wxcss/css/block.css"/>
<link rel="stylesheet" href="${ctx }/wxcss/css/global.css?da=${now}"/>
<link rel="stylesheet" href="${ctx }/wxcss/css/font-awesome.min.css"/>
<style type="text/css">
.msg-item-wrapper{
	cursor: pointer;
}
.msg-item-wrapper .appmsg_mask {
    background-color: #000;
    height: 100%;
     display:none;
    left: 0;
    opacity: 0.6;
    position: absolute;
    top: 0;
    width: 100%;
    z-index: 1;
    border-radius: 5px;
}
.msg-item-wrapper:hover .appmsg_mask{
	 display:block;
}

.msg-item-wrapper.selected .appmsg_mask {
    display: block;
}
.msg-item-wrapper.selected .icon_card_selected {
    display: inline-block;
}

.msg-item-wrapper .icon_card_selected {
    display: none;
    left: 50%;
    margin-left: -23px;
    margin-top: -23px;
    overflow: hidden;
    position: absolute;
    top: 50%;
    z-index: 1;
    background: rgba(0, 0, 0, 0) url("${ctx}/images/base_z284ee4.png") no-repeat scroll 0 -6111px;
    height: 46px;
    vertical-align: middle;
    width: 46px;
}
</style>
</head>
<body>
<div class="block-wrap" style="margin-left: 12px;">
  <input type="hidden" id="newsItemTemplateId" name="newsTemplateId" value="">
  <input type="hidden" id="newsItem" name="newsItem" value="">
  <div class="tj msg-list">
	 <c:forEach var="weixinNewstemplate" items="${weixinNewstemplates }">
	 	<c:if test="${weixinNewstemplate.type==1 }">
	 	  <div class="msg-item-wrapper" tempId="${weixinNewstemplate.dbid }">
               <div class="msg-item">
                 <h4 class="msg-t"><a href="javascript:void(-1)" class="i-title" >${weixinNewstemplate.title }</a></h4>
                 <p class="msg-meta"><span class="msg-date">${fn:substring(weixinNewstemplate.addtime,0,10) }</span></p>
                 <c:forEach items="${weixinNewstemplate.wechatNewsitems }" var="weixinNewsitem">
                	 <div class="cover">
                    <p class="default-tip" style="display:none">封面图片</p>
                   	 	<img src="${weixinNewsitem.thumbUrl }" class="i-img" style=""> </div>
                  	<p class="msg-text">${weixinNewsitem.digest }</p>
                 </c:forEach>
               </div>
                <div class="appmsg_mask"></div>
				<i class="icon_card_selected">已选择</i>
           </div>
         </c:if>
         <c:if test="${weixinNewstemplate.type==2 }">
         	<div class="msg-item-wrapper" tempId="${weixinNewstemplate.dbid }">
	                <div class="msg-item multi-msg">
	                	<c:forEach var="weixinNewsitem" items="${weixinNewstemplate.wechatNewsitems }" varStatus="i">
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
	              <div class="appmsg_mask" ></div>
	             <i class="icon_card_selected">已选择</i>
              </div>
         </c:if>
	 </c:forEach>	   
  	  <!-- list结束 -->
  </div>
</div>
	<script src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
	<script type="text/javascript">
	$().ready(function(){
		$(".msg-item-wrapper").each(function(i){
			$(this).bind("click",function(){
				$(".selected").removeClass("selected");
				$(this).addClass("selected");
				$("#newsItem").val($(this).andSelf().html());
				$("#newsItemTemplateId").val($(this).attr("tempId"));
			})
		})
	})
</script>
	<script src="${ctx }/widgets/bootstrap/jquery.ui.custom.js"></script>
	<script src="${ctx }/widgets/bootstrap/bootstrap.min.js"></script>
	<script src="${ctx }/widgets/bootstrap/jquery.uniform.js"></script>
	<script src="${ctx }/widgets/bootstrap/jquery.dataTables.min.js"></script>
	<script src="${ctx }/widgets/bootstrap/unicorn.js"></script>
	<script src="${ctx }/widgets/bootstrap/unicorn.tables.js"></script>
	<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
	<script src="${ctx }/wxcss/js/global.js"></script>

</body>
</html>

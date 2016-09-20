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
</head>
<body>
<div id="breadcrumb">
	<a href="${ctx }/main/index" title="主页" class="tip-bottom"><i
		class="icon-home"></i>主页</a> <a href="javascript:void(-1)"
		class="current">关注时回复</a>
</div>

<div class="block-wrap">
  <div class="tj msg-list">
    <!-- list开始 -->
      <div id="addAppmsg" class="tc add-access"> 
	      <a href="${ctx }/weixinNewsItem/add" class="dib vm add-btn">+单图文消息</a> 
	      <a href="${ctx }/weixinNewsItem/addMore" class="dib vm add-btn multi-access">+多图文消息</a> 
      </div>
	 <c:forEach var="weixinNewstemplate" items="${weixinNewstemplates }">
	 	<c:if test="${weixinNewstemplate.type==1 }">
	 	  <div class="msg-item-wrapper">
                <div class="msg-item">
                  <c:forEach items="${weixinNewstemplate.weixinNewsitems }" var="weixinNewsitem">
                  <h4 class="msg-t"><a href="" class="i-title" onclick="window.open('${ctx}/newsItemWechat/readNewsItem?dbid=${weixinNewsitem.dbid }')">${weixinNewstemplate.templatename }</a></h4>
                  <p class="msg-meta"><span class="msg-date">${fn:substring(weixinNewstemplate.addtime,0,10) }</span></p>
                 	 <div class="cover">
	                    <p class="default-tip" style="display:none">封面图片</p>
	                   	 	<img src="${weixinNewsitem.imagepath }" class="i-img" style=""> </div>
	                  	<p class="msg-text">${weixinNewsitem.description }</p>
                  </c:forEach>
                </div>
                <div class="msg-opr">
                  <ul class="f0 msg-opr-list">
                    <li class="b-dib opr-item"><a class="block tc opr-btn edit-btn" href="${ctx }/weixinNewsItem/edit?dbid=${weixinNewstemplate.dbid}" data-rid="7200"><span class="th vm dib opr-icon edit-icon">编辑</span></a></li>
                    <li class="b-dib opr-item"><a class="block tc opr-btn del-btn" href="javascript:void(-1)" onclick="$.utile.deleteById('${ctx}/weixinNewsItem/delete?dbids=${weixinNewstemplate.dbid}','searchPageForm')" data-rid="7200"><span class="th vm dib opr-icon del-icon">删除</span></a></li>
                  </ul>
                </div>
              </div>
         </c:if>
         <c:if test="${weixinNewstemplate.type==2 }">
         	<div class="msg-item-wrapper">
                <div class="msg-item multi-msg">
                	<c:forEach var="weixinNewsitem" items="${weixinNewstemplate.weixinNewsitems }" varStatus="i">
                		<c:if test="${i.index==0 }" var="status">
		                  <div class="appmsgItem">
		                    <h4 class="msg-t"><a href="${ctx }/weixinNewsItem/editMore?dbid=${weixinNewstemplate.dbid}" class="i-title" >${weixinNewsitem.title }</a></h4>
		                    <p class="msg-meta"><span class="msg-date"><fmt:formatDate value="${weixinNewsitem.createDate }" pattern="yyyy-MM-dd"></fmt:formatDate></span></p>
		                    <div class="cover">
		                      <p class="default-tip" style="display:none">封面图片</p>
		                      <img src="${weixinNewsitem.imagepath }" class="i-img" style=""> </div>
		                    <p class="msg-text"></p>
		                  </div>
	                  </c:if>
	                  <c:if test="${status==false }">
		                  <div class="rel sub-msg-item appmsgItem"> 
		                  	<span class="thumb"> <img src="${weixinNewsitem.imagepath }" class="i-img" style=""> </span>
		                    <h4 class="msg-t"> <a href="${ctx }/weixinNewsItem/editMore?dbid=${weixinNewstemplate.dbid}"  class="i-title">${weixinNewsitem.title }</a> </h4>
		                  </div>
                      </c:if>                                  	   
	               </c:forEach>
               </div>
                <div class="msg-opr">
                  <ul class="f0 msg-opr-list">
                    <li class="b-dib opr-item"><a class="block tc opr-btn edit-btn" href="${ctx }/weixinNewsItem/editMore?dbid=${weixinNewstemplate.dbid}"><span class="th vm dib opr-icon edit-icon">编辑</span></a></li>
                      <li class="b-dib opr-item"><a class="block tc opr-btn del-btn" href="javascript:void(-1)" onclick="$.utile.deleteById('${ctx}/weixinNewsItem/delete?dbids=${weixinNewstemplate.dbid}','searchPageForm')" data-rid="7200"><span class="th vm dib opr-icon del-icon">删除</span></a></li>
                  </ul>
                </div>
              </div>
         </c:if>
	 </c:forEach>	   
  	  <!-- list结束 -->
  </div>
</div>
	<script src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
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

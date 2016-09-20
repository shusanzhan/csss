<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>群发功能</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx }/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx }/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx }/css/uniform.css" />
<link rel="stylesheet" href="${ctx }/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx }/css/unicorn.grey.css"	class="skin-color" />
<link rel="stylesheet" href="${ctx }/css/common.css"	class="skin-color" />
<style type="text/css">
.table-bordered th, .table-bordered td {
    border-left: 0px solid #ddd;
}
.iconText {
    background: rgba(0, 0, 0, 0) url("${ctx}/images/media_z2880f5.png") no-repeat scroll 0 -252px;
    height: 40px;
    width: 40px;
    margin-left: -20px;
    margin-top: 30px;
    position: absolute;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
		<div id="breadcrumb">
			<a href="${ctx }/main/index" title="主页" class="tip-bottom"><i
				class="icon-home"></i>主页</a> <a href="javascript:void(-1)"
				class="current">群发功能</a>
		</div>
		<br>
		<div class="alert alert-error">
			应腾讯公司要求，为保障用户体验，微信公众平台严禁恶意营销以及诱导分享朋友圈，严禁发布色情低俗、暴力血腥、政治谣言等各类违反法律法规及相关政策规定的信息。一旦发现，将严厉打击和处理。 
		</div>
		<div class="container-fluid">
			<div style="width: 100%;">
				<div style="float: left;margin-top: 10px;">
					<p>
						<a class="btn btn-inverse" href="${ctx }/wechatSendMessage/add">
							<i	class="icon-plus-sign icon-white"></i>群发消息</a>
						<%-- <a class="btn btn-danger" href="javascript:void(-1)" onclick="$.utile.deleteIds('${ctx }/weixinAccount/delete','searchPageForm')">
							<i class="icon-remove icon-white"></i>删除
						</a> --%>
					</p>
				</div>
				<div style="float: right;margin-top: 10px;line-height: 30px;">
					<form name="searchPageForm" id="searchPageForm" class="form-horizontal" action="${ctx}/weixinAccount/queryList" method="post">
				     <input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
				     <input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
				     <input type="hidden" id="parentMenu" name="parentMenu" value='${param.parentMenu}'>
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
				<table class="table table-bordered table-striped with-check">
					<thead>
						<tr>
							<th style="width: 320px;">消息类型</th>
							<th style="width: 120px;">发送设置</th>
							<th style="width: 80px;">发送状态</th>
							<th style="width: 80px;">操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.result }" var="wechatSendMessage">
						<c:if test="${wechatSendMessage.msgtype=='mpnews' }">
						<c:set value="${wechatSendMessage.wechatNewsTemplate }" var="wechatNewsTemplate"></c:set>
						<tr>
							<td>
							    <div style="padding: 5px;">
							    	<c:forEach var="wechatNewsItem" items="${wechatNewsTemplate.wechatNewsitems }" varStatus="i">
							    		<c:if test="${i.index==0 }" var="status">
									    	<div style="float: left;">
										        <img src="${wechatNewsItem.thumbUrl }" class="icon icon_lh" style="padding-left: -152px;width: 100px;height: 100px;">
									    	</div>
								    	</c:if>
							    	</c:forEach>
							    	<div style="float: left;margin-left: 12px;">
							    		<a target="_blank" >
								            <span class="title">
								            	 [图文]
								           ${wechatNewsTemplate.title }</span>
								        </a>
								        <!--  <div class="desc" style="text-align: left;margin-top: 8px;color: #8d8d8d;">
								            <div>
								                <span>阅读 1</span>
								                <span style="margin-left: 32px;">点赞 1</span>
								            </div>
								        </div> -->
								    </div>
								    <div style="clear: both;"></div>
							    </div>
							</td>
							<td>
								 <div style="padding: 5px;">
									<div style="margin-left: 12px;text-align: left;height: 100px;">
								        <span class="title"><fmt:formatDate value="${wechatSendMessage.createDate }" pattern="yyyy年MM月dd日"/> </span>
								         <div class="desc" style="text-align: left;margin-top: 8px;color: #8d8d8d;">
								          	<div>
								                <span>分组：
								                	<c:if test="${empty(wechatSendMessage.weixinGroup) }">
								                		全部用户
								                	</c:if>
								                	<c:if test="${!empty(wechatSendMessage.weixinGroup) }">
								                		${wechatSendMessage.weixinGroup.name }
								                	</c:if>
								                 </span>
								            </div> 
								        </div>
								    </div>
								 </div>
							</td>
							<td>
								<div style="padding: 5px;">
									<div style="margin-left: 12px;text-align: left;height: 100px;">
										<c:if test="${empty(wechatSendMessage.msgId) }">
									        <span class="title" style="color: red;">发送失败</span>
										</c:if>
										<c:if test="${!empty(wechatSendMessage.msgId) }">
									        <span class="title" style="color: green;">发送成功</span>
										</c:if>
										<c:if test="${!empty(wechatSendMessage.msgId) }">
											 <div class="desc" style="text-align: left;margin-top: 8px;color: #8d8d8d;">
								           		 <div>
									                <span>发送成功人数 ${wechatSendMessage.sentCount }</span>
									                <br>
									                <span>发送失败人数 ${wechatSendMessage.errorCount }</span>
									            </div>
									        </div>
								        </c:if>
								    </div>
								 </div>
							</td>
							<td style="text-align: center;">
								<div style="padding: 5px;">
									<div style="margin-left: 12px;height: 100px;">
										<a href="javascript:void(-1)" onclick="$.utile.deleteById('${ctx}/wechatSendMessage/delete?dbids=${wechatSendMessage.dbid}','searchPageForm')" title="删除">删除</a>
									</div>
								</div>
							</td>
						</tr>
						</c:if>
						<c:if test="${wechatSendMessage.msgtype=='text' }">
							<tr>
								<td>
								    <div style="padding: 5px;">
								    	<div style="float: left;height: 100px;width: 100px;background-color: #d7d8da !important; display: inline-block;">
									        <span class="iconText"></span>
								    	</div>
								    	<div style="float: left;margin-left: 12px;max-width: 320px;text-align: left;">
									            <span class="title" style="text-align: left;">
									            	 [文本]
									           ${wechatSendMessage.description }</span>
									    </div>
									    <div style="clear: both;"></div>
								    </div>
								</td>
								<td>
									 <div style="padding: 5px;">
										<div style="margin-left: 12px;text-align: left;height: 100px;">
									        <span class="title"><fmt:formatDate value="${wechatSendMessage.createDate }" pattern="yyyy年MM月dd日"/> </span>
									         <div class="desc" style="text-align: left;margin-top: 8px;color: #8d8d8d;">
									          	<div>
									                <span>分组：
									                	<c:if test="${empty(wechatSendMessage.weixinGroup) }">
									                		全部用户
									                	</c:if>
									                	<c:if test="${!empty(wechatSendMessage.weixinGroup) }">
									                		${wechatSendMessage.weixinGroup.name }
									                	</c:if>
									                 </span>
									            </div> 
									        </div>
									    </div>
									 </div>
								</td>
								<td>
									<div style="padding: 5px;">
										<div style="margin-left: 12px;text-align: left;height: 100px;">
											<c:if test="${empty(wechatSendMessage.msgId) }">
										        <span class="title" style="color: red;">发送失败</span>
											</c:if>
											<c:if test="${!empty(wechatSendMessage.msgId) }">
										        <span class="title" style="color: green;">发送成功</span>
											</c:if>
											<c:if test="${!empty(wechatSendMessage.msgId) }">
												 <div class="desc" style="text-align: left;margin-top: 8px;color: #8d8d8d;">
									           		 <div>
										                <span>发送成功人数 ${wechatSendMessage.sentCount }</span>
										                <br>
										                <span>发送失败人数 ${wechatSendMessage.errorCount }</span>
										            </div>
										        </div>
									        </c:if>
									    </div>
									 </div>
								</td>
								<td style="text-align: center;">
									<div style="padding: 5px;">
										<div style="margin-left: 12px;height: 100px;">
											<a href="javascript:void(-1)" onclick="$.utile.deleteById('${ctx}/wechatSendMessage/delete?dbids=${wechatSendMessage.dbid}','searchPageForm')" title="删除">删除</a>
										</div>
									</div>
								</td>
							</tr>
						</c:if>
					</c:forEach>
					</tbody>
				</table>
				</c:if>
			<div id="fanye">
				<%@ include file="../../commons/commonPagination.jsp" %>
			</div>
		</div>

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
</body>
</html>

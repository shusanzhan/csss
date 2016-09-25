<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>客服管理</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx }/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx }/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx }/css/uniform.css" />
<link rel="stylesheet" href="${ctx }/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx }/css/unicorn.grey.css"
	class="skin-color" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
		<div id="breadcrumb">
			<a href="${ctx }/main/index" title="主页" class="tip-bottom"><i
				class="icon-home"></i>主页</a> <a href="javascript:void(-1)"
				class="current">客服管理</a>
		</div>
		<br>
		<div class="alert alert-error">
			<p>一个公众帐号多个客服同时服务，帮你轻松解决客服问题，不错过任何一个订单；</p>
			<p>您具备开发能力，可通过客户端高级设置，开发更丰富的客服信息服务功能；</p>
			<a href="javascript:void(-1)" onclick="window.open('http://kf.qq.com/faq/120911VrYVrA160126Nvi6NN.html')">多客服使用说明 </a>
			|
			<a href="https://mpkf.weixin.qq.com/cgi-bin/kfindex?token=1449357373" target="blank" >网页客服端</a>
		</div>
		
		<div class="container-fluid">
			<div style="width: 100%;">
				<div style="float: left;margin-top: 10px;">
					<p>
						<a class="btn btn-inverse" href="${ctx }/kfAccount/add">
							<i	class="icon-plus-sign icon-white"></i>添加</a>
						<a class="btn btn-primary" href="javascript:void(-1)" onclick="sysUpload()">
							<i class="icon-plus-sign icon-white"></i>同步
						</a>
						<%-- <a class="btn btn-danger" href="javascript:void(-1)" onclick="$.utile.deleteIds('${ctx }/kfAccount/delete','searchPageForm')">
							<i class="icon-remove icon-white"></i>删除
						</a> --%>
					</p>
				</div>
				<div style="float: right;margin-top: 10px;line-height: 30px;">
					<form name="searchPageForm" id="searchPageForm" class="form-horizontal" action="${ctx}/kfAccount/queryList" method="post">
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
							<!-- <th style="width: 20px;"><div class="checker" id="uniform-title-table-checkbox">
									<span><input type="checkbox" name="title-table-checkbox" id="title-table-checkbox" style="opacity: 0;" onclick="selectAll(this,'id')"></span>
								</div></th> -->
							<th style="width: 140px;">头像</th>
							<th style="width: 120px;">账号</th>
							<th style="width: 120px;">昵称</th>
							<th style="width: 120px;">绑定微信号</th>
							<th style="width: 120px;">邀请状态</th>
							<th style="width: 120px;">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result }" var="kfAccount">
						<tr>
							<%-- <td style="text-align: center;"><div class="checker" id="uniform-undefined">
									<span><input type="checkbox" style="opacity: 0;"  name="id" id="id1" value="${kfAccount.dbid }"></span>
								</div>
							</td> --%>
							<td>
								
								<c:if test="${empty(kfAccount.kfHeadimgurl)}">
									<img src="${ctx }/images/weixin/avatar-medium.png" alt="" height="80" width="80" id="headimage_id1">
								</c:if>	
								<c:if test="${!empty(kfAccount.kfHeadimgurl)}">
									<img alt="" src="${kfAccount.kfHeadimgurl }" width="120" height="120"> 
								</c:if>	
								
							</td>
							<td>${kfAccount.kfAccount }</td>
							<td>${kfAccount.kfNick }</td>
							<td>
								<c:if test="${empty(kfAccount.inviteWx) }">
									<span style="color: red">未绑定</span>
								</c:if>
							${kfAccount.inviteWx }</td>
							<td>${kfAccount.inviteStatus }</td>
							<td style="text-align: center;">
							<c:if test="${empty(kfAccount.inviteWx) }">
								<a href="javascript:void(-1)" onclick="$.utile.openDialog('${ctx}/kfAccount/inviteworker?dbid=${kfAccount.dbid }','绑定微信号',760,400)">绑定</a> |
							</c:if> 
							<a href="javascript:void(-1)" onclick="window.location.href='${ctx}/kfAccount/edit?dbid=${kfAccount.dbid}&parentMenu=${param.parentMenu }'">编辑</a> | 
							<a href="javascript:void(-1)" onclick="$.utile.deleteById('${ctx}/kfAccount/delete?dbids=${kfAccount.dbid}','searchPageForm')" title="删除">删除</a></td>
						</tr>
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
	<script type="text/javascript">
	function sysUpload(){
		$.ajax({	
			url : "${ctx}/kfAccount/sysUpload", 
			data : {}, 
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
					$(".butSave").attr("onclick",url2);
					return ;
				}else if(obj[0].mark==0){
					$.utile.tips(data[0].message);
						setTimeout(
								function() {
									window.location.href = obj[0].url
								}, 1000);
				}
			},
			complete : function(jqXHR, textStatus){
				$(".butSave").attr("onclick",url2);
				var jqXHR=jqXHR;
				var textStatus=textStatus;
			}, 
			beforeSend : function(jqXHR, configs){
				url2=$(".butSave").attr("onclick");
				$(".butSave").attr("onclick","");
				var jqXHR=jqXHR;
				var configs=configs;
			}, 
			error : function(jqXHR, textStatus, errorThrown){
					$.utile.tips("系统请求超时");
					$(".butSave").attr("onclick",url2);
			}
		});
	}	
	</script>
</body>
</html>

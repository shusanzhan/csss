<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>公众账号设置</title>
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
			<a href="${ctx }/main/index" title="微商城中心" class="tip-bottom"><i
				class="icon-home"></i>微商城中心</a> <a href="javascript:void(-1)"
				class="current">关注粉丝</a>
		</div>

		<div class="container-fluid">
			<div style="width: 100%;">
				<div style="float: left;margin-top: 10px;">
					<div class="btn-group">
					  <button id="addToGrop" data-toggle="dropdown" class="btn btn-primary dropdown-toggle disabled">添加到... <span class="caret"></span></button>
					  <ul class="dropdown-menu">
					  	<c:forEach var="weixinGroup" items="${weixinGroups }">
							<li><a href="javascript:void(-1)" onclick="updateGroupBatch(${weixinGroup.dbid})">${weixinGroup.name }</a></li>
						</c:forEach>
					  </ul>
					</div><!-- /btn-group -->
				</div>
				<div style="float: left;margin-top: 10px;margin-left: 12px;">
					<button class="btn btn-success" onclick="sysGzuserInfo()">同步关注用户</button>
				</div>
				<div style="float: right;margin-top: 10px;line-height: 30px;">
					<form name="searchPageForm" id="searchPageForm" class="form-horizontal" action="${ctx}/weixinGzUserInfo/queryList" method="post">
				     <input type="hidden" id="currentPage" name="currentPage" value='${page.currentPageNo}'>
				     <input type="hidden" id="paramPageSize" name="pageSize" value='${page.pageSize}'>
				     <input type="hidden" id="parentMenu" name="parentMenu" value='${param.parentMenu}'>
				      <table  cellpadding="0" cellspacing="0" >
						<tr>
							<td>用户组：</td>
							<td>
								<select  id="groupId" name="groupId" onchange="$('#searchPageForm')[0].submit()" style="width: 120px;">
									<option value="-1">请选择...</option>
									<c:forEach var="weixinGroup" items="${totalWeixinGroups }">
										<option value="${weixinGroup.wechatGroupId }" ${param.groupId==weixinGroup.wechatGroupId?'selected="selected"':''} >${weixinGroup.name }（${weixinGroup.totalNum }）</option>
									</c:forEach>
								</select>
							</td>
							<td>关注状态：</td>
							<td>
								<select  id="eventStatus" name="eventStatus" onchange="$('#searchPageForm')[0].submit()" style="width: 120px;">
									<option value="">请选择...</option>
									<option value="1" ${param.eventStatus==1?'selected="selected"':'' } >已关注</option>
									<option value="2" ${param.eventStatus==2?'selected="selected"':'' }>取消关注</option>
								</select>
							</td>
							<td><input type="submit"  class="btn btn-success" value="查询" style="margin-left: 20px;"></input></td>
						</tr>
					 </table>
					</form>
				</div>
				<div  style="clear: both;"></div>
			</div>
			<br>
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
							<th style="width: 20px;"><div class="checker" id="uniform-title-table-checkbox">
									<span><input type="checkbox" name="title-table-checkbox" id="title-table-checkbox" style="opacity: 0;" onclick="selectAll(this,'id')"></span>
								</div></th>
							<th style="width: 60px;">头像</th>
							<th style="width: 140px;">昵称</th>
							<th style="width: 40px;">称呼</th>
							<th style="width: 120px;">所在地</th>
							<th style="width: 120px;">关注时间</th>
							<th style="width: 120px;">关注状态</th>
							<th style="width: 120px;">组</th>
							<th style="width: 120px;">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result }" var="weixinGzUserInfo">
						<tr>
							 <td style="text-align: center;"><div class="checker" id="uniform-undefined">
									<span><input type="checkbox" style="opacity: 0;"  name="id" id="id1" value="${weixinGzUserInfo.dbid }" onclick="selectWa(this)"></span>
								</div>
							</td>
							<td><img alt="" width="60" src="${weixinGzUserInfo.headimgurl }"> </td>
							<td>${weixinGzUserInfo.nickname }</td>
							<td>
								<c:if test="${weixinGzUserInfo.sex==1 }">
									先生
								</c:if>
								<c:if test="${weixinGzUserInfo.sex==2 }">
									女士
								</c:if>
							</td>
							<td>${weixinGzUserInfo.country }${weixinGzUserInfo.province }${weixinGzUserInfo.city }</td>
							<td>
								<fmt:formatDate value="${weixinGzUserInfo.addtime }" pattern="yyyy-MM-dd HH:ss"/>
							</td>
							<td>
								<c:if test="${weixinGzUserInfo.eventStatus==1 }">
									<span style="color:green;">已关注</span>
								</c:if>
								<c:if test="${weixinGzUserInfo.eventStatus==2 }">
									<span style="color:red;">取消关注</span>
								</c:if>
							</td>
							<td>
								<select id="weixinGroup" name="weixinGroup" style="width: 120px;" onchange="updateGroup(this.value,${weixinGzUserInfo.dbid })">
									<c:forEach var="weixinGroup" items="${weixinGroups }">
										<option value="${weixinGroup.dbid }" ${weixinGzUserInfo.groupId==weixinGroup.wechatGroupId?'selected="selected"':''} >${weixinGroup.name }</option>
									</c:forEach>
								</select>
							</td>
							<td style="text-align: center;">
								<a href="javascript:void(-1)"  onclick="$.utile.openDialog('${ctx}/weixinGzUserInfo/view?dbid=${weixinGzUserInfo.dbid }','查看关注粉丝',760,420)">查看</a> 
							</td> 
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
		function updateGroup(groupId,userInfoId){
			$.post('${ctx}/weixinGzUserInfo/updateGroup?groupId='+groupId+'&userInfoId='+userInfoId+"&dateStatmp="+new Date(),{},function (data){
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					$.utile.tips(data[0].message);
					// 保存数据成功时页面需跳转到列表页面
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					$.utile.tips(data[0].message);
					// 保存失败时页面停留在数据编辑页面
				}
				return;
			})
		}
		function updateGroupBatch(groupId){
			var userInfoIds = getCheckBox();
			if(userInfoIds==''||userInfoIds==null){
				warm("请选择移动粉丝数据！");
				return ;
			}
			$.post('${ctx}/weixinGzUserInfo/updateGroupBatch?groupId='+groupId+'&userInfoIds='+userInfoIds+"&dateStatmp="+new Date(),{},function (data){
				if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
					$.utile.tips(data[0].message);
					// 保存数据成功时页面需跳转到列表页面
				}
				if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
					$.utile.tips(data[0].message);
					// 保存失败时页面停留在数据编辑页面
				}
				return;
			})
		}
		/**
		 * 显示人员选择器 list页面，checkBox选择数据
		 */
		function selectAll(checkbox, domname) {
			var doms = document.getElementsByName(domname);
			for ( var i = 0; i < doms.length; i++) {
				if (doms[i].type == "checkbox") {
					doms[i].checked = checkbox.checked;
					if(checkbox.checked){
						$(doms[i]).parent().addClass("checked");
						$("#addToGrop").removeClass("disabled");
					}else{
						$(doms[i]).parent().removeClass("checked");
						$("#addToGrop").addClass("disabled");
					}
				}
			}
		}
		function selectWa(checkbox) {
			if(checkbox.checked){
				$("#addToGrop").removeClass("disabled");
			}else{
				var users = getCheckBox();
				if(users==''||users==null){
					$("#addToGrop").addClass("disabled");
				}else{
					$("#addToGrop").removeClass("disabled");
				}
			}
		}
	function sysGzuserInfo(){
		$.ajax({	
			url : "${ctx}/weixinGzUserInfo/sysBathGzuserinfo", 
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

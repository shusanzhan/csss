<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/easy_validator.pack.js"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript">
	$(document).ready(function (){
		var result=null;
		$.post("${ctx}/user/userRoleJson?dbid=${user.dbid}&timeStamp="+new Date(), { } ,function callback(json){
			if(null!=json&&json.length>0){
				var length=json.length;
				for(var i=0;i<length;i++){
					var obj=json[i];
					var append="<tr height='32' align='left'>";
					if(obj.checked==true){
						append=append+"<td style='text-align: left;padding-left:12px;'><input type='checkbox' name='id' id='id1' value='"+obj.dbid+"' checked='checked'/></td>"
					}else{
						append=append+"<td style='text-align: left;padding-left:12px;'><input type='checkbox' name='id' id='id1' value='"+obj.dbid+"'/></td>"
					}
					append=append+"<td style='text-align: left;'>"+obj.name+"</td></tr>"
					result=result+append;	
				}
				$("#TableBody").append(result);
			}
		});
	});
</script>
<title>角色赋权限</title>
</head>
<body class="bodycolor">
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/user/queryList'">用户管理中心</a>
</div>
<div class="line"></div>
<div id="result">
	<form name="frmId" id="frmId" method="post" target="_self">
	<s:token></s:token>
	<input type="hidden" name="dbid" value="${user.dbid }" id="dbid">
	<table width="100%" class="mainTable" border="0" id="TableList" style="margin: 0 auto;">
	<thead class="TableHeader" id="TableHeader">
		<tr>
			<td class="sn" style="text-align: left;padding-left:12px;"><input type='checkbox' id="selectAllCheck" onclick="selectAll(this,'id')" />全选</td>
			<td class="span5">角色名称</td>
		</tr>
	</thead>
	<tbody id="TableBody">
	</tbody>
	</table>
	</form>
</div>
<div class="formButton">
    <a class="but butSave" href="javascript:void();" onclick="$.utile.submitForm('frmId','${ctx}/user/saveUserRole')">保存</a>
 	<a href="${ctx }/user/queryList"	onclick="" class="but butCancle">返回</a>
</div>
</body>
</html>
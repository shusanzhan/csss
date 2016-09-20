<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet"/>
<link  href="${ctx }/widgets/easyvalidator/css/validate.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/widgets/auto/jquery.autocomplete.css" />
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript"	src="${ctx}/widgets/auto/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/easyvalidator/js/easy_validator.pack.js"></script>
<title>店员管理</title>
</head>
<body class="bodycolor">
<div class="location">
     	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
     	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
<a href="javascript:void(-1);" onclick="window.location.href='${ctx}/csssStaff/queryList'">店员管理</a>-
<a href="javascript:void(-1);">
	<c:if test="${empty(csssStaff) }">添加店员</c:if>
	<c:if test="${!empty(csssStaff) }">编辑店员</c:if>
</a>
</div>
<div class="line"></div>
<div class="frmContent">
	<form action="" name="frmId" id="frmId"  target="_self">
		<s:token></s:token>
		<input type="hidden" name="csssStaff.dbid" id="dbid" value="${csssStaff.dbid }">
		<table border="0" align="center" cellpadding="0" cellspacing="0" style="width: 92%;">
			<tr height="32">
				<td class="formTableTdLeft">门店编码:&nbsp;</td>
				<td>
					<input type="hidden" name="csssShopId" id="csssShopId"	value="${csssStaff.csssShop.dbid }" >
					<input type="text" name="csssShopNo" id="csssShopNo"	value="${csssStaff.csssShop.no }" onfocus="autoCsssShop('csssShopNo')" class="largeX text" title="编码" placeholder="请输入门店编码"	checkType="string,1,50" tip="门店编码不能为空"><span style="color: red;">*</span>
				</td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">编码:&nbsp;</td>
				<td ><input type="text" name="csssStaff.no" id="no"
					value="${csssStaff.no }" class="largeX text" title="编码"	checkType="string,1,50" tip="编码不能为空"><span style="color: red;">*</span></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">名称:&nbsp;</td>
				<td ><input type="text" name="csssStaff.name" id="name"
					value="${csssStaff.name }" class="largeX text" title="名称"	checkType="string,1,50" tip="名称不能为空">
					<span style="color: red;">*</span></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">性别:&nbsp;</td>
				<td >
					<c:if test="${empty(csssStaff) }">
						<input type="radio" id="sex1"  name="csssStaff.sex" ${csssStaff.sex=='男'?'checked="checked"':'' } checked="checked" value="男"><label id="" for="sex1">男</label>
						<input type="radio" id="sex2"  name="csssStaff.sex" ${csssStaff.sex=='女'?'checked="checked"':'' } value="女"><label id="" for="sex2">女</label>	
					</c:if>
					<c:if test="${!empty(csssStaff) }">
						<input type="radio" id="sex1"  name="csssStaff.sex" ${csssStaff.sex=='男'?'checked="checked"':'' }  value="男"><label id="" for="sex1">男</label>
						<input type="radio" id="sex2"  name="csssStaff.sex" ${csssStaff.sex=='女'?'checked="checked"':'' } value="女"><label id="" for="sex2">女</label>	
					</c:if>
					<span style="color: red;">*</span></td>
			</tr>
			<tr height="42">
				<td class="formTableTdLeft">联系电话:&nbsp;</td>
				<td ><input type="text" name="csssStaff.mobilePhone" id="mobilePhone"
					value="${csssStaff.mobilePhone }" class="largeX text" title="联系电话"	checkType="mobilePhone" tip="联系电话不能为空"><span style="color: red;">*</span></td>
			</tr>
			
			<tr height="42">
				<td class="formTableTdLeft">邮箱:&nbsp;</td>
				<td ><input type="text" name="csssStaff.email" id="email"
					value="${csssStaff.email }" class="largeX text" ></td>
			</tr>
			<tr height="32">
				<td class="formTableTdLeft">备注:&nbsp;</td>
				<td>
					<textarea class="text textarea" rows="8" cols="60" name="csssStaff.note" id="note">${csssStaff.note }</textarea>
				</td>
			</tr>
		</table>
	</form>
	<div class="formButton">
		<a href="javascript:void(-1)"	onclick="$.utile.submitAjaxForm('frmId','${ctx}/csssStaff/save')"	class="but butSave">保&nbsp;&nbsp;存</a> 
	    <a href="javascript:void(-1)"	onclick="window.history.go(-1)" class="but butCancle">取&nbsp;&nbsp;消</a>
	</div>
	</div>
</body>
<script type="text/javascript">
$.utile.submitAjaxForm = function(frmId, url, state) {
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
							$(".butSave").attr("onclick",url2);
							return ;
						}else if(obj[0].mark==0){
							$.utile.tips(data[0].message);
							// 保存数据成功时页面需跳转到列表页面
							var url=data[0].url;
							if(url.indexOf("/") != -1){
								setTimeout(
										function() {
											window.location.href = obj[0].url
										}, 1000);
							}else{
								$.post('${ctx}/csssStaff/createQrCode?csssStaffDbid='+url+"&dateTime="+new Date(),{},function (data){
									if (data[0].mark == 0) {// 返回标志为0表示添加数据成功
										$.utile.tips(data[0].message+"");
										// 保存失败时页面停留在数据编辑页面
										setTimeout(
												function() {
													window.location.href = obj[0].url
												}, 1000);
									}
									if (data[0].mark == 1) {// /返回标志为1表示保存数据失败
										$.utile.tips(data[0].message);
										// 保存失败时页面停留在数据编辑页面
										setTimeout(
												function() {
													window.location.href = obj[0].url
												}, 1000);
									}
								})
							}
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
<script type="text/javascript">
function autoCsssShop(id){
	var id1 = "#"+id;
		$(id1).autocomplete("${ctx}/csssShop/ajaxCsssShop",{
			max: 20,      
	        width: 130,    
	        matchSubset:false,   
	        matchContains: true,  
			dataType: "json",
			parse: function(data) {   
		    	var rows = [];      
		        for(var i=0; i<data.length; i++){      
		           rows[rows.length] = {       
		               data:data[i]       
		           };       
		        }       
		   		return rows;   
		    }, 
			formatItem: function(row, i, total) {   
		       return "<span>编号："+row.no+"&nbsp;&nbsp;&nbsp;名称："+row.name+"&nbsp;&nbsp;</span>";   
		    },   
		    formatMatch: function(row, i, total) {   
		       return row.name;   
		    },   
		    formatResult: function(row) {   
		       return row.name;   
		    }		
		});
	$(id1).result(onRecordSelect2);
	//计算总金额
}

function onRecordSelect2(event, data, formatted) {
		$("#csssShopNo").val(data.no);
		$("#csssShopId").val(data.dbid);
}
</script>
</html>
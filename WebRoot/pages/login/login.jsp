<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ include file="../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>昆仑之星产品销售管理系统 登陆</title>
<link href="${ctx }/css/login.css" type="text/css" rel="stylesheet"/>
</head>
<body>
  <div  id="title" class="title" style="text-align: center;width: 420px;">
  	<c:if test="${empty(systemInfo.nameImage)}">
	  	${systemInfo.name }
  	</c:if>
  	<c:if test="${!empty(systemInfo.nameImage)}">
	  	<img src="${systemInfo.nameImage }"/>
  	</c:if>
  </div>
  <div class="mainbg">
    <div class="container">
      <div class="center">
        <div class="logo" >
	        <c:if test="${!empty(systemInfo.loginLogo) }">
	       		<img src="${systemInfo.loginLogo}" />
	        </c:if>
	        <c:if test="${empty(systemInfo.loginLogo) }">
	       		<img src="${ctx}/images/login/logo_05.png"  />
	        </c:if>
        </div>
        <div class="login">
          <form id="frm" action="${ctx }/j_spring_security_check" name="frm" method="post">
            <div class="login_un">
              <div class="icon_un"></div>
              <div style="float:right;"><input type="text" id="name" name="j_username"  class="user" value="admin" placeholder="用户名"/></div>
              <div class="clear"></div>
            </div>
            <div class="login_pwd">
              <div class="icon_pwd"></div>
              <div style="float:right;"><input type="password"  id="password" name="j_password" class="password" value="123456" placeholder="密码"/></div>
            </div>
          </form>
          <div  class="button">
            <a href="javascript:void(-1)" id="sbmId" onclick="if(checkForm()){$('#frm')[0].submit();}"><img src="${ctx}/images/login/button_05.png" /></a>
          </div> 
        </div>
      </div>
    </div> 
  </div>
	  <div style="color: #FFF;right:45%;bottom :12px;text-algin:center;position:absolute;"><!-- 蜀ICP备14016911号 --></div>
</body>
<script src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	 var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
	 var marginHeight=(wHeight-345)/4;
	 $("#title").css({"margin-top":marginHeight-20,"margin-bottom":marginHeight});
	 $('#sbmId').keydown(function(e){
		 if(e.keyCode==13){
			 $('#frm')[0].submit(); //处理事件
		 }
	});
	 var error="${param.error}";
	if(error=="1"){
		alert("用户名或密码错误");
		return;
	}
})
function checkForm()
{
	var name=document.getElementById("name").value;
	var password=document.getElementById("password").value;
	if(name==null||name.length<=0){
		alert("请输入用户名！");
		document.getElementById("name").focus();
		return false;
	}
	if(password==null||password.length<=0){
		alert("请输入密码！");
		document.getElementById("password").focus();
		return false;
	}
   return true;
}
</script>
</html>

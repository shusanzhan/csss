<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>西物中锐奇瑞 会员中心管理</title>
	<meta name="description" content="overview &amp; stats" />
	<link rel="stylesheet" href="${ctx}/assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/ace-fonts.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/ace.min.css" id="main-ace-style" />
	<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/assets/js/ace-extra.min.js"></script>
	<style type="text/css">
		#headerBut{
			height: 65px;
			padding-top: 14px;
			background: none;
			color: #62a8d1;
		}
		.logoInfo{
			background: rgba(0, 0, 0, 0) url("${systemInfo.infoLogo}") no-repeat scroll 0 0 / contain ;
		}
	</style>
</head>
<body class="no-skin">
<div class="head">
   <c:if test="${empty(systemInfo) }">
	   <div class="logo"></div>
   </c:if>
   <c:if test="${!empty(systemInfo) }">
	   <div class="logoInfo logo"></div>
   </c:if>
   <div class="head_nav">
	     <c:forEach var="resource" items="${ resources}" varStatus="i" begin="0" end="7">
	     	 <c:if test="${i.index==0 }" var="status">
	      	<c:set var="resourceFirst" value="${resource }"></c:set>
	     	 	<a href="javascript:void(-1)" onclick="subMenu(${resource.dbid},this)" class="active" id="menu${resource.dbid }">${resource.title }</a>
	     	 </c:if>
	     	 <c:if test="${status==false }">
	      	<a href="javascript:void(-1)" onclick="subMenu(${resource.dbid},this)" id="menu${resource.dbid }">${resource.title }</a>
	      </c:if>
	     </c:forEach>
	 </div>
	 <div  class="navbar-buttons navbar-header pull-right" >
					<ul class="nav ace-nav" style="height:71px">
						<li class="light-blue">
							<a class="dropdown-toggle" href="#" data-toggle="dropdown" id="headerBut">
								<img  src="${user.staff.photo }" class="nav-user-photo" height="50">
								<span class="user-info">
									<small>欢迎,</small>
									${user.userId }
								</span>
								<i class="ace-icon fa fa-caret-down"></i>
							</a>
							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="${ctx }/user/editSelf" target="contentUrl">
										<i class="ace-icon fa fa-cog"></i>
										个人设置
									</a>
								</li>

								<li>
									<a href="${ctx }/user/modifyPassword" target="contentUrl">
										<i class="ace-icon fa fa-user"></i>
										修改密码
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="${ctx }/j_spring_security_logout">
										<i class="ace-icon fa fa-power-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>

					</ul>
				</div>
 </div>
 	<div id="leftMenu">
		<div class="main-container" id="main-container">
			<div id="sidebar" class="sidebar responsive">
				<ul class="nav nav-list">
					${menu}
				</ul>
				<div id="sidebar-collapse" class="sidebar-toggle sidebar-collapse" onclick="leftMenu()">
					<i data-icon2="ace-icon fa fa-angle-double-right" data-icon1="ace-icon fa fa-angle-double-left" class="ace-icon fa fa-angle-double-left"></i>
				</div>
			</div>
		</div><!-- /.main-container -->
	</div>
 	<div id="mainContent"  >
	 	<c:set value="${sessionScope.user.roles }" var="roles"></c:set>
    	<c:forEach var="role" items="${roles }" begin="0" end="0">
    		<c:if test="${fn:contains(role.name,'顾问')  }" var="gwstatus">
	 			<iframe id="contentUrl" name="contentUrl" src="${ctx }/main/salerContent" scrolling="auto"  frameborder="0" class="contentUrl"  ></iframe>
	 		</c:if>
    		<c:if test="${fn:contains(role.name,'超级')||fn:contains(role.name,'客服部')  }" var="cjstatus">
	 			<iframe id="contentUrl" name="contentUrl" src="${ctx }/main/adminContent" scrolling="auto"  frameborder="0" class="contentUrl"  ></iframe>
	 		</c:if>
    		<c:if test="${fn:contains(role.name,'副总')  }" var="fzstatus">
	 			<iframe id="contentUrl" name="contentUrl" src="${ctx }/main/mangerContent" scrolling="auto"  frameborder="0" class="contentUrl"  ></iframe>
	 		</c:if>
    		<c:if test="${fn:contains(role.name,'总经理')  }" var="zjstatus">
	 			<iframe id="contentUrl" name="contentUrl" src="${ctx }/main/generalMangerContent" scrolling="auto"  frameborder="0" class="contentUrl"  ></iframe>
	 		</c:if>
    		<c:if test="${fn:contains(role.name,'物流') }" var="wlstatus">
	 			<iframe id="contentUrl" name="contentUrl" src="${ctx }/main/wlbContent" scrolling="auto"  frameborder="0" class="contentUrl"  ></iframe>
	 		</c:if>
    		<c:if test="${fn:contains(role.name,'市场部') }" var="scstatus">
	 			<iframe id="contentUrl" name="contentUrl" src="${ctx }/main/serviceContent" scrolling="auto"  frameborder="0" class="contentUrl"  ></iframe>
	 		</c:if>
    		<c:if test="${fn:contains(role.name,'售后') }" var="shstatus">
	 			<iframe id="contentUrl" name="contentUrl" src="${ctx }/main/serviceContent" scrolling="auto"  frameborder="0" class="contentUrl"  ></iframe>
	 		</c:if>
	 		<c:if test="${gwstatus==false&&cjstatus==false&&fzstatus==false&&zjstatus==false&&wlstatus==false&&scstatus==false&&shstatus==false }">
	 			<iframe id="contentUrl" name="contentUrl" src="${ctx }/main/salerContent" scrolling="auto"  frameborder="0" class="contentUrl"  ></iframe>
	 		</c:if>
	 	</c:forEach>
	 </div>

  <!--main-->
  <audio id="chatAudio"><source src="${ctx}/archives/tip.mp3"> </audio>
<script type="text/javascript">
	window.jQuery || document.write("<script src='${ctx}/assets/js/jquery.min.js'>"+"<"+"/script>");
</script>

<script src="${ctx}/assets/js/bootstrap.min.js"></script>
<!-- page specific plugin scripts -->
<script src="${ctx}/assets/js/jquery-ui.custom.min.js"></script>
		<!-- ace scripts -->
<script src="${ctx}/assets/js/ace-elements.min.js"></script>
<script src="${ctx}/assets/js/ace.min.js"></script>
<script src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=twitter"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		bindClick();
		 setInterval('showMessageBox()',5000);
	})
	function leftMenu(){
		var cla=$("#sidebar").attr("class");
		if(cla.indexOf("menu-min")==-1){
			$("#mainContent").css("left",50);
		}else{
			$("#mainContent").css("left",195);
		}
	}
	function subMenu(dbid,obj){
		if(null!=obj&&obj!=undefined){
			var head=$(".head_nav").find(".active").removeClass("active");
			$(obj).addClass("active");
		}
		$.post("${ctx}/main/submenu2?dbid="+dbid+"&dateStamp="+new Date(),{},function(data){
			if(data=="error"){
				$(".nav-list").append();
			}else{
				$(".nav-list").text("");
				$(".nav-list").append(data);
				bindClick();
			}
		})
	}
	function bindClick(){
		$("#sidebar ul li").each(function(index){
            $(this).click(function(){
              var dd=$(this).find("ul");
              if(null==dd||dd==undefined||dd.length<=0){
            	  $("#sidebar .active").removeClass("active");
            	  $("#sidebar .open").removeClass("open");
            	  $(this).addClass("active");
            	  var parent=$(this).parent()
            	  if($(this).parent().attr("class").indexOf("submenu")==-1){
            		  $("#sidebar li  ul").each(function(index){
	            		  var ss=$(this).parent().attr("class");
	            		  if(ss.indexOf("open")==-1){
	            			if($(this).css("display")=="block"){
	            				$(this).css("display",'none');
	            			}  
	            		  }
	            	 }) 
            	  }
              }else{
            	  $(dd).find("li").each(function(index){
            		  $(this).click(function(){
            			  $("#sidebar .active").removeClass("active");
            			  $("#sidebar .open").removeClass("open");
            			  $("#sidebar .nav-show").removeClass("nav-show");
            			  $(this).addClass("active");
			            	 var parentLi= $(dd).parent("li");
			            	 $(parentLi).addClass("active");
			            	 $(parentLi).addClass("open");
		            	  $("#sidebar li  ul").each(function(index){
		            		  var ss=$(this).parent().attr("class");
		            		  if(ss.indexOf("open")==-1){
		            			if($(this).css("display")=="block"){
		            				$(this).css("display",'none');
		            			}  
		            		  }
		            	 }) 
            			
            		  })
            	  })
              }
            })
          }); 
	}
	function showMessageBox(){
		$.post("${ctx}/message/noticeMessage",{},function (data){
			if(data.status==true){
				$('#chatAudio')[0].play();
				var content='<div style="text-align:center;">'+
				              '<a href="${ctx}'+data.url+'" target="contentUrl">'+data.title+'</a>'+
							  '<div style="text-indent: 24px;text-align: left;">'+data.content+'</div>'+
							 '</div>';
				$.utile.MessageBox(content,data.dbid);
				
			}else if(data.status==false){
			}
		})
	}
</script>
</body>
</html>

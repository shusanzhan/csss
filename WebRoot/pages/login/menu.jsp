<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	.cont{
		margin: 5px;width: 200px;height: 20px; border: 1px solid red;
	}
</style>
<link href="${ctx }/css/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
</head>
<body>
  <div class="drop_down_menu">
    <ul>
      <li><a href="javascript:void(-1)">编 辑编 辑编 辑</a></li>
      <li class="drop_down_menu_active"><a href="javascript:void(-1)">编 辑</a></li>
      <li><a href="javascript:void(-1)">编 辑</a></li>
      <li><a href="javascript:void(-1)">编 辑</a></li>
    </ul>
  </div>
 <table cellpadding="0" cellspacing="0" border="1" style="margin-top: 50px;">
	<tr><td width="200"  height="30" >
		<div id="hh"  onmousemove="fn(this,event)" onmouseout="hiden(this)" style="margin: 5px;width: 200px;height: 20px; border: 1px solid red;">shushushushushu
			<div class="drop_down_menu hiden" onmousemove="show(this)" onmouseout="hi(this)" >
		    <ul>
		      <li class="drop_down_menu_active"><a href="javascript:void(-1)">编 辑</a></li>
		      <li ><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		    </ul>
		  </div>
		</div>
		   
		</td>  
	</tr>
	<tr><td width="200" height="30"  ><div  onmouseout="hiden(this)" onmousemove="fn(this)" style="margin: 5px;width: 200px;height: 20px; border: 1px solid red;">shushushushushu
		 <div class="drop_down_menu hiden" onmousemove="show(this)" onmouseout="hi(this)" >
		    <ul>
		      <li class="drop_down_menu_active"><a href="javascript:void(-1)">编 辑</a></li>
		      <li ><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		    </ul>
		  </div>
	</div>
	</td></tr>
	<!-- <tr><td width="200" height="30"  ><div  class="cont">shushushushushu
		 <div class="drop_down_menu hiden"  >
		    <ul>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li class="drop_down_menu_active"><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		    </ul>
		  </div>
	</div>
	</td></tr>
	<tr><td width="200" height="30"  ><div  class="cont">shushushushushu
		 <div class="drop_down_menu hiden"  >
		    <ul>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li class="drop_down_menu_active"><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		    </ul>
		  </div>
	</div>
	</td></tr>
	<tr><td width="200" height="30"  ><div  class="cont">shushushushushu
		 <div class="drop_down_menu hiden"  >
		    <ul>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li class="drop_down_menu_active"><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		    </ul>
		  </div>
	</div>
	</td></tr>
	<tr><td width="200" height="30"  ><div  class="cont">shushushushushu
		 <div class="drop_down_menu hiden"  >
		    <ul>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li class="drop_down_menu_active"><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		      <li><a href="javascript:void(-1)">编 辑</a></li>
		    </ul>
		  </div>
	</div>
	</td></tr> -->
  </table>
</body>
<script type="text/javascript">
	function fn(va){
		var dd=$(".show");
		if(null!=dd){
			$(dd).removeClass("show").addClass("hiden");
		}
		var vs=$(va).find(".drop_down_menu").removeClass("hiden").addClass("show");
	}
	 function hiden(va){
		var vs=$(va).find(".drop_down_menu").removeClass("show").addClass("hiden");
	}
	 function show(va){
		 var vs=$(va).find(".hiden").removeClass("hiden").addClass("show");
	 }
	 function hi(va){
		 var vs=$(va).find(".show").removeClass("show").addClass("hiden");
	 }
</script>
</html>
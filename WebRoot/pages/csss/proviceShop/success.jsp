<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet"/>
<title>上传成功</title>
</head>
<body>
<div class="location">
	<img src="${ctx}/images/homeIcon.png"/> &nbsp;
	<a href="javascript:void(-1);" onclick="window.parent.location.href='${ctx}/main/index'">首页</a>-
	<a href="javascript:void(-1);" onclick="">上传成功</a>
</div>
<div class="line"></div>
	<div class="alert alert-info">
		<strong>提示!</strong> 上传数据成功！
	</div>
<div class="listOperate">
	<div class="operate">
		<a class="but button" href="${ctx }/proviceShop/queryList">返回</a>
   </div>	
</div>
</body>
</html>
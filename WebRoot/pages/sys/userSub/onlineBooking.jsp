<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" type="text/css"  href="${ctx }/css/weixin.css"/>
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript"  src="${ctx }/widgets/utile/weixin.js"></script>
<title>试乘试驾</title>
</head>
<body>
  <div class="head">
    <div  class="title">西物奇瑞试乘试驾在线预约</div>
    <div  class="titlePhone"><a href="tel:02887462277"><img src="${ctx }/images/weixin/phoneIcon.png"></a></div>
    <div class="clear"></div>
  </div>
  <div class="formContent">
  	<div id="message" class="" style="display: none;">
  	</div>
  	<form action="" id="frmId" method="post">
  		<input type="hidden" value="${param.microId }" id="microId" name="onlineBooking.microId">
  		<input type="hidden" value="1" id="bookingType" name="onlineBooking.bookingType">
  		<div class="dataDiv" id="areaLabel">
  			<label><span style="color: red;">*</span>车型</label>
  			<select id="carSeriy" name="carSeriy"  style="border:1px solid #c7c7c7; width: 80px;margin-left: 12px;height: 28px;" onchange="ajaxCarModel('carSeriy')">
  			  <option value="">请选择车型</option>
		      <c:forEach var="carSeriy" items="${carSeriys }">
	  			  <option value="${carSeriy.dbid }">${carSeriy.name }</option>
		      </c:forEach>
		    </select>
  		</div>
  		<div class="dataDiv">
  			<label><span style="color: red;">*</span>联系人</label>
  			<input type="text"  placeholder="请输入联系人" name="onlineBooking.name" id="name" class="textfield" required="required"/>
  		</div>
  		<div class="dataDiv">
  			<label ><span style="color: red;">*</span>手机</label>
  			<input type="text" placeholder="请输入手机" name="onlineBooking.mobilePhone" id="mobilePhone"  class="textfield" required="required"/>
  		</div>
  		<div class="dataDiv">
  			<label ><span style="color: red;">*</span>预约时间</label>
  			<input type="date" name="onlineBooking.bookingDate" id="bookingDate" class="textfield" style="width: 185px;" required="required"/>
  		</div>
  		<div class="dataDiv">
  			<label >备注</label>
  			<textarea name="onlineBooking.note" id="note" cols="45" rows="5" class="jq_watermark" placeholder="亲，您有什么非分的要求尽管提，只要我们能办到，一定竭尽所能。"></textarea>
  		</div>
  		<div class="dataDiv">
  			<div class="button" id="subButton" onclick="ajaxSubmit('${ctx}/onlineBookingWeixin/saveOnlineBooking')"><a href="javascript:void(-1)">提交预约</a></div>
  		</div>
  	</form>
  </div>
</body>
</html>

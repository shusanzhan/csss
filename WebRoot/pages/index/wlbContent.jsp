<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/css/bootstrap/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap/fullcalendar.css" />	
<link rel="stylesheet" href="${ctx}/css/bootstrap/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap/unicorn.grey.css" class="skin-color" />
<link href="${ctx }/css/common.css" type="text/css" rel="stylesheet"/>
<style type="text/css">
.table-bordered td{
	height: 18px;
    padding-bottom: 3px;
}
.comments a{
color: #FFFFFF;
}
</style>
<title>物流部系统主页</title>
</head>
<body>
<div class="row-fluid">
	<div style="text-align: center;" class="span12 center">					
		<ul class="stat-boxes">
			<li>
				<div class="right">
					<strong>${countFinished }</strong>
					已经处理交车预约
				</div>
			</li>
			<li>
				<div class="right">
					<strong>${countFinishedSqlNoCar }</strong>
					已经处理 无车订单
				</div>
			</li>
			<li>
				<div class="right">
					<strong>${countFinishedSqlHasCar }</strong>
					已经处理 有车订单
				</div>
			</li>
			<li>
				<div class="right" >
					<strong style="color: red;">${countNot }</strong>
					未处理交车预约
				</div>
			</li>
		</ul>
	</div>	
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="widget-box">
			<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>未处理交车预约</h5><span class="label label-info tip-left" data-original-title="${fn:length(customers) } 未处理交车预约客户">${fn:length(customers) }</span></div>
			<div class="widget-content nopadding">
				<table class="table table-bordered">
				<c:if test="${empty(customers) }">
					<thead>
						<tr>
							无未处理的交车预约
						</tr>
					</thead>
				</c:if>
				<c:if test="${!empty(customers) }">
					<thead>
						<tr>
							<th style="width: 60px;">编号</th>
							<th style="width: 60px;"> 姓名</th>
							<th style="width: 200px;">车型</th>
							<th style="width: 60px;">颜色</th>
							<th style="width: 60px;">业务员</th>
							<th style="width: 80px;">提交时间</th>
							<th style="width: 60px;">处理状态</th>
							<th style="width: 60px;">操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="customer" items="${customers }" end="8">
						<tr>
							<td  style="text-align: center;width: 60px;">${customer.sn }</td>
							<td style="text-align: center;">${customer.name }</td>
							<c:set value="${customer.customerBussi.carSeriy.name}${ customer.customerBussi.carModel.name }" var="carModel"></c:set>
							<td style="text-align: center;">${carModel }</td>
							<td style="text-align: center;">
								${customer.customerPidBookingRecord.carColor.name }
							</td>
							<td style="text-align: center;">
								${customer.bussiStaff}
							</td>
							<td style="text-align: center;">
								<fmt:formatDate value="${customer.customerPidBookingRecord.createTime }" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td style="text-align: center;">
								<c:if test="${customer.customerPidBookingRecord.wlStatus==1 }">
									<span style="color: red;">等待处理</span>
								</c:if>
								<c:if test="${customer.customerPidBookingRecord.wlStatus==2 }">
									<span style="color:blue;">已经处理</span>
								</c:if>
							</td>
							<td style="text-align: center;">
								<c:if test="${customer.customerPidBookingRecord.pidStatus==1}">
									<a href="javascript:void(-1)" class="aedit" onclick="window.location.href='${ctx}/customerPidBookingRecord/wlbEdit?customerId=${customer.dbid }'">交车预约</a>
								</c:if>
								<c:if test="${customer.customerPidBookingRecord.pidStatus==6}">
									<a href="javascript:void(-1)" class="aedit" onclick="window.location.href='${ctx}/customerPidBookingRecord/wlbEdit?customerId=${customer.dbid }'">交车预约</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					</tbody>
					</c:if>
				</table>
				<ul class="recent-posts" style="margin-top: 5px;">
					<li class="viewall">
						<a href="${ctx }/customerPidBookingRecord/queryWlbWatingList?wlStatus=1" class="tip-top" data-original-title="点击查看更多"> 点击查看更多 </a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="widget-box">
			<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>无车订单统计</h5><span class="label label-info tip-left" data-original-title="${countFinishedSqlNoCar } 未处理交车预约客户">${countFinishedSqlNoCar }</span></div>
			<div class="widget-content nopadding">
				<table class="table table-bordered">
				<c:if test="${empty(hasNoCarOrders) }">
					<thead>
						<tr>
							无无车订单信息
						</tr>
					</thead>
				</c:if>
				<c:if test="${!empty(hasNoCarOrders) }">
					<thead>
						<tr>
							<th style="width: 10px;">车系</th>
							<th style="width: 200px;">车型</th>
							<th style="width: 60px;">颜色</th>
							<th style="width: 60px;">数量</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="hasNoCarOrder" items="${hasNoCarOrders }" >
						<tr>
							
							<td  style="text-align: center;width: 60px;">
									${hasNoCarOrder.carSeriyName }
							</td>
							<td style="text-align: center;">
								${hasNoCarOrder.carModelName }
							</td>
							<td style="text-align: center;">${hasNoCarOrder.color }</td>
							<td style="text-align: center;">${hasNoCarOrder.countNum }</td>
						</tr>
					</c:forEach>
					</tbody>
					</c:if>
				</table>
				<ul class="recent-posts" style="margin-top: 5px;">
					<li class="viewall">
						<a href="${ctx }/customerPidBookingRecord/queryWlbWatingList?hasCarOrder=2" class="tip-top" data-original-title="点击查看更多"> 点击查看更多 </a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>

</body>
<script src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
<script src="${ctx}/widgets/bootstrap/bootstrap.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.ui.custom.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.peity.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
</html>

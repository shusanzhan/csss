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
<title>后台表单页面</title>
</head>
<body>
	<div class="container-fluid">
				<div class="row-fluid">
					<div style="text-align: center;" class="span12 center">					
						<ul class="stat-boxes">
							<li>
								<div class="right">
									<strong>${newsCount }</strong>
									新登记
								</div>
							</li>
							<li>
								<div class="right">
									<strong>${canncelCount }</strong>
									流失客户
								</div>
							</li>
							<li>
								<div class="right">
									<strong>${orderCount }</strong>
									订单客户
								</div>
							</li>
							<li>
								<div class="right">
									<strong>${waitingCar }</strong>
									待交车客户
								</div>
							</li>
							<li>
								<div class="right">
									<strong>${customerSuccess }</strong>
									成交客户
								</div>
							</li>
						</ul>
					</div>	
				</div>
		<div class="row-fluid">
				<div class="span6">
				<div class="widget-box">
					<div id="container1" style="min-width: 310px; height: 300px; max-width: 620px; margin: 0 auto"></div>
				</div>
			</div>
			<div class="span6">
				<div class="widget-box">
					<div id="container2" style="min-width: 310px; height: 300px; max-width: 620px; margin: 0 auto"></div>
				</div>
			</div>
		</div>
				<div class="row-fluid">
					<!-- <div class="span12">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Site Statistics</h5><div class="buttons"><a class="btn btn-mini" href="#"><i class="icon-refresh"></i> Update stats</a></div></div>
							<div class="widget-content">
								<div class="row-fluid">
								<div class="span4">
									<ul class="site-stats">
										<li><i class="icon-user"></i> <strong>1433</strong> <small>Total Users</small></li>
										<li><i class="icon-arrow-right"></i> <strong>16</strong> <small>New Users (last week)</small></li>
										<li class="divider"></li>
										<li><i class="icon-shopping-cart"></i> <strong>259</strong> <small>Total Shop Items</small></li>
										<li><i class="icon-tag"></i> <strong>8650</strong> <small>Total Orders</small></li>
										<li><i class="icon-repeat"></i> <strong>29</strong> <small>Pending Orders</small></li>
									</ul>
								</div>
								<div class="span8">
									<div class="chart" style="padding: 0px; position: relative;"><canvas class="base" width="696" height="300"></canvas><canvas class="overlay" width="696" height="300" style="position: absolute; left: 0px; top: 0px;"></canvas><div style="font-size:smaller" class="tickLabels"><div style="color:#545454" class="xAxis x1Axis"><div style="position:absolute;text-align:center;left:-17px;top:280px;width:87px" class="tickLabel">0</div><div style="position:absolute;text-align:center;left:81px;top:280px;width:87px" class="tickLabel">2</div><div style="position:absolute;text-align:center;left:180px;top:280px;width:87px" class="tickLabel">4</div><div style="position:absolute;text-align:center;left:279px;top:280px;width:87px" class="tickLabel">6</div><div style="position:absolute;text-align:center;left:377px;top:280px;width:87px" class="tickLabel">8</div><div style="position:absolute;text-align:center;left:476px;top:280px;width:87px" class="tickLabel">10</div><div style="position:absolute;text-align:center;left:575px;top:280px;width:87px" class="tickLabel">12</div></div><div style="color:#545454" class="yAxis y1Axis"><div style="position:absolute;text-align:right;top:255px;right:677px;width:19px" class="tickLabel">-1.5</div><div style="position:absolute;text-align:right;top:213px;right:677px;width:19px" class="tickLabel">-1.0</div><div style="position:absolute;text-align:right;top:171px;right:677px;width:19px" class="tickLabel">-0.5</div><div style="position:absolute;text-align:right;top:129px;right:677px;width:19px" class="tickLabel">0.0</div><div style="position:absolute;text-align:right;top:86px;right:677px;width:19px" class="tickLabel">0.5</div><div style="position:absolute;text-align:right;top:44px;right:677px;width:19px" class="tickLabel">1.0</div><div style="position:absolute;text-align:right;top:2px;right:677px;width:19px" class="tickLabel">1.5</div></div></div><div class="legend"><div style="position: absolute; width: 54px; height: 44px; top: 9px; right: 9px; background-color: rgb(249, 249, 249); opacity: 0.85;"> </div><table style="position:absolute;top:9px;right:9px;;font-size:smaller;color:#545454"><tbody><tr><td class="legendColorBox"><div style="border:1px solid #ccc;padding:1px"><div style="width:4px;height:0;border:5px solid #BA1E20;overflow:hidden"></div></div></td><td class="legendLabel">sin(x)</td></tr><tr><td class="legendColorBox"><div style="border:1px solid #ccc;padding:1px"><div style="width:4px;height:0;border:5px solid #459D1C;overflow:hidden"></div></div></td><td class="legendLabel">cos(x)</td></tr></tbody></table></div></div>
								</div>	
								</div>							
							</div>
						</div>					
					</div> -->
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>次日需跟踪客户</h5><span class="label label-info tip-left" data-original-title="${fn:length(theNextNendTracks) } 客户">${fn:length(theNextNendTracks) }</span></div>
							<div class="widget-content nopadding">
								<ul class="recent-comments">
								<c:if test="${empty(theNextNendTracks) }">
									<li>
										次日无需跟踪客户
									</li>
								</c:if>
								<c:if test="${!empty(theNextNendTracks) }">
									<c:forEach var="customer" items="${theNextNendTracks }" end="8">
										<li>
											<div class="comments">
												<span class="user-info"> 
													客户:${customer.name }  
													手机：<span style="color: green;font-size: 14px;">${customer.mobilePhone }</span>
													创建时间:<fmt:formatDate value="${customer.createFolderTime }" pattern="yyyy年MM月dd日"/> 
											   </span>
												  
												<p>
													距离超时还有:
													<span style="color: red;"><fmt:formatDate value="${customerTrack.nextReservationTime }" pattern="yyyy年MM月dd日"/></span>
												</p>
												<a class="btn btn-primary btn-mini" href="javascript:void(-1)" onclick="$.utile.openDialog('${ctx}/customerTrack/add?customerId=${customer.dbid }&typeRedirect=1','添加跟进记录',900,500)">添加跟进记录</a> 
											</div>
										</li>
									</c:forEach>
									</tbody>
									</c:if>
								</ul>
								<ul class="recent-posts" style="margin-top: 5px;">
									<li class="viewall">
										<a href="${ctx }/customer/customerShoppingRecordqueryList" class="tip-top" data-original-title="点击查看更多"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>未来3天需跟进客户</h5><span class="label label-info tip-left" data-original-title="${fn:length(customerTracks) } 需跟进客户">${fn:length(customerTracks) }</span></div>
							<div class="widget-content nopadding">
								<ul class="recent-comments">
								<c:if test="${empty(customerTracks) }">
								<li>
										<div class="comments">
											<p>
												无需跟进记录客户...
											</p>
										</div>
									</li>
								</c:if>
								  <c:forEach var="customerTrack"  items="${customerTracks }" end="4">
									<li>
										<div class="comments">
											<span class="user-info"> 
												客户:${customerTrack.customer.name }  
												跟进时间:<fmt:formatDate value="${customerTrack.trackDate }" pattern="yyyy年MM月dd日"/> 
												联系电话：${customerTrack.customer.mobilePhone }  
										   </span>
											<span style="color: red;"><fmt:formatDate value="${customerTrack.nextReservationTime }" pattern="yyyy年MM月dd日"/></span>  
											<p>
												客户车型：
												<c:set value="${customerTrack.customer.customerBussi.carSeriy.name}${ customerTrack.customer.customerBussi.carModel.name }" var="carModel"></c:set>
												${carModel}  
												跟进方法：
												<c:if test="${customerTrack.trackMethod==1 }">
													电话
												</c:if>
												<c:if test="${customerTrack.trackMethod==2 }">
													短信
												</c:if>
												<c:if test="${customerTrack.trackMethod==3 }">
													到店
												</c:if>
												<c:if test="${customerTrack.trackMethod==4 }">
													回访
												</c:if>
												&nbsp;&nbsp;&nbsp;&nbsp;
												跟进内容：${customerTrack.trackContent }
											</p>
											<a class="btn btn-primary btn-mini" href="javascript:void(-1)" onclick="$.utile.openDialog('${ctx}/customerTrack/add?customerId=${customerTrack.customer.dbid }&typeRedirect=1','添加跟进记录',900,500)">添加跟进记录</a> 
											<a class="btn btn-success btn-mini" href="javascript:void(-1)"  onclick="window.location.href='${ctx}/customerTrack/personCustomerTrack?customerId=${customerTrack.customer.dbid}'">查看</a> 
										</div>
									</li>
								</c:forEach> 
									<li class="viewall">
										<a href="${ctx }/customerTrack/queryList" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					
				</div> 
				<div class="row-fluid">
				<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>最近一周登记客户</h5><span class="label label-info tip-left" data-original-title="${fn:length(customers) } 客户">${fn:length(customers) }</span></div>
							<div class="widget-content nopadding">
								<table class="table table-bordered">
								<c:if test="${empty(customers) }">
									<thead>
										<tr>最近无登记客户
										</tr>
									</thead>
								</c:if>
								<c:if test="${!empty(customers) }">
									<thead>
										<tr>
											<th>姓名</th>
											<th>联系电话</th>
											<th>意向级别</th>
											<th>成交状态</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach var="customer" items="${customers }" end="8">
										<tr>
											<td style="text-align: center;">${customer.name }</td>
											<td style="text-align: center;">${customer.mobilePhone }</td>
											<td style="text-align: center;">${customer.customerPhase.name }</td>
											<td style="text-align: center;">
												<c:if test="${customer.lastResult==0 }">
													客户登记
												</c:if>
												<c:if test="${customer.lastResult==1 }">
													客户成交
												</c:if>
												<c:if test="${customer.lastResult==2 }">
													客户流失（其他）
												</c:if>
												<c:if test="${customer.lastResult==3 }">
													客户流失（取消）
												</c:if>
											</td>
										</tr>
									</c:forEach>
									</tbody>
									</c:if>
								</table>
								<ul class="recent-posts" style="margin-top: 5px;">
									<li class="viewall">
										<a href="${ctx }/customer/customerShoppingRecordqueryList" class="tip-top" data-original-title="点击查看更多"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>跟踪超时情况</h5></div>
							<div class="widget-content nopadding">
								<table class="table table-bordered">
								<c:if test="${empty(overTimeUserCounts) }">
									<thead>
										<tr>最近无超时统计
										</tr>
									</thead>
								</c:if>
								<c:if test="${!empty(overTimeUserCounts) }">
									<thead>
										<tr>
											<th>客户姓名</th>
											<th>次日回访超时</th>
											<th>跟踪超时</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach var="overTimeUserCount" items="${overTimeUserCounts }" end="8">
										<tr>
											<td style="text-align: center;">${overTimeUserCount.name }</td>
											<td style="text-align: center;">${overTimeUserCount.theNextDayTimeOutNum }</td>
											<td style="text-align: center;">${overTimeUserCount.trackingTimeOutNum }</td>
										</tr>
									</c:forEach>
									</tbody>
									</c:if>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>待交车客户</h5><span class="label label-info tip-left" data-original-title="${fn:length(waitingdCars) } 待交车客户">${fn:length(waitingdCars) }</span></div>
							<div class="widget-content nopadding">
								<table class="table table-bordered">
								<c:if test="${empty(waitingdCars) }">
									<thead>
										<tr>
											<th>加油哦！还没有待交车</th>
										</tr>
									</thead>
								</c:if>
								<c:if test="${!empty(waitingdCars) }">
									<thead>
										<tr>
											<th>姓名</th>
											<th>联系电话</th>
											<th>物流状态</th>
											<th width="120">交车日期</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="customer" items="${waitingdCars }" end="7">
											<tr>
												<td style="text-align: center;">${customer.name }</td>
												<td style="text-align: center;">${customer.mobilePhone }</td>
												<td>
												<c:if test="${customer.customerPidBookingRecord.wlStatus ==0 }">
													未交物流部
												</c:if>
												<c:if test="${customer.customerPidBookingRecord.wlStatus ==1 }">
													等待处理
												</c:if>
												<c:if test="${customer.customerPidBookingRecord.wlStatus ==2 }">
													已经处理
												</c:if>
												</td>
												<td>
													<fmt:formatDate value="${customer.customerPidBookingRecord.bookingDate }" pattern="yyyy年MM月dd日 HH:mm" />
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</c:if>
								</table>
									<ul class="recent-comments">
									<li class="viewall">
											<a href="${ctx }/customerPidBookingRecord/queryWatingList" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>近期流失客户</h5><span class="label label-info tip-left" data-original-title="${fn:length(outFlowerCustomers) } 流失客户">${fn:length(outFlowerCustomers) }</span></div>
							<div class="widget-content nopadding">
								<div class="widget-content nopadding">
								<table class="table table-bordered">
								<c:if test="${empty(outFlowerCustomers) }">
									<thead>
										<tr>
											<th>干得不错！没有流失客户</th>
										</tr>
									</thead>
								</c:if>
								<c:if test="${!empty(outFlowerCustomers) }">
									<thead>
										<tr>
											<th>姓名</th>
											<th>联系电话</th>
											<th>结果</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach var="customer" items="${outFlowerCustomers }" end="7">
										<tr>
											<td style="text-align: center;">${customer.name }</td>
											<td style="text-align: center;">${customer.mobilePhone }</td>
											<td style="text-align: center;">
											<c:if test="${ customer.lastResult==2}">
												客户流失购买其他车
											</c:if>
											<c:if test="${ customer.lastResult==3}">
												客户流失构成计划取消
											</c:if>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</c:if>
								</table>
								<ul class="recent-comments">
									<li class="viewall">
											<a href="${ctx }/customer/queryOutFlow" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div> 
			</div>
</body>
<script src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
<script src="${ctx }/widgets/highcharts/highcharts.js"></script>
<script src="${ctx }/widgets/highcharts/modules/exporting.js"></script>
<script src="${ctx}/widgets/bootstrap/bootstrap.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.ui.custom.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.peity.min.js"></script>
<script type="text/javascript" src="${ctx }/widgets/utile/utile.js"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx }/widgets/artDialog/plugins/iframeTools.source.js"></script>
<!-- <script type="text/javascript">
window.onresize=function(){
	 $("#contentUrl").width(window.document.documentElement.clientWidth-145+"px");
}
</script> -->
<script type="text/javascript">
$(function () {
		 var data=eval("[ ['O',${levelCO}], ['A',${levelCA}], ['B',${levelCB}], ['C',${levelCC}]]");
		 $('#container1').highcharts({
	         chart: {
	             type: 'column'
	         },
	         title: {
	             text: '月度各级客户量'
	         },
	       
	         xAxis: {
	             type: 'category',
	             labels: {
	                 rotation: -45,
	                 align: 'right',
	                 style: {
	                     fontSize: '13px',
	                     fontFamily: 'Verdana, sans-serif'
	                 }
	             }
	         },
	         yAxis: {
	             min: 0,
	             title: {
	                 text: '客户量（人）'
	             }
	         },
	         legend: {
	             enabled: false
	         },
	         tooltip: {
	             pointFormat: ': <b>{point.y:.1f} 人</b>',
	         },
	         series: [{
	             name: 'Population',
	             data: data,
	             dataLabels: {
	                 enabled: true,
	                 rotation: -90,
	                 color: '#FFFFFF',
	                 align: 'right',
	                 x: 4,
	                 y: 10,
	                 style: {
	                     fontSize: '13px',
	                     fontFamily: 'Verdana, sans-serif',
	                     textShadow: '0 0 3px black'
	                 }
	             }
	         }]
	     });
		 
		 var data2=eval("${jsonCarData}");
		 $('#container2').highcharts({
		    chart: {
		        type: 'column'
		    },
		    title: {
		        text: '自有车型柱状图'
		    },
		  
		    xAxis: {
		        type: 'category',
		        labels: {
		            rotation: -45,
		            align: 'right',
		            style: {
		                fontSize: '13px',
		                fontFamily: 'Verdana, sans-serif'
		            }
		        }
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text: '数量（台）'
		        }
		    },
		    legend: {
		        enabled: false
		    },
		    tooltip: {
		        pointFormat: ': <b>{point.y:.1f} 台</b>',
		    },
		    series: [{
		        name: 'Population',
		        data: data2,
		        dataLabels: {
		            enabled: true,
		            rotation: -90,
		            color: '#FFFFFF',
		            align: 'right',
		            x: 4,
		            y: 10,
		            style: {
		                fontSize: '13px',
		                fontFamily: 'Verdana, sans-serif',
		                textShadow: '0 0 3px black'
		            }
		        }
		    }]
		});
});
</script> 
</html>

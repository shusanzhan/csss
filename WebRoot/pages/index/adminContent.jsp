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
<title>系统管理员客户</title>
</head>
<body>
	<div class="container-fluid">
				<div class="row-fluid">
					<div style="text-align: center;" class="span12 center">					
						<ul class="stat-boxes">
							<li>
								<div class="right">
									<strong>${countMember }</strong>
									系统会员总数
								</div>
							</li>
							<li>
								<div class="right">
									<strong>${countOnlineBooking }</strong>
									系统在线预约人数
								</div>
							</li>
							<li>
								<div class="right">
									<strong>${countMmergencyhelp }</strong>
									系统发起紧急救援人数
								</div>
							</li>
							<li>
								<div class="right">
									<strong>${countZ }</strong>
									系统赠送总积分
								</div>
							</li>
							<li>
								<div class="right">
									<strong>${countF }</strong>
									系统可用总积分
								</div>
							</li>
						</ul>
					</div>	
				</div>
		<div class="row-fluid">
				<div class="span12">
				<div class="widget-box">
					<div id="container1" style="min-width: 310px; height: 300px; max-width: 620px; margin: 0 auto"></div>
				</div>
			</div>
		</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>最近一周在线预约客户</h5><span class="label label-info tip-left" data-original-title="${fn:length(last7OnlineBookings) } 在线预约客户">${fn:length(last7OnlineBookings) }</span></div>
							<div class="widget-content nopadding">
								<ul class="recent-comments">
								<c:if test="${empty(last7OnlineBookings) }">
								<li>
										<div class="comments">
											<p>
												无在线预约客户...
											</p>
										</div>
									</li>
								</c:if>
								  <c:forEach var="onlineBooking"  items="${last7OnlineBookings }" end="4">
									<li>
										<div class="comments">
											<span class="user-info"> 
												客户:${onlineBooking[2] }  
												预约申请时间:<fmt:formatDate value="${onlineBooking[11] }" pattern="yyyy年MM月dd日"/> 
												预约时间:
										   </span>
											<span style="color: red;"><fmt:formatDate value="${onlineBooking[4] }" pattern="yyyy年MM月dd日"/></span>  
											<p>
												预约类型：
												<c:if test="${onlineBooking[9]==1 }">
													预约试乘试驾
												</c:if>
												<c:if test="${onlineBooking[9]==2 }">
													预约保养维修
												</c:if>
												<c:if test="${onlineBooking[9]==3 }">
													预约续保年审
												</c:if>
												<c:if test="${onlineBooking[9]==4 }">
													旧车置换
												</c:if>
												&nbsp;&nbsp;&nbsp;&nbsp;	
															
												<c:if test="${onlineBooking[10]==1 }">
													<span style="color: red;">未处理</span>
												</c:if>
												<c:if test="${onlineBooking[10]==2 }">
													<span style="color: blue;">已经处理</span>
												</c:if>
											</p>
											<a class="btn btn-primary btn-mini" href="javascript:void(-1)" onclick="window.location.href='${ctx }/onlineBooking/view?dbid=${onlineBooking[0]}'">查看详细</a>
											<c:if test="${onlineBooking[10]==1 }"> 
												<a class="btn btn-success btn-mini" href="javascript:void(-1)"  onclick="window.location.href='${ctx }/onlineBooking/dealWithOnlineBooking?dbid=${onlineBooking[0]}'">处理</a>
											</c:if> 
										</div>
									</li>
								</c:forEach> 
									<li class="viewall">
										<a href="${ctx }/onlineBooking/queryList" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>未来一周需要处理预约</h5><span class="label label-info tip-left" data-original-title="${fn:length(feature7OnlineBookings) } 待交车客户">${fn:length(feature7OnlineBookings) }</span></div>
							<div class="widget-content nopadding">
							<ul class="recent-comments">
								<c:if test="${empty(feature7OnlineBookings) }">
								<li>
										<div class="comments">
											<p>
												无在线预约客户...
											</p>
										</div>
									</li>
								</c:if>
								  <c:forEach var="onlineBooking"  items="${feature7OnlineBookings }" end="4">
									<li>
										<div class="comments">
											<span class="user-info"> 
												客户:${onlineBooking[2] }  
												预约申请时间:<fmt:formatDate value="${onlineBooking[11] }" pattern="yyyy年MM月dd日"/> 
												预约时间:
										   </span>
											<span style="color: red;"><fmt:formatDate value="${onlineBooking[4] }" pattern="yyyy年MM月dd日"/></span>  
											<p>
												预约类型：
												<c:if test="${onlineBooking[9]==1 }">
													预约试乘试驾
												</c:if>
												<c:if test="${onlineBooking[9]==2 }">
													预约保养维修
												</c:if>
												<c:if test="${onlineBooking[9]==3 }">
													预约续保年审
												</c:if>
												<c:if test="${onlineBooking[9]==4 }">
													旧车置换
												</c:if>
												&nbsp;&nbsp;&nbsp;&nbsp;	
															
												<c:if test="${onlineBooking[10]==1 }">
													<span style="color: red;">未处理</span>
												</c:if>
												<c:if test="${onlineBooking[10]==2 }">
													<span style="color: blue;">已经处理</span>
												</c:if>
											</p>
											<a class="btn btn-primary btn-mini" href="javascript:void(-1)" onclick="window.location.href='${ctx }/onlineBooking/view?dbid=${onlineBooking[0]}'">查看详细</a>
										</div>
									</li>
								</c:forEach> 
									<li class="viewall">
										<a href="${ctx }/onlineBooking/queryList" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>紧急救援客户</h5><span class="label label-info tip-left" data-original-title="${fn:length(emergencyHelps) } 紧急救援客户">${fn:length(emergencyHelps) }</span></div>
							<div class="widget-content nopadding">
								<div class="widget-content nopadding">
								<ul class="recent-comments">
								<c:if test="${empty(emergencyHelps) }">
								<li>
										<div class="comments">
											<p>
												无紧急救援客户...
											</p>
										</div>
									</li>
								</c:if>
								<c:if test="${!empty(emergencyHelps) }">
								  <c:forEach var="emergencyHelp"  items="${emergencyHelps }" end="4">
									<li>
										<div class="comments">
											<span class="user-info"> 
												客户于:<fmt:formatDate value="${emergencyHelp.createTime }" pattern="yyyy-MM-dd HH:mm"/>
												在:${emergencyHelp.address }发起紧急救援
												客户的联系电话:
										   </span>
											<span style="color: red;">${emergencyHelp.phone }</span>  
											请尽快及时处理。
											<p>
												处理状态：
												<c:if test="${ emergencyHelp.status==false}">
													<span style="color: red;">未处理</span>
												</c:if>
											</p>
											<a class="btn btn-primary btn-mini" href="javascript:void(-1)" onclick="window.location.href='${ctx}/emergencyHelp/edit?dbid=${emergencyHelp.dbid }'">查看详细</a>
										</div>
									</li>
									</c:forEach> 
								</c:if>
									<li class="viewall">
										<a href="${ctx }/emergencyHelp/queryList" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
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
		 var data=eval("${jsonHasCarData}");
		 $('#container1').highcharts({
	         chart: {
	             type: 'column'
	         },
	         title: {
	             text: '会员有车情况'
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
		 
});
</script> 
</html>

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
<title>销售副总</title>
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
				<li>
					<div class="right">
						<strong>${newsCount+canncelCount+customerSuccess+orderCount+waitingCar }</strong>
						总客户量
					</div>
				</li>
			</ul>
		</div>	
	</div>
</div>
<div class="row-fluid">
	<div class="span4">
		<div class="widget-box">
			<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>需要您审批的流失客户</h5><span class="label label-info tip-left" data-original-title="${fn:length(outFlowerCustomers) } 在线预约客户">${fn:length(outFlowerCustomers) }</span></div>
			<div class="widget-content nopadding">
				<ul class="recent-comments">
				<c:if test="${empty(outFlowerCustomers) }">
				<li>
						<div class="comments">
							<p>
								无待审批流失客户...
							</p>
						</div>
					</li>
				</c:if>
				  <c:forEach var="customer"  items="${outFlowerCustomers }" end="4">
					<li>
						<div class="comments">
							<span class="user-info"> 
								客户:${customer.name }&nbsp;&nbsp;&nbsp;&nbsp;  
								成交结果：<c:if test="${customer.lastResult==2 }">
											<span style="color: red;">流失购买其他品牌</span>
										</c:if>
										<c:if test="${customer.lastResult==3 }">
											<span style="color: red;">购车计划取消</span>
										</c:if>
						   </span>
							<p>
								创建时间:<fmt:formatDate value="${customer.createFolderTime }" pattern="yyyy-MM-dd HH:mm"/>
								&nbsp;&nbsp;&nbsp;&nbsp;
								业务员：${customer.bussiStaff }
							</p>
							<a class="btn btn-primary btn-mini" href="javascript:void(-1)" onclick="$.utile.openDialog('${ctx}/customerLastBussi/approval?customerId=${customer.dbid }&type=2','销售经理审批流失客户',500,300)">审批</a>
						</div>
					</li>
				</c:forEach> 
					<li class="viewall">
						<a href="${ctx }/customer/queryLeaderOutFlow" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="span4">
		<div class="widget-box">
			<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>需要您审批的订单</h5><span class="label label-info tip-left" data-original-title="${fn:length(orderContracts) } 在线预约客户">${fn:length(orderContracts) }</span></div>
			<div class="widget-content nopadding">
				<ul class="recent-comments">
				<c:if test="${empty(orderContracts) }">
				<li>
						<div class="comments">
							<p>
								无待审批订单...
							</p>
						</div>
					</li>
				</c:if>
				  <c:forEach var="orderContract"  items="${orderContracts }" end="4">
				  <c:set var="customer" value="${orderContract.customer }"></c:set>
					<li>
						<div class="comments">
							<span class="user-info"> 
								客户:${customer.name }&nbsp;&nbsp;&nbsp;&nbsp;  
								车型:${customer.customerLastBussi.carSeriy.name}
									${customer.customerLastBussi.carModel.name} 
							<p>
								创建时间:<fmt:formatDate value="${orderContract.createTime }" pattern="yyyy-MM-dd HH:mm"/>
								&nbsp;&nbsp;&nbsp;&nbsp;
								业务员：${customer.bussiStaff }
									
							</p>
								<a class="btn btn-success btn-mini" href="javascript:void(-1)"  onclick="window.location.href='${ctx}/orderContract/approval?dbid=${customer.orderContract.dbid }&type=4'">审批</a>
						</div>
					</li>
				</c:forEach> 
					<li class="viewall">
						<a href="${ctx }/orderContract/queryManagerOrder?status=1" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	 <div class="span4">
		<div class="widget-box">
			<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>需要您审批的合同流失</h5><span class="label label-info tip-left" data-original-title="${fn:length(customers) } 待交车客户">${fn:length(customers) }</span></div>
			<div class="widget-content nopadding">
			<ul class="recent-comments">
				<c:if test="${empty(customers) }">
				<li>
						<div class="comments">
							<p>
								无待审批合同流失...
							</p>
						</div>
					</li>
				</c:if>
				  <c:forEach var="customer"  items="${customers }" end="4">
					<li>
						<div class="comments">
							<span class="user-info"> 
								客户:${customer.name }&nbsp;&nbsp;&nbsp;&nbsp;  
								车型:${customer.customerLastBussi.carSeriy.name}
									${customer.customerLastBussi.carModel.name} 
						   </span>
							<p>
								订车时间:<fmt:formatDate value="${customer.customerPidBookingRecord.bookingDate }" pattern="yyyy-MM-dd HH:mm"/>
								&nbsp;&nbsp;&nbsp;&nbsp;
								业务员：${customer.bussiStaff }
							</p>
							<a class="btn btn-primary btn-mini" href="javascript:void(-1)" onclick="window.location.href='/customerPidBookingRecord/approvalOrderContractCancel?customerId=${customer.dbid }&type=2'">审批</a>
						</div>
					</li>
				</c:forEach> 
					<li class="viewall">
						<a href="${ctx }/customerPidBookingRecord/queryApprovalWatingList" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
					</li>
				</ul>
			</div>
		</div>
	</div> 
</div>
<div class="row-fluid">
	<div class="widget-box">
		<div id="container" style="min-width:1024px; height: 400px; max-width: 1440px; margin: 0 auto"></div>
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
</body>
<script type="text/javascript" src="${ctx }/widgets/highcharts/jquery.1.8.2.jquery.min.js"></script>
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
	var xSer=eval("${xSer}");
	var xSerValue=eval("${xSerValue}");
    $('#container').highcharts({
        chart: {
            type: 'area'
        },
        title: {
            text: '每日进店客流量趋势'
        },
        subtitle: {
        },
        xAxis: {
            categories: xSer,
            tickmarkPlacement: 'on',
            title: {
                enabled: false
            }
        },
        yAxis: {
            title: {
                text: '进店人数（人）'
            },
            labels: {
                formatter: function() {
                    return this.value / 1 +'人';
                }
            }
        },
        tooltip: {
            pointFormat: '{series.name}  {point.x} 日 进店人数 <b>{point.y:,.0f}</b><br/> '
        },
        plotOptions: {
            area: {
                pointStart: 0,
                marker: {
                    enabled: false,
                    symbol: 'circle',
                    radius: 2,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                }
            }
        },
        series: [{
            name: '<fmt:formatDate value="${now }" pattern="MM月"/>',
            data:xSerValue 
            }]
    });
});

var data=eval("[ ['O',${levelCO}], ['A',${levelCA}], ['B',${levelCB}], ['C',${levelCC}], ['L',${levelLC}], ['F',${levelFC}]]");
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
$('#container2').highcharts({
       chart: {
           plotBackgroundColor: null,
           plotBorderWidth: null,
           plotShadow: true
       },
       title: {
       	 text: '月度各级客户占比'
       },
       tooltip: {
   	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
       },
       plotOptions: {
           pie: {
               allowPointSelect: true,
               cursor: 'pointer',
               dataLabels: {
                   enabled: true,
                   format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                   style: {
                       color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                   }
               }
           }
       },
       series: [{
           type: 'pie',
           name: '月度各级客户占比',
           data:data
       }]
   }); 


</script>
</html>

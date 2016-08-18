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
								<div class="left peity_bar_good"><span><span style="display: none;">2,4,9,7,12,10,12</span><canvas width="50" height="24"></canvas></span>+20%</div>
								<div class="right">
									<strong>36094</strong>
									会员人数
								</div>
							</li>
							<li>
								<div class="left peity_bar_neutral"><span><span style="display: none;">20,15,18,14,10,9,9,9</span><canvas width="50" height="24"></canvas></span>0%</div>
								<div class="right">
									<strong>1433</strong>
									预约人数
								</div>
							</li>
							<li>
								<div class="left peity_bar_bad"><span><span style="display: none;">3,5,9,7,12,20,10</span><canvas width="50" height="24"></canvas></span>-50%</div>
								<div class="right">
									<strong>8650</strong>
									客户总数
								</div>
							</li>
							<li>
								<div class="left peity_line_good"><span><span style="display: none;">12,6,9,23,14,10,17</span><canvas width="50" height="24"></canvas></span>+70%</div>
								<div class="right">
									<strong>8650</strong>
									订单数
								</div>
							</li>
						</ul>
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
							<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>近期添加会员</h5><span class="label label-info tip-left" data-original-title="54 总会员">54</span></div>
							<div class="widget-content nopadding">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>姓名</th>
											<th>联系电话</th>
											<th>当前积分</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>10000</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>10000</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>10000</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>10000</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>10000</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>10000</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>10000</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>10000</td>
										</tr>
									</tbody>
								</table>
								<ul class="recent-posts" style="margin-top: 5px;">
									<li class="viewall">
										<a href="#" class="tip-top" data-original-title="点击查看更多"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>近期预约</h5><span class="label label-info tip-left" data-original-title="88 预约人数">88</span></div>
							<div class="widget-content nopadding">
								<ul class="recent-comments">
									<li>
										<div class="comments">
											<span class="user-info"> 预约人: 舒三战 预约时间:2014-04-20 星期二 </span>
											<p>
												舒三战 预约了 2014-04-20 星期二 瑞丰 2012款 试乘试驾...
											</p>
											<a class="btn btn-primary btn-mini" href="#">处理</a> <a class="btn btn-success btn-mini" href="#">添加成会员</a> <a class="btn btn-warning btn-mini" href="#">查看</a>
										</div>
									</li>
									<li>
										<div class="comments">
											<span class="user-info"> 预约人: 舒三战 预约时间:2014-04-20 星期二 </span>
											<p>
												舒三战 预约了 2014-04-20 星期二 瑞丰 2012款 试乘试驾...
											</p>
											<a class="btn btn-primary btn-mini" href="#">处理</a> <a class="btn btn-success btn-mini" href="#">添加成会员</a> <a class="btn btn-warning btn-mini" href="#">查看</a>
										</div>
									</li>
									<li>
										<div class="comments">
											<span class="user-info"> 预约人: 舒三战 预约时间:2014-04-20 星期二 </span>
											<p>
												舒三战 预约了 2014-04-20 星期二 瑞丰 2012款 试乘试驾...
											</p>
											<a class="btn btn-primary btn-mini" href="#">处理</a> <a class="btn btn-success btn-mini" href="#">添加成会员</a> <a class="btn btn-warning btn-mini" href="#">查看</a>
										</div>
									</li>
									<li class="viewall">
										<a href="#" class="tip-top" data-original-title="View all comments"> 点击查看更多 </a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>未来一周生日会员</h5><span class="label label-info tip-left" data-original-title="54 预约人数">54</span></div>
							<div class="widget-content nopadding">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>姓名</th>
											<th>联系电话</th>
											<th>生日</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日 星期二</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日 星期二</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日 星期二</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日 星期二</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日 星期二</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日 星期二</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="widget-box">
							<div class="widget-title"><span class="icon"><i class="icon-comment"></i></span><h5>近期需联系客户</h5><span class="label label-info tip-left" data-original-title="88 总会员">88</span></div>
							<div class="widget-content nopadding">
								<div class="widget-content nopadding">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>姓名</th>
											<th>联系电话</th>
											<th>上次联系时间</th>
											<th>本次联系时间</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日</td>
											<td>1982年3月18日</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日</td>
											<td>1982年3月18日</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日</td>
											<td>1982年3月18日</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日</td>
											<td>1982年3月18日</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日</td>
											<td>1982年3月18日</td>
										</tr>
										<tr>
											<td>舒三战</td>
											<td>18708150883</td>
											<td>1982年3月18日</td>
											<td>1982年3月18日</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div> 
			</div>
</body>
  <script src="${ctx}/widgets/bootstrap/excanvas.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.ui.custom.js"></script>
<script src="${ctx}/widgets/bootstrap/bootstrap.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.flot.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.flot.resize.min.js"></script>
<script src="${ctx}/widgets/bootstrap/jquery.peity.min.js"></script>
<script src="${ctx}/widgets/bootstrap/fullcalendar.min.js"></script>
<script src="${ctx}/widgets/bootstrap/unicorn.js"></script>
<script src="${ctx}/widgets/bootstrap/unicorn.dashboard.js"></script>
<!-- <script type="text/javascript">
window.onresize=function(){
	 $("#contentUrl").width(window.document.documentElement.clientWidth-145+"px");
}
</script> -->
</html>

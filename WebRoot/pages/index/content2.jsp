<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../commons/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台表单页面</title>
<style type="text/css">
.widget-box.widget-plain .widget-content {
    padding: 12px 0 0;
}
.widget-content {
    border-bottom: 1px solid #CDCDCD;
    padding: 12px 15px;
}

.stats-plain {
}
.stats-plain {
    width: 100%;
}
.stat-boxes, .quick-actions, .quick-actions-horizontal, .stats-plain {
    display: inline-block;
    list-style: none outside none;
    margin: 20px 0 10px;
    text-align: center;
}
ul, ol {
    margin: 0 0 10px 25px;
    padding: 0;
}

.stats-plain li {
    display: inline-block;
    margin: 0 10px 20px;
    padding: 0 30px;
}
li {
    line-height: 20px;
}

.stats-plain li h4 {
    font-size: 40px;
    margin-bottom: 15px;
}

.stats-plain li h4 {
    font-size: 40px;
    margin-bottom: 15px;
}
h4 {
    font-size: 17.5px;
}
h1, h2, h3, h4, h5, h6 {
    color: inherit;
    font-family: inherit;
    font-weight: bold;
    line-height: 20px;
    margin: 10px 0;
    text-rendering: optimizelegibility;
}

.stats-plain li span {
    color: #555555;
    font-size: 14px;
}

.row-fluid:before, .row-fluid:after {
    content: "";
    display: table;
    line-height: 0;
}
.row-fluid:before, .row-fluid:after {
    content: "";
    display: table;
    line-height: 0;
}
.row-fluid:after {
    clear: both;
}
.row-fluid:before, .row-fluid:after {
    content: "";
    display: table;
    line-height: 0;
}
.row-fluid:after {
    clear: both;
}
.row-fluid:before, .row-fluid:after {
    content: "";
    display: table;
    line-height: 0;
}
.row-fluid {
    width: 100%;
}
.row-fluid {
    width: 100%;
}

.row-fluid [class*="span"]:first-child {
    margin-left: 0;
}
.row-fluid [class*="span"]:first-child {
    margin-left: 0;
}
.row-fluid .span4 {
    width: 31.6239%;
}
.row-fluid [class*="span"] {
    -moz-box-sizing: border-box;
    display: block;
    float: left;
    margin-left: 2.5641%;
    min-height: 30px;
    width: 100%;
}
.row-fluid .span4 {
    width: 31.9149%;
}
.row-fluid [class*="span"] {
    -moz-box-sizing: border-box;
    display: block;
    float: left;
    margin-left: 2.12766%;
    min-height: 30px;
    width: 100%;
}
.span4 {
    width: 370px;
}
[class*="span"] {
    float: left;
    margin-left: 30px;
    min-height: 1px;
}
.span4 {
    width: 300px;
}
[class*="span"] {
    float: left;
    margin-left: 20px;
    min-height: 1px;
}

.widget-box {
    background: none repeat scroll 0 0 #F9F9F9;
    border-left: 1px solid #CDCDCD;
    border-right: 1px solid #CDCDCD;
    border-top: 1px solid #CDCDCD;
    clear: both;
    margin-bottom: 16px;
    margin-top: 16px;
    position: relative;
}

.widget-title, .modal-header, .table th, div.dataTables_wrapper .ui-widget-header {
    background-color: #EFEFEF;
    background-image: -moz-linear-gradient(center top , #FDFDFD 0%, #EAEAEA 100%);
    border-bottom: 1px solid #CDCDCD;
    height: 36px;
}

.widget-title span.icon {
    border-right: 1px solid #CDCDCD;
    float: left;
    opacity: 0.7;
    padding: 9px 10px 7px 11px;
}

.icon-eye-open {
    background-position: -96px -120px;
}
[class^="icon-"], [class*=" icon-"] {
    background-image: url("../img/glyphicons-halflings.png");
    background-position: 14px 14px;
    background-repeat: no-repeat;
    display: inline-block;
    height: 14px;
    line-height: 14px;
    margin-top: 1px;
    vertical-align: text-top;
    width: 14px;
}

.widget-title h5 {
    color: #666666;
    float: left;
    font-size: 12px;
    font-weight: bold;
    line-height: 12px;
    margin: 0;
    padding: 12px;
    text-shadow: 0 1px 0 #FFFFFF;
}

.nopadding {
    padding: 0 !important;
}
.widget-content {
    border-bottom: 1px solid #CDCDCD;
    padding: 12px 15px;
}

.nopadding .table-bordered {
    border: 0 none;
}
.nopadding .table {
    margin-bottom: 0;
}
.table-bordered {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    border-collapse: separate;
    border-color: #DDDDDD #DDDDDD #DDDDDD -moz-use-text-color;
    border-image: none;
    border-radius: 4px;
    border-style: solid solid solid none;
    border-width: 1px 1px 1px 0;
}
.table {
    margin-bottom: 20px;
    width: 100%;
}
table {
    background-color: rgba(0, 0, 0, 0);
    border-collapse: collapse;
    border-spacing: 0;
    max-width: 100%;
}
</style>
</head>
<body>
<div class="widget-content center">
	<ul class="stats-plain">
		<li>										
			<h4>36094</h4>
			<span>总会员</span>
		</li>
		<li>										
			<h4>1433</h4>
			<span>新增会员</span>
		</li>
		<li>										
			<h4>8650</h4>
			<span>总预约</span>
		</li>
		<li>										
			<h4>29</h4>
			<span>新增预约</span>
		</li>
		<li>										
			<h4>29</h4>
			<span>总客户</span>
		</li>
		<li>										
			<h4>29</h4>
			<span>新增客户</span>
		</li>
	</ul>
</div>
<div class="row-fluid">
					<div class="span4">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon">
									<i class="icon-eye-open"></i>
								</span>
								<h5>Browsers</h5>
							</div>
							<div class="widget-content nopadding">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>Browser</th>
											<th>Visits</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>Chrome</td>
											<td>8775</td>
										</tr>
										<tr>
											<td>Firefox</td>
											<td>5692</td>
										</tr>
										<tr>
											<td>Internet Explorer</td>
											<td>4030</td>
										</tr>
										<tr>
											<td>Opera</td>
											<td>1674</td>
										</tr>
										<tr>
											<td>Safari</td>
											<td>1166</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="span4">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon">
									<i class="icon-arrow-right"></i>
								</span>
								<h5>Refferers</h5>
							</div>
							<div class="widget-content nopadding">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>Site</th>
											<th>Visits</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><a href="#">http://google.com</a></td>
											<td>12679</td>
										</tr>
										<tr>
											<td><a href="#">http://bing.com</a></td>
											<td>11444</td>
										</tr>
										<tr>
											<td><a href="#">http://yahoo.com</a></td>
											<td>8595</td>
										</tr>
										<tr>
											<td><a href="#">http://www.something.com</a></td>
											<td>4445</td>
										</tr>
										<tr>
											<td><a href="#">http://else.com</a></td>
											<td>2094</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="span4">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon">
									<i class="icon-file"></i>
								</span>
								<h5>Most Visited Pages</h5>
							</div>
							<div class="widget-content nopadding">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>Page</th>
											<th>Visits</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><a href="#">Shopping cart</a></td>
											<td>9440</td>
										</tr>
										<tr>
											<td><a href="#">Blog</a></td>
											<td>6974</td>
										</tr>
										<tr>
											<td><a href="#">jQuery UI tips</a></td>
											<td>5377</td>
										</tr>
										<tr>
											<td><a href="#">100+ Free Icon Sets</a></td>
											<td>4990</td>
										</tr>
										<tr>
											<td><a href="#">How to use a Google Web Tools</a></td>
											<td>4834</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
</body>
<script src="${ctx}/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript">
window.onresize=function(){
	 $("#contentUrl").width(window.document.documentElement.clientWidth-145+"px");
}
</script>
</html>

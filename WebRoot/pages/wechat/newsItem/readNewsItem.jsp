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
<link rel="stylesheet" type="text/css" href="http://www.weimob.com/wm-xin-a/font-awesome.css?v=2014043014" media="all" />
<script type="text/javascript" src="${ctx }/widgets/bootstrap/jquery.min.js"></script>
<script type="text/javascript"  src="${ctx }/widgets/utile/weixin.js"></script>
<title>${weixinNewsitem.title}</title>
<style type="text/css">
.body {
    display: block;
    font-size: 12px;
    margin: auto;
    max-width: 640px;
    min-height: 100%;
    min-width: 320px;
    position: relative;
}
#mcover {
    background: none repeat scroll 0 0 rgba(0, 0, 0, 0.7);
    display: none;
    height: 100%;
    left: 0;
    position: fixed;
    top: 0;
    width: 100%;
    z-index: 20000;
}
#mcover img {
    height: 180px;
    position: fixed;
    right: 18px;
    top: 5px;
    width: 260px;
    z-index: 20001;
}
.bg{
   bottom: 0;
    left: 0;
    position: absolute;
    right: 0;
    top: 0;
    z-index: -1;
}
 .article {
    color: #333333;
    overflow: hidden;
    padding: 5px 5px;
}

.article .content2 {
    line-height: 22px;
    word-wrap: break-word;
    font-size: 14px;
    overflow: hidden;
    line-height: 200%;
} 
.article header h3 {
    font-size: 15px;
    line-height: 20px;
}
#weixinNewsitem_1 {
    display: block;
    float: left;
    width: 49%;
}

#weixinNewsitem_2 {
    display: block;
    float: right;
    width: 49%;
}

.weixinNewsitemButton {
    background-color: #E8E8E8;
    background-image: linear-gradient(to top, #DBDBDB, #F4F4F4);
    border: 1px solid #ADADAB;
    border-radius: 3px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.45), 0 1px 1px #EFEFEF inset;
    color: #000000;
    font-size: 14px;
    text-align: center;
    text-shadow: 0.5px 0.5px 1px #FFFFFF;
    width: 100%;
}
.text {
    color: #727272;
    font-size: 14px;
    word-wrap: break-word;
}

</style>
</head>
<body style="background-image: url('');">
<div>
	<div id="mcover" onclick="document.getElementById('mcover').style.display='';"><img src="${ctx }/images/weixin/guide.png"></div>
	<c:if test="${!empty(weixinNewsitem) }">
		<div class="main" style="padding-bottom: 12px;">
			
			<section class="section_body">
				<article class="article">
					<header>
						<h3>${weixinNewsitem.title }</h3>
						<sapn>
							<fmt:formatDate value="${weixinNewsitem.createDate}" pattern="yyyy-MM-dd"/>
						</span>
					</header>
					<c:if test="${weixinNewsitem.coverShow==1 }">
						<section>
							<img src="${weixinNewsitem.imagepath }" style="width:100%;"/><br />
						</section>
					</c:if>
					<div class="content2">
						${weixinNewsitem.content }
					</div>
				</article>
			</section>
			<section style="width:95%; margin:0px auto;">
				<div class="text" id="content" style="padding-bottom: 20px;">
					<div id="mess_weixinNewsitem">
						<div id="weixinNewsitem_1">
							<div class="weixinNewsitemButton" onclick="document.getElementById('mcover').style.display='block';">
									<div style="width:50px;height: 50px;float: left; ">
										<img src="${ctx }/images/weixin/iconMsg.png?=2014-03-07-1" width="50" height="50">
									</div>
									<div style="height: 50px; float: left;line-height: 50px;font-size: 14px;">
										&nbsp;发送给朋友
									</div>
									<div style="clear: both;"></div>
							</div>
						</div>
						<div id="weixinNewsitem_2">
							<div class="weixinNewsitemButton" onclick="document.getElementById('mcover').style.display='block';">
									<div style="width:50px;height: 50px;float: left; ">
										<img src="${ctx }/images/weixin/iconTimeline.png" width="50" height="50">
									</div>
									<div style="height: 50px; float: left;line-height: 50px;font-size: 14px;">
										&nbsp;分享到朋友圈
									</div>
									<div style="clear: both;"></div>
							</div>
						</div>
						<div class="clr" style="clear: both;"></div>
					</div>
				</div>
			</section>
		</div>
	</div>
	<div style="padding-bottom:5px!important;">
		<a href="javascript:window.scrollTo(0,0);" style="font-size:14px;margin:0 auto;display:block;color:#fff;text-align:center;line-height:35px;background:#333;margin-bottom:-10px;">返回顶部</a>
	</div>
</c:if>
<c:if test="${empty(weixinNewsitem) }">
	<div class="alert-danger" style="padding: 12px 20px;font-size: 16px;">
		无积分分享信息！
	</div>
</c:if>
	
</body>
</html>

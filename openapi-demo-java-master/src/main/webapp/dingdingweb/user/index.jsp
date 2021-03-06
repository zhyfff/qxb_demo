<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@include file="/top.jsp"%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta charset="gbk">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />

<script type="text/javascript">

	//在此拿到jsAPI权限验证配置所需要的信息
	var _config = <%= com.alibaba.dingtalk.openapi.demo.auth.AuthHelper.getConfig(request) %>;

	</script>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">

<title>企业开发者首页</title>

<script type="text/javascript" src="${basePath}dingdingweb/HUI/contract/js/zepto.min.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js">
</script>
<script type="text/javascript" src="${basePath}dingdingweb/HUI/contract/js/logger.js">
</script>
 <script type="text/javascript" src="${basePath}dingdingweb/HUI/contract/js/getUserInfor.js">
//免登相关代码
</script>

<script type="text/javascript">
	function senduserjson(){
		var info = $("#info").attr("value")
// 		alert(info)
		if (info=="") {
			alert("请稍等")
		}else{
			window.location ='Contract/tocontract.do?info='+encodeURIComponent(info)
		}

	}
</script>


<script type="text/javascript">
function chuanzhi() {
	var json =$("#info").attr("value");
	location.href="/rlfw/torlfw?json="+json;
};

</script>
</head>

<body >
<header>
	<div align="center">
		<img id ="userImg" alt="头像" src="${basePath}dingdingweb/HUI/img/head.png" style="width:88px; margin:0px; border-radius:50%;">
	</div>
	<div align="center" >
		欢迎您:
		<div id="userName" style="display:inline-block"></div>
	</div >
	<div align="center">
		您当前在钉钉的userId为:
		<div id="userId" style="display:inline-block"></div>
	</div>
	
	<div align="center">
		<input id="info" type="hidden">
	</div>
	
</header>
	<br>
	<!-- <div style="padding-left:10px;">&nbsp;&nbsp;&nbsp;&nbsp;欢迎您：<div id="userName" style="display:inline-block;font-weight:bold"></div>&nbsp;成为钉钉开发者，您当前在钉钉的<code>userId</code>为：
		<div id="userId" style="display:inline-block;font-weight:bold"></div> 。</div> -->
<!-- 	<div style="padding-left:10px;">&nbsp;&nbsp;&nbsp;&nbsp;我们为您提供了文档＋<code>api</code>帮助您快速的开发微应用并接入钉钉。</div> -->
	<br>
	
<div class="hui-list" style="margin-top:22px;">
 <ul>
		<li>
			<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/form.png" style="width: 25px; height: 25px"></div>
			<div class="hui-list-text">IT服务（总裁办IT信息部）</div>
		</li>

		<li>
			<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/form.png" style="width: 25px; height: 25px"></div>
			<div class="hui-list-text">行政办公（总裁办行政部）</div>
		</li>
		<li>
				<div class="hui-list-icons">
					<img src="${basePath}dingdingweb/HUI/img/index/form.png" style="width: 25px; height: 25px">
				</div>
				<div class="hui-list-text" id="contract" onclick="senduserjson()">	
					<span>合同管理（总裁办合同管理部） </span>              		 
				</div>
		</li>
		<li>
				<div class="hui-list-icons">
					<img src="${basePath}dingdingweb/HUI/img/index/form.png" style="width: 25px; height: 25px">
				</div>
				<div class="hui-list-text" onclick="chuanzhi()">	
					<span>人力服务（人力资源中心） </span>              		 
				</div>
		</li>

</ul>
</div>

	<script type="text/javascript">
	hui('#contract').click(function(){
	    hui.loading('数据加载中');
	    setTimeout(function(){hui.loading(false, true);}, 2000);
	});
	hui('#btn2').click(function(){
	    if(!window.plus){
	        hui.alert('只能在h5+环境下运行！');
	        return false;
	    }
	    hui.h5Loading(false, '加载中...',{round:2, padding:'20px', textalign:'right'});
	    setTimeout(function(){hui.h5Loading(true);}, 2000);
	});
</script>
</body>

</html>

<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
	<meta content="telephone=no,email=no" name="format-detection">
	<meta name="wap-font-scale" content="no">
	<title>商城</title>
	<!-- ui框架引入css -->
	<link href="../public/css/weui.min.css" rel="stylesheet" type="text/css" />
	<link href="../public/css/jquery-weui.min.css" rel="stylesheet" type="text/css" />
	<!-- 公用样式css -->
	<link href="../public/css/base.css" rel="stylesheet" type="text/css" />
	<!-- 界面引入css -->
	<link href="../public/css/app/user/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main">
   <!-- 当前提示 -->
   <div class="integral-title">
     当前总金额：<span>90</span><span>RMB</span>
   </div>
   <!-- 当前操作 -->
   <div class="integral-content">
     <b>输入数量</b>
     <i><input id="text" type="number" /></i>
   </div>
   <!-- 操作按钮 -->
   <div class="mt20">
		<a href="money/login.spr" class="weui-btn  weui-btn_primary">确认提现</a>
	</div>
</div>



<!-- jquery引入 -->
<script type="text/javascript" src="../public/js/jquery/jquery.js"></script>
<script type="text/javascript" src="../public/js/jquery/flexible.js"></script>
<!-- weui引入 -->
<script type="text/javascript" src="../public/js/jquery/weui.js"></script>
<!-- 界面对应js -->
<script type="text/javascript" src="../public/js/data/user/money.js"></script>
</body>
</html>

<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@include file="/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="yes" name="apple-touch-fullscreen" />    
<meta name="data-spm" content="a215s" />
<meta content="telephone=no,email=no" name="format-detection" />    
<meta content="fullscreen=yes,preventMove=no" name="ML-Config" />
<link href="icon114-114.png" rel="apple-touch-icon-precomposed">
<link href="icon.png" rel="Shortcut Icon" type="image/x-icon" />
<script type="text/javascript">
	//在此拿到jsAPI权限验证配置所需要的信息
	var _config = <%= com.alibaba.dingtalk.openapi.demo.auth.AuthHelper.getConfig(request) %>;
</script>
<script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
<script type="text/javascript" src="https://g.alicdn.com/dingding/dingtalk-pc-api/2.7.0/index.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	loadScript();
})
function loadScript(){
	if (os.isAndroid || os.isPhone) {
		var script=document.createElement("script");
		script.type="text/javascript";
		script.src="dingdingweb/HUI/itservices/consumable_purchase/js/con_pur.js";
		document.getElementsByTagName('head')[0].appendChild(script); 
	// 	script.onload=function(){}//js加载完成执行方法
	}else {
		var script=document.createElement("script");
		script.type="text/javascript";
		script.src="dingdingweb/HUI/itservices/consumable_purchase/js/con_pur_pc.js";
		document.getElementsByTagName('head')[0].appendChild(script); 
	// 	script.onload=function(){}
	}
}

</script>
<title>采购申请</title>
</head>
<body>
<header class="header">
		<div class="hui-list" align="center">
				<div  class="hui-list-icons" style="width:110px; height:80px;">
					<img id="userImg" alt="头像" src="${basePath}dingdingweb/HUI/img/head.png" style="width:66px; margin:0px; border-radius:50%;">
				</div>
				<div align="center" style="height:auto; height:40px; padding-bottom:8px;" id="userName">
					欢迎您 : 
				</div>
				
				<input type="hidden" id ="info">
				<input type="hidden" id="username">	
		</div>
</header>
<div class="hui-wrap" >
	<form style="padding: 28px 10px;" id="sendapply" name="sendapply" class="hui-form" onsubmit="return false;">

			<input type="hidden" id="userId" name="userId">
			<div class="hui-form-items">
				<div class="hui-form-items-title">申请人工号</div>
				<input type="text" id="borrowid" class="hui-input" name="borrowid"
					readonly="readonly">
			</div>


			<div class="hui-form-items">
				<div class="hui-form-items-title">申请人姓名</div>
				<input type="text" id="borrowname" class="hui-input"
					name="borrowname" readonly="readonly">
			</div>

			<input type="hidden" id="borrowde" class="hui-input" name="borrowde"
				value="defult" readonly="readonly">
				
			<div class="hui-form-items">
				<div class="hui-form-items-title">采购日期</div>
				<input type="date" id="ptime" name="ptime"
					required="required" class="hui-input">
			</div>
			
			<div class="hui-form-items">
	            <div class="hui-form-items-title">采购类型</div>
	            <div class="hui-form-select">
	                <select name="ptype" id="ptype">
	                    <option value="日常采购">日常采购</option>
	                    <option value="项目采购">专项采购</option>
	                </select>
	            </div>
       		 </div>
       		 
       		 <div class="hui-form-items">
	            <div class="hui-form-items-title">耗材分类</div>
	            <div class="hui-form-select">
	                <select name="consumable_type" id="consumable_type">
	                    <option value="打印耗材">打印耗材</option>
	                    <option value="电脑耗材">电脑耗材</option>
	                    <option value="印刷耗材">印刷耗材</option>
	                    <option value="输出介质">输出介质</option>
	                    <option value="手机周边耗材">手机周边耗材</option>
<!-- 	                    <option value="医用耗材">医用耗材</option> -->
	                </select>
	            </div>
       		 </div>
			
			<div class="hui-form-items">
				<div class="hui-form-items-title">耗材名称</div>
				<input type="text" id="consumable_name" name="consumable_name"
					required="required" class="hui-input">
			</div>
			
			<div class="hui-form-items">
				<div class="hui-form-items-title">规格型号</div>
				<input type="text" id="specifications" name="specifications"
					required="required" class="hui-input">
			</div>
			
			<div class="hui-form-items">
				<div class="hui-form-items-title">申请数量</div>
				<div class="hui-number-box" min="1" max="999">
					<div class="reduce">-</div>
					<input value="1" type="number" id="apply_num" name="apply_num">
					<div class="add">+</div>
				</div>
			</div>
			
			<div class="hui-form-items">
				<div class="hui-form-items-title">备注</div>
				<div class="hui-form-textarea">
					<textarea placeholder="备注..." id="note" name="note" required="required"></textarea>
				</div>
			</div>


			<div class="hui-form-items">
				<div class="items-title"
					style="width: 50%; line-height: 40px; height: 40px; flex-shrink: 0;">审批人姓名</div>
				<input type="hidden" id="audit-name" class="hui-input"
					name="audit-name" required="required"> <input
					type="hidden" id="spid" name="spid" readonly="readonly"> <img
					alt="头像" src="${basePath}dingdingweb/HUI/img/add.png"
					style="height: 40px; width: 40px; margin: 0px; border-radius: 50%;"
					onclick="to2()" id="audit-img">
			</div>

			<div style="padding: 15px 8px 50px 15px;">
				<div class="hui-form-items-title"></div>
				<input type="submit" id="submit" value="提交申请"
					class="hui-button hui-primary hui-fr" onclick="sendDing()">
			</div>
		</form>
</div>

<script type="text/javascript">

	function sendDing(){
		if (myCheck()==true) {
			ding();
		}
		return false;
	}

	function myCheck()
    {//循环所有的表单元素； 也可以用jQuery：$("#表单id")[0].elements.length-1
       for(var i=0;i<document.sendapply.elements.length-1;i++) //下面减一是因为数组的下标为0
       {	
          if(document.sendapply.elements[i].value=="")
          {
             alert("当前表单不能有空项");
             document.sendapply.elements[i].focus();
             return false;
          }
       }
       return true;
      
    }
	

	function registered() {
			
		if (myCheck()==true) {
			$.ajax({
				url : "ITServices/consuable_pur/saveApplyInfo",
				data : $("#sendapply").serialize(),
				dataType : "text",
				type : "POST",
				success : function(data) {
					hui.iconToast(data, 'success');
					if (data=='失败') {
						hui.iconToast(data, 'error');
					}
					window.location.reload();//刷新当前页
				},
				error : function() {
					hui.iconToast(data, 'error');
				}
			})
		}	
		return false;
	}
	
</script>
<script type="text/javascript">
hui.numberBox();
</script>
</body>
</html>
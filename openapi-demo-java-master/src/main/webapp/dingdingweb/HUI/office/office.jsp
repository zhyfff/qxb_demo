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
<title>行政办公</title>
</head>
<body>
	<header class="header">
		<div class="hui-list" align="center">
			<c:forEach items="${userinfoList}" var="u" varStatus="abc">
				<div  class="hui-list-icons" style="width:110px; height:80px;">
					<img id="userImg" alt="头像" src="${u.avatar}" style="width:66px; margin:0px; border-radius:50%;">
				</div>
				<div align="center" style="height:auto; height:40px; padding-bottom:8px;">
					欢迎您 : ${u.name}
				</div>
				
					<input type="hidden" id="gonghao" value="${u.jobnumber}">
					<input type="hidden" id="uname" value="${u.name}">
					<input  type="hidden" id = "info" value="${userjson}">
					<input type="hidden" id="username" value="${u.name}">
				
			</c:forEach>
		</div>
	</header>
	
	<div class="hui-wrap">
	    <div style="padding:10px;">
			<div class="hui-speed-dial">
				<ul></ul>
			</div>
	    </div>
	</div>

	<script type="text/javascript">
		var icons = ["icon-kuaidi","icon-cheliang","icon-iconset0147","icon-yinzhang","icon-icon-test"];
		
		var html  = '';
			html += '<li>'+
				'<div class="hui-speed-dial-icons" onclick="senduser2courier()">'+
		            '<span class="icon iconfont '+icons[0]+'"></span>'+
		        '</div>'+
		        '<div class="hui-speed-dial-text">快递发放</div>';
				'</li>';
			hui('.hui-speed-dial ul').html(html);
			
			html += '<li>'+
			'<div class="hui-speed-dial-icons" onclick="senduser2applycar()">'+
	            '<span class="icon iconfont '+icons[1]+'"></span>'+
	        '</div>'+
	        '<div class="hui-speed-dial-text">车辆申请</div>';
			'</li>';
			
			html += '<li>'+
			'<div class="hui-speed-dial-icons" onclick="senduser2businesscard()">'+
	            '<span class="icon iconfont '+icons[2]+'"></span>'+
	        '</div>'+
	        '<div class="hui-speed-dial-text">名片制作</div>';
			'</li>';
			hui('.hui-speed-dial ul').html(html);
			
			html += '<li>'+
			'<div class="hui-speed-dial-icons" onclick="senduser2stamp()">'+
	            '<span class="icon iconfont '+icons[3]+'"></span>'+
	        '</div>'+
	        '<div class="hui-speed-dial-text">印章及证照申请</div>';
			'</li>';
			hui('.hui-speed-dial ul').html(html);
			
			html += '<li>'+
			'<div class="hui-speed-dial-icons" onclick="torepair()">'+
	            '<span class="icon iconfont '+icons[4]+'"></span>'+
	        '</div>'+
	        '<div class="hui-speed-dial-text">行政报修</div>';
			'</li>';
			hui('.hui-speed-dial ul').html(html);
		
	
	</script>
	<script type="text/javascript">
		function senduser2courier(){
			var info = Encrypt($("#info").attr("value"));
//	 		alert(info)
			if (info=="") {
				alert("请稍等")
			}else{
				window.location ='${basePath}Office/Courier/courier?info='+encodeURIComponent(info)
			}
			
		}
		
		function senduser2applycar(){
			var info = Encrypt($("#info").attr("value"));
//	 		alert(info)
// 		alert("暂未上线");
			
		if (info == "") {
				alert("请稍等")
			} else {
				window.location = '${basePath}Office/ApplyCar/applycar?info=' + encodeURIComponent(info)
			}

		}

		function senduser2businesscard() {
			var info = Encrypt($("#info").attr("value"));
			//	 		alert(info)
			if (info == "") {
				alert("请稍等")
			} else {
				window.location = '${basePath}Office/BusinessCard/applycard?info=' + encodeURIComponent(info)
			}

		}

// 		function torepair() {
// 			var info = $("#info").attr("value")
// 			//	 		alert(info)
// 			if (info == "") {
// 				alert("请稍等")
// 			} else {
// 				window.location = '${basePath}Repair/torepair?info='
// 						+ info
// 			}

// 		}
		function senduser2stamp() {
			var info = Encrypt($("#info").attr("value"));
			//	 		alert(info)
			if (info == "") {
				alert("请稍等")
			} else {
				window.location = '${basePath}Office/StampCertificate/applystamp?info='
						+ encodeURIComponent(info)
			}

		}
	</script>


</body>
</html>
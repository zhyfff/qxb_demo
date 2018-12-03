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
<title>我的审批</title>
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
	var icons = ["hui-icons-success","hui-icons-eyes","hui-icons-check","hui-icons-forum"];
	
	var html  = '';
		html += '<li>'+
			'<div class="hui-speed-dial-icons" onclick="senduserjsontoaudit()">'+
	            '<span class="hui-icons '+icons[1]+'"></span>'+
	        '</div>'+
	        '<div class="hui-speed-dial-text">合同借用审核(管理员)</div>';
			'</li>';
		hui('.hui-speed-dial ul').html(html);
	
		html += '<li>'+
			'<div class="hui-speed-dial-icons" onclick="senduserjsontofinal()">'+
		        '<span class="hui-icons '+icons[2]+'"></span>'+
		    '</div>'+
		    '<div class="hui-speed-dial-text">合同归还确认(管理员)</div>';
			'</li>';
		hui('.hui-speed-dial ul').html(html);
		
		
		
		html += '<li>'+
		'<div class="hui-speed-dial-icons" onclick="auditApplyCar()">'+
            '<span class="hui-icons '+icons[1]+'"></span>'+
        '</div>'+
        '<div class="hui-speed-dial-text">车辆借用审核(管理员)</div>';
		'</li>';
		hui('.hui-speed-dial ul').html(html);
		
		html += '<li>'+
		'<div class="hui-speed-dial-icons" onclick="sureBackCar()">'+
	        '<span class="hui-icons '+icons[2]+'"></span>'+
	    '</div>'+
	    '<div class="hui-speed-dial-text">车辆归还确认(管理员)</div>';
		'</li>';
		hui('.hui-speed-dial ul').html(html);
		
		
		html += '<li>'+
		'<div class="hui-speed-dial-icons" onclick="auditBusinessCard()">'+
	        '<span class="hui-icons '+icons[1]+'"></span>'+
	    '</div>'+
	    '<div class="hui-speed-dial-text">名片制作审核</div>';
		'</li>';
		hui('.hui-speed-dial ul').html(html);
		
		html += '<li>'+
		'<div class="hui-speed-dial-icons" onclick="auditApplyStamp()">'+
            '<span class="hui-icons '+icons[2]+'"></span>'+
        '</div>'+
        '<div class="hui-speed-dial-text">证章借用审核(管理员)</div>';
		'</li>';
		hui('.hui-speed-dial ul').html(html);
	
	
	    html += '<li>'+
	   '<div class="hui-speed-dial-icons" onclick="mySpdeInfo()">'+
        '<span class="hui-icons '+icons[1]+'"></span>'+
       '</div>'+
       '<div class="hui-speed-dial-text">支持服务审批</div>';
	   '</li>';
       hui('.hui-speed-dial ul').html(html);
       
		html += '<li>'+
		'<div class="hui-speed-dial-icons" onclick="myRepairInfo()">'+
	        '<span class="hui-icons '+icons[2]+'"></span>'+
	    '</div>'+
	    '<div class="hui-speed-dial-text">报修审批</div>';
		'</li>';
		hui('.hui-speed-dial ul').html(html);
		
		

</script>


<script type="text/javascript">
// 		function mySubmitInfo() {
// 			window.location ='${basePath}Service/toMySubmit.do?info='+encodeURIComponent($("#info").attr("value"))
// 		}
// 		function mySpdeInfo(){
// 			window.location ='${basePath}Service/toMyspde.do?info='+encodeURIComponent($("#info").attr("value"))
// 		}
// 		function toRepairingInfo() {
// 			window.location ='${basePath}Repair/toRepairing.do?info='+encodeURIComponent($("#info").attr("value"))
// 		}
// 		function myRepairInfo(){
// 			window.location ='${basePath}Repair/toMyRepair.do?info='+encodeURIComponent($("#info").attr("value"))
// 		}
		

</script>
</body>
</html>
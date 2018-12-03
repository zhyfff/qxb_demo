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
<script type="text/javascript">
$(document).ready(function(){
	hui.loading('加载中...');
	getMyEborrow();
})

function getMyEborrow(){
	$.ajax({
		url:'ITServices/getBackEborrow?userid='+$("#userid").attr("value")+"&pageNum=1",
		type:'GET',
		dataType:'text',
		success:function (result){
			hui.closeLoading();
			var re = JSON.parse(JSON.stringify(result));
			var blist = JSON.parse(re).blist;
			for (var i = 0; i < blist.length; i++) {
				var dcnumber=blist[i].dcnumber;
				var userId=blist[i].userId;
				var borrowid=blist[i].borrowid;
				var borrowname=blist[i].borrowname;
				var borrowde=blist[i].borrowde;
				var equipment=blist[i].equipment;
				var model=blist[i].model;
				var num=blist[i].num;
				var ptime=blist[i].ptime;
				var ebtime=blist[i].ebtime;
				var audit_name=blist[i].audit_name;
				var spid=blist[i].spid;
				var audit_type=blist[i].audit_type;
				var has_get=blist[i].has_get;
				var abtime=blist[i].abtime;
				var note=blist[i].note;
				var return_type=blist[i].return_type;
				var evaluate=blist[i].evaluate;
				var applytime=blist[i].applytime;
				var overdate=blist[i].overdate;
				var s = '';
				if (audit_type=='是'&&overdate==null&&has_get==null) {
					s='审批通过';
				}else if(audit_type=='否'){
					s='审批拒绝';
				}else if(audit_type==null&&overdate==null){
					s='未审批';
				}else if (abtime!=null&&return_type==null) {
					s='已归还未确认';
				}else if (abtime!=null&&return_type!=null&&evaluate==null) {
					s='已归还已确认';
				}else if (abtime==null&&has_get!=null&&evaluate==null) {
					s='未归还';
				}else if (evaluate!=null) {
					s='已评价反馈';
				}
				
				var imgurl='';
				if (s=='已评价反馈') {
					imgurl='dingdingweb/HUI/img/typelist/compl.png';
				}else if (i==0) {
					imgurl='dingdingweb/HUI/img/typelist/new.png';
				}else if (s=='未归还') {
					imgurl='dingdingweb/HUI/img/typelist/return.png';
				}else if (s=='审批拒绝') {
					imgurl='dingdingweb/HUI/img/typelist/refused.png';
				}else if (s=='未审批') {
					imgurl='dingdingweb/HUI/img/typelist/Noapproval.png';
				}else if (s=='审批通过') {
					imgurl='dingdingweb/HUI/img/typelist/Passapproval.png';
				}else{
					imgurl='dingdingweb/HUI/img/typelist/process.png';
				}
				var div = document.createElement('div');
				div.innerHTML =
					'<div class="hui-accordion" onclick="needY(this)">'+
					'<div class="hui-accordion-title" style="background-image:url('+imgurl+');background-repeat:no-repeat;background-position:80% 20%;background-size:38px 38px;">'+equipment+'</div>'+
					'<div class="hui-accordion-content hui-list">'+
					'<form action="ITServices/eborrowing/backToBase" method="post" id="myback" class="hui-form">'+
							
							'<div class="hui-form-items">'+
					     	'<div class="hui-form-items-title">状态</div>'+
							'<input type="text" readonly="readonly"'+
								'value="'+s+'" class="hui-input" id="s" name="s">'+
							'</div>'+
								
							
							'<div class="hui-form-items">'+
					     	'<div class="hui-form-items-title">单据号</div>'+
							'<input type="text" readonly="readonly"'+
								'value="'+dcnumber+'" class="hui-input" id="dcnumber" name="dcnumber">'+
							'</div>'+
							
								
							'<div class="hui-form-items">'+
							'<div class="hui-form-items-title">借用设备</div>'+
							'<input type="text" readonly="readonly"'+
								'value="'+equipment+'" class="hui-input" id="equipment" name="equipment">'+
							'</div>'+
									
							'<div id="never">'+
							
							'<div style="padding:15px 30px 10px 10px; ">'+
							'<div class="hui-form-items-title"></div>'+
								'<input type="submit" value="归还" class="hui-button hui-primary hui-fr">'+
							'</div>'+
							'</div>'+
						'</form>'+
						'</div>'+
					'</div>';
					hui(div).appendTo('#list');
			}
			hui.accordion(true, false);
		},
		error:function(){
			alert("获取失败");
		}
	})
}

function needY(target){	
	var y = $(target).children(".hui-accordion-content").children("#myback").children(".hui-form-items").find("#s").attr("value");
// 		alert(y);
	if (y.indexOf("未归还")==-1) {
		$(target).children(".hui-accordion-content").children("#myback").find("#never").hide();
	}
}
</script>
<title>我的设备借用</title>
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
<!-- 				<div  align="center" style="height:auto; height:60px; padding-bottom:8px;"> -->
<%-- 					您当前在钉钉的userId为 : ${u.userid} --%>
					<input type="hidden" id="userid" value="${u.userid}">
					<input  type="hidden" id = "info" value="${userjson}">
					<input type="hidden" id="username" value="${u.name}">
<!-- 				</div> -->
			</c:forEach>
		</div>
	</header>
	
	<div class="hui-wrap">
    <div id="refreshContainer" class="hui-refresh">
        <div class="hui-refresh-icon"></div>
        <div class="hui-refresh-content hui-list">
            <div id="list">
           
            </div>
        </div>
    </div>
	</div>
<script type="text/javascript" src="${basePath}dingdingweb/HUI/js/hui-refresh-load-more.js"></script>
<script type="text/javascript">
hui.loadMore(nextPage);
var page = 1;
function nextPage(){
	$.ajax({
		url:'ITServices/getBackEborrow?userid='+$("#userid").attr("value")+"&pageNum="+page,
		type:'get',
		dataType:'text',
		success:function(result){
			var re = JSON.parse(JSON.stringify(result));
			  var pageInfo = JSON.parse(re).pageInfo;
				var hasNextPage = pageInfo.hasNextPage;
//		 			alert(hasNextPage);
				if(hasNextPage == false){
			        hui.endLoadMore(true, '已经到头了...');
//			        alert("已经到头了...");
					
			         return false;
			     }else {
			    	page++;
			    	//hui.refresh('#refreshContainer', refresh);
			    	getMore(page);
				}
		},
		error:function(){}
	})
}


//加载更多
function getMore(page){
// 	alert(getmore);
 	hui.loading('加载更多');
	
    $.ajax({
		url:'ITServices/getBackEborrow?userid='+$("#userid").attr("value")+"&pageNum="+page,
		type:'get',
		dataType:'text',
		success:function(result){
			hui.closeLoading();
			var re = JSON.parse(JSON.stringify(result));
			var blist = JSON.parse(re).blist;
			for (var i = 0; i < blist.length; i++) {
				var dcnumber=blist[i].dcnumber;
				var userId=blist[i].userId;
				var borrowid=blist[i].borrowid;
				var borrowname=blist[i].borrowname;
				var borrowde=blist[i].borrowde;
				var equipment=blist[i].equipment;
				var model=blist[i].model;
				var num=blist[i].num;
				var ptime=blist[i].ptime;
				var ebtime=blist[i].ebtime;
				var audit_name=blist[i].audit_name;
				var spid=blist[i].spid;
				var audit_type=blist[i].audit_type;
				var has_get=blist[i].has_get;
				var abtime=blist[i].abtime;
				var note=blist[i].note;
				var return_type=blist[i].return_type;
				var evaluate=blist[i].evaluate;
				var applytime=blist[i].applytime;
				var overdate=blist[i].overdate;
				var s = '';
				if (audit_type=='是'&&overdate==null&&has_get==null) {
					s='审批通过';
				}else if(audit_type=='否'){
					s='审批拒绝';
				}else if(audit_type==null&&overdate==null){
					s='未审批';
				}else if (abtime!=null&&return_type==null) {
					s='已归还未确认';
				}else if (abtime!=null&&return_type!=null&&evaluate==null) {
					s='已归还已确认';
				}else if (abtime==null&&has_get!=null&&evaluate==null) {
					s='未归还';
				}else if (evaluate!=null) {
					s='已评价反馈';
				}
				
				var imgurl='';
				if (s=='已确认归还') {
					imgurl='dingdingweb/HUI/img/typelist/compl.png';
				}else if (i==0) {
					imgurl='dingdingweb/HUI/img/typelist/new.png';
				}else if (s=='未归还') {
					imgurl='dingdingweb/HUI/img/typelist/return.png';
				}else if (s=='审批拒绝') {
					imgurl='dingdingweb/HUI/img/typelist/refused.png';
				}else if (s=='未审批') {
					imgurl='dingdingweb/HUI/img/typelist/Noapproval.png';
				}else if (s=='审批通过') {
					imgurl='dingdingweb/HUI/img/typelist/Passapproval.png';
				}else{
					imgurl='dingdingweb/HUI/img/typelist/process.png';
				}
				var div = document.createElement('div');
				div.innerHTML =
					'<div class="hui-accordion" onclick="needY(this)">'+
					'<div class="hui-accordion-title" style="background-image:url('+imgurl+');background-repeat:no-repeat;background-position:80% 20%;background-size:38px 38px;">'+equipment+'</div>'+
					'<div class="hui-accordion-content hui-list">'+
					'<form action="ITServices/eborrowing/backToBase" method="post" id="myback" class="hui-form">'+
							
							'<div class="hui-form-items">'+
					     	'<div class="hui-form-items-title">状态</div>'+
							'<input type="text" readonly="readonly"'+
								'value="'+s+'" class="hui-input" id="s" name="s">'+
							'</div>'+
								
							
							'<div class="hui-form-items">'+
					     	'<div class="hui-form-items-title">单据号</div>'+
							'<input type="text" readonly="readonly"'+
								'value="'+dcnumber+'" class="hui-input" id="dcnumber" name="dcnumber">'+
							'</div>'+
							
								
							'<div class="hui-form-items">'+
							'<div class="hui-form-items-title">借用设备</div>'+
							'<input type="text" readonly="readonly"'+
								'value="'+equipment+'" class="hui-input" id="equipment" name="equipment">'+
							'</div>'+
									
							'<div id="never">'+
							
							'<div style="padding:15px 30px 10px 10px; ">'+
							'<div class="hui-form-items-title"></div>'+
								'<input type="submit" value="归还" class="hui-button hui-primary hui-fr">'+
							'</div>'+
							'</div>'+
						'</form>'+
						'</div>'+
					'</div>';
					hui(div).appendTo('#list');
					
		    	}
			
	    		page++;
	            hui.endLoadMore();
	        var pageInfo = JSON.parse(re).pageInfo;
			var hasNextPage = pageInfo.hasNextPage;
//	 			alert(hasNextPage);
			if(hasNextPage == false){
		        hui.endLoadMore(true, '已经到头了...');
// 		        alert("已经到头了...");
				hui.accordion(true, false);
		         return false;
		     }else {
		    	 hui.accordion(true, false);
			}
			
		},
		error:function(){
			alert("查询失败");
		}
	})
	hui.accordion(true, false);
}

</script>
</body>
</html>
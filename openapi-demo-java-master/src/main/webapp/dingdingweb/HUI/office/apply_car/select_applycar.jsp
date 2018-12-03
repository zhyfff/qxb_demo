<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@include file="/top2.jsp"%>
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
<title>查询车辆申请</title>
</head>
<body>

<header class="hui-header">
		
<!--     <div id="hui-back"></div> -->
    <div id="hui-header-sreach">
    	<div id="hui-header-sreach-icon"></div>
		<form><input type="search" id="searchKey" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入姓名" /></form>
	</div>
	<div id="hui-header-menu" onclick="hui.showSlideMenu();"></div>
<!-- 	<div class="hui-header-sreach-txt" onclick="clearSearch();">清空</div> -->
	<input  type="hidden" id = "info" value="${userjson}">
	
</header>
<input type="hidden" id="username">
<div class="hui-wrap">
    <div id="refreshContainer" class="hui-refresh">
        <div class="hui-refresh-icon"></div>
        <div class="hui-refresh-content hui-list">
            <ul id="list"></ul>
        </div>
    </div>
</div>




<script type="text/javascript" src="${basePath}dingdingweb/HUI/js/hui-refresh-load-more.js"></script>
<script type="text/javascript">
var page = 2;
var kwd = hui('#searchKey').val();
kwd = kwd.trim();
if (kwd!='') {
// 	hui.refresh('#refreshContainer', refresh);
	refresh();
}
hui.loadMore(getMore);
//加载更多
function getMore(){
// 	alert(getmore);
 	hui.loading('加载更多');
	var kwd = hui('#searchKey').val();
	kwd = kwd.trim();
	if(kwd.length < 2){
		hui.toast('关键字至少2个字符');
		hui('.hui-hot-sreach').show();
		return;
	}
    $.ajax({
		url:'Office/Applycar/SelectList?info='+$("#info")+'&searchkey='+kwd+'&pageNum='+page,
		type:'get',
		dataType:'text',
		success:function(result){
			hui.closeLoading();
			var res = JSON.parse(JSON.stringify(result));

	    	var alist = JSON.parse(res).applycarList;
//	     	alert(alist)
	    	for (var j = 0; j < alist.length; j++) {
	    		var dcnumber = alist[j].dcnumber;
//	     		alert(dcnumber);
	    		var name = alist[j].name; 
	    		var apply_time = alist[j].apply_time;
				var borrowde = alist[j].borrowde;
	    		var li = document.createElement('li');
	            li.innerHTML ='<a href="javascript:toDetailed('+dcnumber+');"><div class="hui-list-text">'+name+'-'+borrowde+'-'+apply_time+'</div></a>'; 
	            hui(li).appendTo('#list');    
	    	}
	    		page++;
	            hui.endLoadMore();
	        var pageInfo = JSON.parse(res).pageInfo;
			var hasNextPage = pageInfo.hasNextPage;
//	 			alert(hasNextPage);
			if(hasNextPage == false){
		        hui.endLoadMore(true, '已经到头了...');
// 		        alert("已经到头了...");
		         return false;
		     }
	         
		},
		error:function(){
			alert("查询失败");
		}
	})
}

//下拉刷新
function refresh(){
// 	alert(refresh)
    hui.loading('加载中...');
	var kwd = hui('#searchKey').val();
	kwd = kwd.trim();
	if(kwd.length < 2){
		hui.toast('关键字至少2个字符');
		hui('.hui-hot-sreach').show();
		return;
	}
    $.ajax({
		url:'Office/Applycar/SelectList?info='+$("#info")+'&searchkey='+kwd+'&pageNum=1',
		type:'get',
		dataType:'text',
		success:function(result){
			 hui.closeLoading();
			var res = JSON.parse(JSON.stringify(result));
// 			alert(res);
			var alist = JSON.parse(res).applycarList;
// 			alert(alist)
			for (var j = 0; j < alist.length; j++) {
				var dcnumber = alist[j].dcnumber;
// 				alert(dcnumber);
				var name = alist[j].name; 
				var apply_time = alist[j].apply_time;
				var borrowde = alist[j].borrowde;
				var li = document.createElement('li');
	            li.innerHTML ='<a href="javascript:toDetailed('+dcnumber+');"><div class="hui-list-text">'+name+'-'+borrowde+'-'+apply_time+'</div></a>'; 
	            hui(li).appendTo('#list');
			}
			 page = 2;
			//结束刷新
	          hui.endRefresh();
	          //重置加载更多状态
	          hui.resetLoadMore();
		},
		error:function(){
			alert("查询失败");
		}
	})
}
</script>

<script type="text/javascript">
//监听搜索事件
document.getElementById('searchKey').addEventListener('keyup', function(e){if(e.keyCode == 13){searchNow();}});
function hotSearch(k){
	hui('#searchKey').val(k);
	searchNow();
}
//核心搜索函数
function searchNow(){
	hui.loading('加载中...');
	document.activeElement.blur();
	var kwd = hui('#searchKey').val();
	kwd = kwd.trim();
	if(kwd.length < 2){
		hui.toast('关键字至少2个字符');
		hui('.hui-hot-sreach').show();
		return;
	}
// 	//关闭热搜
// 	hui('.hui-hot-sreach').hide();
// 	hui.toast('搜索 '+ kwd);
	$.ajax({
		url:'Office/Applycar/SelectList?info='+$("#info")+'&searchkey='+kwd+'&pageNum=1',
		type:'get',
		dataType:'text',
		success:function(result){
			hui.closeLoading();
			var res = JSON.parse(JSON.stringify(result));
// 			alert(res);
			var alist = JSON.parse(res).applycarList;
// 			alert(alist)
			for (var j = 0; j < alist.length; j++) {
				var dcnumber = alist[j].dcnumber;
				var jobnumber = alist[j].jobnumber;
				var name = alist[j].name;
				var borrowde = alist[j].borrowde;
				var apply_time = alist[j].apply_time;
				var use_time = alist[j].use_time;
				var apply_reason = alist[j].apply_reason;
				var pnum = alist[j].pnum;
				var driver = alist[j].driver;
				var audit_name = alist[j].audit_name;
				var audit_opinion = alist[j].audit_opinion;
				var audit_time = alist[j].audit_time;
				var note = alist[j].note;
				var audit_type = alist[j].audit_type;
				var evaluation = alist[j].evaluation;
				var over_time = alist[j].over_time;
				var li = document.createElement('li');
	            li.innerHTML ='<a href="javascript:toDetailed('+dcnumber+');"><div class="hui-list-text">'+name+'-'+borrowde+'-'+apply_time+'</div></a>'; 
	            hui(li).appendTo('#list');			
	            }
		},
		error:function(){}
		
	})
}


	function toDetailed(dcnumber) {
		var info = Encrypt($("#info").attr("value"));
		//		alert(info)
		if (info == "") {
			alert("请稍等")
		} else {
			window.location = 'Office/Applycar/Detailed?info=' + encodeURIComponent(info)+'&dcnumber='+dcnumber;	
		}
	}
	
</script>

</body>
</html>
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

<title>查询快递通知</title>
</head>
<body>

	<header class="hui-header">
		
<!--     <div id="hui-back"></div> -->
    <div id="hui-header-sreach" style="margin-left: 20px;">
    	<div id="hui-header-sreach-icon"></div>
		<form><input type="search" id="searchKey" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入姓名" /></form>
	</div>
<!-- 	<div class="hui-header-sreach-txt" onclick="clearSearch();">清空</div> -->
		<div id="hui-header-menu" onclick="hui.showSlideMenu();"></div>
	<input  type="hidden" id = "info" value="${userjson}">
	
	</header>
	<input type="hidden" id="username">
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
var page=1;
function nextPage(){
	var kwd = hui('#searchKey').val();
	kwd = kwd.trim();
	if(kwd.length < 2){
		hui.toast('关键字至少2个字符');
		hui('.hui-hot-sreach').show();
		return;
	}
	$.ajax({
		url:'${basePath}Office/SearchCourierNotice?searchkey='+kwd+'&pageNum='+page,
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
	var kwd = hui('#searchKey').val();
	kwd = kwd.trim();
	if(kwd.length < 2){
		hui.toast('关键字至少2个字符');
		hui('.hui-hot-sreach').show();
		return;
	}
 	hui.loading('加载更多');
	
    $.ajax({
		url:'${basePath}Office/SearchCourierNotice?searchkey='+kwd+'&pageNum='+page,
		type:'get',
		dataType:'text',
		success:function(result){
			hui.closeLoading();
			var re = JSON.parse(JSON.stringify(result));
			var blist = JSON.parse(re).blist;
			for (var i = 0; i < blist.length; i++) {
				var dcnumber=blist[i].dcnumber
				var recipients=blist[i].recipients
				var express_no=blist[i].express_no
				var notice_time=blist[i].notice_time
				var pay_type=blist[i].pay_type
				var notice_word=blist[i].notice_word
				var lq=blist[i].lq
				var express=blist[i].express
				var note=blist[i].note
				var evaluation=blist[i].evaluation
				var overtime=blist[i].overtime
				var s = '';
				if(lq!=null&&overtime!=null)
				{
					s='已领取并确认收到';
				}else if (lq!=null&&overtime==null) {
					s='已领取未确认收到';
				}else if (lq==null) {
					s='未领取';
				}
				var div = document.createElement('div');
				 div.innerHTML ='<div class="hui-accordion">'+
					'<div class="hui-accordion-title" >'+recipients+'-'+express+'_'+s+'</div>'+
					'<div class="hui-accordion-content hui-list">'+
								'<div class="hui-form-items">'+
									'<div class="hui-form-items-title">单据号</div>'+
									'<input type="text" name="dcnumber" id="dcnumber"'+
										'class="hui-input" value="'+dcnumber+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">收件人</div>'+
								'<input type="text" name="recipients" id="recipients"'+
									'class="hui-input" value="'+recipients+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">快递号</div>'+
								'<input type="text" name="express_no" id="express_no"'+
									'class="hui-input" value="'+express_no+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">通知时间</div>'+
								'<input type="text" name="notice_time" id="notice_time"'+
									'class="hui-input" value="'+notice_time+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">是否到付</div>'+
								'<input type="text" name="pay_type" id="pay_type"'+
									'class="hui-input" value="'+pay_type+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">领取人</div>'+
								'<input type="text" name="lq" id="lq"'+
									'class="hui-input" value="'+lq+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">快递公司</div>'+
								'<input type="text" name="express" id="express"'+
									'class="hui-input" value="'+express+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">评价</div>'+
								'<input type ="text" id="evaluation" name="evaluation" class="hui-input" value="'+evaluation+'" readonly="readonly">'+	
								'</div>'+
								
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
		hui.closeLoading();
		return;
	}
	
	$.ajax({
		url:'${basePath}Office/SearchCourierNotice?searchkey='+kwd+'&pageNum=1',
		type:'GET',
		dataType:'text',
		success:function (result){
			hui.closeLoading();
			var re = JSON.parse(JSON.stringify(result));
			var blist = JSON.parse(re).blist;
			for (var i = 0; i < blist.length; i++) {
				var dcnumber=blist[i].dcnumber;
				var recipients=blist[i].recipients;
				var express_no=blist[i].express_no;
				var notice_time=blist[i].notice_time;
				var pay_type=blist[i].pay_type;
				var notice_word=blist[i].notice_word;
				var lq=blist[i].lq;
				var express=blist[i].express;
				var note=blist[i].note;
				var evaluation=blist[i].evaluation;
				var overtime=blist[i].overtime;
				var s = '';
				if(lq!=null&&overtime!=null)
				{
					s='已领取并确认收到';
				}else if (lq!=null&&overtime==null) {
					s='已领取未确认收到';
				}else if (lq==null) {
					s='未领取';
				}
				var div = document.createElement('div');
				 div.innerHTML ='<div class="hui-accordion">'+
					'<div class="hui-accordion-title" >'+recipients+'-'+express+'_'+s+'</div>'+
					'<div class="hui-accordion-content hui-list">'+
								'<div class="hui-form-items">'+
									'<div class="hui-form-items-title">单据号</div>'+
									'<input type="text" name="dcnumber" id="dcnumber"'+
										'class="hui-input" value="'+dcnumber+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">收件人</div>'+
								'<input type="text" name="recipients" id="recipients"'+
									'class="hui-input" value="'+recipients+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">快递号</div>'+
								'<input type="text" name="express_no" id="express_no"'+
									'class="hui-input" value="'+express_no+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">通知时间</div>'+
								'<input type="text" name="notice_time" id="notice_time"'+
									'class="hui-input" value="'+notice_time+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">是否到付</div>'+
								'<input type="text" name="pay_type" id="pay_type"'+
									'class="hui-input" value="'+pay_type+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">领取人</div>'+
								'<input type="text" name="lq" id="lq"'+
									'class="hui-input" value="'+lq+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">快递公司</div>'+
								'<input type="text" name="express" id="express"'+
									'class="hui-input" value="'+express+'" readonly="readonly">'+
								'</div>'+
								
								'<div class="hui-form-items">'+
								'<div class="hui-form-items-title">评价</div>'+
								'<input type ="text" id="evaluation" name="evaluation" class="hui-input" value="'+evaluation+'" readonly="readonly">'+	
								'</div>'+
								
						'</div>'+
					'</div>';
		            
		            hui(div).appendTo('#list');
			}
			hui.accordion(true, false);
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
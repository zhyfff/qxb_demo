<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@include file="/top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="yes" name="apple-touch-fullscreen" />    
<meta name="data-spm" content="a215s" />
<meta content="telephone=no,email=no" name="format-detection" />    
<meta content="fullscreen=yes,preventMove=no" name="ML-Config" />
<link href="icon114-114.png" rel="apple-touch-icon-precomposed">
<link href="icon.png" rel="Shortcut Icon" type="image/x-icon" />
<script type="text/javascript">
$(document).ready(function (){
	hui.loading('加载中...');
	getMyApply();
})

function getMyApply(){
	$.ajax({
		url:'${basePath}Contract/getMyContractApply?name='+$("#userName").text()+'&pageNum=1',
		type:'GET',
		dataType:'text',
		success:function (result){
			hui.closeLoading();
			var re = JSON.parse(JSON.stringify(result));
			var blist = JSON.parse(re).blist;
			for (var i = 0; i < blist.length; i++) {
				var dcnumber=blist[i].dcnumber;
				var borrowid=blist[i].borrowid;
				var borrowname=blist[i].borrowname;
				var borrowde=blist[i].borrowde;
				var link_contractName=blist[i].link_contractName;
				var link_reason=blist[i].link_reason;
				var link_abacktime=blist[i].link_abacktime;
				var link_spid=blist[i].link_spid;
				var link_spname=blist[i].link_spname;
				var link_spopinions=blist[i].link_spopinions;
				var link_spdate=blist[i].link_spdate;
				var link_abdate=blist[i].link_abdate;
				var link_rdate=blist[i].link_rdate;
				var link_note=blist[i].link_note;
				var link_evaluation=blist[i].link_evaluation;
				var overdate=blist[i].overdate;
				var audittype=blist[i].audittype;
				var s = '';
				if (audittype== '是') {
					s='审核通过';
				}else if (audittype == '否') {
					s='失败';
				}else{
					s='审核中';
				}
				var div = document.createElement('div');
				div.innerHTML ='<div class="hui-accordion" onclick="auditY(this)">'+
				'<div class="hui-accordion-title" >'+borrowname+'-'+borrowde+'_'+s+'</div>'+
				'<div class="hui-accordion-content hui-list">'+
				'<form action="${basePath}Contract/applyToBasse" method="post" id="myapply">'+
				
				'<div class="hui-form-items">'+
		     	'<div class="hui-form-items-title">单据号</div>'+		
				'<input type="text" readonly="readonly"'+
					'value="'+dcnumber+'" id="dcnumber" name="dcnumber" class="hui-input">'+
				'</div>'+

				'<input type="hidden" readonly="readonly"'+
					'value="'+borrowid+'" id="borrowid" name="borrowid">'+
			
		
				'<input type="hidden" readonly="readonly"'+
					'value="'+borrowname+'" id="borrowname" name="borrowname">'+
			
				'<input type="hidden" readonly="readonly"'+
					'value="'+borrowde+'" id="borrowde" name="borrowde">'+
			
				'<div class="hui-form-items">'+
			    '<div class="hui-form-items-title">借用合同名</div>'+
				'<input type="text" readonly="readonly"'+
					'value="'+link_contractName+'" id="link_contractName" name="link_contractName" class="hui-input">'+
				'</div>'+
			
				'<input type="hidden" readonly="readonly"'+
					'value="'+link_reason+'" id="link_reason" name="link_reason">'+
			
				'<input type="hidden" readonly="readonly"'+
					'value="'+link_abacktime+'" id="link_abacktime" name="link_abacktime">'+
			
				'<input type="hidden" value="'+s.link_spid+'" id="link_spid" name="link_spid">'+
			
				'<input type="hidden" value="'+link_spname+'" id="link_spname" name="link_spname">'+
			
				'<input  type="hidden" id="link_spopinions" name="link_spopinions" value="'+link_spopinions+'">'+
			
				'<input type="hidden" id="link_spdate" name="link_spdate" value="'+link_spdate+'">'+
			
				'<input type="hidden" id="link_abdate" name="link_abdate" value="'+link_abdate+'">'+
			
				'<input type="hidden" id="radio" name="radio" value="'+audittype+'">'+
			
				'<div id="never">'+
				'<div class="hui-form-items">'+
		     	'<div class="hui-form-items-title">服务评价</div>'+
		     	'<textarea placeholder="服务评价" name="link_evaluation" required="required"></textarea>'+
		     	'</div>'+

		    
				'<div class="hui-form-items">'+
				'<div class="hui-form-items-title">备注</div>'+
				 '<textarea placeholder="备注" name="link_note" required="required"></textarea>'+
				'</div>'+
			
				'<div  style="padding:15px 30px 50px 10px; ">'+
				'<div class="hui-form-items-title"></div>'+
					'<input id="submit" type="submit" value="提交评价" class="hui-button hui-primary hui-fr">'+
				'</div>'+
				'</div>'+
				'<form>'+
				'</div>'+
			'</div>';
				
				 hui(div).appendTo('#list');
			
			
			}
			hui.accordion(true, false);
		},
		error:function (){
			alert("获取失败");
		}
	})
	hui.accordion(true, false);
}

function auditY(target){
	var y = $(target).children(".hui-accordion-title").text();
//		alert(y);
	if (y.indexOf("审核通过")==-1) {
		$(target).children(".hui-accordion-content").children("#myapply").children("#never").hide();
	}
}
</script>
<title>我的申请</title>


</head>
<body>

	<header class="header">
	<div class="hui-list" align="center">
		<c:forEach items="${userinfoList}" var="u" varStatus="abc">
			<div  class="hui-list-icons" style="width:110px; height:80px;">
				<img id="userImg" alt="头像" src="${u.avatar}" style="width:66px; margin:0px; border-radius:50%;">
			</div>
			<div align="center" style="height:auto; height:40px; padding-bottom:8px;" id="userName">
				欢迎您 : ${u.name}
			</div>
			<input  type="hidden" id = "info" value="${userjson}">
			<input type="hidden" id="username" value="${u.name}">
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
		url:'${basePath}Contract/getMyContractApply?name='+$("#userName").text()+'&pageNum='+page,
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
		url:'${basePath}Contract/getMyContractApply?name='+$("#userName").text()+'&pageNum='+page,
		type:'get',
		dataType:'text',
		success:function(result){
			hui.closeLoading();
			var re = JSON.parse(JSON.stringify(result));
			var blist = JSON.parse(re).blist;
			for (var i = 0; i < blist.length; i++) {
				var dcnumber=blist[i].dcnumber
				var borrowid=blist[i].borrowid
				var borrowname=blist[i].borrowname
				var borrowde=blist[i].borrowde
				var link_contractName=blist[i].link_contractName
				var link_reason=blist[i].link_reason
				var link_abacktime=blist[i].link_abacktime
				var link_spid=blist[i].link_spid
				var link_spname=blist[i].link_spname
				var link_spopinions=blist[i].link_spopinions
				var link_spdate=blist[i].link_spdate
				var link_abdate=blist[i].link_abdate
				var link_rdate=blist[i].link_rdate
				var link_note=blist[i].link_note
				var link_evaluation=blist[i].link_evaluation
				var overdate=blist[i].overdate
				var audittype=blist[i].audittype
				var s = '';
				if (audittype== '是') {
					s='审核通过';
				}else if (audittype == '否') {
					s='失败';
				}else{
					s='审核中';
				}
				var div = document.createElement('div');
				div.innerHTML ='<div class="hui-accordion" onclick="auditY(this)">'+
				'<div class="hui-accordion-title" >'+borrowname+'-'+borrowde+'_'+s+'</div>'+
				'<div class="hui-accordion-content hui-list">'+
				'<form action="${basePath}Contract/applyToBasse" method="post" id="myapply">'+
				
				'<div class="hui-form-items">'+
		     	'<div class="hui-form-items-title">单据号</div>'+		
				'<input type="text" readonly="readonly"'+
					'value="'+dcnumber+'" id="dcnumber" name="dcnumber" class="hui-input">'+
				'</div>'+

				'<input type="hidden" readonly="readonly"'+
					'value="'+borrowid+'" id="borrowid" name="borrowid">'+
			
		
				'<input type="hidden" readonly="readonly"'+
					'value="'+borrowname+'" id="borrowname" name="borrowname">'+
			
				'<input type="hidden" readonly="readonly"'+
					'value="'+borrowde+'" id="borrowde" name="borrowde">'+
			
				'<div class="hui-form-items">'+
			    '<div class="hui-form-items-title">借用合同名</div>'+
				'<input type="text" readonly="readonly"'+
					'value="'+link_contractName+'" id="link_contractName" name="link_contractName" class="hui-input">'+
				'</div>'+
			
				'<input type="hidden" readonly="readonly"'+
					'value="'+link_reason+'" id="link_reason" name="link_reason">'+
			
				'<input type="hidden" readonly="readonly"'+
					'value="'+link_abacktime+'" id="link_abacktime" name="link_abacktime">'+
			
				'<input type="hidden" value="'+s.link_spid+'" id="link_spid" name="link_spid">'+
			
				'<input type="hidden" value="'+link_spname+'" id="link_spname" name="link_spname">'+
			
				'<input  type="hidden" id="link_spopinions" name="link_spopinions" value="'+link_spopinions+'">'+
			
				'<input type="hidden" id="link_spdate" name="link_spdate" value="'+link_spdate+'">'+
			
				'<input type="hidden" id="link_abdate" name="link_abdate" value="'+link_abdate+'">'+
			
				'<input type="hidden" id="radio" name="radio" value="'+audittype+'">'+
			
				'<div id="never">'+
				'<div class="hui-form-items">'+
		     	'<div class="hui-form-items-title">服务评价</div>'+
		     	'<textarea placeholder="服务评价" name="link_evaluation" required="required"></textarea>'+
		     	'</div>'+

		    
				'<div class="hui-form-items">'+
				'<div class="hui-form-items-title">备注</div>'+
				 '<textarea placeholder="备注" name="link_note" required="required"></textarea>'+
				'</div>'+
			
				'<div  style="padding:15px 30px 50px 10px; ">'+
					'<input id="submit" type="submit" value="提交评价" class="hui-button hui-fl">'+
				'</div>'+
				'</div>'+
				'<form>'+
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
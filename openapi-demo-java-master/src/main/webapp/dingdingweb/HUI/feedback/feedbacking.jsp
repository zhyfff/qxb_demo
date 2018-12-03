<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@include file="/top.jsp"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
<meta content="telephone=no,email=no" name="format-detection">
<meta name="wap-font-scale" content="no">
<title>查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>


<body>
<!-- 	<header class="hui-header"> -->
<!-- 		<div id="hui-header-sreach"> -->
<!--     	<div id="hui-header-sreach-icon"></div> -->
<!-- 		<form name="fs" action="Contract/SelectList.do" method="post" class="form-search"><input type="search" id="searchKey" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入商品关键字" /></form> -->
<!-- 	</div> -->
<!-- 	</header> -->
	
	<header class="header">
		<div class="hui-list" align="center">
		<c:forEach items="${userinfoList}" var="u" varStatus="abc">
			<div  class="hui-list-icons" style="width:110px; height:80px;">
				<img id="userImg" alt="头像" src="${u.avatar}" style="width:66px; margin:0px; border-radius:50%;">
			</div>
			<div align="center" style="height:auto; height:40px; padding-bottom:8px;">
				欢迎您 : ${u.name}
			</div>
		 	<div  align="center" style="height:auto; height:60px; padding-bottom:8px;">
				<!-- 您当前在钉钉的userId为 : ${u.userid}-->
				<input  type="hidden" id = "info" value="${userjson}">
			</div>
		</c:forEach>
	</div>
		

	<div class="hui-wrap">
	<c:forEach items="${feedlist}" var="s" varStatus="abc">
	<form action="feedback/torating" method="post" class="hui-form">
				
	<div class="hui-accordion">
	
		<div class="hui-accordion-title">单 据 号:${s.dctid}-单据状态-
			<c:choose>
					  
					    <c:when test="${s.back_time != null&&s.done_time eq null}">
					        	未评价
					    </c:when>
					    <c:when test="${s.back_time eq null}">
					        	未处理
					    </c:when>				    
					    
					    
			</c:choose>
		</div>
		<div class="hui-accordion-content hui-list">
		<ul>
			
				<li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">单 据 号</div>
									<input type="text" readonly="readonly" id="dctid" name="dctid"
										   value="${s.dctid}"   class="hui-input">
									</div>
								</li>
								
								 
					        	
					    
								<li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">提交人员工号</div>
									<input type="text" readonly="readonly"
											value="${s.complaint_jobnum}"   class="hui-input">
											</div>
								</li>
								<li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">提交人员姓名</div>
									<input type="text" readonly="readonly"
											value="${s.complaint_people}"    class="hui-input">
											</div>
								</li>
								<li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">提交人员部门</div>
									<input type="text" readonly="readonly"
											value="${s.complaint_depart}"   class="hui-input">
											</div>
								</li>
						
								<li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">投诉内容</div>
									<input type="text" readonly="readonly"
											value="${s.complaint_content}"    class="hui-input">
									</div>
								</li>	
								   <li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">接收人</div>
									<input type="text" readonly="readonly"
											value="${s.recive_people}"   class="hui-input">
											</div>
								</li>
								
								
								
						<c:choose>
					    <c:when test="${s.back_time != null&&s.done_time eq null}">
					        	<li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">反馈时间</div>
									<input type="text" readonly="readonly"
											value="${s.back_time}"   class="hui-input">
											</div>
								</li>
								<li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">反馈内容</div>
									<input type="text" readonly="readonly"
											value="${s.back_content}"   class="hui-input">
											</div>
								</li>
								<li>
									<div class="hui-list-icons"><img src="${basePath}dingdingweb/HUI/img/index/number.png" /></div>
									<div class="hui-form-items">
									<div class="hui-form-items-title">服务评价</div>
											<input type="text" id="service_rating" name ="service_rating"
											   class="hui-input">
											</div>								
							</li>
								<li>
										<div align="center">
											<input type="submit" id="submit"  name="submit"  value="提交" class="hui-button hui-fl">
										</div>
									</li>
					    </c:when>
					   				    
					    
					    
			</c:choose>
					
				          
								
							
							
						 
						</ul>
					</div>
					</div>
				
				</form>
		</c:forEach>
	</div>
</body>

</html>
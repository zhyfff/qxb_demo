function getmessage_todo(){
	
	$.ajax({
		url:'Message/getMessageTodo?auditname='+$("#userName").text()+'&pageNum=1',
		type:'GET',
		dataType:'text',
		success:function(result){
			var re = JSON.parse(JSON.stringify(result));
			var blist = JSON.parse(re).blist;
			var html='';
			for (var i = 0; i < blist.length; i++) {
				var _id=blist[i]._id;
				var dcnumber=blist[i].dcnumber;
				var name_id=blist[i].name_id;
				var name=blist[i].name;
				var audit_name_id=blist[i].audit_name_id;
				var audit_name=blist[i].audit_name;
				var type=blist[i].type;
				var state=blist[i].state;
				var insert_time=blist[i].insert_time
				if (type=="快递发放") {
					html+='<a href="Message/toDetailMessage?info='+encodeURIComponent(Encrypt($("#info").attr("value")))+'&dcnumber='+dcnumber+'&audit_name='+encodeURIComponent(name)+'&type='+encodeURIComponent(type)+'">'+
//					'<div class="hui-list-text">'+insert_time+'-'+audit_name+'-'+type+'-'+state+'</div>'+
					'<div class="hui-media-content" style="padding:10px;width:100%; border-bottom:1px solid #F3F4F5;background-image:url(dingdingweb/HUI/img/typelist/todo_icon.png);background-repeat:no-repeat;background-position:80% 20%;background-size:120px 120px;">'+
						'<h1 style="font-size:25px;color:#FFD700">'+type+'</h1>'+
						'<p style="font-size:20px;">发起时间：'+insert_time+'</p>'+
						'<p style="font-size:20px;">单据号：'+dcnumber+'</p>'+
						'<p style="font-size:20px;">经办人：'+audit_name+'</p>'+
						'<p style="font-size:20px; color:#DC143C;">'+state+'</p>'+
					'</div>'+
			
					'</a>';
				}else {
					html+='<a href="Message/toDetailMessage?info='+encodeURIComponent(Encrypt($("#info").attr("value")))+'&dcnumber='+dcnumber+'&name='+encodeURIComponent(name)+'&audit_name='+encodeURIComponent(audit_name)+'&type='+encodeURIComponent(type)+'">'+
//					'<div class="hui-list-text">'+insert_time+'-'+audit_name+'-'+type+'-'+state+'</div>'+
					'<div class="hui-media-content" style="padding:10px;width:100%; border-bottom:1px solid #F3F4F5;background-image:url(dingdingweb/HUI/img/typelist/todo_icon.png);background-repeat:no-repeat;background-position:80% 20%;background-size:120px 120px;">'+
						'<h1 style="font-size:25px;color:#FFD700">'+type+'</h1>'+
						'<p style="font-size:20px;">发起时间：'+insert_time+'</p>'+
						'<p style="font-size:20px;">单据号：'+dcnumber+'</p>'+
						'<p style="font-size:20px;">经办人：'+audit_name+'</p>'+
						'<p style="font-size:20px; color:#DC143C;">'+state+'</p>'+
					'</div>'+
			
					'</a>';
				}
				
				// 				hui(div).appendTo('#todo');
				hui('#todo').html(html);
			}	
		},
		error:function(){
			alert("获取失败");
		}
		
	})
	
}





/*******************************************************************************************************************/
function getmessage_havedeal(){
	
	$.ajax({
		url:'Message/getMessagehavedeal?auditname='+$("#userName").text()+'&pageNum=1',
		type:'GET',
		dataType:'text',
		success:function(result){
			var re = JSON.parse(JSON.stringify(result));
			var blist = JSON.parse(re).blist;
			var html='';
			for (var i = 0; i < blist.length; i++) {
				var _id=blist[i]._id;
				var dcnumber=blist[i].dcnumber;
				var name_id=blist[i].name_id;
				var name=blist[i].name;
				var audit_name_id=blist[i].audit_name_id;
				var audit_name=blist[i].audit_name;
				var type=blist[i].type;
				var state=blist[i].state;
				var insert_time=blist[i].insert_time
				if (type=="快递发放") {
					html+=
					'<a href="Message/toDetailMessage?info='+encodeURIComponent(Encrypt($("#info").attr("value")))+'&dcnumber='+dcnumber+'&audit_name='+encodeURIComponent(name)+'&type='+encodeURIComponent(type)+'">'+
//					'<div class="hui-list-text">'+insert_time+'-'+audit_name+'-'+type+'-'+state+'</div>'+
					'<div class="hui-media-content" style="padding:10px;width:100%; border-bottom:1px solid #F3F4F5;background-image:url(dingdingweb/HUI/img/typelist/finish_icon.png);background-repeat:no-repeat;background-position:80% 20%;background-size:120px 120px;">'+
					'<h1 style="font-size:25px;color:#FFD700;">'+type+'</h1>'+
					'<p style="font-size:20px;">发起时间：'+insert_time+'</p>'+
					'<p style="font-size:20px;">单据号：'+dcnumber+'</p>'+
					'<p style="font-size:20px;">经办人：'+audit_name+'</p>'+
					'<p style="font-size:20px; color:#32CD32;">'+state+'</p>'+
					'</div>'+
					'</a>';
				}else {
					html+=
					'<a href="Message/toDetailMessage?info='+encodeURIComponent(Encrypt($("#info").attr("value")))+'&dcnumber='+dcnumber+'&name='+encodeURIComponent(name)+'&audit_name='+encodeURIComponent(audit_name)+'&type='+encodeURIComponent(type)+'">'+
//					'<div class="hui-list-text">'+insert_time+'-'+audit_name+'-'+type+'-'+state+'</div>'+
					'<div class="hui-media-content" style="padding:10px;width:100%; border-bottom:1px solid #F3F4F5;background-image:url(dingdingweb/HUI/img/typelist/finish_icon.png);background-repeat:no-repeat;background-position:80% 20%;background-size:120px 120px;">'+
					'<h1 style="font-size:25px;color:#FFD700; ">'+type+'</h1>'+
					'<p style="font-size:20px;">发起时间：'+insert_time+'</p>'+
					'<p style="font-size:20px;">单据号：'+dcnumber+'</p>'+
					'<p style="font-size:20px;">经办人：'+audit_name+'</p>'+
					'<p style="font-size:20px; color:#32CD32;">'+state+'</p>'+
					'</div>'+
					'</a>';
				}			
// 				hui(div).appendTo('#havedeal');
				hui('#havedeal').html(html);
			}	
		},
		error:function(){
			alert("获取失败");
		}
	})
	
}


function sendInfor(type){
	var info1=encodeURIComponent(Encrypt($("#info").attr("value")))+'&dcnumber='+dcnumber+'&audit_name='+encodeURIComponent(name)+'&type='+encodeURIComponent(type);
	var info2=encodeURIComponent(Encrypt($("#info").attr("value")))+'&dcnumber='+dcnumber+'&name='+encodeURIComponent(name)+'&audit_name='+encodeURIComponent(audit_name)+'&type='+encodeURIComponent(type);
	var info;
	if (type=="快递发放") {
		info=info1;
	}else {
		info=info2;
	}
	var url="Message/toDetailMessage";
    var form = $("<form method='post'></form>");
    form.attr({"action":url});
	    var input = $("<input type='hidden'>");
	    input.attr({"name":"info"});
	    input.val(info);
	    form.append(input);
	    $("html").append(form);
	    form.submit();


}



 	//跳转到合同借用
 	function tocontract(){
		var info = $("#info").attr("value")
// 		alert(info)
		if (info=="") {
			alert("请稍等")
		}else{
			window.location ='Contract/tocontract.do?info='+encodeURIComponent(info)
		}

	}
 	
 	
//  	跳转到管理员审批
 	function senduserjsontoaudit(){
		var info = $("#info").attr("value")
// 		alert(info)
		if (info=="") {
			alert("请稍等")
		}else{
			window.location ='Contract/tomyaudit?info='+encodeURIComponent(info)
		}
	}
 	
//	跳转到rlfw管理员审批
 	function sendrlfwuserjsontoaudit(){
		var info = $("#info").attr("value")
// 		alert(info)
		if (info=="") {
			alert("请稍等")
		}else{
			window.location ='rlfw/tomyaudit?info='+encodeURIComponent(info)
		}
	}
 	
// 	跳转到我的申请'评价'
 	function senduserjsontomyapply(){
 		var info = $("#info").attr("value")
// 			alert(info)
 		if (info=="") {
 			alert("请稍等")
 		}else{
 			window.location ='Contract/tomyapply?info='+encodeURIComponent(info)
 		}
 	}
 	
 	//跳转到我未归还的合同
 	function senduserjsontoback(){
 		var info = $("#info").attr("value")
// 		alert(info)
 	if (info=="") {
 		alert("请稍等")
 	}else{
 		window.location ='Contract/tomyback?info='+encodeURIComponent(info)
 	}
 	}
 	
 	//跳转到管理员确认归还
 	function senduserjsontofinal(){
 		var info = $("#info").attr("value")
// 		alert(info)
 	if (info=="") {
 		alert("请稍等")
 	}else{
 		window.location ='Contract/tosureback?info='+encodeURIComponent(info)
 	}
 	}
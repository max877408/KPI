/*
* 工作流
* 工作流请求参数
*/

 var wfparame = {			
			GetQueryString : function(name){
					var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
				     var r = window.location.search.substr(1).match(reg);
				     if(r!=null)return  unescape(r[2]); return null;
				},
				setParame : function(){
					 var readonly = wf.GetQueryString("readonly");
					 var processId = wf.GetQueryString("processId");
					 var orderId = wf.GetQueryString("orderId");
					 var taskName = wf.GetQueryString("taskName");
					 var taskId = wf.GetQueryString("taskId");	 
					 
					//查看状态
					 if(processId != null && orderId != null){						 
						 	
						 	var queryParams = $('#dg_list').datagrid('options').queryParams;  
					        queryParams.orderId = orderId;  
					        queryParams.taskId = taskId;  
					        queryParams.processId = processId;
					        queryParams.taskName = taskName;													 	
					 }
				}
 }
 
 $(function(){
	 
	 wfparame.setParame();	
 })
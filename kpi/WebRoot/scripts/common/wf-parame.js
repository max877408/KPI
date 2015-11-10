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
					 var readonly = wfparame.GetQueryString("readonly");
					 var processId = wfparame.GetQueryString("processId");
					 var orderId = wfparame.GetQueryString("orderId");
					 var taskName = wfparame.GetQueryString("taskName");
					 var taskId = wfparame.GetQueryString("taskId");	 
					 
					//查看状态
					var queryParams = $('#dg_list').datagrid('options').queryParams;  
					if(orderId){
						queryParams.orderId = orderId; 
					}
					if(taskId){
						queryParams.taskId = taskId;
					}
					if(processId){
						queryParams.processId = processId;
					}
					if(taskName){
						queryParams.taskName = taskName;
					}			        	
				}
 }
 
 $(function(){
	 
	 wfparame.setParame();	
 })
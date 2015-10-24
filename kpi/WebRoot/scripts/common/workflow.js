/*
* 工作流
* 工作流程页面菜单控制
*/

 var wf = {
				startWorkFlow : function(processId,operator){
					//alert('111111');
					$.post('../kpiYear/saveDeptTask.action', {
						year : year
					}, function(result) {
						if (result.code == "000") {				
							//
						} else {
							$.messager.show({ // show error message
								title : 'Error',
								msg : result.errorMsg
							});
						}
					}, 'json');
				},
				GetQueryString : function(name){
					var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
				     var r = window.location.search.substr(1).match(reg);
				     if(r!=null)return  unescape(r[2]); return null;
				},
				menuStatus : function(){
					 var readonly = wf.GetQueryString("readonly");
					 var processId = wf.GetQueryString("processId");
					 var orderId = wf.GetQueryString("orderId");
					 var taskName = wf.GetQueryString("taskName");
					 var taskId = wf.GetQueryString("taskId");	 
					 
					//查看状态
					 if(processId != null && orderId != null){
						 	$("#tb").hide();
							$('#dg_list').datagrid({	
								 url:'../kpiYear/getKpiDeptList.action?orderId='+orderId+'&taskName='+taskName+'',
								 onLoadSuccess : function(data) {
									
									$("table").find("td[field=dept]").show();									
									
									//页面可以编辑
									if(readonly == '1'){
										tooBar.menuStatus('1');
									}
									else{
										var row = $('#dg_list').datagrid('getData').rows[0];								
										if(row){									
											tooBar.menuStatus(row.auditStatus);
										}
										else{
											tooBar.menuStatus('1');
										}
									}
									
								 }
							});								 	
					 }
				}
 }
 
 $(function(){
	 
	 wf.menuStatus();	
 })
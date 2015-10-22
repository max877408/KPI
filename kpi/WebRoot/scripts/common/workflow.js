/*
* 工作流
* 启动工作流等相关操作
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
		}
}
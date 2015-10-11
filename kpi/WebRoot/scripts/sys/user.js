	$(function(){
	   $('#tt').datagrid({
	   		pageSize:20,
	   		loadmsg:'正在加载,请稍后....',
			onLoadSuccess : function(data) {
				onLoadSuccess(data);					
			}
		});

		//合并单元格
		function onLoadSuccess(data) {			
			var merges = [];
			
			var deptId = '';
			var rows = 0;
			var index = 0;
			$(data.rows).each(function(i){
				if(i == 0){
					deptId = this.deptId;
				}
				else{
					if(deptId == this.deptId){
						rows++;
					}
					else{
						merges.push({index:index,rowspan:rows+1});
						deptId = this.deptId;
						index = index + rows + 1;
						rows = 0;							
					}
					
					//行结束
					if(i == data.rows.length - 1){
						merges.push({index:index,rowspan:rows+1});
					}
				}					
			});
			
			
			for ( var i = 0; i < merges.length; i++) {
				$('#tt').datagrid('mergeCells', {
					index : merges[i].index,
					field : 'deptId',
					rowspan : merges[i].rowspan
				});
			}
		}
	});
	
    function changeP(){
        var dg = $('#tt');
        dg.datagrid('loadData',[]);
        dg.datagrid({pagePosition:$('#p-pos').val()});
        dg.datagrid('getPager').pagination({
            layout:['list','sep','first','prev','sep',$('#p-style').val(),'sep','next','last','sep','refresh']
        });
    }
    
    /**
     * 用户角色
     */
    function userRole(){
    	var row = $('#tt').datagrid('getSelected');
		if (row) {
			
			$('#dlg').dialog('open').dialog('center').dialog('setTitle','用户角色');
			$('#fm').form('load', row);			
		}
		else{
			$.messager.alert("提示", "请选择一条记录！");
		}
    }
    
    /**
     * 保存用户角色
     */
    function saveUserRole(){
    	 $('#fm').form('submit', {
 			url : "../user/saveUserRole",			
 			success : function(result) {
 				var result = eval('(' + result + ')');
 				if (result.code == "000") {
 					$.messager.alert("提示", "提交成功！");	
 					$('#dlg').dialog('close'); // close the dialog
					$('#tt').datagrid('reload'); // reload the user data
					
 				} else {
 					$.messager.show({
 						title : 'Error',
 						msg : result.errorMsg
 					});
 				}
 			}
 		});
    }
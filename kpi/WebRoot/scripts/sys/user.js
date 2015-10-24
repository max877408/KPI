	$(function(){
	   $('#tt').datagrid({
	   		pageSize:15,
	   	    pageList: [15, 20, 30, 40, 50, 100],
	   		loadmsg:'正在加载,请稍后....',
			onLoadSuccess : function(data) {
				onLoadSuccess(data);					
			}
		});

		//合并单元格
		function onLoadSuccess(data) {			
			var merges = [];
			
			var deptName = '';
			var rows = 0;
			var index = 0;
			$(data.rows).each(function(i){
				if(i == 0){
					deptName = this.deptName;
				}
				else{
					if(deptName == this.deptName){
						rows++;
					}
					else{
						merges.push({index:index,rowspan:rows+1});
						deptName = this.deptName;
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
					field : 'deptName',
					rowspan : merges[i].rowspan
				});
			}
		}
		
		//用户查找 
		$("#btSearch").click(function(){
			var dept =$("select[comboname=dept]").combobox("getValue");						
			var userName = $("#userName").val();
			
			
			var queryParams = $('#tt').datagrid('options').queryParams;  
	        queryParams.dept = dept;  
	        queryParams.userName = userName;  
	        
	        //重新加载Datagrid的数据  
	        $('#tt').datagrid('loadData', { total: 0, rows: [] }); 
	        $('#tt').datagrid('reload');
		})
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
    
    /**
     * 设置部门负责人
     */
    function updateDeptCharge(){
    	var row = $('#tt').datagrid('getSelected');
		if (row) {			
			var id = row.id;
			var deptId = row.deptId;
			
			$.post("../user/updateDeptCharge.action?id="+id+"&deptId="+deptId+"", function(rsp) {
				if(rsp.code == "000"){
					$.messager.alert("提示", "提交成功！");				
					$('#tt').datagrid('reload'); // reload the user data
				}
			}, "JSON").error(function() {
				$.messager.alert("提示", "提交错误了！");
			});
		}
		else{
			$.messager.alert("提示", "请选择一条记录！");
		}
    }
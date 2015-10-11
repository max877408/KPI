	$(function(){
		//$("#dlg_permission").
	});
	
	/*
	 * 工具条菜单栏目
	 */
	function newRole() {	
		
		$('#dlg').dialog('open').dialog('center').dialog('setTitle','新增角色');
		$('#fm').form('clear');		
	}
	
	function saveRole(){
		$('#fm').form('submit', {
			url : "../role/saveRole",
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.code == "000") {
					$.messager.alert("提示", "提交成功！");
					$('#dlg').dialog('close'); // close the dialog
					$('#role_list').datagrid('reload'); // reload the user data
					
				} else {
					$.messager.alert("提示", result.errorMsg);					
				}
			}
		});
	}
	
	function editRole() {
		var row = $('#role_list').datagrid('getSelected');
		if (row) {
			
			$('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑角色');
			$('#fm').form('load', row);			
		}
		else{
			$.messager.alert("提示", "请选择一条记录！");
		}
	}
	
	function destroyRole() {
		var row = $('#role_list').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认',
					'你确定要删除当前的角色吗?',
					function(r) {
						if (r) {
							$.post('../role/delRole.action', {
								id : row.id
							}, function(result) {
								if (result.code == "000") {
									$.messager.alert("提示", "删除成功！");
									$('#role_list').datagrid('reload'); // reload the user data
								} else {
									$.messager.show({ // show error message
										title : 'Error',
										msg : result.errorMsg
									});
								}
							}, 'json');
						}
					});
		}
		else{
			$.messager.alert("提示", "请选择一条记录！");
		}
	}
	
	/**
	 * 角色权限
	 */
	function rolePermission() {
		var row = $('#role_list').datagrid('getSelected');
		if (row) {
			
			//clear tree select
			var root = $('#tt').tree('getRoot');  
			$("#tt").tree('uncheck',root.target); 
			
			$.post("../role/getRolePermission.action?roleId="+row.id, function(rsp) {
				$(rsp).each(function(){					
				  var node = $('#tt').tree('find',this.objectCode);
				  $('#tt').tree('check',node.target);
				   //$('#s1').combotree('tree').tree('expandAll', node.target);					
					
				})
			}, "JSON").error(function() {
				$.messager.alert("提示", "提交错误了！");
			});					
				
			$('#dlg_permission').dialog('open').dialog('center').dialog('setTitle','角色权限');
			$('#fm_per').form('load', row);	
		}
		else{
			$.messager.alert("提示", "请选择一条记录！");
		}
	}
	
	/**
	 * 保存角色权限
	 */
	function saveRolePermission(){
        var nodes = $('#tt').tree('getChecked');
        var perId = '';
        for(var i=0; i<nodes.length; i++){
            if (perId != '') perId += ',';
            perId += nodes[i].id;
        }   
        
        $('#fm_per').form('submit', {
			url : "../role/saveRolePermission?perId="+perId,			
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.code == "000") {
					$.messager.alert("提示", "提交成功！");	
				} else {
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				}
			}
		});
    }
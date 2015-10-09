	
	/*
	 * 工具条菜单栏目
	 */
	function newRole() {	
		
		$('#dlg').dialog('open').dialog('center').dialog('setTitle','新增角色');
		$('#fm').form('clear');		
	}
	
	function saveRole(){
		$('#fm').form('submit', {
			url : "../sys/saveRole",
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
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
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
	}
	
	function destroyRole() {
		var row = $('#role_list').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认',
					'你确定要删除当前的角色吗?',
					function(r) {
						if (r) {
							$.post('../kpiYear/delKpiGroup.action', {
								id : row.id
							}, function(result) {
								if (result.code == "000") {
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
	}
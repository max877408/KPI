	
	$(function() {
	
		var dg_list = $('#dg_list').datagrid({
			 striped: true, //行背景交换
			 nowrap: false, //单元格是否可以换行
			 fit: false,
			 pageSize: 15, //每页显示的记录条数，默认为10     
		     pageList: [15, 20, 30, 40, 50, 100],
			 width: 'auto',
		     height: 'auto',
			 onLoadSuccess : function(data) {
				onLoadSuccess(data);					
			 }
		});
		
		//列表合并单元格
		function onLoadSuccess(data) {
		
			var merges = [];
			
			var keyTask = '';
			var keyItem = '';
			
			//关键任务单元格合并
			var rows = 0;
			var index = 0;
			$(data.rows).each(function(i){
				if(i == 0){
					keyTask = this.keyTask;					
				}
				else{
					if(keyTask == this.keyTask){
						rows++;
					}
					else{
						merges.push({index:index,rowspan:rows+1});
						keyTask = this.keyTask;
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
				$('#dg_list').datagrid('mergeCells', {
					index : merges[i].index,
					field : 'keyTask',
					rowspan : merges[i].rowspan
				});
			}
			
			//主要事项单元格合并
			var rows = 0;
			var index = 0;
			merges = [];
			$(data.rows).each(function(i){
				if(i == 0){
					keyItem = this.keyItem;					
				}
				else{
					if(keyItem == this.keyItem){
						rows++;
					}
					else{
						merges.push({index:index,rowspan:rows+1});
						keyItem = this.keyItem;
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
				$('#dg_list').datagrid('mergeCells', {
					index : merges[i].index,
					field : 'keyItem',
					rowspan : merges[i].rowspan
				});
			}
		}
	
		var $dg = $("#dg_add");

		var dg_add = $('#dg_add').datagrid({					
			toolbar : [ {
				text : "添加",
				iconCls : "icon-add",
				handler : function() {
					 $('#dg_add').datagrid('appendRow',{status:'P'});
			            editIndex = $('#dg_add').datagrid('getRows').length-1;
			            $('#dg_add').datagrid('selectRow', editIndex)
			                    .datagrid('beginEdit', editIndex);
				}
			},  {
				text : "删除",
				iconCls : "icon-remove",
				handler : function() {
					if (editIndex == undefined){return}
			        $('#dg_add').datagrid('cancelEdit', editIndex)
			                .datagrid('deleteRow', editIndex);
			        editIndex = undefined;
				}
			}, {
				text : "结束编辑",
				iconCls : "icon-cancel",
				handler :endEdit
			}]
		});
		
		function endEdit(){
			var rows = $dg.datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				$dg.datagrid('endEdit', i);
			}
		}		
	});

	/*
	 * 行编辑
	 */
	var editIndex = undefined;
	function endEditing() {
		if (editIndex == undefined) {
			return true
		}
	
		if ($('#dg_add').datagrid('validateRow', editIndex)) {
			/* var ed = $('#dg_add').datagrid('getEditor', {index:editIndex,field:'dept'});
             var dept = $(ed.target).combobox('getText');
             $('#dg_add').datagrid('getRows')[editIndex]['dept'] = dept;*/
             $('#dg_add').datagrid('endEdit', editIndex);
             editIndex = undefined;
             return true;
		} else {
			return false;
		}
	
	}
	function onClickCell(index, field) {
		if (editIndex != index) {
			if (endEditing()) {
				$('#dg_add').datagrid('selectRow', index).datagrid('beginEdit',
						index);
				 var ed = $('#dg_add').datagrid('getEditor', {index:index,field:field});
				 if (ed){
                     ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                 }
				editIndex = index;
			} else {
				$('#dg_add').datagrid('selectRow', editIndex);
			}
		}
	}
	
	/*
	 * 工具条菜单栏目
	 */
	var url;
	function newKpi() {		
		//新增默认行
		$('#dg_add').datagrid('loadData', { total: 0, rows: [] }); 
		var data_add = [];
		for(var i =1 ; i<= 9; i++){
			data_add.push({
				"id" :"",
				"keyTask" : "",
				"keyItem":"",
				"detailItem" : "",
				"dept" : "",
				"startTime" : "",
				"endTime" : ""
			})
		}
		$('#dg_add').datagrid('loadData', { total: data_add.length, rows: data_add });
		
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
		'新增集团绩效考核指标');
		$('#fm').form('clear');
		
	}
	function editKpi() {
		var row = $('#dlg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('center').dialog('setTitle',
					'Edit User');
			$('#fm').form('load', row);
			url = 'update_user.php?id=' + row.id;
		}
	}
	
	function endEdit(){
		var rows = $('#dg_add').datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			$('#dg_add').datagrid('endEdit', i);
		}
	}
	
	function saveKpiGroup() {
		endEdit();
		var $dg = $("#dg_add");
		if ($dg.datagrid('getChanges').length) {
			
			//设置主要事项
			var keyItem = "";
			var rows = $dg.datagrid("getRows");
			for(var i=0;i<rows.length;i++)
			{			
				if(i == 0){
					keyItem = rows[i].keyItem;
				}
				else{
					if(rows[i].keyItem == ""){
						rows[i].keyItem = keyItem;
					}
					else{
						keyItem = rows[i].keyItem;
					}
				}
			}
			
			var inserted = $dg.datagrid('getChanges', "inserted");
			var deleted = $dg.datagrid('getChanges', "deleted");
			var updated = $dg.datagrid('getChanges', "updated");
			
			var keyTask = $("#fm").find("input[name=keyTask]").val();			
			var effectRow = new Object();
			if (inserted.length) {
				$(inserted).each(function(i){
					
					this.keyTask = keyTask;
				})
				effectRow["inserted"] = JSON.stringify(inserted);
			}
			if (deleted.length) {				
				effectRow["deleted"] = JSON.stringify(deleted);
			}
			if (updated.length) {
				$(updated).each(function(i){					
					this.keyTask = keyTask;
				})
				effectRow["updated"] = JSON.stringify(updated);
			}
			
			if($('#fm').form('validate')){
				$.post("../kpiYear/SaveKpiGroup.action", effectRow, function(rsp) {
					if(rsp.code == "000"){
						$.messager.alert("提示", "提交成功！");
						$('#dlg').dialog('close'); // close the dialog
						$('#dg_list').datagrid('reload'); // reload the user data
					}
				}, "JSON").error(function() {
					$.messager.alert("提示", "提交错误了！");
				});
			}			
		}
		//return false;
		/*$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.code == "000") {
					$('#dlg').dialog('close'); // close the dialog
					$('#dg_list').datagrid('reload'); // reload the user data
					
				} else {
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				}
			}
		});*/
	}
	
	function destroyKpi() {
		var row = $('#dg_list').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认',
					'你确定要删除当前的集团绩效考核指标吗?',
					function(r) {
						if (r) {
							$.post('../kpiYear/delKpiGroup.action', {
								id : row.id
							}, function(result) {
								if (result.code == "000") {
									$('#dg_list').datagrid('reload'); // reload the user data
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
	
  
	
	$(function() {	 
		
		var kpiYear = $("input[name=kpiYear]").val();
		var kpiMonth = $("input[name=kpiMonth]").val();

		var dg_list = $('#dg_list').datagrid({
			 striped: true, //行背景交换
			 nowrap: true, //单元格是否可以换行
			 fit: false,			
			 pageSize: 15, //每页显示的记录条数，默认为10     
		     pageList: [15, 20, 30, 40, 50, 100],
			 width: 'auto',
		     height: 'auto',
		     onSelect:function(rowIndex,rowData){
		    	 return false;
		    	// $('#dg_list').datagrid("unselectRow",rowIndex);
		     },
		     url:'../kpiMonth/getKpiDeptMonthtList.action?kpiYear='+kpiYear+'&kpiMonth='+kpiMonth+'',
			 onLoadSuccess : function(data) {
				onLoadSuccess(data);	
				
				//内容换行
				var div = $(".datagrid-td-merged div");
				div.css({
					"width":div.width(),
					"white-space":"nowrap",
					"text-overflow":"ellipsis",
					"-o-text-overflow":"ellipsis",
					"overflow":"hidden"
				});
				
				$(div).each(function(){
					$(this).parent().attr("title",$(this).text());
				})
				
			 }
		});	
		
		//列表合并单元格
		function onLoadSuccess(data) {
		
			var merges = [];
			
			var keyTask = '';
			var keyItem = '';
			var detailItem = '';
			
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
			
			//具体事项单元格合并
			var rows = 0;
			var index = 0;
			merges = [];
			$(data.rows).each(function(i){
				if(i == 0){
					detailItem = this.detailItem;					
				}
				else{
					if(detailItem == this.detailItem){
						rows++;
					}
					else{
						merges.push({index:index,rowspan:rows+1});
						detailItem = this.detailItem;
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
					field : 'detailItem',
					rowspan : merges[i].rowspan
				});
			}
		}		

		
		function endEdit(){
			var rows = $dg.datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				$dg.datagrid('endEdit', i);
			}
		}
	
	});
	
	/**
	 * 行编辑
	 */
	 var editIndex = undefined;
     function endEditing(){
    	 if (editIndex == undefined) {
 			return true
 		} 	
    	 
 		if ($('#dg_list').datagrid('validateRow', editIndex)) { 			
             // $('#dg_list').datagrid('endEdit', editIndex);
              editIndex = undefined;
              return true;
 		} else {
 			return true;
 		}
      
     }
     function onClickCell(index, field){
    	 if (editIndex != index) {
 			if (endEditing()) {
 				$('#dg_list').datagrid('beginEdit',	index);
 				var ed = $('#dg_list').datagrid('getEditor', {index:index,field:field});
 				 if (ed){
                      ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                  }
 				editIndex = index;
 			} else {
 				$('#dg_list').datagrid('selectRow', editIndex);
 			}
 		}
     }
     
    /**
    * 菜单工具栏
    */    
	function newDeptKpi() {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
				'新增部门年度绩效考核指标');
		$('#fm').form('clear');
		
	}
	
	var dg_index = 1;
	function editDeptKpi() {
		var row = $('#dg_list').datagrid('getSelected');
		if (row) {
			
			toolOp.loadData(row.id);
			
			$('#dg_add').datagrid({
				 striped: true, //行背景交换
				 url:"../kpiYear/getKpiDeptDetail.action?id=" + row.id,
				 onLoadSuccess : function(data) {
					 if(data.rows.length == 0 && dg_index == 1){
						 dg_index++;
						//新增默认行	
						$('#dg_add').datagrid('loadData', { total: 0, rows: [] }); 
						var data_add = [];
						for(var i =1 ; i<= 9; i++){
							data_add.push({
								"id" :"",
								"keyPoint" : "",
								"leadPerson":"",				
								"startTime" : "",
								"endTime" : ""
							})
						}
						$('#dg_add').datagrid('loadData', { total: data_add.length, rows: data_add });
						dg_index = 1;
					 }
					 else{
						 //alert('11111111');
					 }								
				 }
			});
			
			
			$('#dlg').dialog('open').dialog('center').dialog('setTitle','制定部门年度绩效考核指标');			 
			$('#fm').form('clear');
			
		}
		else{
			$.messager.alert("提示", "请选择一个具体事项,进行指定部门绩效考核指标！");
		}	
	}
	
	function endEdit(){
		var rows = $('#dg_add').datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			$('#dg_add').datagrid('endEdit', i);
		}
	}
	
	function saveDeptKpi() {
		endEdit();
		var $dg = $("#dg_add");
		if ($dg.datagrid('getChanges').length) {
			
			//获取Form 表单内容
			var formData = $('#fm').serializeArray();	
			
			//当前选中行id
			var row = $('#dg_list').datagrid('getSelected');
			formData["id"] = row.id;
			
			var parame = {};
			$.each(formData,function(){
				parame[this.name] = this.value;
			})
			formData = JSON.stringify(parame);
			
			console.log(formData);
			
			var inserted = $dg.datagrid('getChanges', "inserted");
			var deleted = $dg.datagrid('getChanges', "deleted");
			var updated = $dg.datagrid('getChanges', "updated");			
					
			var effectRow = new Object();
			if (inserted.length) {				
				effectRow["inserted"] = JSON.stringify(inserted);
			}
			if (deleted.length) {				
				effectRow["deleted"] = JSON.stringify(deleted);
			}
			if (updated.length) {				
				effectRow["updated"] = JSON.stringify(updated);
			}
			effectRow["data"] = formData;
			
			if($('#fm').form('validate')){
				$.post("../kpiYear/SaveKpiDept.action", effectRow, function(rsp) {
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
	}
	function destroyDeptKpi() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm',
					'Are you sure you want to destroy this user?',
					function(r) {
						if (r) {
							$.post('destroy_user.php', {
								id : row.id
							}, function(result) {
								if (result.success) {
									$('#dg').datagrid('reload'); // reload the user data
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
	
	/**
	 * 单元格样式
	 * @param value
	 * @param row
	 * @param index
	 * @returns {String}
	 */
	function cellStyler(value,row,index){		
		return 'background-color:#e6f0ff;';
	}
	
	/**
	 * 工具栏菜单操作
	 */
	var toolOp = {
					loadData : function(id){						
						$('#fm').form('load', '../kpiYear/getKpiDept.action?id='+ id);
					}
	              
	}
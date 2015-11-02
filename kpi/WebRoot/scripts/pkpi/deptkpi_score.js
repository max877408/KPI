  
	$(function() {	
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
		     url:'../kpiMonth/getKpiDeptMonthScore.action',
			 onLoadSuccess : function(data) {				 
				onLoadSuccess(data);	
				
				var row = $('#dg_list').datagrid('getData').rows[0];				
				if(row){
					console.log(row.auditStatus);
					tooBar.menuStatus(row.auditStatus);
				}
				else{
					tooBar.menuStatus('1');
				}				
				
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
              $('#dg_list').datagrid('endEdit', editIndex);
              editIndex = undefined;
              return true;
 		} else {
 			return true;
 		}
      
     }
     function onClickCell(index, field){
    	 //alert('1111111')
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
	
	function endEdit(){
		var rows = $('#dg_list').datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			$('#dg_list').datagrid('endEdit', i);
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
	
	/**
 	 * 保存部门月度评价
 	 */
 	function saveDeptMonthKpi() { 		
		endEdit();
 		var $dg = $("#dg_list");
 		if ($dg.datagrid('getChanges').length) {		
 						
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
 			
 			$.post("../kpiMonth/saveDeptMonthKpi.action", effectRow, function(rsp) {
					if(rsp.code == "000"){
						$.messager.alert("提示", "提交成功！");						
						$('#dg_list').datagrid('reload'); // reload the user data
					}
				}, "JSON").error(function() {
					$.messager.alert("提示", "提交错误了！");
			});		
 		}
 		else{
 			$.messager.alert("提示", "无修改项！");
 		}
 	}
	
	/**
	 * 部门月度评价提交审批
	 */
	function saveDeptApprove() {
		var year = $("select[comboname=kpiYear]").combobox("getValue");
		var month = $("select[comboname=kpiMonth]").combobox("getValue");
		var orderId = "";
		var taskName = "";
		var taskId = "";
		
		if(wf.GetQueryString("orderId")){
			orderId = wf.GetQueryString("orderId");
		}
		if(wf.GetQueryString("taskName")){
			taskName = wf.GetQueryString("taskName");
		}
		if(wf.GetQueryString("taskId")){
			taskId = wf.GetQueryString("taskId");
		}		
		 
		$.messager.confirm('确认','提交审批之后,部门月度评价将不可编辑!',
			function(r) {
			if (r) {
				$.post('../kpiMonth/saveDeptApprove.action?status=5', {
					year : year,
					month : month,
					orderId:orderId,
					taskId:taskId,
					taskName:taskName
				}, function(result) {
					if (result.code == "000") {
						if(orderId != "" && taskId != ""){
							$.messager.alert("提示", "操作成功！");
							parent.refresh();
						}
						else{
							$.messager.alert("提示", "操作成功！");
							$('#dg_list').datagrid('reload');
						}	
					} else {						
						$.messager.alert("提示", result.errorMsg);
					}
				}, 'json');
			}
		 });
	}
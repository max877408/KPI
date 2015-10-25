/*
* toolBar 工具栏相关操作
*
*/

 var tooBar = {
		 menuStatus : function(status){
			 /*
			  * 工具栏菜单状态
			  * 1 任务未下发，可以编辑
			  * 2 任务已下发，只能查看
			  */
			 
			 if(status == '1'){
				 $("#toolbar").find("a").show();
				 $("#toolbar").find("[iconcls=icon-ok]").hide();
				 $("#dlg-buttons").find("[iconcls=icon-ok]").show();				
				 $("#dlg").find(".datagrid-toolbar").show();
				 
			 }
			 else if(status == '2' || status == '3'){
				 $("#toolbar").find("a").hide();
				 $("#toolbar").find("#view").show();
				 $("#dlg-buttons").find("[iconcls=icon-ok]").hide();				
				 $("#dlg").find(".datagrid-toolbar").hide();
			 }
			 else{
				 $("#toolbar").find("a").show();
				 $("#toolbar").find("[iconcls=icon-ok]").hide(); 
				 $("#dlg-buttons").find("[iconcls=icon-ok]").show();				
				 $("#dlg").find(".datagrid-toolbar").show();
			 }			
		 }
 }
 
	 /**
	  * 单元格格式
	  * @param val
	  * @param row
	  * @returns
	  */
	 function cellFormat(val,row){
			if (val != null && val.length > 18){
				//var str = val.substring(0,17) + "...";
				//return '<span title='+val+' style="color:red;">'+str+'</span>';
				return '<span title='+val+' style="color:red;">'+val+'</span>';
			} else {
				return val;
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
 
 $(function(){
	
 })
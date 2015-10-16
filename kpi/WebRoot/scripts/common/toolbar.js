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
				 $("#toolbar").find(".icon-tip").hide();
			 }
			 if(status == '2'){
				 $("#toolbar").find("a").hide();
				 $("#toolbar").find("#view").show();
				 $("#dlg-buttons").find("[iconcls=icon-ok]").hide();				
				 $("#dlg").find(".datagrid-toolbar").hide();
			 }
		 }
 }
 
 $(function(){
	
 })
/*
* toolBar 工具栏相关操作
*
*/

 var tooBar = {
		 show:function(){
			 $("#toolbar").find("a").show();
			 $("#toolbar").find("[iconcls=icon-ok]").hide();
			 $("#dlg-buttons").find("[iconcls=icon-ok]").show();				
			 $("#dlg").find(".datagrid-toolbar").show();
		 },
		 hide:function(){
			 $("#toolbar").find("a").hide();
			 $("#toolbar").find("#view").show();
			 $("#dlg-buttons").find("[iconcls=icon-ok]").hide();				
			 $("#dlg").find(".datagrid-toolbar").hide();
		 },
		 appraise:function(){
			 //判断当前页面是否评价页面
			 var result = false;
			 var url = window.location.href;
			 if(url.indexOf("Score") > 0 ){
				 result = true;
			 }
			 return result;
		 },
		 menuStatus : function(status){
			 /*
			  * 工具栏菜单状态
			  * 1 任务未下发，可以编辑
			  * 2 任务已下发，只能查看
			  * 3 工作流审批通过
			  * 5 评价工作流提交
			  * 6评价工作流审批通过
			  */
			 
			 if(status == '1'){
				this.show();		 
			 }
			 else if(status == '2'){
				this.hide();	
			 } 
			 else if(status == '3'){				 
				 //是否评价页面
				var result = this.appraise();
				if(result){
					this.show();
				}
				else{
					this.hide();
				}
			 }
			 else if(status == '5' || status == '6'){
					this.hide();	
			 } 
			 else{
				this.show();
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
	
	/**
	 * 单元格行高
	 */
	function cellHeigh(index){		
		/*setHeight();
		$(".datagrid-btable").eq(1).find("input[type=text]").click(function(){
			//setHeight(index);
		})*/
	}
	
	function setHeight(index){
		//获取当前选择的行
		var tr = $(".datagrid-btable").eq(1).find("tr").siblings().eq(index); 		
		tr.find("input[type=text]").height(tr.height()-9);
	}
 
 $(function(){
	
 })
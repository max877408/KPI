/*!
 * 页面搜索查找公交
 * 包含搜索添加初始化
 *
 */

$(function(){
	searchTool = {
			comYear:$("select[comboname=kpiYear]"),
			initData : function(){
				//年份初始化
				var options = [];
				for(year=2010;year <=2020;year++)
				{
					options.push([year,year]);
				}
				
				this.comYear.combobox({
					data:options,
					valueField:0,
					textField:1
				});
				
				var myDate = new Date();
				this.comYear.combobox("setValue",myDate.getFullYear());
				
				this.regEvent();				
			},
			searchData : function(keyTask,year){
				//alert(keyTask + year);
				
				var queryParams = $('#dg_list').datagrid('options').queryParams;  
		        queryParams.keyTask = keyTask;  
		        queryParams.year = year;  
		        
		        //重新加载datagrid的数据  
		        $('#dg_list').datagrid('reload');				
			},
			regEvent : function(){
				$("#btSearch").click(function(){
					var year = searchTool.comYear.combobox("getValue");
					var keyTask = $("#keyTask").val();
					searchTool.searchData(keyTask,year);
				})
			}
	}
	searchTool.initData();
})
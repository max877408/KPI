/*!
 * 页面搜索查找公交
 * 包含搜索添加初始化
 *
 */

$(function(){
	searchTool = {
			comYear:$("select[comboname=kpiYear]"),
			comMonth:$("select[comboname=kpiMonth]"),
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
				
				//月份初始化
				options = [];
				for(month=1;month <=12;month++)
				{
					options.push([month,month]);
				}
				
				this.comMonth.combobox({
					data:options,
					valueField:0,
					textField:1
				});
				
				var myDate = new Date();
				this.comMonth.combobox("setValue",myDate.getMonth() + 1);
				this.regEvent();				
			},
			searchData : function(keyTask,year,month){
				//alert(keyTask + year);
				
				var queryParams = $('#dg_list').datagrid('options').queryParams;  
		        queryParams.keyTask = keyTask;  
		        queryParams.year = year;  
		        queryParams.month = month;
		        
		        //重新加载datagrid的数据  
		        $('#dg_list').datagrid('reload');				
			},
			regEvent : function(){
				$("#btSearch").click(function(){
					var year = searchTool.comYear.combobox("getValue");
					var month = 0
					if(searchTool.comMonth.length != 0){
						month = searchTool.comMonth.combobox("getValue");
					}						
					var keyTask = $("#keyTask").val();
					searchTool.searchData(keyTask,year,month);
				})
			}
	}
	searchTool.initData();
})
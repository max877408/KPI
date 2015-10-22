<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
 <base href="<%=basePath%>">
<meta charset="UTF-8">
<title></title>

<link rel="stylesheet" href="themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="themes/color.css" type="text/css"></link>
<link rel="stylesheet" href="css/demo.css" type="text/css"></link>

<script type="text/javascript" src="scripts/jquery-1.6.min.js"></script>
<script type="text/javascript" src="scripts/jquery.easyui.min.js"></script>
<script type="text/javascript" src="scripts/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="scripts/common/workflow.js" ></script>
<script type="text/javascript" src="scripts/common/search-tool.js" ></script>
<script type="text/javascript" src="scripts/common/toolbar.js"></script>

</head>
<body>
<!-- 	<script> 		
	 	document.write("<script src='../scripts/ptarget/deptkpi.js?v="+Math.random()+"'><\/script>");				
	</script> -->
	
	<!-- 启动工作流引擎参数 -->
	<input type="hidden" name="processId" value="9b09d058e4a54af1bd7429396827f44c" />
	<input type="hidden" name="orderId" value="" />
	<input type="hidden" name="taskId" value="" />
	<input type="hidden" name="S_approveDept.operator" value="admin" />

	
	<table id="dg_list" title="部门年度计划申请" class="easyui-datagrid" url="../kpiYear/getKpiDeptList.action"
		 pagination="true" rownumbers="true" fitColumns="false" singleSelect="true">
		<thead data-options="frozen:true">
            <tr>
                <th field="id" hidden="true">id</th>
                <th field="groupId" hidden="true">groupId</th>  
                <th field="auditStatus" hidden="true">auditStatus</th>              
				<th field="keyTask" width="200px">关键任务</th>
				<th field="keyItem" width="250px">主要事项</th>
				<th field="detailItem" width="200px">具体事项</th>
            </tr>
        </thead>
		<thead>
			<tr>							
				<th field="startTime" width="100px">开始日期</th>
				<th field="endTime" width="100px">结束日期</th>
				
				<th field="weight" width="80px">权重</th>
				<th data-options="field:'content',width:250,formatter:cellFormat">指标内容及相关说明</th>	
				<th field="yearTarget" width="200px" data-options="formatter:cellFormat">年度目标</th>	
				<th field="standard" width="250px" data-options="formatter:cellFormat">衡量标准</th>	
				<th field="projectLevel" width="100px">项目等级</th>	
				<th field="difficulty" width="100px">难度系数</th>	
				<th field="responsible_person" width="100px">责任人</th>						
			</tr>
		</thead>
	</table>
	
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>员工年度计划流程</title>
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="${ctx}/styles/css/style.css" type="text/css" media="all" />
		<script src="${ctx}/styles/js/jquery-1.8.3.min.js" type="text/javascript"></script>
		<script src="${ctx}/styles/js/snaker/dialog.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$("input[name=submit]").click(function(){
									
					//获取Form 表单内容
					var formData = $('#inputForm').serializeArray();
					var parame = {};
					$.each(formData,function(){
						parame[this.name] = this.value;
					})
										
					//提交工作流
					$.post("${ctx }/snaker/flow/process", parame, function(rsp) {
						alert("提交成功！");
						history.back()
					}).error(function() {
						alert("提交错误了！");
					}); 
					
				})
			})
			
			/**
			* 工作流提交
			*/
			function submitWF(){
				//获取Form 表单内容
				var formData = $('#inputForm').serializeArray();
				var parame = {};
				$.each(formData,function(){
					parame[this.name] = this.value;
				})
									
				//提交工作流
				$.post("${ctx }/snaker/flow/process", parame, function(rsp) {
					alert("提交成功！");
					history.back()
				}).error(function() {
					alert("提交错误了！");
				});
			}
		</script>
	</head>

	<body>
		<form id="inputForm" action="${ctx }/snaker/flow/process" method="post" target="mainFrame">
			<input type="hidden" name="processId" value="${processId }" />
			<input type="hidden" name="orderId" value="${orderId }" />
			<input type="hidden" name="taskId" value="${taskId }" />		
			<input type="hidden" name="curNode" value="1" />
	
			<iframe src="../../pkpi/employeekpiWF.html?processId=${processId }&orderId=${orderId }&taskId=${taskId }&taskName=apply" frameborder="0" style="width:100%;height:100%"></iframe>
			
		</form>
	</body>
</html>

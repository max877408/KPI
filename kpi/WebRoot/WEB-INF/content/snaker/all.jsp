<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
	    <base href="<%=basePath%>">
		<%@ include file="/common/meta.jsp"%>
		<link rel="stylesheet" href="themes/default/easyui.css" type="text/css"></link>
		<link rel="stylesheet" href="themes/icon.css" type="text/css"></link>
		<link rel="stylesheet" href="themes/color.css" type="text/css"></link>
		<link rel="stylesheet" href="css/demo.css" type="text/css"></link>
		
		<script type="text/javascript" src="scripts/jquery-1.6.min.js"></script>
		<script type="text/javascript" src="scripts/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="scripts/easyui-lang-zh_CN.js"></script>
		
		<script type="text/javascript">
			$(function(){
				var taskName = "${task.taskName}";
				var url =  '${ctx}/snaker/process/diagram?processId=${processId}&orderId=${orderId}';
				addTab('流程图',url);	
				
				var selectNode = '流程图';
				 $.ajax({
					type:'GET',
					url:"${ctx}/snaker/flow/node",
					data:"processId=${processId}",
					async: false,
					globle:false,
					error: function(){
						alert('数据处理错误！');
						return false;
					},
					success: function(data) {
						data = eval("("+data+")");
						var curTab;
						var iscurrent = false;
						for(var i = 0; i < data.length; i++) {
							var node = data[i];
							var iframeUrl = '${ctx }' + node.form + '?processId=${processId}&orderId=${orderId}&taskName=' + node.name;
							if(taskName == node.name || (taskName == '' && i == 0)) {
								iscurrent = true;
								iframeUrl += '&taskId=${taskId}&readonly=1';
							} else {
								iscurrent = false;
								iframeUrl += '&readonly=0';
							}				            
				            addTab(node.displayName,iframeUrl);					            
				           
				            if(iscurrent) {
				            	$('#tt').tabs('select', node.displayName); 
				            	selectNode = node.displayName;
				            }
						}						
					}
				});	
				
				$('#tt').tabs({ 
					onSelect:function(title){ 
					  var p = $(this).tabs('getTab', title); 
					  var src = p.find('iframe').attr('src'); 
					  
					  refreshTab();
					  //addTab(title,src);
					} 
				});	
				
				$('#tt').tabs('select', selectNode);  				
			})
			
			function addTab(title, url){    
			    if ($('#tt').tabs('exists', title)){    
			        $('#tt').tabs('select', title);
			        refreshTab();    
			    } else {    
			        var content = '<iframe scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';    
			        $('#tt').tabs('add',{    
			            title:title,    
			            content:content,    
			            closable:false    
			        });    
			    }    
			}
			
			/**     
			 * 刷新tab 
			 * @cfg  
			 *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
			 *如果tabTitle为空，则默认刷新当前选中的tab 
			 *如果url为空，则默认以原来的url进行reload 
			 */  
			function refreshTab(){  
			   	var currTab = $('#tt').tabs('getSelected');
				var url = $(currTab.panel('options').content).attr('src');
				var content = '<iframe scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';    
				$('#tt').tabs('update', {
					tab : currTab,
					options : {
						content : content
					}
				});				
			}	
			
			function refresh(){
				window.location.href = '../snaker/task/active';
			}		
			 
		</script>
	
	</head>
	<body>
		<table border="0" width=100% align="center">
    		<tr>
        		<td align="center" class="snaker_title">${process.displayName }
        			<hr width=100% size=2 color="#71B2CF">
        		</td>
    		</tr>
		</table>
		<c:if test="${order != null }">
		<table border="0" width=98% align="center" style="margin-top:5;">
    		<tr>
        		<td align="left">
        			<font color="blue">编号：</font><font color="#800080">${order.orderNo }</font> &nbsp;
        			<font color="blue">派单时间：</font><font color="#800080">${order.createTime }</font>&nbsp;
				</td>
			</tr>
		</table>
		</c:if>	    
	    <div id="tt" class="easyui-tabs" style="padding: 10px;">
		
		</div>
		
	</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节点定时任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/timingstrategy/nodeCollectionCycle/">节点定时任务列表</a></li>
		<%--<li class="active"><a href="${ctx}/timingstrategy/nodeCollectionCycle/form?id=${nodeCollectionCycle.id}">节点定时任务<shiro:hasPermission name="timingstrategy:nodeCollectionCycle:edit">${not empty nodeCollectionCycle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="timingstrategy:nodeCollectionCycle:edit">查看</shiro:lacksPermission></a></li>--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="nodeCollectionCycle" action="${ctx}/timingstrategy/nodeCollectionCycle/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">节点id：</label>
			<div class="controls">
				<form:input path="nodeId" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">循环策略：</label>
			<div class="controls">
				<input name="cycleTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${nodeCollectionCycle.cycleTime}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关闭策略：</label>
			<div class="controls">
				<input name="cycleOff" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${nodeCollectionCycle.cycleOff}"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">开启策略：</label>
			<div class="controls">
				<input name="cycleOn" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${nodeCollectionCycle.cycleOn}"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">添加时间：</label>
			<div class="controls">
				<input name="addTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${nodeCollectionCycle.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">添加人：</label>
			<div class="controls">
				<form:input path="addUserId" htmlEscape="false" maxlength="16" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改时间：</label>
			<div class="controls">
				<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${nodeCollectionCycle.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改人：</label>
			<div class="controls">
				<form:input path="updateUserId" htmlEscape="false" maxlength="16" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="timingstrategy:nodeCollectionCycle:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
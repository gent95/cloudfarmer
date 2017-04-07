<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报警信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="waringMessage" action="${ctx}/message/waringMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>节点编号：</label>
				<form:input path="nodeNum" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div class="bodyDiv">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>节点编号</th>
				<th>报警信息</th>
				<th>添加时间</th>
				<shiro:hasPermission name="message:waringMessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="waringMessage">
			<tr>
				<td><a href="${ctx}/message/waringMessage/form?id=${waringMessage.id}">
					${waringMessage.id}
				</a></td>
				<td>
					${waringMessage.nodeNum}
				</td>
				<td>
					${waringMessage.message}
				</td>
				<td>
					<fmt:formatDate value="${waringMessage.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="message:waringMessage:edit"><td>
					<div class="two"><a href="${ctx}/message/waringMessage/form?id=${waringMessage.id}">修改</a></div>
					<div class="one"><a href="${ctx}/message/waringMessage/delete?id=${waringMessage.id}" onclick="return confirmx('确认要删除该报警信息吗？', this.href)">删除</a></div>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>
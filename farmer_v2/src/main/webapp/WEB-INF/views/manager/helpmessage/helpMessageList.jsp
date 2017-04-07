<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帮助信息管理</title>
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/helpmessage/helpMessage/">帮助信息列表</a></li>
		<shiro:hasPermission name="helpmessage:helpMessage:edit"><li><a href="${ctx}/helpmessage/helpMessage/form">帮助信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="helpMessage" action="${ctx}/helpmessage/helpMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>问题：</label>
				<form:input path="question" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>问题</th>
				<th>答案</th>
				<th>添加时间</th>
				<shiro:hasPermission name="helpmessage:helpMessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="helpMessage">
			<tr>
				<td><a href="${ctx}/helpmessage/helpMessage/form?id=${helpMessage.id}">
					${helpMessage.question}
				</a></td>
				<td>
					${helpMessage.answer}
				</td>
				<td>
					<fmt:formatDate value="${helpMessage.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="helpmessage:helpMessage:edit"><td>
    				<a href="${ctx}/helpmessage/helpMessage/form?id=${helpMessage.id}">修改</a>
					<a href="${ctx}/helpmessage/helpMessage/delete?id=${helpMessage.id}" onclick="return confirmx('确认要删除该帮助信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节点定时任务管理</title>
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
		<li class="active"><a href="${ctx}/timingstrategy/nodeCollectionCycle/">节点定时任务列表</a></li>
		<%--<shiro:hasPermission name="timingstrategy:nodeCollectionCycle:edit"><li><a href="${ctx}/timingstrategy/nodeCollectionCycle/form">节点定时任务添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="nodeCollectionCycle" action="${ctx}/timingstrategy/nodeCollectionCycle/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>节点id：</label>
				<form:input path="nodeId" htmlEscape="false" maxlength="255" class="input-medium"/>
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
			<th>节点id</th>
			<th>循环策略</th>
			<th>关闭策略</th>
			<th>开启策略</th>
			<th>添加时间</th>
			<th>添加人</th>
			<th>修改时间</th>
			<th>修改人</th>
			<shiro:hasPermission name="timingstrategy:nodeCollectionCycle:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="nodeCollectionCycle">
			<tr>
				<td><a href="${ctx}/timingstrategy/nodeCollectionCycle/form?id=${nodeCollectionCycle.id}">
						${nodeCollectionCycle.nodeId}
				</a></td>
				<td>
						${nodeCollectionCycle.cycleTime}
				</td>
				<td>
						${nodeCollectionCycle.cycleOff}
				</td>
				<td>
						${nodeCollectionCycle.cycleOn}
				</td>
				<td>
					<fmt:formatDate value="${nodeCollectionCycle.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${nodeCollectionCycle.addUserId}
				</td>
				<td>
					<fmt:formatDate value="${nodeCollectionCycle.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${nodeCollectionCycle.updateUserId}
				</td>
				<shiro:hasPermission name="timingstrategy:nodeCollectionCycle:edit"><td>
					<div class="two"><a href="${ctx}/timingstrategy/nodeCollectionCycle/form?id=${nodeCollectionCycle.id}">修改</a></div>
					<div class="one"><a href="${ctx}/timingstrategy/nodeCollectionCycle/delete?id=${nodeCollectionCycle.id}" onclick="return confirmx('确认要删除该节点定时任务吗？', this.href)">删除</a>
					</div>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>
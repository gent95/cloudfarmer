<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>作物管理</title>
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
		<li class="active"><a href="${ctx}/plant/plantVariety/">作物列表</a></li>
		<shiro:hasPermission name="plant:plantVariety:edit"><li><a href="${ctx}/plant/plantVariety/form">作物添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="plantVariety" action="${ctx}/plant/plantVariety/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>名称</th>
				<th>添加时间</th>
				<shiro:hasPermission name="plant:plantVariety:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="plantVariety">
			<tr>
				<td><a href="${ctx}/plant/plantVariety/form?id=${plantVariety.id}">
					${plantVariety.name}
				</a></td>
				<td>
					<fmt:formatDate value="${plantVariety.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="plant:plantVariety:edit"><td>
					<div class="two"><a href="${ctx}/plant/plantVariety/form?id=${plantVariety.id}">修改</a></div>
					<div class="one"><a href="${ctx}/plant/plantVariety/delete?id=${plantVariety.id}" onclick="return confirmx('确认要删除该作物吗？', this.href)">删除</a>
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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>农场管理</title>
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
		<li class="active"><a href="${ctx}/farmer/farmer/">农场列表</a></li>
		<shiro:hasPermission name="farmer:farmer:edit"><li><a href="${ctx}/farmer/farmer/form">农场添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="farmer" action="${ctx}/farmer/farmer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>农场编号：</label>
				<form:input path="farmerNum" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>农场名称：</label>
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

				<th>编号</th>
				<th>名称</th>
				<th>地址</th>
				<th>面积(亩)</th>
				<th>种植种类</th>
				<th>农场主</th>
				<th>农田数量</th>
				<th>中继数量</th>
				<shiro:hasPermission name="farmer:farmer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="farmer">
			<tr>
				<td>
					${farmer.farmerNum}
				</td>
				<td>
					${farmer.name}
				</td>
				<td>
						${farmer.addr}
				</td>
				<td>
					${farmer.area}
				</td>
				<td>
					${farmer.plantVariety}
				</td>
				<td>
					${farmer.user.name}
				</td>
				<td>
					${farmer.farmlandNumber}
				</td>
				<td>
						${farmer.relayNumber}
				</td>
				<shiro:hasPermission name="farmer:farmer:edit"><td>
					<div class="two"><a href="${ctx}/farmer/farmer/form?id=${farmer.id}">详情</a></div>
    			<%--	<a href="${ctx}/farmer/farmer/form?id=${farmer.id}">修改</a>--%>
					<div class="one"><a href="${ctx}/farmer/farmer/delete?id=${farmer.id}" onclick="return confirmx('确认要删除该农场吗？', this.href)">删除</a></div>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>
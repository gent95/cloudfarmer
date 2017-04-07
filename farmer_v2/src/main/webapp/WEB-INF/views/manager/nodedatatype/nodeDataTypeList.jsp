<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据类型管理</title>
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
		<li class="active"><a href="${ctx}/nodedatatype/nodeDataType/">数据类型列表</a></li>
		<shiro:hasPermission name="nodedatatype:nodeDataType:edit"><li><a href="${ctx}/nodedatatype/nodeDataType/form">数据类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="nodeDataType" action="${ctx}/nodedatatype/nodeDataType/" method="post" class="breadcrumb form-search">
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
				<th>是否使用</th>
				<th>是否删除</th>
				<th>添加时间</th>
				<th>添加人</th>
				<th>修改时间</th>
				<th>修改人</th>
				<shiro:hasPermission name="nodedatatype:nodeDataType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="nodeDataType">
			<tr>
				<td>
					${nodeDataType.name}
				</td>
				<td>
					${nodeDataType.useFlag}
				</td>
				<td>
					${nodeDataType.delFalg}
				</td>
				<td>
					<fmt:formatDate value="${nodeDataType.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${nodeDataType.addUserId}
				</td>
				<td>
					<fmt:formatDate value="${nodeDataType.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${nodeDataType.updateUserId}
				</td>
				<shiro:hasPermission name="nodedatatype:nodeDataType:edit"><td>
					<div class="two"><a href="${ctx}/nodedatatype/nodeDataType/form?id=${nodeDataType.id}">修改</a></div>
					<div class="one"><a href="${ctx}/nodedatatype/nodeDataType/delete?id=${nodeDataType.id}" onclick="return confirmx('确认要删除该数据类型吗？', this.href)">删除</a>
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
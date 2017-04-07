<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>农田管理</title>
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
		<li class="active"><a href="${ctx}/farmerland/farmland/">农田列表</a></li>
		<shiro:hasPermission name="farmerland:farmland:edit"><li><a href="${ctx}/farmerland/farmland/form">农田添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="farmland" action="${ctx}/farmerland/farmland/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="farmlandNum" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>种植种类：</label>
				<form:input path="plantVaritety" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>农户：</label>
			<%--	<sys:treeselect id="usedId" name="usedId" value="${farmland.user.id}" labelName="user.name" labelValue="${farmland.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>--%>
				<form:input path="usedName" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>所属农场：</label>
			<%--	<sys:treeselect id="farmer" name="farmer.id" value="${farmland.farmer.id}" labelName="farmer.name" labelValue="${farmland.farmer.name}"
								title="所属农场" url="/farmer/farmer/treeDate" cssClass="input-small" allowClear="true"/>--%>
				<form:input path="farmer.name" htmlEscape="false" maxlength="16" class="input-medium"/>
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
				<th>类型</th>
				<th>面积(亩)</th>
				<th>所属农场</th>
				<th>种植种类</th>
				<th>农户</th>
				<th>节点数量</th>
				<shiro:hasPermission name="farmerland:farmland:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="farmland">
			<tr>
				<td>
					${farmland.farmlandNum}
				</td>
				<td>
					${farmland.alias}
				</td>
				<td>
						<c:if test="${farmland.landType==1}">农田</c:if>
						<c:if test="${farmland.landType==0}">大棚</c:if>
				</td>
				<td>
						${farmland.area}
				</td>
				<td>
						${farmland.farmer.name}
				</td>
				<td>
						${farmland.plantVaritety}
				</td>
				<td>
						${farmland.usedName}
				</td>
				<td>
						${farmland.nodeNumber}
				</td>
				<shiro:hasPermission name="farmerland:farmland:edit"><td>
					<div class="two"><a href="${ctx}/farmerland/farmland/form?id=${farmland.id}">详情</a></div>
    			<%--	<a href="${ctx}/farmerland/farmland/form?id=${farmland.id}">修改</a>--%>
					<div class="one"><a href="${ctx}/farmerland/farmland/delete?id=${farmland.id}" onclick="return confirmx('确认要删除该农田(大棚)吗？', this.href)">删除</a></div>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>
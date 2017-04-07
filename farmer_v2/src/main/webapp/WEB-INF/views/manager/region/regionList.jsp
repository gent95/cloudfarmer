<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>地区管理</title>
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
		<li class="active"><a href="${ctx}/region/region/">地区列表</a></li>
		<shiro:hasPermission name="region:region:edit"><li><a href="${ctx}/region/region/form">地区添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="region" action="${ctx}/region/region/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>省市区名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>上级ID：</label>
				<form:input path="parentid" htmlEscape="false" maxlength="7" class="input-medium"/>
			</li>
			<li><label>简称：</label>
				<form:input path="shortname" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>级别:0,中国；1，省分；2，市；3，区、县：</label>
				<form:input path="leveltype" htmlEscape="false" maxlength="2" class="input-medium"/>
			</li>
			<li><label>城市代码：</label>
				<form:input path="citycode" htmlEscape="false" maxlength="7" class="input-medium"/>
			</li>
			<li><label>邮编：</label>
				<form:input path="zipcode" htmlEscape="false" maxlength="7" class="input-medium"/>
			</li>
			<li><label>经度：</label>
				<form:input path="lng" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>纬度：</label>
				<form:input path="lat" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>拼音：</label>
				<form:input path="pinyin" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>status：</label>
				<form:input path="status" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>省市区名称</th>
				<th>上级ID</th>
				<th>简称</th>
				<th>级别:0,中国；1，省分；2，市；3，区、县</th>
				<th>城市代码</th>
				<th>邮编</th>
				<th>经度</th>
				<th>纬度</th>
				<th>拼音</th>
				<th>status</th>
				<shiro:hasPermission name="region:region:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="region">
			<tr>
				<td>
					${region.name}
				</td>
				<td>
					${region.parentid}
				</td>
				<td>
					${region.shortname}
				</td>
				<td>
					${region.leveltype}
				</td>
				<td>
					${region.citycode}
				</td>
				<td>
					${region.zipcode}
				</td>
				<td>
					${region.lng}
				</td>
				<td>
					${region.lat}
				</td>
				<td>
					${region.pinyin}
				</td>
				<td>
					${region.status}
				</td>
				<shiro:hasPermission name="region:region:edit"><td>
					<div class="two"><a href="${ctx}/region/region/form?id=${region.id}">修改</a></div>
					<div class="one"><a href="${ctx}/region/region/delete?id=${region.id}" onclick="return confirmx('确认要删除该地区吗？', this.href)">删除</a>
					</div>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
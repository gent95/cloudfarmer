<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>农场管理</title>
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
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
	</script>
</head>
<body style="background-color: red;">
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/farmer/farmer/">农场列表</a></li>
		<li class="active"><a href="${ctx}/farmer/farmer/form?id=${farmer.id}">农场<shiro:hasPermission name="farmer:farmer:edit">${not empty farmer.id?'详情':'添加'}</shiro:hasPermission><shiro:lacksPermission name="farmer:farmer:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="farmer" action="${ctx}/farmer/farmer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">农场编号：</label>
			<div class="controls">
				<input type="text" name="farmerNum" htmlEscape="false" maxlength="16" class="input-xlarge"
						<c:if test="${farmer.id!=null&&farmer.id!=''}"> value="${farmer.farmerNum}" </c:if>/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">农场名称：</label>

			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(亩)：</label>
			<div class="controls">
				<form:input path="area" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">农场地址：</label>
			<div class="controls">
				<form:input path="addr" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">种植种类：</label>
			<div class="controls">
				<form:input path="plantVariety" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">管理人员：</label>
			<div class="controls">
				<sys:treeselect id="usedId" name="usedId" value="${farmer.usedName}" labelName="user.name" labelValue="${farmer.usedName}"
								title="管理人员" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">农场简介：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${farmer.id!=null&&farmer.id!=''}">
			<div class="control-group">
				<label class="control-label">下属农田：</label>
				<div class="controls">
				<table id="contentTables" class="table table-striped table-bordered table-condensed">
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
					<c:forEach items="${faPage.list}" var="farmland">
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
								<div class="one"><a href="${ctx}/farmerland/farmland/delete?id=${farmland.id}" onclick="return confirmx('确认要删除该农田(大棚)吗？', this.href)">删除</a></div>
							</td></shiro:hasPermission>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<%--<div class="pagination">${page}</div>--%>
			</div>
			</div>
		<div class="control-group">
		<label class="control-label">下属中继：</label>
			<div class="controls">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>编号</th>
					<th>位置</th>
					<th>管理者</th>
					<th>节点数量</th>
					<th>绑定时间</th>
					<th>电量</th>
					<shiro:hasPermission name="relay:relay:edit">
						<th>操作</th>
					</shiro:hasPermission>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="relay">
					<tr>
						<td><a href="${ctx}/relay/relay/form?id=${relay.id}">
								${relay.relayNum}
						</a></td>
						<td>
								${relay.area}
						</td>
						<td>
								${relay.user.name}
						</td>
						<td>
								${relay.nodeNum}
						</td>
						<td>
							<fmt:formatDate value="${relay.bindingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
								${relay.powerSupply}
						</td>
						<shiro:hasPermission name="relay:relay:edit">
							<td>
								<div class="two"><a href="${ctx}/relay/relay/form?id=${relay.id}">详情</a></div>
									<%-- <a href="${ctx}/relay/relay/form?id=${relay.id}">修改</a>--%>
								<div class="one"><a href="${ctx}/relay/relay/delete?id=${relay.id}"
													onclick="return confirmx('确认要删除该中继吗？', this.href)">删除</a></div>
							</td>
						</shiro:hasPermission>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<%--<div class="pagination">${page}</div>--%>
			</div>
		</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="farmer:farmer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
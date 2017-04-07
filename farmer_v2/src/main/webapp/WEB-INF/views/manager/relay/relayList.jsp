<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>中继管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/relay/relay/">中继列表</a></li>
    <shiro:hasPermission name="relay:relay:edit">
        <li><a href="${ctx}/relay/relay/form">中继添加</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="relay" action="${ctx}/relay/relay/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>中继编号：</label>
            <form:input path="relayNum" htmlEscape="false" maxlength="64" class="input-medium"/>
        </li>
        <li><label>所属农场：</label>
            <sys:treeselect id="farmer" name="farmer.id" value="${relay.farmer.id}" labelName="farmer.name" labelValue="${relay.farmer.name}"
                            title="所属农场" url="/farmer/farmer/treeDate" cssClass="" allowClear="true" notAllowSelectParent="true"/>
        </li>
        <li><label>地理位置：</label>
            <form:input path="area" htmlEscape="false" maxlength="255" class="input-medium"/>
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
            <td>
                    ${relay.relayNum}
            </td>
            <td>
                    ${relay.area}
            </td>
            <td>
                    ${relay.used.name}
            </td>
            <td>
                    ${relay.nodeNum}
            </td>
            <td>
                <fmt:formatDate value="${relay.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
<div class="pagination">${page}</div>
</div>
</body>
</html>
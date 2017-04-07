<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>异常策略管理</title>
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
    <li class="active"><a href="${ctx}/waring/waringCycle/">异常策略列表</a></li>
        <li><a href="${ctx}/waring/waringCycle/form">异常策略添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="waringCycle" action="${ctx}/waring/waringCycle/" method="post"
           class="breadcrumb form-search">
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
        <th>节点编号</th>
        <th>属性</th>
        <th>最大值</th>
        <th>最小值</th>
        <th>异常周期</th>
            <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${waringCycles}" var="waringCycle">
        <tr>
            <td><a href="${ctx}/waring/waringCycle/form?id=${waringCycle.id}">
                    ${waringCycle.nodeNum}
            </a></td>
            <td>
                <c:if test="${waringCycle.property == 'airTemperature'}">
                    大气温度
                </c:if>
                <c:if test="${waringCycle.property == 'airHumidity'}">
                    大气湿度
                </c:if>
                <c:if test="${waringCycle.property == 'soilTemperature1'}">
                    1号菌棒温度
                </c:if>
                <c:if test="${waringCycle.property == 'soilHumidity1'}">
                    1号菌棒湿度
                </c:if>
                <c:if test="${waringCycle.property == 'soilTemperature2'}">
                    2号菌棒温度
                </c:if>
                <c:if test="${waringCycle.property == 'soilHumidity2'}">
                    2号菌棒湿度
                </c:if>
                <c:if test="${waringCycle.property == 'soilTemperature3'}">
                    3号菌棒温度
                </c:if>
                <c:if test="${waringCycle.property == 'soilHumidity3'}">
                    3号菌棒湿度
                </c:if>
                <c:if test="${waringCycle.property == 'co2'}">
                    二氧化碳
                </c:if>
            </td>
            <td>
                    ${waringCycle.max}
            </td>
            <td>
                    ${waringCycle.min}
            </td>
            <td>
                    ${waringCycle.cycle}
            </td>
                <td>
                    <div class="two"><a href="${ctx}/waring/waringCycle/form?id=${waringCycle.id}">修改</a></div>
                    <div class="one"><a href="${ctx}/waring/waringCycle/delete?id=${waringCycle.id}"
                                        onclick="return confirmx('确认要删除该异常策略吗？', this.href)">删除</a></div>

                </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</div>
</body>
</html>
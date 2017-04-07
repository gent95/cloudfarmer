<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>节点管理</title>

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
        //查看数据
        function viewDate(num) {

            layer.open({
                title: "详细数据",
                type: 2,
                area: ['100%', '100%'],
                fixed: false, //不固定
                maxmin: true,
                content: "${ctx}/nodedatadetails/nodeDataDetails/list?nodeId=" + num
            });
        }
        //开关控制
        function strategyManagerment(str) {
            layer.open({
                title: "开关控制",
                type: 2,
                area: ['100%', '100%'],
                fixed: false,
                maxmin: true,
                content: "${ctx}/node/node/strategyManagerment?id=" + str
            })
        }
        //禁止使用
        function banUser(str,obj) {
           var userId=$(obj).next("#userId").val();
            layer.confirm('确定要禁止使用吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    url:"${ctx}/node/node/banUsed",
                    data:{
                        id:str,
                        usedId:userId
                    },
                    dataType:"json",
                    success:function (result) {
                        if(result.status==1){
                            layer.msg('更改成功');
                            location.reload();
                        }
                        else{
                            layer.msg('更改失败');
                        }
                    }
                });

            }, function(){

            });

        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/node/node/">节点列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="node" action="${ctx}/node/node/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>编号：</label>
            <form:input path="nodeNum" htmlEscape="false" maxlength="10" class="input-medium"/>
        </li>
        <li><label>农户：</label>
            <sys:treeselect id="user" name="user.id" value="${node.user.id}" labelName="user.name"
                            labelValue="${node.user.name}"
                            title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="true"/>
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
        <th>所属农田</th>
        <th>所属农户</th>
        <th>所属中继</th>
        <th>状态</th>
        <th>电量</th>

        <th>节点控制</th>

        <shiro:hasPermission name="node:node:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="node">
        <tr>
            <td>
                    ${node.nodeNum}
            </td>
            <td>
                    ${node.farmlandName}
            </td>
            <td>
                    ${node.usedName}
            </td>
            <td>
                    ${node.relayName}
            </td>
            <td>
                <c:if test="${node.openFlag == 1}">
                    已打开
                </c:if>
                <c:if test="${node.openFlag == 0 || node.openFlag == null }">
                    已关闭
                </c:if>
            </td>
            <td>
                100%
            </td>
            <td>
               <div class="abian"> <a class="btn" style="cursor:pointer" onclick="strategyManagerment(${node.id})">开关</a></div>
                <div class="abian"><a class="btn" style="cursor:pointer" onclick="viewDate(${node.id})">查看数据</a></div>
                <shiro:hasPermission name="node:node:edit">
                   <div class="abian"> <a class="btn" style="cursor:pointer" onclick="javascript:banUser(${node.id},this)">禁止使用</a></div>
                    <input type="hidden" value="${node.user.id}" id="userId"/>
                </shiro:hasPermission>
            </td>


            <shiro:hasPermission name="node:node:edit">
                <td>
                    <div class="two"><a href="${ctx}/node/node/form?id=${node.id}">详情</a></div>
         <%--           <a href="${ctx}/node/node/form?id=${node.id}">修改</a>--%>
                    <div class="one"><a href="${ctx}/node/node/delete?id=${node.id}" onclick="return confirmx('确认要删除该节点吗？', this.href)">删除</a>
                    </div>
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
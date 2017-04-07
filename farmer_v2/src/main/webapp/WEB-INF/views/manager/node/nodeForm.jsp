<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>节点管理</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">form{background-image: "${ctxStatic}/images/login/juxing-1.png";opacity:1;}</style>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    var id = $("#nodeId").val();
                    if (id != null && id != "") {
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
                    if (id == null || id == "") {
                        var nodeNum = $("[name='nodeNum']").val();
                        if (nodeNum == null || nodeNum == "") {
                            $("#messageBox").text("请输入节点编号");
                        }
                        else {
                            $.ajax({
                                url: "${ctx}/node/node/fetechNode",
                                data: {
                                    nodeNum: nodeNum
                                },
                                dataType: "json",
                                success: function (returnData) {
                                    if (returnData > 0) {
                                        $("#messageBox").text("已有该节点编号，请重新填写！！");
                                    } else {
                                        loading('正在提交，请稍等...');
                                        form.submit();
                                    }
                                }
                            })
                        }
                    }


                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            $("#farmlandId").change(function () {
             var fid=$(this).children('option:selected').val();
                if(fid!=-1){
                    $.ajax({
                        url:"${ctx}/farmerland/farmland/getFarmerById",
                        data:{
                            id:fid
                        },
                        dataType:"JSON",
                        success:function (result) {
                          $("#usednameS").html(result.info.usedName);
                        }
                    });
                }else{
                    $("#usednameS").html("");
                }
            });
        });
        function tefcetion() {
            var nodeNum = $("[name='nodeNum']").val();
            if (nodeNum == null || nodeNum == "") {
                $("#messageBox").text("请输入节点编号");
            }
            else {
                $.ajax({
                    url: "${ctx}/node/node/fetechNode",
                    data: {
                        nodeNum: nodeNum
                    },
                    dataType: "json",
                    success: function (returnData) {
                        if (returnData > 0) {
                            $("#messageBox").text("已有该节点编号，请重新填写！！");
                        }
                    }
                })
            }

        }
      

    </script>
</head>
<body style="background-image: url("${ctxStatic}/images/login/juxing-1.png")">
<ul class="nav nav-tabs" >
    <li><a href="${ctx}/node/node/">节点列表</a></li>
    <li class="active"><a href="${ctx}/node/node/form?id=${node.id}">节点<shiro:hasPermission
            name="node:node:edit">${not empty node.id?'详情':'查看'}</shiro:hasPermission><shiro:lacksPermission
            name="node:node:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="node" action="${ctx}/node/node/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="usedId"/>
    <form:hidden path="relayId"/>
    <form:hidden path="openFlag"/>
    <input type="hidden" value="${node.id}" id="nodeId"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">节点编号：</label>
        <div class="controls">
            <input type="text" name="nodeNum" htmlEscape="false" maxlength="10" class="input-xlarge " readonly="readonly" value="${node.nodeNum}"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">建立时间：</label>
        <div class="controls">
            <input name="addTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                   value="<fmt:formatDate value="${node.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                    <c:if test="${node.id==null||node.id==''}"> onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"</c:if>
            />
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">开关状态：</label>
        <div class="controls">
          <%--  <select name="openFlag">
                <option value="1" <c:if test="${node.openFlag == 1}">selected="selected"</c:if>>已打开</option>
                <option value="0" <c:if test="${node.openFlag == 0}">selected="selected"</c:if>>已关闭</option>
            </select>--%>
              <span style="font-size: 16px;">
            <c:if test="${node.openFlag == 1}"> 已打开</c:if>
            <c:if test="${node.openFlag == 0}">已关闭</c:if>
              </span>
        </div>
    </div>
        <div class="control-group">
            <label class="control-label">农户：</label>
            <div class="controls">
           <%--  <sys:treeselect id="userId" name="usedId" value="${node.usedId}" labelName="usedName"
                                labelValue="${node.usedName}"
                                title="农户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true"
                                notAllowSelectParent="true"/>--%>
                   <%--<input type="text"htmlEscape="false" maxlength="16" readonly="readonly" value="${node.usedName}"/>--%>
              <span id="usednameS" style="font-size: 16px;"> ${node.usedName}</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">所属中继：</label>
            <div class="controls">
                <input type="text" htmlEscape="false" maxlength="16" class="input-xlarge  digits" readonly="true" value="${node.relayName}"/>
            </div>
        </div>
    <c:if test="${node.relayName!=''&&node.relayName!=null&&relay.farmer!=null&&relay.farmer.id!=null&&relay.farmer.id!=''}">
        <div class="control-group">
            <label class="control-label">所属农田：</label>
            <div class="controls">
                    <select name="farmlandId" id="farmlandId">
                        <option value="-1">——请选择——</option>
                        <c:forEach items="${lists}" var="fa" >
                            <option value="${fa.id}" <c:if test="${fa.id == node.farmlandId}">selected="selected"</c:if> >${fa.alias}</option>
                        </c:forEach>
                    </select>
              </div>
        </div>
    </c:if>
    <div class="form-actions">
        <shiro:hasPermission name="node:node:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                          value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>
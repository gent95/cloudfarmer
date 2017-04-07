<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>异常策略管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
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
        });

        var reg=new RegExp("[0-9]+");
        function save() {

            var id=$("input[name='id']").val();
            var nodeNum=$("input[name='nodeNum']").val();
            var property=$("select[name='property']").val();
            var max=$("input[name='max']").val();
            if(max == null || max=='' || !reg.test(max) ){
                layer.msg("最大值不能为空，且只能输入纯数字！")
                return;
            }
            var min=$("input[name='min']").val();
            if(min == null || min=='' || !reg.test(min) ){
                layer.msg("最大值不能为空，且只能输入纯数字！")
                return;
            }
            var cycle=$("input[name='cycle']").val();
            if(min > max){
                layer.msg("！输入错误，最小值不能大于最大值");
                return;
            }

            $.ajax({
                type:"POST",
                url:"${ctx}/waring/waringCycle/add",
                dataType:"JSON",
                data:{
                    id:id,
                    nodeNum:nodeNum,
                    property:property,
                    max:max,
                    min:min,
                    cycle:cycle,
                },
                success:function(result){
                    if(result.status == 1){
                        layer.msg("！修改成功");
                        setTimeout("parent.location.reload()",1000);

                    }
                    if(result.status == 0){
                        layer.msg("！修改失败");
                    }
                }
            });


        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <%--<li><a href="${ctx}/waring/waringCycle/">异常策略列表</a></li>--%>
    <li class="active"><a href="${ctx}/waring/waringCycle/form?id=${waringCycle.id}">异常策略<shiro:hasPermission
            name="waring:waringCycle:edit">${not empty waringCycle.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="waring:waringCycle:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form modelAttribute="waringCycle" action="${ctx}/waring/waringCycle/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">节点编号：</label>
        <div class="controls">
            <form:select path="nodeNum" htmlEscape="false" maxlength="64" class="input-xlarge ">
                <c:forEach items="${nodes}" var="nodes">
                    <form:option value="${nodes.nodeNum}">${nodes.nodeNum}</form:option>
                </c:forEach>
            </form:select>

        </div>
    </div>
    <div class="control-group">
        <label class="control-label">属性：</label>
        <div class="controls">
            <form:select path="property" htmlEscape="false" maxlength="126" class="input-xlarge ">
                <form:option value="airTemperature">大气温度</form:option>
                <form:option value="airHumidity">大气湿度</form:option>
                <form:option value="soilTemperature1">1号菌棒温度</form:option>
                <form:option value="soilHumidity1">1号菌棒湿度</form:option>
                <form:option value="soilTemperature2">2号菌棒温度</form:option>
                <form:option value="soilHumidity2">2号菌棒湿度</form:option>
                <form:option value="soilTemperature3">3号菌棒温度</form:option>
                <form:option value="soilHumidity3">3号菌棒湿度</form:option>
                <form:option value="co2">二氧化碳浓度</form:option>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">最大值：</label>
        <div class="controls">
            <form:input path="max" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">最小值：</label>
        <div class="controls">
            <form:input path="min" htmlEscape="false" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">异常周期：</label>
        <div class="controls">
            <form:input path="cycle" htmlEscape="false" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" onclick="save()" type="button"
                                                                   value="保 存"/>&nbsp;
        <%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
    </div>
</form:form>
</body>
</html>
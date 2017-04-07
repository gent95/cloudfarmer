<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>开关控制</title>
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
        //添加方法
        function addWaringCycle() {
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
                    nodeNum:nodeNum,
                    property:property,
                    max:max,
                    min:min,
                    cycle:cycle,
                },
                success:function(result){
                    if(result.status == 1){
                        layer.msg("！添加成功");
//                        $("#waringCycle").find("input").val("")
                        $("input[name='max']").val('');
                        $("input[name='min']").val('');
                        $("input[name='cycle']").val('');
                        setTimeout("location.reload()",1000);

                    }
                    if(result.status == 0){
                        layer.msg("！添加失败");
                    }
                }
            });


        }
        //删除方法
        function deleteWaringCycle(id) {

            $.ajax({
                type:"POST",
                url:"${ctx}/waring/waringCycle/del",
                dataType:"JSON",
                data:{
                    id:id,
                },
                success:function(result){
                    if(result.status == 1){
                        layer.msg("！删除成功");
                        setTimeout("location.reload()",1000);
                    }
                    if(result.status == 0){
                        layer.msg("！删除失败");
                    }
                }
            });
        }
        <%--href="${ctx}/waring/waringCycle/form?id=${waringCycle.id}"--%>
        function modify(id) {
            layer.open({
                title: "详细数据",
                type: 2,
                area: ['800px', '600px'],
                fixed: false, //不固定
                maxmin: true,
                content: "${ctx}/waring/waringCycle/form?id="+id
            });

        }
    </script>
</head>
<body>
<ul id="myTab" class="nav nav-tabs">
    <li class="active">
        <a href="#manual" data-toggle="tab">
            手动
        </a>
    </li>
    <li>
        <a href="#cycl" data-toggle="tab">
            周期
        </a>
    </li>
    <li>
        <a href="#auto" data-toggle="tab">
            智能
        </a>
    </li>
</ul>

<div id="tab" class="tab-content">
    <div class="tab-pane fade in active" id="manual">
        <form:form id="manualform" modelAttribute="nodeCollectionCycle" action="${ctx}/node/node/manual?id=+${node.id}" method="post"
                   class="form-horizontal">
            <sys:message content="${message}"/>
            <div class="control-group">
                <label class="control-label">节点id：</label>
                <div class="controls">
                    <input name="nodeId" type="text" maxlength="20" readonly="readonly" class="input-xlarge"
                           value="${node.nodeNum}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">开启：<input type="radio" name="task" value="1"/></label>
                <label class="control-label">关闭：<input type="radio" name="task" value="0" checked="checked"/></label>
            </div>
            <div class="control-group">
                <label class="control-label">状态：
                    <c:if test="${node.openFlag eq '0'}">
                        已关闭
                    </c:if>
                    <c:if test="${node.openFlag eq '1'}">
                        已开启
                    </c:if>
                </label>
            </div>
            <div class="control-group">
                <label class="control-label"><input type="submit" class="btn" value="立即执行"></label>
            </div>
        </form:form>
    </div>

    <div class="tab-pane fade in" id="cycl">
        <form:form id="inputForm" modelAttribute="nodeCollectionCycle"
                   action="${ctx}/timingstrategy/nodeCollectionCycle/save" method="post" class="form-horizontal">
            <%--<form:hidden path="id"/>--%>
            <sys:message content="${message}"/>
            <div class="control-group">
                <label class="control-label">节点id：</label>
                <div class="controls">
                    <input name="nodeId" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                           value="${node.nodeNum}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">采集周期：</label>
                <div class="controls">
                    <%--<input name="cycleTime" type="text" maxlength="20" class="input-medium Wdate " readonly="readonly"--%>
                           <%--value="${nodeCollectionCycle.cycleTime}" onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>--%>
                        <ul class="ul-form" type="none">
                            <li>
                                <select id="cycleTime" name="cycleTime">
                                    <option name="cycleTime" value="5">5分钟</option>
                                    <option name="cycleTime" value="10">10分钟</option>
                                    <option name="cycleTime" value="30">30分钟</option>
                                    <option name="cycleTime" value="60">60分钟</option>
                                </select>
                            </li>
                        </ul>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">关闭策略：</label>
                <div class="controls">
                    <input name="cycleOff" type="text" maxlength="20" class="input-medium Wdate " readonly="readonly"
                           value="${nodeCollectionCycle.cycleOff}" onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">关闭重复：</label>
                <div class="controls">
                    <input type="checkbox" name="off" value="1"> 星期日
                    <input type="checkbox" checked="checked" name="off" value="2"> 星期一
                    <input type="checkbox" name="off" value="3"> 星期二
                    <input type="checkbox" name="off" value="4"> 星期三
                    <input type="checkbox" name="off" value="5"> 星期五
                    <input type="checkbox" name="off" value="6"> 星期六
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">开启策略：</label>
                <div class="controls">
                    <input name="cycleOn" type="text" maxlength="20" class="input-medium Wdate " readonly="readonly"
                           value="${nodeCollectionCycle.cycleOn}" onclick="WdatePicker({dateFmt:'HH:mm:ss',isShowClear:false});"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">开启重复：</label>
                <div class="controls">
                    <input type="checkbox" name="on" value="1"> 星期日
                    <input type="checkbox" checked="checked" name="on" value="2"> 星期一
                    <input type="checkbox" name="on" value="3"> 星期二
                    <input type="checkbox" name="on" value="4"> 星期三
                    <input type="checkbox" name="on" value="5"> 星期五
                    <input type="checkbox" name="on" value="6"> 星期六
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">添加时间：</label>
                <div class="controls">
                    <input name="addTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                           value="<fmt:formatDate value="${nodeCollectionCycle.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">修改时间：</label>
                <div class="controls">
                    <input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                           value="<fmt:formatDate value="${nodeCollectionCycle.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                </div>
            </div>
            <div class="form-actions">
                <shiro:hasPermission name="timingstrategy:nodeCollectionCycle:edit"><input id="btnSubmit"
                                                                                           class="btn btn-primary"
                                                                                           type="submit"
                                                                                           value="保 存"/>&nbsp;</shiro:hasPermission>
                <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
            </div>
        </form:form></div>

    <div class="tab-pane fade in" id="auto">
        <%--添加--%>
        <form modelAttribute="waringCycle"  id="waringCycle" class="form-horizontal">

            <div class="control-group">
                <label class="control-label">节点编号</label>
                <div class="controls">
                    <input name="nodeNum" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
                           value="${node.nodeNum}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">属性：</label>
                <div class="controls">
                    <select name="property" style="width: 180px">
                        <option value="airTemperature">大气温度</option>
                        <option value="airHumidity">大气湿度</option>
                        <option value="soilTemperature1">1号菌棒温度</option>
                        <option value="soilHumidity1">1号菌棒湿度</option>
                        <option value="soilTemperature2">2号菌棒温度</option>
                        <option value="soilHumidity2">2号菌棒湿度</option>
                        <option value="soilTemperature3">3号菌棒温度</option>
                        <option value="soilHumidity3">3号菌棒湿度</option>
                        <option value="co2">二氧化碳浓度</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">最大值：</label>
                <div class="controls">
                    <input type="text" name="max" htmlEscape="false" class="input-xlarge "/>

                </div>
            </div>
            <div class="control-group">
                <label class="control-label">最小值：</label>
                <div class="controls">
                    <input type="text"  name="min" htmlEscape="false" class="input-xlarge "/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">异常周期：</label>
                <div class="controls">
                    <input name="cycle" type="text"    placeholder="请输入分钟数" htmlEscape="false" maxlength="255" class="input-xlarge "/>
                </div>
            </div>
            <div class="form-actions">
                <input  onclick="addWaringCycle()" class="btn btn-primary" type="button"  value="添 加"/>&nbsp;
            </div>
        </form>

        <%--列表--%>
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

                        <div class="two"> <a onclick="modify(${waringCycle.id})" >修改</a></div>
                        <div class="one"><a  onclick="deleteWaringCycle(${waringCycle.id})">删除</a></div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

</div>
</body>
</html>
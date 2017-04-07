<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节点数据详情管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/sys/user/export");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function ExportExcel(){
            top.$.jBox.confirm("确认要节点详情数据吗？","系统提示",function(v,h,f){
                if(v=="ok"){
                    $("#searchForm").attr("action","${ctx}/nodedatadetails/nodeDataDetails/export?nodeMac=${nodeMac}");
                    $("#searchForm").submit();
                }
            },{buttonsFocus:1});
            top.$('.jbox-body .jbox-icon').css('top','55px');
		}
        //获取节点信息
        function refreshNodeInfo(str) {
            $.ajax({
                url: "${ctx}/node/node/refreshNodeInfo",
                data: {
                    id: str
                },
                dataType: "json",
                success: function (result) {
                    if (result.status == 1) {
                        location.reload();
                    } else {
                        layer.msg("设备掉线或未连接！")
                    }
                }
            });
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/nodedatadetails/nodeDataDetails/list?nodeId=${nodeId}">详细信息</a></li>
		<li><a href="${ctx}/nodedatadetails/nodeDataDetails/form?nodeId=${nodeId}">图表数据</a></li>
	</ul>
	<div style="color: white; font-size: 16px; font-weight: bold">&nbsp;&nbsp;&nbsp;设备：${nodeMac}</div>
	<form:form id="searchForm" modelAttribute="nodeDataDetails" action="${ctx}/nodedatadetails/nodeDataDetails/list?nodeId=${nodeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>起始时间：</label>
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${node.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>结束时间：</label>
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${node.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="导出数据" onclick="ExportExcel()"/></li>
			<li class="btns"><input class="btn btn-primary" type="button" value="获取节点信息" onclick="refreshNodeInfo(${nodeId})"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div class="bodyDiv">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>采集时间</th>
				<th>大气温度(℃)</th>
				<th>大气湿度(%RH)</th>
				<th>1号菌棒温度(℃)</th>
				<th>1号菌棒湿度(%RH)</th>
				<th>2号菌棒温度(℃)</th>
				<th>2号菌棒湿度(%RH)</th>
				<th>3号菌棒温度(℃)</th>
				<th>3号菌棒湿度(%RH)</th>
				<th>二氧化碳浓度(ppm)</th>
				<th>开关状态</th>
				<th>电量</th>
				<shiro:hasPermission name="nodedatadetails:nodeDataDetails:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="nodeDataDetails">
			<tr>
				<td>
					<fmt:formatDate value="${nodeDataDetails.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${nodeDataDetails.airTemperature}
				</td>
				<td>
					${nodeDataDetails.airHumidity}
				</td>
				<td>
					${nodeDataDetails.soilTemperature1}
				</td>
				<td>
					${nodeDataDetails.soilHumidity1}
				</td>
				<td>
					${nodeDataDetails.soilTemperature2}
				</td>
				<td>
					${nodeDataDetails.soilHumidity2}
				</td>
				<td>
					${nodeDataDetails.soilTemperature3}
				</td>
				<td>
					${nodeDataDetails.soilHumidity3}
				</td>
				<td>
					${nodeDataDetails.co2}
				</td>
				<td>
					<c:if test="${nodeDataDetails.openFlag==0}">关</c:if>
					<c:if test="${nodeDataDetails.openFlag==1}">开</c:if>
				</td>
				<td>
					${nodeDataDetails.power}%
				</td>

				<shiro:hasPermission name="nodedatadetails:nodeDataDetails:edit"><td>
					<div class="two"><a href="${ctx}/nodedatadetails/nodeDataDetails/delete?id=${nodeDataDetails.id}&nodeId=${nodeId}" onclick="return confirmx('确认要删除该节点数据详情吗？', this.href)">删除</a>
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
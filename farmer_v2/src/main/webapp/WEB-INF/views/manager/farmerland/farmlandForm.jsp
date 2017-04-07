<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>农田管理</title>
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
                        layer.msg("成功获取最新信息！")
                    } else {
                        layer.msg("设备掉线或未连接！")
                    }
                }
            });
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
        //策略管理
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
		<li><a href="${ctx}/farmerland/farmland/">农田列表</a></li>
		<li class="active"><a href="${ctx}/farmerland/farmland/form?id=${farmland.id}">农田<shiro:hasPermission name="farmerland:farmland:edit">${not empty farmland.id?'详情':'添加'}</shiro:hasPermission><shiro:lacksPermission name="farmerland:farmland:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="farmland" action="${ctx}/farmerland/farmland/save" method="post" class="form-horizontal">

		<form:hidden path="id"/>

		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">编号：</label>
			<div class="controls">
				<input type="text" name="farmlandNum" htmlEscape="false" maxlength="10" class="input-xlarge"
						<c:if test="${farmland.id!=null&&farmland.id!=''}"> readonly="readonly" value="${farmland.farmlandNum}" </c:if>
				/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<input type="text" name="alias" htmlEscape="false" maxlength="255" class="input-xlarge "
						<c:if test="${farmland.id!=null&&farmland.id!=''}"> value="${farmland.alias}" </c:if>
				/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<select name="landType">
					<option value="-1">——请选择——</option>
					<option value="0"
							<c:if test="${farmland.id!=null&&farmland.id!=''}">
								<c:if test="${farmland.landType==0}">selected="selected"</c:if>
							</c:if>
					>大棚</option>
					<option value="1"
							<c:if test="${farmland.id!=null&&farmland.id!=''}">
								<c:if test="${farmland.landType==1}">selected="selected"</c:if>
							</c:if>
					>农田</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地理位置：</label>
			<div class="controls">
				<input type="text" name="addr" htmlEscape="false" maxlength="64" class="input-xlarge "
						<c:if test="${farmland.id!=null&&farmland.id!=''}"> value="${farmland.addr}" </c:if>
				/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(亩)：</label>
			<div class="controls">
				<input type="text" name="area" htmlEscape="false" maxlength="64" class="input-xlarge "
						<c:if test="${farmland.id!=null&&farmland.id!=''}"> value="${farmland.area}" </c:if>
				/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">种植种类：</label>
			<div class="controls">
				<form:input path="plantVaritety" htmlEscape="false" class="input-xlarge "/>
		</div>
		</div>
		<div class="control-group">
			<label class="control-label">简介：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<c:if test="${farmland.id!=null&&farmland.id!=''}">
		<div class="control-group">
			<label class="control-label">所属农户：</label>
			<div class="controls">
				<sys:treeselect id="usedId" name="usedId" value="${farmland.usedId}" labelName="user.name" labelValue="${farmland.usedName}"
								title="所属农户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">分配时间：</label>
			<div class="controls">
				<input name="assigneTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${farmland.assigneTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属农场：</label>
			<div class="controls">
				<sys:treeselect id="farmer" name="farmer.id" value="${farmland.farmer.id}" labelName="farmer.name" labelValue="${farmland.farmer.name}"
								title="所属农场" url="/farmer/farmer/treeDate" cssClass="input-small" allowClear="true"/>
				<%--<input type="text" htmlEscape="false" maxlength="64" class="input-xlarge " value="${farmland.farmer.name}"/>--%>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">所属中继：</label>
			<div class="controls">
			<%--	<sys:treeselect id="relay" name="relay.id" value="${farmland.relay.id}" labelName="relay.name" labelValue="${farmland.relay.relayNum}"
								title="所属中继" url="/relay/relay/treeDate" cssClass="" allowClear="true" notAllowSelectParent="true"/>--%>
				<input type="text" htmlEscape="false" maxlength="64" class="input-xlarge " value="${farmland.relay.relayNum}" readonly="readonly"/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">建立时间：</label>
				<div class="controls">

					<input name="addTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						   value="<fmt:formatDate value="${farmland.addTime}" pattern="yyyy-MM-dd"/>"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">下属节点：</label>
				<div class="controls">
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
							<td><a href="${ctx}/node/node/form?id=${node.id}">
									${node.nodeNum}
							</a></td>
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
								<div class="abian"> <a class="btn" onclick="strategyManagerment(${node.id})">开关</a></div>
								<div class="abian"> <a class="btn" onclick="viewDate(${node.id})">查看数据</a></div>
								<shiro:hasPermission name="node:node:edit">
									<div class="abian"> <a class="btn" onclick="javascript:banUser(${node.id},this)">禁止使用</a></div>
									<input type="hidden" value="${node.user.id}" id="userId"/>
								</shiro:hasPermission>
							</td>


							<shiro:hasPermission name="node:node:edit">
								<td>
									<div class="two"><a href="${ctx}/node/node/form?id=${node.id}">详情</a></div>
										<%--           <a href="${ctx}/node/node/form?id=${node.id}">修改</a>--%>
									<a href="${ctx}/node/node/delete?id=${node.id}" onclick="return confirmx('确认要删除该节点吗？', this.href)">删除</a>

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
			<shiro:hasPermission name="farmerland:farmland:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
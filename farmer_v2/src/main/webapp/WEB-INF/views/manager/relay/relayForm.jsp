<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>中继管理</title>
	<meta name="decorator" content="default"/>
	<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
				    var id=$("#relayId").val();

			       if(id==null||id==""){
                       var num=$("[name='relayNum']").val();
                       if(num==null||num==""){
                           $("#messageBox").text("请填写节点编号！！");
					   }else{
                       $.ajax({
                           url:"${ctx}/relay/relay/detectionNum",
                           data:{
                               relayNum:num
                           },
                           dataType:"json",
                           success:function (returnData) {
                               if(returnData>0){
                                   $("#messageBox").text("已有该中继，请重新填写！！");
                                   return false;
                               }else{
                                   loading('正在提交，请稍等...');
                                   form.submit();
                               }
                           }
                       });
					   }
                   }
                   if(id!=null&&id!=""){
                       loading('正在提交，请稍等...');
                       form.submit();
                   }

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
		function  detectionNum() {
           var num=$("[name='relayNum']").val();
           if(num==null||num==""){
               $("#messageBox").text("请填写节点编号！！");
		   }
				$.ajax({
				    url:"${ctx}/relay/relay/detectionNum",
					data:{
				        relayNum:num
				    },
					dataType:"json",
					success:function (returnData) {
				        if(returnData>0){
                            $("#messageBox").text("已有该中继，请重新填写！！");
                        }
                    }
				})
        }
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
		<li><a href="${ctx}/relay/relay/">中继列表</a></li>
		<li class="active"><a href="${ctx}/relay/relay/form?id=${relay.id}">中继<shiro:hasPermission name="relay:relay:edit">${not empty relay.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="relay:relay:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="relay" action="${ctx}/relay/relay/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="relayId" value="${relay.id}">
		<sys:message content="${message}"/>
			<div class="control-group">
				<label class="control-label">中继编号：</label>
				<div class="controls">
					<input type="text" name="relayNum" htmlEscape="false" maxlength="64" class="input-xlarge"
							<c:if test="${relay.id==null||relay.id==''}">onblur="detectionNum()" </c:if>
							<c:if test="${relay.id!=null&&relay.id!=''}"> value="${relay.relayNum}" </c:if>
					/>
				</div>
			</div>
		<c:if test="${relay.id!=null&&relay.id!=''}">
			<div class="control-group">
				<label class="control-label">所属农场：</label>
				<div class="controls">
					<select name="farmerId">
						<option value="-1">——请选择——</option>
						<c:forEach items="${famers}" var="f">
							<option value="${f.id}"  <c:if test="${f.id==relay.farmer.id}"> selected="selected"</c:if>>${f.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</c:if>
			<div class="control-group">
				<label class="control-label">经度：</label>
				<div class="controls">
					<input type="text" id="lng" name="log" htmlEscape="false" class="input-xlarge "
						   <c:if test="${relay.id!=null&&relay.id!=''}">  value="${relay.log}"</c:if> />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">纬度：</label>
				<div class="controls">
					<input type="text" id="lat" name="lat" htmlEscape="false" class="input-xlarge "
						   <c:if test="${relay.id!=null&&relay.id!=''}"> value="${relay.lat}"</c:if>
					/>
				</div>
			</div>
		<div class="control-group">
			<div style="width:700px;height:340px;border:1px solid gray" id="container"></div>
		</div>

			<div class="control-group">
				<label class="control-label">地理位置：</label>
				<div class="controls">
					<input type="text" name="area" htmlEscape="false" maxlength="255" class="input-xlarge "
		<c:if test="${relay.id!=null&&relay.id!=''}">value="${relay.area}"</c:if>
			/>
				</div>
			</div>
		<c:if test="${relay.id!=null&&relay.id!=''}">
			<div class="control-group">
				<label class="control-label">绑定时间：</label>
				<div class="controls">
					<input name="bindingTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						   value="<fmt:formatDate value="${relay.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"
					/>
				</div>
			</div>
		</c:if>
		<c:if test="${relay.id!=null&&relay.id!=''}">
			<div class="control-group">
				<label class="control-label">电量：</label>
				<div class="controls">
					<input type="text" name="powerSupply" htmlEscape="false" maxlength="12" class="input-xlarge " readonly="readonly" value="${relay.powerSupply}"/>
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
									<div class="abian"> 	<a class="btn" onclick="strategyManagerment(${node.id})">开关</a></div>
									<div class="abian"> <a class="btn" onclick="viewDate(${node.id})">查看数据</a></div>
									<shiro:hasPermission name="node:node:edit">
										<div class="abian"> 	<a class="btn" onclick="javascript:banUser(${node.id},this)">禁止使用</a></div>
										<input type="hidden" value="${node.user.id}" id="userId"/>
									</shiro:hasPermission>
								</td>


								<shiro:hasPermission name="node:node:edit">
									<td>
										<div class="two"><a href="${ctx}/node/node/form?id=${node.id}">详情</a></div>
											<%--           <a href="${ctx}/node/node/form?id=${node.id}">修改</a>--%>
										<div class="one"><a href="${ctx}/node/node/delete?id=${node.id}" onclick="return confirmx('确认要删除该节点吗？', this.href)">删除</a></div>

									</td>
								</shiro:hasPermission>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				<%--	<div class="pagination">${page}</div>--%>
				</div>
			</div>

		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="relay:relay:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

	<script type="text/javascript">
        //var lat=window.parent.document.getElementById("lat").value;
        //var lng=window.parent.document.getElementById("lng").value;
        var lat;
        var lng;
        var lats=document.getElementById("lat").value;
        var lngs=document.getElementById("lng").value;
       if(lats!=null||lats!=""){
           lat=lats;
	   }
	   if(lngs!=null||lngs!=""){
           lng=lngs;
	   }
      if(lats==null||lats==""){
	     lat=39.91;
	  }
	  if(lngs==null||lngs==""){
          lng=116.40;
	  }
        //在指定的容器内创建地图实例
        var map = new BMap.Map("container");

        map.enableScrollWheelZoom();//启用滚轮放大缩小，默认禁用。
        var point = new BMap.Point(lng, lat); //默认中心点
        var marker = new BMap.Marker(point);
        var opts = {
            width: 40,  // 信息窗口宽度
            height:80,  // 信息窗口高度
            title: "地址" // 信息窗口标题
        }
        var infoWindow = new BMap.InfoWindow("当前位置<br/>经度:"+lng+"<br/>纬度："+lat, opts); // 创建信息窗口对象
        marker.enableDragging(); //启用拖拽
        marker.addEventListener("dragend", function (e) {
            point = new BMap.Point(e.point.lng, e.point.lat); //标记坐标（拖拽以后的坐标）
            infoWindow = new BMap.InfoWindow("当前位置<br />经度：" + e.point.lng + "<br />纬度：" + e.point.lat, opts);
            marker = new BMap.Marker(point);
            map.openInfoWindow(infoWindow, point);
        })
        map.addControl(new BMap.NavigationControl()); //左上角控件
        map.enableScrollWheelZoom(); //滚动放大
        map.enableKeyboard(); //键盘放大
        map.centerAndZoom(point, 13); //绘制地图
        map.addOverlay(marker); //标记地图
        map.openInfoWindow(infoWindow, map.getCenter());  // 打开信息窗口
	</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>注册</title>
		<link rel="stylesheet" href="${ctxStatic}/css/login.css" />
		<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.js" ></script>
		<script type="text/javascript" src="${ctxStatic}/layer/layer.js" ></script>
		<script type="text/javascript">
            $(document).ready(function() {
                reg = /^1[0-9]{10}$/;
                $(".gain").click(function () {
                    phone=$("[name='loginName']").val();
                    if(phone==null||phone==""){
                        layer.msg("请填写账号(手机号)");
					}
                    if(phone!=null&&phone!=""){
                        if(reg.test(phone)==false){
                            layer.msg("手机号输入错误");
						}else{
                        $.ajax({
                            url:"${ctx}/reg/checkUserName",
                            data:{
                                name:phone
                            },
                            dataType:"JSON",
                            success:function (result) {
                                if(result.status==0){
                                    layer.msg("该手机号已经注册过，请登录！");
                                }
                                if(result.status==1){
                                    layer.msg("验证码已发送");
                                /*    $.ajax({
                                        url:"{ctx}/reg/validateMsg",
                                        data:{
                                            name:phone
                                        },
                                        dataType:"JSON",
                                        success:function (result) {
                                            code=result.msgCode;
                                        }
                                    });*/
									code=123;
                                }
                            }
                        });
                        }
                    }
                });
                $(".regis").click(function () {
                    var flag=true;
                    var name=$("[name='name']").val();
                    if(name==""||name==null){
                        layer.msg("请填写姓名");
                        flag=false;
                    }
                    var officeName=$(".officeName").val();
                    if(officeName==-1){
                        layer.msg("请选择所属公司");
                        flag=false;
					}
                    var loginName=$("[name='loginName']").val();
                    if(loginName==""||loginName==null){
                        layer.msg("请填写账号(手机号)");
                        flag=false;
                    }
                    if(loginName!=""&&loginName!=null){
                        if(reg.test(loginName)){
                        $.ajax({
                            url:"${ctx}/reg/checkUserName",
                            data:{
                                name:loginName
                            },
                            dataType:"JSON",
                            success:function (result) {
                                if(result.status==0){
                                    layer.msg("该手机号已经注册过，请登录！");
                                    flag=false;
                                }
                            }
                        });
						}else{
                            layer.msg("手机号输入错误");
                        }
                    }
                    var newPassword=$("[name='newPassword']").val();
                    if(newPassword==""||newPassword==null){
                        layer.msg("请填写密码");
                        flag=false;
                    }
                    var confirmNewPassword=$("[name='password']").val();
                    if(confirmNewPassword==""||confirmNewPassword==null){
                        layer.msg("请填写确认密码");
                        return false;
                    }
                    if(confirmNewPassword!=newPassword){
                        layer.msg("确认密码与密码不一致，请重新输入");
                        flag=false;
                    }
                    var codeMsg=$(".inputs1").val();
                    if(codeMsg!=code){
                        layer.msg("验证码输入错误！");
                        flag=false;
                    }

                    if(flag==true){
                        var eName=$("#eName").val();
                        var email=$("#email").val();
                        var  farmerName=$("#farmerName").val();
                        $.ajax({
                            url:"${ctx}/reg/addUser",
                            type:"POST",
                            data:{
                                name:loginName,
                                eName:eName,
                                farmerName:farmerName,
                                officeName:officeName,
                                name:name,
                                loginName:loginName,
                                password:confirmNewPassword,
                                email:email
                            },
                            dataType:"JSON",
                            success:function (result) {
                                if(result.flag==1){
                                   layer.confirm('注册成功', {
                                        btn: ['登录'] //按钮
                                    }, function(){
                                        var url="${ctx}/reg/login?user="+result.info;
                                        $("#loginForm").attr("action",url);
                                        $("#loginForm").submit();
                                    });
                             }
                                else{
                                    layer.confirm('注册失败', {
                                        btn: ['关闭'] //按钮
                                    }, function(){
                                       layer.msg({time: 1});
                                    });
                                }
                            }
                        });
                    }
                });
            });
		</script>
	</head>
	<body class="body">
		<div class="header">
			<div class="he_left">
				<img src="${ctxStatic}/images/no-3.png"/>
			</div>
			<div class="he_rigth"><a href="${ctx}">登录</a> | <a href="${ctx}/reg/optionRole">注册</a></div>
		</div>

		<div class="cont">
			<%--action="${ctx}/reg/addUser"--%>
				<form class="form-signin"  id="loginForm" method="post">

				<div class="texts2">
					<c:choose>
						<c:when test="${eName=='farmerBoss'}">农场主</c:when>
						<c:otherwise>农 户</c:otherwise>
					</c:choose>注册
				</div>
					<c:if test="${eName!='farmerBoss'}">
					<div class="reda" style="margin-bottom: 0px;">
						所属公司名称：<%--<input type="text" class="inputs"/>--%>
					<%--	<input type="text"  class="required" id="o" onkeyup="autoComplete.start(event)"></div>
						<div class="auto_hidden" id="auto"></div>--%>

						<select class="officeName" name="officeName">
							<option value="-1">——请选择——</option>
							<c:forEach var="o" items="${resultList}">
								<option value="${o.name}">${o.name}</option>
							</c:forEach>
						</select>&nbsp;
						<span class="help-inline"><font color="red">*</font> </span>&nbsp;&nbsp;&nbsp;
					</div>
					</c:if>
				<div class="reda">
					<input type="hidden" id="eName" name="eName" value="${eName}"/>
					<input type="hidden" id="farmerName" name="farmerName" value="${farmerName}"/>
				姓名：<%--<input type="text" class="inputs"/>--%>
					<input type="text" id="name" name="name" htmlEscape="false" maxlength="50" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
				<div class="reda">	
				账号(手机号)：	<input type="text" id="loginName" name="loginName" class="required" onblur="f1()">
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
				<div class="reda">
					<input type="text" class="inputs1" value="短信校验码" onfocus="if (value =='短信校验码'){value=''}" onblur="if (value ==''){value='短信校验码'}" />
					<input type="button" class="gain" value="获取验证码" />
				</div>
				<div class="reda">	
				密码：
					<input id="newPassword" name="newPassword" type="password"  maxlength="50" minlength="3"  class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
				<div class="reda">
					确认密码：
					<input id="password" name="password" type="password"  maxlength="50" minlength="3"  class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
				<div class="reda">
					邮箱：
					<input type="text" id="email" name="email" htmlEscape="false" maxlength="100" class="email"/>
					<%--<span class="help-inline"><font color="red">*</font> </span>--%>
				</div>
				<input class="regis" value="注册" type="button"/>
			</form>
		</div>
		<div class="footer2">
		Copyright &copy;北京集萃通联科技有限公司
	</div>
	<div class="wenjian">
			<a><img src="${ctxStatic}/images/login/tianqi.png"/></a>
			<a><img src="${ctxStatic}/images/login/jianjie.png"/></a>
			<a style="margin-right: 0px;"><img src="${ctxStatic}/images/login/yiwen.png"/></a>
	</div>
<%--	<script>

		var list=document.getElementById("resultList").value;
        var strs= new Array(); //定义一数组
        var list2=list.slice(1,list.length-1);
		strs=list2.split(","); //字符分割

        var autoComplete=new AutoComplete('o','auto',strs);
	</script>--%>
	</body>
</html>

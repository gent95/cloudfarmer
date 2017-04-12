<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改密码</title>
		<link rel="stylesheet" href="${ctxStatic}/css/login.css" />
		<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.js" ></script>
		<script type="text/javascript" src="${ctxStatic}/layer/layer.js" ></script>
		<script type="text/javascript">
            function f1(){
                var password=$("#password").val();
                var password2=$("#newPassword").val();
                var loginName=$("#loginName").val();
                if(loginName!=null&&loginName!="") {
                    if (password != null && password != "") {
                        if (password2 != null && password2 != "") {
                            if (password2 != password) {
                                layer.msg("两次密码输入不一致，请重新输入！");
                            }
                            if(password2==password){
                                var url="${ctx}/reg/modify?password="+password+"&loginName="+loginName;
                                $(".form-signin").attr("action",url);
                                $(".form-signin").submit();
							}
                        }
                    }
                }

			}
		</script>
	</head>
	<body class="body">
		<div class="header">
			<div class="he_left">
				<img src="${ctxStatic}/images/no-3.png"/>
			</div>
			<div class="he_rigth"><a href="${ctx}">登录</a> | <a href="${ctx}/reg/optionRole">注册</a></div>
		</div>
		<div class="contPassword">
			<form class="form-signin" id="inputForm" action="${ctx}/reg/modify" method="post">
				<input type="hidden" id="loginName" value="${name}"/>
				<div class="texts2">
					修改密码
				</div>
				<div class="reda">
					新密码：
					<input id="newPassword" name="newPassword" type="password"  maxlength="50" minlength="3" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
				<div class="reda">
					确认密码：
					<input id="password" name="password" type="password" maxlength="50" minlength="3" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
				<input type="button" class="regis" value="修改" onclick="f1()"/>
			</form>
		</div>
		<div class="footer3">
		Copyright &copy;北京集萃通联科技有限公司
	</div>
	<div class="wenjian">
			<a><img src="${ctxStatic}/images/login/tianqi.png"/></a>
			<a><img src="${ctxStatic}/images/login/jianjie.png"/></a>
			<a  style="margin-right: 0px;"><img src="${ctxStatic}/images/login/yiwen.png"/></a>
	</div>
	</body>
</html>

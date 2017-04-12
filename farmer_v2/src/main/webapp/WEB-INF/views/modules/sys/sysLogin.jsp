<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta charset="utf-8" />
	<title>登录</title>
	<link rel="stylesheet" href="${ctxStatic}/css/login.css" />
	<script type="text/javascript">
        $(document).ready(function() {
			/*    $("#loginForm").validate({
			 messages: {
			 username: {required: "请填写用户名."},password: {required: "请填写密码."}
			 },
			 errorLabelContainer: "#messageBox",
			 errorPlacement: function(error, element) {
			 error.appendTo($("#loginError").parent());
			 }
			 });*/
        });
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
            alert('未登录或登录超时。请重新登录，谢谢！');
            top.location = "${ctx}";
        }
	</script>
</head>
<body class="body">
<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
<div class="header">
	<div class="he_left">
		<img src="${ctxStatic}/images/no-3.png"/>
	</div>
	<div class="he_rigth"><a href="${adminPath}">登录</a> | <a href="${ctx}/reg/optionRole">注册</a></div>
</div>
<div class="header2">
	<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}">
		<label id="loginError" class="error">${message}</label>
	</div>
</div>
<div class="content">
	<form id="loginForm" name="form1" class="form-signin" action="${ctx}/login" method="post">
		<div class="texts">
			Welocome
		</div>
		<div class="username1">
			<img src="${ctxStatic}/images/login/user.png" />
			<input type="text" class="input" name="username" id="username"  value="${user.loginName}"/>
		</div>
		<div class="password1">
			<img src="${ctxStatic}/images/login/pwd.png"/>
			<input type="password" class="input" id="password" name="password"/>
		</div>
		<div class="jizhu">
			<a href="#" class="oneo"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/>记住密码</a>
			<a href="${ctx}/reg/forgetpassword">忘记密码?</a>
		</div>
		<%--<div class="btn">
			<div onclick="submitform()"><a>登录</a></div>
		</div>--%>
		<input type="submit" class="btn" value="登录"/>
	</form>
</div>
<div class="footer">
	Copyright &copy;大唐移动通讯设备有限公司
</div>
<div class="wenjian">
	<a><img src="${ctxStatic}/images/login/tianqi.png"/></a>
	<a><img src="${ctxStatic}/images/login/jianjie.png"/></a>
	<a style="margin-right: 0px;"><img src="${ctxStatic}/images/login/yiwen.png"/></a>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>忘记密码</title>
		<link rel="stylesheet" href="${ctxStatic}/css/login.css" />
		<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.js" ></script>
		<script type="text/javascript" src="${ctxStatic}/layer/layer.js" ></script>
		<script type="text/javascript">
         $(document).ready(function() {
             reg = /^1[0-9]{10}$/;
				$(".gain").click(function(){
                   phone=$("[name='name']").val();
                    if(phone!=null&&phone!=""){
                       if(reg.test(phone)==false){
                              layer.msg("手机号输入错误！");
                          }
                          else{
                  		$.ajax({
                  		    url:"${ctx}/reg/checkUserName",
							data:{
                  		        name:phone
							},
							dataType:"JSON",
							success:function (result) {
								if(result.status==1){
								    layer.msg("该手机号不存在！");
                                }
                                if(result.status==0){
                                    layer.msg("验证码已发送");
                                  $.ajax({
                                        url:"${ctx}/reg/validateMsg",
                                        data:{
                                            name:phone
                                        },
                                        dataType:"JSON",
                                        success:function (result) {
                                            code=result.msgCode;
                                        }
                                    });
                                }
                            }
						});
					   }
                    }else{
                        layer.msg("请输入手机号");
                    }
				});

             $(".regis").click(function () {
               var yanzhengma=$(".inputs1").val();
                 if(phone!=null&&phone!=""){
                     if(yanzhengma!=null&&yanzhengma!=""){
                         if(yanzhengma!=code){
                             layer.msg("验证码输入错误！");
                         }
                         if(yanzhengma==code){
                             var url="${ctx}/reg/modifyPassword?loginName="+phone;
                             $(".form-signin").attr("action",url);
                             $(".form-signin").submit();
                         }
                     }else {
                         layer.msg("请输入验证码");
                     }
                 }else{
                     layer.msg("请输入手机号");
                 }
             });

			});
         function f1(){
             var name=$("[name='name']").val();
             if(name!=null||name!=""){
                 if(reg.test(name)==false){
                     layer.msg("手机号输入错误");
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
			<form class="form-signin" action="${ctx}/reg/modifyPassword" method="post">
				<div class="texts2">
					忘记密码
				</div>
					<div class="reda">
						手机号：
						<input type="text" name="name" htmlEscape="false" maxlength="50" class="required" onblur="f1()"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				<div class="reda">	
				<input type="text" class="inputs1" value="短信校验码" onfocus="if (value =='短信校验码'){value=''}" onblur="if (value ==''){value='短信校验码'}" />
					<input type="button" class="gain" value="获取验证码" />
				</div>
			<!--	<div class="regis">
				<div onclick="form1.submit()"><a>注册</a></div>
				</div>-->
				<input type="button" class="regis" value="下一步" />
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

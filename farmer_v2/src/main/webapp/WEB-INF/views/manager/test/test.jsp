<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测试</title>
	<meta name="decorator" content="default"/>
	<script src="/static/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript">
		$.extend({
		    min:function (var1,var2) {
		        return var1 < var2 ? var1:var2;
            }
		})
	</script>
	<script type="text/javascript">
	function test() {
		alert($.min(100,5));
    }
	</script>
</head>
<body>
<input type="button" onclick="test()"  value="测试">
</body>
</html>
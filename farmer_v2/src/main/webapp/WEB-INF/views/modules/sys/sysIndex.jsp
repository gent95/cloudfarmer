<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="blank"/>
    <c:set var="tabmode" value="${empty cookie.tabmode.value ? '0' : cookie.tabmode.value}"/>
    <c:if test="${tabmode eq '1'}">
        <link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css"/>
        <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script>
    </c:if>
    <style type="text/css">
        #main {
            padding: 0;
            margin: 0;
        }

        #main .container-fluid {
            padding: 0 4px 0 6px;
        }

        #header {
            margin: 0 0 8px;
            position: static;
        }

        #header li {
            font-size: 14px;
            _font-size: 12px;
        }

        #header .brand {
            font-family: pingfang-regular;
            font-size: 26px;
            padding-left: 33px;
        }

        #left {
            overflow-x: hidden;
            overflow-y: auto;
        }

        #left .collapse {
            position: static;
        }

        #userControl > li > a { /*color:#fff;*/
            text-shadow: none;
        }

        #userControl > li > a:hover, #user #userControl > li.open > a {
            background: transparent;
        }

        .selected {
            font-size: 16.57px;
            background-color: rgba(255, 255, 255, 0.25);
            border-radius: 6px;
            color: white;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            dispTime();

            //通过调用新浪IP地址库接口查询用户当前所在国家、省份、城市、运营商信息
            $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function () {
                $(".city").html(remote_ip_info.city);
                $.ajax({
                    url: '/weather?name=' + remote_ip_info.city, success: function (result) {
                        $(".temp").html(result.info.temperature+ "°");
                    }
                })
            });
            <c:if test="${tabmode eq '1'}">
            初始化页签
            $.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: {'height': $('#right').height() - tabTitleHeight},
                tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
            });//</c:if>
            // 绑定菜单单击事件
            $("#menu a.menu").click(function () {
                // 一级菜单焦点
                $("#menu li.menu").removeClass("active");
                $("#menu li.menu").removeClass("selected");
                $(this).parent().addClass("selected");

                // 左侧区域隐藏
                if ($(this).attr("target") == "mainFrame") {
                    $("#left,#openClose").hide();
                    wSizeWidth();
                    // <c:if test="${tabmode eq '1'}"> 隐藏页签
                    $(".jericho_tab").hide();
                    $("#mainFrame").show();//</c:if>
                    return true;
                }
                // 左侧区域显示
                $("#left,#openClose").show();
                if (!$("#openClose").hasClass("close")) {
                    $("#openClose").click();
                }
                // 显示二级菜单
                var menuId = "#menu-" + $(this).attr("data-id");
                if ($(menuId).length > 0) {
                    $("#left .accordion").hide();
                    $(menuId).show();
                    // 初始化点击第一个二级菜单
                    if (!$(menuId + " .accordion-body:first").hasClass('in')) {
                        $(menuId + " .accordion-heading:first a").click();
                    }
                    if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")) {
                        $(menuId + " .accordion-body a:first i").click();
                    }
                    // 初始化点击第一个三级菜单
                    $(menuId + " .accordion-body li:first li:first a:first i").click();
                } else {
                    // 获取二级菜单数据
                    $.get($(this).attr("data-href"), function (data) {
                        if (data.indexOf("id=\"loginForm\"") != -1) {
                            alert('未登录或登录超时。请重新登录，谢谢！');
                            top.location = "${ctx}";
                            return false;
                        }
                        $("#left .accordion").hide();
                        $("#left").append(data);
                        // 链接去掉虚框
                        $(menuId + " a").bind("focus", function () {
                            if (this.blur) {
                                this.blur()
                            }
                            ;
                        });
                        // 二级标题
                        $(menuId + " .accordion-heading a").click(function () {
                        });
                        // 二级内容
                        $(menuId + " .accordion-body a").click(function () {
                            $(menuId + " li").removeClass("active");
                            $(menuId + " li i").removeClass("icon-white");
                            $(this).parent().addClass("active");
                            $(this).children("i").addClass("icon-white");

                            $(".icon-chevron-down").remove();
                            $(".icon-chevron-right").remove();
                        });
                        // 展现三级
                        $(menuId + " .accordion-inner a").click(function () {
                            var href = $(this).attr("data-href");
                            if ($(href).length > 0) {
                                $(href).toggle().parent().toggle();
                                return false;
                            }
                            // <c:if test="${tabmode eq '1'}"> 打开显示页签
                            return addTab($(this)); // </c:if>
                        });
                        // 默认选中第一个菜单
                        $(menuId + " .accordion-body a:first i").click();
                        $(menuId + " .accordion-body li:first li:first a:first i").click();

                        /*非管理员删除导航*/
                        if( !${fns:ifAdmin()}){
                            $("#menu").remove();
                        }
                    });
                }
                // 大小宽度调整
                wSizeWidth();
                return false;
            });
            // 初始化点击第一个一级菜单
            $("#menu a.menu:first span").click();
            // <c:if test="${tabmode eq '1'}"> 下拉菜单以选项卡方式打开
            $("#userInfo .dropdown-menu a").mouseup(function () {
                return addTab($(this), true);
            });// </c:if>
            // 鼠标移动到边界自动弹出左侧菜单
            $("#openClose").mouseover(function () {
                if ($(this).hasClass("open")) {
                    $(this).click();
                }
            });
            // 获取通知数目  <c:set var="oaNotifyRemindInterval" value="${fns:getConfig('oa.notify.remind.interval')}"/>
            function getNotifyNum() {
                $.get("${ctx}/oa/oaNotify/self/count?updateSession=0&t=" + new Date().getTime(), function (data) {
                    var num = parseFloat(data);
                    if (num > 0) {
                        $("#notifyNum,#notifyNum2").show().html("(" + num + ")");
                    } else {
                        $("#notifyNum,#notifyNum2").hide()
                    }
                });
            }

            getNotifyNum(); //<c:if test="${oaNotifyRemindInterval ne '' && oaNotifyRemindInterval ne '0'}">
            setInterval(getNotifyNum, ${oaNotifyRemindInterval}); //</c:if>
            $(".accordion").after("<div style='font-size: 10px; width: 155px; margin-bottom: 0px; margin-top: 90%;'> Copyright &copy;${fns:getConfig('productName')}</div>");


        });
        function dispTime() {
            var nowDate = new Date();
            var hour = nowDate.getHours();
            var minu = nowDate.getMinutes();
            if (minu < 10) {
                minu = "0" + minu;
            }
            var time = hour + ":" + minu;
            document.getElementById("Time").innerHTML = time;
            var myTime = setTimeout("dispTime()", 10000);
        }
        // <c:if test="${tabmode eq '1'}"> 添加一个页签
        function addTab($this, refresh) {
            $(".jericho_tab").show();
            $("#mainFrame").hide();
            $.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $this.text(),
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('href')
                }
            }).loadData(refresh);
            return false;
        }// </c:if>

    </script>

    <script>
        $(function () {


        })

    </script>
</head>
<body>
<div id="main">

    <div id="header" class="navbar navbar-fixed-top" style="margin-bottom: 0px;">
        <%--登录用户--%>
        <div class="navbar-inner">
            <div class="brand">
                <c:choose>
                    <c:when test="${fns:getUser().photo!=null&&fns:getUser().photo!=''}">
                        <img src="${fns:getUser().photo}" style="border-radius: 50%;height: 130px; width: 140px;"/>
                    </c:when>
                    <c:otherwise>
                        <li style="list-style-type:none;" id="userInfo" class="dropdown">

                            <img src="${ctxStatic}/images/homePage/tuxiang.png"/><br/>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">
                                <span>${fns:getUser().name}</span>
                                <span id="notifyNum" class="label label-info hide"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp;
                                    个人信息</a>
                                </li>
                                <li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;
                                    修改密码</a>
                                </li>
                                <li><a href="${ctx}/logout"><i></i>&nbsp;
                                    退出登陆</a>
                                </li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </div>
            <%--天气--%>
            <div style="height: 100px; width: 300px; float: left; margin-top: 10px">
                <img style="float:left;" src="/static/js/regions/img/fog.png">
                <div style="float:left; margin-top: 30px; margin-left: 10px; font-family:'Microsoft Yahei'; font-size: 60px;"
                     class="temp"></div>
            </div>

            <div>

                <ul id="userControl" class="nav pull-right"style="margin-right: 50px">
                    <div >
                        <p style="font-size: 30px;margin-bottom:20px"><%= new SimpleDateFormat("yyyy/MM/dd ").format(new Date()) %></p>
                        <p style="font-size: 49px" id="Time"></p>
                    </div>
                </ul>
                <div class="nav-collapse">
                    <ul id="menu" class="nav" style="*white-space:nowrap;float:none;">
                        <c:set var="firstMenu" value="true"/>
                        <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                            <c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
                                <li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
                                    <c:if test="${empty menu.href}">
                                        <a class="menu " href="javascript:"
                                           data-href="${ctx}/sys/menu/tree?parentId=${menu.id}"
                                           data-id="${menu.id}"><span>${menu.name}</span></a>
                                    </c:if>
                                    <c:if test="${not empty menu.href}">
                                        <a class="menu"
                                           href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}"
                                           data-id="${menu.id}" target="mainFrame"><span>${menu.name}</span></a>
                                    </c:if>
                                </li>
                                <c:if test="${firstMenu}">
                                    <c:set var="firstMenuId" value="${menu.id}"/>
                                </c:if>
                                <c:set var="firstMenu" value="false"/>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <script>


    </script>
    <div class="container-fluid">
        <div id="content" class="row-fluid">
            <div id="left" style="background: url(/static/images/homePage/tu-2.png) no-repeat">
            </div>
            <div id="right">
                <iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes"
                        frameborder="no" width="100%"></iframe>
            </div>
        </div>
        <div id="footer" class="row-fluid">
            Copyright &copy;${fns:getConfig('productName')}
        </div>

    </div>
</div>
<script type="text/javascript">
    var leftWidth = 228; // 左侧窗口大小
    var tabTitleHeight = 33; // 页签的高度
    var htmlObj = $("html"), mainObj = $("#main");
    var headerObj = $("#header");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize() {
        var minHeight = 500, minWidth = 980;
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({
            "overflow-x": strs[1] < minWidth ? "auto" : "hidden",
            "overflow-y": strs[0] < minHeight ? "auto" : "hidden"
        });
        mainObj.css("width", strs[1] < minWidth ? minWidth - 10 : "auto");
        frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - (strs[1] < minWidth ? 42 : 28) + 28);
        $("#openClose").height($("#openClose").height());// <c:if test="${tabmode eq '1'}">
        $(".jericho_tab iframe").height($("#right").height() - tabTitleHeight); // </c:if>
        wSizeWidth();
    }
    function wSizeWidth() {
        if (!$("#openClose").is(":hidden")) {
            var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
            $("#right").width($("#content").width() - leftWidth - $("#openClose").width());
        } else {
            $("#right").width("100%");
        }
    }// <c:if test="${tabmode eq '1'}">
    function openCloseClickCallBack(b) {
        $.fn.jerichoTab.resize();
    } // </c:if>
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>
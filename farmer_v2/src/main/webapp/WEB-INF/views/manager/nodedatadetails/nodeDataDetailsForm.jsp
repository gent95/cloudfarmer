<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节点数据详情管理</title>
	<meta name="decorator" content="default"/>
	<%--
        <link rel="stylesheet" href="${ctxStatic}/bootstrap/css/bootstrap.css">--%>
	<style>
		body{
			color: red;
			font-size:20px;
		}

	</style>
</head>
<body>

<div>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/nodedatadetails/nodeDataDetails/list?nodeId=${nodeId}">详细信息</a></li>
		<li class="active"><a href="${ctx}/nodedatadetails/nodeDataDetails/form?nodeId=${nodeId}">图表数据</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="node" action="${ctx}/node/node/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>刷新周期：</label>
				<select id="cycleTimes">
					<option value="5">5分钟</option>
					<option value="10">10分钟</option>
					<option value="30">30分钟</option>
					<option value="60">60分钟</option>
				</select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="启用" onclick="cycleTime()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<input type="hidden" value="${nodeMac}" id="mac"/>
	<ul>
		<li  style="display:block;">
			<div class="col-md-2 div-padding" id="chart1" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
			<div class="col-md-2 div-padding" id="chart2" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
			<div class="col-md-2 div-padding" id="chart3" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
			<div class="col-md-2 div-padding" id="chart4" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
			<div class="col-md-2 div-padding" id="chart5" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
			<div class="col-md-2 div-padding" id="chart6" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
			<div class="col-md-2 div-padding" id="chart7" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
			<div class="col-md-2 div-padding" id="chart8" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
			<div class="col-md-2 div-padding" id="chart9" style="width: 480px;height: 320px;margin: 0 auto;border: 1px solid gray;"></div>
		</li>
	</ul>
</div>
<script type="text/javascript">

    function dateFormatUtil(longTypeDate){
        var date = new Date(longTypeDate);
        Y = date.getFullYear() + '-';
        M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        D = date.getDate() + ' ';
        h = date.getHours() + ':';
        m = date.getMinutes() + ':';
        s = date.getSeconds();
        var time=(Y+M+D+h+m+s);
        return time;
    }
</script>

<!--Echart Start -->
<script src="${ctxStatic}/js/echarts.js"></script>
<script type="text/javascript">
    var flag = 0;
    var infoList;
    var timeInterval;
    var nodeMac = $("#mac").val();
    // 配置路径
    require.config({
        paths: {
            echarts: '${ctxStatic}/js'  //（这里）
        }
    });
    $.ajax({
        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
        type:"POST",
        data:{
            nodeMac:nodeMac
        },
        dataType:"json",
        async:false,
        success:function(info){
            if(info.data.length==0){
                if(flag==0){
                    alert("暂无数据");
                    flag=-1;
                }
            }
            else{
                infoList=info;
            }
        }
    });

    function cycleTime(){
        var time=$("#cycleTimes").find("option:selected").val();
        timeInterval=1000*parseInt(time)*60;
        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart1');
                var echart = ec.init(chart);

				/* echart.showLoading({
				 text: '正在努力加载中...'
				 }); */

                // 同步执行
                $.ajaxSettings.async = true;
                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++)
                    {
                        yValue[i] = infoList.data[i].airTemperature;
                        yDate[i]=infoList.data[i].addTime;
                        da[i] =dateFormatUtil(yDate[i]);
                    }
                }
                var option = {
                    //设置标题的
                    texStyle:{
                        color:'#cff7cd'
                    },
                    title : {
                        text: '大气温度',//大标题写类型
                        subtext: '（%RH）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['大气温度']
                    },
                    toolbox: {
                        show : false,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        show : false,
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '温度',
                            boundaryGap: [0.2, 0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新温度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                var ti=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket=setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].airTemperature;
                                ti[i]=info.data[i].addTime;
                                axisData[i] =dateFormatUtil(ti[i]);
                            }
                        }
                    });
					/* //这个地方就是更新了Y的值
					 lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );


        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart2');
                var echart = ec.init(chart);

				/* echart.showLoading({
				 text: '正在努力加载中...'
				 }); */



                // 同步执行
                $.ajaxSettings.async = false;



                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++){
                        yValue[i] = infoList.data[i].airHumidity;
                        yDate[i]=infoList.data[i].addTime;
                        da[i]=dateFormatUtil(yDate[i]);
                    }
                }

                var option = {
                    //设置标题的
                    title : {
                        text: '大气湿度',//大标题写类型
                        subtext: '（%RH）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['大气湿度']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        show : false,
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '湿度',
                            boundaryGap: [0.2, 0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新湿度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                var ti=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket=setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].airHumidity;
                                ti[i]=info.data[i].addTime;
                                axisData[i] =dateFormatUtil(ti[i]);
                            }
                        }
                    });
					/* //这个地方就是更新了Y的值
					 lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );

        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart3');
                var echart = ec.init(chart);



                // 同步执行
                $.ajaxSettings.async = false;



                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++){
                        yValue[i] = infoList.data[i].soilTemperature1;
                        yDate[i]=infoList.data[i].addTime;
                        da[i]=dateFormatUtil(yDate[i]);
                    }
                }
                var option = {
                    //设置标题的
                    title : {
                        text: '1号菌棒温度',//大标题写类型
                        subtext: '（℃）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['1号菌棒温度']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        show : false,
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '温度',
                            boundaryGap: [0.2, 0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新温度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                var ti=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket = setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].soilTemperature1;
                                ti[i]=info.data[i].addTime;
                                axisData[i] =dateFormatUtil(ti[i]);
                            }
                        }
                    });

					/*     //这个地方就是更新了Y的值
					 lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );

        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart4');
                var echart = ec.init(chart);



                // 同步执行
                $.ajaxSettings.async = false;
                var   nid=$("[name='nodeMac']").val();

                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++){
                        yValue[i] = infoList.data[i].soilHumidity1;
                        yDate[i]=infoList.data[i].addTime;
                        da[i]=dateFormatUtil(yDate[i]);

                    }
                }
                var option = {
                    //设置标题的
                    title : {
                        text: '1号菌棒湿度',//大标题写类型
                        subtext: '（%RH）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['1号菌棒湿度']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        show : false,
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '湿度',
                            boundaryGap: [0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新湿度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                var ti=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket = setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].soilHumidity1;
                                ti[i]=info.data[i].addTime;
                                axisData[i]=dateFormatUtil(ti[i]);
                            }
                        }
                    });
					/* //这个地方就是更新了Y的值
					 lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );

        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart5');
                var echart = ec.init(chart);



                // 同步执行
                $.ajaxSettings.async = false;



                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++){
                        yValue[i] = infoList.data[i].soilTemperature2;
                        yDate[i]=infoList.data[i].addTime;
                        da[i]=dateFormatUtil(yDate[i]);
                    }
                }
                var option = {
                    //设置标题的
                    title : {
                        text: '2号菌棒温度',//大标题写类型
                        subtext: '（℃）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['2号菌棒温度']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        show : false,
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '温度',
                            boundaryGap: [0.2, 0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新温度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                var ti=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket = setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].soilTemperature2;
                                ti[i]=info.data[i].addTime;
                                axisData[i] =dateFormatUtil(ti[i]);
                            }
                        }
                    });
					/* //这个地方就是更新了Y的值
					 lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );



        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart6');
                var echart = ec.init(chart);


                // 同步执行
                $.ajaxSettings.async = false;

                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++){
                        yValue[i] = infoList.data[i].soilHumidity2;
                        yDate[i]=infoList.data[i].addTime;
                        da[i]=dateFormatUtil(yDate[i]);
                    }
                }
                var option = {
                    //设置标题的
                    title : {
                        text: '2号菌棒湿度',//大标题写类型
                        subtext: '（%RH）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['2号菌棒湿度']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        show : false,
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '湿度',
                            boundaryGap: [0.2, 0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新湿度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                var ti=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket = setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].soilHumidity2;
                                ti[i]=info.data[i].addTime;
                                axisData[i] =dateFormatUtil(ti[i]);
                            }
                        }
                    });

					/* //这个地方就是更新了Y的值
					 lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );


        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart7');
                var echart = ec.init(chart);


                // 同步执行
                $.ajaxSettings.async = false;


                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++){
                        yValue[i] = infoList.data[i].soilTemperature3;
                        yDate[i]=infoList.data[i].addTime;
                        da[i]=dateFormatUtil(yDate[i]);
                    }
                }
                var option = {
                    //设置标题的
                    title : {
                        text: '3号菌棒温度',//大标题写类型
                        subtext: '（℃）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['3号菌棒温度']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        show : false,
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '温度',
                            boundaryGap: [0.2, 0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新温度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                var ti=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket = setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].soilTemperature3;
                                ti[i]=info.data[i].addTime;
                                axisData[i] =dateFormatUtil(ti[i]);
                            }
                        }
                    });
					/*  //这个地方就是更新了Y的值
					 lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );


        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart8');
                var echart = ec.init(chart);
                $.ajaxSettings.async = false;
                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++){
                        yValue[i] = infoList.data[i].soilHumidity3;
                        yDate[i]=infoList.data[i].addTime;
                        da[i]=dateFormatUtil(yDate[i]);
                    }

                }
                var option = {
                    //设置标题的
                    title : {
                        text: '3号菌棒湿度',//大标题写类型
                        subtext: '（%RH）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['3号菌棒湿度']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        show : false,
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '湿度',
                            boundaryGap: [0.2, 0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新湿度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                var ti=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket = setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].soilHumidity3;
                                ti[i]=info.data[i].addTime;
                                axisData[i] =dateFormatUtil(ti[i]);
                            }
                        }
                    });

					/* //这个地方就是更新了Y的值
					 lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );
        // 按需加载  (这个地方是从库里调柱状图，折线图)
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
            function (ec) {
                var chart = document.getElementById('chart9');
                var echart = ec.init(chart);

				/* echart.showLoading({
				 text: '正在努力加载中...'
				 }); */


                // 同步执行
                $.ajaxSettings.async = false;

                // 加载数据
                $.getJSON('node/getData.action', function (json) {
                    categories = json.dataTypeName;
                    values = json.dataValue;
                });

                var yValue = new Array();
                var yDate=new Array();
                var da=new Array();
                if(infoList.data.length!=0){
                    for(i=0;i<infoList.data.length;i++){
                        yValue[i] = infoList.data[i].co2;
                        yDate[i]=infoList.data[i].addTime;
                        da[i]=dateFormatUtil(yDate[i]);
                    }
                }
                var option = {
                    //设置标题的
                    title : {
                        text: '二氧化碳浓度',//大标题写类型
                        subtext: '（ppm）',//小标题我写的单位
                        textStyle: {
                            color: '#BE6219',
                            fontStyle: 'normal',
                            fontWeight: 'bolder',
                            fontFamily: 'sans-serif',
                            fontSize: 18,
                        }
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['二氧化碳浓度']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: false, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    //调整缩放的，设置成true的话效果拔群，可以当成附加的
                    dataZoom : {
                        type : 'slider',
                        start : 0,
                        end : 100
                    },
                    //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                    xAxis : [
                        {
                            type : 'category',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            boundaryGap : true,
                            data : (function(){
                                return da;
                            })()
                        }

                    ],
                    //y轴，
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                textStyle: {
                                    color: '#BE6219'
                                }
                            },
                            scale: true,
                            name : '浓度',
                            boundaryGap: [0.2, 0.2]
                        }
                    ],
                    //值，push往里存值，len跟上面的X轴对应，要改都改
                    series : [
                        {
                            name:'最新浓度',
                            type:'line',
                            data:(function (){
                                return yValue;
                            })()
                        }
                    ]
                };
                echart.setOption(option);
                //初始化最近的值，下面替换
                var lastData=new Array();
                //初始化X轴时间轴的值，下面替换
                var axisData=new Array();
                var ti=new Array();
                //清空计时器
                var timeTicket;
                clearInterval(timeTicket);
                //是指计时器，第一个参数是一个函数，设置刚刚的那两个X轴和Y轴的值
                //第二个参数2100是时间间隔，就是多长时间后再执行一次第一个函数，咱们改成传值间隔
                timeTicket = setInterval(function (){

                    var nodeMac = $("#mac").val();
                    $.ajax({
                        url:"${ctx}/nodedatadetails/nodeDataDetails/fetchLastData",
                        type:"POST",
                        data:{
                            nodeMac:nodeMac
                        },
                        dataType:"json",
                        success:function(info){
                            for(i=0;i<info.data.length;i++)
                            {
                                lastData[i] = info.data[i].co2;
                                ti[i]=info.data[i].addTime;
                                axisData[i] =dateFormatUtil(ti[i]);
                            }
                        }
                    });

                    //这个地方就是更新了Y的值
					/* lastData = 5 * Math.random(); */
                    //这里更新了时间轴X的值

                    // 动态数据接口 addData，然而之前都没有用到……
                    echart.addData([
                        [
                            0,        // 系列索引
                            lastData, // 新增数据
                            false,     // 新增数据是否从队列头部插入
                            false,     // 是否增加队列长度，false则自定删除原有数据，队头插入删队尾，队尾插入删队头
                            axisData
                        ]
                    ]);
                }, timeInterval);
            }
        );
    }


    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart1');
            var echart = ec.init(chart);

			/* echart.showLoading({
			 text: '正在努力加载中...'
			 }); */

            // 同步执行
            $.ajaxSettings.async = true;
            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++)
                {
                    yValue[i] = infoList.data[i].airTemperature;
                    yDate[i]=infoList.data[i].addTime;
                    da[i] =dateFormatUtil(yDate[i]);
                }
            }

            var option = {
                //设置标题的
                title : {
                    text: '大气温度',//大标题写类型
                    subtext: '（%RH）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['大气温度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    show : false,
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {
                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '温度',
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新温度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );


    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart2');
            var echart = ec.init(chart);
            // 同步执行
            $.ajaxSettings.async = false;
            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++){
                    yValue[i] = infoList.data[i].airHumidity;
                    yDate[i]=infoList.data[i].addTime;
                    da[i]=dateFormatUtil(yDate[i]);
                }
            }

            var option = {
                //设置标题的
                title : {
                    text: '大气湿度',//大标题写类型
                    subtext: '（%RH）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['大气湿度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    show : false,
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {
                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '湿度',
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新湿度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );

    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart3');
            var echart = ec.init(chart);
            // 同步执行
            $.ajaxSettings.async = false;
            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++){
                    yValue[i] = infoList.data[i].soilTemperature1;
                    yDate[i]=infoList.data[i].addTime;
                    da[i]=dateFormatUtil(yDate[i]);
                }
            }
            var option = {
                //设置标题的
                title : {
                    text: '1号菌棒温度',//大标题写类型
                    subtext: '（℃）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['1号菌棒温度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    show : false,
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {
                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '温度',
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新温度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );


    // 配置路径
    require.config({
        paths: {
            echarts: '${ctxStatic}/js'  //（这里）
        }
    });

    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart4');
            var echart = ec.init(chart);
            // 同步执行
            $.ajaxSettings.async = false;
            var   nid=$("[name='nodeMac']").val();

            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++){
                    yValue[i] = infoList.data[i].soilHumidity1;
                    yDate[i]=infoList.data[i].addTime;
                    da[i]=dateFormatUtil(yDate[i]);

                }
            }
            var option = {
                //设置标题的
                title : {
                    text: '1号菌棒湿度',//大标题写类型
                    subtext: '（%RH）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['1号菌棒湿度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    show : false,
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {
                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '湿度',
                        boundaryGap: [0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新湿度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );

    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart5');
            var echart = ec.init(chart);
            // 同步执行
            $.ajaxSettings.async = false;
            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++){
                    yValue[i] = infoList.data[i].soilTemperature2;
                    yDate[i]=infoList.data[i].addTime;
                    da[i]=dateFormatUtil(yDate[i]);
                }
            }
            var option = {
                //设置标题的
                title : {
                    text: '2号菌棒温度',//大标题写类型
                    subtext: '（℃）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['2号菌棒温度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    show : false,
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {
                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '温度',
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新温度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );



    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart6');
            var echart = ec.init(chart);

            // 同步执行
            $.ajaxSettings.async = false;

            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++){
                    yValue[i] = infoList.data[i].soilHumidity2;
                    yDate[i]=infoList.data[i].addTime;
                    da[i]=dateFormatUtil(yDate[i]);
                }
            }
            var option = {
                //设置标题的
                title : {
                    text: '2号菌棒湿度',//大标题写类型
                    subtext: '（%RH）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['2号菌棒湿度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    show : false,
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {
                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '湿度',
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新湿度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );


    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart7');
            var echart = ec.init(chart);
            // 同步执行
            $.ajaxSettings.async = false;
            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++){
                    yValue[i] = infoList.data[i].soilTemperature3;
                    yDate[i]=infoList.data[i].addTime;
                    da[i]=dateFormatUtil(yDate[i]);
                }
            }
            var option = {
                //设置标题的
                title : {
                    text: '3号菌棒温度',//大标题写类型
                    subtext: '（℃）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['3号菌棒温度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    show : false,
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {
                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '温度',
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新温度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );


    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart8');
            var echart = ec.init(chart);
            $.ajaxSettings.async = false;
            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++){
                    yValue[i] = infoList.data[i].soilHumidity3;
                    yDate[i]=infoList.data[i].addTime;
                    da[i]=dateFormatUtil(yDate[i]);
                }

            }
            var option = {
                //设置标题的
                title : {
                    text: '3号菌棒湿度',//大标题写类型
                    subtext: '（%RH）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['3号菌棒湿度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    show : false,
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {

                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '湿度',
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新湿度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );

    // 按需加载  (这个地方是从库里调柱状图，折线图)
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line'
        ],
        //这个地方的id是前面ul里的div的那个id，ajax里的方法应该不用动
        function (ec) {
            var chart = document.getElementById('chart9');
            var echart = ec.init(chart);
            // 同步执行
            $.ajaxSettings.async = false;

            // 加载数据
            $.getJSON('node/getData.action', function (json) {
                categories = json.dataTypeName;
                values = json.dataValue;
            });

            var yValue = new Array();
            var yDate=new Array();
            var da=new Array();
            if(infoList.data.length!=0){
                for(i=0;i<infoList.data.length;i++){
                    yValue[i] = infoList.data[i].co2;
                    yDate[i]=infoList.data[i].addTime;
                    da[i]=dateFormatUtil(yDate[i]);
                }
            }
            var option = {
                //设置标题的
                title : {
                    text: '二氧化碳浓度',//大标题写类型
                    subtext: '（ppm）',//小标题我写的单位
                    textStyle: {
                        color: '#BE6219',
                        fontStyle: 'normal',
                        fontWeight: 'bolder',
                        fontFamily: 'sans-serif',
                        fontSize: 18,
                    }
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['二氧化碳浓度']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: false, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                //调整缩放的，设置成true的话效果拔群，可以当成附加的
                dataZoom : {
                    type : 'slider',
                    start : 0,
                    end : 100
                },
                //X轴，时间轴的动态显示，len的值是x轴分多少段，可以不改
                xAxis : [
                    {
                        type : 'category',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        boundaryGap : true,
                        data : (function(){
                            return da;
                        })()
                    }

                ],
                //y轴，
                yAxis : [
                    {
                        type : 'value',
                        axisLabel: {
                            textStyle: {
                                color: '#BE6219'
                            }
                        },
                        scale: true,
                        name : '浓度',
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                //值，push往里存值，len跟上面的X轴对应，要改都改
                series : [
                    {
                        name:'最新浓度',
                        type:'line',
                        data:(function (){
                            return yValue;
                        })()
                    }
                ]
            };
            echart.setOption(option);
        }
    );

</script>

</body>
</html>

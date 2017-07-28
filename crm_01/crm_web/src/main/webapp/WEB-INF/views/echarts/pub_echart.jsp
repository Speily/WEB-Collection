<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-我的客户</title>
    <%@ include file="../base/base-css.jsp"%>
    <style>
        .name-avatar {
            display: inline-block;
            width: 45px;
            height: 45px;
            background-color: #3d5d7c;
            border-radius: 50%;
            text-align: center;
            line-height: 45px;
            font-size: 24px;
            color: #FFF;
        }
        .table>tbody>tr:hover {
            cursor: pointer;
        }
        .table>tbody>tr>td {
            vertical-align: middle;
        }
        .star {
            font-size: 20px;
            color: #a16092;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="chart_public"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">柱状图</h3>
                </div>
                <div class="box-body">
                    <div id="bar" style="height: 300px;width: 100%"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">饼图</h3>
                        </div>
                        <div class="box-body">
                            <div id="pie" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">折线图</h3>
                        </div>
                        <div class="box-body">
                            <div id="line" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">HOME</h3>
                </div>
                <div class="box-body">
                    <div id="home" style="height: 300px;width: 100%"></div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/echarts/echarts.min.js"></script>
<script>
    $(function () {
        var bar =echarts.init($("#bar")[0]);
        var pie =echarts.init($("#pie")[0]);
        var line =echarts.init($("#line")[0]);
        var home =echarts.init($("#home")[0]);

        bar.setOption({
            title: {
                text: "2017\n上半年手机销售量统计图",
                left: 'center'
            },
            color: ['rgb(25, 183, 207)'],
            tooltip: {},
            legend: {
                data: ['销量'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: ['OPPO', "VIVO", "三星", "小米", "华为", "苹果"]
            },
            yAxis: {},
            series: {
                name: "销量",
                type: 'bar',
                data: [1000, 950, 600, 800, 750, 700]
            }
        });

        line.setOption({
            title: {
                text: "2017上半年手机销售量统计图",
                left: 'center'
            },
            tooltip: {},
            legend: {
                data: ['销量'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: ['OPPO', "VIVO", "三星", "小米", "华为", "苹果"]
            },
            yAxis: {},
            series: {
                name: "销量",
                type: 'line',
                data: [1000, 950, 600, 800, 750, 700]
            }
        });
        pie.setOption({
            title: {
                text: "网站访问来源统计"
            },
            series: {
                name: "销量",
                type: 'pie',
                data: [
                    {value:335, name:'直接访问'},
                    {value:310, name:'邮件营销'},
                    {value:274, name:'联盟广告'},
                    {value:235, name:'视频广告'},
                    {value:800, name:'搜索引擎'}
                ]
            }
        });

        home.setOption({
            title: {
                text: '动态数据',
                subtext: 'crm'
            },
            color:['rgb(232, 13, 181)'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#283b56'
                    }
                }
            },
            legend: {
                data:['最新成交价', '预购队列']
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: {
                show: false,
                start: 0,
                end: 100
            },
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: true,
                    data: (function (){
                        var now = new Date();
                        var res = [];
                        var len = 10;
                        while (len--) {
                            res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
                            now = new Date(now - 2000);
                        }
                        return res;
                    })()
                },
                {
                    type: 'category',
                    boundaryGap: true,
                    data: (function (){
                        var res = [];
                        var len = 10;
                        while (len--) {
                            res.push(len + 1);
                        }
                        return res;
                    })()
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    scale: true,
                    name: '价格',
                    max: 30,
                    min: 0,
                    boundaryGap: [0.2, 0.2]
                },
                {
                    type: 'value',
                    scale: true,
                    name: '预购量',
                    max: 1200,
                    min: 0,
                    boundaryGap: [0.2, 0.2]
                }
            ],
            series: [
                {
                    name:'预购队列',
                    type:'bar',
                    xAxisIndex: 1,
                    yAxisIndex: 1,
                    data:(function (){
                        var res = [];
                        var len = 10;
                        while (len--) {
                            res.push(Math.round(Math.random() * 1000));
                        }
                        return res;
                    })()
                },
                {
                    name:'最新成交价',
                    type:'line',
                    data:(function (){
                        var res = [];
                        var len = 0;
                        while (len < 10) {
                            res.push((Math.random()*10 + 5).toFixed(1) - 0);
                            len++;
                        }
                        return res;
                    })()
                }
            ]
        });
    });
</script>

</body>
</html>

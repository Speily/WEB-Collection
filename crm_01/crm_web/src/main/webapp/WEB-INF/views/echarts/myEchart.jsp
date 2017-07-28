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
        <jsp:param name="active" value="chart_my"/>
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
        var option = {
            title: {
                text: "客户级别数量统计",
                left: 'center'
            },
            color: ['rgb(25, 183, 207)'],
            tooltip: {},
            legend: {
                data: ['人数'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: []
            },
            yAxis: {},
            series: {
                name: "人数",
                type: 'bar',
                data: []
            }
        }
        bar.setOption(option);

        $.get("/chart/customer/bar.json").done(function (resp) {
            var levelArray = [];
            var dataArray = [];
            //将对应数据转成数组
            for(var i = 0;i < resp.data.length;i++) {
                var obj = resp.data[i];
                levelArray.push(obj.level);
                dataArray.push(obj.num);
            }
            bar.setOption({
                xAxis: {
                    data: levelArray
                },
                series: {
                    data: dataArray
                }
            });
        }).error(function () {
            layer.msg("获取数据异常");
        });

    });
</script>

</body>
</html>

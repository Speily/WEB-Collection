<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-我的客户</title>
    <%@ include file="../base/base-css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="task_my"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">计划任务</h3>

                    <div class="box-tools pull-right">
                        <<a href="/task/new">
                        <button class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增任务</button>
                    </a>
                        <c:if test="${dataFrom == 'all'}">
                            <a href="/task/my">
                                <button class="btn btn-primary btn-sm"><i class="fa fa-eye"></i>显示未完成任务</button>
                            </a>
                        </c:if>
                        <c:if test="${dataFrom != 'all'}">
                            <a href="/task/my/all">
                                <button class="btn btn-primary btn-sm"><i class="fa fa-eye"></i>显示所有任务</button>
                            </a>
                        </c:if>
                    </div>
                </div>
                <div class="box-body">
                    <%--redirectFlash--%>
                    <c:if test="${not empty message}">
                        <div class="alert alert-info">${message}</div>
                    </c:if>

                    <%--任务--%>
                    <ul class="todo-list">
                        <c:forEach items="${taskList}" var="task">
                        <c:if test="${not empty task.custId}">
                        <li class="${task.state == 0 ?'':'done'}">
                            <input type="checkbox" class="task_checkbox" ${task.state == 0 ?'':'checked'}
                                   value="${task.id}">
                            <span class="text">${task.name}</span>
                            <a href="/customer/my/${task.custId}"><i class="fa fa-user-o"></i> ${task.customer.custName}
                            </a>
                            <small class="label label-danger"><i class="fa fa-clock-o"></i>${task.endTime}"</small>
                            <div class="tools">
                                <i class="fa fa-edit"></i>
                                <i class="fa fa-trash-o del_task" rel="${task.id}"></i>
                            </div>
                        </li>
                        </c:if>
                        <c:if test="${not empty task.chanceId}">
                        <li class="${task.state == 0 ? '':'done'}">
                            <input type="checkbox" class="task_checkbox" ${task.state == 0 ?'':'checked'}
                                   value="${task.id}">
                            <span class="text">${task.name}</span>
                            <a href="/chance/my/${task.chanceId}"><i class="fa fa-user-o"></i> ${task.chance.name}</a>
                            <small class="label label-danger"><i class="fa fa-clock-o"></i>${task.endTime}</small>
                            <div class="tools">
                                <i class="fa fa-edit"></i>
                                <i class="fa fa-trash-o del_task" rel="${task.id}"></i>
                            </div>
                        </li>
                        </c:if>
                        <c:if test="${empty task.custId and empty task.chanceId}">
                        <li class="${task.state == 0?'':'done'}">
                            <input type="checkbox" class="task_checkbox" ${task.state == 0 ?'':'checked'}
                                   value="${task.id}">
                            <span class="text">${task.name}</span>
                            <small class="label label-danger"><i class="fa fa-clock-o"></i>${task.endTime}"</small>
                            <div class="tools">
                                <i class="fa fa-edit"></i>
                                <i class="fa fa-trash-o del_task" rel="${task.id}"></i>
                            </div>
                        </li>
                        </c:if>
                        </c:forEach>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%@ include file="../base/base-footer.jsp" %>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp" %>
<script src="/static/plugins/page/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {

        /*选中事件*/
        $(".task_checkbox").click(function () {
            var id = $(this).val();
            var checked = $(this)[0].checked;//原生js，checked属性
            if (checked) {
                window.location.href = "/task/undone/" + id;
            } else {
                window.location.href = "/task/done/" + id;
            }
        });
        //删除
        $(".del_task").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除吗",function () {
                window.location.href = "/task/del/"+id;
            });
        });
    });
</script>
</body>
</html>

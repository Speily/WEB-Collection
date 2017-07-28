<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-新增客户</title>
    <%@ include file="../base/base-css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="customer_my"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">新增客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="addForm" method="post">
                        <input type="hidden" name="userId" value="${sessionScope.currentUser.id}">
                        <div class="form-group">
                            <label>机会名称</label>
                            <input type="text" class="form-control" name="name">
                        </div>
                        <div class="form-group">
                            <label>关联客户</label>
                            <select name="custId" id="" class="form-control">
                                <option value=""></option>
                                <c:forEach items="${custList}" var="cust">
                                    <option value="${cust.id}">${cust.custName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>机会价值</label>
                            <input type="text" class="form-control" name="price">
                        </div>
                        <div class="form-group">
                            <label>当前进度</label>
                            <select name="currentState" class="form-control">
                                <option value=""></option>
                                <c:forEach items="${chanceStateList}" var="state">
                                    <option value="${state}">${state}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>详细内容</label>
                            <textarea name="" class="form-control"></textarea>
                        </div>
                    </form>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button id="saveBtn" class="btn btn-primary">保存</button>
                </div>
            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>

</body>
<script>
    $(function () {

        $("#saveBtn").click(function () {
            $("#addForm").submit();
        });
    });
</script>
</html>
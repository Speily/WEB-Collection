<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <title></title>
</head>
<body>

<div class="container">
    <div class="panel panel-info ">
        <div class="panel-heading">搜索</div>
        <div class="panel-body">
            <form class="form-inline" method="get">
                <input type="text" class="form-control" name="min" placeholder="最小id" value="${min}">
                <input type="text" class="form-control" name="max" placeholder="最大id" value="${max}">
                <input type="text" class="form-control" name="userName" placeholder="姓名" value="${userName}">
                <input type="text" class="form-control" name="address" placeholder="姓名" value="${address}">
                <button class="btn btn-default"><i class="fa fa-search"></i> 搜</button>
            </form>
        </div>
    </div>
        <a href="/user/add" class="btn btn-success">新增用户</a>
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                    ${message}
            </div>
        </c:if>
    <h3>共${page.total}条数据</h3>
    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>姓名</th>
            <th>密码</th>
            <th>地址</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.userName}</td>
                <td>${user.password}</td>
                <td>${user.address}</td>
                <td>
                    <a href="/user/${user.id}/edit"><i class="fa fa-pencil"></i></a>
                    <a href="javascript:;" rel="${user.id}" class="del"><i class="fa fa-trash text-info"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <ul id="pagination-demo" class="pagination-sm"></ul>
</div>

<script src="/static/js/jquery.js"></script>
<script src="/static/layer/layer.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
        //分页
        $('#pagination-demo').twbsPagination({
            totalPages: ${page.pages},
            visiblePages: 7,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?userName=${userName}&address=${address}&min=${min}&max=${max}&p={{number}}"
        });

        //删除事件
        $(".del").click(function () {
           var $id = $(this).attr("rel")
            layer.confirm("确认删除吗？",function(){
                window.location.href = "/user/"+ $id +"/del";
            })
        });
    });

</script>
</body>
</html>
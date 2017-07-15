<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/dist/css/font-awesome.min.css">
    <title></title>
</head>
<body>

<div class="container">
        <a href="/user/add" class="btn btn-success">新增用户</a>
        <c:if test="${not empty message}">
            <div class="alert alert-success">
                    ${message}
            </div>
        </c:if>
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
        <c:forEach items="${userList}" var="user">
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
</div>
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
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
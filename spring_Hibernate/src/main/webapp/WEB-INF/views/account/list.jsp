<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
    <link rel="stylesheet" href="/static/css/bootstrap.css">
</head>
<body>
    <div class="container">

        <div class="panel panel-default">
            <div class="panel-heading">
                搜索
            </div>
            <div class="panel-body">
                <form method="get" class="form-inline">
                    <input type="text" placeholder="客户名称或地址" class="form-control" name="req_like_s_userName_or_address" value="${req_like_s_userName_or_address}">
                    <input type="text" placeholder="地址" class="form-control" name="req_like_s_address" value="${req_like_s_address}">
                    <input type="text" placeholder="所属用户ID" class="form-control" name="req_like_i_user.id" value="${requestScope['req_like_i_user.id']}">
                    <input type="text" placeholder="所属用户名" class="form-control" name="req_like_s_u.name" value="${requestScope['req_like_s_u.name']}">
                    <button class="btn btn-default">搜索</button>
                </form>
            </div>
        </div>


        <table class="table">
            <thead>
            <tr>
                <th>客户名称</th>
                <th>地址</th>
                <th>年龄</th>
                <th>所属用户</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.items}" var="account">
                    <tr>
                        <td>${account.userName}</td>
                        <td>${account.address}</td>
                        <td>${account.age}</td>
                        <td>${account.user.name}</td>
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
                totalPages: ${page.pageSize},
                visiblePages: 5,
                first:'首页',
                last:'末页',
                prev:'上一页',
                next:'下一页',
                href:"?p={{number}}&userName=${userName}&address=${address}"
                        /*?req_like_userName=lilith&req_like_address=北京*/
            });
        });
    </script>
</body>
</html>
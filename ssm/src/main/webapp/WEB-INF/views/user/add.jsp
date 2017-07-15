<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <title>新增</title>
</head>
<body>

    <form action="/user/add" method="post">
        <div class="form-group">
            <label>用户名</label>
            <input type="text" class="form-control" name="userName">
        </div>
        <div class="form-group">
            <label>密码</label>
            <input type="text" class="form-control" name="password">
        </div>
        <div class="form-group">
            <label>地址</label>
            <input type="text" class="form-control" name="address">
        </div>
        <button class="btn btn-primary">保存</button>
    </form>

</div>
</body>
</html>
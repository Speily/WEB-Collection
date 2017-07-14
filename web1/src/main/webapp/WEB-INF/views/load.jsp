<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<p>---表单提交---</p>
<form action="/save" method="post">

    <c:if test="${not empty message}">
        <h3 style="color: green;">${message}</h3>
    </c:if>
    id:<input type="text" name="id">
    UserName:<input type="text" name="name"/></br>
    Password:<input type="address" name="address"/><br/>
    Others:<input type="text" name="others"/><br/>
    <button>保存</button>
</form>
<p>---文件上传---</p>
    <form action="/upload" method="post" enctype="multipart/form-data">
        <input type="text" name="doc">
        <input type="file" name="multipartFile"/>
        <button>上传</button>
    </form>
<p>---文件下载---</p>
<img src="/static/img/girl.jpg" style="width: 500px" alt="美女">
<a href="/download?fileName=girl.jpg&downloadName=美女.jpg">下载该图片</a>
</body>
</html>

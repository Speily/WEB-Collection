<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-公司网盘</title>
    <%@ include file="../base/base-css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/webuploader/webuploader.css">
    <style>
        tr{
            height: 50px;
            line-height: 50px;
        }
        .table>tbody>tr>td{
            vertical-align: middle;
        }
        .file_icon {
            font-size: 30px;
            text-align: center;
        }
        .table>tbody>tr:hover{
            cursor: pointer;
        }
        .webuploader-container {
            display: inline-block;
        }
        .webuploader-pick {
            padding: 5px 10px;
            overflow: visible;
            font-size: 12px;
            line-height:1.5;
            font-weight: 400;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="disk"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">${disk.name}</h3>

                    <div class="box-tools pull-right">
                        <c:if test="${not empty disk}">
                            <a href="/disk/home?id=${disk.pId}" class="btn btn-default btn-sm"><i class="fa fa-arrow-left"></i> 返回上级</a>
                        </c:if>
                        <div id="picker"><i class="fa fa-upload"></i> 上传文件</div>
                        <button id="showNewFolderModal" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新建文件夹</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover" >
                        <tbody id="dataTable">
                        <c:if test="${empty diskList}">
                            <tr><td colspan="4">暂无内容</td></tr>
                        </c:if>
                        <c:forEach items="${diskList}" var="disk">
                            <tr class="tr" rel="${disk.id}" ty="${disk.type}" save="${disk.saveName}">
                                <td width="50" class="file_icon">
                                    <c:choose>
                                        <c:when test="${disk.type == 'dir'}">
                                            <i class="fa fa-folder-o"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa fa-file-o"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${disk.name}</td>
                                <td><fmt:formatDate value="${disk.updateTime}" pattern="MM月dd日"/> </td>
                                <td width="100">
                                    <c:if test="${disk.type == 'file'}">
                                        ${disk.fileSize}
                                    </c:if>
                                </td>
                                <td width="150">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                            <i class="fa fa-ellipsis-h"></i>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li class="open" rel="${disk.id}"><a href="javascript:;">打开</a></li>
                                            <li class="rename" rel="${disk.id}"><a href="javascript:;">重命名</a></li>
                                            <li class="del" rel="${disk.id}"><a href="javascript:;">删除</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
                <!-- /.box-body -->
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
<script src="/static/plugins/art-template/art-template.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/webuploader/webuploader.min.js"></script>

<%--html模板 art-template--%>
<script type="text/template" id="trTemplate">
    <%--https://aui.github.io/art-template/--%>
    <tr class="tr" rel="{{id}}" ty="{{type}}" save="{{saveName}}">
        <td width="80" class="file_icon">
            <? if(type == 'file') { ?>
            <i class="fa fa-file-o"></i>
            <? } else if(type == 'dir') { ?>
            <i class="fa fa-folder-o"></i>
            <?}?>
        </td>
        <td>{{name}}</td>
        <td>{{updateTime}}</td>
        <td width="100">{{fileSize}}</td>
        <td width="100">
            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-ellipsis-h"></i>
                </button>
                <ul class="dropdown-menu">
                    <li class="" rel="{{id}}"><a href="javascript:;">下载</a></li>
                    <li class="rename" rel="{{id}}"><a href="javascript:;">重命名</a></li>
                    <li class="del" rel="{{id}}"><a href="javascript:;">删除</a></li>
                </ul>
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {

        var pId = ${not empty requestScope.disk ? requestScope.disk.id : '0'};
        var userId = ${sessionScope.currentUser.id};


        //添加新文件夹
        $("#showNewFolderModal").click(function () {
            layer.prompt({title:"请输入文件夹名称"},function(text,index){
                layer.close(index);
                $.post("/disk/new/folder",{"pId":pId,"name":text,"userId":userId}).done(function(resp){
                    if(resp.state == 'success') {
                        layer.msg("创建成功");
                        $("#dataTable").html("");
                        for(var i = 0;i < resp.data.length;i++) {
                            var obj = resp.data[i]; //{id:1,name:'',fileSize:}
                            obj.updateTime = moment(obj.updateTime).format("MM月DD日"); //将时间戳格式化
                            var html = template("trTemplate", obj); //将JSON对象传递给模板对象，转换为HTML
                            $("#dataTable").append(html);
                        }
                    } else {
                        layer.msg(resp.message);
                    }
                }).error(function(){
                    layer.msg("服务器异常");
                });
            });
        });
        //事件代理，点击下一级
        $(document).delegate(".tr","click",function () {
            var id = $(this).attr("rel");
            var ty = $(this).attr("ty");//获取文件类型
            var fileName = $(this).attr("save");//文件保存名
            if(ty == 'dir'){
                    window.location.href = "/disk/home?id="+id;
            }else{
                    //下载
                    window.location.href = "/disk/download?id="+id;
            }
        });
        //删除
        $(document).delegate(".del","click",function (event) {
            var id = $(this).attr("rel");
            layer.confirm("确认要删除吗？",function () {
                $.get("/disk/del",{"id":id,"pId":pId}).done(function(resp){
                    if(resp.state == 'success') {
                        layer.msg("删除成功");
                        $("#dataTable").html("");
                        for(var i = 0;i < resp.data.length;i++) {
                            var obj = resp.data[i]; //{id:1,name:'',fileSize:}
                            obj.updateTime = moment(obj.updateTime).format("MM月DD日"); //将时间戳格式化
                            var html = template("trTemplate", obj); //将JSON对象传递给模板对象，转换为HTML
                            $("#dataTable").append(html);
                        }
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function(){
                    layer.msg("服务器异常");
                });
            });

            event.stopPropagation();//停止当前事件向上冒泡

        });
        //重命名
        $(document).delegate(".rename","click",function (event) {
            var id = $(this).attr("rel");
            layer.prompt(function(val, index){
                window.location.href = "/disk/rename?id="+id+"&_"+val;
                layer.msg('文件名已更改为：'+val);
                layer.close(index);
            });
            event.stopPropagation();//停止当前事件向上冒泡
        });
        //下一级
        $(document).delegate(".open","click",function () {
            var id = $(this).attr("rel");
            var ulli = $(this).attr("ulli");//事件级别标识
            if(!ulli){
                window.location.href = "/disk/home?id="+id;
            }
        });

        //文件上传
        var uploader = WebUploader.create({
            pick:"#picker",
            swf:'/static/plugins/webuploader/Uploader.swf',
            server:'/disk/upload', //上传服务器
            auto:true, //自动上传
            fileVal:'file',
            //发送请求给服务器的额外数据
            formData:{
                "pId":pId,
                "userId":userId
            }
        });

        var loadIndex = -1;
        //开始上传
        uploader.on('uploadStart',function (file) {
            loadIndex = layer.load(2);
        });
        //上传成功
        uploader.on('uploadSuccess',function (file,resp) {
            if(resp.state == 'success') {
                layer.msg("文件上传成功");
                $("#dataTable").html("");
                for(var i = 0;i < resp.data.length;i++) {
                    var obj = resp.data[i]; //{id:1,name:'',fileSize:}
                    obj.updateTime = moment(obj.updateTime).format("MM月DD日"); //将时间戳格式化
                    var html = template("trTemplate", obj); //将JSON对象传递给模板对象，转换为HTML
                    $("#dataTable").append(html);
                }
            }
        });
        //上传失败
        uploader.on('uploadError',function (file) {
            layer.msg("上传失败，服务器异常");
        });
        //无论上传成功还是失败
        uploader.on('uploadComplete',function (file) {
            layer.close(loadIndex);
        });
    });
</script>

</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户详情</title>
    <%@ include file="../base/base-css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="/static/plugins/datepicker/datepicker3.css">
    <style>
        .td_title {
            font-weight: bold;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <c:if test="${formWhere =='my'}">
        <jsp:include page="../base/base-side.jsp">
            <jsp:param name="active" value="cust_my"/>
        </jsp:include>
    </c:if>
    <c:if test="${formWhere =='public'}">
        <jsp:include page="../base/base-side.jsp">
            <jsp:param name="active" value="cust_public"/>
        </jsp:include>
    </c:if>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
                        <a href=" ${formWhere =='my' ? '/customer/my' : '/customer/public'} " class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/customer/my/${customer.id}/edit" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                        <button id="tranBtn" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</button>
                        <c:if test="${formWhere == 'my'}">
                            <button id="sharePublicBtn" rel="${customer.id}" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 放入公海</button>
                        </c:if>
                        <button id="delBtn" rel="${customer.id}" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.custName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.job}</td>
                            <td class="td_title">性别</td>
                            <td>${customer.sex}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.tel}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td class="star">${customer.level}</td>
                        </tr>
                        <c:if test="${not empty customer.address}">
                            <tr>
                                <td class="td_title">地址</td>
                                <td colspan="5">${customer.address}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty customer.mark}">
                            <tr>
                                <td class="td_title">备注</td>
                                <td colspan="5">${customer.mark}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                        创建日期：<fmt:formatDate value="${customer.createTime}" pattern="yyyy-MM-dd"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        <c:if test="${not empty customer.updateTime}">
                            最后修改日期：<fmt:formatDate value="${customer.updateTime}" pattern="yyyy-MM-dd"/>
                        </c:if>
                    </span>
                </div>
            </div>
            <%--增加跟进记录--%>
            <div class="modal fade" id="recordModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">添加跟进记录</h4>
                        </div>
                        <div class="modal-body">
                            <form action="/customer/my/new/record" id="recordForm" method="post">
                                <input type="hidden" name="custId" value="${customer.id}">
                                <textarea id="recordContent"  class="form-control" name="text"></textarea>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="saveRecordBtn">保存</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <div class="row">
                <div class="col-md-8">
                    <h4>跟进记录
                        <small><button id="showRecordModalBtn" class="btn btn-success btn-xs"><i class="fa fa-plus"></i></button></small>
                    </h4>
                    <ul class="timeline">
                        <c:if test="${empty recordList}">
                            <li>
                                <!-- timeline icon -->
                                <i class="fa fa-circle-o bg-red"></i>
                                <div class="timeline-item">
                                    <div class="timeline-body">
                                        暂无跟进记录
                                    </div>
                                </div>
                            </li>
                        </c:if>
                        <c:forEach items="${recordList}" var="record">
                            <li>
                                <!-- timeline icon -->
                                <i class="fa fa-check bg-info"></i>
                                <div class="timeline-item">
                                    <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>
                                    <div class="timeline-body">
                                            ${record.text}
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-body" style="text-align: center">
                            <img src="/customer/my/qrcode/${customer.id}" alt="">
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">待办事项</h3>
                            <div class="box-tools">
                                <button class="btn btn-sm btn-default" id="showAddTaskModal"><i class="fa fa-plus"></i></button>
                            </div>
                        </div>
                        <div class="box-body">
                            <ul class="todo-list">
                                <c:forEach items="${taskList}" var="task">
                                    <li class="${task.state==1 ? 'done' : ''}">
                                        <input type="checkbox" class="task_checkbox" value="${task.id}">
                                        <span class="text">${task.name}</span>
                                        <small class="label label-danger"><i class="fa fa-clock-o"></i> ${task.endTime}</small>
                                        <div class="tools">
                                            <i class="fa fa-edit task_checkbox"></i>
                                            <i class="fa fa-trash-o del_task"></i>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>


        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <%--模态框--%>
    <div class="modal fade" id="accountModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择转入员工</h4>
                </div>
                <div class="modal-body">
                    <select id="userId" class="form-control">
                        <option value=""></option>
                        <c:forEach items="${userList}" var="user">
                            <option value="${user.id}">
                                    ${user.userName} ( ${user.tel} )
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="tranBtnOk">转交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <%--添加新任务Modal--%>
    <div class="modal fade" id="taskModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">新增待办事项</h4>
                </div>
                <div class="modal-body">
                    <form action="/customer/my/${customer.id}/task/new" method="post" id="saveTaskForm">
                        <input type="hidden" name="userId" value="${sessionScope.currentUser.id}">
                        <input type="hidden" name="custId" value="${customer.id}">
                        <div class="form-group">
                            <label>任务名称</label>
                            <input type="text" class="form-control" name="name">
                        </div>
                        <div class="form-group">
                            <label>完成日期</label>
                            <input type="text" class="form-control" id="datepicker" name="endTime">
                        </div>
                        <div class="form-group">
                            <label>提醒时间</label>
                            <input type="text" class="form-control" id="datepicker2" name="remindTime">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="saveTaskBtn">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/static/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="/static/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script src="/static/plugins/validate/jquery.validate.js"></script>
<script>
    $(function () {

        //获取当前用户id
        var custId = ${customer.id};

        //删除客户
        $("#delBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("删除客户会自动删除相关数据，确定吗?",function(){
                window.location.href = "/customer/my/"+id+"/del";
            });
        });

        //放入公海
        $("#sharePublicBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要将该客户放入公海吗？",function () {
                window.location.href = "/customer/my/"+id+"/topublic";
            });
        });
        //转交他人
        $("#tranBtn").click(function () {
            $("#accountModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#tranBtnOk").click(function () {
            var userId = $("#userId").val();
            if(!userId) {
                layer.msg("请选择转入账号");
                return;
            }
            window.location.href = "/customer/my/"+custId+"/turnto/"+userId;
        });

        //添加跟进记录
        $("#showRecordModalBtn").click(function () {
            $("#recordModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#saveRecordBtn").click(function () {
            if(!$("#recordContent").val()) {
                layer.msg("请输入跟进内容");
                return ;
            }
            $("#recordForm").submit();
        });

        //处理时间选择
        var picker = $('#datepicker').datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true,
            startDate:moment().format("yyyy-MM-dd")
        });
        picker.on("changeDate",function (e) {
            var today = moment().format("YYYY-MM-DD");
            $('#datepicker2').datetimepicker('setStartDate',today);
            $('#datepicker2').datetimepicker('setEndDate', e.format('yyyy-mm-dd'));
        });


        var timepicker = $('#datepicker2').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true
        });

        //添加新任务
        $("#showAddTaskModal").click(function () {
            $("#taskModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#saveTaskBtn").click(function () {
            $("#saveTaskForm").submit();
        });
        $("#saveTaskForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                title:{
                    required:true
                },
                finishTime:{
                    required:true
                }
            },
            messages:{
                title:{
                    required:"请输入任务内容"
                },
                finishTime:{
                    required:"请选择完成时间"
                }
            }
        });

        /*待办事项选中事件*/
        $(".task_checkbox").click(function () {
            var id = $(this).val();
            var checked = $(this)[0].checked;//原生js，checked属性
            if (checked) {
                window.location.href = "/task/undone/" + id;
            } else {
                window.location.href = "/task/done/" + id;
            }
        });
        //待办事项删除
        $(".del_task").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除吗",function () {
                window.location.href = "/task/del/"+id;
            });
        });
    })
</script>

</body>
</html>

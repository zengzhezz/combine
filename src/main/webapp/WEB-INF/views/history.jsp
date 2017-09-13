<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
<head>
    <base href="<%=basePath%>">
    <title>历史记录查询</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/statics/css/restcss.css">
    <link rel="stylesheet" type="text/css"
          href="<%=path %>/statics/plugins/huatiao/css/style.css" />
    <style type="text/css">
        #header {
            height: 50px;
            line-height: 50px;
            background: #edf6fa;
        }
        #header .title{
            text-align: center;
            font-size: 1.5em;
            letter-spacing: .2em;
            font-weight: 400;
            margin-left: 60px;
            display: inline-block;
        }
        #menu {
            float: right;
            display: inline;
            padding-right: 30px;
        }
        a , a:link {
            text-decoration: underline;
            color: #431385;
        }
        #search {
            height: 50px;
            line-height: 50px;
        }
        #search div input[type="text"]{
            border: 1px solid #d4d4d4;
            padding: 6px 0 6px 4px;
            box-sizing: border-box;
        }
        #search div select{
            width: 120px;
        }
        #search div {
            float: left;
            margin-left: 6px;
        }
        #search div input[type="button"] {
            padding: 4px 16px;
            border-radius: 4px;
            background: #379b8c;
            color: #fff;
            cursor: pointer;
        }
        #search div input[type="button"]:hover {
            background: #268878;
        }
        .message {
            width: 100%
        }
        .message table {
            width: inherit;
            border-collapse:collapse;
            border: 2px solid #000;
            text-align: center;
        }
        .message table .first td{
            border-bottom: 2px solid #888;
            border-right: 1px dashed #000;
            width: 20%;
            height: 40px;
            font-size: 1.1em;
            font-weight: 500;
        }
        .message table th {
            border-bottom: 2px solid #888;
            border-right: 1px dashed #000;
            width: 20%;
            height: 40px;
            font-size: 1.0em;
            font-weight: 400;
        }
        .message table .first {
            background: rgb(62, 203, 148);
        }
        button {
            padding: 2px;
        }
    </style>
</head>
<body>
<div id="header">
    <div class='title'>
        历史记录查询
    </div>
    <div id="menu">
        <a href="index">演示页面</a>
    </div>
</div>
<div id="search">
    <div style="margin-left:10px;">
        始时间:
        <input type="text" id="start"
               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" />
    </div>
    <div>
        末时间:
        <input type="text" id="end"
               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" />
    </div>
    <div style="margin-left: 20px; padding-right: 10px;">
        uuid :
        <select id="uuid" style="margin-right: 10px;">
            <c:forEach var="item" items="${labels }">
                <option value="${item.uuid }" name="${item.uuidName }">${item.uuid } (${item.uuidName })</option>
            </c:forEach>
        </select>
        <button id="search_by_uuid">按uuid查询</button>
    </div>
    <div style="margin-left: 10px;">
        mac :
        <select id="mac" style="margin-right: 10px;">
            <c:forEach var="item" items="${nodes }">
                <option value="${item.mac }" name="${item.name }">${item.mac } (${item.name })</option>
            </c:forEach>
        </select>
        <button id="search_by_mac">按mac查询</button>
    </div>
</div>
<div class="message">
    <table>
        <tr class='first'>
            <td>时间</td>
            <td>uuid</td>
            <td>姓名</td>
            <td>事件</td>
            <td>地点</td>
        </tr>
        <tbody>
        <c:forEach var="item" items="${inOutData }">
            <tr>
                <th style="display:none;" class='item_id'>${item.id }</th>
                <th class="">${item.lastUpdateTime }</th>
                <th class="">${item.uuid }</th>
                <th class="">${item.uuidName }</th>
                <th class="">${item.state }</th>
                <th class="">${item.macName }</th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="<%=path %>/statics/js/jquery-3.1.1.min.js" charset="utf-8"></script>
<script type="text/javascript"
        src="<%=path %>/statics/plugins/date/WdatePicker.js"></script>
<script src="<%=path %>/statics/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
        src="<%=path %>/statics/plugins/huatiao/js/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript"
        src="<%=path %>/statics/plugins/huatiao/js/jQuery.peSlider.js"></script>
<script>
    $("#search_by_uuid").click(function(){
        var start = $("#start").val();
        var end = $("#end").val();
        var uuid = $("#uuid").val();
        if (start == null || start == '' || end == null || end == '') {
            alert("请选择时间段");
            return;
        }
        window.location.href = "<%=path %>/inout/getdatabyuuid/?uuid="+uuid+"&start="+start+"&end="+end;
    });
    $("#search_by_mac").click(function(){
        var start = $("#start").val();
        var end = $("#end").val();
        var mac = $("#mac").val();
        if (start == null || start == '' || end == null || end == '') {
            alert("请选择时间段");
            return;
        }
        window.location.href = "<%=path %>/inout/getdatabymac/?mac="+mac+"&start="+start+"&end="+end;
    });
</script>
</body>
</html>
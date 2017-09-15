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
    <title>注册页面</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/statics/css/restcss.css">
    <style type="text/css">
        *{
            margin: 0;
            padding: 0;
        }
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
        }
        a , a:link {
            text-decoration: underline;
            color: #431385;
        }
        input[type="text"]{
            border: 1px solid #d4d4d4;
            padding: 6px 0 6px 4px;
            box-sizing: border-box;
        }
        #register_mac {
            border-top: 1px solid #000;
            border-bottom: 1px solid #000;
        }
        #register_location_mac {
            border-top: 1px solid #000;
            border-bottom: 1px solid #000;
        }
        #register_label {
            border-top: 1px solid #000;
            border-bottom: 1px solid #000;
        }
        .show_node, .show_location_node {
            width: 100%
        }
        table {
            width: inherit;
            border-collapse:collapse;
            border: 2px solid #000;
            text-align: center;
        }
        table td , th {
            border-bottom: 2px solid #888;
            border-right: 1px dashed #000;
            width: 20%;
            height: 40px;
            font-size: 1.1em;
        }
        table .first {
            background: rgb(143, 203, 159);
        }
        button {
            padding: 2px;
        }
        #menu {
            float: right;
            display: inline;
            padding-right: 30px;
        }
    </style>
</head>
<body>
<div id="header">
    <div class='title' style="display: inline">
        注册页面
    </div>
    <div id="menu">
        <a href="index">演示页面</a>
        <a href="config" style="margin-left: 10px;">配置页面</a>
    </div>
</div>
<div id="register_mac">
    <div class="menu" style="margin: 10px 20px;">
        <div class="hint" style="margin-bottom: 5px;">注册区域检测节点: <button id="hide_mac_table" style="margin-left: 10px; width: 70px;">隐藏表格</button></div>
        mac : <input type="text" name="" id="mac" style="margin-right: 20px;">
        名称 : <input type="text" name="" id="mac_name" style="margin-right: 20px;">
        <button id = "add_mac">添加</button>
    </div>
    <div class="show_node">
        <table>
            <tr class="first">
                <td>mac</td>
                <td>名称</td>
                <td>操作</td>
            </tr>
            <tbody>
            <c:forEach var="item" items="${nodes }">
                <tr>
                    <th style="display:none;" class='item_id'>${item.id }</th>
                    <th class="">${item.mac }</th>
                    <th class="">${item.name }</th>
                    <th class=""><button class="delete_node">删除</button></th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div id="register_location_mac">
    <div class="menu" style="margin: 10px 20px;">
        <div class="hint" style="margin-bottom: 5px;">注册人员定位节点: <button id="hide_location_mac_table" style="margin-left: 10px; width: 70px;">隐藏表格</button></div>
        mac : <input type="text" name="" id="location_mac" style="margin-right: 20px;">
        名称 : <input type="text" name="" id="location_mac_name" style="margin-right: 20px;">
        <button id = "add_location_mac">添加</button>
    </div>
    <div class="show_location_node">
        <table>
            <tr class="first">
                <td>mac</td>
                <td>名称</td>
                <td>操作</td>
            </tr>
            <tbody>
            <c:forEach var="item" items="${locationNodes }">
                <tr>
                    <th style="display:none;" class='item_id'>${item.id }</th>
                    <th class="">${item.mac }</th>
                    <th class="">${item.name }</th>
                    <th class=""><button class="delete_location_node">删除</button></th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div id="register_label">
    <div class="menu" style="margin: 10px 20px;">
        <div class="hint" style="margin-bottom: 5px;">注册标签: <button id="hide_label_table" style="margin-left: 10px; width: 70px;">隐藏表格</button></div>
        uuid : <input type="text" name="" id="label_uuid" style="margin-right: 20px;">
        名字 : <input type="text" name="" id="label_name" style="margin-right: 20px;">
        标签类型:	<select name="type" id="type">

                         <option value="0">老人</option>
                         <option value="1">护工</option>
                         <option value="2">保安</option>

                    </select>
        <button id = "add_label">添加</button>
    </div>
    <div class="show_label" style="width:100%;">
        <table>
            <tr class="first">
                <td>mac</td>
                <td>名称</td>
                <td>标签类型(0老人,1护工,2保安)</td>
                <td>操作</td>
            </tr>
            <tbody>
            <c:forEach var="item" items="${labels }">
                <tr>
                    <th style="display:none;" class='item_id'>${item.id }</th>
                    <th class="">${item.uuid }</th>
                    <th class="">${item.uuidName }</th>
                    <th class="">

                            ${item.labelType }</th>
                    <th class=""><button class="delete_label">删除</button></th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="<%=path %>/statics/js/jquery-3.1.1.min.js" charset="utf-8"></script>
<script>
    var image_type1 = ["oldman","nurse","people"];
    $("#hide_mac_table").click(function(){
        $('.show_node').fadeToggle(0);
        if($('.show_node').is(":hidden")){
            $("#hide_mac_table").html("显示表格");
        }else{
            $("#hide_mac_table").html("隐藏表格");
        }
    });
    $("#hide_location_mac_table").click(function(){
        $('.show_location_node').fadeToggle(0);
        if($('.show_location_node').is(":hidden")){
            $("#hide_location_mac_table").html("显示表格");
        }else{
            $("#hide_location_mac_table").html("隐藏表格");
        }
    });
    $("#hide_label_table").click(function(){
        $('.show_label').fadeToggle(0);
        if($('.show_label').is(":hidden")){
            $("#hide_label_table").html("显示表格");
        }else{
            $("#hide_label_table").html("隐藏表格");
        }
    });
    $("#add_mac").click(function(){
        var mac = $("#mac").val().toLowerCase();
        var macName = $("#mac_name").val();
        if(mac=="" || macName==""){
            alert("mac或名字不能为空")
            return;
        }
        $.ajax({
            type : "post",// 请求方式
            url : "<%=basePath%>register/addnode", // 发送请求地址
            dataType : "json",
            data : {// 发送给数据库的数据
                "mac" : mac,
                "macName": macName
            },
            // 请求成功后的回调函数有两个参数
            success : function(data, textStatus) {
                if(data.code == 0){
                    window.location.href = "register";
                }else{
                    alert("保存失败，该节点已经存在数据库中！");
                }
            }
        });
    });
    $("#add_location_mac").click(function(){
        var mac = $("#location_mac").val().toLowerCase();
        var macName = $("#location_mac_name").val();
        if(mac=="" || macName==""){
            alert("mac或名字不能为空")
            return;
        }
        $.ajax({
            type : "post",// 请求方式
            url : "<%=basePath%>register/addlocationnode", // 发送请求地址
            dataType : "json",
            data : {// 发送给数据库的数据
                "mac" : mac,
                "macName": macName
            },
            // 请求成功后的回调函数有两个参数
            success : function(data, textStatus) {
                if(data.code == 0){
                    window.location.href = "register";
                }else{
                    alert("保存失败，该节点已经存在数据库中！");
                }
            }
        });
    });
    $("#add_label").click(function(){
        var uuid = $("#label_uuid").val().toLowerCase();
        var uuidName = $("#label_name").val();
        var type = $("#type").val();
        if(uuid=="" || uuidName==""){
            alert("uuid或名字不能为空");
            return;
        }
        $.ajax({
            type : "post",// 请求方式
            url : "<%=basePath%>register/addlabel", // 发送请求地址
            dataType : "json",
            data : {// 发送给数据库的数据
                "uuid" : uuid,
                "uuidName": uuidName,
                "type" : type

            },
            // 请求成功后的回调函数有两个参数
            success : function(data, textStatus) {
                if(data.code == 0){
                    window.location.href = "register";
                }else{
                    alert("保存失败，该uuid已经存在数据库中！");
                }
            }
        });
    });
    $(".delete_node").click(function(){
        var id = $(this).parent().parent().children(".item_id").text();
        $.ajax({
            type : "post",// 请求方式
            url : "<%=basePath%>register/deletenode", // 发送请求地址
            dataType : "json",
            data : {// 发送给数据库的数据
                "id" : id
            },
            // 请求成功后的回调函数有两个参数
            success : function(data, textStatus) {
                if(data.code == 0){
                    window.location.href = "register";
                }else{
                    alert("删除节点失败！");
                }
            }
        });
    });
    $(".delete_location_node").click(function(){
        var id = $(this).parent().parent().children(".item_id").text();
        $.ajax({
            type : "post",// 请求方式
            url : "<%=basePath%>register/deletelocationnode", // 发送请求地址
            dataType : "json",
            data : {// 发送给数据库的数据
                "id" : id
            },
            // 请求成功后的回调函数有两个参数
            success : function(data, textStatus) {
                if(data.code == 0){
                    window.location.href = "register";
                }else{
                    alert("删除节点失败！");
                }
            }
        });
    });
    $(".delete_label").click(function(){
        var id = $(this).parent().parent().children(".item_id").text();
        $.ajax({
            type : "post",// 请求方式
            url : "<%=basePath%>register/deletelabel", // 发送请求地址
            dataType : "json",
            data : {// 发送给数据库的数据
                "id" : id
            },
            // 请求成功后的回调函数有两个参数
            success : function(data, textStatus) {
                if(data.code == 0){
                    window.location.href = "register";
                }else{
                    alert("删除人员失败！");
                }
            }
        });
    });
</script>
</body>
</html>


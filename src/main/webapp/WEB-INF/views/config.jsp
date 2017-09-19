<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>配置页面</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/statics/css/restcss.css">
    <!--2017-9-17改byGuwei-begin-->
    <link rel="stylesheet" href="<%=path%>/statics/css/mystyle.css">
    <link rel="stylesheet" href="<%=path%>/statics/css/font-awesome-4.7.0/css/font-awesome.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/statics/css/turnpage/paging.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/statics/css/media-queries.css"/>
    <script src="<%=path%>/statics/js/jquery-1.7.2.min.js"></script>
    <script src="<%=path%>/statics/js/myjs.js"></script>
    <script src="<%=path%>/statics/date/time.js"></script>
    <!--2017-9-17改byGuwei-end-->
    <style type="text/css">
        *{
            margin: 0;
            padding: 0;
        }
        #header {
            height: 50px;
            line-height: 50px;
            background: #64a5bab3;
        }
        #header .title{
            text-align: center;
            font-size: 1.5em;
            letter-spacing: .2em;
            font-weight: 400;
            margin-left: 60px;
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

        .config_msg{
            padding: 0px 0px;
            line-height: 40px;
            padding-bottom: 10px;
        }
        button {
            padding: 2px;
        }
        .scene{
            width: 88%;
        }
        img {
            width: inherit;
            user-select: none;
        }
        .node {
            width: 30px;
            height: 30px;
            border-radius: 5px;
            position: absolute;
            background: #FFF;
            cursor: pointer;
        }
        /*.node:hover .popwindow {
          display: block;
        }*/
        .node .node_icon {
            width: inherit;
            height: inherit;
            background: url(<%=path %>/statics/imgs/location_icon.png) no-repeat 2px 0px;
            background-size: contain;
        }
        .node .popwindow {
            width: 60px;
            position: absolute;
            top: 31px;
            left: -15px;
            font-size: .6em;
            text-align: center;
            color: #FFF;
        }
        .node .popbox {
            background: rgb(221, 51, 90);
            color: #FFF;
            border-radius: 2px;
        }
        .node .popwindow p {
            margin: 0px;
            display: block;
            padding: 5px;
        }
        .node .triangle {
            width: 0;
            height: 0;
            border-left: 7px solid transparent;
            border-right: 7px solid transparent;
            border-bottom: 5px solid rgb(221, 51, 90);
            margin: 0 auto;
        }
        .location_node {
            width: 14px;
            height: 14px;
            border-radius: 7px;
            position: absolute;
            background: #ff4510;
            cursor: pointer;
        }
        .location_node .popwindow {
            width: 50px;
            position: absolute;
            top: 14px;
            left: -17px;
            font-size: .6em;
            text-align: center;
            color: #FFF;
        }
        .location_node .popbox {
            background: rgb(221, 51, 90);
            color: #FFF;
            border-radius: 2px;
        }
        .location_node .popwindow p {
            margin: 0px;
            display: block;
            padding: 5px;
        }
        .location_node .triangle {
            width: 0;
            height: 0;
            border-left: 7px solid transparent;
            border-right: 7px solid transparent;
            border-bottom: 5px solid rgb(221, 51, 90);
            margin: 0 auto;
        }
    </style>
</head>
<body>
<div id="header" style="background: #64a5bab3;">
    <div class='title' style="display: inline">
        配置页面
    </div>
    <div id="menu">
        <a href="index">演示页面</a>
        <a href="register" style="margin-left: 10px;">注册页面</a>
    </div>
</div>

<!--2017-9-17改byGuwei-begin-->
<div id="sidebar" style="width:12%;top: 50px;">
    <ul class="menu"  style="width:100%;">
        <li class="first"><span>主菜单</span><i></i></li>
        <li><a href="index"> <i class="fa fa-address-book-o "></i><span>区间检测</span></a></li>
        <li><a href="register"> <i class="fa fa-book"></i><span>注册页面</span></a></li>
        <li class="current"><a href="config"><i class="fa fa-calendar-check-o"></i><span>配置页面</span></a></li>
        <li><a href="history"><i class="fa fa-bar-chart"></i><span>历史轨迹</span></a></li>


    </ul>

</div>
<!--2017-9-17改byGuwei-end-->

<div id="scene" class="scene" style="width: 88%; position:relative; left:12%;top:50px;">
    <img style="width: 100%;" src="<%=path %>/statics/imgs/background3.jpg" alt="">
</div>
<div class="config_msg" style="width: 88%; position:relative; left:12%;top:35px;">
    <div style="border-bottom: 1px dashed black;">在页面上添加节点:</div>
    <em>选择要添加的区域检测节点：</em>
    <select id="nodes" style="margin-left: 20px;">
        <c:forEach var="item" items="${nodes }">
            <option value="${item.mac }" name="${item.name }">${item.mac } (${item.name })</option>
        </c:forEach>
    </select>
    <button type="button" name="button" class="add_node_btn" style="margin-left: 20px;">添加</button>
    <em>选择要添加的人员定位节点：</em>
    <select id="locationNodes" style="margin-left: 20px;">
        <c:forEach var="item" items="${locationNodes }">
            <option value="${item.mac }" name="${item.name }">${item.mac } (${item.name })</option>
        </c:forEach>
    </select>
    <button type="button" name="button" class="add_location_node_btn" style="margin-left: 20px;">添加</button>
    <button type="button" name="button" class="save_node_btn" style="margin-left: 10px;">保存位置</button>
</div>
<script src="<%=path %>/statics/js/jquery-3.1.1.min.js" charset="utf-8"></script>
<script>
    // 图片的长宽和比例
    var image_width;
    var image_height;
    var image_ratio = 0.4884;
    $(function(){
        //清除scenebox默认的右击事件
        document.getElementById("scene").oncontextmenu = function(event){
            event.returnValue = false;
        };
        //得到图片的宽高
        image_width = document.body.clientWidth*0.88;
        image_height = image_width * image_ratio;
        getAllNode();
        getAllLocationNode();
        $('.save_node_btn').click(function(){
            saveNode();
        });
        $('.add_node_btn').click(function(){
            var mac = $("#nodes").val(),
                name = $("#nodes").find("option:selected").attr("name");
            var $nodes = $(".node");
            var flag = 0;
            $.each($nodes,function(index, item){
                if($(this).attr('id')==mac){
                    alert("此mac已注册!");
                    flag = 1;
                    return false;
                }
            });
            if(flag == 0) {
                addNodeByMsg(mac, name, 300, 300);
            }
        });
        $('.add_location_node_btn').click(function(){
            var mac = $("#locationNodes").val(),
                name = $("#locationNodes").find("option:selected").attr("name");
            var $nodes = $(".location_node");
            var flag = 0;
            $.each($nodes,function(index, item){
                if($(this).attr('id')==mac){
                    alert("此mac已注册!");
                    flag = 1;
                    return false;
                }
            });
            if(flag == 0) {
                addLocationNodeByMsg(mac, name, 300, 300);
            }
        });
    });
    /**
     * 得到所有的节点
     */
    function getAllNode(){
        $.ajax({
            type:"post",//请求方式
            url: "node/getallnode",//发送请求地址
            dataType:"json",
            data:{//发送给数据库的数据
            },
            //请求成功后的回调函数有两个参数
            success:function(data,textStatus){
                $.each(data, function(index,item){
                    var mac = data[index].mac,
                        name = data[index].name,
                        nodeTop = data[index].nodeTop * image_height + 50,
                        nodeLeft = data[index].nodeLeft * image_width+image_width*0.12;
                    addNodeByMsg(mac, name, nodeTop, nodeLeft);
                });
            }
        });
    }
    /**
     * 得到所有的节点
     */
    function getAllLocationNode(){
        $.ajax({
            type:"post",//请求方式
            url: "node/getalllocationnode",//发送请求地址
            dataType:"json",
            data:{//发送给数据库的数据
            },
            //请求成功后的回调函数有两个参数
            success:function(data,textStatus){
                $.each(data, function(index,item){
                    var mac = data[index].mac,
                        name = data[index].name,
                        nodeTop = data[index].nodeTop * image_height + 50,
                        nodeLeft = data[index].nodeLeft * image_width+image_width*0.12;
                    addLocationNodeByMsg(mac, name, nodeTop, nodeLeft);
                });
            }
        });
    }
    /**
     * 在页面上添加一个节点
     * @param mac
     * @param name
     * @param x
     * @param y
     */
    function addNodeByMsg(mac,name,nodeTop,nodeLeft){

        console.log(mac + ", " + name);
        var node = document.createElement("div"),
            img = document.createElement("div");
        img.setAttribute("class", "node_icon");
        node.setAttribute("id", mac);
        node.setAttribute("class","node");
        node.setAttribute("name",name);
        node.appendChild(img);
        node.appendChild(createpopwindow(name));
        node.onmousedown = fnDown;
        //设置出现的初始位置
        node.style.top = nodeTop + 'px';
        node.style.left = nodeLeft + 'px';
        document.body.appendChild(node);
    }
    // 节点的名称显示框
    function createpopwindow(name){
        var popwindow = document.createElement("div"),
            triangle = document.createElement("div"),
            popbox = document.createElement("div"),
            p = document.createElement("p");
        p.textContent = name;
        triangle.setAttribute("class", "triangle");
        popbox.setAttribute("class", "popbox");
        popwindow.setAttribute("class", "popwindow");
        popwindow.appendChild(triangle);
        popbox.appendChild(p);
        popwindow.appendChild(popbox);
        return popwindow;
    }
    /**
     * 在页面上添加一个节点
     * @param mac
     * @param name
     * @param x
     * @param y
     */
    function addLocationNodeByMsg(mac,name,nodeTop,nodeLeft){
        var node = document.createElement("div");
        node.setAttribute("id", mac);
        node.setAttribute("class","location_node");
        node.setAttribute("name",name);
        node.appendChild(createLocationPopwindow(name));
        node.onmousedown = fnDown;
        //设置出现的初始位置
        node.style.top = nodeTop + 'px';
        node.style.left = nodeLeft + 'px';
        document.body.appendChild(node);
    }
    // 节点的名称显示框
    function createLocationPopwindow(name){
        var popwindow = document.createElement("div"),
            triangle = document.createElement("div"),
            popbox = document.createElement("div"),
            p = document.createElement("p");
        p.textContent = name;
        triangle.setAttribute("class", "triangle");
        popbox.setAttribute("class", "popbox");
        popwindow.setAttribute("class", "popwindow");
        popwindow.appendChild(triangle);
        popbox.appendChild(p);
        popwindow.appendChild(popbox);
        return popwindow;
    }
    // 在页面上保存节点
    function saveNode(){
        var jsonstr="[]";
        var jsonarray = eval('('+jsonstr+')');
        var jsonarray2 = eval('('+jsonstr+')');
        var $nodes = $(".node");
        var $location_nodes = $(".location_node");
        for (var node of $nodes){
            var arr = {
                mac: $(node).attr("id"),
                name: $(node).attr("name"),
                nodeTop: (((node.style.top).replace("px","") - 50)/image_height).toFixed(4),
                nodeLeft: (((node.style.left).replace("px","")-image_width*0.12)/image_width).toFixed(4)
            }
            jsonarray.push(arr);
        }
        $.ajax({
            type:"post",//请求方式
            url: "node/savenode",//发送请求地址
            dataType:"json",
            data: JSON.stringify(jsonarray),
            //请求成功后的回调函数有两个参数
            success:function(data,textStatus){
                //如果添加成功
                if(data.code==0){

                }
            }
        });
        for (var node of $location_nodes){
            var arr = {
                mac: $(node).attr("id"),
                name: $(node).attr("name"),
                nodeTop: (((node.style.top).replace("px","") - 50)/image_height).toFixed(4),
                nodeLeft: (((node.style.left).replace("px","")-image_width*0.12)/image_width).toFixed(4)
            }
            jsonarray2.push(arr);
        }
        $.ajax({
            type:"post",//请求方式
            url: "node/savelocationnode",//发送请求地址
            dataType:"json",
            data: JSON.stringify(jsonarray2),
            //请求成功后的回调函数有两个参数
            success:function(data,textStatus){
                //如果添加成功
                if(data.code==0){

                }
            }
        });
    }

    // 页面节点的拖动
    var key = false;
    var firstTime = 0;
    var lastTime = 0
    //鼠标按下时的操作
    function fnDown(event){
        var node = this;
        //如果鼠标左键按下
        if(event.button == 0){
            firstTime = new Date().getTime();
            var id = this.id,
                // 光标按下时光标和面板之间的距离
                disX=event.clientX-this.offsetLeft,
                disY=event.clientY-this.offsetTop;
            document.onmousemove = function(event){
                fnMove(node,event,disX,disY,id);
            };
            document.onmouseup = function(){
                document.onmousemove = null;
                document.onmouseup = null;
            };
            node.onmouseup = function(){
                lastTime = new Date().getTime();
                node.onmouseup = null;
                if(lastTime - firstTime < 200){
                    //如果时间差小于200ms，执行点击事件
                    key = true;
                }
            }
        }
        //如果鼠标右键按下，删除此node
        else if(event.button == 2){
            if(confirm("是否删除此节点？")){
                if($(node).attr("class")=="node"){
                    //在页面上删除此节点
                    node.parentNode.removeChild(node);
                    $.ajax({
                        type:"post",//请求方式
                        url: "node/deletenode.do",//发送请求地址
                        dataType:"json",
                        data:{//发送给数据库的数据
                            mac:$(node).attr("id")
                        },
                        //请求成功后的回调函数有两个参数
                        success:function(data,textStatus){
                            if(data.code==1){
                                console.log(data.msg);
                            }
                        }
                    });
                }else if($(node).attr("class")=="location_node"){
                    //在页面上删除此节点
                    node.parentNode.removeChild(node);
                    $.ajax({
                        type:"post",//请求方式
                        url: "node/deletelocationnode.do",//发送请求地址
                        dataType:"json",
                        data:{//发送给数据库的数据
                            mac:$(node).attr("id")
                        },
                        //请求成功后的回调函数有两个参数
                        success:function(data,textStatus){
                            if(data.code==1){
                                console.log(data.msg);
                            }
                        }
                    });
                }
            }
            else{
                this.oncontextmenu = function(evt){
                    //清除默认的右键点击菜单
                    evt.returnValue = false;
                }
            }
        }
    }
    function fnMove(node,event,posX,posY,id){
        var oDrag =node,
            left = event.clientX - posX,
            top = event.clientY - posY;
        oDrag.style.left = left + 'px';
        oDrag.style.top = top + 'px';
    }
</script>
</body>
</html>
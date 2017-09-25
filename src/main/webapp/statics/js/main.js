//获取项目的url根地址
var localObj = window.location;
var contextPath = localObj.pathname.split("/")[1];
var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
var server_context = basePath;
var socket_context = "ws://"+localObj.host + "/" + contextPath;

var image_width;
var image_height;
// 定义图片比例
var image_ratio = 0.4884;
var image_type1 = ["oldman","nurse","people"];
//服务器地址
var socket = new WebSocket(socket_context + '/websocket');

$(function() {
    // 得到图片的宽高
    image_width = document.body.clientWidth*0.88;
    image_height = image_width * image_ratio;

    // 从服务器获取所有节点并显示
    getAllNode();
    getAllLocationNode();
    // 从服务器得到所有节点下的人员信息
    getAllLabel();
    // 从服务器得到最新的5条流水信息显示
    getAllFlowMsg();
    $('#show_node').click(function(){
        if($('.node').is(':hidden')){
            $('.node').show();
            $('#show_node').val('隐藏节点');
        }else{
            $('.node').hide();
            $('#show_node').val('显示节点');
        }
    })
    $('#show_location_node').click(function(){
        if($('.location_node').is(':hidden')){
            $('.location_node').show();
            $('#show_location_node').val('隐藏节点');
        }else{
            $('.location_node').hide();
            $('#show_location_node').val('显示节点');
        }
    })
    $("#show_log").click(function(){
        if($('.flowmsg').is(':hidden')){
            $('.flowmsg').show();
            $('#show_log').val('隐藏日志');
        }else{
            $('.flowmsg').hide();
            $('#show_log').val('显示日志');
        }
    })
    $('#show_oldman').click(function(){
        if($(this).is(':checked')){
            $('.label_man').show();
        }else{
            $('.label_man').hide();
        }
    })
    $('#show_people').click(function(){
        if($(this).is(':checked')){
            $('.label_car').show();
        }else{
            $('.label_car').hide();
        }
    })
    $('#show_nurse').click(function(){
        if($(this).is(':checked')){
            $('.label_dog').show();
        }else{
            $('.label_dog').hide();
        }
    })
   /* $('#show_thief').click(function(){
        if($(this).is(':checked')){
            $('.label_thief').show();
        }else{
            $('.label_thief').hide();
        }
    })
    $('#show_bike').click(function(){
        if($(this).is(':checked')){
            $('.label_bike').show();
        }else{
            $('.label_bike').hide();
        }
    })*/
  /*  $('.').click(function () {

    })*/
    // 在关闭网页时，把此时页面的node相关的信息保存进数据库
    window.onbeforeunload = function() {
        // 关闭socket
        socket.close();
    }
});

/**
 * 在页面上添加节点图标
 * @param mac
 * @param name
 * @param nodeTop
 * @param nodeLeft
 */
function addNodeByMsg(mac, name, nodeTop, nodeLeft) {
    var node = document.createElement("div"), img = document
        .createElement("div"), corner = document.createElement("div"), cornertext = document
        .createElement("p");
    img.setAttribute("class", "node_icon");
    cornertext.setAttribute("class", "cornertext");
    cornertext.textContent = "0";
    node.setAttribute("id", mac);
    node.setAttribute("class", "node");
    node.setAttribute("name", name);
    node.appendChild(img);
    node.appendChild(corner);
    corner.appendChild(cornertext);
    corner.setAttribute("class", "corner");
    // 设置出现的初始位置
    node.style.top = nodeTop + 'px';
    node.style.left = nodeLeft + 'px';
    document.body.appendChild(node);
    // 在node后添加一个list
    $(node).append("<div class='poplist' style='display:none'><div class='triangle'></div><div class='listContent'><ul class='list-group'><p>地点:"
        + name + "</p></ul></div></div>");
    // 在node后添加一个气泡提示
    $(node).append("<em><div class='trai'></div><p>张晓军进入</p></em>");
    $("#" + mac).click(function() {
        // 获取listContent的jquery对象
        var $list = $(this).children(".poplist");
        // 判断List是否已经显示，若显示则隐藏，否则显示
        if ($list.is(':hidden')) {
            $list.fadeIn();
        } else {
            $list.fadeOut();
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
function addLocationNodeByMsg(mac,name,nodeTop,nodeLeft){
    var node = document.createElement("div");
    node.setAttribute("id", mac);
    node.setAttribute("class","location_node");
    node.setAttribute("name",name);
    //设置出现的初始位置
    node.style.top = nodeTop + 'px';
    node.style.left = nodeLeft + 'px';
    document.body.appendChild(node);
}

/**
 * 得到所有的节点
 */
function getAllNode() {
    $.ajax({
        type : "post",// 请求方式
        url : server_context + "/node/getallnode",// 发送请求地址
        dataType : "json",
        data : {// 发送给数据库的数据
        },
        // 请求成功后的回调函数有两个参数
        success : function(data, textStatus) {
            $.each(data,function(index, item) {
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
        url: server_context + "/node/getalllocationnode",//发送请求地址
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
 * 得到所有节点的人员信息
 */
function getAllLabel(){
    $.ajax({
        type : "post",// 请求方式
        url : server_context + "/label/getallnodelabel",// 发送请求地址
        dataType : "json",
        data : {// 发送给数据库的数据
        },
        // 请求成功后的回调函数有两个参数
        success : function(data, textStatus) {
            $.each(data,function(index, item) {
                var mac = data[index].mac,
                    number = data[index].number,
                    uuidNameString = data[index].uuidNameString;
                var uuidNameGroup = uuidNameString.split(",");
                setNodePerson(mac,number,uuidNameGroup);
               // addLabel(uuid, uuidName, labelType, description, left, top)
            });
        }
    });
}

/**
 * 得到最近的5条流水信息
 */
function getAllFlowMsg(){
    $.ajax({
        type : "post",// 请求方式
        url : server_context + "/inout/get5inoutdata",// 发送请求地址
        dataType : "json",
        data : {// 发送给数据库的数据
        },
        // 请求成功后的回调函数有两个参数
        success : function(data, textStatus) {
            if(data !== null && data !== undefined && data !== ''){
                $.each(data,function(index, item) {
                    var time = data[index].lastUpdateTime,
                        uuid = data[index].uuid,
                        uuidName = data[index].uuidName,
                        event = data[index].state,
                        mac = data[index].mac,
                        macName = data[index].macName;
                    setFlowMsg(time,uuid,uuidName,event,mac,macName,false);
                });
            }
        }
    });
}

socket.onopen = function(event) {
    socket.send("I am a client and I am listening!");
}

socket.onmessage = function(event) {
    var result = event.data;
    console.log("socket message:" + result);
    var group = result.split(",");
    if (group[0] == "label_msg") {
        var uuidGroup = group.concat();
        uuidGroup.splice(0,3);
        setNodePerson(group[1], group[2], uuidGroup);
    } else if (group[0] == "flow_msg") {
        //setFlowMsg(group[1],group[2],group[3],group[4],group[5],group[6],true);
    } else if(group[0] == "location"){
        // 删除此人
        $('.label_' + group[1]).remove();
        addLabel(group[1], group[2], group[3], group[4],group[5], group[6]);
       // addLabel(uuid, uuidName, labelType, description, left, top)
    }else if(group[0] == "delete_all"){
        $('.label').remove();
    }
}

/**
 * 添加显示的人员
 *
 * @param uuid
 * @param uuidName
 * @param left
 * @param top
 */
/*
function addLabel(uuid, uuidName, left, top) {
    $('body').append("<div class='label label_"+uuid+"'><img width='20px' src='"+ server_context
        + "/statics/imgs/location_man.png'></img><div class='label_msg'><div class='tria'></div><div class='label_p'><p>姓名:"
        + uuidName + "</p></div></div></div>");
    $('.label_' + uuid).css("position", "absolute");
    $('.label_' + uuid).css("left", (image_width * left) + "px");
    $('.label_' + uuid).css("top", (image_height * top + 50) + "px");
}
*/
function addLabel(uuid, uuidName, labelType, description, left, top) {
        //alert("add");
        var image_string = image_type1[parseInt(labelType)];
        $('body').append("<div class='label label_"+uuid+ " label_"+image_string+"'><img width='20px' src='"+ server_context
            + "/statics/imgs/"+ image_string+ ".png'></img><div class='label_msg'><div class='tria'></div><div class='label_p'><p>姓名:"
            + uuidName + "<br/>"+description+"</p></div></div></div>");
        $('.label_' + uuid).css("position", "absolute");
        $('.label_' + uuid).css("left", (image_width * left +image_width*0.12- 8) + "px");
        $('.label_' + uuid).css("top", (image_height * top + 50 - 10) + "px");
        if(!$('#show_'+image_string).is(':checked')){
            $('.label_' + uuid).hide();
        }
    $(".label_" + uuid).click(function() {
              labelclick1(uuid,uuidName,image_string);
    });

}
    function setFlowMsg(time, uuid, uuidName, event, mac, macName, isAnimate) {
        if (isAnimate) {
            //出现气泡提示框
            if ($("#" + mac + " em").is(":hidden")) {
                $("#" + mac + " em p").text(uuidName + event);
                $("#" + mac + " em").animate({opacity: "show"}, "fast").delay(7500).animate({opacity: "hide"}, "slow");
            }
            var random_num = Math.ceil(Math.random() * 10000);

            if(trs_length>0){
               /* $(".flow_message .first").remove();
                $(".flow_message .second").remove();*/
            }else{
            $(".flow_message .my").append(
                "<tr class='first'><td><div class='new_message_hint'><em>new</em></div>时间</td><td>uuid</td><td id='xinlv'>姓名</td><td id='maibo'>事件</td><td>地点</td><div></div></tr>");
            }
            $(".flow_message .first").after(
                "<tr class='" + random_num + "'><td><div class='new_message_hint'><em>new</em></div>" + time + "</td><td>" + uuid + "</td><td>"
                + uuidName + "</td><td>" + event + "</td><td>" + macName + "</td><div></div></tr>");
            window.setTimeout(function () {
                $(".flow_message ." + random_num + " .new_message_hint").remove();
                $(".flow_message ." + random_num).removeAttr("class");
            }, 8000);
            var trs_length = $(".flow_message tr").length;
            if (trs_length > 11) {
                $(".flow_message tr:last").remove();
            }
        } else {
            $(".flow_message tr:last").after(
                "<tr><td>" + time + "</td><td>" + uuid + "</td><td>"
                + uuidName + "</td><td>" + event + "</td><td>" + macName + "</td><div></div></tr>");
        }
    }

    function setNodePerson(mac, number, uuidGroup) {
        $("#" + mac + " .cornertext").html(number);
        // 更新popView的List中的内容,在ul中添加人员信息
        // 得到ul的jquery对象
        var $ul = $("#" + mac + " .listContent").children(".list-group");
        $ul.children("li").remove();
        // 如果人数非空，才添加图片和人数信息
        if (number != 0) {
            for (var i = 0; i < uuidGroup.length; i++) {
                $ul.append("<li><img src='" + server_context
                    + "/statics/imgs/man.png' width='20px'></img>"
                    + uuidGroup[i] + "</li>");
            }
        }
    }
    function labelclick1(uuid,uuidname,type) {
        $.ajax({
            type: "post",// 请求方式
            url: server_context + "/inout/get1inoutdata",// 发送请求地址
            dataType: "json",
            data: {// 发送给数据库的数据
                "uuid": uuid
            },
            // 请求成功后的回调函数有两个参数
            success: function (data, textStatus) {

                    var lastuptime = data.lastUpdateTime
                    //   state = data[index].state
                    addlabelclick1(uuid,uuidname,type,lastuptime)


            }
        });
    }
        function addlabelclick1(uuid,uuidname,type,lastuptime){
        var random_num = Math.ceil(Math.random() * 10000);
        var trs_length = $(".flow_message tr").length;
        if(trs_length>0){
            $(".flow_message .first").remove();
            $(".flow_message .second").remove();
        }

        $(".flow_message .my").append(
            "<tr class='first'><td><div class='new_message_hint'><em>"+type+"</em></div>人名</td><td>编号</td><td >心率</td><td >脉搏</td><td>更新时间</td><div></div></tr>");
        $(".flow_message .first").after(
            "<tr class='second'><td><div class='new_message_hint'><em>"+uuid+"</em></div>" + uuidname + "</td><td>" + random_num + "</td><td id='xinlv'>"
            + random_num + "</td><td id='maibo'>" + random_num + "</td><td>" + lastuptime + "</td><div></div></tr>");

        setInterval(refresh,1000*60*10);
                function refresh(){
                    var xinlv = 60+parseInt(40*Math.random());
                    var maibo = 60+parseInt(40*Math.random());
                    $("#xinlv").html(xinlv);
                    $("#maibo").html(maibo);
                }


}

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
<head>
  <base href="<%=basePath%>">
  <title>区间检测平台</title>
  <link rel="stylesheet" type="text/css" href="<%=path %>/statics/css/restcss.css">
  <link rel="stylesheet" type="text/css" href="<%=path %>/statics/css/main.css">
  <!--2017-9-17改byGuwei-begin-->
  <!--2017-9-17改byGuwei-end-->

  <!--2017-9-17改byGuwei-begin-->
  <link rel="stylesheet" href="<%=path%>/statics/css/restcss.css" type="text/css">
  <link rel="stylesheet" href="<%=path%>/statics/css/mystyle.css">
  <link rel="stylesheet" href="<%=path%>/statics/css/font-awesome-4.7.0/css/font-awesome.css" type="text/css"/>
  <link rel="stylesheet" type="text/css" href="<%=path%>/statics/css/turnpage/paging.css"/>
  <link rel="stylesheet" type="text/css" href="<%=path%>/statics/css/media-queries.css"/>
  <script src="<%=path%>/statics/js/jquery-1.7.2.min.js"></script>
  <script src="<%=path%>/statics/js/myjs.js"></script>
  <script src="<%=path%>/statics/date/time.js"></script>
  <!--2017-9-17改byGuwei-end-->

</head>

<body>
  <div id="header" style="background: #64a5bab3;">
    <div class='title'>
        区间检测平台
    </div>

      <div id="menu">
        <a href="register">注册页面</a>
        <a href="config" style="margin-left: 10px;">配置页面</a>
        <a href="history" style="margin-left: 10px;">历史轨迹</a>
    </div>
  </div>

  <!--2017-9-17改byGuwei-begin-->
  <div id="sidebar" style="width:12% ;height: 80%;top: 50px;" >
    <ul class="menu" style="width:100%;">
      <li class="first"><span>主菜单</span><i></i></li>
      <li class="current"><a href="index"> <i class="fa fa-address-book-o "></i><span>区间检测</span></a></li>
      <li><a href="register"> <i class="fa fa-book"></i><span>注册页面</span></a></li>
      <li><a href="config"><i class="fa fa-calendar-check-o"></i><span>配置页面</span></a></li>
      <li><a href="history"><i class="fa fa-bar-chart"></i><span>历史轨迹</span></a></li>


    </ul>

  </div>
  <!--2017-9-17改byGuwei-end-->
  <!--2017-9-17改byGuwei-begin-->
  <div class="scene" style="position:relative; left: 12%; top:50px; width: 88%;">
    <img style="width:100%;" src="<%=path %>/statics/imgs/background3.jpg" alt="">
  </div>
  <!--2017-9-17改byGuwei-end-->

  <div class="checkbox" style="position:relative; left: 12%; top:50px; width: 88%;">
    选择要显示的类型:
    <label><input id="show_oldman" type="checkbox" value="" checked/> 老人 </label>
    <label><input id="show_nurse" type="checkbox" value="" checked/> 护工</label>
    <label><input id="show_people" type="checkbox" value="" checked/>保安 </label>
    <%--<label><input id="show_thief" type="checkbox" value="" checked/>重点人员 </label>
    <label><input id="show_bike" type="checkbox" value="" checked/>电动车 </label>--%>
  </div>
  <div class="flow_message" style="position:relative; left: 12%; top:60px; width: 88%;">
    <table class="my">
     <%-- <tr class='first'>
       <td>时间</td>
        <td>uuid</td>
        <td>姓名</td>
        <td>事件</td>
        <td>地点</td>
      </tr>--%>
    </table>
  </div>
  <script src="<%=path %>/statics/js/jquery-3.1.1.min.js" charset="utf-8"></script>
  <script src="<%=path %>/statics/js/main.js" charset="utf-8"></script>
</body>
</html>

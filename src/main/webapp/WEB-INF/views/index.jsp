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
</head>

<body>
  <div id="header">
    <div class='title'>
        区间检测平台
    </div>
    <div id="menu">
        <a href="register">注册页面</a>
        <a href="config" style="margin-left: 10px;">配置页面</a>
        <a href="history" style="margin-left: 10px;">历史查询</a>
    </div>
  </div>
  <div class="scene">
    <img src="<%=path %>/statics/imgs/background3.jpg" alt="">
  </div>
  <div class="flow_message">
    <table>
      <tr class='first'>
        <td>时间</td>
        <td>uuid</td>
        <td>姓名</td>
        <td>事件</td>
        <td>地点</td>
      </tr>
    </table>
  </div>
  <script src="<%=path %>/statics/js/jquery-3.1.1.min.js" charset="utf-8"></script>
  <script src="<%=path %>/statics/js/main.js" charset="utf-8"></script>
</body>
</html>

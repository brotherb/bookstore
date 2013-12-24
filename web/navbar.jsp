<%@ page import="foo.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">文档中心</a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li><a href="/doclist">全部文档</a></li>
        <li><a href="/upload">上传</a></li>
        <li><a href="/mycart">购物车</a></li>
      </ul>

      <form class="navbar-form navbar-left" role="search" action="/search" method="post">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="请输入关键词,逗号分开" style="height: 34px" name="keywords">
        </div>
        <button type="submit" class="btn btn-default">搜索</button>
      </form>

      <ul class="nav navbar-nav navbar-right">
        <% if (session.getAttribute("loggedin")==null) {%>
          <li><a href="login.jsp">登录</a></li>
          <li><a href="register.jsp">注册</a></li>
        <%}else {%>
          <li><a><%= ((User)session.getAttribute("user")).getName()%></a></li>
          <li><a href="/logout">退出</a></li>
        <%}%>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>

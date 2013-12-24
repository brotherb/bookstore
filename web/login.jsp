<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>登录到文档中心</title>
  <jsp:include page="fragments/css.html" />
</head>
<body>

<jsp:include page="navbar.jsp" />
<div id="wrap">
  <div id="main" class="container">
    <form class="form-signin" action="/login" method="post">
      <legend><h3 class="form-signin-heading">登录到文档中心</h3></legend>
      <label class="text-left" id="error" style="color:red"></label>
      <div class="form-group">
        <input type="text" class="form-control" placeholder="用户名" name="username" required autofocus>
      </div>
      <div class="form-group">
        <input type="password" class="form-control" placeholder="密码" name="password" required>
      </div>
      <label class="checkbox">
        <input type="checkbox" value="remember-me"> 记住我
      </label>
      <input class="btn btn-lg btn-primary btn-block" type="submit" value="登录"/>
    </form>
  </div>
</div>

<%
String loginStatus = "";
if(request.getAttribute("loginstatus")!=null){
	loginStatus = request.getAttribute("loginstatus").toString();
}
%>

<script type="text/javascript">
  window.onload = function(){
    var loginStatus = '<%= loginStatus %>';
    if(loginStatus.length > 0){
      document.getElementById("error").appendChild(document.createTextNode(loginStatus));
    }

  }
</script>
<jsp:include page="fragments/footer.html" />
<jsp:include page="fragments/js.html" />
</body>
</html>

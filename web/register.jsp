<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>注册</title>
  <jsp:include page="fragments/css.html" />

</head>
<body>
<jsp:include page="navbar.jsp" />
<div id="wrap">
  <div id="main" class="container">
    <div class="col-md-6 col-md-offset-3">
      <form class="form" method="post" action="/register">
        <legend><h3>注册</h3></legend>
        <label class="text-left" id="error" style="color:red"></label>
        <div class="form-group">
          <input type="text" class="form-control" placeholder="用户名" name="username" required autofocus>
        </div>
        <div class="form-group">
          <input type="password" class="form-control" placeholder="密码" name="passwd" required>
        </div>
        <div class="form-group">
          <input type="password" class="form-control" placeholder="重复密码" name="conpasswd" required>
        </div>
        <div class="form-group">
          <input type="email" class="form-control" placeholder="邮箱" name="email" required>
        </div>

        <div class="control-group">
          <div class="controls">
            <button type="submit" class="btn btn-success btn-block btn-lg">注册</button>
          </div>
        </div>
      </form>
    </div>

  </div>
</div>
<%
  String registerstatus = "";
  if(request.getAttribute("registerStatus")!=null){
    registerstatus = request.getAttribute("registerStatus").toString();
  }
%>

<script type="text/javascript">
  window.onload = function(){
    var registerStatus = '<%= registerstatus %>';
    if(registerStatus.length > 0){
      document.getElementById("error").appendChild(document.createTextNode(registerStatus));
    }

  }
</script>

<jsp:include page="fragments/footer.html" />
<jsp:include page="fragments/js.html" />
</body>
</html>

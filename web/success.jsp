<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.OutputStream" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>购买成功</title>
  <jsp:include page="fragments/css.html" />
</head>
<body>

<jsp:include page="navbar.jsp" />
<div id="main">
  <div class="col-md-8 offset2">
    <h3 style="color:green">购买成功!即将开始下载！点击<a href="/downzip">这里</a>开始下载！</h3>

  </div>
</div>


<jsp:include page="fragments/footer.html" />
<jsp:include page="fragments/js.html" />
</body>
</html>

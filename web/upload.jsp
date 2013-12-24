<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>上传文件</title>
  <jsp:include page="fragments/css.html" />
  <style>
    .bootstrap-filestyle input[type="text"] {
      width: 500px;
    }
  </style>
</head>
<body>

<jsp:include page="navbar.jsp" />
<div id="wrap">
  <div id="main" class="container">
    <div class="col-md-8 col-md-offset-2">
      <form class="form" action="/upload" method="post" enctype="multipart/form-data">
        <h3 class="text-left">上传文档</h3>

        <label class="text-left" id="error" style="color:red"></label>
        <div class="form-group">
        <input type="file" class="filestyle" data-classButton="btn btn-success" data-buttonText="选择文件" name="file" />
          <input class="btn btn-primary" type="submit"/>
        </div>

      </form>
    </div>
  </div>
</div>

<%
  String loginStatus = "";
  if(request.getAttribute("Error")!=null){
    loginStatus = request.getAttribute("Error").toString();
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

<jsp:include page="fragments/footer.html"/>
<jsp:include page="fragments/js.html"/>
<script src="static/js/bootstrap-filestyle.min.js"/>
<script>
  $(":file").filestyle({buttonText: "选择文件"});
</script>
</body>
</html>

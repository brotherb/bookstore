<%--
  Created by IntelliJ IDEA.
  User: brotherb
  Date: 13-12-21
  Time: 下午9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>设置文档</title>
  <jsp:include page="fragments/css.html" />

  <link rel="stylesheet" href="static/css/bootstrap-formhelpers.min.css">
</head>
<body>
<jsp:include page="navbar.jsp" />
<div id="wrap">
  <div id="main" class="container">
    <div class="col-md-6 col-md-offset-3">
      <form class="form" method="post" action="/docsetting">
        <legend><h3>设置文档</h3></legend>
        <label class="text-left" id="error" style="color:red"></label>
        <div class="form-group">
          <textarea rows = "4" class="form-control" placeholder="请为刚才上传的文档添加一段文字描述，200个汉字以内～" name="description" required autofocus></textarea>
        </div>
        <div class="form-group">
          <input type="text" class="form-control bfh-number" data-min="0" data-max="25" name="price">
          <%--<input type="text" class="form-control bfh-number">--%>
        </div>
        <div class="control-group">
          <div class="controls">
            <button type="submit" class="btn btn-success btn-block btn-lg">确定</button>
          </div>
        </div>
      </form>
    </div>

  </div>
</div>

<jsp:include page="fragments/footer.html" />
<jsp:include page="fragments/js.html" />
<script src="static/js/bootstrap-formhelpers.min.js" ></script>
<script src="static/js/bootstrap-formhelpers-number.js"></script>
</body>
</html>

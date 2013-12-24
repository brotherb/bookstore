<%@ page import="foo.model.Document" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>文章列表</title>
  <jsp:include page="fragments/css.html" />
</head>
<body>

<jsp:include page="navbar.jsp" />
<div id="main" class="container">
<div class="col-md-8 col-md-offset-2">
  <%
    List<Document> docs = (List<Document>)request.getAttribute("alldoc");

    if(docs == null) {%>
  <h3 style="color: green"> 没有任何文档，快去上传吧！</h3>

  <%}else{%>
  <table class="table">
    <thead>
    <tr>
      <th>文档名称</th>
      <th>价格</th>
    </tr>
    </thead>
    <tbody>


    <%for(Document item : docs) {%>
    <tr>
      <td>
        <a href="/Doc?id=<%=item.getId()%>"><%= item.getName()%></a><br>
      </td>
      <td>
        <%= item.getPrice()%>
      </td>
    </tr>

    <%}%>

    </tbody>
  </table>
  <%}%>

</div>
</div>

<jsp:include page="fragments/footer.html" />
<jsp:include page="fragments/js.html" />
</body>
</html>

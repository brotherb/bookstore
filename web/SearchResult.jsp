<%@ page import="foo.model.Document" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>搜索结果</title>
  <jsp:include page="fragments/css.html" />
</head>
<body>

<jsp:include page="navbar.jsp" />

<div id="main" class="container">
  <div class="col-md-6 col-md-offset-3">
    <h3>搜索结果:</h3>
    <%
      List<Document> docs = (List<Document>)request.getAttribute("result");
      for(Document item : docs) {%>

    <a href="/Doc?id=<%=item.getId()%>"><%= item.getName()%></a><br>

    <%}%>


  </div>
</div>

<jsp:include page="fragments/footer.html" />
<jsp:include page="fragments/js.html" />
</body>
</html>

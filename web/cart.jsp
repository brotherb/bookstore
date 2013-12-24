<%@ page import="foo.model.Document" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>购物车</title>
  <jsp:include page="fragments/css.html" />
</head>
<body>

<jsp:include page="navbar.jsp" />

<div id="main" class="container">
  <h2>购物车</h2>
  <div class="col-md-8 col-md-offset-2">
    <%
      List<Document> docs = (List<Document>)session.getAttribute("docincart");

      if(docs == null) {%>
        <h3 style="color: green"> 您的购物车是空的</h3>

    <%}else{
      if(docs.size()==0){%>
    <h3 style="color: green"> 您的购物车是空的</h3>

      <%}else{%>

    <label class="text-left" id="error" style="color:red"></label>
      <table class="table">
        <thead>
          <tr>
            <th>文档名称</th>
            <th>价格</th>
            <th></th>
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
  <td>
    <a href="/removefromcart?id=<%= item.getId()%>" class="btn btn-danger btn-block">删除</a>
  </td>
</tr>

    <%}%>

        </tbody>
      </table>
    <br>

    <a href="/pay" class="btn btn-success btn-block pull-right" >结算</a>

      <%}
}%>
  </div>
</div>

<%
  String loginStatus = "";
  if(session.getAttribute("error")!=null){
    loginStatus = session.getAttribute("error").toString();
    session.removeAttribute("error");
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
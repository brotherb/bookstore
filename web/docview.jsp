<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
  <jsp:include page="fragments/css.html" />
  <%--<link href="static/css/flexpaper.css" rel="stylesheet">--%>
  <script type="text/javascript" src="static/js/jquery.min.js"></script>
  <script type="text/javascript" src="static/js/flexpaper.js"></script>
  <script type="text/javascript" src="static/js/flexpaper_handlers_debug.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp" />

<div id="wrap">
  <div id="main" class="container">
    <div class="col-md-10 col-md-offset-1">
      <h3><%= request.getAttribute("title").toString()%></h3>
      <h4><%= request.getAttribute("authorname").toString()%>  贡献于  <%= request.getAttribute("uploadtime").toString()%>
        <a class="btn btn-success" href="/down?id=<%= request.getAttribute("docid")%>">下载</a>
        <a class="btn btn-success" href="/addtocart?id=<%= request.getAttribute("docid")%>">加入到购物车</a>
      </h4>
      <label class="text-left" id="error" style="color:red"></label>
      <label class="text-left" id="msg" style="color:green"></label>

      <div id="documentViewer" class="flexpaper_viewer " style="width:900px;height:720px"></div>
        <script type="text/javascript">
          $('#documentViewer').FlexPaperViewer(
            { config : {

              SWFFile : '<%= request.getAttribute("swfname").toString()%>',

              Scale : 0.6,
              ZoomTransition : 'easeOut',
              ZoomTime : 0.5,
              ZoomInterval : 0.2,
              FitPageOnLoad : false,
              FitWidthOnLoad : true,
              FullScreenAsMaxWindow : false,
              ProgressiveLoading : false,
              MinZoomSize : 0.2,
              MaxZoomSize : 5,
              SearchMatchAll : false,
              InitViewMode : 'Portrait',
              RenderingOrder : 'flash',
              StartAtPage : '',

              ViewModeToolsVisible : true,
              ZoomToolsVisible : true,
              NavToolsVisible : true,
              CursorToolsVisible : true,
              SearchToolsVisible : true,
              WMode : 'window',
              localeChain: 'en_US'
            }}
          );
        </script>
      </div>
      </div>
  </div>


<%
  String loginStatus = "";
  if(session.getAttribute("repeaterror")!=null){
    loginStatus = session.getAttribute("repeaterror").toString();
    session.removeAttribute("repeaterror");
  }
  String msg = "";
  if(session.getAttribute("addtocartsuccessmsg")!=null){
    msg = session.getAttribute("addtocartsuccessmsg").toString();
    session.removeAttribute("addtocartsuccessmsg");
  }
%>

<script type="text/javascript">
  window.onload = function(){
    var loginStatus = '<%= loginStatus %>';
    if(loginStatus.length > 0){
      document.getElementById("error").appendChild(document.createTextNode(loginStatus));
    }
    var msg = '<%= msg %>';
    if(msg.length > 0){
      document.getElementById("msg").appendChild(document.createTextNode(msg));
    }
  }
</script>
<jsp:include page="fragments/footer.html" />
</body>
</html>

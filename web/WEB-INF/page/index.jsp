<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
	  <%@ include file="../org/inc.jsp"%>
  </head>
  <body>
    <%--<button id="open_urljk"  onclick="fnClick('open_urljk')" style="width: 80px;height: 40px;">开启网址监控</button>
    <button id="open_jdbcjk"  onclick="fnClick('open_jdbcjk')" style="width: 80px;height: 40px;">开启数据库监控</button>--%>
    <li><a href="<%=basePath%>monitorUrlAction.do" target="_blank">开启网址监控</a></li>
    <li><a href="<%=basePath%>monitorJdbcAction.do" target="_blank">开启数据库监控</a></li>
  </body>
  <script>
      $(document).ready(function() {
          hiddenMask();
      });
      function fnClick(id) {
          var url;
          if(id == 'open_urljk'){
              url = contextPath+"/monitorUrlAction.do";
          }else if (id == 'open_jdbcjk'){
              url = contextPath+"/monitorJdbcAction.do";
          }
          window.location.href = url;
      }
  </script>
</html>

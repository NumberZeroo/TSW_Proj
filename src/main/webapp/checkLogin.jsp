<%--
  Created by IntelliJ IDEA.
  User: developer
  Date: 5/7/24
  Time: 6:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login check</title>
</head>
<body>
<p>Questa pagina serve solo per controllare che l'utente sia loggato ed eventualmente stampa le informazioni</p>

<%
    String username = null;
    if (request.getSession() != null && request.getSession().getAttribute("username") != null){
        username = request.getSession().getAttribute("username").toString();
%>
    <p>Ciao <%=username%></p>
<% } else { %>
    <p>Utente non loggato</p>
<% } %>


</body>
</html>

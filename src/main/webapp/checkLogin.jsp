<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: developer
  Date: 5/7/24
  Time: 6:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>login check</title>
</head>
<body>
<p>Questa pagina serve solo per controllare che l'utente sia loggato ed eventualmente stampa le informazioni</p>

<%
    SessionFacade userSession = new SessionFacade(request.getSession());
    if (userSession.getUsername().isPresent()){
%>
    <p>Ciao <%=userSession.getUsername().get()%></p>
<% } else { %>
    <p>Utente non loggato</p>
<% } %>

// TODO: togliere questa parte, è solo per debug (implementare vista carrello)
<%
    StringBuilder ids = new StringBuilder().append("[");
    for (Map.Entry<Long, Long> productIds : userSession.getCartProducts().entrySet()) {
        ids.append(productIds.getKey()).append(", ");
    }
    ids.append("]");
%>

<p><%=ids.toString()%></p>


</body>
</html>

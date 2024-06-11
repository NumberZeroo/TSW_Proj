<%@ page import="com.tswproject.tswproj.SessionFacade" %><%--
  Created by IntelliJ IDEA.
  User: developer
  Date: 5/30/24
  Time: 6:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
    <p>Ciao <%=(new SessionFacade(request.getSession()).getUsername().orElse("Anonymous"))%></p>

    <a href="${pageContext.request.contextPath}/logout">logout</a>
</body>
</html>

<%@ page import="com.tswproject.tswproj.EmptyPoolException" %>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/website.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/error.css">

    <%@include file="navbar.jsp" %>
    <title>Errore</title>
</head>
<body>
<div class="error-container">
    <img class="error-image" src="${pageContext.request.contextPath}/img/gattoChePiange.jpg" alt="Error Image">
    <h2>Si è verificato un errore</h2>
    <button class="home-button" onclick="window.location.href='home.jsp'">Torna alla Home</button>

    <!-- TODO: errori personalizzati in base al codice d'errore (es: 401 -> "hey...non sei autorizzato a farlo..."-->
</div>
<%@include file="footer.jsp" %>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>

    <div id="notification"></div>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/popupFeedback.css">

    <h2>Login</h2>
    <form action="loginServlet" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit" value="Login">
    </form>
    <p>Sei un coglione? Registrati qui: <a href="register.jsp">Register page</a></p>

    <%@ include file="footer.jsp" %>

    <script src="${pageContext.request.contextPath}/scripts/login.js" type="module"></script>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/website.css">
</head>
<body>
    <%@ include file="navbar.jsp" %>

    <div id="notification"></div>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/popupFeedback.css">

    <div class="auth-container">
        <h2>Login</h2>
        <form action="loginServlet" method="post" class="auth-form">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password">
            <input type="submit" value="Login">
        </form>
        <p>Non sei registrato? <a href="register.jsp">Registrati qui</a></p>
    </div>

    <%@ include file="footer.jsp" %>

    <script src="${pageContext.request.contextPath}/scripts/login.js" type="module"></script>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <title>Register Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/auth.css">
</head>
<body>
    <%@ include file="navbar.jsp" %>

    <div id="notification"></div>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/popupFeedback.css">
    <script src="${pageContext.request.contextPath}/scripts/register.js" type="module"></script>

    <div class="auth-container">
        <h2>Registrazione</h2>
        <form action="${pageContext.request.contextPath}/registerServlet" method="post" class="auth-form">

            <label for="username">Username:</label>
            <input type="text" id="username" name="username">

            <label for="email">Email:</label>
            <input type="text" id="email" name="email">

            <label for="password">Password:</label>
            <input type="password" id="password" name="password">

            <label for="confirmPassword">Conferma password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword">

            <input type="submit" value="Registrati">
        </form>
        <p>Hai un profilo? <a href="login.jsp">Accedi qui</a></p>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>
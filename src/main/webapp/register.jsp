<!DOCTYPE html>
<html>
<head>
    <title>Register Page</title>
</head>
<body>

<div id="notification"></div>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/popupFeedback.css">
<script src="${pageContext.request.contextPath}/scripts/register.js" type="module"></script>

<h2>Registrazione</h2>
<form action="${pageContext.request.contextPath}/registerServlet" method="post">
    Username: <input type="text" name="username"><br>
    Email: <input type="text" name="email"><br>
    Password: <input type="password" name="password"><br>

    <input type="submit" value="Registrati">
</form>
<a href="login.jsp">Login page</a>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
  <title>Register Page</title>
</head>
<body>
<h2>Registrazione</h2>
<form action="${pageContext.request.contextPath}/registerServlet" method="post">
  Username: <input type="text" name="username"><br>
  Email: <input type="text" name="email"><br>
  Password: <input type="password" name="password"><br>

  <input type="submit" value="Registrati">
</form>
<p>Sei già un coglione? Loggati qui: <a href="login.jsp">Login page</a></p>
</body>
</html>
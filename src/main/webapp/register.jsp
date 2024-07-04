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
        <input autofocus type="text" id="username" name="username">

        <label for="email">Email:</label>
        <input type="text" id="email" name="email">

        <label for="password">Password:</label>
        <input type="password" id="password" name="password">
        <div id="passwordError"></div>

        <label for="confirmPassword">Conferma password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword">

        <input type="submit" value="Registrati">
    </form>
    <p>Hai un profilo? <a href="login.jsp">Accedi qui</a></p>
</div>

<%@ include file="footer.jsp" %>

<script>
    document.getElementById('password').addEventListener('input', function (event) {
        validatePassword(event.target.value);
    });

    document.querySelector('.auth-form').addEventListener('submit', function (event) {
        var password = document.getElementById('password').value;

        if (!validatePassword(password)) {
            event.preventDefault();
        }
    });

    function validatePassword(password) {
        var hasLowerCase = /[a-z]/.test(password);
        var hasUpperCase = /[A-Z]/.test(password);
        var hasNumber = /\d/.test(password);
        var hasMinLength = password.length >= 8;

        var message = '';
        if (!hasLowerCase) {
            message = 'La password deve contenere almeno una lettera minuscola.';
        } else if (!hasUpperCase) {
            message = 'La password deve contenere almeno una lettera maiuscola.';
        } else if (!hasNumber) {
            message = 'La password deve contenere almeno un numero.';
        } else if (!hasMinLength) {
            message = 'La password deve contenere almeno 8 caratteri.';
        }

        document.getElementById('passwordError').textContent = message;

        return hasLowerCase && hasUpperCase && hasNumber && hasMinLength;
    }
</script>
</body>
</html>
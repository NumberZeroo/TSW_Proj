<%@ page import="com.tswproject.tswproj.EmptyPoolException" %>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Errore</title>
</head>
<body>
<h1>Si è verificato un errore</h1>

<!-- TODO: errori personalizzati in base al codice d'errore (es: 401 -> "hey...non sei autorizzato a farlo..."-->

<h3>Nota bene: questa è una pagina di errore custom e quindi va fatta la grafica :D</h3>

<% if (request.getAttribute("jakarta.servlet.error.exception") instanceof EmptyPoolException) {%>

<p>Servizio non disponibile. Riprovare più tardi :(</p>

<% } else {%>

<p>Codice di stato: <%= request.getAttribute("jakarta.servlet.error.status_code") %></p>
<p>Messaggio: <%= request.getAttribute("jakarta.servlet.error.message") %></p>
<p>Eccezione: <%= request.getAttribute("jakarta.servlet.error.exception") %></p>

<% } %>
</body>
</html>

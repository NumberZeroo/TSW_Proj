<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo Prodotti</title>
</head>
<body>
<h1>Catalogo Prodotti</h1>

<% List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti"); %>

<% if(prodotti == null){ %>
    <p>Nessun prodotto disponibile.</p>
<% } %>

<% if(prodotti != null && !prodotti.isEmpty()){ %>
    <table border="1">
        <thead>
        <tr>
            <th>Nome Prodotto</th>
            <th>Prezzo</th>
            <th>Disponibilità</th>
            <!-- Aggiungi altre colonne se necessario -->
        </tr>
        </thead>
        <tbody>
        <% for(ProdottoBean prodotto : prodotti){ %>
            <tr>
                <td><%=prodotto.getId()%></td>
                <!-- Aggiungi altre colonne se necessario -->
            </tr>
        <% } %>
        </tbody>
    </table>
<% } %>

<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>
</body>
</html>
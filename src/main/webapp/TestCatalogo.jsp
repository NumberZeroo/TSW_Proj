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

    <% if(prodotti == null || prodotti.isEmpty()){ %>
        <p>Nessun prodotto disponibile.</p>
    <% } else { %>
        <table>
            <thead>
            <tr>
                <th>Nome Prodotto</th>
                <th>Tipo</th>
                <th>Taglia</th>
                <th>Prezzo</th>
            </tr>
            </thead>
            <tbody>
            <% for(ProdottoBean prodotto : prodotti){ %>
                <tr>
                    <td><img src="${pageContext.request.contextPath}/Website%20assets/Kart_256.png" alt=""> </td>
                    <td><%=prodotto.getNome()%></td>
                    <td><%=prodotto.getCategoria()%></td>
                    <td><%=prodotto.getTaglia()%></td>
                    <td><%=prodotto.getPrezzo()%></td>
                </tr>
            <% } %>
            </tbody>
        </table>
    <% } %>
</body>
</html>
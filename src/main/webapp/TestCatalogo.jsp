<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@ include file="navbar.jsp" %>

    <title>Catalogo Prodotti</title>

    <style>
        .test-div-class {
            display: inline-block;
            margin: 10px;
            padding: 10px;
            border: 1px solid black;
            border-radius: 10px;
            text-align: center;
        }
    </style>


</head>
<body>

    <h1>Catalogo Prodotti</h1>

    <% List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti"); %>

    <% if(prodotti == null || prodotti.isEmpty()){ %>
        <p>Nessun prodotto disponibile.</p>
    <% } else {
        for(ProdottoBean prodotto : prodotti) { %>
    <div class = "test-div-class">

        <a href="product.jsp?id=<%=prodotto.getId()%>">
            <img src="<%=prodotto.getImgPath()%>" alt="Immagine">
            <h2><%=prodotto.getNome()%></h2>
            <p>Prezzo: <%=prodotto.getPrezzo()%> €</p>
        </a>
    </div>
    <% }
        } %>
</body>
</html>
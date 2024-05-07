<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <% } else {
        for(ProdottoBean prodotto : prodotti) { %>
    <div>
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
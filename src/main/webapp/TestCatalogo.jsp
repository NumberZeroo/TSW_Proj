<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="javax.swing.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Catalogo Prodotti</title>
</head>
<body>
    <%@ include file="navbar.jsp" %>

    <% List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti"); %>

    <% if(prodotti == null || prodotti.isEmpty()){ %>
        <p>Nessun prodotto disponibile.</p>
    <% } else {
        for(ProdottoBean prodotto : prodotti) { %>
    <div class = "test-div-class">
        <a href="product.jsp?id=<%=prodotto.getId()%>">
            <img src="<%= prodotto.getImgPath() %>" alt="Immagine">
            <h2><%=prodotto.getNome()%></h2>
        </a>
        <p>Prezzo: <%=prodotto.getPrezzo()%> €</p>
    </div>
    <% }
        } %>

<%--    <div class="filter-bar bg-dark text-white p-3">--%>
<%--    <h3>Filtri</h3>--%>
<%--    <div class="filter-option">--%>
<%--        <label for="filter-price">Prezzo</label>--%>
<%--        <input type="range" id="filter-price" name="filter-price" min="0" max="100">--%>
<%--    </div>--%>
<%--    <!-- Aggiungi qui altre opzioni di filtro se necessario -->--%>
<%--    </div>--%>

    <div>
        <%@ include file="footer.jsp" %>
    </div>
</body>
</html>
<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/catalogo.css">
    <meta charset="UTF-8">
    <title>Catalogo Prodotti</title>
</head>
<body>
<%@ include file="navbar.jsp" %>

<% SessionFacade sessionFacade = new SessionFacade(request.getSession());
    if (sessionFacade.isLoggedIn() && sessionFacade.getIsAdmin()) { %>
<button id="adminButton" class="adminButton">Aggiungi Prodotto</button>
<% } %>

<script>
    document.getElementById('adminButton').addEventListener('click', function () {
        window.location.href = 'addProduct.jsp';
    });
</script>


<div class="content">
    <button id="open-filter-button" class="display-none">
        <i class="fas fa-filter"></i>
    </button> <!-- Pulsante per mostrare la barra dei filtri a pagina rimpicciolita -->
    <i id="filter-button" class="fas fa-filter"></i>

    <%@include file="filterBar.jsp" %>

    <!-- Catalogo.jsp -->
    <% List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti"); %>

    <% if (prodotti == null || prodotti.isEmpty()) { %>
    <div class="no-products" style="text-align: center;">
        <i class="fas fa-heart-broken fa-5x"></i>
        <h3>Nessun prodotto disponibile.</h3>
        <p>Prova a cambiare i filtri!</p>
        <a href="${pageContext.request.contextPath}/mostraCatalogoServlet">
            <button>Torna al catalogo</button>
        </a>
    </div>
    <% } else if (sessionFacade.isLoggedIn() && sessionFacade.getIsAdmin()) { %>
    <div class="product-grid-new">
        <%for (ProdottoBean prodotto : prodotti) { %>
        <div class="product-card-new">
            <div class="product-image-new">
                <a href="product?id=<%=prodotto.getId()%>">
                    <img src="<%= prodotto.getImgPath() %>" alt="Immagine">
                </a>
            </div>
            <div class="product-info-new">
                <h3><%=prodotto.getNome()%>
                </h3>
                <p><%=prodotto.getDescrizione()%>
                </p>
                <h4>Prezzo: <%=prodotto.getPrezzo()%> €</h4>
                <button class="product-button" onclick="window.location.href='product?id=<%=prodotto.getId()%>'">Vai al
                    prodotto
                </button>
            </div>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <div class="product-grid-new">
        <%for (ProdottoBean prodotto : prodotti.stream().filter(ProdottoBean::isVisibile).toList()) { %>
        <div class="product-card-new">
            <div class="product-image-new">
                <a href="product?id=<%=prodotto.getId()%>">
                    <img src="<%= prodotto.getImgPath() %>" alt="Immagine">
                </a>
            </div>
            <div class="product-info-new">
                <h3><%=prodotto.getNome()%>
                </h3>
                <p><%=prodotto.getDescrizione()%>
                </p>
                <h4><%=prodotto.getPrezzo()%>€</h4>
                <button class="product-button" onclick="window.location.href='product?id=<%=prodotto.getId()%>'">Vai al
                    prodotto
                </button>
            </div>
        </div>
        <% }
        } %>
    </div>
</div>

<%@ include file="footer.jsp" %>

<!-- Barra dei filtri a pagina ingrandita -->
<script src="${pageContext.request.contextPath}/scripts/catalogo.js"></script>
</body>
</html>
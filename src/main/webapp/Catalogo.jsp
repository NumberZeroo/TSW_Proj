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
    <%@ include file="navbar.jsp" %>

    <% SessionFacade sessionFacade = new SessionFacade(request.getSession());
        if(sessionFacade.isLoggedIn() && sessionFacade.getIsAdmin()){ %>
        <button id="adminButton" class="adminButton">Aggiungi Prodotto</button>
    <% } %>

    <script>
        document.getElementById('adminButton').addEventListener('click', function() {
            window.location.href = 'addProduct.jsp';
        });
    </script>


    <div class="content">
        <button id="open-filter-button" class="display-none">
            <i class="fas fa-filter"></i>
        </button> <!-- Pulsante per mostrare la barra dei filtri a pagina rimpicciolita -->
        <i id="filter-button" class="fas fa-filter"></i>

        <div id="filter-bar" class="filter-bar">
            <i id="close-button" class="fas fa-times"></i>
            <form action="${pageContext.request.contextPath}/mostraCatalogoServlet" method="get">
                <label for="price">Prezzo: </label> <span id="price-value"></span>
                <input type="range" id="price" name="price" min="0" max="100">

                <label for="size">Taglia:</label>
                <select id="size" name="size">
                    <option value="PICCOLA">Piccola</option>
                    <option value="MEDIA">Media</option>
                    <option value="GRANDE">Grande</option>
                </select>

                <label for="category">Categoria:</label>
                <select id="category" name="category">
                    <option value="Alimentari">Alimentari</option>
                    <option value="Giocattoli">Giocattoli</option>
                    <option value="Cat3">Cat3</option>
                </select>

                <label for="animalRace">Tipo di animale:</label>
                <select id="animalRace" name="animalRace">
                    <option value="1">Cane</option>
                    <option value="2">Gatto</option>
                    <option value="3">Volatile</option>
                </select>

                <!-- Filtro per età minima -->
                <label for="min-age">Età Minima: </label><span id="min-age-value"></span>
                <input type="range" id="min-age" name="min-age" min="0" max="20">

                <!-- Filtro per età massima -->
                <label for="max-age">Età Massima: </label><span id="max-age-value"></span>
                <input type="range" id="max-age" name="max-age" min="0" max="30">

                <!-- Filtro per sterilizzazione -->
                <label for="sterilized">Sterilizzato:</label>
                <select id="sterilized" name="sterilized">
                    <option value="1">Sì</option>
                    <option value="0">No</option>
                </select>

                <input type="submit" value="Applica filtri">
            </form>
        </div>

    <% List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti"); %>

    <% if(prodotti == null || prodotti.isEmpty()){ %>
        <p>Nessun prodotto disponibile.</p>
    <% } else if(sessionFacade.isLoggedIn() && sessionFacade.getIsAdmin()) { %>
        <div class="griglia-prodotti">
            <%for(ProdottoBean prodotto : prodotti) { %>
                <div class = "catalogoProd">
                    <a href="product?id=<%=prodotto.getId()%>">
                        <img src="<%= prodotto.getImgPath() %>" alt="Immagine">
                        <h3><%=prodotto.getNome()%></h3>
                    </a>
                    <p>Prezzo: <%=prodotto.getPrezzo()%> €</p>
                </div>
            <% } %>
        </div>
            <% } else { %>
                <div class="griglia-prodotti">
                        <%for(ProdottoBean prodotto : prodotti.stream().filter(ProdottoBean::isVisibile).toList()) { %>
                            <div class = "catalogoProd">
                                <a href="product?id=<%=prodotto.getId()%>">
                                    <img src="<%= prodotto.getImgPath() %>" alt="Immagine">
                                    <h3><%=prodotto.getNome()%></h3>
                                </a>
                            <p>Prezzo: <%=prodotto.getPrezzo()%> €</p>
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
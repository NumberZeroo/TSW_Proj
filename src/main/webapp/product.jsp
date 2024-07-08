<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="model.recensione.RecensioneBean" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="model.recensione.RecensioneDAO" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="model.ordine.*" %>

<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="navbar.jsp" %>

    <% ProdottoBean prodotto = (ProdottoBean) request.getAttribute("product"); %>
    <title><%=prodotto.getNome()%>
    </title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/popupFeedback.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/product.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/starRating.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/scrollableContainer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/defaultProductAdvices.css">
</head>

<body>
<div id="notification"></div>
<% SessionFacade sessionFacade = new SessionFacade(request.getSession());
    if (sessionFacade.isLoggedIn() && sessionFacade.getIsAdmin()) { %>
<div class="admin-button" style="grid-row: auto">
    <button id="adminButton" class="adminButton">Modifica Prodotto</button>
    <button id="visibilityButton" class="adminButton"><%= prodotto.isVisibile() ? "Nascondi" : "Rendi visibile" %>
    </button>
</div>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script> // Script per cambiare la visibilità del prodotto con Ajax
window.onload = function () {
    var visibilityButton = document.getElementById('visibilityButton');
    var productId = "<%=prodotto.getId()%>";
    var isVisible = "<%= !prodotto.isVisibile() %>";

    visibilityButton.onclick = function () {
        swal({
            title: "Sei sicuro?",
            text: "Sicuro di voler cambiare la visibilità del prodotto?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", 'changeVisibilityServlet', true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    xhr.onreadystatechange = function () {
                        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                            // Reindirizza il browser alla nuova pagina dopo aver ricevuto la risposta
                            window.location.href = "product?id=" + productId;
                        }
                    }
                    xhr.send("productId=" + productId + "&isVisible=" + isVisible);
                }
            });
    };
};
</script>
<% } %>

<div class="product-details">
    <div class="product-image">
        <img src="<%=prodotto.getImgPath()%>" alt="Immagine non disponibile">
        <form action="${pageContext.request.contextPath}/addToWishlistServlet" method="post">
            <input type="hidden" name="productId" value="<%=prodotto.getId()%>">
            <button class="addToWishlistButton"><i class="far fa-heart"></i></button>
        </form>
    </div>
    <div class="product-info-specs">
        <!-- Nuovo div che raggruppa le informazioni del prodotto e le specifiche del prodotto -->
        <div id="product-info" class="product-info">
            <h1><%=prodotto.getNome()%>
            </h1>
            <p><%=prodotto.getDescrizione()%>
            </p>
            <h4>Prezzo: <%=prodotto.getPrezzo()%> €</h4>
        </div>

        <form id="editProductForm" class="product-info-mod"
              action="${pageContext.request.contextPath}/editProductServlet"
              method="post">

            <input type="hidden" name="id" value="<%=prodotto.getId()%>">

            <label for="nome">Nome:</label><input type="text" id="nome" name="nome" value="<%=prodotto.getNome()%>">

            <label for="descrizione">Descrizione:</label>
            <input type="text" id="descrizione" name="descrizione" value="<%=prodotto.getDescrizione()%>">

            <label for="prezzo">Prezzo:</label>
            <input type="number" id="prezzo" name="prezzo" value="<%=prodotto.getPrezzo()%>">

            <input type="hidden" name="id" value="<%=prodotto.getId()%>">

            <label for="category">Categoria:</label>
            <select id="category" name="category">
                <option value="alimentari" <%=prodotto.getCategoria().equals("alimentari") ? "selected" : ""%>>
                    Alimentari
                </option>
                <option value="giocattoli" <%=prodotto.getCategoria().equals("giocattoli") ? "selected" : ""%>>
                    Giocattoli
                </option>
            </select>

            <label for="taglia">Taglia:</label>
            <select id="taglia" name="taglia">
                <option value="piccola" <%=prodotto.getTaglia().equals("PICCOLA") ? "selected" : ""%>>PICCOLA</option>
                <option value="media" <%=prodotto.getTaglia().equals("MEDIA") ? "selected" : ""%>>MEDIA</option>
                <option value="grande" <%=prodotto.getTaglia().equals("GRANDE") ? "selected" : ""%>>GRANDE</option>
            </select>

            <input type="submit" value="Conferma Modifiche">
        </form>

        <div id="product-specs" class="product-specs">
            <h2>Dettagli prodotto</h2>
            <p>Categoria: <%=prodotto.getCategoria()%>
            </p>
            <p>Taglia: <%=prodotto.getTaglia()%>
        </div>
    </div>

    <%--    <div class="addToWishlist">--%>
    <%--        <form action="${pageContext.request.contextPath}/addToWishlistServlet" method="post">--%>
    <%--            <input type="hidden" name="productId" value="<%=prodotto.getId()%>">--%>
    <%--            <input type="hidden" name="userId" value="<%=sessionFacade.getUserId()%>">--%>
    <%--            <button class="addToWishlistButton" data-product-id="<%=prodotto.getId()%>">Aggiungi alla Wishlist</button>--%>
    <%--        </form>--%>
    <%--    </div>--%>

    <div class="addToCart">
        <h3>Disponibilità: <%=prodotto.getDisponibilita()%>
        </h3>
        <label for="quantity">Quantità:</label>
        <input type="number" id="quantity" name="quantity" min="1" max="99" value="1">
        <button class="addToCartButton" data-product-id="<%=prodotto.getId()%>">Aggiungi al Carrello</button>
    </div>
</div>

<script>
    document.getElementById('adminButton').addEventListener('click', function () {
        document.getElementById('product-info').style.display = 'none';
        document.getElementById('product-specs').style.display = 'none';
        document.getElementById('editProductForm').style.display = 'grid';
        document.getElementById('editProductFormDetails').style.display = 'grid';
    });
</script>

<%-- Visualizza il modulo per inserire o modificare una recensione --%>
<div class="reviewOrModify">
    <%
        try (OrdineDAO ordineDAO = new OrdineDAO();
             RecensioneDAO recensioneDAO = new RecensioneDAO();) {
            boolean hasPurchased = ordineDAO.hasUserPurchasedProduct(sessionFacade.getUserId(), prodotto.getId());
            boolean hasReviewed = recensioneDAO.hasUserReviewedProduct(sessionFacade.getUserId(), prodotto.getId());
            if (hasPurchased && !hasReviewed) { %>
    <button id="addReviewButton" class="addReviewButton">Aggiungi recensione</button>
</div>

<%-- Visualizza il modulo per inserire una recensione in un popup --%>
<div id="addReview" class="review-form">
    <form action="addReviewServlet" method="post">
        <div class="review-form-header">
            <label for="titolo">Titolo:</label>
            <input autofocus type="text" id="titolo" name="titolo" required>
            <!--
             <label for="valutazione">Valutazione:</label>
             <input type="number" id="valutazione" name="valutazione" min="1" max="5" required>
             -->
            <label class="rating-label">
                <input
                        class="rating"
                        max="5"
                        oninput="this.style.setProperty('--value', this.value)"
                        step="0.5"
                        type="range"
                        value="1" name="valutazione">
            </label>
        </div>
        <label for="commento">Commento:</label>
        <textarea id="commento" name="commento" required></textarea>
        <input type="hidden" id="idProdotto" name="idProdotto" value="<%=prodotto.getId()%>">
        <input type="hidden" id="idUtente" name="idUtente" value="<%=sessionFacade.getUserId()%>">
        <input type="submit" value="Invia">
    </form>
    <% }
    } catch (Exception e) {
        e.printStackTrace();
    }
    %>
</div>

<script>
    let modal = document.getElementById("addReview");
    let btn = document.getElementById("addReviewButton");

    // Quando l'utente fa clic sul pulsante, mostra il modulo
    btn.onclick = function () {
        modal.style.display = "block";
        btn.style.display = "none";
    }
</script>

<%-- Visualizza le recensioni --%>
<div class="reviews">
    <h2 style="font-style: italic">Recensioni degli utenti</h2>

    <%-- Recupera le recensioni e i prodotti consigliati dal tuo database --%>
    <% try (RecensioneDAO recensioneDAO = new RecensioneDAO()) {
        List<RecensioneBean> recensioni = (List<RecensioneBean>) recensioneDAO.doRetrieveByProduct(prodotto.getId());

        if (recensioni.isEmpty()) { %>
    <div class="emptyReviews">
        <i class="fas fa-ghost"></i> <!-- Icona di recensioni vuote -->
        <h3 style="font-style: italic">Non ci sono recensioni per questo prodotto. Sii il primo a recensire!</h3><br>
    </div>
    <% } else {
        for (RecensioneBean recensione : recensioni) { %>
    <div class="review">
        <% if (sessionFacade.isLoggedIn() && sessionFacade.getIsAdmin()) { %>
        <form action="${pageContext.request.contextPath}/deleteReviewServlet" method="post">
            <input type="hidden" id="productId" name="productId" value="<%=prodotto.getId()%>">
            <input type="hidden" name="reviewId" value="<%=recensione.getId()%>">
            <input type="submit" class="deleteReviewButton" value="Elimina">
        </form>
        <% } %>
        <h4>Utente n.<%=recensione.getIdUtente()%>
        </h4>
        <h3 style="display: inline-block"><%=recensione.getTitolo()%>
        </h3> <input class="rating inline" max="5" step="0.5" type="range" name="valutazione" disabled
                     style="--starsize: 1.5rem; --value: <%=recensione.getValutazione()%>">

        <style>
            .inline {
                display: inline-block;
                vertical-align: text-bottom;
            }
        </style>

        <p><%=recensione.getCommento()%>
        </p>
        <p>Data: <%=recensione.getData()%>
        </p>
    </div>
    <% }
    }
    } catch (Exception e) {
        e.printStackTrace();
    }%>
</div>

<%-- Visualizza i prodotti consigliati --%>
<h2 style="font-style: italic">Potrebbe piacerti...</h2>
<script src="${pageContext.request.contextPath}/scripts/scrollController.js"></script>
<div class="scroll-container">
    <button class="scroll-button left"><i class="fas fa-chevron-left"></i></button>
    <%@include file="productAdvices.jsp" %>
    <button class="scroll-button right"><i class="fas fa-chevron-right"></i></button>
</div>

<script src="${pageContext.request.contextPath}/scripts/addToCart.js" type="module"></script>

<%@ include file="footer.jsp" %>
</body>
</html>

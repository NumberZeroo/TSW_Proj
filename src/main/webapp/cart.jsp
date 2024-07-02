 <%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="model.resolvedCartItem.ResolvedCartItemDAO" %>
<%@ page import="model.resolvedCartItem.ResolvedCartItemBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <meta charset="UTF-16">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/cart.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/popupFeedback.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/checkoutBtn.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/defaultProductAdvices.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/scrollableContainer.css">
    <script src="${pageContext.request.contextPath}/scripts/scrollController.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>

    <%@include file="navbar.jsp"%>
    <script type="module">
        import {incrementQuantity, decrementQuantity} from "${pageContext.request.contextPath}/scripts/cart.js";
        window.incrementQuantity = incrementQuantity;
        window.decrementQuantity = decrementQuantity;
    </script>

    <script type="module">
        import {showNotification} from "${pageContext.request.contextPath}/scripts/notification.js";
        document.addEventListener('DOMContentLoaded', function() {
            const urlParams = new URLSearchParams(window.location.search);
            const error = urlParams.get('error');
            if (error){
                showNotification("Errore", 'error');
            }
        });
    </script>

    <div id="notification"></div>
    <%
        SessionFacade userSession = new SessionFacade(request.getSession());
        Map<Long, Integer> productIDs = userSession.getCartProducts();

        Map<ResolvedCartItemBean, Integer> products = new HashMap<>();
        try(ResolvedCartItemDAO resolvedCartItemDAO = new ResolvedCartItemDAO()) {
            products = resolvedCartItemDAO.doRetrieveByProductIds(productIDs, userSession.getCartId());
        } catch (SQLException s){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            s.printStackTrace();
            return;
            // TODO: log
        }
    %>

    <div id="class-container">

    <%
        for (Map.Entry<ResolvedCartItemBean, Integer> entry : products.entrySet()) {
    %>
        <div class="item-box">
            <div class="item-image">
                <img src="<%=entry.getKey().getImgPath()%>" alt="Immagine non disponibile">
            </div>
            <div class="item-details">
                <h3 class="item-name"><%=entry.getKey().getNome()%></h3>
                <p class="item-description"><%=entry.getKey().getDescrizione()%></p>
                <p class="item-price"><%=entry.getKey().getPrezzo()%></p>
                <div class="item-quantity">
                    Quantità:
                    <button class="quantity-btn" onclick="decrementQuantity(this)">-</button>
                    <span class="quantity-value" data-id="<%=entry.getKey().getIdCartItem()%>"><%=entry.getValue()%></span>
                    <button class="quantity-btn" onclick="incrementQuantity(this)">+</button>
                </div>
            </div>

            <a href="<%=request.getContextPath() + "/removeFromCart?id=" + entry.getKey().getIdProdotto()%>">Rimuovi</a>
        </div>
        <%}%>
    </div>

    <% if(!products.isEmpty()) { %>
    <a href="${pageContext.request.contextPath}/checkout.jsp">
        <button id="checkout-btn"><span>Check out</span></button>
    </a>
    <% } else { %>
    <div class="emptyCart" style="text-align: center;">
        <i class="fas fa-shopping-cart fa-5x"></i>
        <h3>Il carrello è vuoto</h3>
        <p>Aggiungi qualcosa al carrello!</p>
        <a href="${pageContext.request.contextPath}/mostraCatalogoServlet">
            <button>Vai ai prodotti</button>
        </a>
    </div>
    <% } %>

    <h2>Potrebbe piacerti...</h2>
    <div class="scroll-container">
        <button class="scroll-button left"><i class="fas fa-chevron-left"></i></button>
        <%@include file="productAdvices.jsp"%>
        <button class="scroll-button right"><i class="fas fa-chevron-right"></i></button>
    </div>


    <%@include file="footer.jsp"%>
</body>
</html>

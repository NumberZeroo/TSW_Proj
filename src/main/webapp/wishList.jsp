<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
<%@ page import="model.wishlist.WishlistDAO" %>
<%@ page import="com.tswproject.tswproj.RuntimeSQLException" %>
<%@ page import="model.wishlist.WishlistBean" %>
<%@ page import="model.wishlistItem.WishlistItemDAO" %>
<%@ page import="model.wishlistItem.WishlistItemBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Wishlist</title>
    <meta charset="UTF-16">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/cart.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/popupFeedback.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/defaultProductAdvices.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/scrollableContainer.css">
    <script src="${pageContext.request.contextPath}/scripts/scrollController.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>

<%@include file="navbar.jsp" %>

<div id="notification"></div>
<%
    SessionFacade userSession = new SessionFacade(request.getSession());
    List<WishlistItemBean> wishlistItems = null;
    List<ProdottoBean> products = new ArrayList<>();

    try (WishlistDAO wishlistDAO = new WishlistDAO();
         WishlistItemDAO wishlistItemDAO = new WishlistItemDAO();
         ProdottoDAO prodottoDAO = new ProdottoDAO()) {

        WishlistBean wishlist = wishlistDAO.doRetrieveByUser(userSession.getUserId());
        wishlistItems = wishlistItemDAO.doRetrieveByWishlistId(wishlist.getId());

        for (WishlistItemBean wishlistItem : wishlistItems) {
            ProdottoBean product = prodottoDAO.doRetrieveByKey(wishlistItem.getProductId());
            products.add(product);
        }

    } catch (SQLException e) {
        throw new RuntimeSQLException("Errore nel recuperare i dati della wishlist", e);
    }
%>

<div id="class-container">
    <% for (ProdottoBean product : products) { %>
    <div class="item-box">
        <div class="item-image">
            <img src="<%=product.getImgPath()%>" alt="Immagine non disponibile">
        </div>
        <div class="item-details">
            <h3 class="item-name"><%=product.getNome()%>
            </h3>
            <p class="item-description"><%=product.getDescrizione()%>
            </p>
            <p class="item-price"><%=product.getPrezzo()%>
            </p>
        </div>

        <a href="<%=request.getContextPath() + "/removeFromWishlist?id=" + product.getId()%>"><i
                class="fas fa-trash"></i></a>
    </div>
    <% } %>
</div>

<% if (products.isEmpty()) { %>
<div class="emptyCart" style="text-align: center;">
    <i class="fas fa-heart fa-5x"></i>
    <h3>La tua wishlist è vuota</h3>
    <p>Aggiungi qualcosa alla tua wishlist!</p>
    <a href="${pageContext.request.contextPath}/mostraCatalogoServlet">
        <button>Vai ai prodotti</button>
    </a>
</div>
<% } %>

<div class="section" style="padding: 10px">
    <h2>Potrebbe piacerti...</h2>
    <div class="scroll-container">
        <button class="scroll-button left"><i class="fas fa-chevron-left"></i></button>
        <%@include file="productAdvices.jsp" %>
        <button class="scroll-button right"><i class="fas fa-chevron-right"></i></button>
    </div>
</div>

<%@include file="footer.jsp" %>
</body>
</html>
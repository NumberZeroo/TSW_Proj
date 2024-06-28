<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.infoConsegna.InfoConsegnaDAO" %>
<%@ page import="model.infoConsegna.InfoConsegnaBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tswproject.tswproj.RuntimeSQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
</head>
<body>
    <%@include file="navbar.jsp"%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/checkout.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/popoutShipmentInfos.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/popupFeedback.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/checkoutBtn.css">
    <script src="${pageContext.request.contextPath}/scripts/checkout.js" type="module"></script>
    <script src="${pageContext.request.contextPath}/scripts/addShipmentInfo.js" type="module"></script>

    <div id="notification"></div>

    <!-- Popup per l'aggiunta di informazioni di spedizione -->
    <!-- hidden di default -->
    <div id="popupOverlay" class="overlay-container">
        <div class="popup-box">
            <span class="close-popup" onclick="togglePopup()">&times;</span>
            <form id="popout-form-container">
                <label class="form-label" for="destinatario">Destinatario</label>
                <input class="form-input" type="text" id="destinatario" name="destinatario" placeholder="Destinatario" required>
                <label class="form-label" for="citta">Città: </label>
                <input class="form-input" type="text" id="citta" name="citta" placeholder="Città" required>
                <label class="form-label" for="via">Via: </label>
                <input class="form-input" type="text" id="via" name="via" placeholder="Via" required>
                <label class="form-label" for="cap">CAP: </label>
                <input class="form-input" type="number" id="cap" name="cap" placeholder="CAP" required>
                <label class="form-label" for="altro">Informazioni aggiuntive: </label>
                <input class="form-input" type="text" id="altro" name="altro" placeholder="Informazioni aggiuntive">

                <button class="btn-popout-submit" >Aggiungi</button>
            </form>
        </div>
    </div>
    <!-- Fine popup -->

    <!-- Inizio scelta opzioni di consegna -->
    <%
        SessionFacade userSession = new SessionFacade(request.getSession());
        InfoConsegnaBean defaultInfoConsegna;
        try(InfoConsegnaDAO infoConsegnaDAO = new InfoConsegnaDAO()) {
            defaultInfoConsegna = infoConsegnaDAO.doRetrieveDefault(userSession.getUserId());
        } catch(SQLException s){
            throw new RuntimeSQLException("Problema durante la ricerca dei dati di spedizione dell'utente", s);
        }

        Map<Long, Integer> productIDs = userSession.getCartProducts(); // TODO (refactor ids): saranno id di cartItems, per la visualizzazione fai associazione
        Map<ProdottoBean, Integer> products = new HashMap<>();
        try(ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            products = prodottoDAO.doRetrieveByKeys(productIDs);
        } catch (SQLException s){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // TODO: log
        }
    %>
    <div id="outer-container">
        <div id="container">
            <div id="shipment-options">
                <h3>Riepilogo</h3>
                <% if (defaultInfoConsegna != null){ %>
                <form id = "shipment-info-form" name="shipment-selection">
                    <div class="shipment-selection">
                        <input name="g1" type="radio" class="radio_<%=defaultInfoConsegna.getId()%>" value="<%=defaultInfoConsegna.getId()%>" checked="checked">
                        <label for="radio_<%=defaultInfoConsegna.getId()%>"  class="shipment-option">
                            <strong><%=defaultInfoConsegna.getVia()%></strong> <br>
                            <%=defaultInfoConsegna.getCitta()%><br>
                            <%=defaultInfoConsegna.getDestinatario()%> <br>
                        </label>
                    </div>
                </form>
                <button id="get-shipment-infos-btn">Modifica</button>
                <button id="add-shipment-infos-btn" onclick="togglePopup()" hidden ><span>Aggiungi un metodo di spedizione</span></button>

                <%} else {%>
                <input id="default-shipment-info" type="hidden" value="1"> <!-- Flag per js -->
                <button id="add-shipment-infos-btn" onclick="togglePopup()"><span>Aggiungi un metodo di spedizione</span></button>
                <% } %>

                <form id="submit-form" method="post" action="${pageContext.request.contextPath}/checkout">
                    <input name ="selected-option" id="selected-option" type="hidden" value="-1">
                    <button id="checkout-btn" type="submit"><span>Acquista</span></button>
                    <span>
                        Prezzo totale: <%=products.entrySet().stream().mapToDouble(e -> e.getKey().getPrezzo() * e.getValue()).sum()%>
                    </span>
                </form>
            </div>

            <div id="product-details">
                <h3>Prodotti selezionati</h3>
                <% for (Map.Entry<ProdottoBean, Integer> entry : products.entrySet()) { %>
                <div class="product-details-box">
                    <p><%=entry.getKey().getNome()%></p>
                    <p>Prezzo: <%=entry.getKey().getPrezzo()%></p>
                    <p>Quantità: <%=entry.getValue()%></p>
                </div>
                <br>
                <%}%>
            </div>
        </div>
    </div>

    <script>
        function togglePopup() {
            const overlay = document.getElementById('popupOverlay');
            overlay.classList.toggle('show');
        }
    </script>

    <%@include file="footer.jsp"%>
</body>
</html>

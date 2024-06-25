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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/checkout.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/popoutShipmentInfos.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/popupFeedback.css">
    <script src="${pageContext.request.contextPath}/scripts/checkout.js" type="module"></script>
    <script src="${pageContext.request.contextPath}/scripts/addShipmentInfo.js" type="module"></script>
    <div id="notification"></div>

    <!-- Popup per l'aggiunta di informazioni di spedizione -->
    <!-- hidden di default -->
    <div id="popupOverlay" class="overlay-container">
        <div class="popup-box">
            <h2 style="color: green;">Popup Form</h2>
            <form id="popout-form-container">
                <label class="form-label" for="destinatario">Destinatario</label>
                <input class="form-input" type="text" id="destinatario" name="destinatario" required>
                <label class="form-label" for="citta">Città: </label>
                <input class="form-input" type="text" id="citta" name="citta" required>
                <label class="form-label" for="via">Via: </label>
                <input class="form-input" type="text" id="via" name="via" required>
                <label class="form-label" for="cap">CAP: </label>
                <input class="form-input" type="number" id="cap" name="cap" required>
                <label class="form-label" for="altro">Informazioni aggiuntive: </label>
                <input class="form-input" type="text" id="altro" name="altro">

                <button class="btn-popout-submit" >Submit</button>
            </form>

            <button class="btn-close-popup" onclick="togglePopup()">Close</button>
        </div>
    </div>
    <!-- Fine popup -->

    <%
        SessionFacade userSession = new SessionFacade(request.getSession());
        Map<Long, Integer> productIDs = userSession.getCartProducts();
        Map<ProdottoBean, Integer> products = new HashMap<>();
        try(ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            products = prodottoDAO.doRetrieveByKeys(productIDs);
        } catch (SQLException s){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // TODO: log
        }

        for (Map.Entry<ProdottoBean, Integer> entry : products.entrySet()) {
    %>
    <div class="product-details-box">
        <h3><%=entry.getKey().getNome()%></h3>
        <p>Quantità: <%=entry.getValue()%></p>
        <p>Prezzo singolo: <%=entry.getKey().getPrezzo()%></p>
        <p>Totale per prodotto: <%=entry.getKey().getPrezzo() * entry.getValue()%></p>
    </div>
    <br>
    <%}%>

    <p>Prezzo totale: <%=products.entrySet().stream().mapToDouble(e -> e.getKey().getPrezzo() * e.getValue()).sum()%></p>

    <%
        InfoConsegnaBean defaultInfoConsegna;
        try(InfoConsegnaDAO infoConsegnaDAO = new InfoConsegnaDAO()) {
            defaultInfoConsegna = infoConsegnaDAO.doRetrieveDefault(userSession.getUserId());
        } catch(SQLException s){
            throw new RuntimeSQLException("Problema durante la ricerca dei dati di spedizione dell'utente", s);
        }


    %>
        <div id="shipment-options">
        <%if (defaultInfoConsegna != null){%>
            <form id = "shipment-info-form" name="shipment-selection">
                <input name="g1" type="radio" id="radio_<%=defaultInfoConsegna.getId()%>" value="<%=defaultInfoConsegna.getId()%>" checked="checked">
                <div id="shipment-selection-<%=defaultInfoConsegna.getId()%>">
                    <label for="radio_<%=defaultInfoConsegna.getId()%>"  class="shipment-option">
                        <strong><%=defaultInfoConsegna.getVia()%></strong> <br>
                                <%=defaultInfoConsegna.getCitta()%><br>
                                <%=defaultInfoConsegna.getDestinatario()%> <br>
                    </label>
                </div>
            </form>
            <button id="get-shipment-infos-btn">Modifica</button>
            <button id="add-shipment-infos-btn" onclick="togglePopup()" hidden >Usa un altro metodo di spedizione</button>

            <%} else {%> <!-- TODO: testa questo caso -->
<%--            <p>Inserisci qui le informazioni di consegna</p>--%>
<%--            <!--TODO: sanifica input -->--%>
<%--            <form id="add-shipment-info-form">--%>
<%--                <label for="city-input">Città: </label><input id="city-input" type="text" name="city"> <br>--%>
<%--                <label for="cap-input">CAP: </label><input id="cap-input" type="number" name="cap"><br>--%>
<%--                <label for="address-input">Indirizzo (via, corso, ...): </label><input id="address-input" type="text" name="address"><br>--%>
<%--                <label for="additional-info-input">Informazioni di recapito aggiuntive: </label><input id="additional-info-input" type="text" name="other"><br>--%>
<%--                <label for="receiver-input">Destinatario: </label><input id="receiver-input" type="text" name="receiver"><br>--%>
<%--                <input name="willBeDefault" type="hidden" value="1">--%>
<%--                <button type="submit">Conferma</button>--%>
<%--            </form>--%>
            <input id="default-shipment-info" type="hidden" value="1"> <!-- Flag per js -->
            <button class="btn-close-popup" onclick="togglePopup()">Aggiungi un metodo di spedizione</button>
        <% } %>
        </div>

    <!-- TODO: questo bottone dovrebbe essere cliccabile solo se è stato selezionato qualcosa
                tramite js imposta il value all'id del radio selezionato -->
    <form id="submit-form" method="post" action="${pageContext.request.contextPath}/checkout">
        <input name ="selected-option" id="selected-option" type="hidden" value="-1">
        <button type="submit">Checkout</button>
    </form>

    <script>
        function togglePopup() {
            const overlay = document.getElementById('popupOverlay');
            overlay.classList.toggle('show');
        }
    </script>

</body>
</html>

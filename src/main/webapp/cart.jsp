<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="jakarta.websocket.Session" %>
<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: developer
  Date: 6/7/24
  Time: 8:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Carrello</title>
</head>
<body>
    <% // PROBLEMA: capisci come includere le quantità
        SessionFacade userSession = new SessionFacade(request.getSession());
        Map<Long, Long> productIDs = userSession.getCartProducts();
        Map<ProdottoBean, Long> products = new HashMap<>();
        try(ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            products = prodottoDAO.doRetrieveByKeys(productIDs);
        } catch (SQLException s){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // TODO: log
        }

        for (Map.Entry<ProdottoBean, Long> entry : products.entrySet()) {
    %>
    <h3><%=entry.getKey().getNome()%></h3>
    <p>Quantità: <%=entry.getValue()%></p>
    <a href="<%=request.getContextPath() + "/removeFromCart?id=" + entry.getKey().getId()%>">Rimuovi</a>
    <%}%>
</body>
</html>

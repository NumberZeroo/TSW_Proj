<%--<%@ page import="model.ordine.*" %>--%>
<%--<%@ page import="model.ordine.OrdineBean" %>--%>
<%--<%@ page import="java.util.Collection" %>--%>
<%--<%@ page import="model.orderItem.OrderItemDAO" %>--%>
<%--<%@ page import="model.orderItem.OrderItemBean" %>--%>
<%--<%@ page import="model.prodotto.ProdottoDAO" %>--%>
<%--<%@ page import="model.prodotto.ProdottoBean" %>--%>
<%--<%@ page import="java.sql.SQLException" %>--%>
<%--<%@ page import="com.tswproject.tswproj.RuntimeSQLException" %>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<div id="ordersSection" style="display: none;">--%>
<%--    <h1>Storico Ordini</h1>--%>
<%--    <%--%>
<%--        try (OrdineDAO ordineDAO = new OrdineDAO(); OrderItemDAO orderItemDAO = new OrderItemDAO()) {--%>
<%--            Collection<OrdineBean> ordini = ordineDAO.doRetrieveAll("id");--%>
<%--            for (OrdineBean ordine : ordini) {--%>
<%--                if (ordine.getIdUtente() == sessionFacade.getUserId()) {--%>
<%--    %>--%>
<%--    <div class="order-box">--%>
<%--        <div class="order-header">--%>
<%--            <p>Data Ordine: <%= ordine.getData() %>--%>
<%--            </p>--%>
<%--            <p>Totale Ordine: <%= ordine.getIdUtente() %> <!-- todo Totale Ordine:   -->--%>
<%--            </p>--%>
<%--            <p>ID Ordine: <%= ordine.getId() %>--%>
<%--            </p>--%>
<%--        </div>--%>
<%--        <%--%>
<%--            Collection<OrderItemBean> orderItems = orderItemDAO.doRetrieveAll("id");--%>
<%--            for (OrderItemBean orderItem : orderItems) {--%>
<%--                if (orderItem.getIdOrdine() == ordine.getId()) {--%>
<%--        %>--%>
<%--        <%--%>
<%--            try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {--%>
<%--                ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(orderItem.getIdProdotto());--%>
<%--        %>--%>
<%--        <div class="order-item">--%>
<%--            <% if (prodotto.isVisibile()) { %>--%>
<%--            <a href="product?id=<%= prodotto.getId() %>">--%>
<%--                <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">--%>
<%--            </a>--%>
<%--            <% } else { %>--%>
<%--            <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">--%>
<%--            <% } %>--%>
<%--            <div class="order-item-details">--%>
<%--                <p><%= prodotto.getNome() %>--%>
<%--                </p>--%>
<%--                <p><%= orderItem.getPrezzo() %>--%>
<%--                </p>--%>
<%--                <p>Quantità: <%= orderItem.getQuantita() %>--%>
<%--                </p>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <%--%>
<%--            } catch (SQLException s) {--%>
<%--                throw new RuntimeSQLException("Errore durante il recupero delle informazioni del prodotto", s);--%>
<%--            }--%>
<%--        %>--%>
<%--        <%--%>
<%--                }--%>
<%--            }--%>
<%--        %>--%>
<%--        <div class="order-footer">--%>
<%--            <button class="download-invoice">Scarica Fattura</button>--%>
<%--        </div>--%>
<%--    </div>--%>

<%--    <%--%>
<%--                }--%>
<%--            }--%>
<%--        } catch (SQLException s) {--%>
<%--            throw new RuntimeSQLException("Errore durante il recupero degli ordini", s);--%>
<%--        }--%>
<%--    %>--%>
<%--</div>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.pet.PetDAO" %>
<%@ page import="model.pet.PetBean" %>
<%@ page import="model.utente.*" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ordine.*" %>
<%@ page import="model.orderItem.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.tswproject.tswproj.RuntimeSQLException" %>
<html>
<head>
    <title>Profilo Utente</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/profile.css">
</head>
<body>
    <%@ include file="navbar.jsp" %>

    <div class="content profile-container">
        <% SessionFacade sessionFacade = new SessionFacade(request.getSession()); %>
        <div class="menu-row">
            <button id="infoButton">Informazioni</button>
            <button id="ordersButton">Storico Ordini</button>
<%--            <button id="petsButton">I Miei Pet</button>--%>
        </div>

        <div class="profile-content">
            <div class="profile-column">
                <div id="infoSection" style="display: none;">
                    <h1>Informazioni Utente</h1>
                    <% try (UtenteDAO userDAO = new UtenteDAO()) { %>
                    <% UtenteBean user = userDAO.doRetrieveByKey(sessionFacade.getUserId()); %>
                    <p>Username: <%= user.getUsername() %>
                    </p>
                    <p>Email: <%= user.getEmail() %>
                    </p>
                    <% } catch (SQLException s) {
                        throw new RuntimeSQLException("Errore durante il recupero delle informazioni utente", s);
                    }%>

                    <div class="pets">
                        <h1>I Miei Pet</h1>
                        <% PetDAO petDAO = new PetDAO(); %>
                        <% List<PetBean> pets = petDAO.doRetrieveByUser(sessionFacade.getUserId()); %>
                        <% for (PetBean pet : pets) { %>
                        <div class="pet">
                            <img alt="<%= pet.getNome() %>" src="<%= pet.getImgPath() %>">
                            <h4>Taglia: <%= pet.getTaglia() %>
                            </h4>
                            <h4>Sterilizzato: <%= pet.getSterilizzato() ? "Sì" : "No" %>
                            </h4>
                            <h4>Data di nascita: <%= pet.getDataNascita() %>
                            </h4>
                        </div>
                        <% } %>
                    </div>
                </div>

                <div id="ordersSection" style="display: none;">
                    <h1>Storico Ordini</h1>
                    <%
                        try (OrdineDAO ordineDAO = new OrdineDAO(); OrderItemDAO orderItemDAO = new OrderItemDAO()) {
                            Collection<OrdineBean> ordini = ordineDAO.doRetrieveAll("id");
                            for (OrdineBean ordine : ordini) {
                                if (ordine.getIdUtente() == sessionFacade.getUserId()) {
                    %>
                    <div class="order-box">
                        <div class="order-header">
                            <p>Data Ordine: <%= ordine.getData() %>
                            </p>
                            <p>Totale Ordine: <%= ordine.getIdUtente() %> <!-- todo campo totale -->
                            </p>
                            <p>ID Ordine: <%= ordine.getId() %>
                            </p>
                        </div>
                        <%
                            Collection<OrderItemBean> orderItems = orderItemDAO.doRetrieveAll("id");
                            for (OrderItemBean orderItem : orderItems) {
                                if (orderItem.getIdOrdine() == ordine.getId()) {
                        %>
                        <div class="order-item">
                            <img src="${pageContext.request.contextPath}/img/placeholder.png"
                                 alt="<%= orderItem.getIdProdotto() %>"> <!-- todo come prendo l'immagine? -->
                            <div class="order-item-details">
                                <p>Nome: <%= orderItem.getId() %>
                                </p> <!-- todo come prendo il nome? -->
                                <p>Prezzo: <%= orderItem.getPrezzo() %>
                                </p>
                                <p>Quantità: <%= orderItem.getQuantita() %>
                                </p>
                            </div>
                        </div>
                        <%
                                }
                            }
                        %>
                        <div class="order-footer">
                            <button class="download-invoice">Scarica Fattura</button>
                        </div>
                    </div>

                    <%
                                }
                            }
                        } catch (SQLException s) {
                            throw new RuntimeSQLException("Errore durante il recupero degli ordini", s);
                        }
                    %>
                </div>

<%--                <div id="petsSection" style="display: none;">--%>
<%--                    <h1>I Miei Pet</h1>--%>
<%--                    <% PetDAO petDAO = new PetDAO(); %>--%>
<%--                    <% List<PetBean> pets = petDAO.doRetrieveByUser(sessionFacade.getUserId()); %>--%>
<%--                    <% for (PetBean pet : pets) { %>--%>
<%--                    <div class="pet">--%>
<%--                        <img alt="<%= pet.getNome() %>" src="<%= pet.getImgPath() %>">--%>
<%--                        <h4>Taglia: <%= pet.getTaglia() %>--%>
<%--                        </h4>--%>
<%--                        <h4>Sterilizzato: <%= pet.getSterilizzato() ? "Sì" : "No" %>--%>
<%--                        </h4>--%>
<%--                        <h4>Data di nascita: <%= pet.getDataNascita() %>--%>
<%--                        </h4>--%>
<%--                    </div>--%>
<%--                    <% } %>--%>
<%--                </div>--%>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>

    <script>
        // Seleziona i pulsanti e le sezioni
        let infoButton = document.getElementById('infoButton');
        let ordersButton = document.getElementById('ordersButton');
        // let petsButton = document.getElementById('petsButton');

        let infoSection = document.getElementById('infoSection');
        let ordersSection = document.getElementById('ordersSection');
        // let petsSection = document.getElementById('petsSection');

        // Funzione per nascondere tutte le sezioni
        function hideAllSections() {
            infoSection.style.display = 'none';
            ordersSection.style.display = 'none';
            // petsSection.style.display = 'none';
        }

        // Funzione per mostrare una sezione specifica
        function showSection(sectionId) {
            hideAllSections();
            let section = document.getElementById(sectionId);
            section.style.display = 'block';
        }

        // Aggiungi gestori di eventi di click ai pulsanti
        infoButton.addEventListener('click', function() {
            showSection('infoSection');
        });

        ordersButton.addEventListener('click', function() {
            showSection('ordersSection');
        });

        // petsButton.addEventListener('click', function() {
        //     showSection('petsSection');
        // });
    </script>
</body>
</html>
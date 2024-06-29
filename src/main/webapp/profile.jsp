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
<%@ page import="model.prodotto.*" %>
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
                    <div class="pet-header">
                        <h1>I Miei Pet</h1>
                        <button class="addPetButton" id="addPetButton">Aggiungi Pet</button>
                        <script>
                            document.getElementById('addPetButton').addEventListener('click', function () {
                                window.location.href = 'addPet.jsp';
                            });
                        </script>
                    </div>
                    <% try(PetDAO petDAO = new PetDAO()){ %>
                    <% List<PetBean> pets = petDAO.doRetrieveByUser(sessionFacade.getUserId()); %>
                    <% if (!pets.isEmpty()) { %>
                    <table>
                        <thead>
                        <tr>
                            <th></th> <!-- Colonna per l'immagine -->
                            <th>Nome</th>
                            <th>Taglia</th>
                            <th>Sterilizzato</th>
                            <th>Data di nascita</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (PetBean pet : pets) { %>
                        <tr>
                            <td><img class="pet-image" src="<%= pet.getImgPath() %>" alt="<%= pet.getNome() %>">
                            </td> <!-- Immagine del pet -->
                            <td><%= pet.getNome() %>
                            </td>
                            <td><%= pet.getTaglia() %>
                            </td>
                            <td><%= pet.getSterilizzato() ? "Sì" : "No" %>
                            </td>
                            <td><%= pet.getDataNascita() %>
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                    <% } }catch (SQLException s){
                        throw new RuntimeSQLException("Errore durante il retrieve dei pets", s); // TODO: log
                    }%>

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
                        <p>Totale Ordine: <%= ordine.getIdUtente() %> <!-- todo Totale Ordine:   -->
                        </p>
                        <p>ID Ordine: <%= ordine.getId() %>
                        </p>
                    </div>
                    <%
                        Collection<OrderItemBean> orderItems = orderItemDAO.doRetrieveAll("id");
                        for (OrderItemBean orderItem : orderItems) {
                            if (orderItem.getIdOrdine() == ordine.getId()) {
                    %>
                    <%
                        try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {
                            ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(orderItem.getIdProdotto());
                    %>
                    <div class="order-item">
                        <% if (prodotto.isVisibile()) { %>
                        <a href="product?id=<%= prodotto.getId() %>">
                            <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
                        </a>
                        <% } else { %>
                        <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
                        <% } %>
                        <div class="order-item-details">
                            <p><%= prodotto.getNome() %>
                            </p>
                            <p><%= orderItem.getPrezzo() %>
                            </p>
                            <p>Quantità: <%= orderItem.getQuantita() %>
                            </p>
                        </div>
                    </div>
                    <%
                        } catch (SQLException s) {
                            throw new RuntimeSQLException("Errore durante il recupero delle informazioni del prodotto", s);
                        }
                    %>
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
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

<script>
    // Seleziona i pulsanti e le sezioni
    let infoButton = document.getElementById('infoButton');
    let ordersButton = document.getElementById('ordersButton');

    let infoSection = document.getElementById('infoSection');
    let ordersSection = document.getElementById('ordersSection');

    // Funzione per nascondere tutte le sezioni
    function hideAllSections() {
        infoSection.style.display = 'none';
        ordersSection.style.display = 'none';
    }

    // Funzione per mostrare una sezione specifica
    function showSection(sectionId) {
        hideAllSections();
        let section = document.getElementById(sectionId);
        section.style.display = 'block';
    }

    // Aggiungi gestori di eventi di click ai pulsanti
    infoButton.addEventListener('click', function () {
        showSection('infoSection');
    });

    ordersButton.addEventListener('click', function () {
        showSection('ordersSection');
    });

    showSection('infoSection');
</script>
</body>
</html>
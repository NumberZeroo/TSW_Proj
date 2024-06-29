<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aggiungi Prodotto</title>
    <%@ include file="navbar.jsp" %>
    <script src="${pageContext.request.contextPath}/scripts/addProduct.js" type="module"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/popupFeedback.css">
</head>
<body>

<div id="notification"></div>
<form action="addProductServlet" method="post" class="form-container form-grid">
        <h2 style="font-style: italic; color: #50b3a2;">Aggiungi un nuovo prodotto</h2>

        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>

        <label for="descrizione">Descrizione:</label>
        <textarea id="descrizione" name="descrizione" required></textarea>

        <label for="prezzo">Prezzo:</label>
        <input type="number" id="prezzo" name="prezzo" min="0" required>

        <label for="animalRace">Tipo di animale:</label>
        <select id="animalRace" name="animalRace">
            <option value="1">Cane</option>
            <option value="2">Gatto</option>
            <option value="3">Volatile</option>
        </select>

        <label for="category">Categoria:</label>
        <select id="category" name="category">
            <option value="Giocattoli">Giocattoli</option>
            <option value="Alimentari">Alimentari</option>
        </select>

        <label for="size">Taglia:</label>
        <select id="size" name="size">
            <option value="PICCOLA">Piccola</option>
            <option value="MEDIA">Media</option>
            <option value="GRANDE">Grande</option>
        </select>

        <label for="minEta">Età minima consigliata:</label>
        <input type="number" id="minEta" name="minEta"  min="0" required>

        <label for="maxEta">Età massima consigliata:</label>
        <input type="number" id="maxEta" name="maxEta" min="0" required>

        <label for="disponibilita">Disponibilità:</label>
        <input type="number" id="disponibilita" name="disponibilita" min="1" required>

        <label for="imgPath">Carica immagine:</label>
        <input type="file" id="imgPath" name="imgPath" accept="image/*" required>

        <label for="sterilized">Sterilizzato:</label>
        <select id="sterilized" name="sterilized">
            <option value="0">No</option>
            <option value="1">Sì</option>
        </select>

        <input type="submit" value="Aggiungi Prodotto" class="center-button-add">
    </form>

    <%@ include file="footer.jsp" %>

    <style>
        #sterilized, label[for="sterilized"] {
        display: none;
    }
    </style>
</body>
</html>

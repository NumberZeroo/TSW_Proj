<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aggiungi Prodotto</title>
    <%@ include file="navbar.jsp" %>
</head>
<body>
<form action="addPetServlet" method="post" class="form-container form-grid">
    <h2 style="font-style: italic; color: #50b3a2;">Aggiungi un nuovo pet</h2>

    <label for="nome">Nome:</label>
    <input autofocus type="text" id="nome" name="nome" required>

    <label for="animalRace">Tipo di animale:</label>
    <select id="animalRace" name="animalRace">
        <option value="1">Cane</option>
        <option value="2">Gatto</option>
        <option value="3">Volatile</option>
    </select>

    <label for="size">Taglia:</label>
    <select id="size" name="size">
        <option value="PICCOLA">Piccola</option>
        <option value="MEDIA">Media</option>
        <option value="GRANDE">Grande</option>
    </select>

    <label for="birthDate">Data di nascita:</label>
    <input type="date" id="birthDate" name="birthDate" required>

    <label for="sterilized">Sterilizzato:</label>
    <select id="sterilized" name="sterilized">
        <option value="0">No</option>
        <option value="1">Sì</option>
    </select>

    <label for="imgPath">Carica immagine:</label>
    <input type="file" id="imgPath" name="imgPath" accept="image/*" required>

    <input type="submit" value="Aggiungi pet" class="center-button-add">
</form>

<%@ include file="footer.jsp" %>
</body>
</html>

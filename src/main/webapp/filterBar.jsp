<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/filterBar.css">

<div id="filter-bar" class="filter-bar">
    <i id="close-button" class="fas fa-times"></i>
    <form action="${pageContext.request.contextPath}/mostraCatalogoServlet" method="get">
        <label for="price">Prezzo: </label> <span id="price-value"></span>
        <input type="range" id="price" name="price" min="0" max="100">

        <label for="size">Taglia:</label>
        <select id="size" name="size">
            <option value="PICCOLA">Piccola</option>
            <option value="MEDIA">Media</option>
            <option value="GRANDE">Grande</option>
        </select>

        <label for="category">Categoria:</label>
        <select id="category" name="category">
            <option value="Alimentari">Alimentari</option>
            <option value="Giocattoli">Giocattoli</option>
            <option value="Cat3">Cat3</option>
        </select>

        <label for="animalRace">Tipo di animale:</label>
        <select id="animalRace" name="animalRace">
            <option value="1">Cane</option>
            <option value="2">Gatto</option>
            <option value="3">Volatile</option>
        </select>

        <!-- Filtro per età minima -->
        <label for="min-age">Età Minima: </label><span id="min-age-value"></span>
        <input type="range" id="min-age" name="min-age" min="0" max="20">

        <!-- Filtro per età massima -->
        <label for="max-age">Età Massima: </label><span id="max-age-value"></span>
        <input type="range" id="max-age" name="max-age" min="0" max="30">

        <!-- Filtro per sterilizzazione -->
        <label for="sterilized">Sterilizzato:</label>
        <select id="sterilized" name="sterilized">
            <option value="1">Sì</option>
            <option value="0">No</option>
        </select>

        <input type="submit" value="Applica filtri">
    </form>
</div>
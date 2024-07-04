let filterButton = document.getElementById('filter-button');
let filterBar = document.getElementById('filter-bar');
let closeButton = document.getElementById('close-button');
let productGrid = document.querySelector('.product-grid-new');

// Aggiungi un gestore di eventi al pulsante Filtra per mostrare o nascondere la barra dei filtri
filterButton.addEventListener('click', function () {
    filterBar.classList.toggle('show');
    productGrid.classList.toggle('shift-left');
    filterButton.classList.add('display-none');
    if (filterBar.classList.contains('show')) {
        filterBar.style.position = 'absolute';
    } else {
        filterBar.style.position = 'fixed';
    }
});

// Aggiungi un gestore di eventi al pulsante Chiudi per nascondere la barra dei filtri
closeButton.addEventListener('click', function () {
    filterBar.classList.remove('show');
    filterButton.classList.remove('display-none');
    productGrid.classList.remove('shift-left');
    filterBar.style.position = 'fixed';
});

// Seleziona i filtri e gli elementi <span>
let priceFilter = document.getElementById('price');
let priceValue = document.getElementById('price-value');

// Aggiorna il contenuto degli elementi <span> con il valore corrente del filtro
priceValue.textContent = priceFilter.value;

// Aggiungi un gestore di eventi a ciascun filtro per aggiornare il valore ogni volta che cambia
priceFilter.addEventListener('input', function () {
    priceValue.textContent = this.value;
});


<!-- Barra dei filtri a pagina rimpicciolita -->
let openFilterButton = document.getElementById('open-filter-button');

openFilterButton.addEventListener('click', function () {
    filterBar.style.display = 'block';
    openFilterButton.style.display = 'none';
});


// Seleziona l'elemento select della categoria e il filtro Sterilizzato
let categorySelect = document.getElementById('category');
let sterilizedLabel = document.querySelector('label[for="sterilized"]');
let sterilizedSelect = document.getElementById('sterilized');

// Aggiungi un gestore di eventi per l'evento change dell'elemento select della categoria
categorySelect.addEventListener('change', function () {
    // Se la categoria selezionata è "Alimentari", mostra il filtro Sterilizzato
    if (this.value === 'Alimentari') {
        sterilizedLabel.style.display = 'block';
        sterilizedSelect.style.display = 'block';
    } else {
        // Altrimenti, nascondi il filtro Sterilizzato
        sterilizedLabel.style.display = 'none';
        sterilizedSelect.style.display = 'none';
    }
});

document.getElementById('open-filter-button').addEventListener('click', function () {
    document.getElementById('filter-bar').classList.toggle('show');
});

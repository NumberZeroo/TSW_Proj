import {showNotification} from "./notification.js";

document.addEventListener('DOMContentLoaded', function() {
    var button = document.getElementsByClassName("addToCartButton")[0];

    button.addEventListener('click', function() {
        var productId = this.getAttribute('data-product-id');
        var xhr = new XMLHttpRequest();

        xhr.open('POST', 'aggiungiAlCarrello', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xhr.onreadystatechange = function() {
            var feedback = "";
            var statusType = ""
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    try{
                        var response = JSON.parse(xhr.responseText);
                    } catch(e){
                        console.log(e)
                        console.log("mannagg")
                    }
                    if (response.status === 'success') {
                        feedback = 'Prodotto aggiunto al carrello con successo!';
                        statusType = 'success';
                    } else {
                        feedback = 'Errore nell\'aggiunta al carrello. Riprova.';
                        statusType = 'error';
                    }
                } else {
                    feedback = 'Errore nella comunicazione con il server.';
                    statusType = 'error';
                }
            }
            // alert(feedback)
            showNotification(feedback, statusType);
        };
        xhr.send('id=' + encodeURIComponent(productId));
    });
});
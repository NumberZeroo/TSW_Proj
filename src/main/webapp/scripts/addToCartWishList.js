import {showNotification} from "./notification.js";

document.addEventListener('DOMContentLoaded', function () {
    var addToCartButtons = document.getElementsByClassName("addToCartBtn");
    for (var i = 0; i < addToCartButtons.length; i++) {
        addToCartButtons[i].addEventListener('click', function () {
            var productId = this.getAttribute('data-product-id');
            var quantityId = this.getAttribute('data-quantity-id');
            var quantityInput = document.getElementById(quantityId);
            var quantity = parseInt(quantityInput.value);

            var min = parseInt(quantityInput.getAttribute('min'));
            var max = parseInt(quantityInput.getAttribute('max'));

            if (quantity < min || quantity > max || isNaN(quantity)) {
                this.disabled = true;
                return;
            } else {
                this.disabled = false;
            }

            var xhr = new XMLHttpRequest();

            xhr.open('POST', 'aggiungiAlCarrello', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xhr.onreadystatechange = function () {
                var feedback = "";
                var statusType = ""
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        try {
                            var response = JSON.parse(xhr.responseText);
                        } catch (e) {
                            console.log(e)
                            console.log("mannagg")
                        }
                        if (response.status === 'success') {
                            feedback = 'Prodotto aggiunto al carrello!';
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
                showNotification(feedback, statusType);
            };
            var body = 'id=' + encodeURIComponent(productId) + '&quantity=' + encodeURIComponent(quantity)
            xhr.send(body);
        });
    }
});
import {showNotification} from "./notification.js";

paypal.Buttons({
    createOrder: function (data) {
        let price = document.getElementById("total-price").innerHTML;
        return fetch("api/orders", {
            method: "POST",
            body: JSON.stringify({
                paymentSource: data.paymentSource,
                totalPrice: price,
            }),
        })
            .then((response) => response.json())
            .then((order) => order.id);
    },
    onApprove: function (data) {
        let option;
        // Cerca il metodo di pagamento selezionato
        document.getElementById("shipment-info-form")
            .querySelectorAll("input")
            .forEach(function(radio) {
                if (radio.checked) {
                    option = radio.value;
                }
        })
        return fetch(`api/orders/${data.orderID}/capture`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                selectedOption: option,
            })
        })
            .then((response) => response.json())
            .then((orderData) => {
                // Successful capture! For dev/demo purposes:
                if (orderData['status'] === 'COMPLETED'){
                    window.location.href = "greetings.jsp";
                } else {
                    showNotification("Errore backend", "error");
                }
            });
    },
    onError: function (error) {
        showNotification("Errore di rete", "error");
    },
}).render("#paypal-button-container");

import {showNotification} from "./notification.js";


function changeQuantity(productId, quantity) {
    return new Promise(function(resolve, reject){
        let xhr = new XMLHttpRequest()
        xhr.open("POST", "changeQuantity", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function (){
            if (xhr.readyState === XMLHttpRequest.DONE){
                if (xhr.status === 200)
                    resolve(true);
                else
                    reject(false);
            }
        }

        let queryString = "id=" + productId + "&quantity=" + quantity;
        xhr.send(queryString);
    });
}

export function incrementQuantity(button) {
    const quantitySpan = button.previousElementSibling;
    let quantity = parseInt(quantitySpan.textContent, 10);
    let productId = parseInt(quantitySpan.getAttribute("data-id"));
    changeQuantity(productId, quantity + 1).then(function(e) {
        quantitySpan.textContent = String(quantity + 1);
    }).catch(function (err){
        showNotification("Errore backend", "error");
    })

}

export function decrementQuantity(button) {
    const quantitySpan = button.nextElementSibling;
    let productId = parseInt(quantitySpan.getAttribute("data-id"));
    let quantity = parseInt(quantitySpan.textContent, 10);
    if (quantity <= 1) {
        return;
    }
    changeQuantity(productId, quantity - 1).then(function(e) {
        quantitySpan.textContent = String(quantity - 1);
    }).catch(function (err){
        showNotification("Errore backend", "error");
    })
}


document.addEventListener('DOMContentLoaded', function() {
    function retrieveShipmentInfos() {

        return new Promise(function(resolve, reject) {
            const xhr = new XMLHttpRequest();
            xhr.open('GET', 'getShipmentInfos', true);
            xhr.setRequestHeader('Accept', 'application/json');

            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        resolve(JSON.parse(xhr.responseText));
                    } else {
                        reject("error: " + xhr.responseText);
                    }
                }
            }
            xhr.send();
        })
    }

    function addToPage(infos) {
         infos.forEach(function(item) {
            var radio = document.createElement('input');
            radio.name = "g1";
            radio.type = "radio";
            radio.id = "radio_" + item['id'];
            radio.value = item['id'];

            var outerDiv = document.createElement('div');
            outerDiv.id = "shipment-selection-" + item['id'];

            var label = document.createElement('label');
            label.for = "radio_" + item['id'];
            label.class = "shipment-option";
            label.innerHTML = "<strong>" + item['via'] + "</strong><br>" +
                item['citta'] + "<br>" +
                item['destinatario'] + "<br>";

            // TODO: se non esiste crea
            var outerForm = document.getElementById("shipment-info-form");

            outerDiv.appendChild(label);
            outerForm.appendChild(radio);
            outerForm.appendChild(outerDiv);

        })
    }

    var addShipmentInfosBtn = document.getElementById("add-shipment-infos-btn");
    addShipmentInfosBtn.addEventListener("click", function (){
        console.log("Implementa");
    });

    var getShipmentInfosBtn = document.getElementById("get-shipment-infos-btn");
    getShipmentInfosBtn.addEventListener('click', function() {
        retrieveShipmentInfos().then(function(shipmentInfos) {
            addToPage(shipmentInfos);
            getShipmentInfosBtn.hidden = true;
            addShipmentInfosBtn.hidden = false;
        }).catch(function(err) {
            console.error(err);
        });
    })
});
function addElementToPage(item){
        var radio = document.createElement('input');
        radio.name = "g1";
        radio.type = "radio";
        radio.id = "radio_" + item['id'];
        radio.value = item['id'];
        if (item['isDefault'] === true){
            radio.checked = true
        }

        var outerDiv = document.createElement('div');
        outerDiv.classList.add("shipment-selection");

        var label = document.createElement('label');
        label.htmlFor = "radio_" + item['id'];
        label.classList.add("shipment-option");
        label.innerHTML = "<strong>" + item['via'] + "</strong><br>" +
            item['citta'] + "<br>" +
            item['destinatario'] + "<br>";

        var outerForm = document.getElementById("shipment-info-form");
        if (outerForm == null) {
            outerForm = document.createElement("form");
            outerForm.id = "shipment-info-form";
            outerForm.name = "shipment-selection";
            document.getElementById("added-form-container").appendChild(outerForm);
        }

        outerDiv.appendChild(radio);
        outerDiv.appendChild(label);
        outerForm.appendChild(outerDiv);
}

/**
 * Modifica "shipment-info-form" per contenere tutte le informazioni di spedizione
 *  tranne quella di default (giò nella pagina)
 * @param infos array di object
 */
export function updatePage(infos) {
    let isFirstShipmentInfo = document.getElementById("default-shipment-info") != null;
    if (isFirstShipmentInfo){
        addElementToPage(infos[0])
    } else {
        infos.forEach(function (item) {
            if (item['isDefault'] !== 'true'){
                addElementToPage(item);
            }
        });
    }
    // document.getElementById("checkout-btn").disabled = false;
}

document.addEventListener('DOMContentLoaded', function () {
    if (document.getElementsByClassName("shipment-selection").length <= 0){
        document.getElementById("checkout-btn").disabled = true;
    }


    function retrieveShipmentInfos() {

        return new Promise(function (resolve, reject) {
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

    var addShipmentInfosBtn = document.getElementById("add-shipment-infos-btn");
    var getShipmentInfosBtn = document.getElementById("get-shipment-infos-btn");
    if (getShipmentInfosBtn != null){
        getShipmentInfosBtn.addEventListener('click', function () {
            retrieveShipmentInfos().then(function (shipmentInfos) {
                updatePage(shipmentInfos);
                getShipmentInfosBtn.hidden = true;
                addShipmentInfosBtn.hidden = false;
            }).catch(function (err) {
                console.error(err);
            });
        });
    }
});
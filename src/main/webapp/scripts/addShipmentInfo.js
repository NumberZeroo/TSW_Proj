import {showNotification} from "./notification.js";
import {updatePage} from "./checkout.js";

document.addEventListener('DOMContentLoaded', function () {

    /**
     * Questa funzione prende i dati inseriti dal form "popout-form-container" e li manda in formato json alla servlet
     *  "addShipmentInfo". La risposta contiene l'esito e, se con successo, l'id del nuovo record nel db.
     * Chiama la funzione "updatePage" per aggiornare il contenitore delle informazioni di spedizione.
     */
    function handleForm() {
        const form = document.getElementById("popout-form-container");

        let formData = new FormData(form);
        let jsonObject = {};

        formData.forEach(function(value, key) {
            jsonObject[key] = value;
        });

        if (document.getElementById("default-shipment-info") == null){
            jsonObject['isDefault'] = true;
            const flag = document.createElement("input");
            flag.id = "default-shipment-info";
            flag.value = "1";
            flag.type = "hidden";
            document.getElementById("shipment-options").appendChild(flag);
        }

        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'addShipmentInfo', true);
        xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status !== 200) {
                    showNotification("Errore durante l'aggiornamento dei dati", "error");
                    return;
                }

                var response = JSON.parse(xhr.responseText);
                if (response.status === "success"){
                    showNotification("Operazione effettuata", "success");
                    jsonObject['id'] = response.id;
                    updatePage(new Array(jsonObject));
                } else {
                    showNotification("Errore durante l'aggiornamento dei dati", "error");
                }
            }
        };

        xhr.send(JSON.stringify(jsonObject));
        togglePopup();
    }

    function togglePopup() {
        const overlay = document.getElementById('popupOverlay');
        overlay.classList.toggle('show');
    }


    document.getElementById("popout-form-container").addEventListener("submit", function(e){
        if (e.preventDefault)
            e.preventDefault();

        handleForm();
        return false;
    });
});

/**
 * N.B: questo script è per la pagina di login...ma non fa login
 */

import {showNotification} from "./notification.js";


document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');
    if (error){
        showNotification("Login fallito", 'error');
    }
});
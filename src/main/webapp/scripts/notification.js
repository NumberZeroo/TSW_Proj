export function showNotification(message, type) {
    var notification = document.getElementById("notification");
    if (notification != null){
        notification.innerHTML = message;
        notification.className = type + " show";

        setTimeout(function(){
            notification.className = notification.className.replace("show", "");
        }, 3000);
    }
}
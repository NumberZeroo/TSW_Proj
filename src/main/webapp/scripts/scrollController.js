document.addEventListener("DOMContentLoaded", function () {
    const containers = document.querySelectorAll(".gallery");
    containers.forEach(function (container) {
        const scrollLeft = container.previousElementSibling;
        const scrollRight = container.nextElementSibling;

        scrollLeft.addEventListener("click", function() {
            container.scrollBy({
                top: 0,
                left: -250,
                behavior: 'smooth'
            });
        });

        scrollRight.addEventListener("click", function() {
            container.scrollBy({
                top: 0,
                left: 250,
                behavior: 'smooth'
            });
        });
    })
})
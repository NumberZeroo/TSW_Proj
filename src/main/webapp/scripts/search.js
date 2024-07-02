document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementsByClassName("search-input")[0];
    const resultBox = document.getElementsByClassName("resultBox")[0];
    const searchForm = document.getElementsByClassName('search-form')[0];

    searchInput.addEventListener('keyup', function(e){
        const xhr = new XMLHttpRequest();
        xhr.open("get", "search?q=" + encodeURIComponent(e.target.value), true);
        let suggestions = []
        xhr.onreadystatechange = function(){
            if (xhr.readyState === 4){
                if (xhr.status === 200){
                    response = JSON.parse(xhr.responseText)
                    if (response["status"] === "success")
                        showSuggestions(response["products"]);
                }
            }
        }
        let userData = e.target.value;

        if(userData){
            resultBox.hidden = false;
            xhr.send();

            searchInput.classList.add("active"); //show autocomplete box
            showSuggestions(suggestions);
            let allList = resultBox.querySelectorAll("li");
            for (let i = 0; i < allList.length; i++) {
                //adding onclick attribute in all li tag
                allList[i].setAttribute("onclick", "select(this)");
            }
        }else{
            searchInput.classList.remove("active"); //hide autocomplete box
            resultBox.hidden = true;
        }
    });

    // Nascondi o mostra in base a dove si ha cliccato
    document.addEventListener('click', (event) => {
        if (!searchForm.contains(event.target)) {
            resultBox.hidden = true;
        }
    });

    searchInput.addEventListener('focusin', function () {
        resultBox.hidden = false;
    })

    function showSuggestions(obj){
        let listData;
        if(!Object.keys(obj).length){
            listData = '<li></li>';
        }else{
            listData = Object.keys(obj).map(item => `<a href="product?id=${obj[item]}"><li>${item}</li></a>`)
            listData = listData.join('');
        }
        resultBox.innerHTML = listData;
    }
})
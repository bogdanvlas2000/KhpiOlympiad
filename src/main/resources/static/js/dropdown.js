const dropbuttons = document.getElementsByClassName('dropbtn')
for (btn of dropbuttons) {
    btn.onclick = dropdown
}

function dropdown(event) {
    let elem = event.target
    elem.nextElementSibling.classList.toggle("show")
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function (event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}
function openNav() {
    document.getElementById("sidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "260px";
    document.getElementById("openNav").style.display = "none";
    document.getElementById("closeNav").style.display = "block"
}

function closeNav() {
    document.getElementById("sidenav").style.width = "0";
    document.getElementById("main").style.marginLeft = "0px";
    document.getElementById("openNav").style.display = "block";
    document.getElementById("closeNav").style.display = "none"
}
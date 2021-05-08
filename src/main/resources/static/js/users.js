let users = []

async function loadUsers(param) {
    let url = `/api/users`
    if (param != null && param != undefined) {
        url += "?word=" + param
    }
    let response = await fetch(url)
    return await response.json()
}

function fillUsers(users) {
    let root = document.getElementById("users")
    while (root.firstChild) {
        root.removeChild(root.lastChild);
    }
    for (let u of users) {
        let tr = document.createElement("tr")
        let username = document.createElement("td")
        username.innerText = u.username
        let email = document.createElement("td")
        email.innerText = u.email
        let name = document.createElement("td")
        name.innerText = u.profile ? u.profile.name : "no info"
        let university = document.createElement("td")
        university.innerText = u.profile.university ? u.profile.university.ukrShortName : ""
        let city = document.createElement("td")
        city.innerText = u.profile.university ? u.profile.university.city.ukrName : ""
        tr.appendChild(username)
        tr.appendChild(email)
        tr.appendChild(name)
        tr.appendChild(university)
        tr.appendChild(city)
        tr.onclick = function () {
            window.location.href = "/users/" + u.id
        }
        root.appendChild(tr)
    }
}

async function onLoadUsersPage() {
    users = await loadUsers()
    fillUsers(users)
}


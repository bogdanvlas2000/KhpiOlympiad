async function loadUsers(param) {
    let url = `/api/users`
    if (param != null && param != undefined) {
        url += "?word=" + param
    }
    let response = await fetch(url)
    if (response.status == 200) {
        let users = await response.json()
        return users
    }
    throw Error(response.status)
}

async function fillUsers(param) {
    let users = await loadUsers(param)
    let root = document.getElementById("users")
    while (root.firstChild) {
        root.removeChild(root.lastChild);
    }
    users.forEach(u => {
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
        root.appendChild(tr)
    })
}

fillUsers()
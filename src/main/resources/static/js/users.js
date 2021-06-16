let users = []
const options = document.getElementsByName("options")
for (let i = 0; i < options.length; i++) {
    options[i].onchange = search
}

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
    for (let i = 0; i < users.length; i++) {
        let u = users[i]
        let tr = document.createElement("tr")
        let name = document.createElement("td")
        if (u.profile) {
            let secondName = u.profile.secondName
            let firstName = u.profile.firstName
            let patronymicName = u.profile.patronymicName
            if (secondName && firstName && patronymicName) {
                let fullName = secondName + " " + firstName.charAt(0) + ". " + patronymicName.charAt(0) + "."
                name.innerText = fullName
            }
        }
        let email = document.createElement("td")
        email.innerText = u.email
        let university = document.createElement("td")
        university.innerText = u.profile.university ? u.profile.university.ukrShortName : ""
        let city = document.createElement("td")
        city.innerText = u.profile.university ? u.profile.university.city.ukrName : ""
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

async function search() {
    let option
    for (let i = 0; i < options.length; i++) {
        if (options[i].checked) {
            option = options[i].value
        }
    }
    let filtered
    if (option == "all") {
        filtered = users
    }
    if (option == "ready") {
        filtered = users.filter(u => u.ready)
    }
    if (option == "not_ready") {
        filtered = users.filter(u => !u.ready)
    }

    let word = document.getElementById("field").value
    let pattern = ".*" + word + ".*"
    filtered = filtered.filter(u => {
        let answ1 = u.username.toLowerCase().match(pattern) ||
            u.email.toLowerCase().match(pattern)
        if (u.ready) {
            return answ1 ||
                u.profile.firstName.toLowerCase().match(pattern) ||
                u.profile.secondName.toLowerCase().match(pattern) ||
                u.profile.patronymicName.toLowerCase().match(pattern) ||
                u.profile.university.ukrName.toLowerCase().match(pattern) ||
                u.profile.university.ukrShortName.toLowerCase().match(pattern) ||
                u.profile.university.engName.toLowerCase().match(pattern) ||
                u.profile.university.city.ukrName.toLowerCase().match(pattern)
        }
        return answ1
    })
    await fillUsers(filtered)
}

async function onLoadUsersPage() {
    users = await loadUsers()
    fillUsers(users)
}




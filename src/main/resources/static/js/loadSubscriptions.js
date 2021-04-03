async function loadUserById(userId) {
    let url = `/api/user?user_id=` + userId
    let response = await fetch(url)
    if (response.status == 200) {
        let user = await response.json()
        return user
    }
    throw Error(response.status)
}


async function loadSubscriptions() {
    let eventId = document.getElementById("event_id").innerText
    let tbody = document.querySelector("tbody")
    let url = `/api/subscriptions?eventId=` + eventId
    let response = await fetch(url)
    if (response.status == 200) {
        let subscriptions = await response.json()
        for (s of subscriptions) {
            let user = await loadUserById(s.userId)
            let tr = document.createElement("tr")
            let username = document.createElement("td")
            username.innerText = user.username
            let name = document.createElement("td")
            name.innerText = user.profile.name
            let university = document.createElement("td")
            university.innerText = user.profile.university.ukrShortName
            let subscriptionDate = document.createElement("td")
            subscriptionDate.innerText = s.subscriptionDate.replace('T', ', ')
            tr.appendChild(username)
            tr.appendChild(name)
            tr.appendChild(university)
            tr.appendChild(subscriptionDate)
            tbody.appendChild(tr)
        }
    } else {
        console.log(response.status)
    }
}

if (document.getElementById("admin_div")) {
    loadSubscriptions()
}
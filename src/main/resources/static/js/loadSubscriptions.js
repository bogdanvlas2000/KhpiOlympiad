async function loadSubscriptions() {
    let eventId = document.getElementById("event_id").innerText
    let tbody = document.querySelector("tbody")
    let url = `/api/subscriptions?eventId=` + eventId
    let response = await fetch(url)
    if (response.status == 200) {
        let subscriptions = await response.json()
        subscriptions.forEach(s => {
            let user = s.user
            let tr = document.createElement("tr")
            let username = document.createElement("tr")
            username.innerText = user.username
            let name = document.createElement("tr")
            name.innerText = user.name
            let university = document.createElement("tr")
            university.innerText = user.university.ukrShortName
            let subscriptionDate = document.createElement("tr")
            subscriptionDate.innerText = s.subscriptionDate

            tr.appendChild(username)
            tr.appendChild(name)
            tr.appendChild(university)
            tr.appendChild(subscriptionDate)
            tbody.appendChild(tr)
        })
    } else {
        console.log(response.status)
    }
}

loadSubscriptions()
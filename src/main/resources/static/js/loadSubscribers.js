let subscribers = []

async function loadSubscribers() {
    let eventId = document.getElementById("event_id").innerText
    let url = `/api/subscribers?eventId=` + eventId
    let response = await fetch(url)
    if (response.status == 200) {
        let res = await response.json()
        return res
    }
}

async function onLoadEventPage() {
    if (document.getElementById("admin")) {
        subscribers = await loadSubscribers()
        fillUsers(subscribers)
    }
}

onLoadEventPage()
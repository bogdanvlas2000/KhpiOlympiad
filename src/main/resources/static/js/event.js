async function subscribe(eventId) {
    let body = {eventId: eventId}
    let url = "/api/subscription"
    let response = await fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(eventId)
    })
    let result = await response.json()
    return result
}

async function getSubscription() {
    let eventId = document.getElementById("event_id").innerText
    let body = {eventId: eventId}
    let url = "/api/subscription?eventId=" + eventId
    let response = await fetch(url)
    if (response.status == 200) {
        let result = await response.json()
        return result
    } else {
        return null
    }
}

function subscribeListener() {
    let subscribeButton = document.getElementById("subscribe_btn")
    let eventId = document.getElementById("event_id")
}


const subscribeButton = document.getElementById("subscribe_btn")
const eventId = document.getElementById("event_id").innerText

async function subscribe() {
    let body = {eventId: eventId}
    let url = "/api/subscription"
    let response = await fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(body)
    })
    subscribeButton.classList.remove("subscribe")
    subscribeButton.classList.add("unsubscribe")
    subscribeButton.innerText = "Unsubscribe"
}

async function unsubscribe() {
    let body = {eventId: eventId}
    let url = "/api/subscription"
    let response = await fetch(url, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(body)
    })
    subscribeButton.classList.remove("unsubscribe")
    subscribeButton.classList.add("subscribe")
    subscribeButton.innerText = "Subscribe"
}

async function subscribeListener() {
    if (subscribeButton.classList.contains("subscribe")) {
        await subscribe()
    } else {
        await unsubscribe()
    }
}


async function getSubscription() {
    let body = {eventId: eventId}
    let url = "/api/subscription?eventId=" + eventId
    let response = await fetch(url)
    if (response.status == 200) {
        let result = await response.json()
        console.log("wait")
        return result
    } else {
        return null
    }
}

async function loadSubscribeButton() {
    let subscription = await getSubscription()
    if (subscription == null) {
        subscribeButton.classList.add("subscribe")
        subscribeButton.classList.remove("unsubscribe")
        subscribeButton.innerText = "Subscribe"
    } else {
        subscribeButton.classList.remove("subscribe")
        subscribeButton.classList.add("unsubscribe")
        subscribeButton.innerText = "Unsubscribe"
    }
}

loadSubscribeButton()

const subscribeButton = document.getElementById("subscribe_btn")
const eventId = document.getElementById("event_id").innerText
const message = document.getElementById("message")

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
    subscribeButton.classList.remove("active")
    subscribeButton.classList.add("used")
    subscribeButton.innerText = "Unsubscribe"
    message.style.display = "block"
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
    subscribeButton.classList.remove("used")
    subscribeButton.classList.add("active")
    subscribeButton.innerText = "Subscribe"
    message.style.display = "none"
}

async function subscribeListener() {
    if (await isReady()) {
        if (subscribeButton.classList.contains("active")) {
            if (confirm("Subscribe?"))
                await subscribe()
        } else {
            if (confirm("Unsubscribe?"))
                await unsubscribe()
        }
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
        subscribeButton.classList.add("active")
        subscribeButton.classList.remove("used")
        subscribeButton.innerText = "Subscribe"
        message.style.display = "none"
    } else {
        subscribeButton.classList.remove("active")
        subscribeButton.classList.add("used")
        subscribeButton.innerText = "Unsubscribe"
        message.style.display = "block"
    }
}

if (document.getElementById("user_block")) {
    loadSubscribeButton()
}

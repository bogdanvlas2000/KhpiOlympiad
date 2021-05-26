const subscribeButton = document.getElementById("subscribe_btn")
const id = document.getElementById("event_id").innerText
const title = document.getElementById("title")
const description = document.getElementById("description")
const modified = document.getElementById("modified")
const date = document.getElementById("date")
const message = document.getElementById("message")
const dropdownContent = document.getElementById("dropdown-content")

let event

async function subscribe() {
    let body = {eventId: id}
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
    subscribeButton.innerText = "Отписаться"
    message.style.display = "block"
}

async function unsubscribe() {
    let body = {eventId: id}
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
    subscribeButton.innerText = "Подписаться"
    message.style.display = "none"
}

async function subscribeListener() {
    if (await isReady()) {
        if (subscribeButton.classList.contains("active")) {
            if (confirm("Подписаться?"))
                await subscribe()
        } else {
            if (confirm("Отписаться?"))
                await unsubscribe()
        }
    }
}


async function getSubscription() {
    let body = {eventId: id}
    let url = "/api/subscription?eventId=" + id
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
        subscribeButton.innerText = "Подписаться"
        message.style.display = "none"
    } else {
        subscribeButton.classList.remove("active")
        subscribeButton.classList.add("used")
        subscribeButton.innerText = "Отписаться"
        message.style.display = "block"
    }
}

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

async function fillEventInfo() {
    let url = "/api/event?id=" + id
    let response = await fetch(url)
    event = await response.json()
    title.innerText = event.title
    description.value = event.description
    modified.innerText = event.lastModifiedDate.replace("T", ", ")
    date.innerText = event.eventDate.replace("T", ", ")
    if (event.eventStatus != "ACTIVE") {
        message.innerText = "Это событие неактивно!"
        message.style.display = "block"
        if (subscribeButton) {
            subscribeButton.style.display = "none"
        }
    }
}

async function deleteEvent() {
    if (confirm("Удалить это событие?")) {
        let url = "/api/event/" + id
        await fetch(url, {
            method: "DELETE"
        })
        window.location.href = window.location.href.replace("/" + id, "");
    }
}

async function onLoadEventPage() {
    await fillEventInfo()

    popupHeader.innerText = "Измените это событие"
    applyButton.innerText = "Изменить"
    eventTitle.value = title.innerText
    eventDescription.value = description.value
    eventDate.value = date.innerText.replace(", ", "T")

    if (event.eventStatus == "ACTIVE") {
        if (document.getElementById("user_block")) {
            loadSubscribeButton()
        }
        if (document.getElementById("admin_block")) {
            subscribers = await loadSubscribers()
            fillUsers(subscribers)
        }
    }
}

onLoadEventPage()
const eventPopup = document.getElementById("eventPopup")
const popupHeader = eventPopup.querySelector("h4")

const eventTitle = eventPopup.querySelector("input[type='text']")
const eventDescription = eventPopup.querySelector("textarea")
const eventDate = eventPopup.querySelector("input[type='datetime-local']")

const applyButton = eventPopup.querySelector("button")

applyButton.onclick = async function () {


    let body = {
        title: eventTitle.value,
        description: eventDescription.value,
        date: eventDate.value
    }
    let eventId = document.getElementById("event_id")
    let url = "/api/event"

    if (eventId) {
        body.id = eventId.innerText
        let response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(body)
        });
        alert("Подію змінено!")
        onLoadEventPage()
    } else {
        let response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(body)
        });
        alert("Событие создано!")
        onLoadEventsPage()
    }


    eventTitle.value = ""
    eventDescription.value = ""
    eventDate.value = ""
    window.location.href = window.location.href.replace("eventPopup", "");
}

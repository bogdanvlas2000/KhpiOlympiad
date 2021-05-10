let events = []

async function loadEvents(param) {
    let url = `/api/events`
    if (param != null && param != undefined) {
        url += "?word=" + param
    }
    let response = await fetch(url)
    return await response.json()
}

function fillEvents(events) {
    let root = document.getElementById("events")
    while (root.firstChild) {
        root.removeChild(root.lastChild);
    }
    for (let e of events) {
        let event = document.createElement("div")
        event.className = "item"
        event.innerHTML = "" +
            "<div class=\"header\">\n" +
            "   <h5>\n" +
            "     <a></a>\n" +
            "   </h5>\n" +
            "</div>\n" +
            "<div>\n" +
            "   <textarea class=\"text\" readonly=\"readonly\"\n" +
            "    rows=\"4\"></textarea>\n" +
            "</div>\n" +
            "<div>\n" +
            "   <span></span>\n" +
            "</div>"
        let title = event.querySelector("a")
        title.href = "/events/" + e.id
        title.innerText = e.title
        let description = event.querySelector("textarea")
        description.value = e.description
        let date = event.querySelector("span")
        date.innerText = "Событие состоится: " + e.eventDate.split("T")[0]
        root.appendChild(event)
    }
}

async function search() {
    let word = document.getElementById("field").value
    await fillEvents(await loadEvents(word))
}

async function onLoadEventsPage() {
    events = await loadEvents()
    fillEvents(events)
}

onLoadEventsPage()


/// event popup scripts
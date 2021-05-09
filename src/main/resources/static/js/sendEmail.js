const emailPopup = document.getElementById("emailPopup")
const emailTextarea = emailPopup.querySelector("textarea")
const sendButton = emailPopup.querySelector("button")

sendButton.onclick = async function () {
    let message = emailTextarea.value

    if (message != "") {
        if (confirm("Отправить сообщение?")) {
            let users = document.getElementById("users").children
            let emails = []
            for (let u of users) {
                let email = u.firstChild.nextSibling.innerText
                emails.push(email)
            }
            let url = "/api/email"
            let body = {
                message: message,
                emails: emails
            }

            let response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(body)
            });
            if (response.status == 200) {
                console.log("Emails are sent!")
            }
            emailTextarea.value = ""
            window.location.href = window.location.href.replace("emailPopup", "");
            alert("Сообщение отправлено участникам на почту!")
        }
    } else {
        alert("Введите сообщение!")
    }
}
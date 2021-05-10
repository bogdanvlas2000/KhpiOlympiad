const username = document.getElementById("username")
const email = document.getElementById("email")
const name = document.getElementById("name")
const university = document.getElementById("university")
const city = document.getElementById("city")
const age = document.getElementById("age")
const image = document.getElementById("image")

async function fillUserInfo() {
    let response = await fetch("/api/user")
    let user = await response.json()
    username.innerText = user.username
    email.innerText = user.email
    if (user.profile) {
        name.innerText = user.profile.name
        university.innerText = user.profile.university.ukrName
        city.innerText = user.profile.university.city.ukrName
        age.innerText = user.profile.age
        if (user.profile.image) {
            image.src = 'data:image/jpeg;base64,' + user.profile.image
        }
    }
}

fillUserInfo()
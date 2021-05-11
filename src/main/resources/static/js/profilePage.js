const username = document.getElementById("username")
const email = document.getElementById("email")
const name = document.getElementById("name")
const university = document.getElementById("university")
const city = document.getElementById("city")
const gender = document.getElementById("gender")
const age = document.getElementById("age")
const image = document.getElementById("image")

const nameField = document.getElementById("nameField")
const cityField = document.getElementById("cityName")
const universityField = document.getElementById("universityName")
const ageField = document.getElementById("ageField")
const genderField = document.getElementsByName("gender")
const imageField = document.getElementsByName("imageField")

async function fillUserInfo() {
    let response = await fetch("/api/user")
    let user = await response.json()
    username.innerText = user.username
    email.innerText = user.email
    if (user.ready) {
        name.innerText = user.profile.name
        nameField.value = user.profile.name

        city.innerText = user.profile.university.city.ukrName

        university.innerText = user.profile.university.ukrName

        age.innerText = user.profile.age
        ageField.value = user.profile.age
        gender.innerText = user.profile.gender

        genderField.forEach(f => {
            if (f.value == user.profile.gender) {
                f.checked = true
            }
        })

        if (user.profile.image) {
            image.src = 'data:image/jpeg;base64,' + user.profile.image
            imageField.value = user.profile.image
        }

    }
}

fillUserInfo()

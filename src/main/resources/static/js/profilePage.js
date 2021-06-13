const id = document.getElementById("id")
const username = document.getElementById("username")
const email = document.getElementById("email")
const firstName = document.getElementById("firstName")
const patronymicName = document.getElementById("patronymicName")
const secondName = document.getElementById("secondName")
const phoneNumber = document.getElementById("phoneNumber")
const university = document.getElementById("university")
const city = document.getElementById("city")
const courseNumber = document.getElementById("courseNumber")
const gender = document.getElementById("gender")
const age = document.getElementById("age")
const image = document.getElementById("image")

const profilePopup = document.getElementById("profilePopup")
const firstNameField = document.getElementById("firstNameField")
const patronymicNameField = document.getElementById("patronymicNameField")
const secondNameField = document.getElementById("secondNameField")
const phoneNumberField = document.getElementById("phoneNumberField")
const cityField = document.getElementById("cityName")
const universityField = document.getElementById("universityName")
const universityId = document.getElementById("universityId")
const courseNumberField = document.getElementById("courseNumberField")
const ageField = document.getElementById("ageField")
const genderField = document.getElementsByName("gender")
const imageField = document.getElementById("imageField")


const oldPasswordField = document.getElementById("oldPassword")
const newPasswordField = document.getElementById("newPassword")
const confirmPasswordField = document.getElementById("confirmPassword")


function encodeImageFileAsURL(element) {
    var file = element.files[0];
    var reader = new FileReader();
    reader.onloadend = function () {
        console.log('RESULT', reader.result)
        imageBinary.value = reader.result
    }
    reader.readAsDataURL(file);
}

const applyButton = profilePopup.querySelector("button")

async function fillUserInfo() {
    let response = await fetch("/api/user/" + id.innerText)
    let user = await response.json()
    username.innerText = user.username
    email.innerText = user.email
    if (user.ready) {
        firstName.innerText = user.profile.firstName
        firstNameField.value = user.profile.firstName

        patronymicName.innerText = user.profile.patronymicName
        patronymicNameField.value = user.profile.patronymicName

        secondName.innerText = user.profile.secondName
        secondNameField.value = user.profile.secondName

        phoneNumber.innerText = user.profile.phoneNumber
        phoneNumberField.value = user.profile.phoneNumber

        city.innerText = user.profile.university.city.ukrName
        cityField.value = user.profile.university.city.ukrName

        university.innerText = user.profile.university.ukrName
        universityField.value = user.profile.university.ukrShortName
        universityId.value = user.profile.university.id

        courseNumber.innerText = user.profile.courseNumber
        courseNumberField.value = user.profile.courseNumber

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
        }
    }
    await loadCitiesAndUniversities()
}

fillUserInfo()

applyButton.onclick = async function () {
    if (secondNameField.value == "" ||
        !secondNameField.value.match("[a-zA-ZА-Яа-яЁё]+")) {
        alert("Введіть фамілію у коректонму форматі!")
        return
    }
    if (firstNameField.value == "" ||
        !firstNameField.value.match("[a-zA-ZА-Яа-яЁё]+")) {
        alert("Введіть ім'я у коректонму форматі!")
        return
    }
    if (patronymicNameField.value == "" ||
        !patronymicNameField.value.match("[a-zA-ZА-Яа-яЁё ]+")) {
        alert("Введіть по-батькові у коректонму форматі!")
        return
    }
    if (phoneNumberField.value == "" ||
        !phoneNumberField.value.match("^((\\+)?(3)?(8)?[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?\\d{3}[\\- ]?\\d{2}[\\- ]?\\d{2}$")) {
        alert("Вкажіть мобільний телефон в коректному форматі!")
        return
    }
    if (cityField.value == "") {
        alert("Оберіть місто!")
        return
    }
    if (universityField.value == "") {
        alert("Оберіть ВНЗ!")
        return
    }
    if (ageField.value == "") {
        alert("Оберіть вік!")
        return
    }
    if (courseNumberField.value == "") {
        alert("Вкажіть номер курсу!")
        return
    }
    if (!(genderField[0].checked || genderField[1].checked)) {
        alert("Оберіть стать!")
    }

    let body = {
        firstName: firstNameField.value,
        patronymicName: patronymicNameField.value,
        secondName: secondNameField.value,
        phoneNumber: phoneNumberField.value,
        universityId: universityId.value,
        courseNumber: courseNumberField.value,
        age: ageField.value
    }
    for (let i = 0; i < genderField.length; i++) {
        if (genderField[i].checked) {
            body.gender = genderField[i].value
        }
    }
    console.log(imageField)

    let files = imageField.files
    if (files.length > 0) {
        let formData = new FormData()
        formData.append("avatar", files[0])
        let response = await fetch("/api/loadAvatar", {
            method: 'POST',
            body: formData
        })
    }
    console.log(body)

    let url = "/api/profile"

    let response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(body)
    })

    if (confirm("Зберегти профіль?")) {
        alert("Інформація профілю збережена!")
        window.location.href = window.location.href.replace("profilePopup", "");
        document.location.reload();
        await fillUserInfo()
    }
}


async function matchOldPassword() {
    let url = "/profile/match_password?password=" + oldPasswordField.value
    let response = await fetch(url)
    let matches = await response.json()
    return matches
}

async function changePassword() {
    if (oldPasswordField.value == ""
        || newPasswordField.value == ""
        || confirmPasswordField.value == "") {
        alert("Заповніть необхідні дані!")
        return
    }
    if (await matchOldPassword() != true) {
        alert("Старий пароль не підтверджено!")
        return
    }
    if (newPasswordField.value != confirmPasswordField.value) {
        alert("Паролі не співпадають!")
        return
    }
    let url = "/profile/change_password"
    let response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            oldPassword: oldPasswordField.value,
            newPassword: newPasswordField.value
        })
    })
    alert("Пароль змінений успішно!")
    oldPasswordField.value = ""
    newPasswordField.value = ""
    confirmPasswordField.value = ""
    window.location.href = window.location.href.replace("passwordPopup", "");
}
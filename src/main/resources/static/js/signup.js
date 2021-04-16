let form = document.getElementById("signup_form")

async function submitForm() {
    let inputs = form.getElementsByTagName('input');
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].value == "") {
            alert("Please fill all fields");
            return false;
        }
    }
    if (await checkForm()) {
        form.submit()
        return true
    }
    return false
}

async function checkForm() {
    let username = document.getElementById("username")
    if (!username.value.match("[A-Za-z0-9_]+")) {
        username.classList.add("wrong")
        alert("Username should contain letters or digits!")
        return false
    }
    let password = document.getElementById('password')
    if (!password.value.match("[A-Za-z0-9_]{8,}")) {
        password.classList.add("wrong")
        alert("Password should contain at least 8 letters or digits!")
        return false
    }
    let confirmPassword = document.getElementById('confirm_password')
    if (confirmPassword.value != password.value) {
        confirmPassword.classList.add("wrong")
        alert("Password is not confirmed!")
        return false
    }
    let email = document.getElementById("email")
    if (!email.value.match("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")) {
        password.classList.add("wrong")
        alert("Email doesn't match!")
        return false
    }
    let users
    let response = await fetch("/api/users")
    if (response.status == 200) {
        users = await response.json()
    } else {
        throw Error(response.status)
    }
    for (u of users) {
        if (u.username == username.value) {
            username.classList.add("wrong")
            alert("Username exists!")
            return false
        }
        if (u.email == email.value) {
            email.classList.add("wrong")
            alert("Email exists!")
            return false
        }
    }

    let msg = document.getElementById('error')
    msg.innerText = ''

    console.log("correct data!")
    return true
}

form.onkeyup = function (event) {
    let elem = event.target
    if (elem.classList.contains('wrong')) {
        elem.classList.remove("wrong")
        elem.style["boxShadow"] = ""
    }
}
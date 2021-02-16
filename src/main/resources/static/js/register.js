function checkUsername() {
    var form = document.getElementById("signup_form");
    var username = document.getElementById("username")
    var pattern = /^[a-zA-Z0-9_]+$/;
    var msg = document.getElementById("username_msg")

    if (username.match(pattern)) {
        form.classList.add("validUsername");
        form.classList.remove("invalidUsername")
        msg.innerText = "Correct username!"
        msg.style.color = "#32cd32"
    } else {
        form.classList.add("invalidUsername");
        form.classList.remove("validUsername")
        msg.innerText = "Username should contain letters and digits!"
        msg.style.color = "#ff0000"
    }
}

function checkPassword() {
    var form = document.getElementById("signup_form");
    var password = document.getElementById("password").value;
    var pattern = /^[a-zA-Z0-9]{8,}$/;
    var msg = document.getElementById("passwordMsg")

    if (password.match(pattern)) {
        form.classList.add("validPassword");
        form.classList.remove("invalidPassword")
        msg.innerText = "Correct password!"
        msg.style.color = "#32cd32"
    } else {
        form.classList.add("invalidPassword");
        form.classList.remove("validPassword")
        msg.innerText = "Password should contain at least 8 letters or digits"
        msg.style.color = "#ff0000"
    }
}

function confirmPasswords() {
    var form = document.getElementById("form");
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value
    var msg = document.getElementById("confirmPasswordMsg")
    if (password == confirmPassword) {
        form.classList.add("validConfirmPassword");
        form.classList.remove("invalidConfirmPassword")
        msg.innerText = "Password confirmed!"
        msg.style.color = "#32cd32"
    } else {
        form.classList.add("invalidConfirmPassword");
        form.classList.remove("validConfirmPassword")
        msg.innerText = "Password doesn't match!"
        msg.style.color = "#ff0000"
    }
    if (password == "" && confirmPassword == "" || !form.classList.contains("validPassword")) {
        form.classList.remove("validConfirmPassword")
        form.classList.remove("invalidConfirmPassword")
        msg.innerText = ""
    }
}

function checkEmail() {
    var form = document.getElementById("form");
    var email = document.getElementById("email").value;
    console.log(email)
    var emailMsg = document.getElementById("emailMsg");
    var pattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;

    if (email.match(pattern)) {
        form.classList.add("validEmail");
        form.classList.remove("invalidEmail");
        emailMsg.innerText = "Correct email!"
        emailMsg.style.color = "#32cd32"
        console.log("valid!")
    } else {
        form.classList.add("invalidEmail");
        form.classList.remove("validEmail")
        emailMsg.innerText = "Please enter valid email!"
        emailMsg.style.color = "#ff0000"
        console.log("invalid!")
    }
}

function checkForm() {
    checkUsername()
    checkPassword()
    confirmPasswords()
    checkEmail()
    var f = document.getElementById("form")
    if (
        f.classList.contains("validUsername")
        && f.classList.contains("validEmail")
        && f.classList.contains("validPassword")
        && f.classList.contains("validConfirmPassword")) {
        if (confirm("Confirm?")) {
            console.log("Sign up")
            f.submit()
        }
    }
}

function dropErrorUsername() {
    let elem = document.getElementById("username-msg")
    elem.innerText = ""
}

function dropErrorEmail() {
    let elem = document.getElementById("email-msg")
    elem.innerText = ""
}
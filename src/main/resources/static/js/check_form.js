function checkForm() {
	var elements = document.getElementsByTagName("input")
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].value == "") {
			elements[i].focus()
			alert('Заполните все поля')
			return false;
		}
	}
	var male = document.getElementById("m")
	var female = document.getElementById("f")
	if (male.checked == false && female.checked == false) {
		alert("Укажите пол")
		return false
	}
}
const citySelected = document.getElementById("citySelected")

const universitySelected = document.getElementById("universitySelected")

const cityName = document.getElementById("cityName")


async function fillElements(optionsContainer, elements) {
    elements.forEach(element => {
        let elementOption = document.createElement('div')
        elementOption.classList.add("option")

        let input = document.createElement('input')
        input.type = "radio"
        input.classList.add("radio")
        input.id = element
        input.name = "cityElement"

        elementOption.appendChild(input)

        let label = document.createElement('label')
        label.htmlFor = input.id
        label.innerHTML = element

        elementOption.appendChild(label)

        optionsContainer.appendChild(elementOption)
    })
}


async function setEventListeners(selected) {
    const optionsContainer = selected.previousElementSibling
    const searchBox = selected.nextElementSibling

    const optionsList = optionsContainer.querySelectorAll(".option")

    selected.addEventListener("click", () => {
        filterList("")
    })

    optionsList.forEach(o => {
        o.addEventListener("click", () => {
            selected.innerHTML = o.querySelector("label").innerHTML
            searchBox.nextElementSibling.value = selected.innerHTML
            optionsContainer.classList.remove("active")
            if (selected.id == "citySelected") {
                console.log("city selected!")
                universitySelected.innerText = "Select university"
                document.getElementById("universityName").value = ""
                reloadUniversities()
            } else {
                console.log("university selected!")
            }
        })
    })
    searchBox.addEventListener("keyup", function (e) {
        filterList(e.target.value)
    })
    const filterList = searchTerm => {
        searchTerm = searchTerm.toLowerCase()
        optionsList.forEach(option => {
            let label = option.firstElementChild.nextElementSibling.innerText.toLowerCase()
            if (label.indexOf(searchTerm) != -1) {
                option.style.display = "block"
            } else {
                option.style.display = "none"
            }
        })
    }
}

async function clearChildren(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.lastChild);
    }
}

async function loadCities() {
    let response = await fetch("/api/cities")
    if (response.status == 200) {
        let cities = await response.json()
        console.log(cities.map(city => city.ukrName))
        const cityOptionsContainer = document.getElementById("cityOptionsContainer")
        //заполняет селектбокс городами
        await fillElements(cityOptionsContainer, cities.map(city => city.ukrName))
        //навешивает обработчики клика
        await setEventListeners(citySelected)
        return cities
    }
    throw new Error(response.status)
}

async function reloadUniversities() {
    let city = document.getElementById("cityName").value.toString()
    console.log(city)
    let response = await fetch("/api/universities?city=" + city)
    if (response.status == 200) {
        let universities = await response.json()
        console.log(universities)
        const universityOptionsContainer = document.getElementById("universityOptionsContainer")
        await clearChildren(universityOptionsContainer)
        await fillElements(universityOptionsContainer, universities.map(u => u.ukrShortName))
        await setEventListeners(universitySelected)
        return universities
    }
    throw new Error(response.status)
}


async function loadData() {
    let currentCity = document.getElementById("cityName").value
    if (currentCity) {
        citySelected.innerHTML = currentCity
    }
    let currentUniversity = document.getElementById("universityName").value
    if (currentUniversity) {
        universitySelected.innerHTML = currentUniversity
    }

    await loadCities()

    await reloadUniversities()

}

loadData()
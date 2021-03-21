function createNode(element) {
    return document.createElement(element);
}

function append(parent, el) {
    return parent.appendChild(el);
}

const cityOptionsContainer = document.getElementById("cityOptionsContainer")



/*const data = ["Київ", "Харків", "Лвів"]*/
const getCitiesUrl = "/api/cities"

fetch(getCitiesUrl)
    .then((resp) => resp.json())
    .then(function (data) {
        data.forEach(city => {
            let cityOption = document.createElement('div')
            cityOption.classList.add("option")

            let input = document.createElement('input')
            input.type = "radio"
            input.classList.add("radio")
            input.id = city.ukrName
            input.name = "cityElement"

            append(cityOption, input)

            let label = document.createElement('label')
            label.htmlFor = input.id
            label.innerHTML = city.ukrName

            append(cityOption, label)

            append(cityOptionsContainer, cityOption)
        })
    })
    .catch(function (error) {
        console.log(error);
    });


const universityOptionsContainer = document.getElementById("universityOptionsContainer")

const universities = ['НТУ "ХПІ"', "ХНУРЕ", "ХАІ"]

universities.forEach(university => {
    let universityOption = createNode('div')
    universityOption.classList.add("option")
    let input = document.createElement('input')
    input.type = "radio"
    input.classList.add("radio")
    input.id = university
    input.name = "universityElement"

    append(universityOption, input)

    let label = document.createElement('label')
    label.htmlFor = input.id
    label.innerHTML = university

    append(universityOption, label)

    append(universityOptionsContainer, universityOption)
})
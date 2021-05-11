const selectedAll = document.querySelectorAll(".selected")

selectedAll.forEach(selected => {

    const optionsContainer = selected.previousElementSibling
    const searchBox = selected.nextElementSibling

    const optionsList = optionsContainer.querySelectorAll(".option")

    selected.addEventListener("click", () => {
            if (optionsContainer.classList.contains("active")) {
                optionsContainer.classList.remove("active")
            } else {
                let currentActive = document.querySelector(".options-container.active")
                if (currentActive) {
                    currentActive.classList.remove("active")
                }
                optionsContainer.classList.add("active")
            }

            filterList("")
            if (optionsContainer.classList.contains("active")) {
                searchBox.querySelector("input").value = ""
                searchBox.querySelector("input").focus()
            }
        }
    )

    optionsList.forEach(o => {
        o.addEventListener("click", () => {
            selected.innerHTML = o.querySelector("label").innerHTML
            searchBox.nextElementSibling.value = selected.innerHTML
            optionsContainer.classList.remove("active")
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
})


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
            searchBox.nextElementSibling.nextElementSibling.value = o.querySelector("span").innerText
            optionsContainer.classList.remove("active")
            // if (selected.id == "citySelected") {
            //     universitySelected.innerText = "Select university"
            //     document.getElementById("universityName").value = ""
            //     reloadUniversities()
            // }
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


async function fillElements(optionsContainer, ids, elements, titles) {
    for (let i = 0; i < ids.length; i++) {
        let elementOption = document.createElement('div')
        elementOption.classList.add("option")
        let id = document.createElement("span")
        id.innerText = ids[i]
        id.hidden = "hidden"
        let input = document.createElement('input')
        input.type = "radio"
        input.classList.add("radio")
        input.id = elements[i]
        elementOption.appendChild(input)
        let label = document.createElement('label')
        label.htmlFor = input.id
        label.innerHTML = elements[i]
        if (titles) {
            label.title = titles[i]
        }
        elementOption.appendChild(label)
        elementOption.appendChild(id)

        optionsContainer.appendChild(elementOption)
    }
}

async function clearChildren(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.lastChild);
    }
}

async function isReady() {
    let url = "/api/ready"
    let response = await fetch(url)
    let ready = await response.json()
    if (!ready) {
        alert("You should complete your profile info!")
    }
    return ready
}

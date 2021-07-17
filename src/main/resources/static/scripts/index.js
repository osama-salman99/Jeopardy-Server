function proceed() {
    let nickname = document.getElementById("nicknameInput").value
    if (nickname.length === 0) {
        alert("Nickname cannot be empty")
        return
    }
    console.log(nickname)
    // noinspection JSUnusedGlobalSymbols
    $.ajax({
        type: 'POST',
        url: '/',
        contentType: "application/json",
        dataType: 'text',
        data: nickname,
        success: function (response) {
            console.log(response)
            window.location = '/welcome'
        },
        error: function (response) {
            let message = eval("(" + response.responseText + ")").message;
            console.log(message)
            alert("Error: " + message)
        }
    })
}
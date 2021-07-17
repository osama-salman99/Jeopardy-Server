function submit() {
    let gameId = document.getElementById("gameIdInput").value
    console.log(gameId)
    let password = null
    if (document.getElementById("checkbox").checked) {
        password = document.getElementById("gamePasswordInput").value
    }
    let data = JSON.stringify({
        gameId: gameId,
        password: password
    })
    console.log(data)
    $.ajax({
        type: 'POST',
        url: 'create-game',
        contentType: 'application/json',
        dataType: 'text',
        data: data,
        success: function (response) {
            console.log(response)
            window.location = '/game/' + gameId
        },
        error: function (response) {
            let message = eval("(" + response.responseText + ")").message;
            console.log(message)
            alert("Error: " + message)
        }
    })
}
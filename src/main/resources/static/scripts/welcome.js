function joinGame() {
    let gameId = document.getElementById("gameId").value
    console.log(gameId)
    window.location = '/game/' + gameId
}

function createGame() {
    window.location = '/game/create-game'
}
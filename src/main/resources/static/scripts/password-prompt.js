let password = prompt("Enter the password:")
console.log(password)
$.ajax({
    type: 'POST',
    url: '',
    contentType: 'application/json',
    dataType: 'text',
    data: password,
    success: function (response) {
        console.log(response)
        goToGame()
    },
    error: function (response) {
        let message = eval("(" + response.responseText + ")").message;
        console.log(message)
        alert("Error: " + message)
        goToGame()
    }
})


function goToGame() {
    window.location.href = '../'
}
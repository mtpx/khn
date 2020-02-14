function prepareloginData() {
    let loginData = {
        email: $('#email').val(),
        password: $('#password').val()
    };
    login(loginData);
}

function login(data) {
    $.ajax({
        url: "http://localhost:8080/seller/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(res) {
            if (res > 0) {
                alert("Successfully logged");
                localStorage.setItem('loggedUserId', res);
                window.location = "./index";
            } else {
                alert("User does not exist");
            }
        },
        error: function() {
            alert("Login error")
        }
    })
}
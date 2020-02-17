function prepareRegisterData() {
    let registerData = {
        firstname: $('#firstname').val(),
        lastname: $('#lastname').val(),
        email: $('#email').val(),
        password: $('#password').val()
    };
    register(registerData);
}

function register(data) {
    $.ajax({
        url: "http://localhost:8080/customer/register",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function() {
            alert("Successfully registered");
        },
        error: function() {
            alert("Registration error")
        }
    })
}
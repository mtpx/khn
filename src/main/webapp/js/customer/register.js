function prepareRegisterData() {
    let firstnameVal = $('#firstname').val();
    let lastnameVal = $('#lastname').val();
    let emailVal = $('#email').val();
    let passwordVal = $('#password').val();
    if (passwordVal.trim().length ===0 || emailVal.trim().length===0 || firstnameVal.trim().length===0 || lastnameVal.trim().length===0)
        alert("Register fields cannot be empty ");
    else {
        let registerData = {
            firstname: firstnameVal,
            lastname: lastnameVal,
            email: emailVal,
            password: passwordVal
        };
        register(registerData);
    }
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
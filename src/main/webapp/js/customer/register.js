let firstnameVal, lastnameVal, emailVal, passwordVal;

function prepareRegisterData() {
    firstnameVal = $('#firstname').val();
    lastnameVal = $('#lastname').val();
    emailVal = $('#email').val();
    passwordVal = $('#password').val();
    validateRegisterFields();
}

function validateRegisterFields() {
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
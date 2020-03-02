let emailVal, passwordVal;

function prepareloginData() {
    emailVal = $('#email').val();
    passwordVal = $('#password').val();
    validateLoginFields();
}

function validateLoginFields(){
    if (passwordVal.trim().length ===0 || emailVal.trim().length===0)
        alert("Login fields cannot be empty ");
    else {
        let loginData = {
            email: emailVal,
            password: passwordVal
        };
        login(loginData);
    }
}

function login(data) {
    $.ajax({
        url: "http://localhost:8080/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (resultLogin) {
            if (resultLogin === 0) {
                alert("Bad Credentials");
            }
            if (resultLogin !== 0) {
                sessionStorage.setItem('loggedUserEmail', $('#email').val());
                sessionStorage.setItem('loggedUserId', resultLogin);
                setRoles(resultLogin);
            }
        },
        error: function () {
            alert("Login error")
        }
    })
}

function setRoles(resultLogin){
    $.ajax({
        url: "http://localhost:8080/user/role/" + resultLogin,
        type: "GET",
        success: function (resultRole) {
            if (resultRole[0].name === "admin" || window.location.pathname.toString().includes(resultRole[0].name)) {
                window.location = "../main";
                sessionStorage.setItem('loggedUserIdRole', resultRole[0].id);
                sessionStorage.setItem('loggedUserIdSecondRole', resultRole[1].id);
            } else
                alert("Access denied")
        },
        error: function () {
            alert("Getting role from user error")
        }
    })
}
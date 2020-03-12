let emailVal, passwordVal;

function prepareLoginData() {
    emailVal = $('#email').val();
    passwordVal = $('#password').val();
    validateLoginFields();
}

function validateLoginFields(){
    if (passwordVal.trim().length ===0 || emailVal.trim().length===0)
        alert("Login fields cannot be empty ");
    else {
        validateUserRoles(emailVal);
    }
}

function validateUserRoles(email) {
    $.ajax({
        url: "http://localhost:8080/user/role/email/" + email,
        type: "GET",
        success: function (resultRole) {
            if (resultRole.length === 0)
                alert("Login error - user without role/user not exists");
            else if (resultRole[0].name === "admin" || window.location.pathname.toString().includes(resultRole[0].name)) {
                let loginData = $('form').serialize();
                loginSecured(loginData);
            } else
                alert("Access denied");
        },
        error: function () {
            alert("Getting role from user error")
        }
    })
}

function loginSecured(data) {
    $.ajax({
        url: "http://localhost:8080/performLogin",
        type: "POST",
        contentType: "application/x-www-form-urlencoded",
        data: data,
        success: function () {
            sessionStorage.setItem('loggedUserEmail', emailVal);
            getRolesByEmail(emailVal);
            },
        error: function () {
            alert("Login error - bad credentials")
        }
    });
}

function getRolesByEmail(email) {
    $.ajax({
        url: "http://localhost:8080/user/email/"+email,
        type: "GET",
        contentType: "application/json",
        success: function (result) {
            console.log(result);
            sessionStorage.setItem('loggedUserRole', result.roles[0].id);
            sessionStorage.setItem('loggedUserId', result.id);
            window.location.href = "http://localhost:8080/main";
        },
    });
}





//logowanie po staremu, bez spring sec
// function login(data) {
//     $.ajax({
//         url: "http://localhost:8080/login",
//         type: "POST",
//         contentType: "application/json",
//         data: JSON.stringify(data),
//         success: function (resultLogin) {
//             if (resultLogin === 0) {
//                 alert("Bad Credentials");
//             }
//             if (resultLogin !== 0) {
//                 sessionStorage.setItem('loggedUserEmail', $('#email').val());
//                 setRoles(resultLogin);
//             }
//         },
//         error: function () {
//             alert("Login error");
//         }
//     })
// }

// function setRoles(resultLogin){
//     $.ajax({
//         url: "http://localhost:8080/user/role/" + resultLogin,
//         type: "GET",
//         success: function (resultRole) {
//             if (resultRole[0].name === "admin" || window.location.pathname.toString().includes(resultRole[0].name)) {
//                 window.location = "../main";
//                 sessionStorage.setItem('loggedUserIdRole', resultRole[0].id);
//                 sessionStorage.setItem('loggedUserIdSecondRole', resultRole[1].id);
//             } else
//                 alert("Access denied")
//         },
//         error: function () {
//             alert("Getting role from user error")
//         }
//     })
// }




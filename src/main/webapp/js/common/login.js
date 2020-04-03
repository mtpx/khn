let emailVal, passwordVal;

function prepareLoginData(loginPageType) {
    emailVal = $('#email').val();
    passwordVal = $('#password').val();
    validateLoginFields(loginPageType);
}

function validateLoginFields(loginPageType) {
    if (passwordVal.trim().length === 0 || emailVal.trim().length === 0)
        alert("Login fields cannot be empty ");
    else {
        validateUserRoles(emailVal, loginPageType);
    }
}

function validateUserRoles(email, loginPageType) {
    //loginPageType - parametr przechowujacy informacje z której strony logowania został wysłany request (seller/customer)
    //służy do weryfikacji czy użytkownik z właściwą rolą loguje się przez właściwą stronę do logowania
    $.ajax({
            url: "http://localhost:8080/user/email/" + encodeURIComponent(email)+"/",
            type: "GET",
            success: function (resultUser) {
                let rolesArray = [];
                for (var i = 0; i < resultUser.roles.length; i++) {
                    rolesArray.push(resultUser.roles[i].name);
                }
                if (resultUser.roles.length === 0) {
                    alert("Login error - user without role");
                    return 0;
                }
                switch (loginPageType) {
                    //jeśli znajdujemy się na seller/login zalogować może się jedynie użytkownik z rolami admin/seller
                    case "seller":
                        if (rolesArray.includes('admin') || rolesArray.includes('seller')) {
                            let loginData = $('form').serialize();
                            loginSecured(loginData);
                        } else
                            alert("Access denied");
                        break;
                    //jeśli znajdujemy się na customer/login zalogować może się jedynie użytkownik z rolami admin/customer
                    case "customer":
                        if (rolesArray.includes('admin') || rolesArray.includes('customer')) {
                            let loginData = $('form').serialize();
                            loginSecured(loginData);
                        } else
                            alert("Access denied");
                        break;
                }
            },
            error: function () {
                alert("Login error")
            }
        }
    )
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
        url: "http://localhost:8080/user/email/"+email+"/",
        type: "GET",
        contentType: "application/json",
        success: function (result) {
            if (result.roles.length === 1) { //jeśli użytkownik ma jedną rolę  - zmienna z drugą rolą = 0
                sessionStorage.setItem('loggedUserRole', result.roles[0].id);
                sessionStorage.setItem('loggedUserRole2', 0);
            } else {
                sessionStorage.setItem('loggedUserRole', result.roles[0].id);
                sessionStorage.setItem('loggedUserRole2', result.roles[1].id);
            }
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




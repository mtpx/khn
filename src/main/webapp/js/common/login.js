function prepareloginData() {
    let loginData = {
        email: $('#email').val(),
        password: $('#password').val()
    };
    login(loginData);
}

function login(data) {
    $.ajax({
        url: "http://localhost:8080/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(res) {
            if (res.userId===0) {
                alert("Bad Credentials");
            }
            if(res.userId!==0){
                localStorage.setItem('loggedUserId', res);
                window.location = "../main";
            }
        },
        error: function() {
            alert("Login error")
        }
    })
}
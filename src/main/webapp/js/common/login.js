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
        success: function(resultLogin) {
            if (resultLogin===0) {
                alert("Bad Credentials");
            }
            if(resultLogin!==0){
                $.ajax({
                    url: "http://localhost:8080/user/role/"+resultLogin,
                    type: "GET",
                    success: function(resultRole) {
                        window.location = "../main";
                        alert(resultRole[0].id)
                        if(resultRole[0].id===1)
                            $("#role").append('Administration');
                        else if(resultRole[0].id===2)
                            $("#role").append('Seller');
                        else if(resultRole[0].id===3)
                            $("#role").append('Customer');
                        localStorage.setItem('loggedUserId', resultLogin);

                    },
                    error: function() {
                        alert("Getting role from user error")
                    }
                })

            }
        },
        error: function() {
            alert("Login error")
        }
    })
}
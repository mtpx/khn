$(document).ready(function() {
    // getRolesByEmail(sessionStorage.getItem("loggedUserEmail"));
    getSecuredUserName();
    $("#footerPlaceholder").load("/footer.html");
    $("#navigationMenuPlaceholder").load("/navigationMenu.html");
});

function getSecuredUserName() {
    let name;
    $.ajax({
        url: "http://localhost:8080/user/getSecuredUserName",
        type: "GET",
        contentType: "application/json",
        success: function (result) {
            console.log('secured user name: '+result);
            //sessionStorage.setItem('loggedUserEmail', result);
            //getRolesByEmail(result)
            name=result;
        },
    });
    return name;
}

function goBack() {
    window.history.back();
}

function logout() {
    $.ajax({
        url: "http://localhost:8080/performLogout",
        type: "GET",
        contentType: "application/json",
    });
    sessionStorage.setItem('loggedUserRole',"0");
    sessionStorage.setItem('loggedUserSecondRole', "0");
    sessionStorage.setItem('loggedUserId', "0");
    sessionStorage.setItem('loggedUserEmail', "0");
    $("ul li").hide();
    window.location = "/index";
}




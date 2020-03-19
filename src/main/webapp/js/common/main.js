$(document).ready(function() {
    $("#footerPlaceholder").load("/footer.html");
    $("#navigationMenuPlaceholder").load("/navigationMenu.html");
});

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




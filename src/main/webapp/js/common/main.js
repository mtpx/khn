$(document).ready(function() {
    $("#footerPlaceholder").load("/footer.html");
    $("#navigationMenuPlaceholder").load("/navigationMenu.html");
});

function goBack() {
    window.history.back();
}

function logout() {
    sessionStorage.setItem('loggedUserIdRole',"0");
    sessionStorage.setItem('loggedUserIdSecondRole', "0");
    sessionStorage.setItem('loggedUserId', "0");
    sessionStorage.setItem('loggedUserEmail', "0");
    $("ul li").hide();
    window.location = "/index";
}


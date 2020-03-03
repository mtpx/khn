$(document).ready(function() {
    $("#footer").load("/footer.html");
    $("#footerPlaceholder").load("/footer.html");
    $("#navigationMenuPlaceholder").load("/navigationMenu.html");
    showMenuOptions();
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
    window.location = "../index";
}

function showMenuOptions() {
    let role = "#role";
    let welcome = "#welcome";
    $("ul li").hide();
    $(role).append('');
    $(welcome).show().append('hello '+sessionStorage.getItem('loggedUserEmail'));
    if(sessionStorage.getItem('loggedUserIdRole')==="1") {
        $(role).show();
        $(role).append('Administration');
        $("#users").show();
        $("#logout").show();
    }
    else if(sessionStorage.getItem('loggedUserIdRole')==="2") {
        $(role).show();
        $(role).append('Seller');
        $("#auctions").show();
        $("#sales").show();
        $("#addProperty").show();
        $("#profile").show();
        $("#logout").show();
    }
    else if(sessionStorage.getItem('loggedUserIdRole')==="3") {
        $(role).show();
        $(role).append('Customer');
        $("#auctions").show();
        $("#buyings").show();
        $("#credits").show();
        $("#profile").show();
        $("#logout").show();
    }
}
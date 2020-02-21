$(document).ready(function() {
    let role = "#role";
    $("ul li").hide();
    $(role).append('');
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
});
function goBack() {
    window.history.back();
}

function logout() {
    sessionStorage.setItem('loggedUserIdRole',"0");
    sessionStorage.setItem('loggedUserIdSecondRole', "0");
    sessionStorage.setItem('loggedUserId', "0");
    $("ul li").hide();
    window.location = "../index";
}
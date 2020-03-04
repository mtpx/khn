let role = "#role";
let welcome = "#welcome";
$("ul li").hide();
$(role).append('');
$(welcome).show().append('hello ' + sessionStorage.getItem('loggedUserEmail'));
if (sessionStorage.getItem('loggedUserIdRole') === "1") {
    $(role).show();
    $(role).append('Administration');
    $("#users").show();
    $("#logout").show();
} else if (sessionStorage.getItem('loggedUserIdRole') === "2") {
    $(role).show();
    $(role).append('Seller');
    $("#auctions").show();
    $("#sales").show();
    $("#addProperty").show();
    $("#profile").show();
    $("#logout").show();
} else if (sessionStorage.getItem('loggedUserIdRole') === "3") {
    $(role).show();
    $(role).append('Customer');
    $("#auctions").show();
    $("#buyings").show();
    $("#credits").show();
    $("#profile").show();
    $("#logout").show();
}

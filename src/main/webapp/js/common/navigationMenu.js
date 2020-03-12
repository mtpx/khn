let role = "#role";
let welcome = "#welcome";
let welcomeSecured = "#welcomeSecured";
$("ul li").hide();
$(role).append('');
$(welcome).show().append('hello session storage ' + sessionStorage.getItem('loggedUserEmail'));
$(welcomeSecured).show().append('hello secured ajax request:' + getSecuredUserName());
$("#logout").show();
 if (sessionStorage.getItem('loggedUserRole') === "1") {
    $(role).show();
    $(role).append('Administration');
    $("#users").show();
    $("#profile").show();
 } else if (sessionStorage.getItem('loggedUserRole') === "2") {
    $(role).show();
    $(role).append('Seller');
    $("#auctions").show();
    $("#sales").show();
    $("#addProperty").show();
    $("#profile").show();
 } else if (sessionStorage.getItem('loggedUserRole') === "3") {
    $(role).show();
    $(role).append('Customer');
    $("#auctions").show();
    $("#buyings").show();
    $("#credits").show();
    $("#profile").show();
}

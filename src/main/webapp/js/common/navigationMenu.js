let role;
$(document).ready(function() {
   role = "#role";
   renderMenu();
});

function renderMenu(){
   //wyświetlanie opcji w menu w zależności od posiadanych ról
   $("ul li").hide();
   $(role).append('');
   $("#logout").show();
   if (sessionStorage.getItem('loggedUserRole') === "1" || sessionStorage.getItem('loggedUserRole2') === "1") {
      $(role).show();
      $(role).append('Administrator ');
      $("#users").show();
   }
   if (sessionStorage.getItem('loggedUserRole') === "2" || sessionStorage.getItem('loggedUserRole2') === "2") {
      $(role).show();
      $(role).append('Seller ');
      $("#auctions").show();
      $("#sales").show();
      $("#addProperty").show();
      $("#profile").show();
   }
   if (sessionStorage.getItem('loggedUserRole') === "3" || sessionStorage.getItem('loggedUserRole2') === "3") {
      $(role).show();
      $(role).append('Customer ');
      $("#auctions").show();
      $("#buyings").show();
      $("#credits").show();
      $("#profile").show();
   }
}




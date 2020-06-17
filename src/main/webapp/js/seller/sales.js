$(function() {
    let totalCost = $(".balanceData");
    if(parseInt(totalCost.text(), 10) < 0) {
         totalCost.addClass("red");
     } else {
         totalCost.addClass("green");
     }
});

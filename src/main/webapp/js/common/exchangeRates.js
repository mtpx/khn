$(document).ready(function ()  {
    getExchangeRates();
});

function getExchangeRates(){
    $.ajax({
        url: 'https://api.exchangeratesapi.io/latest?base=PLN',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            calculateBalanceInForeignCurrency(response);
        }
    });
}

function calculateBalanceInForeignCurrency(response) {
    let balance = parseFloat($("span[id*='balancePLN']").text());

    $('#balanceEUR').val((parseFloat(response.rates.EUR)*balance).toFixed());
    $('#balanceUSD').val((parseFloat(response.rates.USD)*balance).toFixed());
}





$(document).ready(function ()  {
    getAuctionView("all");
});

let table;

function getAuctionView(type){
    let url='http://localhost:8080/auctionView/';
    switch (type) {
        case "all": url='http://localhost:8080/auctionView/'; break;
        case "flat": url='http://localhost:8080/auctionView/flat'; break;
        case "plot": url='http://localhost:8080/auctionView/plot'; break;
        case "house": url='http://localhost:8080/auctionView/house'; break;
    }
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            bindToDatatable(data);
        }
    });
}


function bindToDatatable(data) {
    table = $('#table').dataTable({
        data: data,
        paging: false,
        searching: false,
        destroy: true,
        autoWidth: true,
        columns: [{
            data: "type"
        }, {
            data: "propertyId"
        }, {
            data: "city"
        }, {
            data: "street"
        }, {
            data: "price"
        }, {
            data: "size"
        }]
    })
}

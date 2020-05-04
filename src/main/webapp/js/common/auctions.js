$(document).ready(function ()  {
    getAuctionView("all");
});

let table;

function getAuctionView(type){
    let url='http://localhost:8080/property/view/all';
    switch (type) {
        case "all": url='http://localhost:8080/property/view/all'; break;
        case "flat": url='http://localhost:8080/property/view/flat'; break;
        case "plot": url='http://localhost:8080/property/view/plot'; break;
        case "house": url='http://localhost:8080/property/view/house'; break;
    }
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if(sessionStorage.getItem('loggedUserRole')==='3' || sessionStorage.getItem('loggedUserRole2')==='3')
                bindToDatatableCustomer(data);
            else
                bindToDatatableSeller(data);
        }
    });
}


function bindToDatatableSeller(data){
    $('#actionColumn').hide();
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

function bindToDatatableCustomer(data) {
    $('#actionColumn').show();
    table = $('#table').DataTable({
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
        }, {
            sortable: false,
            "render": function ( data, type, full, meta ) {
                return '<a role="button" class="btn btn-danger" onClick="buyPropertyConfirmation(\'' + full.type + '\','+full.propertyId+')"> Buy</a>';
            }
        }]
    })
}

function buyPropertyConfirmation(propertyType, propertyId) {
    if (confirm('Do You really want to buy this property ?'))
        executeTransaction(propertyType, propertyId);
}

function executeTransaction(propertyType, propertyId) {
    $.ajax({
        url: "http://localhost:8080/property/transaction/"+propertyType,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            propertyId: propertyId,
            customerId: sessionStorage.getItem('loggedUserId')
        }),
        success: function() {
            alert('You bought '+propertyType+', id: '+propertyId);
        },
        error: function(response) {
            alert("Transaction error: "+response.responseText)
        }
    });
}





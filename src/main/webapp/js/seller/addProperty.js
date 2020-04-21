let floorInput, roomsInput, typeInput, areaInput, priceInput, streetInput, houseNumberInput, localNumberInput, postCodeInput, cityInput;
let houseRadioBtn, flatRadioBtn, plotRadioBtn, allRadioBtns;
let realAssetId=2; //default radio button - house
$(document).ready(function () {
    floorInput = $('#floorInput');
    roomsInput = $('#roomsInput');
    typeInput = $('#typeInput');
    areaInput = $('#areaInput');
    priceInput = $('#priceInput');
    streetInput = $('#streetInput');
    houseNumberInput = $('#houseNumberInput');
    localNumberInput = $('#localNumberInput');
    postCodeInput = $('#postCodeInput');
    cityInput = $('#cityInput');

    houseRadioBtn = $('#houseRadio');
    plotRadioBtn = $('#plotRadio');
    flatRadioBtn = $('#flatRadio');
    allRadioBtns = $('.form-check-input');

    floorInput.hide();
    roomsInput.hide();
    typeInput.hide();
    plotRadioBtn.click(function () {
        realAssetId = 3;
        allRadioBtns.removeClass("checked")
        plotRadioBtn.addClass("checked");
        floorInput.hide();
        roomsInput.hide();
        typeInput.show();
    });
    houseRadioBtn.click(function () {
        realAssetId = 2;
        allRadioBtns.removeClass("checked")
        houseRadioBtn.addClass("checked");
        floorInput.hide();
        roomsInput.show();
        typeInput.hide();
    });
    flatRadioBtn.click(function () {
        realAssetId = 1;
        allRadioBtns.removeClass("checked")
        flatRadioBtn.addClass("checked");
        floorInput.show();
        roomsInput.show();
        typeInput.hide();
    });
});

function preparePropertyData(){
        let propertyData = {
            street: streetInput.val(),
            homeNumber: houseNumberInput.val(),
            localNumber: localNumberInput.val(),
            postCode: postCodeInput.val(),
            city: cityInput.val(),
            size: areaInput.val(),
            price: priceInput.val(),
            rooms: roomsInput.val(),
            floor: floorInput.val(),
            type: typeInput.val(),
            realAssetId: realAssetId,
            userId: sessionStorage.getItem('loggedUserId')
        };
    submitProperty(propertyData);
}

function submitPropertyConfirmation() {
    if (confirm('Add Property?')) {
        preparePropertyData();
    } else {
        //back
    }
}

function submitProperty(propertyData) {
    $.ajax({
        url: "http://localhost:8080/property",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(propertyData),
        success: function() {
            alert("Property added")
        },
        error: function(response) {
            alert("Property adding error: " + response.responseText)
        }
    })
}
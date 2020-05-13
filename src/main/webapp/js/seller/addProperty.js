let floorInput, roomsInput, typeInput, areaInput, priceInput, streetInput, houseNumberInput, localNumberInput, postCodeInput, cityInput;
let houseRadioBtn, flatRadioBtn, plotRadioBtn, allRadioBtns;
let realAssetId, requestUrlSuffix="house";//default radio button - house
$(document).ready(function () {
    //nie jestem pewna czy to wszystko powinno byc w tej metodzie
    //chyba fajniej byloby to jakos pogrupowac - chodzi o to zeby bylo czytelne dla innego deva :)
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
    typeInput.hide();
    plotRadioBtn.click(function () {
        requestUrlSuffix="plot";
        realAssetId = 3;
        allRadioBtns.removeClass("checked")
        plotRadioBtn.addClass("checked");
        floorInput.hide();
        roomsInput.hide();
        typeInput.show();
    });
    houseRadioBtn.click(function () {
        requestUrlSuffix="house";
        realAssetId = 2;
        allRadioBtns.removeClass("checked")
        houseRadioBtn.addClass("checked");
        floorInput.hide();
        roomsInput.show();
        typeInput.hide();
    });
    flatRadioBtn.click(function () {
        requestUrlSuffix="flat";
        realAssetId = 1;
        allRadioBtns.removeClass("checked")
        flatRadioBtn.addClass("checked");
        floorInput.show();
        roomsInput.show();
        typeInput.hide();
    });
});

function submitPropertyConfirmation() {
    if (confirm('Add Property?'))
        validateForm();
}

function validateForm(){
    let errors =[];
    if (streetInput.val() === '')
        errors.push("Street should not be empty");
    if (houseNumberInput.val() === '')
        errors.push("House number should not be empty");
    if (localNumberInput.val() === '')
        errors.push("Local number should not be empty");
    if (postCodeInput.val() === '')
        errors.push("Post code should not be empty");
    if (cityInput.val() === '')
        errors.push("City should not be empty");
    if (priceInput.val() === '')
        errors.push("Price should not be empty");
    if (areaInput.val() === '')
        errors.push("Area should not be empty");
    if (roomsInput.val() === '' && (requestUrlSuffix==='flat' || requestUrlSuffix==='house'))
        errors.push("Rooms should not be empty");
    if (floorInput.val() === '' && requestUrlSuffix==='flat')
        errors.push("Floor should not be empty");
    if (typeInput.val() ==='' && requestUrlSuffix==='plot')
        errors.push("Plot type should not be empty");
    if(errors.length === 0){
        preparePropertyData()
    }else{
        alert("Form errors: "+errors)
    }
}
function preparePropertyData(){
    let propertyData = {
        street: streetInput.val(),
        houseNumber: houseNumberInput.val(),
        localNumber: localNumberInput.val(),
        postCode: postCodeInput.val(),
        city: cityInput.val(),
        size: areaInput.val(),
        price: priceInput.val(),
        rooms: roomsInput.val(),
        floor: floorInput.val(),
        type: typeInput.val(),
        userId: sessionStorage.getItem('loggedUserId')
    };
    submitProperty(propertyData);
}

function submitProperty(propertyData) {
    $.ajax({
        //suffix urla ustawiony na flat/plot/house w zależności od wybranego radio buttona przy dodawaniu nieruchomości
        url: "http://localhost:8080/property/add/"+requestUrlSuffix,
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
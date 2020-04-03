let firstnameInput,lastnameInput,emailInput,passwordInput;
let lockUserDataBtn,updateUserDataBtn,editUserDataBtn,becomeCustomerBtn;

$(document).ready(function() {
    $("#footerPlaceholder").load("/footer.html");
    $("#navigationMenuPlaceholder").load("/navigationMenu.html");

    firstnameInput =  $('#firstname');
    lastnameInput =  $('#lastname');
    emailInput =  $('#email');
    passwordInput =  $('#password');

    lockUserDataBtn =  $('#lockUserDataBtn');
    updateUserDataBtn =  $('#updateUserDataBtn');
    editUserDataBtn =  $('#editUserDataBtn');
    becomeCustomerBtn =  $('#becomeCustomerBtn');

    if (sessionStorage.getItem('loggedUserRole') === "2" && sessionStorage.getItem('loggedUserRole2') === "0") {
        becomeCustomerBtn.removeClass('invisible');
    }

    getUserData();
});

function getUserData(){
    $.ajax({
        url: 'http://localhost:8080/user/'+sessionStorage.getItem('loggedUserId'),
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            firstnameInput.val(data.firstname);
            lastnameInput.val(data.lastname);
            emailInput.val(data.email);
            passwordInput.val(data.password);
        }
    });
}

function validateChangeUserDataFields() {
    if (passwordInput.val().trim().length ===0 || emailInput.val().trim().length===0 || firstnameInput.val().trim().length===0 || lastnameInput.val().trim().length===0)
        alert("Fields cannot be empty ");
    else {
        let newUserData = {
            firstname: firstnameInput.val(),
            lastname: lastnameInput.val(),
            email: emailInput.val(),
            password: passwordInput.val()
        };
        updateUserData(newUserData);
    }
}

function editUserData() {
    firstnameInput.removeAttr('readonly');
    lastnameInput.removeAttr('readonly');
    emailInput.removeAttr('readonly');
    passwordInput.removeAttr('readonly');
    lockUserDataBtn.removeClass('invisible');
    updateUserDataBtn.removeClass('invisible');
    editUserDataBtn.addClass('invisible')
}

function lockUserData() {
    firstnameInput.attr('readonly','readonly');
    lastnameInput.attr('readonly','readonly');
    emailInput.attr('readonly','readonly');
    passwordInput.attr('readonly','readonly');
    lockUserDataBtn.addClass('invisible');
    updateUserDataBtn.addClass('invisible');
    editUserDataBtn.removeClass('invisible')
}

function updateUserData(newUserData) {
    $.ajax({
        url: 'http://localhost:8080/user/'+sessionStorage.getItem('loggedUserId'),
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(newUserData),
        success: function() {
            alert("User data changed");
        },
        error: function() {
            alert("User data change error")
        }
    })
}

function becomeCustomer() {
    $.ajax({
        url: 'http://localhost:8080/user/becomeCustomer/'+sessionStorage.getItem('loggedUserId'),
        type: "POST",
        contentType: "application/json",
        success: function() {
            alert("You are customer!");
            sessionStorage.setItem('loggedUserRole2', 3);
            becomeCustomerBtn.addClass('invisible');
            location.reload();
        },
        error: function() {
            alert("Adding customer role to user error")
        }
    })
}


let oldPasswordVal,newPasswordVal, emailVal;

function preparePasswordChangeData() {
    oldPasswordVal = $('#oldPassword').val();
    newPasswordVal = $('#newPassword').val();
    emailVal = $('#email').val();
    validateChangePasswordFields();
}

function validateChangePasswordFields(){
    if (oldPasswordVal.trim().length === 0 || newPasswordVal.trim().length === 0 || emailVal.trim().length === 0)
        alert("Password change fields cannot be empty");
    else if (oldPasswordVal.trim() === newPasswordVal.trim())
        alert("Passwords should be different");
    else {
        let passwordChangeData = {
            email: emailVal,
            oldPassword: oldPasswordVal,
            newPassword: newPasswordVal
        };
        changePassword(passwordChangeData);
    }
}

function changePassword(data) {
    $.ajax({
        url: "http://localhost:8080/changePassword",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (res) {
            alert(res);
        },
        error: function (res) {
            alert(res.responseText);
        }
    })
}
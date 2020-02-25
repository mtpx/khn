function preparePasswordChangeData() {
    let oldPasswordVal = $('#oldPassword').val();
    let newPasswordVal = $('#newPassword').val();
    let emailVal = $('#email').val();
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
            if (res === true) {
                alert("Your password has been changed");
            } else if (res === false) {
                alert("Invalid old password");
            }
        },
        error: function () {
            alert("Password change error")
        }
    })
}
function preparePasswordChangeData() {
    let passwordChangeData = {
        email: $('#email').val(),
        oldPassword: $('#oldPassword').val(),
        newPassword: $('#newPassword').val()
    };
    changePassword(passwordChangeData);
}

function changePassword(data) {
    $.ajax({
        url: "http://localhost:8080/changePassword",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(res) {
            if (res===true) {
                alert("Your password has been changed");
            } else if(res===false) {
                alert("Invalid old password");
            }
        },
        error: function() {
            alert("Password change error")
        }
    })
}
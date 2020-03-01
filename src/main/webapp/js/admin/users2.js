function getUsers() {
    $list = $('.table tbody');
    $.ajax({
        url: 'http://localhost:8080/user',
        dataType: 'json'
    })
        .done((res) => {
            $list.empty();
            $.each(res, function (i, item) {
                $list.append('<tr>' +
                    '<th scope="row" >' + res[i].id + '</th>' +
                    '<td>' + res[i].firstname + '</td>' +
                    '<td>' + res[i].lastname + '</td>' +
                    '<td>' + res[i].email + '</td>' +
                    '<td><button class="btn btn-danger btn-xs btn-delete" onclick="deleteUser(id)" id=' + res[i].id + '>Delete</button></td>' +
                    '</tr>');
            })
        })
}

function deleteUser(id) {
    let id2 = this.id;
    let row = $(this);
    let loggedUserId = sessionStorage.getItem('loggedUserId');
    if (loggedUserId === id)
        alert('admin cannot delete his account');
    else {
        $.ajax({
            url: 'http://localhost:8080/user/' + id,
            type: 'DELETE',
            contentType:'application/json',
            dataType: 'text',
            success: function () {
                alert('user: ' + id + ' deleted')
                $("#getUsers").click();
            },
            error: function () {
                alert('cannot delete user: ' + id);
            }
        });
    }

}
$(document).ready(function ()  {
    getUsers()
});

let table;

function getUsers(){
    $.ajax({
        url: 'http://localhost:8080/user/',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            bindtoDatatable(data);
        }
    });
}

function bindtoDatatable(data) {
    table = $('#table').dataTable({
        data: data,
        columns: [{
            data: "id"
        }, {
            data: "firstname"
        }, {
            data: "lastname"
        }, {
            data: "email"
        }, {
            sortable: false,
            "render": function ( data, type, full, meta ) {
                let userId = full.id;
                return '<a onclick="deleteUser('+full.id+')" class="btn btn-danger" role="button">Delete</a>';
            }
        }]
    })
}

function deleteUser(id) {
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
                alert('user: ' + id + ' deleted');
                getUsers();
            },
            error: function () {
                alert('cannot delete user: ' + id);
            }
        });
    }
}
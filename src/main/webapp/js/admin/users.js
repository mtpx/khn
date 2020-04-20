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
            data: "id"
        }, {
            data: "city"
        }, {
            data: "price"
        }, {
            data: "area"
        }, {
            sortable: false,
            "render": function ( data, type, full, meta ) {
                let userId = full.id;
                return '<a onclick="deleteUser('+userId+')" class="btn btn-danger" role="button">Delete</a>';
            }
        }]
    })
}

function deleteUser(id) {
    let loggedUserId = parseInt(sessionStorage.getItem('loggedUserId'),10);
    if (loggedUserId === id)
        alert('Admin cannot delete his account');
    else if (confirm('Delete user '+id+'?')) {
        $.ajax({
            url: 'http://localhost:8080/user/' + id,
            type: 'DELETE',
            contentType:'application/json',
            dataType: 'text',
            success: function () {
                alert('User: ' + id + ' deleted');
                getUsers();
            },
            error: function () {
                alert('Cannot delete user: ' + id);
            }
        });
    } else {
        //back to user list
    }
}
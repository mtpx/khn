function showFlats() {

    table = $('#table').dataTable({
        data: data,
        paging: false,
        searching: false,
        destroy: true,
        autoWidth: true,
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
                return '<a onclick="deleteUser('+userId+')" class="btn btn-danger" role="button">Delete</a>';
            }
        }]
    })
}
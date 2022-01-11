var brendAjaxTable = function () {
    console.log("contextRoot : " + context);
    var brendTable = $('#brend_data').mDatatable();
    var lang = 'en';

    var demo = function () {
        brendTable.destroy();
        brendTable = $('#brend_data').mDatatable({
            data: {
                type: 'remote',
                source: {
                    read: {
                        method: 'GET',
                        url: context + '/brends',
                        map: function (raw) {
                            // sample data mapping
                            var dataSet = raw;
                            if (typeof raw.data !== 'undefined') {
                                dataSet = raw.data;
                            }
                            return dataSet;
                        }
                    }
                },
                pageSize: 10,
                saveState: {
                    cookie: true,
                    webstorage: true
                },
                serverPaging: true,
                serverFiltering: true,
                serverSorting: true
            },

            // layout definition
            layout: {
                theme: 'default', // datatable theme
                class: '', // custom wrapper class
                scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
                footer: false // display/hide footer
            },

            // column sorting
            sortable: false,

            pagination: true,

            toolbar: {
                // toolbar items
                items: {
                    // pagination
                    pagination: {
                        // page size select
                        pageSizeSelect: [10, 20, 30, 50, 100]
                    }
                }
            },

            search: {
                input: $('#generalSearch')
            },

            // columns definition
            columns: [
                {
                    field: 'name',
                    title: 'Name'
                },  {
                    field: 'actions',
                    title: 'Actions',
                    sortable: false
                }]
        });
    };

    $('#addBrendBtn').click(function () {
        $('#addBrend').modal();
    });

    $('#brendAddForm')[0].reset();

    $('body').on('submit', '#brendAddForm', function (e) {
        console.log("brendAddForm");

        var name = ($('#addBrend #name').val() || '').trim();

        var count = 0;

        if (name == '') {
            count = 1;
            document.getElementById("name").style.borderColor = "red";
        } else {
            document.getElementById("name").style.borderColor = "#154360";
        }

        if (count === 0) {
            $('#brendAddForm').find('button').hide();
            $.ajax({
                url: context + "/brends",
                type: 'POST',
                data: {
                    name: name,
                },
                success: function (data) {
                    swal(_lang[lang].added, '', 'success');
                    $('#brendAddForm')[0].reset();
                    brendTable.reload();
                    $('#addBrend').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#brendAddForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.brendEdit', function (e) {
        var id = $(this).attr('data-id');

        $.ajax({
            url: context + "/brends/" + id,
            type: 'GET',
            success: function (res) {
                console.log(res);
                $('#editBrend #brendId').val(id);
                $('#editBrend #editName').val(res.data.name);
                $('#editBrend').modal();
            },
            error: function (data) {
                swal(JSON.parse(data.responseText).statusMessage, '', 'error');
            }
        });
        e.preventDefault();
    });

    $('body').on('submit', '#brendEditForm', function (e) {

        var id = $('#editBrend #brendId').val();
        var name = ($('#editBrend #editName').val() || '').trim();

        var count = 0;

        if (name == '') {
            count = 1;
            document.getElementById("editName").style.borderColor = "red";
        } else {
            document.getElementById("editName").style.borderColor = "#154360";
        }
        if (count === 0) {
            $('#brendEditForm').find('button').hide();
            $.ajax({
                url: context + "/brends",
                type: "PUT",
                data: {
                    id: id,
                    name: name,
                },
                success: function (data) {
                    console.log(data);
                    swal(_lang[lang].saved, '', 'success');
                    brendTable.reload();
                    $('#editBrend').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#brendEditForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.brendDelete', function () {
        var id = $(this).attr('data-id');
        if (id < 1) {
            swal(_lang['en'].notExist, '', 'error');
        } else {
            swal({
                title: _lang['en'].areYouSure,
                text: _lang['en'].notRevert,
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#D61822',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Yes, delete it!'
            }, function (willDelete) {
                if (willDelete) {
                    $.ajax({
                        url: context + "/brend-delete/" + id,
                        type: 'GET',
                        success: function (data) {
                            console.log(data);
                            swal(_lang[lang].deleted, '', 'success');
                            brendTable.reload();
                        },
                        error: function (data) {
                            swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                        }
                    });
                }
            });
        }

    });

    return {
        // public functions
        init: function () {
            console.log("demo");
            demo();
        }
    };
}();

jQuery(document).ready(function () {
    brendAjaxTable.init();
});
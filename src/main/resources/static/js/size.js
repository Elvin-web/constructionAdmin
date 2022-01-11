var sizeAjaxTable = function () {
    console.log("contextRoot : " + context);
    var sizeTable = $('#size_data').mDatatable();
    var lang = 'en';

    var demo = function () {
        sizeTable.destroy();
        sizeTable = $('#size_data').mDatatable({
            data: {
                type: 'remote',
                source: {
                    read: {
                        method: 'GET',
                        url: context + '/sizes',
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
                },
                {
                    field: 'category',
                    title: 'Category'
                },
                {
                    field: 'actions',
                    title: 'Actions',
                    sortable: false
                }]
        });
    };

    $.ajax({
        url: context + "/category-list",
        type: 'GET',
        success: function (data) {
            var list = '';
            for (var i = 0; i < data.data.length; i++) {
                list += '<option value="' + data.data[i].id + '">' + data.data[i].nameAz + '</option>';
            }
            $("#categoryDropDown").html(list)
            $("#editCategoryDropDown").html(list);
        },
        error: function (data) {
            swal(JSON.parse(data.responseText).statusMessage, '', 'error');
        }
    });



    $('#addSizeBtn').click(function () {
        $('#addSize').modal();
    });

    $('#sizeAddForm')[0].reset();

    $('body').on('submit', '#sizeAddForm', function (e) {
        console.log("sizeAddForm");

        var name = ($('#addSize #name').val() || '').trim();
        var categoryId = $('#categoryDropDown').find('option:selected').attr('value');

        var count = 0;

        if (name == '') {
            count = 1;
            document.getElementById("name").style.borderColor = "red";
        } else {
            document.getElementById("name").style.borderColor = "#154360";
        }

        if (count === 0) {
            $('#sizeAddForm').find('button').hide();
            $.ajax({
                url: context + "/sizes",
                type: 'POST',
                data: {
                    name: name,
                    categoryId:categoryId
                },
                success: function (data) {
                    swal(_lang[lang].added, '', 'success');
                    $('#sizeAddForm')[0].reset();
                    sizeTable.reload();
                    $('#addSize').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#sizeAddForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.sizeEdit', function (e) {
        var id = $(this).attr('data-id');

        $.ajax({
            url: context + "/sizes/" + id,
            type: 'GET',
            success: function (res) {
                console.log(res);
                $('#editSize #sizeId').val(id);
                $('#editSize #editCategoryDropDown').val(res.data.categoryId);
                $('#editSize #editName').val(res.data.name);
                $('#editSize').modal();
            },
            error: function (data) {
                swal(JSON.parse(data.responseText).statusMessage, '', 'error');
            }
        });
        e.preventDefault();
    });

    $('body').on('submit', '#sizeEditForm', function (e) {

        var id = $('#editSize #sizeId').val();
        var name = ($('#editSize #editName').val() || '').trim();
        var categoryId = $('#editCategoryDropDown').find('option:selected').attr('value');
        var count = 0;

        if (name == '') {
            count = 1;
            document.getElementById("editName").style.borderColor = "red";
        } else {
            document.getElementById("editName").style.borderColor = "#154360";
        }
        if (count === 0) {
            $('#sizeEditForm').find('button').hide();
            $.ajax({
                url: context + "/sizes",
                type: "PUT",
                data: {
                    id: id,
                    name: name,
                    categoryId:categoryId
                },
                success: function (data) {
                    console.log(data);
                    swal(_lang[lang].saved, '', 'success');
                    sizeTable.reload();
                    $('#editSize').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#sizeEditForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.sizeDelete', function () {
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
                        url: context + "/size-delete/" + id,
                        type: 'GET',
                        success: function (data) {
                            console.log(data);
                            swal(_lang[lang].deleted, '', 'success');
                            sizeTable.reload();
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
    sizeAjaxTable.init();
});
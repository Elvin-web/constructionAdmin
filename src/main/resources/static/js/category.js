var categoryAjaxTable = function () {
    console.log("contextRoot : " + context);
    var categoryTable = $('#category_data').mDatatable();
    var lang = 'en';

    var demo = function () {
        categoryTable.destroy();
        categoryTable = $('#category_data').mDatatable({
            data: {
                type: 'remote',
                source: {
                    read: {
                        method: 'GET',
                        url: context + '/categories',
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
                    field: 'nameAz',
                    title: 'Name Az'
                }, {
                    field: 'nameEn',
                    title: 'Name En'
                }, {
                    field: 'nameRu',
                    title: 'Name Ru'
                }, {
                    field: 'actions',
                    title: 'Actions',
                    sortable: false
                }]
        });
    };

    $('#addCategoryBtn').click(function () {
        $('#addCategory').modal();
    });

    $('#categoryAddForm')[0].reset();

    $('body').on('submit', '#categoryAddForm', function (e) {
        console.log("categoryAddForm");

        var nameAz = ($('#addCategory #nameAz').val() || '').trim();
        var nameEn = ($('#addCategory #nameEn').val() || '').trim();
        var nameRu = ($('#addCategory #nameRu').val() || '').trim();

        var count = 0;

        if (nameAz == '') {
            count = 1;
            document.getElementById("nameAz").style.borderColor = "red";
        } else {
            document.getElementById("nameAz").style.borderColor = "#154360";
        }
        if (nameEn == '') {
            count = 1;
            document.getElementById("nameEn").style.borderColor = "red";
        } else {
            document.getElementById("nameEn").style.borderColor = "#154360";
        }
        if (nameRu == '') {
            count = 1;
            document.getElementById("nameRu").style.borderColor = "red";
        } else {
            document.getElementById("nameRu").style.borderColor = "#154360";
        }

        if (count === 0) {
            $('#categoryAddForm').find('button').hide();
            $.ajax({
                url: context + "/categories",
                type: 'POST',
                data: {
                    nameAz: nameAz,
                    nameEn: nameEn,
                    nameRu: nameRu,
                },
                success: function (data) {
                    swal(_lang[lang].added, '', 'success');
                    $('#categoryAddForm')[0].reset();
                    categoryTable.reload();
                    $('#addCategory').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#categoryAddForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.categoryEdit', function (e) {
        var id = $(this).attr('data-id');

        $.ajax({
            url: context + "/categories/" + id,
            type: 'GET',
            success: function (res) {
                console.log(res);
                $('#editCategory #categoryId').val(id);
                $('#editCategory #editNameAz').val(res.data.nameAz);
                $('#editCategory #editNameEn').val(res.data.nameEn);
                $('#editCategory #editNameRu').val(res.data.nameRu);
                $('#editCategory').modal();
            },
            error: function (data) {
                swal(JSON.parse(data.responseText).statusMessage, '', 'error');
            }
        });
        e.preventDefault();
    });

    $('body').on('submit', '#categoryEditForm', function (e) {

        var id = $('#editCategory #categoryId').val();
        var nameAz = ($('#editCategory #editNameAz').val() || '').trim();
        var nameEn = ($('#editCategory #editNameEn').val() || '').trim();
        var nameRu = ($('#editCategory #editNameRu').val() || '').trim();

        var count = 0;

        if (nameAz == '') {
            count = 1;
            document.getElementById("editNameAz").style.borderColor = "red";
        } else {
            document.getElementById("editNameAz").style.borderColor = "#154360";
        }
        if (nameEn == '') {
            count = 1;
            document.getElementById("editNameEn").style.borderColor = "red";
        } else {
            document.getElementById("editNameEn").style.borderColor = "#154360";
        }
        if (nameRu == '') {
            count = 1;
            document.getElementById("editNameRu").style.borderColor = "red";
        } else {
            document.getElementById("editNameRu").style.borderColor = "#154360";
        }
        if (count === 0) {
            $('#categoryEditForm').find('button').hide();
            $.ajax({
                url: context + "/categories",
                type: "PUT",
                data: {
                    id: id,
                    nameAz: nameAz,
                    nameEn: nameEn,
                    nameRu: nameRu,
                },
                success: function (data) {
                    console.log(data);
                    swal(_lang[lang].saved, '', 'success');
                    categoryTable.reload();
                    $('#editCategory').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#categoryEditForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.categoryDelete', function () {
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
                        url: context + "/category-delete/" + id,
                        type: 'GET',
                        success: function (data) {
                            console.log(data);
                            swal(_lang[lang].deleted, '', 'success');
                            categoryTable.reload();
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
    categoryAjaxTable.init();
});
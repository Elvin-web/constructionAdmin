var productAjaxTable = function () {
    console.log("contextRoot : " + context);
    var productTable = $('#product_data').mDatatable();
    var lang = 'en';

    var demo = function () {
        productTable.destroy();
        productTable = $('#product_data').mDatatable({
            data: {
                type: 'remote',
                source: {
                    read: {
                        method: 'GET',
                        url: context + '/products',
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
                    field: 'productCode',
                    title: 'Product code'
                },
                {
                    field: 'category',
                    title: 'Category'
                },
                {
                    field: 'oldAmount',
                    title: 'Old amount'
                },
                {
                    field: 'havingStatusName',
                    title: 'Having status'
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

    $.ajax({
        url: context + "/brend-list",
        type: 'GET',
        success: function (data) {
            var list = '';
            for (var i = 0; i < data.data.length; i++) {
                list += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
            }
            $("#brendDropDown").html(list)
            $("#editBrendDropDown").html(list);
        },
        error: function (data) {
            swal(JSON.parse(data.responseText).statusMessage, '', 'error');
        }
    });

    $.ajax({
        url: context + "/colour-list",
        type: 'GET',
        success: function (data) {
            var list = '';
            for (var i = 0; i < data.data.length; i++) {
                list += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
            }
            $("#colourDropDown").html(list)
            $("#editColourDropDown").html(list);
        },
        error: function (data) {
            swal(JSON.parse(data.responseText).statusMessage, '', 'error');
        }
    });
    $.ajax({
        url: context + "/country-list",
        type: 'GET',
        success: function (data) {
            var list = '';
            for (var i = 0; i < data.data.length; i++) {
                list += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
            }
            $("#countryDropDown").html(list)
            $("#editCountryDropDown").html(list);
        },
        error: function (data) {
            swal(JSON.parse(data.responseText).statusMessage, '', 'error');
        }
    });
    $.ajax({
        url: context + "/size-list",
        type: 'GET',
        success: function (data) {
            var list = '';
            for (var i = 0; i < data.data.length; i++) {
                list += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
            }
            $("#sizeDropDown").html(list)
            $("#editSizeDropDown").html(list);
        },
        error: function (data) {
            swal(JSON.parse(data.responseText).statusMessage, '', 'error');
        }
    });

    $.ajax({
        url: context + "/having_status-list",
        type: 'GET',
        success: function (data) {
            var list = '';
            for (var i = 0; i < data.data.length; i++) {
                list += '<option value="' + data.data[i].active + '">' + data.data[i].name + '</option>';
            }
            $("#havingStatusDropDown").html(list)
            $("#editHavingStatusDropDown").html(list);
        },
        error: function (data) {
            swal(JSON.parse(data.responseText).statusMessage, '', 'error');
        }
    });

    $('#addProductBtn').click(function () {
        $('#addProduct').modal();
    });

    $('#productAddForm')[0].reset();

    $('body').on('submit', '#productAddForm', function (e) {
        console.log("productAddForm");

        var formData = new FormData(this);
        console.log("data = " + formData);

        var name = ($('#addProduct #name').val() || '').trim();
        var productCode = ($('#addProduct #productCode').val() || '').trim();
        var categoryId = $('#categoryDropDown').find('option:selected').attr('value');
        var description = ($('#addProduct #description').val() || '').trim();
        var oldAmount = ($('#addProduct #oldAmount').val() || '').trim();
        var newAmount = ($('#addProduct #newAmount').val() || '').trim();
        var brendId = $('#brendDropDown').find('option:selected').attr('value');
        var colourId = $('#colourDropDown').find('option:selected').attr('value');
        var countryId = $('#countryDropDown').find('option:selected').attr('value');
        var sizeId = $('#sizeDropDown').find('option:selected').attr('value');
        var havingStatus = $('#havingStatusDropDown').find('option:selected').attr('value');

        var count = 0;

        if (name == '') {
            count = 1;
            document.getElementById("name").style.borderColor = "red";
        } else {
            document.getElementById("name").style.borderColor = "#154360";
        }

        if (productCode == '') {
            count = 1;
            document.getElementById("productCode").style.borderColor = "red";
        } else {
            document.getElementById("productCode").style.borderColor = "#154360";
        }
        if (description == '') {
            count = 1;
            document.getElementById("description").style.borderColor = "red";
        } else {
            document.getElementById("description").style.borderColor = "#154360";
        }
        if (oldAmount == '') {
            count = 1;
            document.getElementById("oldAmount").style.borderColor = "red";
        } else {
            document.getElementById("oldAmount").style.borderColor = "#154360";
        }

        if (count === 0) {
            $('#productAddForm').find('button').hide();
            $.ajax({
                url: context + "/products",
                type: 'POST',
                data: formData,
                contentType: false,
                cache: false,
                processData: false,

                success: function (data) {
                    console.log("productAddForm success " + data);
                    swal(_lang[lang].added, '', 'success');
                    $('#productAddForm')[0].reset();
                    productTable.reload();
                    $('#addProduct').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#productAddForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.productEdit', function (e) {
        var id = $(this).attr('data-id');

        $.ajax({
            url: context + "/products/" + id,
            type: 'GET',
            success: function (res) {
                console.log(res);
                $('#editProduct #productId').val(id);
                $('#editProduct #editCategoryDropDown').val(res.data.categoryId);
                $('#editProduct #editName').val(res.data.name);
                $('#editProduct #editProductCode').val(res.data.productCode);
                $('#editProduct #editDescription').val(res.data.description);
                $('#editProduct #editOldAmount').val(res.data.oldAmount);
                $('#editProduct #editNewAmount').val(res.data.newAmount);
                $('#editProduct #editBrendDropDown').val(res.data.brendId);
                $('#editProduct #editColourDropDown').val(res.data.colourId);
                $('#editProduct #editCountryDropDown').val(res.data.countryId);
                $('#editProduct #editSizeDropDown').val(res.data.sizeId);
                $('#editProduct #editHavingStatusDropDown').val(res.data.havingStatus);

                $('#editProduct').modal();
            },
            error: function (data) {
                swal(JSON.parse(data.responseText).statusMessage, '', 'error');
            }
        });
        e.preventDefault();
    });

    $('body').on('submit', '#productEditForm', function (e) {

        var formData = new FormData(this);

        var id = $('#editProduct #productId').val();
        var name = ($('#editProduct #editName').val() || '').trim();
        var categoryId = $('#editCategoryDropDown').find('option:selected').attr('value');
        var productCode = ($('#editProduct #editProductCode').val() || '').trim();
        var description = ($('#editProduct #editDescription').val() || '').trim();
        var oldAmount = ($('#editProduct #editOldAmount').val() || '').trim();
        var newAmount = ($('#editProduct #editNewAmount').val() || '').trim();
        var brendId = $('#editBrendDropDown').find('option:selected').attr('value');
        var colourId = $('#editColourDropDown').find('option:selected').attr('value');
        var countryId = $('#editCountryDropDown').find('option:selected').attr('value');
        var sizeId = $('#editSizeDropDown').find('option:selected').attr('value');
        var havingStatus = $('#editHavingStatusDropDown').find('option:selected').attr('value');

        var count = 0;

        if (name == '') {
            count = 1;
            document.getElementById("editName").style.borderColor = "red";
        } else {
            document.getElementById("editName").style.borderColor = "#154360";
        }

        if (productCode == '') {
            count = 1;
            document.getElementById("productCode").style.borderColor = "red";
        } else {
            document.getElementById("productCode").style.borderColor = "#154360";
        }
        if (description == '') {
            count = 1;
            document.getElementById("description").style.borderColor = "red";
        } else {
            document.getElementById("description").style.borderColor = "#154360";
        }
        if (oldAmount == '') {
            count = 1;
            document.getElementById("oldAmount").style.borderColor = "red";
        } else {
            document.getElementById("oldAmount").style.borderColor = "#154360";
        }
        if (count === 0) {
            $('#productEditForm').find('button').hide();
            $.ajax({
                url: context + "/products?id=" + id,
                type: "PUT",
                data: formData,
                contentType: false,
                cache: false,
                processData: false,
                // data: {
                //     id: id,
                //     name: name,
                //     categoryId: categoryId,
                //     productCode: productCode,
                //     description: description,
                //     oldAmount: oldAmount,
                //     newAmount: newAmount,
                //     brendId: brendId,
                //     colourId: colourId,
                //     countryId: countryId,
                //     sizeId: sizeId,
                //     havingStatus: havingStatus
                // },
                success: function (data) {
                    console.log(data);
                    swal(_lang[lang].saved, '', 'success');
                    productTable.reload();
                    $('#editProduct').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#productEditForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.productDelete', function () {
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
                        url: context + "/product-delete/" + id,
                        type: 'GET',
                        success: function (data) {
                            console.log(data);
                            swal(_lang[lang].deleted, '', 'success');
                            productTable.reload();
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
    productAjaxTable.init();
});
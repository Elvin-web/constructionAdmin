var userAjaxTable = function () {
    console.log("userAjaxTable");
    console.log("contextRoot : " + context);
    var userTable = $('#user_data').mDatatable();
    var lang = 'en';

    var demo = function () {
        userTable.destroy();
        userTable = $('#user_data').mDatatable({
            data: {
                type: 'remote',
                source: {
                    read: {
                        method: 'GET',
                        url: context + '/users',
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
                }, {
                    field: 'surname',
                    title: 'Surname'
                }, {
                    field: 'username',
                    title: 'User Name',
                    width: 185
                }, {
                    field: 'mobile',
                    title: 'Mobile'
                }, {
                    field: 'roleName',
                    title: 'Role Name'
                }, {
                    field: 'status',
                    title: 'Status'
                }, {
                    field: 'createdDate',
                    title: 'Created Date'
                }, {
                    field: 'actions',
                    title: 'Actions',
                    sortable: false
                }]
        });
    };

    $.ajax({
        url: context + "/roles",
        type: 'GET',
        success: function (data) {
            var list = '';
            for (var i = 0; i < data.data.length; i++) {
                if (data.data[i].id === 2 || data.data[i].id === 3)
                    list += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
            }
            $("#roleDropDown").html(list)
            $("#editRoleDropDown").html(list);
        },
        error: function (data) {
            swal(JSON.parse(data.responseText).statusMessage, '', 'error');
        }
    });

    // var hospitalId;
    // $('body').on('change', '#roleDropDown', function (e) {
    //     var roleId = $('#roleDropDown').find('option:selected').attr('value');
    //     if (roleId == 3) {
    //         $.ajax({
    //             url: context + "/hospital-list",
    //             type: 'GET',
    //             success: function (data) {
    //                 var list = '';
    //                 for (var i = 0; i < data.data.length; i++) {
    //                     list += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
    //                 }
    //                 $("#hospitalDropDown").html(list)
    //             },
    //             error: function (data) {
    //                 swal(JSON.parse(data.responseText).statusMessage, '', 'error');
    //             }
    //         });
    //         $('#hospitalDropDownDiv').css("display", "block");
    //         $('#imageDiv').css("display", "block");
    //     } else {
    //         $('#hospitalDropDownDiv').css("display", "none");
    //         $('#imageDiv').css("display", "none");
    //     }
    //     e.preventDefault();
    // });

    $('#userAddForm')[0].reset();

    $('body').on('submit', '#userAddForm', function (e) {
        console.log("userAddForm");

        var formData = new FormData(this);
        console.log("data = " + formData);
        var name = ($('#addUser #name').val() || '').trim();
        var surname = ($('#addUser #surname').val() || '').trim();
        var username = ($('#addUser #username').val() || '').trim();
        var mobile = ($('#addUser #mobile').val() || '').trim();
        var roleId = $('#roleDropDown').find('option:selected').attr('value');
        var password = ($('#addUser #password').val() || '').trim();
        var confirmPassword = ($('#addUser #confirmPassword').val() || '').trim();
     //   hospitalId = $('#hospitalDropDown').find('option:selected').attr('value');

        var count = 0;

        if (name == '') {
            count = 1;
            document.getElementById("name").style.borderColor = "red";
        } else {
            document.getElementById("name").style.borderColor = "#154360";
        }

        if (surname == '') {
            count = 1;
            document.getElementById("surname").style.borderColor = "red";
        } else {
            document.getElementById("surname").style.borderColor = "#154360";
        }

        if (username == '') {
            count = 1;
            document.getElementById("username").style.borderColor = "red";
        } else {
            document.getElementById("username").style.borderColor = "#154360";
        }

        if (mobile == '') {
            count = 1;
            document.getElementById("mobile").style.borderColor = "red";
        } else {
            document.getElementById("mobile").style.borderColor = "#154360";
        }

        if (password == '') {
            count = 1;
            document.getElementById("password").style.borderColor = "red";
        } else {
            document.getElementById("password").style.borderColor = "#154360";
        }

        if (confirmPassword == '') {
            count = 1;
            document.getElementById("confirmPassword").style.borderColor = "red";
        } else {
            document.getElementById("confirmPassword").style.borderColor = "#154360";
        }

        if (password.length !== 0 && confirmPassword.length !== 0) {
            if (password !== confirmPassword) {
                count++;
                swal(_lang[lang].notEquals, '', 'warning');
            }
        }

        if (count === 0) {
            $('#userAddForm').find('button').hide();
            $.ajax({
                url: context + "/users",
                type: 'POST',
                data: formData,
                contentType: false,
                cache: false,
                processData: false,
                success: function (data) {
                    console.log("addUserForm success " + data);
                    swal(_lang[lang].added, '', 'success');
                    $('#userAddForm')[0].reset();
                    userTable.reload();
                    $('#addUser').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#userAddForm').find('button').show();
            });
        }
        e.preventDefault();
    });

    $('body').on('click', '.userEdit', function (e) {

        var id = $(this).attr('data-id');

        $.ajax({
            url: context + "/users/" + id,
            type: 'GET',
            success: function (res) {
                console.log(res);
                $('#editUser #userId').val(id);
                $('#editUser #editName').val(res.data.name);
                $('#editUser #editSurname').val(res.data.surname);
                $('#editUser #editUsername').val(res.data.username);
                $('#editUser #editMobile').val(res.data.mobile);
                $('#editUser #editRoleDropDown').val(res.data.roleId);
                $('#editUser #editPassword').val('');
                $('#editUser #editConfirmPassword').val('');

                // if (res.data.roleId == 3) {
                //     $.ajax({
                //         url: context + "/hospital-list",
                //         type: 'GET',
                //         success: function (data) {
                //             var list = '';
                //             for (var i = 0; i < data.data.length; i++) {
                //                 list += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
                //             }
                //             $("#editHospitalDropDown").html(list);
                //             if (res.data.hospitalId != null) {
                //                 $('#editUser #editHospitalDropDown').val(res.data.hospitalId);
                //             }
                //         },
                //         error: function (data) {
                //             swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                //         }
                //     });
                //     $('#editHospitalDropDownDiv').css("display", "block");
                //     $('#editImageDiv').css("display", "block");
                // } else {
                //     $('#editHospitalDropDownDiv').css("display", "none");
                //     $('#editImageDiv').css("display", "none");
                // }

                $('#editUser').modal();
            },
            error: function (data) {
                swal(JSON.parse(data.responseText).statusMessage, '', 'error');
            }
        });
        e.preventDefault();
    });

    // $('body').on('change', '#editRoleDropDown', function (e) {
    //     var roleId = $('#editRoleDropDown').find('option:selected').attr('value');
    //     if (roleId == 3) {
    //         $.ajax({
    //             url: context + "/hospital-list",
    //             type: 'GET',
    //             success: function (data) {
    //                 var list = '';
    //                 for (var i = 0; i < data.data.length; i++) {
    //                     list += '<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>';
    //                 }
    //                 $("#editHospitalDropDown").html(list);
    //                 if (res.data.hospitalId != null) {
    //                     $('#editUser #editHospitalDropDown').val(res.data.hospitalId);
    //                 }
    //             },
    //             error: function (data) {
    //                 swal(JSON.parse(data.responseText).statusMessage, '', 'error');
    //             }
    //         });
    //         $('#editHospitalDropDownDiv').css("display", "block");
    //         $('#editImageDiv').css("display", "block");
    //     } else {
    //         $('#editHospitalDropDownDiv').css("display", "none");
    //         $('#editImageDiv').css("display", "none");
    //     }
    //     e.preventDefault();
    // });

    $('body').on('submit', '#userEditForm', function (e) {

        var formData = new FormData(this);
        var id = $('#editUser #userId').val();
        var name = ($('#editUser #editName').val() || '').trim();
        var surname = ($('#editUser #editSurname').val() || '').trim();
        var username = ($('#editUser #editUsername').val() || '').trim();
        var mobile = ($('#editUser #editMobile').val() || '').trim();
        var roleId = $('#editRoleDropDown').find('option:selected').attr('value');
        var password = ($('#editUser #editPassword').val() || '').trim();
        var confirmPassword = ($('#editUser #editConfirmPassword').val() || '').trim();
     //   hospitalId = $('#editUser #editHospitalDropDown').find('option:selected').attr('value');

     //   if (roleId != 3) hospitalId = null;

        var count = 0;

        if (name == '') {
            count = 1;
            document.getElementById("editName").style.borderColor = "red";
        } else {
            document.getElementById("editName").style.borderColor = "#154360";
        }

        if (surname == '') {
            count = 1;
            document.getElementById("editSurname").style.borderColor = "red";
        } else {
            document.getElementById("editSurname").style.borderColor = "#154360";
        }

        if (username == '') {
            count = 1;
            document.getElementById("editUsername").style.borderColor = "red";
        } else {
            document.getElementById("editUsername").style.borderColor = "#154360";
        }

        if (mobile == '') {
            count = 1;
            document.getElementById("editMobile").style.borderColor = "red";
        } else {
            document.getElementById("editMobile").style.borderColor = "#154360";
        }

        if (password == '') {
            count = 1;
            document.getElementById("editPassword").style.borderColor = "red";
        } else {
            document.getElementById("editPassword").style.borderColor = "#154360";
        }

        if (confirmPassword == '') {
            count = 1;
            document.getElementById("editConfirmPassword").style.borderColor = "red";
        } else {
            document.getElementById("editConfirmPassword").style.borderColor = "#154360";
        }


        if (password.length !== 0 && confirmPassword.length !== 0) {
            if (password !== confirmPassword) {
                count++;
                swal(_lang[lang].notEquals, '', 'warning');
            }
        }

        if (count === 0) {
            $('#userEditForm').find('button').hide();
            $.ajax({
                url: context + "/users?id=" + id,
                type: "PUT",
                data: formData,
                contentType: false,
                cache: false,
                processData: false,
                success: function (data) {
                    console.log(data);
                    swal(_lang[lang].saved, '', 'success');
                    userTable.reload();
                    $('#editUser').modal('hide');
                },
                error: function (data) {
                    swal(JSON.parse(data.responseText).statusMessage, '', 'error');
                }
            }).done(function () {
                $('#userEditForm').find('button').show();
            });
            // }
        }

        e.preventDefault();
    });

    $('body').on('click', '.userDelete', function () {
        console.log('userDelete');
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
                        url: context + "/user-delete/" + id,
                        type: 'GET',
                        success: function (data) {
                            console.log(data);
                            swal(_lang[lang].deleted, '', 'success');
                            userTable.reload();
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
    userAjaxTable.init();
});
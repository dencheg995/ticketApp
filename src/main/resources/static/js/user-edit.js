function changeUser(userId) {

    let url = '/edit/user/update';

    let wrap = {
        id: id,
        firstName: $('#edit-user-first-name').val(),
        lastName: $('#edit-user-last-name').val(),
        phoneNumber: $('#edit-user-phone-number').val(),
        email: $('#edit-user-email').val(),
        password: $('#edit-user-password').val(),
        enabled: true
    };

    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(wrap),
        success: function (result) {
            window.location.replace("/lk")
        },
        error: function (e) {
            setErrorMessage(e.responseJSON.message);
            console.log(e.responseText);
        }
    });
}

$(document).on('click', '#editUser', function editUserBtn() {
    $('#column1').find('input').each(function () {
        $(this)[0].disabled = $(this)[0].disabled !== true;
    });

    $('#column1').find('select').each(function () {
        $(this)[0].disabled = $(this)[0].disabled !== true;
    });

        $('#editUser').attr("class", "btn btn-secondary")[0].innerText = 'Заблокировать';
        $("#edit-user-password").prop("disabled", true);
        $('#editUser').attr("class", "btn btn-primary")[0].innerText = 'Редактировать';
        $("#edit-user-password").prop("disabled", true);
});
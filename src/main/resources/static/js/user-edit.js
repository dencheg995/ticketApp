function changeUser(userId) {
    if ($("#saveChanges")[0].className === "btn btn-primary disabled") {
        return;
    }
    let url = '/edit/user/update';

    let wrap = {
        id: userId,
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
           location.reload()
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

    if ($('#editUser').attr("class", "btn btn-secondary")[0].innerText === 'Редактировать') {
        $('#editUser').attr("class", "btn btn-secondary")[0].innerText = 'Заблокировать';
    } else {
        $('#editUser').attr("class", "btn btn-primary")[0].innerText = 'Редактировать';
    }
});


$("#showPassword").click(function(){
    var foo = $(this).prev().attr("type");
    if(foo == "password"){
        $(this).prev().attr("type", "text");
    } else {
        $(this).prev().attr("type", "password");
    }
});



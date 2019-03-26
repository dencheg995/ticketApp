function registerUser() {
    let url = '/register';
    var obj = {};
    obj["id"] = 2;
    obj["roleName"] = 'USER';
    var myRows = [];
    myRows.push(obj);
    let wrap = {
        firstName: $('#add-user-first-name').val(),
        lastName: $('#add-user-last-name').val(),
        phoneNumber: $('#add-user-phone-number').val(),
        email: $('#add-user-email').val(),
        password: $('#add-user-password').val(),
        promoCode : $('#add-user-promo').val() ,
        isEnabled: true,
        role: myRows
    };

    if ($("#add-user-first-name").val() == "" || $("#add-user-last-name").val() == "" || $('#add-user-phone-number').val() || $('#add-user-email').val() || $('#add-user-password').val()) {
        alert('Заполните все поля');
        $('#saveChanges').prop('disabled', true);
        location.reload()
    } else {
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(wrap),
            beforeSend: function () {
                var current = document.getElementById("message");
                current.style.color = "darkorange";
                current.textContent = "Загрузка...";
            },
            success: function (result) {
                window.location.replace("/login")
            },
            error: function (e) {
                alert('Пользователь не был зарегистрирован');
            }
        });
    }
}
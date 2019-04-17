function registerUser() {
    if ($("#saveChanges")[0].className === "btn btn-primary disabled") {
        return;
    }
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
        isEnabled: true,
        role: myRows
    };

    if ($("#add-user-first-name").val() == "" || $("#add-user-last-name").val() == "" ||
        $('#add-user-phone-number').val() == "" || $('#add-user-email').val() == "" || $('#add-user-password').val() =="") {
        alert('Заполните все поля');
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

function vkLogin() {
    var url = "https://oauth.vk.com/authorize?client_id=6941019&display=page&redirect_uri=http://localhost:8080/vk/token&scope=offline,questions,offers,pages,wall&response_type=code&v=5.92"
    window.open(url,  '_self');
}
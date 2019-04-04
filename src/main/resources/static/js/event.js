function buyTicket(ticketId, ticketPrice, eventID) {

    if ($("#first-name-for-buy-ticket").val() == "" || $("#last-name-for-buy-ticket").val() == "" ||
        $('#add-user-email').val() == "" || $('#add-user-phone-number').val() == "") {
        alert('Заполните все поля');
    } else {
        var pocket;
        ticketPrice = ticketPrice * $("#ticketCount").val();
        var succsessURL = "http://localhost:8080/purchase/tickets?firstName=" + $("#first-name-for-buy-ticket").val() + "&lastName=" + $("#last-name-for-buy-ticket").val()
            + "&email=" + $('#add-user-email').val() + "&phoneNumber=" + $('#add-user-phone-number').val() + "&ticketId=" + ticketId + "&priceTicket=" + ticketPrice + "&countTicket=" + $("#ticketCount").val();
        var url = "https://money.yandex.ru/transfer?receiver=";

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/get/event",
            data: {eventId: eventID},
            success: function (data) {
                url = url + data.pocket + "&sum=" + ticketPrice + "&successURL=" + succsessURL + "&label=SPBJikP8VwRo6ByuhColzWFWKb48KvNsaf1jpHia3Zp0rNkz&targets=%2316972&comment=&origin=form&selectedPaymentType=AC&destination=%2316972&form-comment=йц&short-dest=&quickpay-form=donate";
                window.open(url);
            }
        });
    }

    // $.ajax({
    //     type: "POST",
    //     contentType: "application/json",
    //     url: url,
    //     success: function () {
    //         location.reload();
    //         console.log("Success");
    //     },
    //     error: function (e) {
    //         alert("Неверный формат данных!");
    //         console.log("ERROR: ", e);
    //     }
    // });


    //https://money.yandex.ru/transfer?receiver=410018098845268&sum=100&successURL=https%3A%2F%2Fsoldout.media%2Ftickets%2Fpayment%2Fpay.php%3Fhash%3D0440e9f6edb931aeb8859b3ec281c57e%26uid%3D42713&quickpay-back-url=https%3A%2F%2Fsoldout.media%2Ftickets%2Fpayment%2Fpay.php%3Fhash%3D0440e9f6edb931aeb8859b3ec281c57e%26uid%3D42713&shop-host=soldout.media&label=SPBJikP8VwRo6ByuhColzWFWKb48KvNsaf1jpHia3Zp0rNkz&targets=%2316972&comment=&origin=form&selectedPaymentType=AC&destination=%2316972&form-comment=%D0%A1%D0%95%D0%9A%D0%A2%D0%90%20II&short-dest=&quickpay-form=donate
    // https://money.yandex.ru/transfer?receiver=410018098845268-------sum=100-------successURL=-------quickpay-back-url=-------shop-host=-------label=-------targets=-------comment=-------origin=form-------selectedPaymentType=AC-------destination=-------form-comment=------short-dest=-------quickpay-form=donate
}

function addTicket() {
    var formData = {
        ticketCount: $("#ticketCount").val(),
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/event/app/{id}",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function () {
            location.reload();
            console.log("Success");
        },
        error: function (e) {
            alert("Неверный формат данных!");
            console.log("ERROR: ", e);
        }
    });
}

function putTicket(eventId) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/put/new/ticket",
        data : {eventId : eventId},
        success: function () {
            location.reload()
        }
    });
}

function editTicket(ticketId) {

    if ($("#editButton" + ticketId).text() === "Редактировать билет") {
        $("#ticketType" + ticketId).removeAttr('disabled');
        $("#ticketPrice" + ticketId).removeAttr('disabled');
        $("#ticketCount" + ticketId).removeAttr('disabled');
        $("#editButton" + ticketId).html('Сохранить');
    } else if ($("#editButton" + ticketId).text() === "Сохранить") {

        var formData = {
            ticketId: ticketId,
            ticketType: $("#ticketType" + ticketId).val(),
            ticketCount: $("#ticketCount" + ticketId).val(),
            ticketPrice: $("#ticketPrice" + ticketId).val()
        };
        var url = "/edit/ticket"
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: url,
            data: formData,
            success: function () {
                location.reload()
            }
        });
    }
}

function removeTicket(ticketId) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/remove/ticket",
        data: {ticketId : ticketId} ,
        success: function () {
            location.reload()
        }
    });
}

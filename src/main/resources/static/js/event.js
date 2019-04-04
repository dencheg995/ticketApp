
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
        contentType: "application/json; charset=utf-8",
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


function buyTicket(ticketId, ticketPrice, eventId) {
    if ($("#first-name-for-buy-ticket").val() == "" || $("#last-name-for-buy-ticket").val() == "" ||
        $('#add-user-email').val() == "" || $('#add-user-phone-number').val() == "") {
        alert('Заполните все поля');
    } else {
        var url = "/event/app/" + eventId + "/purchase/tickets";

        let wrap = {
            firstName: $("#first-name-for-buy-ticket").val(),
            lastName: $("#last-name-for-buy-ticket").val(),
            phoneNumber: $('#add-user-phone-number').val(),
            email: $('#add-user-email').val() ,
            ticketId: ticketId,
            ticketPrice: ticketPrice,
            countTicket: $("#ticketCount").val()
        };

        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(wrap),
            success: function (result) {
                location.reload()
            }
        });
    }
}

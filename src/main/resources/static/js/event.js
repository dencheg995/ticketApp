function addEvent() {

    var formData = {
        id: $("#id") .val(),
        name: $("#name").val(),
        address: $("#address").val(),
        pocket: $("#pocket").val(),
        nameTicket: $("#nameTicket").val(),
        priceTicket: $("#priceTicket").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/client/profile/event",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function () {
            alert("Event add!");
            console.log("Success");
        },
        error: function (e) {
            alert("Неверный формат данных!");
            console.log("ERROR: ", e);
        }
    });
}
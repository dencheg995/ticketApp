function addEvent() {

    var formData = {
        name: $("#name").val(),
        address: $("#address").val(),
        pocket: $("#pocket").val(),
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/lk/add/event",
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

// function createTickets(eventId) {
//
//     var url = '/tickets/new/' + eventId + "/"
//     $.ajax({
//         type: "GET",
//         url: url,
//     });
// }

function openTicketPage() {

    var eventId = $("#eventName").val()
    var url = '/tickets/new/?eventId=' + eventId
    window.open(url, '_self');
}
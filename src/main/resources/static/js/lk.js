

// function createTickets(eventId) {
//
//     var url = '/tickets/new/' + eventId + "/"
//     $.ajax({
//         type: "GET",
//         url: url,
//     });
// }

function openTicketPage() {
    var eventId = $("#eventName").val();
    if (eventId != null && eventId != "") {
        var url = '/tickets/new/?eventId=' + eventId
        window.open(url, '_self');
    }
}

function openEventPage() {

    var eventId = $("#eventName").val()
    if (eventId != null && eventId != "") {
        var url = '/event/new/?eventId=' + eventId
        window.open(url, '_self');
    }
}
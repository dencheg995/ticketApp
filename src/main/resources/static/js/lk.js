

// function createTickets(eventId) {
//
//     var url = '/tickets/new/' + eventId + "/"
//     $.ajax({
//         type: "GET",
//         url: url,
//     });
// }

function openTicketPage(eventId) {
    if (eventId != null && eventId != "") {
        var url = '/tickets/new/?eventId=' + eventId
        window.open(url, '_self');
    }
}

function openSalePage(eventId) {
    if (eventId != null && eventId != "") {
        var url = '/event/app/' + eventId
        window.open(url, '_self');
    }
}

function openEventPage(eventId) {

    if (eventId != null && eventId != "") {
        var url = '/event/new/?eventId=' + eventId
        window.open(url, '_self');
    }
}
function openPromoPage(ticketId, eventId) {
    if (ticketId != null && ticketId != "" && eventId != null && eventId != "") {
        var url = '/promo/new/?ticketId=' + ticketId + '&eventId=' + eventId;
        window.open(url, '_self');
    }
}

function listPromo(ticketId, eventId) {
    if (ticketId != null && ticketId != "" && eventId != null && eventId != "") {
        var url = '/promo/edit/?ticketId=' + ticketId + '&eventId=' + eventId;
        window.open(url, '_self');
    }
}
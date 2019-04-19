function openPromoPage(ticketId) {
    if (ticketId != null && ticketId != "") {
        var url = '/promo/new/?ticketId=' + ticketId
        window.open(url, '_self');
    }
}

function listPromo(ticketId) {
    if (ticketId != null && ticketId != "") {
        var url = '/promo/edit/?ticketId=' + ticketId
        window.open(url, '_self');
    }
}
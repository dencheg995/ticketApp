function openPromoPage(ticketId) {
    if (ticketId != null && ticketId != "") {
        var url = '/promo/new/?ticketId=' + ticketId
        window.open(url, '_self');
    }
}
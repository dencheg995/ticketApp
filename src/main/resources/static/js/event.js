function buyTicket(ticketPrice, ticketId) {

    var url = "https://money.yandex.ru/transfer?receiver=410018098845268&sum="+ ticketPrice
    window.open(url);
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

    // https://money.yandex.ru/transfer?receiver=410018098845268-------sum=100-------successURL=-------quickpay-back-url=-------shop-host=-------label=-------targets=-------comment=-------origin=form-------selectedPaymentType=AC-------destination=-------form-comment=------short-dest=-------quickpay-form=donate
}
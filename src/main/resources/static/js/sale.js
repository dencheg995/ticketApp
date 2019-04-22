var sum = 0;
var currentCount = 0;
var count = 0;
var prevId = 0;
localStorage.setItem("currentCount", currentCount);
localStorage.setItem("count", count);
localStorage.setItem("sum", sum);
function addition(ticketId, ticketPrice, ticketCountMax) {
    var cId = "currentCount" + ticketId;
    $("#down" + ticketId).attr("disabled", false);
    $("#saleButton").attr("disabled", false);
    $("#down" + ticketId).attr("field", "off");
    if (ticketCountMax != parseInt(document.getElementById(cId).value)) {
        currentCount = parseInt(localStorage.getItem("currentCount"));
        count = parseInt(localStorage.getItem("count"));
        currentCount =+ parseInt(document.getElementById(cId).value);
        count = count + (currentCount - (currentCount - 1))
        localStorage.setItem("currentCount", currentCount);
        localStorage.setItem("count", count);
        if (parseInt(currentCount) == 0) {
            $("#sumResult").val(sum);
        }
        sum = parseInt(localStorage.getItem("sum"));
        sum += ticketPrice;

        localStorage.setItem("sum", sum);

        localStorage.setItem("prevId", ticketId);

        $("#sumResult").val(sum);
    } else {
        if ($("#up" + ticketId).attr("field") != "on") {
            currentCount = parseInt(localStorage.getItem("currentCount"));
            currentCount = +parseInt(document.getElementById(cId).value);
            localStorage.setItem("currentCount", currentCount);
            if (parseInt(currentCount) == 0) {
                $("#sumResult").val(sum);
            }
            sum = parseInt(localStorage.getItem("sum"));

            sum += ticketPrice;

            localStorage.setItem("sum", sum);
            if (parseInt(localStorage.getItem("prevId")) != parseInt(ticketId))
                localStorage.setItem("prevId", ticketId);
            $("#sumResult").val(sum);
        }
        $("#up" + ticketId).attr("field", "on");
        $("#up" + ticketId).attr("disabled", true)
    }
}

function subtraction(ticketId, ticketPrice) {
    var cId = "currentCount" + ticketId;
    $("#up" + ticketId).attr("disabled", false);
    $("#up" + ticketId).attr("field", "off");
    if (parseInt(document.getElementById(cId).value) > 0) {
        currentCount = parseInt(localStorage.getItem("currentCount"));
        currentCount = +parseInt(document.getElementById(cId).value);
        if (parseInt(currentCount) == 0) {
            $("#sumResult").val(sum);
        }
        localStorage.setItem("currentCount", currentCount);

        sum = parseInt(localStorage.getItem("sum"));
        sum -= ticketPrice;
        localStorage.setItem("sum", sum);
        localStorage.setItem("prevId", ticketId);
        $("#sumResult").val(sum);
    }
         else {
            if ($("#down" + ticketId).attr("field") != "on") {
                currentCount = parseInt(localStorage.getItem("currentCount"));
                currentCount = +parseInt(document.getElementById(cId).value);
                localStorage.setItem("currentCount", currentCount);
                if (parseInt(currentCount) == 0) {
                    $("#sumResult").val(sum);
                }
                sum = parseInt(localStorage.getItem("sum"));

                sum -= ticketPrice;
                localStorage.setItem("sum", sum);
                if (sum == 0){
                    $("#saleButton").attr("disabled", true);
                }
                if (parseInt(localStorage.getItem("prevId")) != parseInt(ticketId))
                    localStorage.setItem("prevId", ticketId);
                $("#sumResult").val(sum);
            }
            $("#down" + ticketId).attr("disabled", true);
            $("#down" + ticketId).attr("field", "on")

    }
}

$(document).ready(function () {
    $("#sumResult").val(sum);
    $('#saleButton').click(function() {
        $('#ticketModal').modal('show');
    })
});
// $(document).ready(function () {
//     $('#promoTable').delegate('.promo-modal', 'click', function() {
//         $('#editPromoModal').modal('show');
//         var idPromo = $(this).closest('tr').children('td:first').text();
//         $.ajax({
//             type: "GET",
//             contentType: 'application/json; charset=UTF-8',
//             url: '/get-promo',
//             data: {
//                 promoId: idPromo
//             },
//             success: function (promo) {
//                 $("#edit-promo-sale").val(promo.sale);
//                 $("#edit-promo-date-start").val(promo.dateStart);
//                 $("#edit-promo-date-end").val(promo.dateEnd);
//                 $("#edit-promo-date-count").val(promo.count);
//                 $("#edit-promo").each(function() {
//                     $(this).val(promo.promocode
//                         .join("\n"));
//                 });
//             }
//         });
//     })
// });

function openLKPage() {
    var url = '/lk';
    window.open(url, '_self');
}
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
        success: function (html) {
            location.reload()
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
        data: {eventId: eventId},
        success: function (html) {
            location.reload();
        }
    });
}

function editTicket(ticketId) {

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

function openTicketInput(ticketId) {
    if ($("#ticketType" + ticketId).prop('disabled') === true)
        $("#ticketType" + ticketId).removeAttr('disabled');
    $("#ticketPrice" + ticketId).removeAttr('disabled');
    $("#ticketCount" + ticketId).removeAttr('disabled');
}


function removeTicket(ticketId) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/remove/ticket",
        data: {ticketId: ticketId},
        success: function () {
            location.reload()
        }
    });
}


function buyTicket(eventId) {
    if ($("#first-name-for-buy-ticket").val() == "" || $("#last-name-for-buy-ticket").val() == "" ||
        $('#add-user-email').val() == "" || $('#add-user-phone-number').val() == "") {
        alert('Заполните все поля');
    } else {
        var url = "/event/app/" + eventId + "/purchase/tickets";
        var date = new Date();
        offset = date.getTimezoneOffset();
        date.setMinutes(date.getMinutes() + offset);
        var count = 0;
        var k = 0;
        var obj = {};
        var j = 1;
        $(".qet").each(function (i, index) {
            $(".count").each(function (idx, c) {
                if ($(this).val() > 0 && (k == idx)) {
                    count = $(this).val();
                    obj[$(index)[0].id] = count;
                    return false;
                }
            });
            k++;
            return;
        });

        var urlParametrs = "";

        let wrap = {
            firstName: $("#first-name-for-buy-ticket").val(),
            lastName: $("#last-name-for-buy-ticket").val(),
            phoneNumber: $('#add-user-phone-number').val(),
            email: $('#add-user-email').val(),
            ticketId: obj,
            ticketPrice: $("#sumResult").val(),
            countTicket: localStorage.getItem("count"),
            date: date,
            promo: $("#promo-for-buy-ticket").val()
        };
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(wrap),
            success: function (result) {
                for (var i = 1; i < result.length; i++) {
                    if (i <= result[0] & i % 2 != 0) {
                        $(".fillBody").append("<div class='row'> \
                            <div class='col left-position'> \
                             <p style='color: #fff;'>" + result[i].ticketType + "</p>  \
                            </div> \
                            <div class='col center-position'> \
                            <p id ='ticketCount2' style='color: #fff;'>" + result[i + 1] + "</p> \
                            </div> \
                            <div class='col right-position'> \
                            <p id ='ticketPrice2' style='color: #fff;'>" + result[i].ticketPrice * result[i + 1] + "</p> \
                            </div> \
                            </div>");
                        urlParametrs += "indexTicket[]=" + result[i].id + "&"

                    } else if (i > result[0] & i < result.length - 1) {
                        $("#consumerFName").text(result[i].firstName);
                        $("#consumerLName").text(result[i].lastName);
                        $("#consumerEmail").text(result[i].email);
                        $("#consumerTelephone").text(result[i].phoneNumber)
                    } else if (i < result.length - 1) {
                        $("#lastSum").text((result[result.length - 1][0] / 1.1).toFixed(0));
                        $("#service").text((result[result.length - 1][0] - result[result.length - 1][0] / 1.1).toFixed(2));
                        $("#forPay").text((result[result.length - 1][0]).toFixed(1))
                        if (j == 1) {
                            $(".yandex-form").append("<form id='yandexForm' method='POST' action='https://money.yandex.ru/quickpay/confirm.xml'> \
                    <input type='hidden' id = 'receiver' name='receiver' value=" + result[result.length - 1][2] + " /> \
                    <input type='hidden' id = 'label' name='label' value=" + '#' + result[result.length - 1][1] + " /> \
                    <input type='hidden' name='quickpay-form' value='donate'/> \
                    <input type='hidden' id = 'targets' name='targets' value=" + '#' + result[result.length - 1][1] + " /> \
                    <input type='hidden' id = 'sum' name='sum' data-type='number' value =" + result[result.length - 1][0].toFixed(1) + " /> \
                    <input type='hidden' name='successURL' value=" + 'http://localhost:8080/send/ticket?' + urlParametrs + "/> \
                    <input type='hidden' name='need-fio' value='false'/> \
                    <input type='hidden' name='need-email' value='false'/> \
                    <input type='hidden' name='need-phone' value='false'/> \
                    <input type='hidden' name='need-address' value='false'/> \
                    <input type='hidden' name='paymentType' value='AC'/> \
                    <input class='btn btn-primary center-position' style='width: 200px;' type='submit' name='paymentType' value='Перейти к оплате'/> \
                    </form>");
                            j++;
                        }
                    }
                }
                $(".count").each(function (idx, c) {
                    $(this).val(0);
                });
                $('#saleModal').modal('show');

            }
        });
    }
}

function editEvent(eventId) {

    if ($("#editButton" + eventId).text() === "Редактировать") {
        $("#eventName" + eventId).removeAttr('disabled');
        $("#eventDate" + eventId).removeAttr('disabled');
        $("#eventAgeLimit" + eventId).removeAttr('disabled');
        $("#eventClubName" + eventId).removeAttr('disabled');
        $("#eventSaleForVkPost" + eventId).removeAttr('disabled');
        $("#eventCloseVkRepost" + eventId).removeAttr('disabled');
        $("#eventVkPostUrl" + eventId).removeAttr('disabled');
        $("#eventAddress" + eventId).removeAttr('disabled');
        $("#eventPocket" + eventId).removeAttr('disabled');
        $("#editButton" + eventId).html('Сохранить');
    } else if ($("#editButton" + eventId).text() === "Сохранить") {

        var formData = {
            eventId: eventId,
            eventName: $("#eventName" + eventId).val(),
            eventAddress: $("#eventAddress" + eventId).val(),
            eventDate: $("#eventDate" + eventId).val(),
            eventAgeLimit: $("#eventAgeLimit" + eventId).val(),
            eventClubName: $("#eventClubName" + eventId).val(),
            eventSaleForVkPost: $("#eventSaleForVkPost" + eventId).val(),
            eventCloseVkRepost: $("#eventCloseVkRepost" + eventId).val(),
            eventVkPostUrl: $("#eventVkPostUrl" + eventId).val(),
            eventPocket: $("#eventPocket" + eventId).val()
        };
        var url = "/edit/event"
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

function removeEvent(eventId) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/remove/event",
        data: {eventId: eventId},
        success: function () {
            window.open("/lk", '_self')
        }
    });
}


function addEvent() {

    var formData = {
        name: $("#name").val(),
        clubName: $("#clubName").val(),
        address: $("#address").val(),
        pocket: $("#pocket").val(),
        date: $("#date").val(),
        vkPostUrl: $("#postVk").val(),
        closeVkRepost: $("#timePostVk").val(),
        saleForVkPost: $("#salePostVk").val(),
        eventAgeLimit: $("#age-limit").val()
    };
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/add/event",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function () {
            window.open("/lk", '_self');
            console.log("Success");
        },
        error: function (e) {
            alert("Неверный формат данных!");
            console.log("ERROR: ", e);
        }
    });
}

$(document).ready(function () {
    let startDate = moment(new Date()).utcOffset(180); //устанавливаем минимальную дату и время по МСК (UTC + 3 часа )
    $('#date').daterangepicker({
        "singleDatePicker": true, //отключаем выбор диапазона дат (range)
        "showWeekNumbers": false,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 10,
        "locale": {
            "format": "DD.MM.YYYY HH:mm МСК",
            "separator": " - ",
            "applyLabel": "Apply",
            "cancelLabel": "Cancel",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": ["Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
            "monthNames": ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            "firstDay": 0
        },
        "linkedCalendars": false,
        "startDate": startDate,
    }, function (start, end, label) {
        console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' +
            end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
    });


    $('#timePostVk').daterangepicker({
        "singleDatePicker": true, //отключаем выбор диапазона дат (range)
        "showWeekNumbers": false,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 10,
        "locale": {
            "format": "DD.MM.YYYY HH:mm МСК",
            "separator": " - ",
            "applyLabel": "Apply",
            "cancelLabel": "Cancel",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": ["Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
            "monthNames": ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            "firstDay": 0
        },
        "linkedCalendars": false,
        "startDate": startDate,
    }, function (start, end, label) {
        console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' +
            end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
    });


    $('#promoStartDate').daterangepicker({
        "singleDatePicker": true, //отключаем выбор диапазона дат (range)
        "showWeekNumbers": false,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 10,
        "locale": {
            "format": "DD.MM.YYYY HH:mm МСК",
            "separator": " - ",
            "applyLabel": "Apply",
            "cancelLabel": "Cancel",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": ["Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
            "monthNames": ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            "firstDay": 0
        },
        "linkedCalendars": false,
        "startDate": startDate,
    }, function (start, end, label) {
        console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' +
            end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
    });
    $('#promoEndDate').daterangepicker({
        "singleDatePicker": true, //отключаем выбор диапазона дат (range)
        "showWeekNumbers": false,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 10,
        "locale": {
            "format": "DD.MM.YYYY HH:mm МСК",
            "separator": " - ",
            "applyLabel": "Apply",
            "cancelLabel": "Cancel",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": ["Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
            "monthNames": ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            "firstDay": 0
        },
        "linkedCalendars": false,
        "startDate": startDate,
    }, function (start, end, label) {
        console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' +
            end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
    });
    $('#startDate').daterangepicker({
        "singleDatePicker": true, //отключаем выбор диапазона дат (range)
        "showWeekNumbers": false,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 10,
        "locale": {
            "format": "DD.MM.YYYY HH:mm МСК",
            "separator": " - ",
            "applyLabel": "Apply",
            "cancelLabel": "Cancel",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": ["Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
            "monthNames": ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
            "firstDay": 0
        },
        "linkedCalendars": false,
        "startDate": startDate,
    }, function (start, end, label) {
        console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' +
            end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
    });
});


function addPromo(ticketId) {
    let promocodes = $('#promocodes').val();
    let discountValue = $('#discountValue').val();
    let promoStartDate = $('#promoStartDate').val();
    let promoEndDate = $('#promoEndDate').val();

    let wrap = {
        idTicket: ticketId,
        promocodes: promocodes,
        discountValue: discountValue,
        promoStartDate: promoStartDate,
        promoEndDate: promoEndDate
    };

    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=UTF-8',
        url: '/add-promo',
        data: JSON.stringify(wrap),
        success: function () {
            location.reload();
        }
    });
}

$(document).ready(function () {
    $('#promoTable').delegate('.promo-modal', 'click', function () {
        $('#editPromoModal').modal('show');
        var idPromo = $(this).closest('tr').children('td:first').text();
        $.ajax({
            type: "GET",
            contentType: 'application/json; charset=UTF-8',
            url: '/get-promo',
            data: {
                promoId: idPromo
            },
            success: function (promo) {
                $("#edit-promo-sale").val(promo.sale);
                $("#edit-promo-date-start").val(promo.dateStart);
                $("#edit-promo-date-end").val(promo.dateEnd);
                $("#edit-promo-date-count").val(promo.count);
                $("#edit-promo").each(function () {
                    $(this).val(promo.promocode
                        .join("\n"));
                });
            }
        });
    })
});


function editPromo(ticketId) {
    if ($("#editPromo").text() === "Редактировать") {
        $("#edit-promo-sale").removeAttr('disabled');
        $("#edit-promo-date-start").removeAttr('disabled');
        $("#edit-promo-date-end").removeAttr('disabled');
        $("#edit-promo-date-count").removeAttr('disabled');
        $("#edit-promo").removeAttr('disabled');
        $("#editPromo").html('Сохранить');
    } else if ($("#editPromo").text() === "Сохранить") {
        let discountValue = $("#edit-promo-sale").val();
        let promoStartDate = $("#edit-promo-date-start").val();
        let promoEndDate = $("#edit-promo-date-end").val();
        let count = $("#edit-promo-date-count").val();
        let promocodes = $("#edit-promo").val();
        let wrap = {
            idTicket: ticketId,
            promocodes: promocodes,
            discountValue: discountValue,
            promoStartDate: promoStartDate,
            promoEndDate: promoEndDate,
            count: count
        };
        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=UTF-8',
            url: '/edit-promo',
            data: JSON.stringify(wrap),
            success: function () {
                location.reload();
            }
        });
    }
}
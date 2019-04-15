
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

function putTicket(eventId) {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "/put/new/ticket",
        data : {eventId : eventId},
        success: function () {
            location.reload()
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
        data: {ticketId : ticketId} ,
        success: function () {
            location.reload()
        }
    });
}


function buyTicket(ticketId, ticketPrice, eventId) {
    if ($("#first-name-for-buy-ticket").val() == "" || $("#last-name-for-buy-ticket").val() == "" ||
        $('#add-user-email').val() == "" || $('#add-user-phone-number').val() == "") {
        alert('Заполните все поля');
    } else {
        var url = "/event/app/" + eventId + "/purchase/tickets";
        var date = new Date();
        let wrap = {
            firstName: $("#first-name-for-buy-ticket").val(),
            lastName: $("#last-name-for-buy-ticket").val(),
            phoneNumber: $('#add-user-phone-number').val(),
            email: $('#add-user-email').val() ,
            ticketId: ticketId,
            ticketPrice: ticketPrice,
            countTicket: $("#ticketCount").val(),
            date : date
        };

        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(wrap),
            success: function (result) {
                ("#yandexForm").submit()
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
        data: {eventId : eventId} ,
        // success: function () {
        //     location.reload()
        // }
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
            window.open("/lk",  '_self');
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
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
        currentCount = +parseInt(document.getElementById(cId).value);
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
    } else {
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
            if (sum == 0) {
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
    $('#saleButton').click(function () {
        var obj = {};
        var count = 0;
        var k = 0;
        $(".firstPrice").text(localStorage.getItem("sum"))
        $(".qet").each(function (i, index) {
            $(".count").each(function (idx, c) {
                if ($(this).val() > 0 && (k == idx)) {
                    count = $(this).val();
                    $(".firstModal").append("<div class='row'> \
                            <div class='col left-position'> \
                             <p style='color: #fff;'>" + $(index)[0].innerHTML + "</p>  \
                            </div> \
                            <div class='col right-position'> \
                            <p id ='ticketPrice2' style='color: #fff;'>" + count + "</p> \
                            </div> \
                            </div>")
                    obj[$(index)[0].innerHTML] = count;
                    return false;
                }
            });
            k++;
            return;
        });
        $('#ticketModal').modal('show');
        var date = new Date();
        offset = date.getTimezoneOffset();
        date.setMinutes(date.getMinutes() + offset);

    })
});

function openLKPage() {
    var url = '/lk';
    window.open(url, '_self');
}

function clearModal() {
    $(".firstModal").empty();
}

function clearModal2() {
    $(".yandex-from").empty();
    $(".fillBody").empty();
    location.reload();
}
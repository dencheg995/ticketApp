var sum = 0;
var currentCount = 0;
var prevId = 0;
localStorage.setItem("currentCount", currentCount);
localStorage.setItem("sum", sum);
function addition(ticketId, ticketPrice, ticketCountMax) {
    var cId = "currentCount" + ticketId;
    $("#down" + ticketId).attr("disabled", false);
    $("#down" + ticketId).attr("field", "off");
    if (ticketCountMax != parseInt(document.getElementById(cId).value)) {
        currentCount = parseInt(localStorage.getItem("currentCount"));
        currentCount =+ parseInt(document.getElementById(cId).value);
        localStorage.setItem("currentCount", currentCount);
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

function subtraction(ticketId, ticketPrice, ticketCountMax) {
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
                if (parseInt(localStorage.getItem("prevId")) != parseInt(ticketId))
                    localStorage.setItem("prevId", ticketId);
                $("#sumResult").val(sum);
            }
            $("#down" + ticketId).attr("disabled", true);
            $("#down" + ticketId).attr("field", "on")

    }
    }


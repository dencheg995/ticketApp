var sum = 0;
var currentCount = 0;
var prevCount = 0;
localStorage.setItem("currentCount", currentCount);
localStorage.setItem("prevCount", prevCount);
localStorage.setItem("sum", sum);
function addition(ticketId, ticketPrice) {
    var cId = "currentCount" + ticketId;
    // if (parseInt(localStorage.getItem("prevCount")) != parseInt(document.getElementById(cId).value)) {
        currentCount = parseInt(localStorage.getItem("currentCount"));
        currentCount =+ parseInt(document.getElementById(cId).value);
        localStorage.setItem("currentCount", currentCount);
        // localStorage.setItem("prevCount", currentCount);
        sum = localStorage.getItem("sum");
        sum =+ currentCount * ticketPrice;
        localStorage.setItem("sum", sum);
        // document.getElementById("sumResult").innerHTML = sum + " руб.";
        $("#sumResult").val(sum);
    // }

    // var buttons = document.getElementsByTagName("button");
    // var buttonsCount = buttons.length;
    // for (var i = 0; i <= buttonsCount; i += 1) {
    //     if (this.id == "down") {}
    //     buttons[i].onclick = function(e) {
    //         alert(this.id);
    //     };
    // }​
}

function subtraction(ticketId, ticketPrice) {
    var cId = "currentCount" + ticketId;
    currentCount = parseInt(localStorage.getItem("currentCount"));
    currentCount =+ parseInt(document.getElementById(cId).value);
    localStorage.setItem("currentCount", currentCount);
    // localStorage.setItem("prevCount", currentCount);
    sum = localStorage.getItem("sum");
    sum =+ currentCount * ticketPrice;
    localStorage.setItem("sum", sum);
    // document.getElementById("sumResult").innerHTML = sum + " руб.";
    $("#sumResult").val(sum);
    //document.getElementById("sumResult").innerHTML = sum + " руб.";
   // $("#sumResult").val(sum);
}

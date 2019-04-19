function getSum(ticketId, ticketPrice) {
    var cId = "currentCount" + ticketId;
    let currentCount = parseInt(document.getElementById(cId).value);
    var sum =+ currentCount*ticketPrice;
    document.getElementById("sumResult").innerHTML = sum + " руб.";
}
function subtraction(ticketId, ticketPrice) {
    var cId = "currentCount" + ticketId;
    let currentCount = parseInt(document.getElementById(cId).value);
    var sum =+ currentCount*ticketPrice;
    document.getElementById("sumResult").innerHTML = sum + " руб.";
}
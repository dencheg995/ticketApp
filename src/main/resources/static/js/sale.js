function getSum(ticketId, ticketPrice) {
    var count = $('span[value]');
    var actionType = $('button[name]').char;
    if (actionType === "up"){
        count++;
    }else if (actionType === "down" && count > 0){
        count--;
    }
    var up = document.getElementById("up");
    var logF = document.getElementById('values');
    up.addEventListener("click", updateValueF);

    function updateValueF() {
        logF.textContent = count;
    }

    var logS = document.getElementById('sum');
    function updateValueS() {
        logS.textContent = count;
    }
    up.addEventListener("click", updateValueS);
};
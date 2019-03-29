var event;
var colors = ["rgb(160, 198, 255)", "rgb(73, 123, 255)", "rgb(92, 245, 61)", "rgb(67, 185, 3)", "rgb(255, 159, 46)", "rgb(255, 92, 109)", "rgb(255, 0, 239)"];
var inpay = 0;
var pz;
var rm;
var overlay;
var cart;
function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
function setCookie(name, value) {
    options = {};

    var d = new Date();
    d.setTime(d.getTime() + 1000*60*60*24*365*15);
    options.expires = d;

    options.path = "/";
    options.domain = "soldout.media";

    value = encodeURIComponent(value);

    var updatedCookie = name + "=" + value;

    for (var propName in options) {
        updatedCookie += "; " + propName;
        var propValue = options[propName];
        if (propValue !== true) {
            updatedCookie += "=" + propValue;
        }
    }

    document.cookie = updatedCookie;
}
function loadEvent(pid, uid){
    event = $.ajax({
        type: "POST",
        url: "func_w.php?r=loadEvent",
        data: { pid:pid, uid:uid, hash: getCookie("u_hash") },
        async: false
    }).responseText;
    try{ event = JSON.parse(event); }catch(r){ location.reload(); }
    console.log(event);
    if(event.status == "success"){
        event.saleByHall.booked = JSON.parse(event.saleByHall.booked);
        event.saleByHall.sold = JSON.parse(event.saleByHall.sold);
        event.saleByHall.prices = JSON.parse(event.saleByHall.prices);
        event.saleByHall.hallScheme = JSON.parse(event.saleByHall.hallScheme);
        init();
    }else if(event.status == "error"){
        if(event.msg == "stopSale"){
            $( ".content" ).prepend("<p style='position: fixed;top: 0;bottom: 0;left: 0;right: 230px;background: #f7f7f7;z-index: 3;text-align: center;padding-top: 210px;'><img src='img/end.gif' style='width: 17%;'><span style='display: block;margin-top: 20px;'>Продажа билетов завершена</span></p>");
        }else if(event.msg == "timeout"){
            $( ".content" ).prepend("<p style='position: fixed;top: 0;bottom: 0;left: 0;right: 230px;background: #f7f7f7;z-index: 3;text-align: center;padding-top: 210px;'><img src='img/end.gif' style='width: 17%;'><span style='display: block;margin-top: 20px;'>Продажа билетов завершена</span></p>");
        }else{
            $( ".content" ).prepend("<p style='position: fixed;top: 0;bottom: 0;left: 0;right: 230px;background: #f7f7f7;z-index: 3;text-align: center;padding-top: 240px;'><img src='img/nf.gif' style='width: 17%;'><span style='display: block;margin-top: 20px;'>Мероприятие не найдено</span></p>");
        }
        loader();
    }
}
function uniqueVal(value, index, self) { return self.indexOf(value) === index; }
function nf(n){ return new Intl.NumberFormat('de-DE').format(n); }
function map(){
    this.makeSVG = function(tag, attrs){ // Отрисовка
        var el= document.createElementNS('http://www.w3.org/2000/svg', tag);
        for (var k in attrs)
            el.setAttribute(k, attrs[k]);
        document.getElementById('s').appendChild(el);
    }

    this.set_price_colors = function(){ // Установка цен и цветов цен
        var out = [];
        var many_prices = [];
        for(i=0;i<event.saleByHall.prices.length;i++){
            for(b=0;b<event.saleByHall.prices[i].length;b++){
                if(many_prices.indexOf(event.saleByHall.prices[i][b]["p"]) == "-1"){
                    many_prices.push(event.saleByHall.prices[i][b]["p"]);
                }
            }
        }
        many_prices.sort(function(a, b) {
            return a - b;
        });
        var gout = "";
        for(i=0;i<many_prices.length;i++){
            gout += "<div class=\"pr\" onmouseenter=\"hilight('"+colors[i]+"', 1)\"><div class=\"circl\" style='background: "+colors[i]+";'></div><p>"+many_prices[i]+"</p></div>";
            out[many_prices[i]] = colors[i];
        }
        $(".prices").html(gout);
        return out;
    }
    this.get_sector_data = function(l){
        var sector = event.saleByHall.hallScheme.sectors[l];
        var sector_prices = event.saleByHall.prices[l];

        var f=false,c=false;
        var p=0;
        if(sector_prices[0]['f']){
            f=true;
            p=sector_prices[0]['p'];
            for(e=0;e<sector.seats.length;e++) sector.seats[e]["price"] = sector_prices[0]['p'];// Добавление цен в массив
        }else{
            if(sector_prices.length == 1){
                p=nf(sector_prices[0]['p']);
                if(sector_prices[0]['c']) c=sector_prices[0]['c'];
                for(e=0;e<sector.seats.length;e++) sector.seats[e]["price"] = sector_prices[0]['p'];// Добавление цен в массив
            }else{
                var many_prices=[];
                for(i=0;i<sector_prices.length;i++){
                    // Добавление цен в массив
                    for(e=0;e<sector.seats.length;e++){
                        if(sector_prices[i]["r"] == sector.seats[e]["row"]){
                            sector.seats[e]["price"] = sector_prices[i]["p"];
                        }
                    }
                    //
                    many_prices.push(sector_prices[i]['p']);
                }
                many_prices = many_prices.filter(uniqueVal).sort(function(a, b) {
                    return a - b;
                });
                p=nf(many_prices[0])+" ₽ - "+nf(many_prices[many_prices.length-1]);
            }
        }
        var fp = c;
        if(!fp){
            fp = sector.seats.length;
        }
        for(i=0;i<event.saleByHall.sold.length;i++){
            if(event.saleByHall.sold[i].split("s")[1] == l){
                fp--;
            }else if(event.saleByHall.sold[i].split("s")[1].split("r")[0] == l){
                for(b=0;b<sector.seats.length;b++){
                    if(sector.seats[b]['row'] == event.saleByHall.sold[i].split("r")[1].split("p")[0] && sector.seats[b]['place'] == event.saleByHall.sold[i].split("p")[1]){
                        fp--;
                        sector.seats[b]["sold"] = 1;
                    }
                }
            }
        }
        for(i=0;i<event.saleByHall.booked.length;i++){
            if(event.saleByHall.booked[i].split("s")[1] == l){
                fp--;
            }else if(event.saleByHall.booked[i].split("s")[1].split("r")[0] == l){
                for(b=0;b<sector.seats.length;b++){
                    if(sector.seats[b]['row'] == event.saleByHall.booked[i].split("r")[1].split("p")[0] && sector.seats[b]['place'] == event.saleByHall.booked[i].split("p")[1]){
                        fp--;
                        sector.seats[b]["booked"] = 1;
                    }
                }
            }
        }
        return {"id":l,"n":sector.name,"o":sector.outline,"f":f,"c":c,"p":p,"fp":fp,"s":sector.seats};
    }
    this.priceByColors = set_price_colors();
    var sector_data;
    for(l=0;l<event.saleByHall.hallScheme.sectors.length;l++){ // Перебираем сектора
        sector_data = get_sector_data(l); // Получаем данные сектора.
        var sclass="sector_stroke";
        if(sector_data.f){ sclass += " full-sale"; var fullsale = 1; }
        if(sector_data.c) sclass += " admission";
        //if(sector_data.fp == 0) sclass += " sold";

        for(c=0;c<sector_data.s.length;c++){ // Отрисовка мест

            var place_data = sector_data.s[c];
            var pclass="place";
            if(sclass.split("sector_stroke ")[1]) pclass += sclass.split("sector_stroke")[1];
            if(place_data.sold) pclass += " sold";
            if(place_data.booked) pclass += " booked";

            var g = document.createElementNS("http://www.w3.org/2000/svg", "g");
            g.setAttribute("id", "s"+sector_data.id+"r"+place_data.row+"p"+place_data.place);
            g.setAttribute('shape-rendering', 'inherit');
            g.setAttribute('pointer-events', 'all');
            g.setAttribute('class', pclass);
            g.setAttribute('data-sector', sector_data.n);
            g.setAttribute('data-sector-id', sector_data.id);
            g.setAttribute('data-row', place_data.row);
            g.setAttribute('data-place', place_data.place);
            g.setAttribute('fill', this.priceByColors[place_data.price]);

            var circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
            circle.setAttribute('cx', place_data.x);
            circle.setAttribute('cy', place_data.y);
            circle.setAttribute('r', 6.4);
            g.appendChild(circle);

            if(!sector_data.c){
                var text = document.createElementNS('http://www.w3.org/2000/svg', 'text');
                text.setAttribute('x', place_data.x);
                text.setAttribute('y', place_data.y);
                text.setAttribute('dy', '1px');
                text.setAttribute('pointer-events', 'none');
                text.textContent = place_data.place;
                g.appendChild(text);
            }

            document.getElementById('s').appendChild(g);

            if(fullsale) break;
        }

        if(sclass.indexOf("sold")) sclass = sclass.split(" sold")[0];
        if(sclass.indexOf("booked")) sclass = sclass.split(" booked")[0];
        makeSVG('path', {d: sector_data.o, class: sclass, "data-name": sector_data.n, id: "s"+sector_data.id, "data-price": sector_data.p, "data-free": sector_data.fp});
    }
    this.getPlacePrice = function(sector_id, row){
        for(i=0;i<event.saleByHall.prices[sector_id].length;i++){
            if(!event.saleByHall.prices[sector_id][i]["r"]){ return event.saleByHall.prices[sector_id][i]["p"];
            }else if(event.saleByHall.prices[sector_id][i]["r"] == row) return event.saleByHall.prices[sector_id][i]["p"];
        }
    }
}
function PanZoom(id){
    document.getElementById("SeatSelect").innerHTML += "<div id=\"zoomBtns\"><div id=\"plusBtn\" class=\"active\">+</div><div id=\"minusBtn\" class=\"active\">−</div></div>";
    document.getElementById("zoomBtns").style.marginTop = (outerHeight(document.getElementById("SeatSelect"))/2 - outerHeight(document.getElementById("zoomBtns"))/2)+"px";

    if(id.indexOf("#") !== false) id = id.split("#")[1];
    var elem = document.getElementById(id);
    var hasZoom = new Event("hasZoom");
    var minZoom = new Event("minZoom");
    var maxZoom = new Event("maxZoom");

    var accuracity=3;
    var isMove=0;
    var MoveStart=0;

    function outerWidth(el) {
        var Width = el.offsetWidth;
        var style = getComputedStyle(el);
        Width += parseInt(style.marginLeft) + parseInt(style.marginRight);
        return Width;
    }
    function outerHeight(el) {
        var Height = el.offsetHeight;
        var style = getComputedStyle(el);
        Height += parseInt(style.marginLeft) + parseInt(style.marginRight);
        return Height;
    }
    this.panzoom = function(panX,panY,zoom){
        if(zoom != null){
            var event="";
            switch(zoom){
                case "+":
                    zoom = this.currentZoom*2;
                    break;
                case "-":
                    zoom = this.currentZoom/2;
                    break;
            }
            zoom = parseFloat( zoom.toFixed(4) );
            if(zoom > this.maxZoom){ zoom = parseFloat( this.maxZoom.toFixed(4) );
            }else if(zoom < this.minZoom) zoom = parseFloat( this.minZoom.toFixed(4) );

            elem.style.transition = "all 0.25s ease-in-out";

            if(zoom != this.currentZoom && zoom >= this.minZoom && zoom <= this.maxZoom || zoom == null){
                if(zoom > this.currentZoom){
                    if(zoom == this.maxZoom){
                        event = maxZoom;
                        document.getElementById('plusBtn').classList.remove("active");
                    }else{
                        event = hasZoom;
                        document.getElementById('zoomBtns').classList.add("visible");
                        document.getElementById('plusBtn').classList.add("active");
                        document.getElementById('minusBtn').classList.add("active");
                    }
                }else{
                    if(zoom == this.minZoom){
                        event = minZoom;
                        document.getElementById('zoomBtns').classList.remove("visible");
                        document.getElementById('minusBtn').classList.remove("active");

                        panX = outerWidth(document.getElementById("SeatSelect"))/2 - (outerWidth(elem)/2);
                        panY = outerHeight(document.getElementById("SeatSelect"))/2 - (outerHeight(elem)/2);
                        zoom = this.minZoom;
                    }else{
                        event = hasZoom;
                        document.getElementById('plusBtn').classList.add("active");
                    }
                }

                if(zoom > this.minZoom){
                    var blockW = (outerWidth(elem) + 60) * zoom;
                    var blockH = outerHeight(elem) * zoom;
                    var screeW = outerWidth(document.getElementById("SeatSelect"));
                    var screeH = outerHeight(document.getElementById("SeatSelect"));
                    var cW = outerWidth(document.getElementById("SeatSelect"))/2 - (outerWidth(elem)/2);
                    var cH = outerHeight(document.getElementById("SeatSelect"))/2 - (outerHeight(elem)/2);

                    if(screeW < blockW){
                        var dW = screeW/2 - blockW/zoom*(zoom-1)/2;
                        var maxPanW = Math.round(screeW/2 - dW);
                        var minPanW = Math.round( cW - (maxPanW - cW) );
                        if(panX > maxPanW) panX = maxPanW;
                        if(panX < minPanW) panX = minPanW;
                    }else{
                        panX = this.cW;
                    }

                    var dH = screeH/2 - blockH/zoom*(zoom-1)/2;
                    var maxPanH = Math.round(screeH/2 - dH);
                    var minPanH = Math.round( cH - (maxPanH - cH) );

                    if(panY > maxPanH) panY = maxPanH;
                    if(panY < minPanH) panY = minPanH;
                }
                elem.style.transform = "matrix("+zoom+",0,0,"+zoom+","+panX+","+panY+")";
                if(event != "") elem.dispatchEvent(event);

                this.oldZoom = this.currentZoom;
                this.currentZoom = zoom;
            }
        }else{
            if(parseFloat(this.currentZoom) > parseFloat(this.minZoom)){
                zoom = this.currentZoom;
                var blockW = (outerWidth(elem) + 60) * zoom;
                var blockH = outerHeight(elem) * zoom;
                var screeW = outerWidth(document.getElementById("SeatSelect"));
                var screeH = outerHeight(document.getElementById("SeatSelect"));
                var cW = outerWidth(document.getElementById("SeatSelect"))/2 - (outerWidth(elem)/2);
                var cH = outerHeight(document.getElementById("SeatSelect"))/2 - (outerHeight(elem)/2);

                if(screeW < blockW){
                    var dW = screeW/2 - blockW/zoom*(zoom-1)/2;
                    var maxPanW = Math.round(screeW/2 - dW);
                    var minPanW = Math.round( cW - (maxPanW - cW) );
                    if(panX > maxPanW) panX = maxPanW;
                    if(panX < minPanW) panX = minPanW;
                }else{
                    panX = this.cW;
                }

                var dH = screeH/2 - blockH/zoom*(zoom-1)/2;
                var maxPanH = Math.round(screeH/2 - dH);
                var minPanH = Math.round( cH - (maxPanH - cH) );

                if(panY > maxPanH) panY = maxPanH;
                if(panY < minPanH) panY = minPanH;

                elem.style.transition = "none";
                elem.style.transform = "matrix("+zoom+",0,0,"+zoom+","+panX+","+panY+")";
            }

        }
    };
    this.pan = function(panX,panY){
        this.panzoom(panX,panY,null);
    };
    this.zoom = function(zoom){
        var current = this.getCurrent();
        switch(zoom){
            case "+":
                zoom = this.currentZoom*2;
                break;
            case "-":
                zoom = this.currentZoom/2;
                break;
        }
        if(zoom > this.currentZoom){
            var panX = current["panX"] - (this.cW - current["panX"])/this.currentZoom;
            var panY = current["panY"] - (this.cH - current["panY"])/this.currentZoom;
        }else{
            var panX = current["panX"] + (this.cW - current["panX"])/this.currentZoom;
            var panY = current["panY"] + (this.cH - current["panY"])/this.currentZoom;
        }
        this.panzoom(panX,panY,zoom);
    };
    this.panzoomTo = function(panX, panY){
        var current = this.getCurrent();

        var сWc = outerWidth(document.getElementById("SeatSelect"))/2; // центр (точка масштабирования)
        var сHc = outerHeight(document.getElementById("SeatSelect"))/2; // центр (точка масштабирования)
        panX = panX - сWc;
        panY = panY - сHc;

        var oldX = parseInt(current[4]);
        var oldY = parseInt(current[5]);

        var newX = current["panX"] - panX;
        var newY = current["panY"] - panY;

        var сW2 = outerWidth(document.getElementById("SeatSelect"))/2 - (outerWidth(elem)/2); // центр (точка масштабирования)
        var сH2 = outerHeight(document.getElementById("SeatSelect"))/2 - (outerHeight(elem)/2); // центр (точка масштабирования)

        newX = newX - (сW2 - newX)/this.currentZoom;
        newY = newY - (сH2 - newY)/this.currentZoom;
        this.panzoom(newX,newY,"+");
    };
    this.getCurrent = function(){
        var current = elem.style.transform.split("(")[1].split(")")[0].split(",");
        return {"zoom":parseFloat(current[0]), "panX":parseInt(current[4]), "panY":parseInt(current[5])};
    };
    this.setCenter = function(){ this.panzoom(this.cW, this.cH, this.minZoom, 1); };
    this.resize = function(){
        console.log("resize");
        var rsz = 0;
        if(this.currentZoom == this.minZoom) rsz = 1;
        this.calcMinMax();
        if(rsz == 1){
            this.setCenter();
            this.currentZoom = parseFloat(this.minZoom.toFixed(4));
        }
    };
    this.calcMinMax = function(){
        this.cW = outerWidth(document.getElementById("SeatSelect"))/2 - (outerWidth(elem)/2);
        this.cH = outerHeight(document.getElementById("SeatSelect"))/2 - (outerWidth(elem)/2);

        console.log("Height"+outerHeight(elem)+" "+outerHeight(document.getElementById("SeatSelect")));

        if(outerWidth(document.getElementById("SeatSelect")) < outerHeight(document.getElementById("SeatSelect"))){
            this.minZoom = parseFloat((outerWidth(document.getElementById("SeatSelect")) / (outerWidth(elem))).toFixed(4));
        }else{
            // ширина больше высоты
            this.minZoom = parseFloat((outerHeight(document.getElementById("SeatSelect")) / (outerHeight(elem))).toFixed(4));
        }
        this.maxZoom = parseFloat((this.minZoom + 4).toFixed(4));
        if(this.minZoom > 2){
            var t = this;
            setTimeout(function(){
                t.resize();
            }, 100);
        }
        console.log(this.minZoom+" "+this.minZoom);
    };
    this.calcMinMax();

    var t = this;
    $( window ).resize(function() {
        t.resize();
    });


    ['mousedown','touchstart'].forEach( function(e) {
        elem.addEventListener(e, touchstart, false);
    });
    ['mousemove','touchmove'].forEach( function(e) {
        elem.addEventListener(e, touchmove, false);
    });
    ['mouseup','touchend'].forEach( function(e) {
        elem.addEventListener(e, touchend, false);
    });
    function touchstart(e){
        t.isMove = 0;
        if(e.type == "mousedown"){
            sx = e.clientX;
            sy = e.clientY;
        }else{
            sx = e.touches[0].pageX;
            sy = e.touches[0].pageY;
        }
        old_pos = elem.style.transform.split("(")[1].split(")")[0].split(",");
        oldX = parseInt(old_pos[4]);
        oldY = parseInt(old_pos[5])
        t.MoveStart=1;
        if(pz.currentZoom > pz.minZoom+1){
            old_pos = elem.style.transition = "none";
        }
    }
    function touchmove(e){
        if(t.MoveStart==1 && t.currentZoom != t.minZoom){
            if(e.type == "mousemove"){
                if(e.clientX - sx > t.accuracity || e.clientX - sx < -t.accuracity || e.clientY - sy > t.accuracity || e.clientY - sy < -t.accuracity) t.isMove = 1;
                t.pan(e.clientX - sx + oldX, e.clientY - sy + oldY);
            }else{
                if(e.touches[0].pageX - sx > t.accuracity || e.touches[0].pageX - sx < -t.accuracity || e.touches[0].pageY - sy > t.accuracity || e.touches[0].pageY - sy < -t.accuracity) t.isMove = 1;
                t.pan(e.touches[0].pageX - sx + oldX, e.touches[0].pageY - sy + oldY);
            }
        }
    }
    function touchend(e){
        t.MoveStart=0;
        t.isMove=0;
    }

    this.setCenter();
    elem.style.opacity = 1;

    document.getElementById("plusBtn").addEventListener("click", function(){ t.zoom("+"); }, false);
    document.getElementById("minusBtn").addEventListener("click", function(){ t.zoom("-"); }, false);

    var mousewheelevt=(/Firefox/i.test(navigator.userAgent))? "DOMMouseScroll" : "mousewheel";
    function scrollEE (e) {
        e.preventDefault();
        var current = t.getCurrent();
        var newPanY = current["panY"] - e.deltaY;
        var newPanX = current["panX"] - e.deltaX;
        t.pan(newPanX,newPanY);
    }
    elem.addEventListener(mousewheelevt, scrollEE, false);
}
class ovl{
    constructor() {
        this.type = "";
        this.active = false;
        this.el = "";
    }
    show(type, el){
        if(!this.active){
            this.type = type;
            this.el = el;
            this.active = true;
            $(".ovl").css({"z-index":"2"});
            setTimeout(function(){ $(".ovl").css({"opacity":"1"}); }, 10);
        }
        var th = this;
        $(".ovl").on("click tap touchstart", function(){
            th.hide();
        });
    }
    hide(f = false){
        if(this.active){
            this.active = false;
            $(".ovl").css({"opacity":"0"});
            setTimeout(function(){ $(".ovl").css({"z-index":"-99"}) },250);
            if(this.type == 1){
                if(f === false) cart.sidebarPage('cart');
            }else{
                this.el.hide();
            }
        }
    }
}
window.dataLayer = [];
function gtag(){
    console.log("GTAG");
    dataLayer.push(arguments);
}
setInterval(function(){
    console.log(dataLayer);
}, 1000);
class remarketing{
    constructor() {
        this.init();
    }
    add(id, name, price, q){
        try{
            yandexLayer.push({
                "ecommerce": {
                    "add": {
                        "products": [
                            {
                                "id": id,
                                "name": name,
                                "price": price,
                                "quantity": q
                            }
                        ]
                    }
                }
            });
            if(typeof event.remarketing.go != "undefined"){
                gtag('event', 'add_to_cart', {
                    "items": [{
                        "id": id,
                        "name": name,
                        "price": price,
                        "quantity": q
                    }]
                });
            }
        }catch(e){ }
    }
    remove(id, q){
        try{
            yandexLayer.push({
                "ecommerce": {
                    "remove": {
                        "products": [
                            {
                                "id": id,
                                "quantity": q
                            }
                        ]
                    }
                }
            });
            if(typeof event.remarketing.go != "undefined"){
                gtag('event', 'remove_from_cart', {
                    "items": [{
                        "id": id,
                        "quantity": q
                    }]
                });
            }
        }catch(e){ }
    }
    pay(){
        try{
            var products = [];
            for (var i=0; i < cart.items.length; i++){
                var item = cart.items[i];
                if(typeof item.r != "undefined") {
                    var price = getPlacePrice(item.s, item.r);
                    var id = "s"+item.s+"r"+item.sr+"p"+item.p;
                }else{
                    var price = getPlacePrice(item.s, 1);
                    var id = "s"+item.s;
                }
                var sector = get_sector_data(item.s);
                products.push({id:id, "name": sector.n, price: price});
            }
            var af = {};
            af.id = event.user.hash;
            af.revenue = cart.mainPrice;
            if(typeof event.user.promocode != "undefined") af.coupon = event.user.promocode.code;
            yandexLayer.push({
                "ecommerce": {
                    "purchase": {
                        "actionField": af,
                        "products": products
                    }
                }
            });
            yandexLayer = [];
            console.log(cart.mainPrice);
            if(typeof event.remarketing.fb != "undefined") fbq('track', 'Purchase', '{currency: "RUB", value: "'+cart.mainPrice+'.00"}');
            if(typeof event.remarketing.go != "undefined"){
                gtag('event', 'purchase', {
                    "transaction_id": event.user.hash,
                    "value": cart.mainPrice,
                    "currency": "RUB",
                    "items": products
                });
            }
            this.goal('Payd');
        }catch(e){ }
    }
    goal(goal){
        try{
            yaCounter.reachGoal(goal);
            if(typeof event.remarketing.ya != "undefined") yaCounterUser.reachGoal(goal);
            if(goal != "OpenApp" && goal != "OpenVkApp"){
                //VK.Retargeting.Event(goal);
                //if(goal == "AddToCart") fbq('track', 'AddPaymentInfo');
            }
            if(goal == "ToPay"){
                if(typeof event.remarketing.go != "undefined"){
                    var products = [];
                    for (var i=0; i < cart.items.length; i++){
                        var item = cart.items[i];
                        if(typeof item.r != "undefined") {
                            var price = getPlacePrice(item.s, item.r);
                            var id = "s"+item.s+"r"+item.sr+"p"+item.p;
                        }else{
                            var price = getPlacePrice(item.s, 1);
                            var id = "s"+item.s;
                        }
                        var sector = get_sector_data(item.s);
                        products.push({id:id, "name": sector.n, price: price});
                    }
                    var gtagarr;
                    if(typeof event.user.promocode != "undefined"){
                        gtagarr = {"items": products, "coupon": event.user.promocode.code};
                    }else{
                        gtagarr = {"items": products};
                    }

                    gtag('event', 'checkout_progress', gtagarr);
                }
            }
        }catch(e){ }
    }
    initGO(){
        if(typeof event.remarketing.go == "undefined") return false;
        var t = document.createElement("script");
        t.type = "text/javascript";
        t.async = !0;
        t.src = "https://www.googletagmanager.com/gtag/js?id="+event.remarketing.go;
        gtag('js', new Date());
        gtag('config', event.remarketing.go);
        gtag('config', event.remarketing.goAW);
        gtag('set', {'currency': 'RUB'});
        console.log("goAW = "+ event.remarketing.goAW);

        document.head.appendChild(t);
        return true;
    }
    initVK(){
        if(typeof event.remarketing.vk == "undefined") return false;
        var t = document.createElement("script");
        t.type = "text/javascript";
        t.async = !0;
        t.src = "https://vk.com/js/api/openapi.js?159";
        t.onload = function () {
            VK.Retargeting.Init(event.remarketing.vk);
            VK.Retargeting.Hit();
        }
        document.head.appendChild(t);
        return true;
    }
    initFB(){
        if(typeof event.remarketing.fb == "undefined") return false;
        ! function (f, b, e, v, n, t, s) {
            if (f.fbq) return;
            n = f.fbq = function () {
                n.callMethod ?
                    n.callMethod.apply(n, arguments) : n.queue.push(arguments)
            };
            if (!f._fbq) f._fbq = n;
            n.push = n;
            n.loaded = !0;
            n.version = '2.0';
            n.queue = [];
            t = b.createElement(e);
            t.async = !0;
            t.src = v;
            s = b.getElementsByTagName(e)[0];
            s.parentNode.insertBefore(t, s)
        }(window, document, 'script',
            'https://connect.facebook.net/en_US/fbevents.js');
        fbq('init', event.remarketing.fb);
        fbq('track', 'PageView');
        return true;
    }
    initYA(){
        (function (d, w, c) {
            (w[c] = w[c] || []).push(function() {
                try {
                    w.yaCounter = new Ya.Metrika2({id:51165434,clickmap:true,trackLinks:true,webvisor:true,accurateTrackBounce:true,trackHash:true,ut:"noindex",ecommerce:"dataLayer"});
                    if(typeof event.remarketing != "undefined"){ if(typeof event.remarketing.ya != "undefined") w.yaCounterUser = new Ya.Metrika2({id:event.remarketing.ya,clickmap:true,trackLinks:true,webvisor:true,accurateTrackBounce:true,trackHash:true,ut:"noindex",ecommerce:"yandexLayer"}); }
                } catch(e) { }
            });

            var n = d.getElementsByTagName("script")[0],
                s = d.createElement("script"),
                img = d.createElement("img"),
                div = d.createElement("div"),
                ns = d.createElement("noscript"),
                imgUser = d.createElement("img"),
                f = function () {
                    n.parentNode.insertBefore(s, n);
                    div.appendChild(img);
                    ns.appendChild(div);
                    if(typeof event.remarketing.ya != "undefined") div.appendChild(imgUser);
                    n.parentNode.insertBefore(ns, n);
                };

            img.setAttribute("src", "https://mc.yandex.ru/watch/51165434");
            img.setAttribute("style", "position:absolute; left:-9999px;");
            if(typeof event.remarketing.ya != "undefined"){
                imgUser.setAttribute("src", "https://mc.yandex.ru/watch/"+event.remarketing.ya);
                imgUser.setAttribute("style", "position:absolute; left:-9999px;");
            }
            s.type = "text/javascript";
            s.async = true;
            s.src = "https://mc.yandex.ru/metrika/tag.js";

            w.yandexLayer = [];

            if (w.opera == "[object Opera]") {
                d.addEventListener("DOMContentLoaded", f, false);
            } else { f(); }
        })(document, window, "yandex_metrika_callbacks2");
    }
    init(){
        if( this.initVK() ) console.log("VK INIT");
        if( this.initFB() ) console.log("FB INIT");
        if( this.initGO() ) console.log("GO INIT");
        if( this.initYA() ) console.log("YA INIT");

        var t = this;
        setTimeout(function(){
            t.goal('OpenVkApp');
        }, 1000);
    }
}
class cartClass{
    constructor() {
        this.items = new Array();
        this.page = "no";
        this.timer=false;
        this.ping=false;
        this.timeend=false;
        this.modal = new Modal(this);
        this.lastRemoved = new Array();
        this.lri;
        this.mainPrice=0;
        this.init();
    }
    init(){
        this.sidebarPage("no");
        this.pinger();
    }
    arr_diff(a1, a2) {

        var a = [], diff = [];

        for (var i = 0; i < a1.length; i++) {
            a[a1[i]] = true;
        }

        for (var i = 0; i < a2.length; i++) {
            if (a[a2[i]]) {
                delete a[a2[i]];
            } else {
                a[a2[i]] = true;
            }
        }

        for (var k in a) {
            diff.push(k);
        }

        return diff;
    }
    addRemoved(tid){
        this.lastRemoved.push(tid);
        clearInterval(this.lri);
        var t = this;
        this.lri = setInterval(function(){
            t.lastRemoved = [];
            clearInterval(t.lri);
        }, 10000);
    }
    sidebarPage(p){
        //console.trace();
        switch(p){
            case "no":
                $("#sp1").removeClass("hide").removeClass("right").removeClass("left");
                $("#sp2").addClass("hide").removeClass("left").addClass("right");
                $("#sp3").addClass("hide").removeClass("left").addClass("right");
                $("#sp4").addClass("hide").removeClass("left").addClass("right");
                $("#sp5").addClass("hide").removeClass("left").addClass("right");
                $(".back").removeClass("show");
                $(".time").removeClass("show");
                if(this.modal.active){
                    this.modal.hide();
                }else if(overlay.active){
                    overlay.hide(1);
                }
                break;
            case "cart":
                $("#sp2").removeClass("hide").removeClass("right").removeClass("left");
                $("#sp1").addClass("hide").removeClass("right").addClass("left");
                $("#sp3").addClass("hide").removeClass("left").addClass("right");
                $("#sp4").addClass("hide").removeClass("left").addClass("right");
                $("#sp5").addClass("hide").removeClass("left").addClass("right");
                $(".back").removeClass("show");
                $(".time").addClass("show");
                if(!this.modal.active) overlay.hide();
                if(inpay == 1) toPay();
                break;
            case "order":
                $("#sp3").removeClass("hide").removeClass("right").removeClass("left");
                $("#sp1").addClass("hide").removeClass("right").addClass("left");
                $("#sp2").addClass("hide").removeClass("right").addClass("left");
                $("#sp4").addClass("hide").removeClass("left").addClass("right");
                $("#sp5").addClass("hide").removeClass("left").addClass("right");
                $(".back").addClass("show");
                if(this.modal.active){
                    this.modal.hide(this, 1);
                    overlay.type = 1;
                }else{
                    overlay.show(1);
                }
                break;
            case "payment":
                $("#sp4").removeClass("hide").removeClass("right").removeClass("left");
                $("#sp1").addClass("hide").addClass("left");
                $("#sp2").addClass("hide").addClass("left");
                $("#sp3").addClass("hide").addClass("left");
                $("#sp5").addClass("hide").removeClass("left").addClass("right");
                $(".back").addClass("show");
                if(!overlay.active) overlay.show(1);
                break;
            case "done":
                $("#sp5").removeClass("hide").removeClass("right").removeClass("left");
                $("#sp1").addClass("hide").removeClass("right").addClass("left");
                $("#sp2").addClass("hide").removeClass("right").addClass("left");
                $("#sp3").addClass("hide").removeClass("right").addClass("left");
                $("#sp4").addClass("hide").removeClass("right").addClass("left");
                $(".back").addClass("show").removeClass("right").addClass("left");
                break;
            case "back":
                if(this.page == "payment"){ cart.sidebarPage("order");
                }else if(this.page == "order") cart.sidebarPage("cart")
                if(inpay == 1) toPay();
                break;
        }
        if(p != "back") this.page = p;
    }
    add_minutes(dt, minutes) {
        return new Date(dt.getTime() + minutes*60000);
    }
    declOfNum(num, expressions){
        var result;
        var count = num % 100;
        if (count >= 5 && count <= 20) {
            result = expressions['2'];
        } else {
            count = count % 10;
            if (count == 1) {
                result = expressions['0'];
            } else if (count >= 2 && count <= 4) {
                result = expressions['1'];
            } else {
                result = expressions['2'];
            }
        }
        return result;
    }
    bookPlace(tid, plusminus){
        return $.ajax({
            type: "POST",
            url: "func_w.php?r=bookPlace",
            data: { tid: tid, pid: event.project.id, uid: event.user.id, hash: event.user.hash, a: plusminus },
            async: false
        }).responseText;
    }
    checkItem(s){
        for(var i=0; i<this.items.length;i++){
            if(this.items[i].s == s){
                return {c: this.items[i].c, h: i};
            }
        }
        return {h: false};
    }
    addOrderList(s,r,p){
        var sector = get_sector_data(s);

        if(r){
            var order = s+"_"+r+"_"+p;
            var price = getPlacePrice(s, r);
            var $el = $("#s"+s+"r"+r+"p"+p);
            p = r+" ряд, "+p+" место";
        }else{
            var order = s;
            var price = getPlacePrice(order, 1);
            var $el = $("#s"+s+"r1p"+sector.s[0].place);
            p = p+" "+this.declOfNum(p, ['место', 'места', 'мест']);
        }
        var color = priceByColors[price];

        $el.find("circle").css({"stroke": color});
        $el.removeClass("hover").addClass("selected");

        document.getElementById("orderlist").innerHTML += "<div id=\"order"+order+"\" class=\"item hide\"><div class=\"circle\" style=\"background:"+color+"\"></div><div class=\"data\">"+p+"<br>"+sector.n+"</div><div class=\"price\">"+nf(price)+" ₽</div></div>";
        setTimeout(function(){ document.getElementById("order"+order).classList.remove("hide"); },1);

        this.calcCart();
    }
    editItem(s,c,n){
        var price = getPlacePrice(s, 1)*c;
        $("#order"+s).find(".data").html( c+" "+this.declOfNum(c, ['место', 'места', 'мест'])+"<br>"+n );
        $("#order"+s).find(".price").html( nf(price)+" ₽");
        this.calcCart();
    }
    getTicket(id){
        for(var i=0; i<tickets.length;i++){
            if(tickets[i].id == id) return tickets[i];
        }
        return false;
    }
    calcPrice(){
        var price = 0;
        for(var i=0;i<this.items.length;i++){
            if(this.items[i].c > 0){
                price += getPlacePrice(this.items[i].s, 1)*this.items[i].c;
            }else{
                price += getPlacePrice(this.items[i].s, 1);
            }

        }
        return price;
    }
    calcCart(){
        console.log("calcCart");
        var price = this.calcPrice();
        $("#mainSum").html("Цена билетов: <span style='float:right'>"+price+" ₽</span>");
        if(typeof event.user.promocode != "undefined"){
            var promoSell=0;
            if(event.user.promocode.sellType == "percent"){
                promoSell = price*event.user.promocode.sell/100;
            }else if(event.user.promocode.sellType == "price"){
                promoSell = event.user.promocode.sell;
            }
            price -= promoSell;
            $("#promocodeSell").html("Скидка по промокоду: <span style='float:right'>"+promoSell+" ₽</span>");
        }else{
            $("#promocodeSell").html("");
        }
        if(price != 0){
            if(event.project.repostSell > 0){
                $("#repostSell").html("");
                console.log(event.project.repostSell+" "+event.user.repost);
                if(event.user.repost == "active"){
                    $("#repost").html("<div class=\"pd\"><img src=\"img/repost.png\">Скидка за репост "+event.project.repostSell+" ₽</div>");
                    price = price - event.project.repostSell;
                    $("#repostSell").html("Скидка за репост: <span style='float:right'>"+event.project.repostSell+" ₽</span>");
                }
            }else{
                $("#repostSell").html("");
            }
        }else{
            $("#repostSell").html("");
        }

        $('.tooltip').tooltipster({
            delay: 50,
            interactive: true
        });
        if(price < 0) price = 0;
        document.getElementById("fullprice").innerHTML = price + " ₽";
        if(price != 0){
            this.mainPrice = parseFloat((Math.round((price * 100 / 94)*100+1)/100).toFixed(2));
        }else{
            this.mainPrice = 0;
        }
        document.getElementById("sbor").innerHTML = "Cервисный сбор: <span style='float:right'>"+(this.mainPrice - price).toFixed(2)+" ₽</span>";
        document.getElementById("fullprice2").innerHTML = this.mainPrice + " ₽";
    }
    addTicket(id, type){
        var count = 1;
        var item = this.checkItem(id);
        if(type == 1){
            if(item.c) count = item.c + 1;
        }else{
            if(item.c) count = item.c - 1;
        }
        var ticket = this.getTicket(id);
        var p = count+" "+this.declOfNum(count, ['билет', 'билета', 'билетов']);

        if(type == 1){
            if(item.c){
                if(count < 10){
                    if(this.bookPlace(id, 1) == 1){
                        this.items[item.h].c = count;
                        $("#count"+id).html(count);
                        document.getElementById("order"+id).innerHTML = "<div class=\"data\">"+p+"<br>"+ticket.name+"</div><div class=\"price\">"+(ticket.price * count)+" ₽</div>";
                    }else{
                        $("#count"+id).tooltipster().tooltipster('content', 'Эти билеты закончились').tooltipster('open');
                        setTimeout(function(){
                            $("#count"+id).tooltipster('destroy');
                        }, 1500);
                    }
                }
            }else{
                if(this.bookPlace(id, 1) == 1){
                    this.items.push({s: id, c: 1, a: true});
                    $("#count"+id).html(count);
                    document.getElementById("orderlist").innerHTML += "<div id=\"order"+id+"\" class=\"item hide\"><div class=\"data\">"+p+"<br>"+ticket.name+"</div><div class=\"price\">"+ticket.price+" ₽</div></div>";
                    setTimeout(function(){ document.getElementById("order"+id).classList.remove("hide"); },1);
                    if(this.items.length == 1){
                        cart.sidebarPage("cart");
                        this.timerstart();
                        if(event.user.repost == 0) this.checkRepost(1);
                    }
                }else{
                    $("#count"+id).tooltipster().tooltipster('content', 'Эти билеты закончились').tooltipster('open');
                    setTimeout(function(){
                        $("#count"+id).tooltipster('destroy');
                    }, 1500);
                }
            }
        }else{
            if(count > 0){
                if(this.bookPlace(id, 0) == 1){
                    this.items[item.h].c = count;
                    $("#count"+id).html(count);
                    document.getElementById("order"+id).innerHTML = "<div class=\"data\">"+p+"<br>"+ticket.name+"</div><div class=\"price\">"+(ticket.price * count)+" ₽</div>";
                }
            }else{
                if(count == 0){
                    if(this.bookPlace(id, 0) == 1){
                        this.checkRepost(1);
                        this.repostInt = false;
                        this.items.splice(item.h, 1);
                        $("#count"+id).html(0);
                        var order = document.getElementById("order"+id);
                        order.classList.add("hide");
                        setTimeout(function(){ order.parentNode.removeChild(order); },200);
                    }
                }
            }
        }

        this.sortItems();

        if(this.items.length > 8){
            document.getElementsByClassName("zakaz")[0].classList.add("bottom−shadow");
        }else if(this.items.length > 0){
            $("#toCatrBtn").removeClass("hide");
        }else if(this.items.length == 0){
            $("#toCatrBtn").addClass("hide");
            this.timeout();
        }
        this.calcCart();
    }
    breakOrder(){
        $.post("func_w.php?r=breakOrder", { uid: event.user.id, hash: event.user.hash });
        if(event.project.saleType == 2){
            $("#panzoom").find(".selected").removeClass("selected").removeClass("hover");
            document.getElementById("orderlist").innerHTML = "";
        }else{
            for(var i=0;i<this.items.length;i++){
                document.getElementById("count"+this.items[i].s).innerHTML = "0";
                document.getElementById("order"+this.items[i].s).classList.add("hide");
            }
        }
        // add to Removed
        for(var i=0;i<this.items.length;i++) if(this.items[i].p !== false) this.addRemoved("s"+this.items[i].s+"r"+this.items[i].r+"p"+this.items[i].p);

        this.items = new Array();
        this.calcCart();
        cart.sidebarPage("no");
    }
    getCart(){
        var t = this;
        $.post("func_w.php?r=getCart", { uid: event.user.id, hash: event.user.hash }, function( resp ) {
            if(resp == 0) return false;
            var cartresp = JSON.parse(resp);
            var cartdata = JSON.parse(cartresp.order);
            if(cartdata.length == 0) return false;
            cart.sidebarPage("cart");
            t.timerstart(cartresp.dt);
            for(var i=0;i<cartdata.length;i++){

                t.items.push({s: cartdata[i].tid, c: cartdata[i].count, a: true});
                var ticket = tickets.filter(function (p){ return p.id == cartdata[i].tid })[0];

                $("#count"+cartdata[i].tid).html(cartdata[i].count);

                var p = cartdata[i].count+" "+t.declOfNum(count, ['билет', 'билета', 'билетов']);
                document.getElementById("orderlist").innerHTML += "<div id=\"order"+ticket.id+"\" class=\"item\"><div class=\"data\">"+p+"<br>"+ticket.name+"</div><div class=\"price\">"+ticket.price+" ₽</div></div>";
            }
            if(event.user.repost == 0) t.checkRepost();
            t.calcCart();
        });
    }
    sortItems(){
        this.items.sort(function(a, b) {
            if(parseInt(a.s) > parseInt(b.s)){
                return 1;
            }else if(parseInt(a.s) == parseInt(b.s)){
                if(parseInt(a.r) > parseInt(b.r)){
                    return 1;
                }else if(parseInt(a.r) == parseInt(b.r)){
                    if(parseInt(a.p) > parseInt(b.p)){
                        return 1;
                    }else{
                        return '−1';
                    }
                }else{
                    return '−1';
                }
            }else{
                return '−1';
            }
        });
    }
    add(s,r=false,p=false,notAdd=false){
        var itc = 0;
        for(var i=0;i<this.items.length;i++){
            if(this.items[i].c){ itc += this.items[i].c; }else{ itc++ }
        }
        if(itc > 6){
            $(".itog:eq(0)").tooltipster({animationDuration:0,delay: 0,contentAsHTML: true}).tooltipster('content', 'За один заказ можно купить<br /> не более 7 билетов').tooltipster('open');
            setTimeout(function(){ $(".itog:eq(0)").tooltipster('destroy'); }, 1500);
            return false;
        }
        var sector = get_sector_data(s);
        if(sector.c){
            this.items.push({s: s, c: 1, a: true});
        }else if(sector.f){
            this.items.push({s: s, f: true});
        }else{
            this.items.push({s: s, r: r, p: p});
        }
        if(this.calcPrice() > 14000){
            $(".itog:eq(0)").tooltipster({contentAsHTML: true}).tooltipster('content', 'Сумма заказа не может<br />превышать 14.000 ₽').tooltipster('open');
            setTimeout(function(){ $(".itog:eq(0)").tooltipster('destroy'); }, 1500);
            this.items.splice(-1,1);
            return false;
        }
        this.items.splice(-1,1);

        if(sector.c || sector.f){
            if(!notAdd){
                if(!cart.bookPlace("s"+s, 1, 1)){
                    $(".count").tooltipster({animationDuration:0,delay: 0}).tooltipster('content', 'Эти билеты закончились').tooltipster('open');
                    setTimeout(function(){
                        $(".count").tooltipster('destroy');
                    }, 1500);
                    return false;
                }else{
                    $("#s"+s+"r1p1").addClass("selected").removeClass("hover");
                }
            }else{
                $("#s"+s+"r1p1").removeClass("hover");
            }
        }else{
            if(!cart.bookPlace("s"+s+"r"+r+"p"+p, 1)){
                $(".count").tooltipster({animationDuration:0,delay: 0}).tooltipster('content', 'Этот билет только что купили').tooltipster('open');
                setTimeout(function(){
                    $(".count").tooltipster('destroy');
                }, 1500);
                return false;
            }
        }

        if(sector.f){
            this.items.push({s: s, f: true});
            this.addOrderList(s, false, sector.s.length);
            rm.add("s"+s, sector.n, getPlacePrice(s, 1), 1);

        }else if(sector.c){
            var count = 0;
            var item = this.checkItem(s);
            if(item.c) count = item.c + 1;

            if(item.h !== false){
                this.items[item.h].c = count;
                this.editItem(s,count,sector.n);
                rm.add("s"+s, sector.n, getPlacePrice(s, 1), count);
            }else{
                if(this.modal.active){
                    this.items.push({s: s, c: 1, a: true});
                    this.addOrderList(s, false, 1);
                    rm.add("s"+s, sector.n, getPlacePrice(s, 1), 1);
                }else{
                    this.modal.show(s, count, this);
                }
            }

        }else if(r && p){
            var price = getPlacePrice(s, r);
            var color = priceByColors[price];

            this.items.push({s: s, r: r, p: p});
            this.addOrderList(s, r, p);
            rm.add("s"+s+"r"+r+"p"+p, sector.n, price, 1);

            $("#s"+s+"r"+r+"p"+p).removeClass("hover").addClass("selected");
        }

        this.sortItems();

        if(this.items.length > 0){
            rm.goal('AddToCart');
            cart.sidebarPage("cart");
            this.timerstart();
            if(this.items.length > 8) document.getElementsByClassName("zakaz")[0].classList.add("bottom−shadow");
        }

        return true;
    }
    removeFull(s, r, p){
        var sector = get_sector_data(s);
        if(!r){
            for(var i=0;i<this.items.length;i++){
                if(this.items[i].s == s){
                    this.items.splice(i, 1);
                    break;
                }
            }
            var item = document.getElementById("s"+s+"r1p"+sector.s.length);
            item.classList.remove("hover");
            item.classList.remove("selected");

            var item = document.getElementById("order"+s);
        }else{
            for(var i=0;i<this.items.length;i++){
                if(this.items[i].s == s && this.items[i].r == r && this.items[i].p == p){
                    this.items.splice(i, 1);
                    break;
                }
            }
            var item = document.getElementById("s"+s+"r"+r+"p"+p);
            item.classList.remove("hover");
            item.classList.remove("selected");
            var item = document.getElementById("order"+s+"_"+r+"_"+p);
        }
        item.classList.add("hide");
        setTimeout(function(){ item.parentNode.removeChild(item); },200);
        this.modal.hide();
        overlay.hide();
    }
    remove(s,r,p){
        var sector = get_sector_data(s);
        if(sector.c || sector.f){
            if(!cart.bookPlace("s"+s, 0, 1)) return false;
        }else{
            if(!cart.bookPlace("s"+s+"r"+r+"p"+p, 0)) return false;
        }

        this.addRemoved("s"+s+"r"+r+"p"+p);

        if(sector.f){
            this.removeFull(s);
            rm.remove("s"+s, 1);

        }else if(sector.c){
            var count = 0;
            var item = this.checkItem(s);

            if(item.c) count = item.c - 1;

            if(item.h !== false){
                if(count > 0){
                    this.items[item.h].c = count;
                    this.editItem(s,count,sector.n);
                }else{
                    this.removeFull(s);
                }
                rm.remove("s"+s, 1);
            }

        }else if( r && p ){
            this.removeFull(s,r,p);
            rm.remove("s"+s+"r"+r+"p"+p, 1);
        }

        this.sortItems();

        if(this.items.length <= 8) document.getElementsByClassName("zakaz")[0].classList.remove("bottom−shadow");

        this.calcCart();

        if(this.items.length == 0){
            cart.sidebarPage("no");
            clearInterval(this.ping);
        }
    }
    pinger(){
        var t = this;
        setInterval(function(){
            $.post( "func_w.php?r=ping", { uid: event.user.id, pid: event.project.id, hash: event.user.hash }, function(r){
                var data = JSON.parse(r);
                console.log(data);
                if(event.user.repost != data.repost){
                    event.user.repost = data.repost;
                    switch(data.repost){
                        case "active":
                            $("#repost").html("<div class=\"pd\"><img src=\"img/repost.png\">Скидка за репост "+event.project.repostSell+" ₽</div>");
                            $("#repostSell").html("Скидка за репост: <span style='float:right'>"+event.project.repostSell+" ₽</span>");
                            t.calcCart();
                            break;
                        case "spent":
                            $("#repost").html("<div class=\"pd tooltip\" data-tooltip-content=\"#tooltip_content2\"><img src=\"img/norepost.png\">Скидка за репост использована</div>");
                            break;
                        case "timeout":
                            $("#repost").html("<div class=\"pd tooltip\" data-tooltip-content=\"#tooltip_content2_2\"><img src=\"img/norepost.png\">Скидка за репост не действует</div>");
                            break;
                        case "notactive":
                            $("#repost").html("");
                            break;
                        case "norepost":
                            $("#repost").html("<div class=\"pd tooltip\" data-tooltip-content=\"#tooltip_content\"><img src=\"img/norepost.png\">Получите скидку за репост!</div>");
                            break;
                    }
                    $("body").find(".tooltip").not(".tooltipstered").tooltipster({contentAsHTML: true, interactive: true});
                }
                if(data.payed == true){
                    rm.pay();

                    setCookie('hash', data.nh);
                    event.user.hash = data.nh;
                    delete event.user.promocode;

                    $("body").find(".selected").addClass("sold").removeClass("selected");
                    document.getElementById("orderlist").innerHTML = "";
                    t.items = new Array();
                    clearInterval(t.timer);
                    overlay.hide(1);
                    t.timer = false;
                    cart.sidebarPage('done');
                    pz.setCenter();
                    t.calcCart();
                    $("#sp5 .loader").html('<svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52"><circle class="checkmark__circle" cx="26" cy="26" r="25" fill="none"/><path class="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/></svg><span>Оплата произведена<br>Проверьте свои сообщения</span>');
                    try{ wind.close(); } catch(e){ }

                }

                var diff = t.arr_diff(event.saleByHall.booked, data.booked);
                if(diff.length > 0){
                    for (var i=0; i < diff.length; i++) {
                        if(event.saleByHall.booked.indexOf(diff[i]) != -1){
                            var s = get_sector_data(diff[i].split("s")[1].split("r")[0]);
                            if(!s.c && !s.f){
                                var $el = $("#"+diff[i]);
                                if(t.lastRemoved.indexOf(diff[i]) == -1) $el.removeClass("booked").addClass("freeSpace").tooltipster({contentAsHTML: true}).tooltipster('content', 'Место освободилось').tooltipster('open');
                                $("#"+diff[i]+" circle").css({"stroke": $("#"+diff[i]).attr("fill")});
                            }
                        }else{
                            if(diff[i].indexOf("r") != -1){
                                if(!$("#"+diff[i]).hasClass("selected")){
                                    $("#"+diff[i]).addClass("booked");
                                    $("#"+diff[i]+" circle").css({"stroke": "#000"});
                                }
                            }
                        }
                    }
                    setTimeout(function(){ $("body").find(".freeSpace").tooltipster('destroy').removeClass("freeSpace"); }, 1500);
                    event.saleByHall.booked = data.booked;
                }
            });
        }, 3000);
    }
    timerstart(dt){
        if(this.timer === false){
            console.log("timerstart");
            if(typeof dt != "undefined"){
                this.timeend = this.add_minutes(new Date(dt), 15);
            }else{
                this.timeend = this.add_minutes(new Date(), 15);
            }
            this.ticker();
            var t = this;
            this.timer = setInterval(t.ticker.bind(t), 500);
        }
    }
    ticker(){
        var $el = $("#timeleft");
        var today = new Date();
        today = Math.floor((this.timeend-today)/1000);
        var te = 0;
        var tsec=today%60; today=Math.floor(today/60); te=tsec; if(tsec<10)tsec='0'+tsec;
        var tmin=today%60; today=Math.floor(today/60); te+=tmin*60; if(tmin<10)tmin='0'+tmin;
        if(parseInt(tmin) <= 0 && parseInt(tsec) <= 0){
            $el.text("00:00");
            this.timeout();
        }else{
            $el.text(tmin+":"+tsec);
        }
    }
    timeout(){
        console.log("timeout");
        this.breakOrder()
        clearInterval(this.timer);
        this.timer = false;
        overlay.hide(1);
        pz.setCenter();
        swal("Врямя вышло", "Повторите свой заказ", "error");
    }
}
function Modal(t, ho){
    var ct = t;
    this.active = false;
    this.hide = function(){
        if(this.active == true){
            this.active = false;
            if(typeof ho != "undefined"){ overlay.hide(); }
            $("#modal").css({"transition": "all 0.25s", "opacity": "0"});
            setTimeout(function(){ $("#modal").remove(); }, 250);
        }
        console.log("hide");
    }
    this.show = function(s, c){
        if(this.active == false){
            this.active = true;
            var th = this;

            var sector_data = get_sector_data(s);
            $("body").append("<div id=\"modal\" class=\"window hide\"><div class=\"descr\">Выберите количество билетов</div><div class=\"sector\">"+sector_data.n+"</div><div class=\"info\"><div class=\"price\">"+sector_data.p+" P</div><div class=\"counter\"><div class=\"icon\">−</div><div class=\"count\">"+c+"</div><div class=\"icon\">+</div></div></div></div>");

            $("#modal").css({"margin-top": ($(window).height()/2 - $("#modal").outerHeight()/2) + "px", "margin-left": (($(window).width() - $(".sidebar").width())/2 - $("#modal").outerWidth()/2) + "px"});

            setTimeout(function(){ $("#modal").removeClass("hide"); }, 1);

            overlay.show(0, th);

            $("#modal .icon:eq(0)").on("click tap", function(e){
                var count = parseInt( $("#modal .count").text() );
                if(count > 0){
                    $("#modal .count").text( count-1 );
                    ct.remove(s);
                }
            });
            $("#modal .icon:eq(1)").on("click tap", function(e){
                var count = parseInt( $("#modal .count").text() );
                if(count < 9){
                    if(ct.add(s)) $("#modal .count").text( count+1 );
                }
            });
        }
    }
}
function validate(){
    var name = $("#name").val();
    var mail = $("#mail").val();
    var phone = $("#phone").val();
    var promocode = $("#promocode").val();
    $.post("func_w.php?r=validate", { uid: event.user.id, pid: event.project.id, hash: event.user.hash, name: name, mail: mail, phone: phone, promocode: promocode }, function( resp ) {
        resp = JSON.parse(resp);
        if(resp.type == "error"){
            switch(resp.msg){
                case 'smallname':
                    swal("Ошибка", "Слишком корокие имя и фамилия", "error");
                    break;
                case 'nolastname':
                    swal("Ошибка", "Введите фамилию", "error");
                    break;
                case 'badmail':
                    swal("Ошибка", "Не верно введен e-mail", "error");
                    break;
                case 'hasmail':
                    swal("Ошибка", "Кто то уже зарегистрирован с данный e-mail адресом", "error");
                    break;
                case 'badphone':
                    swal("Ошибка", "Не верно введен номер телефона", "error");
                    break;
                case 'hasphone':
                    swal("Ошибка", "Кто то уже зарегистрирован с данным номером телефона", "error");
                    break;
                case 'badPromocode':
                    swal("Ошибка", "Промокод не найден", "error");
                    $("#promocode").val("");
                    break;
                case 'spentPromocode':
                    swal("Ошибка", "Промокод уже использован", "error");
                    $("#promocode").val("");
                    break;
                case 'endPromocode':
                    swal("Ошибка", "Промокод больше не активен", "error");
                    $("#promocode").val("");
                    break;
            }

        }else if(resp.type == "success"){
            if(resp.promocode && typeof event.user.promocode == "undefined"){
                if(resp.sellType == "percent"){ sell = resp.sell+"%"; }
                if(resp.sellType == "price") sell = resp.sell+"₽";
                event.user.promocode = {"sellType": resp.sellType, "sell": resp.sell};
                swal("Ваша скидка: "+sell, "Промокод активирован", "success");
                cart.calcCart();
            }
            rm.goal('SetUserData');
            cart.sidebarPage("payment");
        }
    });
}
var wind;
function toPay(){
    if(inpay == 0){
        if(event.user.messageAllowed == 1){
            inpay = 1;
            $("#sp4 .itog").css({"opacity": "0"});
            $("#sp4 .sbor").css({"opacity": "0"});
            $("#sp4 .loader").removeClass("hide");
            var href = $("#sp4 a").attr('href');
            $("#sp4 a").html("Отмена").css({"background": "#d64646"}).attr("oldHref", href).attr("href", "#").attr("target", "");
            setTimeout(function(){
                wind = window.open("https://soldout.media/tickets/payment/pay.php?hash="+event.user.hash+"&uid="+event.user.id, "_blank", "width=630,height=734");
            }, 500);

            rm.goal('ToPay');

        }else{
            VK.addCallback('onAllowMessagesFromCommunity', function (){
                event.user.messageAllowed = 1;
                toPay();
            });
            VK.addCallback('onAllowMessagesFromCommunityCancel', wantmessage);
            remessage();
        }
    }else{
        try{ wind.close(); }catch(e){ }
        inpay = 0;
        $("#sp4 .itog").css({"opacity": "1"});
        $("#sp4 .sbor").css({"opacity": "1"});
        $("#sp4 .loader").addClass("hide");
        var href = $("#sp4 a").attr('oldHref');
        $("#sp4 a").html("Перейти к оплате").css({"background": "#6dc872"}).attr("href", href).attr("oldHref", "").attr("target", "");
    }
}
function wantmessage(){
    swal("Вам необходимо согласится на получение сообщений потому, что билет придет в сообщения vk").then((value) => {
        remessage();
    });
}
function remessage(){
    VK.callMethod('showAllowMessagesFromCommunityBox');
}
function AddTooltipToPlace(){
    $('.place').tooltipster({
        animation: 'none',
        animationDuration:0,
        delay: 0,
        contentAsHTML: true
        //trigger: 'click'
    }).attr("pointer-events", "all");
}
function loader(c=false){
    if(c === false){
        $(".preloader").css({"opacity":"0"});
        setTimeout(function(){
            $(".preloader").css({"display":"none"});
        }, 300);
    }else{
        $(".preloader").css({"display":"block"});
        $(".preloader").css({"opacity":"1"});
    }
}
function init(){
    if(event.project.saleType == 2){
        $("#event").html(event.project.name+" ("+event.project.age+"+)<span><div id=\"date\">"+event.project.date+"</div></span>");
        $("#place").text(event.place.city+", "+event.place.name);

        $("#SeatSelect").html('<div id="panzoom"><img src="halls/'+event.place.id+'.png" style="width: 100%;"><svg style="width: auto;height:auto;"id="s" xmlns="http://www.w3.org/2000/svg" viewBox="'+event.saleByHall.hallScheme.viewbox+'"><filter id="dropshadow" width="200%" height="200%" y="-50%" x="-50%"><feGaussianBlur in="SourceAlpha" stdDeviation="2"></feGaussianBlur> <feOffset dx="0" dy="0"></feOffset><feComponentTransfer><feFuncA type="linear" flood-color="#ff0000" slope="0.5"></feFuncA></feComponentTransfer><feMerge> <feMergeNode></feMergeNode><feMergeNode in="SourceGraphic"></feMergeNode> </feMerge></filter><filter id="dropshadow2" width="200%" height="200%" y="-50%" x="-50%"><feGaussianBlur in="SourceAlpha" stdDeviation="1"></feGaussianBlur> <feOffset dx="0" dy="0"></feOffset><feComponentTransfer><feFuncA type="linear" slope="0.3"></feFuncA></feComponentTransfer><feMerge> <feMergeNode></feMergeNode><feMergeNode in="SourceGraphic"></feMergeNode> </feMerge></filter></svg></div>');

        $(".sidebar #name").val(event.user.fi);
        $(".sidebar #mail").val(event.user.mail);
        $(".sidebar #phone").val(event.user.phone);
        $("#post").attr("href", event.project.post);
        map();
        pz = new PanZoom("#panzoom");

        overlay = new ovl;
        cart = new cartClass;
        rm = new remarketing;

        $(".place").on("mouseover mouseout click tap", function (e) {
            if(pz.currentZoom > pz.minZoom){
                var text = $("#"+this.id+" text");
                var circle = $("#"+this.id+" circle");

                var g = $(this);
                var sector_id = g.attr("data-sector-id");
                var sector_data = get_sector_data(sector_id);
                var rows = 1;
                for(var i=0;i<sector_data.s.length;i++){
                    if(parseInt(sector_data.s[i].row) > rows) rows = parseInt(sector_data.s[i].row);
                }
                var row = g.attr("data-row");
                var place = g.attr("data-place");
                var color = g.css("fill");

                switch(e.type){
                    case "mouseover":
                        $("body").find(".hover").removeClass("hover");
                        if(rows > 1){
                            var place_text = "Ряд "+row+", место "+place;
                        }else{
                            var place_text = "Место "+place;
                        }
                        if(g.hasClass("full-sale")) place_text = "Весь сектор ("+sector_data.fp+" мест)";
                        if(g.hasClass("admission")){ place_text = sector_data.n; if(sector_data.fp > 0){ sector_data.n = "Есть свободные места"; }else{ sector_data.n = "Cвободных мест нет"; } }

                        var price = nf(getPlacePrice(sector_id, row));

                        //try { g.tooltipster('destroy'); }catch(e){}

                        //g.tooltipster({contentAsHTML: true,animationDuration:0,delay: 0,trigger:'hover'});


                        if(g.hasClass("selected")){
                            g.tooltipster("content", "<span class=\"tooltip-header\">Место выбрано</span><span class=\"tooltip-price\">"+price+" ₽</span><span class=\"tooltip-count\">"+place_text+"</span>");
                        }else if(g.hasClass("sold")){
                            g.tooltipster("content", "<span class=\"tooltip-header\">Место занято</span><span class=\"tooltip-count\">"+sector_data.n+"</span>");
                        }else if(g.hasClass("booked")){
                            g.tooltipster("content", "<span class=\"tooltip-header\">Кто то забронировал это место.<br>Если его не оплатят, оно освободится</span><span class=\"tooltip-count\">"+sector_data.n+"</span>");
                        }else{
                            g.addClass("hover");
                            circle.css({"stroke": color});
                            g.tooltipster("content", "<span class=\"tooltip-header\">"+place_text+"</span><span class=\"tooltip-price\">"+price+" ₽</span><span class=\"tooltip-count\">"+sector_data.n+"</span>");
                        }
                        g.tooltipster('open');

                        var t = this;
                        $(t).removeClass("hover");
                        t.parentNode.appendChild(t);
                        setTimeout(function(){ $(t).addClass("hover"); },1);

                        break;
                    case "mouseout":
                        if(!g.hasClass("selected")) g.removeClass("hover");
                        break;

                    case "click":
                        if(!g.hasClass("sold") && !g.hasClass("booked")){
                            if(!g.hasClass("selected")){
                                if(sector_data.c != 0 && sector_data.f != 0) circle.css({"stroke": color});
                                cart.add(sector_data.id, row, place, 1);

                            }else{
                                if(sector_data.c){
                                    var id=false;
                                    for(var i=0;i<cart.items.length;i++){
                                        if(cart.items[i].s == sector_id ){
                                            id = i;
                                            break;
                                        }
                                    }

                                    if(id !== false){
                                        cart.modal.show(sector_id, cart.items[id].c);
                                    }else{
                                        cart.remove(sector_data.id, row, place);
                                    }
                                }else{
                                    cart.remove(sector_data.id, row, place);
                                }
                            }
                        }
                }
            }
        });

        $(".sector_stroke").on('click touchstart tap', function (e) {
            if(!$(this).hasClass("admission") && !$(this).hasClass("full-sale")){
                if(pz.currentZoom == pz.minZoom && pz.isMove == 0){
                    if(e.clientX){
                        sx = e.clientX;
                        sy = e.clientY;
                    }else{
                        sx = e.touches[0].pageX;
                        sy = e.touches[0].pageY;
                    }
                    sy = sy - 77;
                    pz.panzoomTo(sx, sy);

                    $("svg").find(".sector_stroke").addClass("hide_sector");
                    setTimeout(function(){
                        $("svg").find(".sector_stroke").css({"display": "none"});
                    }, 200);

                }
            }else{
                var sector = get_sector_data( $(this).attr("id").split("s")[1] );
                var g = $("#s"+sector.id+"r1p1");
                var circle = g.find("circle");
                var color = g.css("fill");
                if($(this).hasClass("admission")){

                    var id=false;
                    for(var i=0;i<cart.items.length;i++){
                        if(cart.items[i].s == sector.id ){
                            id = i;
                            break;
                        }
                    }

                    if(id !== false){
                        cart.modal.show(sector.id, cart.items[id].c);
                    }else{
                        circle.css({"stroke": color});
                        cart.modal.show(sector.id, 0);
                    }

                }else{
                    var g = $("#s"+sector.id+"r1p"+sector.s[sector.s-1]);
                    var circle = g.find("circle");
                    var color = g.css("fill");
                    var place = sector.s[sector.s.length-1];

                    var id=false;
                    for(var i=0;i<cart.items.length;i++){
                        if(cart.items[i].s == sector.id ){
                            id = i;
                            break;
                        }
                    }

                    if(id !== false){
                        cart.remove(sector.id, place.row, place.place);
                    }else{
                        circle.css({"stroke": color});
                        cart.add(sector.id, place.row, place.place);
                    }
                }
            }
        }).on("mouseenter", function() {
            var name = $(this).attr("data-name");
            var price = $(this).attr("data-price");
            var free = $(this).attr("data-free");

            if($(this).hasClass("full-sale")){
                if(free != 0){
                    $('.sector_stroke').tooltipster("content", "<span class=\"tooltip-header\">"+name+"</span><span class=\"tooltip-price\">"+price+" ₽</span><span class=\"tooltip-count\">Весь сектор ("+free+" мест)</span>");
                }else{
                    $('.sector_stroke').tooltipster("content", "<span class=\"tooltip-header\">"+name+"</span><span class=\"tooltip-count\">Cвободных мест нет</span>");
                }
            }else if($(this).hasClass("admission")){
                if(free != 0){
                    $('.sector_stroke').tooltipster("content", "<span class=\"tooltip-header\">"+name+"</span><span class=\"tooltip-price\">"+price+" ₽</span><span class=\"tooltip-count\">Есть свободные места</span>");
                }else{
                    $('.sector_stroke').tooltipster("content", "<span class=\"tooltip-header\">"+name+"</span><span class=\"tooltip-count\">Cвободных мест нет</span>");
                }
            }else{
                if(free != 0){
                    $('.sector_stroke').tooltipster("content", "<span class=\"tooltip-header\">"+name+"</span><span class=\"tooltip-price\">"+price+" ₽</span><span class=\"tooltip-count\">Cвободных мест: "+free+"</span>");
                }else{
                    $('.sector_stroke').tooltipster("content", "<span class=\"tooltip-header\">"+name+"</span><span class=\"tooltip-count\">Cвободных мест нет</span>");
                }
            }
        });

        var element = document.getElementById("panzoom");
        element.addEventListener('hasZoom', function(e) {
            $("svg").find(".sector_stroke").addClass("hide_sector");
            setTimeout(function(){
                $("svg").find(".sector_stroke").css({"display": "none"});
                if(pz.oldZoom == pz.minZoom) AddTooltipToPlace();
            }, 200);
        });
        element.addEventListener('minZoom', function(e) {
            $("svg").find(".sector_stroke").css({"display": "block"}).removeClass("hide_sector");
            setTimeout(function(){
                $("svg").find(".place").not(".selected").tooltipster('destroy').removeClass("tooltipstered").attr("pointer-events", "none");
            }, 200);
        });

        $('.sector_stroke').tooltipster({
            animation: 'grow',
            animationDuration:0,
            delay: 0,
            contentAsHTML: true,
            side: ['top', 'left'],
            //trigger: 'click'
        });

        $("#phone").mask("+7 (999) 999-99-99");
        loader();
    }
}
function hilight(c){
    console.log("hilight");
    $("body").find(".sector_selected").removeClass("sector_selected");
    $("body").find(".place").not(".selected").not(".booked").not(".sold").removeClass("sBp").addClass("NsBp");
    var v=0;
    var s;
    $("body").find(".place").not(".selected").not(".booked").not(".sold").each(function(){
        if($(this).attr("fill") == c){
            v++;
            $(this).removeClass("NsBp").addClass("sBp");
            s = $(this).attr("data-sector-id");
        }
    });
    if(v == 1) $("#s"+s).addClass("sector_selected");
}
function offHilight(){
    $("body").find(".sector_selected").removeClass("sector_selected");

    $("body").find(".sBp").not(".selected").not(".booked").not(".sold").removeClass("sBp");
    $("body").find(".NsBp").not(".selected").not(".booked").not(".sold").removeClass("NsBp");
}loadEvent("179123523", "164066813");
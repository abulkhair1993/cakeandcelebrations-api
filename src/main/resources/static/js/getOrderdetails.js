function getOrders() {

$.getJSON('http://localhost:8080/api/v1/orders',function(data) {
    $.each(data, function(index, element) {
    console.log(data);
        /*$('body').append($('<div>', {
            text: element.name
        }));*/
    });
});

}




//function getOrderUrl(){
//	var baseUrl = $("meta[name=baseUrl]").attr("content")
//	return baseUrl + "/api/report/salesReport";
//}
function getOrderUrl(startDate, endDate) {
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    return baseUrl + "/api/report/salesReport?startDate=" + startDate + "&endDate=" + endDate;
}
//function getOrderList(){
//	var url = getOrderUrl();
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		displayOrderList(data);
//	   },
//	   error: handleAjaxError
//	});
//}
function getOrderList(startDate, endDate) {
    var url = getOrderUrl(startDate, endDate);
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            displayOrderList(data);
        },
        error: handleAjaxError
    });
}


//UI DISPLAY METHODS

function displayOrderList(data){
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	console.log("displayBrandList.............");
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>'  + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

//INITIALIZATION CODE
//function init(){
//	$('#refresh-data').click(getOrderList);
//}

function init() {
    $('#search').click(function() {
        var startDate = $('#inputStartDate').val();
        var endDate = $('#inputEndDate').val();
        getOrderList(startDate, endDate);
    });
}

$(document).ready(init);
$(document).ready(getOrderList);

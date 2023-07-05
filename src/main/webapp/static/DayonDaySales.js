
function getDayonDaySalesUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report/dayonDaySales";
}


//BUTTON ACTIONS

function getDayonDaySalesList(){
	var url = getDayonDaySalesUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayDayonDaySalesList(data);
	   },
	   error: handleAjaxError
	});
}
// FILE UPLOAD METHODS

//UI DISPLAY METHODS

function displayDayonDaySalesList(data){
	var $tbody = $('#dayonDaySales-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + e.invoice_orders_count + '</td>'
		+ '<td>'  + e.invoice_items_count + '</td>'
		+ '<td>'  + e.total_revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function updateFileName(){
	var $file = $('#dayonDaySalesFile');
	var fileName = $file.val();
	$('#dayonDaySalesFileName').html(fileName);
}



//INITIALIZATION CODE
function init(){
	$('#refresh-data').click(getDayonDaySalesList);
    $('#dayonDaySalesFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getDayonDaySalesList);

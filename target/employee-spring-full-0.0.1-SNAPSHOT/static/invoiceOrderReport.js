
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderItem";
}


//BUTTON ACTIONS

function getOrderItemList(){
	var url = getOrderItemUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   data: {
             startDate: '2023-01-01',  // Specify the start date
             endDate: '2023-12-31'     // Specify the end date
           },
	   success: function(data) {
	   		displayOrderItemList(data);
	   },
	   error: handleAjaxError
	});
}
setInterval(fetchData, 5000);
// FILE UPLOAD METHODS

//UI DISPLAY METHODS

function displayOrderItemList(data){
	var $tbody = $('#orderItem-table').find('tbody');
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
	var $file = $('#orderItemFile');
	var fileName = $file.val();
	$('#orderItemFileName').html(fileName);
}

unction displayUploadData(){
 	resetUploadDialog();
	$('#upload-orderItem-modal').modal('toggle');
}f



//INITIALIZATION CODE
function init(){
	$('#refresh-data').click(getOrderItemList);
    $('#orderItemFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getOrderItemList);

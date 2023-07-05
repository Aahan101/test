
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report/salesReport";
}
function getOrderUrl2(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report/salesReport/new";
}

function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}
function addOrderData(event){
	//Set the values to update
	var $form = $("#sales-form");
	var json = toJson($form);
	var url = getOrderUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
           console.log("yessssssssssssssss");
           displayOrderList(response);
	   },
	   error: handleAjaxError
	});

	return false;
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
function init(){
	$('#refresh-data').click(getOrderList);
	$('#search').click(addOrderData);
}


$(document).ready(init);
$(document).ready(getOrderList);

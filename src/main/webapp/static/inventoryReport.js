
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
//function addInventory(event){
//	//Set the values to update
//	var $form = $("#inventory-form");
//	var json = toJson($form);
//	var url = getInventoryUrl();
//
//	$.ajax({
//	   url: url,
//	   type: 'POST',
//	   data: json,
//	   headers: {
//       	'Content-Type': 'application/json'
//       },
//	   success: function(response) {
//	   		getInventoryList();
//	   },
//	   error: handleAjaxError
//	});
//
//	return false;
//}


function getInventoryList(){
	var url = getInventoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);
	   },
	   error: handleAjaxError
	});
}

//function deleteInventory(id){
//	var url = getInventoryUrl() + "/" + id;
//
//	$.ajax({
//	   url: url,
//	   type: 'DELETE',
//	   success: function(data) {
//	   		getInventoryList();
//	   },
//	   error: handleAjaxError
//	});
//}


//UI DISPLAY METHODS

function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		console.log(e);
		var row = '<tr>'
		//+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand  + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE
function init(){
	//$('#add-inventory').click(addInventory);
	$('#refresh-data').click(getInventoryList);
}

$(document).ready(init);
$(document).ready(getInventoryList);

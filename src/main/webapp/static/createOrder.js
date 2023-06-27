
function getCreateOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/createOrder";
}

//BUTTON ACTIONS
var createOrderData=[];

function getCreateOrderList() {
  // Retrieve the create order data from the array
  var data = createOrderData;
  displayCreateOrderList(data);
}



function addCreateOrder(event) {
  // Set the values to update
  var $form = $("#createOrder-form");
    var json = toJson($form);
    var url = getCreateOrderUrl();
    $.ajax({
      url: url,
      type: 'POST',
      data: json,
      headers: {
        'Content-Type': 'application/json'
      },
      success: function(response) {
        // Store the create order data in the array
        createOrderData.push(json);
        getCreateOrderList();
      },
      error: handleAjaxError
    });
    return false;
}

function updateCreateOrder(event){
	$('#edit-createOrder-modal').modal('toggle');
	//Get the ID
	var id = $("#createOrder-edit-form input[name=id]").val();
	var url = getCreateOrderUrl() + "/" + id;

	//Set the values to update
	var $form = $("#createOrder-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getCreateOrderList();
	   },
	   error: handleAjaxError
	});
	return false;
}


function deleteCreateOrder(id){
	var url = getCreateOrderUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getCreateOrderList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#createOrderFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getCreateOrderUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayCreateOrderList(data){
	var $tbody = $('#createOrder-table').find('tbody');
	$tbody.empty();
	console.log(data);
//	for(var i in data){
//		var e = data[i];
//		console.log(e);
//		var buttonHtml = '<button onclick="deleteCreateOrder(' + e.id + ')">delete</button>'
//		buttonHtml += ' <button onclick="displayEditCreateOrder(' + e.id + ')">edit</button>'
//		var row = '<tr>'
//		+ '<td>' + e.barcode + '</td>'
//		+ '<td>' + e.quantity + '</td>'
//		+ '<td>' + e.sellingPrice + '</td>'
//		+ '<td>' + buttonHtml + '</td>'
//		+ '</tr>';
//        $tbody.append(row);
//	}
var parsedArray = data.map(function(jsonString) {
     return JSON.parse(jsonString);
   });
   // Accessing the parsed objects
   parsedArray.forEach(function(e) {
     console.log(e.barcode);
     console.log(e.quantity);
     console.log(e.sellingPrice);
                var buttonHtml = '<button onclick="deleteCreateOrder(' + e.id + ')">delete</button>'
               buttonHtml += ' <button onclick="displayEditCreateOrder(' + e.id + ')">edit</button>'
               var row = '<tr>'
               + '<td>' + e.barcode + '</td>'
               + '<td>' + e.quantity + '</td>'
               + '<td>' + e.sellingPrice + '</td>'
               + '<td>' + buttonHtml + '</td>'
               + '</tr>';
                 $tbody.append(row);
   });
}

function displayEditCreateOrder(id){
	var url = getCreateOrderUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayCreateOrder(data);
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#createOrderFile');
	$file.val('');
	$('#createOrderFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#createOrderFile');
	var fileName = $file.val();
	$('#createOrderFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-createOrder-modal').modal('toggle');
}

function displayCreateOrder(data){
	$("#createOrder-edit-form input[name=orderId]").val(data.orderId);
	$("#createOrder-edit-form input[name=quantity]").val(data.quantity);
	$("#createOrder-edit-form input[name=sellingPrice]").val(data.sellingPrice);
	$('#edit-createOrder-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-createOrder').click(addCreateOrder);
	$('#update-createOrder').click(updateCreateOrder);
	$('#refresh-data').click(getCreateOrderList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#createOrderFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getCreateOrderList);


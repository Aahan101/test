
function getCreateOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/createOrder";
}
var createOrderData=[];
function getCreateOrderList() {
  // Retrieve the create order data from the array
  var data = createOrderData;
  displayCreateOrderList(data);
}
//BUTTON ACTIONS
function addCreateOrder(event){
	//Set the values to update
	var $form = $("#createOrder-form");
	var json = toJson($form);

	var url = getCreateOrderUrl();
	var json1=JSON.parse(json);
	var data= createOrderData;
        console.log("The data is: ",data);
        // Find the object with the matching ID
        //parsedArray.forEach(function(e) {

        var objectToUpdate= data.find(function(obj) {
            return obj.barcode === json1.barcode;
        });

        if (objectToUpdate) {
          // Update the properties of the object
          throw new Error("Barcode already exists");
        }

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
           var pa = JSON.parse(json);
           console.log(json);
           console.log(pa.barcode);
           pa.id=generateUniqueId();
           console.log("json id= ",pa.id);

           createOrderData.push(pa);

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
	var url = getCreateOrderUrl();

	//Set the values to update
	var $form = $("#createOrder-edit-form");
	var json = toJson($form);
	json=JSON.parse(json);
//
//    var idToUpdate = id; // ID of the object to update
    var data= createOrderData;
    console.log("The data is: ",data);
    // Find the object with the matching ID
    //parsedArray.forEach(function(e) {

    var objectToUpdate = data.find(function(obj) {
        console.log("obj id= ",obj.id,"id = ",id);
        return obj.id === id;
    });

    if (objectToUpdate) {
      // Update the properties of the object
      objectToUpdate.barcode = json.barcode;
      objectToUpdate.quantity = json.quantity;
      objectToUpdate.sellingPrice = json.sellingPrice;

      console.log("Object with ID " + id + " is updated:");
      console.log(objectToUpdate);
    } else {
      console.log("Object with ID " + id + " not found.");
    }
    //getCreateOrderList();

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: JSON.stringify(objectToUpdate),
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
	var url = getCreateOrderUrl();
	var data=createOrderData;
	var idString=id.toString();
	var index=createOrderData.find(function(obj){
	console.log("obj id= ",obj.id,"id = ",id);
            return obj.id === id;
	});
	if(index!=-1){
	    createOrderData.splice(index,1);
	}
	else{
	    console.log("Object with ID " + id + " not found.");
	}
    getCreateOrderList();
}
function updateData(data){
    var baseUrl = $("meta[name=baseUrl]").attr("content");
        var url= baseUrl + "/api/orderItem";
        $.ajax({
               url: url,
               type: 'POST',
               data: JSON.stringify(data),
               headers: {
                'Content-Type': 'application/json'
               },
               success: function(response) {
                    console.log("Created order item successfully");
               },
               error: handleAjaxError
            });
            return false;
}
function placeOrder(event){
    //Set the values to update
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    var url= baseUrl + "/api/order";
    console.log("placing the order");
    //OrderForm form

    var json;
    console.log("json is:::",json);
    $.ajax({
       url: url,
       type: 'POST',
       data: json,
       headers: {
        'Content-Type': 'application/json'
       },
       success: function(response) {
            console.log(response);
            var generatedId=response;
            createOrderData.forEach(function(e) {
                e.orderId=generatedId;
                console.log("e value is: ",e);
                updateData(e);
            });
            console.log("generatedId= ",generatedId);
            console.log("Created order successfully");
       },
       error: handleAjaxError
    });
    return false;
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

// Function to generate a unique ID
    var lastAssignedId = 0;
   function generateUniqueId() {
     // Generate a random number or use a library for generating unique IDs
     lastAssignedId++;
     return lastAssignedId.toString();
   }
//UI DISPLAY METHODS

function displayCreateOrderList(parsedArray){
	var $tbody = $('#createOrder-table').find('tbody');
	$tbody.empty();
	console.log(parsedArray);
//        var parsedArray = data.map(function(jsonString) {
//            return JSON.parse(jsonString);
//       });
       // Accessing the parsed objects
       parsedArray.forEach(function(e) {
            if (!e.id) {
                 e.id = generateUniqueId();
            }


         console.log("e.id");
         console.log(e.id);
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
	var url = getCreateOrderUrl();
	var id=id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data.id);
	        data.id=id;
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
	$("#createOrder-edit-form input[name=id]").val(data.id);
	$("#createOrder-edit-form input[name=barcode]").val(data.barcode);
    $("#createOrder-edit-form input[name=quantity]").val(data.quantity);
    $("#createOrder-edit-form input[name=sellingPrice]").val(data.sellingPrice);
    $('#edit-createOrder-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-createOrder').click(addCreateOrder);
	$('#update-createOrder').click(updateCreateOrder);
	$('#placeOrder').click(placeOrder);
	$('#refresh-data').click(getCreateOrderList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#createOrderFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getCreateOrderList);



function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report/salesReport";
}

//BUTTON ACTIONS

function getBrandList(){
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandList(data);
	   },
	   error: handleAjaxError
	});
}
//
//function deleteBrand(id){
//	var url = getBrandUrl() + "/" + id;
//
//	$.ajax({
//	   url: url,
//	   type: 'DELETE',
//	   success: function(data) {
//	   		getBrandList();
//	   },
//	   error: handleAjaxError
//	});
//}

// FILE UPLOAD METHODS

//UI DISPLAY METHODS

function displayBrandList(data){
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.revenue +'</td>'

		+ '</tr>';
        $tbody.append(row);
	}
}



function updateFileName(){
	var $file = $('#brandFile');
	var fileName = $file.val();
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-brand-modal').modal('toggle');
}



//INITIALIZATION CODE
function init(){
	$('#refresh-data').click(getBrandList);
    $('#brandFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getBrandList);

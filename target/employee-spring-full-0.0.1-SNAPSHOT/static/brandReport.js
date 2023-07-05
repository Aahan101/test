//
//function getBrandUrl(){
//	var baseUrl = $("meta[name=baseUrl]").attr("content")
//	return baseUrl + "/api/brand";
//}
//
////BUTTON ACTIONS
//
//function getBrandList(){
//	var url = getBrandUrl();
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		displayBrandList(data);
//	   },
//	   error: handleAjaxError
//	});
//}
//
//// Retrieve the table rows
////const tableRows = document.querySelectorAll('#brand-table tbody tr');
////
////// Retrieve the search input field
////const searchInput = document.getElementById('inputBrand');
////
////// Add an input event listener to the search input
////searchInput.addEventListener('input', function() {
////  const searchTerm = searchInput.value.trim().toLowerCase();
////
////  // Loop through the table rows and hide/show them based on the search term
////  tableRows.forEach(row => {
////    const brandText = row.cells[1].textContent.toLowerCase();
////    const categoryText = row.cells[2].textContent.toLowerCase();
////
////    if (brandText.includes(searchTerm) || categoryText.includes(searchTerm)) {
////      row.style.display = '';
////    } else {
////      row.style.display = 'none';
////    }
////  });
////});
//
//
//
//
//
//
//// FILE UPLOAD METHODS
//
////UI DISPLAY METHODS
//
//function displayBrandList(data){
//	var $tbody = $('#brand-table').find('tbody');
//	$tbody.empty();
//	for(var i in data){
//		var e = data[i];
//		var row = '<tr>'
//		+ '<td>' + e.id + '</td>'
//		+ '<td>' + e.brand + '</td>'
//		+ '<td>'  + e.category + '</td>'
//
//		+ '</tr>';
//        $tbody.append(row);
//	}
//}
//
//
//
//function updateFileName(){
//	var $file = $('#brandFile');
//	var fileName = $file.val();
//	$('#brandFileName').html(fileName);
//}
//function displayResults(filteredArray) {
//  var $tbody = $('#brand-table').find('tbody');
//  $tbody.empty();
//
//  if (filteredArray.length > 0) {
//    filteredArray.forEach(obj => {
//      var row = '<tr>'
//      + '<td>' + obj.id + '</td>'
//      + '<td>' + obj.brand + '</td>'
//      + '<td>'  + obj.category + '</td>'
//      + '</tr>';
//      $tbody.append(row);
//    });
//  } else {
//    var noResultsRow = '<tr><td colspan="3">No matching results</td></tr>';
//    $tbody.append(noResultsRow);
//  }
//}
//
////INITIALIZATION CODE
//function init(){
//  $('#search').click(function(event) {
//    event.preventDefault();
//    var searchTerm = $('#inputBrand').val().toLowerCase();
//    var data = JSON.parse(localStorage.getItem('brandData'));
//    var filteredArray = data.filter(obj =>
//      obj.brand.toLowerCase().includes(searchTerm) ||
//      obj.category.toLowerCase().includes(searchTerm)
//    );
//    displayResults(filteredArray);
//  });
//
//  $('#brandFile').on('change', updateFileName);
//}
//
////INITIALIZATION CODE
////function init(){
////	$('#refresh-data').click(getBrandList);
////    $('#brandFile').on('change', updateFileName)
////}
//
//$(document).ready(init);
//$(document).ready(getBrandList);




function getBrandUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/brand";
}

function getBrandList() {
  var url = getBrandUrl();
  $.ajax({
    url: url,
    type: 'GET',
    success: function(data) {
      localStorage.setItem('brandData', JSON.stringify(data));
      displayBrandList(data);
    },
    error: handleAjaxError
  });
}

function displayBrandList(data) {
  var $tbody = $('#brand-table').find('tbody');
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var row = '<tr>'
      + '<td>' + e.id + '</td>'
      + '<td>' + e.brand + '</td>'
      + '<td>' + e.category + '</td>'
      + '</tr>';
    $tbody.append(row);
  }
}

function updateFileName() {
  var $file = $('#brandFile');
  var fileName = $file.val();
  $('#brandFileName').html(fileName);
}

function displayResults(filteredArray) {
  var $tbody = $('#brand-table').find('tbody');
  $tbody.empty();

  if (filteredArray.length > 0) {
    filteredArray.forEach(obj => {
      var row = '<tr>'
        + '<td>' + obj.id + '</td>'
        + '<td>' + obj.brand + '</td>'
        + '<td>' + obj.category + '</td>'
        + '</tr>';
      $tbody.append(row);
    });
  } else {
    var noResultsRow = '<tr><td colspan="3">No matching results</td></tr>';
    $tbody.append(noResultsRow);
  }
}

function init() {
  $('#search').click(function(event) {
    event.preventDefault();
    var searchBrand = $('#inputBrand').val().toLowerCase();
    var searchCategory = $('#inputCategory').val().toLowerCase();
    var data = JSON.parse(localStorage.getItem('brandData')) || [];
    var filteredArray = data.filter(obj =>
      obj.brand.toLowerCase().includes(searchBrand) ||
      obj.category.toLowerCase().includes(searchCategory)
    );
    displayResults(filteredArray);
  });

  $('#brandFile').on('change', updateFileName);
}

$(document).ready(init);
$(document).ready(getBrandList);

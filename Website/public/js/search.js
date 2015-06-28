// Add listner on search field and update result when there ia a new change
$(document).ready(function() {
	$("#search").keyup(function() { 
	  	var val = $(this).val()
	  	console.log(val)
	  	$("#result").load("/search", {query:val})
	});
});


var refreshPageIntervalId = 0;
var contextPath;

$(document).ready(function(){
	$("#closeMessageButton").click(function() {
		$(".message").toggleClass("visible_message");
	});
	
	$("#list").click(function() {
		if(!$("#list").hasClass("selected")) {
			$('#list').addClass('selected');
		}
		$('#grid').removeClass('selected');
		$(".container").removeClass("grid");
	});
	
	$("#grid").click(function() {
		if(!$("#grid").hasClass("selected")) {
			$('#grid').addClass('selected');
		}
		$('#list').removeClass('selected');
		
		if(!$(".container").hasClass("grid")) {
			$(".container").addClass("grid");
		}
	});
})




function refreshPage() {
	$.ajax({
		url : contextPath + '/updateStatus',
		cache : false,
		success : function(data) {
			for ( var i = 0; i < data.length; i++) {
				var id = data[i].id;
				var status = data[i].status;
				
				$('#' + id + "-container")
					.removeClass('Pass Failed Unknown')
					.addClass(status);
				
				$('#' + id + "-status").removeClass('icon-checkmark icon-close icon-question');
				$('#' + id + "-status").html(''); 
				
				if(status == 'Pass') {
					$('#' + id + "-status").addClass('icon-checkmark');
				} else if(status == 'Failed'){
					$('#' + id + "-status").addClass('icon-close');
				} else if(status == 'Unknown'){
					$('#' + id + "-status").html(getLoadingHtml());
				}
				
				if(data[i].formattedLastCheck){
					$('#' + id + "-lastCheck").text(data[i].formattedLastCheck);
				}
				else {
					$('#' + id + "-lastCheck").html(getLoadingHtml());
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			clearInterval(refreshPageIntervalId);
			$('#message').text("An error has occurred making the request: " + errorThrown);
			
			if(!$(".message").hasClass("visible_message")) {
				$(".message").addClass("visible_message");
			}
		}
	});
}

function getLoadingHtml() {
	return "<img alt='Loading...' src='resources/images/loader.gif'/>";
}

function toggleDetails(toggleBtn, rowId) {
	$("#" + rowId + '-details').toggleClass("visible");

	if ($("#" + rowId + '-details').hasClass("visible")) {
		$(toggleBtn).removeClass('icon-chevron-down').addClass(
		'icon-chevron-up');
	} else {
		$(toggleBtn).removeClass('icon-chevron-up').addClass(
		'icon-chevron-down');
	}
}
var refreshPageInterval = 5000; //5 seconds
var refreshPageIntervalId = 0;
var contextPath;
var view = 'list';

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
		
		view = 'list';
	});
	
	$("#grid").click(function() {
		if(!$("#grid").hasClass("selected")) {
			$('#grid').addClass('selected');
		}
		$('#list').removeClass('selected');
		
		if(!$(".container").hasClass("grid")) {
			$(".container").addClass("grid");
		}
		
		view = 'grid';
	});
	
	$("#auto_refresh").click(function() {
		if($("#auto_refresh").is(':checked')) {
			refreshPageIntervalId = setInterval(refreshPage, refreshPageInterval);
		} else {
			clearInterval(refreshPageIntervalId);
		}
	});
	
	$("#filter_text").change(function() {
		$(".content").html(getLargeLoadingHtml());
		filterList($('#filter_text').val());
	});
})


function filterList(filterText) {
	$.ajax({
		url : contextPath + '/filterList',
		cache : false,
		data: ({filterText : filterText}),
		success : function(response) {
			$(".content").html(response);
			
			if(view == 'grid') {			
				$('#grid').trigger('click');
			} else {
				$('#list').trigger('click');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			clearInterval(refreshPageIntervalId);
			console.log(errorThrown);
			
			displayMessage(getErrorMessageHeader(), errorThrown);
		}
	});
}


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
			displayMessage(getErrorMessageHeader(), errorThrown);
		}
	});
}

function getLoadingHtml() {
	return "<img alt='Loading...' src='resources/images/loader.gif'/>";
}

function getLargeLoadingHtml() {
	return "<img alt='Loading...' src='resources/images/loader-large.gif'/>";
}

function getErrorMessageHeader() {
	return "An error has occurred making the request. " +
			"Try <a href='#' onclick='location.reload(true); return false;'>refresh page</a> later.";
}

function displayMessage(messageHeaderHTML, message) {
	if(messageHeaderHTML) {
		if(!$(".message").hasClass("visible_message")) {
			$(".message").addClass("visible_message");
		}
		$("#message").html(messageHeaderHTML);
		
		$('#message_details').text(message);
	}
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
var refreshPageIntervalId = 0;

function refreshPage() {
	$.ajax({
		url : '/urlMonitor/updateStatus',
		cache : false,
		success : function(data) {
			for ( var i = 0; i < data.length; i++) {
				var hashcode = data[i].hashCode;
				var status = data[i].status;
				
				$('#' + hashcode + "-container")
					.removeClass('Pass Failed Unknown')
					.addClass(status);
				
				$('#' + hashcode + "-status").removeClass('icon-checkmark icon-close icon-question');
				
				if(status == 'Pass') {
					$('#' + hashcode + "-status").addClass('icon-checkmark');
				} else if(status == 'Failed'){
					$('#' + hashcode + "-status").addClass('icon-close');
				} else if(status == 'Unknown'){
					$('#' + hashcode + "-status").addClass('icon-question');
				}
				
				$('#' + hashcode + "-lastCheck").text(data[i].formattedLastCheck);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			clearInterval(refreshPageIntervalId);
			$('#message').text("An error has occurred making the request: " + errorThrown);
		}
	});
}

function setView(viewType) {
	if (viewType == 'list') {
		$('#list').addClass('selected');
		$('#grid').removeClass('selected');

	} else if (viewType == 'grid') {
		$('#grid').addClass('selected');
		$('#list').removeClass('selected');
	}

	$(".container").toggleClass("grid");
}

function toggleDetails(toggleBtn, rowId) {
	$("#" + rowId).toggleClass("visible");

	if (containCssClass(details, visibleClass)) {
		$(toggleBtn).removeClass('icon-chevron-up').addClass(
				'icon-chevron-down');
	} else {
		$(toggleBtn).removeClass('icon-chevron-down').addClass(
				'icon-chevron-up');
	}
}
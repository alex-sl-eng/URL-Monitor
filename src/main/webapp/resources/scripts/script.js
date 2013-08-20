function refreshPage() {
	$.ajax({
		url : '/urlMonitor/updateStatus',
		success : function(data) {
			for ( var i = 0; i < data.length; i++) {
				var monitor = data[i];
				var hashcode = monitor.hashCode;
				
				$('#' + hashcode + "-container")
					.removeClass('Pass Failed Unknown')
					.addClass(monitor.status);
				
				$('#' + hashcode + "-status").removeClass('icon-checkmark icon-close icon-question');
				
				if(monitor.status == 'Pass') {
					$('#' + hashcode + "-status").addClass('icon-checkmark');
				} else if(monitor.status == 'Failed'){
					$('#' + hashcode + "-status").addClass('icon-close');
				} else if(monitor.status == 'Unknown'){
					$('#' + hashcode + "-status").addClass('icon-question');
				}
				
				$('#' + hashcode + "-lastCheck").text(monitor.formattedLastCheck);
			}
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
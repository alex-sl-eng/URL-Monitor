function refreshPage() {
	$.ajax({
		url : '/urlMonitor/updateStatus',
		success : function(data) {
			for ( var i = 0; i < data.length; i++) {
				var monitor = data[i];
				console.log(monitor.name);
				var hashcode = monitor.hashCode;
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
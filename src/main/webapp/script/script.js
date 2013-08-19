function setView(viewType) {
	var containerList = document.getElementsByClassName("container");
	var listButton = document.getElementById('list');
	var gridButton = document.getElementById('grid');

	var removeClass, addClass;

	if (viewType == 'list') {
		removeClass = 'grid';
		addClass = 'list';

		addCssClass(listButton, 'selected');
		removeCssClass(gridButton, 'selected');
	} else if (viewType == 'grid') {
		removeClass = 'list';
		addClass = 'grid';

		addCssClass(gridButton, 'selected');
		removeCssClass(listButton, 'selected');
	}

	for ( var i = 0; i < containerList.length; i++) {
		removeCssClass(containerList[i], removeClass);
		addCssClass(containerList[i], addClass);
	}
}

function toggleDetails(toggleBtn, rowId) {
	var downArrowClass = 'icon-chevron-down';
	var upArrowClass = 'icon-chevron-up';
	var visibleClass = 'visible';

	var details = document.getElementById(rowId);

	if (containCssClass(details, visibleClass)) {
		removeCssClass(toggleBtn, upArrowClass);
		addCssClass(toggleBtn, downArrowClass);
		removeCssClass(details, visibleClass);
	} else {
		addCssClass(details, visibleClass);
		removeCssClass(toggleBtn, downArrowClass);
		addCssClass(toggleBtn, upArrowClass);
	}
}

function containCssClass(element, cssClass) {
	var regex = new RegExp("(?:^|\\s)" + cssClass + "(?!\\S)");
	return element.className.match(regex);
}

function removeCssClass(element, cssClass) {
	var regex = new RegExp("(?:^|\\s)" + cssClass + "(?!\\S)", 'g');
	element.className = element.className.replace(regex, '');
}

function addCssClass(element, cssClass) {
	if (!containCssClass(element, cssClass)) {
		element.className += ' ' + cssClass;
	}
}
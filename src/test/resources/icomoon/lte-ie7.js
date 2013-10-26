/* Load this script using conditional IE comments if you need to support IE 7 and IE 6. */

window.onload = function() {
	function addIcon(el, entity) {
		var html = el.innerHTML;
		el.innerHTML = '<span style="font-family: \'icomoon\'">' + entity + '</span>' + html;
	}
	var icons = {
			'icon-close' : '&#xe003;',
			'icon-checkmark' : '&#xe002;',
			'icon-question' : '&#xe004;',
			'icon-chevron-down' : '&#xf078;',
			'icon-chevron-up' : '&#xf077;',
			'icon-health' : '&#xe005;',
			'icon-list' : '&#xe009;',
			'icon-cancel' : '&#xe006;',
			'icon-envelope' : '&#xf003;',
			'icon-google-plus' : '&#xe007;',
			'icon-yahoo' : '&#xe008;',
			'icon-cog' : '&#xe00a;',
			'icon-menu' : '&#xe00b;',
			'icon-plus' : '&#xe00c;'
		},
		els = document.getElementsByTagName('*'),
		i, attr, c, el;
	for (i = 0; ; i += 1) {
		el = els[i];
		if(!el) {
			break;
		}
		attr = el.getAttribute('data-icon');
		if (attr) {
			addIcon(el, attr);
		}
		c = el.className;
		c = c.match(/icon-[^\s'"]+/);
		if (c && icons[c[0]]) {
			addIcon(el, icons[c[0]]);
		}
	}
};
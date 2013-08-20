<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="cleartype" content="on">
<title><spring:message code="Application.name"/></title>
<link rel="stylesheet" href="resources/styles/main.css">
<link rel="stylesheet" href="resources/styles/style.css">
<link
	href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,400italic'
	rel='stylesheet' type='text/css'>
<script src="resources/scripts/script.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
</head>

<header>
	<h1>
		<a href="/">
			<span class="logo icon-health"></span>
			<spring:message code="Application.name"/>
		</a>
	</h1>

	<li class="right huge right_menu_toggle">
		<span class="icon-list-2"></span>
		<ul class="right_menu">
			<li><a href="/about"><spring:message code="nav.About"/></a></li>
			<li><a href="/license"><spring:message code="nav.License"/></a></li>
			<li><a href="https://bitbucket.org/aeng/urlmonitor/issues/new"><spring:message code="nav.ReportIssue"/></a></li>
		</ul>
	</li>
</header>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="cleartype" content="on">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><spring:message code="Application.name"/></title>
<link rel="stylesheet" href="resources/styles/main.css">
<link rel="stylesheet" href="resources/styles/style.css">
<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,400italic'
	rel='stylesheet' type='text/css'>
	
<link rel="icon" type="image/png" href="resources/images/icon.png"/>
<script type="text/javascript" src="resources/scripts/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="resources/scripts/script.js"></script>
<script>contextPath = "${pageContext.request.contextPath}"</script>
</head>

<header>
	<h1>
		<a href="home">
			<span class="logo icon-health"></span>
			<spring:message code="Application.name"/>
		</a>
	</h1>

	<li class="right huge right_menu_toggle">
		<span class="icon-list-2"></span>
		<ul class="right_menu">
			<li><a href="about"><spring:message code="nav.About"/></a></li>
			<li><a href="https://bitbucket.org/aeng/url-monitor/issues/new" target="_blank"><spring:message code="nav.ReportIssue"/></a></li>
		</ul>
	</li>
	
    <ul class="message list-h">
        <li>
            <button id="close_message_button" class="icon-cancel"></button>
        </li>
        <li>
			<span id="message"></span>
			<span id="message_details"></span>
		</li>
	</ul>
</header>
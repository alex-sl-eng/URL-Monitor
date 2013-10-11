<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="Application.name"/></title>

    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base
            href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="resources/styles/style.css">
    <link rel="stylesheet" href="resources/styles/ico.css">
    <link
            href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,400italic'
            rel='stylesheet' type='text/css'>

    <link rel="icon" type="image/png" href="resources/images/icon.png"/>
    <script type="text/javascript"
            src="resources/scripts/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="resources/scripts/script.js"></script>
    <script>contextPath = "${pageContext.request.contextPath}"</script>
</head>

<header>
    <nav>
        <h1 class="l--display-inline-block">
            <a href="home"
                    class="l--pad-v-eighth l--pad-h-eighth l--display-inline-block">
                <span class="icon-health l--pad-h-eighth"></span>
                <spring:message code="Application.name"/>
            </a>
        </h1>

        <div class="l--float-right txt--huge link right-menu-toggle">
            <span class="icon-list-2 l--pad-v-eighth l--pad-h-eighth"></span>
            <ul class="right-menu">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.username"
                            var="email"/>
                    <li>
                        <a href="auth/settings"
                                class="l--pad-v-half l--pad-h-half dark-background">
                            <img src="${avatarServiceImpl.getUserAvatar(email, 75)}"
                                    class="avatar l--push-h-quarter"/>
                            <spring:message code="jsp.Settings"/>
                        </a>
                    </li>
                    <li>
                        <a href="auth/logout"
                                class="l--pad-v-half l--pad-h-half dark-background">
                            <spring:message code="jsp.Logout"/></a>
                    </li>
                </sec:authorize>
                <sec:authorize access="! isAuthenticated()">
                    <li>
                        <a href="auth/login"
                                class="l--pad-v-half l--pad-h-half dark-background">
                            <spring:message code="jsp.Login"/>
                        </a>
                    </li>
                </sec:authorize>
                <li>
                    <a href="about"
                            class="l--pad-v-half l--pad-h-half dark-background">
                        <spring:message code="jsp.About"/>
                    </a>
                </li>
                <li>
                    <a href="https://github.com/aeng/url-monitor/issues"
                            target="_blank"
                            class="l--pad-v-half l--pad-h-half dark-background">
                        <spring:message code="jsp.ReportIssue"/>
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <ul
            class="message list-h txt--small l--pad-h-quarter <c:if test="${not empty messages}">visible-message</c:if> <c:if test="${not empty severity}">${severity}</c:if>">
        <li>
            <button id="close_message_button"
                    class="icon-cancel txt--large"></button>
        </li>
        <li>
            <span id="message">${messages}</span>
            <span id="message_details"></span>
        </li>
    </ul>

</header>
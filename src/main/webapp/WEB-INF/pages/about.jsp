<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp" />

<main>
    <ul class="small list-no-bullet list-v content-wrapper">
        <li>
			<span class="label-highlight">Version</span>
			<span><c:out value="${buildVersion}"/></span>
			 <span class="label-highlight">Build</span>
            <span><c:out value="${buildDate}"/></span>
        </li>
        <li>
            <span class="label-highlight">Contact</span> 
            <span><a href="mailto:${replyTo}">${replyTo}</a></span>  
            <span class="label-highlight">Follow us @</span> 
            <a href="https://twitter.com/UrlmonitorOrg"><span class="icon-twitter"></span></a>
        </li>
        <li>
            <span class="label-highlight">Project Information</span> 
            <span>
                <a href="https://github.com/aeng/URL-Monitor/wiki" target="_blank">
                    Project Wiki Page
                </a>
            </span>
        </li>
        <li>
            <span class="label-highlight">License</span>
            <span><a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License, Version 2.0</a></span>
        </li>
    </ul>
	
</main>

<jsp:include page="includes/footer.jsp" />
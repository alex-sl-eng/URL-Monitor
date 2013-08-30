<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp" />

<main>
    <ul class="list-no-bullet list-v">
        <li>
			<span class="small label">Version</span> <c:out value="${buildVersion}"/> <c:out value="${buildDate}"/> 
        </li>
        <li>
            <span class="small label">Contact</span> <a href="mailto:${replyTo}">${replyTo}</a>  
        </li>
        <li>
            <span class="small label">Wiki</span> <a href="https://bitbucket.org/aeng/url-monitor/wiki/Home" target="_blank">
                Project Wiki Page
            </a>
        </li>
    </ul>
	
</main>

<jsp:include page="includes/footer.jsp" />
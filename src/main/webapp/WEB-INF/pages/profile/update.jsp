<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../includes/header.jsp"/>

<main>
    <sec:authentication property="principal.username" var="username"/>
    <div class="content-wrapper-small">
        <h3><spring:message code="jsp.UpdateProfile"/></h3>

        <form:form method="post" class="section l--pad-v-half l--pad-h-half">
            <ul class="list-no-bullet">
                <li class="l--pad-v-quarter txt--align-center">
                    <img src="${avatarService.getUserAvatar(username, 100)}"
                            title="${username}"/>
                </li>
                <li class="l--pad-v-quarter">
                    <label><spring:message code="jsp.Name"/></label><form:input
                        path=""
                        type="text" class="full-width"
                        value="${identity.getName()}"/>
                </li>
                <li class="l--pad-v-quarter">
                    <label><spring:message code="jsp.Email"/></label>
                    <input type="text" class="full-width" disabled="true"
                            value="${identity.getEmail()}"/>
                </li>
                <li class="l--pad-v-quarter">
                    <label><spring:message code="jsp.Roles"/></label><input
                        type="text" class="full-width"
                        disabled="true" value="${identity.getRoles()}"/>
                </li>
                <li class="l--pad-v-quarter">
                    <label><spring:message
                            code="jsp.MemberSince"/></label><input type="text"
                        class="full-width"
                        disabled="true" value="${identity.getJoinedDate()}"/>
                </li>
                <li class="l--pad-v-quarter txt--align-right">
                    <button class="button-primary"><spring:message
                            code="jsp.Update"/></button>
                    <button onclick="document.location='home';return false;">
                        <spring:message code="jsp.Cancel"/>
                    </button>
                </li>
            </ul>
        </form:form>
    </div>
</main>
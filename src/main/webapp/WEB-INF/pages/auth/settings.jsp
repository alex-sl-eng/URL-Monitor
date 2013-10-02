<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main class="l--pad-h-1 txt--align-center ">
    <sec:authentication property="principal.username" var="username"/>
    <h3><spring:message code="jsp.Settings"/></h3>
    <ul class="list-h">
        <li class="content-wrapper-small section l--pad-v-half l--pad-h-half l--align-top">
            <ul class="list-h txt--small txt--small-spacing">
                <li class="l--push-h-half">
                    <img src="${avatarService.getUserAvatar(username, 120)}"
                            title="${username}"/>
                </li>
                <li class="l--push-h-half">
                    <ul class="txt--align-left list-v list-no-bullet">
                        <li><label
                                class="l--push-h-quarter"><spring:message
                                code="jsp.Email"/></label>${username}
                        </li>
                        <li><label
                                class="l--push-h-quarter"><spring:message
                                code="jsp.Name"/></label>${identity.getName()}
                        </li>
                        <li><label
                                class="l--push-h-quarter"><spring:message
                                code="jsp.Roles"/></label>${identity.getRoles()}
                        </li>
                        <li><label class="l--push-h-quarter"><spring:message
                                code="jsp.MemberSince"/></label>${identity.getJoinedDate()}
                        </li>
                        <li>
                            <button class="button-primary"
                                    onclick="document.location='profile/update';return false;">
                                <spring:message
                                        code="jsp.UpdateProfile"/></button>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
        <c:if test="${identity.isAdmin()}">
            <li class="content-wrapper-small section l--pad-v-half l--pad-h-half l--align-top">
                <a href="monitoring">Java Melody</a>
            </li>
        </c:if>
    </ul>
</main>
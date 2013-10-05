<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sec:authentication property="principal.username" var="username"/>

<form:form method="post" action="auth/settings/profile" modelAttribute="dataForm">
    <ul class="list-h txt--small txt--small-spacing">
        <li class="l--push-h-half">
            <img src="${avatarService.getUserAvatar(username, 120)}"
                    title="${username}"/>
        </li>
        <li class="l--push-h-half">
            <ul class="txt--align-left list-v list-no-bullet">
                <li class="l--pad-v-quarter">
                    <label><spring:message code="jsp.Name"/></label>
                    <input type="text" class="full-width" name="data['name']"
                            value="${dataForm.data['name']}"/>
                </li>
                <li class="l--pad-v-quarter">
                    <label>
                        <spring:message code="jsp.Email"/>
                    </label>
                    <input type="text" class="full-width" readonly
                            name="data['email']"
                            value="${dataForm.data['email']}"/>
                </li>
                <li class="l--pad-v-quarter">
                    <c:forEach var="userRole" items="${dataForm.booleanData}">
                        <span class="l--push-right-1">
                            <input type="checkbox" id="chk-${userRole.key}"
                                    onchange="document.getElementById('${userRole.key}').value=this.checked"
                                    <c:if test="${! identity.isAdmin()}">disabled</c:if>
                                    <c:if test="${userRole.value}">checked</c:if>/>
                            <label class="label-none"
                                    for="chk-${userRole.key}">${userRole.key}</label>

                            <input type="hidden" id="${userRole.key}"
                                    value="${userRole.value}"
                                    name="booleanData['${userRole.key}']"/>
                        </span>
                    </c:forEach>
                </li>
                <li class="l--pad-v-half">
                    <label class="txt--small label-none">
                        <spring:message
                                code="jsp.MemberSince"/>: ${dataForm.data['joinedDate']}
                    </label>
                </li>
                <li class="l--pad-v-quarter txt--align-right">
                    <button class="button-primary">
                        <spring:message code="jsp.Update"/></button>
                    </button>
                </li>
            </ul>
        </li>
    </ul>
</form:form>
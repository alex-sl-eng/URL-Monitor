<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main class="l--pad-h-1">
    <sec:authentication property="principal.username" var="username"/>
    <h3><spring:message code="jsp.Settings"/></h3>

    <ul class="list-tab">
        <li>
            <input type="radio" name="tabs" id="tab1" checked>
            <label class="tab animated" for="tab1"><spring:message code="jsp.Profile"/></label>

            <div class="section l--pad-h-half l--pad-v-half">
                <ul class="list-h txt--small txt--small-spacing">
                    <li class="l--push-h-half">
                        <img src="${avatarService.getUserAvatar(username, 120)}"
                                title="${username}"/>
                    </li>
                    <li class="l--push-h-half">
                        <ul class="txt--align-left list-v list-no-bullet">
                            <li class="l--pad-v-quarter">
                                <label><spring:message code="jsp.Name"/></label>
                                <input type="text" class="full-width"
                                        value="${identity.getName()}"/>
                            </li>
                            <li class="l--pad-v-quarter">
                                <label><spring:message
                                        code="jsp.Email"/></label>
                                <input type="text" class="full-width"
                                        disabled="true"
                                        value="${identity.getEmail()}"/>
                            </li>
                            <li class="l--pad-v-quarter">
                                <label><spring:message
                                        code="jsp.Roles"/></label>
                                <input type="text" class="full-width"
                                        disabled="true"
                                        value="${identity.getRoles()}"/>
                            </li>
                            <li class="l--pad-v-quarter">
                                <label>
                                    <spring:message code="jsp.MemberSince"/>
                                </label>
                                <input type="text" class="full-width"
                                        disabled="true"
                                        value="${identity.getJoinedDate()}"/>
                            </li>
                            <li class="l--pad-v-quarter txt--align-right">
                                <button class="button-primary">
                                    <spring:message code="jsp.Update"/></button>
                                </button>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </li>
        <c:if test="${identity.isAdmin()}">
            <li>
                <input type="radio" name="tabs" id="tab2">
                <label class="tab animated" for="tab2"><spring:message code="jsp.System"/></label>

                <div class="section l--pad-h-half l--pad-v-half">
                    <a href="monitoring">Java Melody</a> <br/>
                    <a href="#">Stop all monitoring</a> <br/>
                    <a href="#">Resume all monitoring</a> <br/>
                    <label>Active tasks:10</label>
                </div>
            </li>
        </c:if>
    </ul>

</main>
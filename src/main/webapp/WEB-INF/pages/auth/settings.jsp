<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main class="l--pad-h-1">
    <h3><spring:message code="jsp.Settings"/></h3>

    <ul class="list-tab">
        <li>
            <input type="radio" name="tabs" id="tab1" checked>
            <label class="tab animated" for="tab1"><spring:message
                    code="jsp.Profile"/></label>

            <div class="section l--pad-h-half l--pad-v-half l--display-none">
                <jsp:include page="../view/profile_edit.jsp"/>
            </div>
        </li>
        <c:if test="${identity.isAdmin()}">
            <li>
                <input type="radio" name="tabs" id="tab2">
                <label class="tab animated" for="tab2"><spring:message
                        code="jsp.System"/></label>

                <div class="section l--pad-h-half l--pad-v-half l--display-none">
                    <div class="content-wrapper">
                        <a href="monitoring">Java Melody</a> <br/>
                        <a href="#">Stop all monitoring</a> <br/>
                        <a href="#">Resume all monitoring</a> <br/>
                        <label>Active tasks:10</label>
                    </div>
                </div>
            </li>
        </c:if>
    </ul>

</main>
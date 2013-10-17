<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="includes/header.jsp"/>

<main>
    <script type="text/javascript">
        $(document).ready(function () {
            refreshPageIntervalId =
                    setInterval(refreshPage, refreshPageInterval);
            $("#filter_text").trigger("change");
        });
    </script>

    <div class="l--push-v-half">
        <ul class="list-h txt--small">
            <li class="checkbox">
                <label>
                    <input type="checkbox" id="auto_refresh" value="true"
                            checked="checked"/>Auto refresh
                </label>
            <span id="refresh_status"
                    class="txt--smaller txt--smaller-spacing l--pad-h-quarter"></span>
            </li>
        </ul>
        <div class="content-wrapper-small">
            <input type="text" id="filter_text" title="Search by tag or name."
                    value="${filterText}" class="full-width input-large"/>
        </div>
    </div>
    <ul class="list-tab content-wrapper-small">
        <li>
            <input type="radio" name="tabs" id="tab_public" checked>
            <label class="tab animated" for="tab_public">
                <spring:message code="jsp.All"/>
            </label>

            <div class="section content  l--pad-h-half l--pad-v-half l--display-none">
                <jsp:include page="view/index_content.jsp"/>
            </div>
        </li>
        <sec:authorize access="isAuthenticated()">
            <li>
                <input type="radio" name="tabs" id="tab_private">
                <label class="tab animated" for="tab_private">
                    <spring:message code="jsp.MyMonitor"/>
                </label>

                <div class="section l--pad-h-half l--pad-v-half l--display-none">
                        <%--<jsp:include page="view/index_content.jsp"/>--%>
                </div>
            </li>
        </sec:authorize>
    </ul>
</main>

<jsp:include page="includes/footer.jsp"/>
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

    <div class="l--pad-v-half content-wrapper-small">
        <ul class="list-h txt--small">
            <li class="checkbox">
                <label>
                    <input type="checkbox" id="auto_refresh" value="true"
                            checked="checked"/>Auto refresh
                </label>
                <span id="refresh_status"
                        class="txt--smaller txt--smaller-spacing l--pad-h-quarter"></span>
            </li>
            <li class="l--float-right">
                <a href="action/new-monitor" class="button-icon button-primary"
                        title="Create new monitor">
                    <span class="icon-plus"></span>
                    <span class="l--push-bottom-eighth"><spring:message
                            code="jsp.CreateNewMonitor"/></span>
                </a>
            </li>
        </ul>
    </div>
    <div class="content-wrapper-small">
        <div class="section content l--pad-h-half l--pad-v-half">
            <jsp:include page="view/content.jsp"/>
        </div>
    </div>
</main>

<jsp:include page="includes/footer.jsp"/>
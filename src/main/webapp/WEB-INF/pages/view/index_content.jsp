<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${empty monitorList}">
    <h3><spring:message code="jsp.NoEntry"/></h3>
</c:if>
<c:if test="${not empty monitorList}">
    <c:forEach var="monitor" items="${monitorList}">
        <div id="${monitor.hashCode()}-container"
                class="l--pad-v-half l--pad-h-quarter section container ${monitor.status}">
            <ul class="list-h">
                <li>
                  <span class="status txt--large">
                      <img alt="Loading..." src="resources/images/loader.gif"/>
                  </span>
                </li>
                <li class="name">
                    <strong>
                        <a href="${monitor.url}"
                                title="${monitor.name}">${monitor.name}</a>
                    </strong>
                </li>
                <li class="time txt--smaller l--float-right">
                    <strong>
                        <span class="lastChecked">
                            <img alt="Loading..."
                                    src="resources/images/loader.gif"/>
                        </span>
                    </strong>
                    <a href='#' class="more-info icon-chevron-down"
                            title="More details"
                            onclick="toggleDetails(this, ${monitor.hashCode()})">
                    </a>
                </li>
            </ul>

            <div class="details txt--small l--pad-h-half">
                <div title="${monitor.url}" class="value l--push-v-half">
                    <a href="${monitor.url}">${monitor.url}</a>

                    <div>
                        <c:if test="${not empty monitor.description}">
                            ${monitor.description}
                        </c:if>
                        <c:if test="${empty monitor.description}">
                            <i><spring:message code="jsp.NoDescription"/></i>
                        </c:if>
                    </div>
                </div>

                <div title="${monitor.contentRegex}" class="value">
                    <strong><spring:message code="jsp.SearchText"/></strong>
                    <c:if test="${not empty monitor.contentRegex}">
                        "${monitor.contentRegex}"
                    </c:if>
                    <c:if test="${empty monitor.contentRegex}">
                        <i><spring:message code="jsp.NoPatternChecking"/></i>
                    </c:if>

                    <div>
                        Check every
                        <strong>
                                ${cronHelper.getTypeFromExpression(monitor.cron).display}.
                        </strong>

                        <strong><spring:message
                                code="jsp.LastFailed"/>:</strong>
                        <c:if test="${empty monitor.lastFailed}">
                            <i>None</i>
                        </c:if>
                        <c:if test="${not empty monitor.lastFailed}">
                            ${monitor.lastFailed}
                        </c:if>
                    </div>

                    <div>
                        <c:if test="${not empty monitor.tagList}">
                        <span title="${monitor.tagList}">
                                ${monitor.tagList}
                        </span>
                        </c:if>
                    </div>

                </div>


            </div>
        </div>
    </c:forEach>
</c:if>
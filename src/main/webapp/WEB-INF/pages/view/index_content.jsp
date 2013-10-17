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
                <li class="link l--pad-h-eighth l--pad-v-eighth icon-chevron-down txt--large"
                        title="More details"
                        onclick="toggleDetails(this, ${monitor.hashCode()})"/>
                <li>
                  <span class="status txt--large">
                      <img alt="Loading..." src="resources/images/loader.gif"/>
                  </span>
                </li>
                <li class="name">
                    <strong>
                        <a href="${monitor.url}"
                                title="${monitor.name}"
                                target="_blank">${monitor.name}</a>
                    </strong>
                </li>
                <li class="time txt--smaller l--float-right">
                    <ul class="list-h">
                        <li class="l--pad-h-eighth l--pad-v-eighth">
                            <strong>
                                <span class="lastChecked">
                                    <img alt="Loading..."
                                            src="resources/images/loader.gif"/>
                                </span>
                            </strong>
                        </li>
                        <li class="link l--pad-h-eighth l--pad-v-eighth icon-menu txt--larger hover-toggle">
                            <ul class="hover-menu">
                                <li>
                                    <a class="l--pad-v-half l--pad-h-half">Share</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>

            <ul class="list-no-bullet details txt--small l--push-h-quarter">
                <li class="value l--push-v-half">
                    <a href="${monitor.url}"
                            title="${monitor.url}"
                            target="_blank">${monitor.url}</a>

                    <div>
                        <c:if test="${not empty monitor.description}">
                            ${monitor.description}
                        </c:if>
                        <c:if test="${empty monitor.description}">
                            <i><spring:message code="jsp.NoDescription"/></i>
                        </c:if>
                    </div>
                    <c:if test="${not empty monitor.tagList}">
                        <div class="txt--small">
                            <span title="${monitor.tagList}">
                                    ${monitor.tagList}
                            </span>
                        </div>
                    </c:if>
                </li>

                <li class="value">
                    <p>
                        <spring:message code="jsp.SearchText"/>
                        <strong>
                            <c:if test="${not empty monitor.contentRegex}">
                                "${monitor.contentRegex}".
                            </c:if>
                            <c:if test="${empty monitor.contentRegex}">
                                <i><spring:message
                                        code="jsp.NoPatternChecking"/>.</i>
                            </c:if>
                        </strong>
                        Check every
                        <strong>${cronHelper.getTypeFromExpression(monitor.cron).display}.</strong>
                        <spring:message code="jsp.LastFailed"/>:
                        <strong>
                            <c:if test="${empty monitor.lastFailed}">
                                <i>None</i>
                            </c:if>
                            <c:if test="${not empty monitor.lastFailed}">
                                ${monitor.lastFailed}
                            </c:if>
                        </strong>
                    </p>
                </li>
            </ul>
        </div>
    </c:forEach>
</c:if>
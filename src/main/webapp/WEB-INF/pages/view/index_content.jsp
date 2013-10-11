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

            <div class="details">
                <table class="txt--small l--pad-v-quarter l--pad-h-quarter full-width">
                    <tr>
                        <td>
                            <strong>
                                <spring:message
                                        code="monitor.details.Description"/>
                            </strong>
                        </td>
                        <td>
                            <c:if test="${not empty monitor.description}">
                                ${monitor.description}
                            </c:if>
                            <c:if test="${empty monitor.description}">
                                <i>No description</i>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong>
                                <spring:message code="monitor.details.Url"/>
                            </strong>
                        </td>
                        <td>
                            <span class="value full-width" title="${monitor.url}">
                                <a href="${monitor.url}">${monitor.url}</a>
                            </span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <strong>
                                <spring:message
                                        code="monitor.details.LastFailed"/>
                            </strong>
                        </td>
                        <td class="lastFailed">
                            None
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong>
                                <spring:message
                                        code="monitor.details.SearchText"/>
                            </strong>
                        </td>
                        <td>
                         <span class="value full-width" title="${monitor.contentRegex}">
                             <c:if test="${not empty monitor.contentRegex}">
                                 ${monitor.contentRegex}
                             </c:if>
                             <c:if test="${empty monitor.contentRegex}">
                                 <i>No pattern checking</i>
                             </c:if>
                         </span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong>
                                <spring:message
                                        code="monitor.details.Interval"/>
                            </strong>
                        </td>
                        <td>
                          <span class="value full-width"
                                  title="${cronHelper.getTypeFromExpression(monitor.cron).display}">
                                  ${cronHelper.getTypeFromExpression(monitor.cron).display}
                          </span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong>
                                <spring:message code="monitor.details.Tag"/>
                            </strong>
                        </td>
                        <td>
                            <c:if test="${not empty monitor.tagList}">
                                <span class="value full-width"
                                        title="${monitor.tagList}">${monitor.tagList}
                                </span>
                            </c:if>
                            <c:if test="${empty monitor.tagList}">
                                <i>No tag</i>
                            </c:if>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </c:forEach>
</c:if>
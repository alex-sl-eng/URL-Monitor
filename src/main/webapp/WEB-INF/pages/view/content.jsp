<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${empty monitorList}">
  <h3><spring:message code="message.noEntry"/></h3>
</c:if>
<c:if test="${not empty monitorList}">
  <c:forEach var="monitor" items="${monitorList}">
    <div id="${monitor.hashCode()}-container"
      class="pad-v-half pad-h-quarter section container ${monitor.status}">
      <ul class="list-h">
        <li>
          <span class="status">
              <img alt="Loading..." src="resources/images/loader.gif"/>
          </span>
        </li>
        <li class="name">
          <a href="${monitor.url}" title="${monitor.name}">${monitor.name}</a>
        </li>
        <li class="time right">
          <span class="lastChecked">
              <img alt="Loading..." src="resources/images/loader.gif"/>
          </span>
          <a href='#' class="more-info icon-chevron-down" title="More details"
            onclick="toggleDetails(this, ${monitor.hashCode()})"></a>
        </li>
      </ul>

      <div class="details">
        <table class="small">
          <tr>
            <td class="header"><spring:message
              code="monitor.details.Description"/></td>
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
            <td class="header"><spring:message code="monitor.details.Url"/></td>
            <td><span class="value" title="${monitor.url}"><a
              href="${monitor.url}">${monitor.url}</a></span></td>
          </tr>

          <tr>
            <td class="header"><spring:message
              code="monitor.details.LastFailed"/></td>
            <td class="lastFailed">
              None
            </td>
          </tr>


          <tr>
            <td class="header"><spring:message
              code="monitor.details.SearchText"/></td>
            <td>
             <span class="value" title="${monitor.contentRegex}">
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
            <td class="header"><spring:message
              code="monitor.details.Interval"/></td>
            <td>
              <span class="value"
                title="${cronHelper.getTypeFromExpression(monitor.cron).display}">${cronHelper.getTypeFromExpression(monitor.cron).display}</span>
            </td>
          </tr>
          <tr>
            <td class="header"><spring:message code="monitor.details.Tag"/></td>
            <td>
              <c:if test="${not empty monitor.tagList}">
                <span class="value"
                  title="${monitor.tagList}">${monitor.tagList}</span>
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
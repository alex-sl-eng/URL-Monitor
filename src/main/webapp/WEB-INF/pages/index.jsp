<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp" />

<main>
<div class="filter">
	<ul class="horizontal">
		<li style="width: 50%"><input type="text" /></li>
		<li><select>
				<option value="name"><spring:message code="sort.name"/></option>
				<option value="status"><spring:message code="sort.status"/></option>
		</select></li>
		<li>
			<button class="icon-list selected" id="list" onclick="setView('list')"></button>
		</li>

		<li>
			<button class="icon-grid" id="grid" onclick="setView('grid')"></button>
		</li>
	</ul>
</div>
<div class="content">

	<script type="text/javascript">
	    setInterval(refreshPage, 1000);
	</script>

	<c:forEach var="monitor" items="${monitorList}">
		<div id="${monitor.hashCode()}-container" class="container ${monitor.statusValue}">
			<c:if test="${monitor.statusValue == 'Pass'}">
				<span id="${monitor.hashCode()}-status" class="status icon-checkmark"></span> 
			</c:if>
			<c:if test="${monitor.statusValue == 'Failed'}">
				<span id="${monitor.hashCode()}-status" class="status icon-close"></span> 
			</c:if>
			<c:if test="${monitor.statusValue == 'Unknown'}">
				<span id="${monitor.hashCode()}-status" class="status icon-question"></span> 
			</c:if>
			
			<span class="name">
				<a href="${monitor.url}}">${monitor.name}</a>
			</span>
			
			<span id="${monitor.hashCode()}-lastCheck" class="time right"><fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss"  value="${monitor.lastCheck}"/>
				<a href='#' class="more-info icon-chevron-down" onclick="toggleDetails(this, ${monitor.hashCode()} + '-details')"></a>
			</span>
			<div id="${monitor.hashCode()}-details" class="details">
				<table>
					<tr>
						<td class="header"><spring:message code="monitor.details.Description"/></td>
						<td class="value"><span>${monitor.description}</span></td>
					</tr>
					<tr>
						<td class="header"><spring:message code="monitor.details.Url"/></td>
						<td class="value"><a href="${monitor.url}">${monitor.url}</a></td>
					</tr>
					<tr>
						<td class="header"><spring:message code="monitor.details.SearchText"/></td>
						<td class="value">${monitor.contentRegex}</td>
					</tr>
					<tr>
						<td class="header"><spring:message code="monitor.details.CronExpression"/></td>
						<td class="value">${monitor.cronExpression}</td>
					</tr>
					<tr>
						<td class="header"><spring:message code="monitor.details.Tag"/></td>
						<td class="value">${monitor.tag}</td>
					</tr>
				</table>
			</div>
		</div>
	</c:forEach>
</div>
</main>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp" />

<main>
	<script type="text/javascript">
		$(document).ready(function() {
			refreshPageIntervalId = setInterval(refreshPage, refreshPageInterval);
		});
	</script>
	
	<span class="small">
       <input type="checkbox" id="auto_refresh" value="true" checked="checked"/>Auto refresh
       <span id="refresh_status"></span>
    </span>
	
	<div class="filter content-wrapper">
		<ul class="list-h">
			<li style="width: 50%"><input type="text" id="filter_text" title="Search by tag or name."/></li>
			<li>
				<button class="icon-list selected" id="list" title="List view"></button>
			</li>
	
			<li>
				<button class="icon-grid" id="grid" title="Grid view"></button>
			</li>
		</ul>
	</div>
	
	<div class="content content-wrapper">
	   <jsp:include page="view/content.jsp" />	
	</div>
</main>

<jsp:include page="includes/footer.jsp" />
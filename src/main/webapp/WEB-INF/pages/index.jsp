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
	
	<div class="filter">
		<ul class="horizontal">
			<li style="width: 50%"><input type="text" id="filter_text"/></li>
			<li>
				<button class="icon-list selected" id="list"></button>
			</li>
	
			<li>
				<button class="icon-grid" id="grid"></button>
			</li>
		</ul>
	</div>
	
	<div class="content">
	   <jsp:include page="view/content.jsp" />	
	</div>
</main>
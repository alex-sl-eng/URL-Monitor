<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="includes/header.jsp"/>

<main>
  <script type="text/javascript">
    $(document).ready(function () {
      refreshPageIntervalId = setInterval(refreshPage, refreshPageInterval);
      $("#filter_text").trigger("change");
    });
  </script>

    <span class="txt--small">
    <input title="Refresh page every 30 seconds" type="checkbox" id="auto_refresh" value="true" checked="checked"/>Auto refresh
    <span id="refresh_status"></span>
    </span>

  <div class="filter content-wrapper">
    <ul class="list-h">
      <li style="width: 50%"><input type="text" id="filter_text" title="Search by tag or name." value="${filterText}" class="full-width"/></li>
      <li>
        <button class="icon-list selected" id="list-view" title="List view"></button>
        <button class="icon-grid" id="grid-view" title="Grid view"></button>
      </li>
    </ul>
  </div>

  <div class="content content-wrapper">
    <jsp:include page="view/index_content.jsp"/>
  </div>
</main>

<jsp:include page="includes/footer.jsp"/>
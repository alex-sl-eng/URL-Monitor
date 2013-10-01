<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="../includes/header.jsp"/>

<main>
  <sec:authentication property="principal.username" var="username"/>
  <div class="content-wrapper-small">
    <h3><spring:message code="jsp.UpdateProfile"/></h3>

    <form class="section pad-v-half pad-h-half">
      <ul class="list-no-bullet">
        <li class="pad-v-quarter align-center">
          <img src="${avatarService.getUserAvatar(username, 100)}"
            title="${username}"/>
        </li>
        <li class="pad-v-quarter">
          <label>Name</label><input type="text" class="full-width"
          value="${identity.getName()}"/>
        </li>
        <li class="pad-v-quarter">
          <label>Email</label>
          <input type="text" class="full-width" disabled="true"
            value="${identity.getEmail()}"/>
        </li>
        <li class="pad-v-quarter">
          <label>Roles</label><input type="text" class="full-width"
          disabled="true" value="${identity.getRoles()}"/>
        </li>
        <li class="pad-v-quarter">
          <label>Member Since</label><input type="text" class="full-width"
          disabled="true" value="${identity.getJoinedDate()}"/>
        </li>
        <li class="pad-v-quarter align-right">
          <button class="button-primary">Update</button>
          <button onclick="document.location='/home';return false;">Cancel
          </button>
        </li>
      </ul>

    </form>
  </div>
</main>
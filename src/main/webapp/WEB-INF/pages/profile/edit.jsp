<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main>
  <sec:authentication property="principal.username" var="username"/>
  <div class="content-wrapper-small">
    <h3>Edit Profile</h3>
    <form class="section pad-v-half pad-h-half">
      <ul class="list-no-bullet">
        <li class="pad-v-quarter align-center">
            <img src="${avatarService.getUserAvatar(username, 100)}" title="${username}"/>
        </li>
        <li class="pad-v-quarter">
          <label>Name</label><input type="text" class="full-width"/>
        </li>
        <li class="pad-v-quarter">
          <label>Email</label>
          <input type="text" class="full-width" disabled="true" value="${username}"/>
        </li>
        <li class="pad-v-quarter">
          <label>Roles</label><input type="text" class="full-width" disabled="true"/>
        </li>
        <li class="pad-v-quarter">
          <label>Member Since</label><input type="text" class="full-width" disabled="true"/>
        </li>
        <li class="pad-v-quarter align-right">
          <button class="button-primary">Update</button><button class="">Cancel</button>
        </li>
      </ul>

    </form>
  </div>
</main>
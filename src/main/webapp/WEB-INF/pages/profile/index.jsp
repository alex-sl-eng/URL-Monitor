<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main>
  <sec:authentication property="principal.username" var="username"/>
  <div class="content-wrapper">
    <h3>${username}</h3><button class="right button-primary">Edit Profile</button>
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
          <label class="label-highlight">Member Since</label>
        </li>
      </ul>

    </form>
  </div>
</main>
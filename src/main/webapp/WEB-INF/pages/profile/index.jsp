<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main>
  <sec:authentication property="principal.username" var="username"/>
  <c:set var="name" value="Alex Eng"/>
  <c:set var="joinedDate" value="12/12/2013"/>
  <div class="content-wrapper-small">
    <ul class="list-h section push-v-1 pad-v-1 pad-h-1 align-center">
      <li class="push-h-half">
        <img src="${avatarService.getUserAvatar(username, 120)}"
          title="${username}"/>
      </li>
      <li class="push-h-half">
        <ul class="align-left list-v list-no-bullet">
          <li><label class="push-h-quarter">Email</label>${username}</li>
          <li><label class="push-h-quarter">Name</label>${name}</li>
          <li><label class="push-h-quarter">Member since</label>${joinedDate}
          </li>
          <li>
            <button class="button-primary" onclick="">Update Profile</button>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</main>
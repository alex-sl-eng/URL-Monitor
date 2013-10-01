<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main>
  <sec:authentication property="principal.username" var="username"/>

  <div class="content-wrapper-small">
    <ul
      class="list-h section push-v-1 pad-v-1 pad-h-1 align-center small small-spacing">
      <li class="push-h-half">
        <img src="${avatarService.getUserAvatar(username, 120)}"
          title="${username}"/>
      </li>
      <li class="push-h-half">
        <ul class="align-left list-v list-no-bullet">
          <li><label class="push-h-quarter">Email</label>${username}</li>
          <li><label class="push-h-quarter">Name</label>${identity.getName()}
          </li>
          <li><label class="push-h-quarter">Roles</label>${identity.getRoles()}
          </li>
          <li><label class="push-h-quarter">Member
            since</label>${identity.getJoinedDate()}
          </li>
          <li>
            <button class="button-primary"
              onclick="document.location='profile/edit';return false;"><spring:message code="jsp.UpdateProfile"/></button>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</main>
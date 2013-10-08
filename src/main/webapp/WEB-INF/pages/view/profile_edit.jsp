<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sec:authentication property="principal.username" var="username"/>

<form:form method="post" action="auth/settings/profile"
  commandName="profileForm">
  <ul class="list-h txt--small txt--small-spacing">
    <li class="l--push-h-half">
      <img src="${avatarServiceImpl.getUserAvatar(username, 120)}"
        title="${username}"/>
    </li>
    <li class="l--push-h-half">
      <ul class="txt--align-left list-v list-no-bullet">
        <li class="l--pad-v-quarter">
          <label><spring:message code="jsp.Name"/></label>
          <form:errors path="name" cssClass="l--pad-h-eighth l--pad-v-eighth error"/>
          <span id="name_error" class="l--pad-h-eighth l--pad-v-eighth l--display-none error"></span>
          <form:input path="name" class="full-width" maxlength="255" onblur="validateName(this.value)"/>
        </li>
        <li class="l--pad-v-quarter">
          <label>
            <spring:message code="jsp.Email"/>
          </label>
          <form:input path="email" class="full-width" readonly="true" tabindex="-1"/>
        </li>
        <li class="l--pad-v-quarter">
          <span class="l--push-right-1">
            <c:choose>
              <c:when test="${identity.isAdmin()}">
                <form:checkbox path="user" id="chk-isUser"/>
              </c:when>
              <c:otherwise>
                <form:checkbox path="user" id="chk-isUser" disabled="true"/>
              </c:otherwise>
            </c:choose>
            <label class="label-none" for="chk-isUser">User</label>
          </span>

          <span class="l--push-right-1">
            <c:choose>
              <c:when test="${identity.isAdmin()}">
                <form:checkbox path="admin" id="chk-isAdmin"/>
              </c:when>
              <c:otherwise>
                <form:checkbox path="admin" id="chk-isAdmin" disabled="true"/>
              </c:otherwise>
            </c:choose>
            <label class="label-none" for="chk-isAdmin">Admin</label>
          </span>
        </li>
        <li class="l--pad-v-half">
          <label class="txt--small label-none">
            <spring:message code="jsp.MemberSince"/>:
          </label>
          <form:label path="joinedDate"
            cssClass="label-none txt-small">${profileForm.joinedDate}</form:label>
        </li>
        <li class="l--pad-v-quarter txt--align-right">
          <button class="button-primary">
            <spring:message code="jsp.Update"/></button>
          </button>
        </li>
      </ul>
    </li>
  </ul>
</form:form>
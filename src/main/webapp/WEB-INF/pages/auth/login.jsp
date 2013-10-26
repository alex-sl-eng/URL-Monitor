<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main>
  <div class="content-wrapper-smaller">
    <spring:message code="jsp.Login" var="login"/>
    <h3>${login}</h3>
    <c:url value="/j_spring_openid_security_check" var="openIDLoginUrl"/>

    <ul class="list-no-bullet l--pad-v-half l--pad-h-half section">
      <li class="l--push-v-half l--pad-h-quarter button" onclick="googleForm.submit();">
        <form action="${openIDLoginUrl}" method="post" id="googleForm">
          <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id">
          <span class="icon-google-plus txt--huge"></span>
          <span class="l--push-h-half txt--large">Google</span>
        </form>
      </li>

      <li class="l--push-v-half l--pad-h-quarter button" onclick="yahooForm.submit();">
        <form action="${openIDLoginUrl}" method="post" id="yahooForm">
          <input name="openid_identifier" type="hidden" value="https://me.yahoo.com">
          <span class="icon-yahoo txt--huge"></span>
          <span class="l--push-h-half txt--large">Yahoo</span>
        </form>
      </li>
      <li class="l--push-v-half">
        <form action="${openIDLoginUrl}" method="post">
          <label for="openid_identifier">OpenID</label>
          <input id="openid_identifier" name="openid_identifier" type="text" maxlength="100" class="l--w-full">
          <input type="submit" value="${login}" class="button-primary"/>
        </form>
      </li>
    </ul>
  </div>
</main>

<jsp:include page="../includes/footer.jsp"/>
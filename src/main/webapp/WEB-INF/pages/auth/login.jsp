<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="../includes/header.jsp"/>

<main>
  <div class="content-wrapper-small">
    <spring:message code="jsp.Login" var="login"/>
    <h3>${login}</h3>
    <c:url value="/j_spring_openid_security_check" var="openIDLoginUrl"/>

    <ul class="list-no-bullet pad-v-half pad-h-half section">
      <li class="push-v-half pad-h-quarter button button-primary" onclick="googleForm.submit();">
        <form action="${openIDLoginUrl}" method="post" id="googleForm">
          <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id">
          <span class="icon-google-plus huge"></span>
          <span class="push-h-half large">Google</span>
        </form>
      </li>

      <li class="push-v-half pad-h-quarter button button-primary" onclick="yahooForm.submit();">
        <form action="${openIDLoginUrl}" method="post" id="yahooForm">
          <input name="openid_identifier" type="hidden" value="https://me.yahoo.com">
          <span class="icon-yahoo huge"></span>
          <span class="push-h-half large">Yahoo</span>
        </form>
      </li>
      <li class="push-v-half">
        <form action="${openIDLoginUrl}" method="post">
          <label for="openid_identifier">OpenID</label>
          <input id="openid_identifier" name="openid_identifier" type="text" maxlength="100" class="full-width large">
          <input type="submit" value="${login}" class="button-primary"/>
        </form>
      </li>
    </ul>
  </div>
</main>

<jsp:include page="../includes/footer.jsp"/>
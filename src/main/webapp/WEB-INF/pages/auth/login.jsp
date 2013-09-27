<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../includes/header.jsp"/>

<main>
  <div class="content-wrapper-small">
    <h3>Login/Sign Up</h3>
    <c:url value="/j_spring_openid_security_check" var="openIDLoginUrl"/>

    <ul class="list-no-bullet pad-v-half pad-h-half section">
      <li class="push-v-half pad-h-quarter button" onclick="googleForm.submit();">
        <a href="#">
          <form action="${openIDLoginUrl}" method="post" id="googleForm">
            <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id">
            <span class="icon-google-plus huge"></span>
            <span class="push-h-half large">Google</span>
          </form>
        </a>
      </li>

      <li class="push-v-half pad-h-quarter button" onclick="yahooForm.submit();">
        <a href="#">
          <form action="${openIDLoginUrl}" method="post" id="yahooForm">
            <input name="openid_identifier" type="hidden" value="https://me.yahoo.com">
            <span class="icon-yahoo huge"></span>
            <span class="push-h-half large">Yahoo</span>
          </form>
        </a>
      </li>
      <li class="push-v-half">
        <form action="${openIDLoginUrl}" method="post">
          <label for="openid_identifier">OpenID</label>
          <input id="openid_identifier" name="openid_identifier" type="text" maxlength="100" class="full_width large">
          <input type="submit" value="Go">
        </form>
      </li>
    </ul>
  </div>
</main>

<jsp:include page="../includes/footer.jsp"/>
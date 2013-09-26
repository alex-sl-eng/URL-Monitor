<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../includes/header.jsp"/>

<main>
  <div id="login-error">
    ${error}
  </div>

  <form action="${openIDLoginUrl}" method="post">
    For Google users:
    <input name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id">
    <input type="submit" value="Sign with Google">
  </form>

  <c:url value="/j_spring_openid_security_check" var="openIDLoginUrl">
    <form action="${openIDLoginUrl}" method="post">
      <label for="openid_identifier">OpenID Login</label>:
      <input id="openid_identifier" name="openid_identifier" type="text" maxlength="100">
      <input type="submit" value="Login">
    </form>
  </c:url>
</main>

<jsp:include page="../includes/footer.jsp"/>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp"/>

<main>
  <ul class="small list-no-bullet list-v content-wrapper">
    <li>
      <span class="label-highlight">Build</span>
      <span><c:out value="${buildDate}"/></span>
      <span class="label-highlight">Version</span>
      <span><c:out value="${buildVersion}"/></span>
    </li>
    <li>
      <span class="label-highlight">Contact</span>
      <span><a href="mailto:${replyTo}">${replyTo}</a></span>
    </li>
    <li>
      <span class="label-highlight">Social</span>
      <span>
        <a href="https://twitter.com/UrlmonitorOrg" class="twitter-follow-button" data-show-count="false" data-lang="en">Follow @urlmonitor</a>
        <script>!function (d, s, id) {
          var js, fjs = d.getElementsByTagName(s)[0];
          if (!d.getElementById(id)) {
            js = d.createElement(s);
            js.id = id;
            js.src = "//platform.twitter.com/widgets.js";
            fjs.parentNode.insertBefore(js, fjs);
          }
        }(document, "script", "twitter-wjs");</script>


        <a href="https://twitter.com/share" class="twitter-share-button" data-lang="en" data-count="none">Tweet</a>
        <script>!function (d, s, id) {
          var js, fjs = d.getElementsByTagName(s)[0];
          if (!d.getElementById(id)) {
            js = d.createElement(s);
            js.id = id;
            js.src = "https://platform.twitter.com/widgets.js";
            fjs.parentNode.insertBefore(js, fjs);
          }
        }(document, "script", "twitter-wjs");</script>
      </span>
    </li>
    <li>
      <span class="label-highlight">Project</span>
        <span>
          <a href="https://github.com/aeng/URL-Monitor" target="_blank">
            GitHub
          </a>
        </span>
        <span>
          <iframe src="http://ghbtns.com/github-btn.html?user=aeng&repo=url-monitor&type=watch"
                  allowtransparency="true" frameborder="0" scrolling="0" width="62" height="20"></iframe>
        </span>
    </li>
    <li>
      <span class="label-highlight">License</span>
      <span><a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License, Version 2.0</a></span>
    </li>
  </ul>

</main>

<jsp:include page="includes/footer.jsp"/>
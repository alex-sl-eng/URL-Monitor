<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp"/>

<main>
  <ul class="small list-no-bullet list-v content-wrapper">
    <h3>About</h3>
    <li class="push-v-half">
      <span class="label-highlight">Build</span>
      <span><c:out value="${buildDate}"/></span>
      <span class="label-highlight">Version</span>
      <span><c:out value="${buildVersion}"/></span>
    </li>
    <li class="push-v-half">
      <span class="label-highlight">Contact</span>
      <span><a href="mailto:${replyTo}">${replyTo}</a></span>
    </li>
    <li class="push-v-half">
      <span class="label-highlight">Project</span>
        <span>
          <a href="https://github.com/aeng/URL-Monitor" target="_blank">GitHub</a>
        </span>
        <span>
          <iframe src="http://ghbtns.com/github-btn.html?user=aeng&repo=url-monitor&type=watch"
            allowtransparency="true" frameborder="0" scrolling="0" width="62" height="20"></iframe>
        </span>
    </li>
    <li class="push-v-half">
      <span class="label-highlight">License</span>
      <span><a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License, Version 2.0</a></span>
    </li>
    <li class="push-v-half">
      <span class="label-highlight">Share</span>
      <span class="push-h-quarter">
        <a href="https://twitter.com/UrlMonitorOrg" class="twitter-follow-button" data-show-count="false" data-show-screen-name="false">Follow
          @UrlMonitorOrg</a>
        <script>!function (d, s, id) {
          var js, fjs = d.getElementsByTagName(s)[0], p = /^http:/.test(d.location) ? 'http' : 'https';
          if (!d.getElementById(id)) {
            js = d.createElement(s);
            js.id = id;
            js.src = p + '://platform.twitter.com/widgets.js';
            fjs.parentNode.insertBefore(js, fjs);
          }
        }(document, 'script', 'twitter-wjs');
        </script>
      </span>

      <span class="push-h-quarter">
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

      <span class="push-h-quarter">
        <script src="//platform.linkedin.com/in.js" type="text/javascript">
          lang: en_US
        </script>
        <script type="IN/Share"></script>
      </span>

      <span class="push-h-quarter">
        <!-- Place this tag where you want the share button to render. -->
        <div class="g-plus" data-action="share" data-annotation="none"></div>
        <!-- Place this tag after the last share tag. -->
        <script type="text/javascript">
          (function () {
            var po = document.createElement('script');
            po.type = 'text/javascript';
            po.async = true;
            po.src = 'https://apis.google.com/js/plusone.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(po, s);
          })();
        </script>
      </span>

      <span class="push-h-quarter">
        <a href="http://www.reddit.com/submit" onclick="window.location = 'http://www.reddit.com/submit?url=' + encodeURIComponent(window.location); return false">
          <img src="http://www.reddit.com/static/spreddit7.gif" alt="submit to reddit" border="0" />
        </a>
      </span>
    </li>
  </ul>

</main>

<jsp:include page="includes/footer.jsp"/>
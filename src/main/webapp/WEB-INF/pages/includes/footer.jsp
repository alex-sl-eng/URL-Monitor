<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<footer>
  <c:if test="${fn:contains(pageContext.request.contextPath, 'live-urlmonitor.rhcloud.com')}">
    <script>
      (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
          (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o), m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
      })(window, document, 'script',
          '//www.google-analytics.com/analytics.js', 'ga');

      ga('create', 'UA-43451548-1', 'live-urlmonitor.rhcloud.com');
      ga('send', 'pageview');
    </script>
  </c:if>
</footer>
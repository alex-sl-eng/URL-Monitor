<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp" />

<main>
    <ul class="small list-no-bullet list-v">
        <li>
			<span class="label">Version</span>
			<span><c:out value="${buildVersion}"/></span>
			<span class="label">Build</span>
            <span><c:out value="${buildDate}"/></span>
        </li>
        <li>
            <span class="label">Contact</span> 
            <span><a href="mailto:${replyTo}">${replyTo}</a></span>  
        </li>
        <li>
            <span class="label">Project Information</span> 
            <span>
                <a href="https://bitbucket.org/aeng/url-monitor/wiki/Home" target="_blank">
                    Project Wiki Page
                </a>
            </span>
        </li>
        <li>
            <span class="label">Release License</span>
            <span><a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License, Version 2.0</a></span>
            
            <p>
                Copyright © 2013 Alex Eng 
            </p>
            <p>
                Licensed under the Apache License, Version 2.0 (the "License"); <br/>
                you may not use this file except in compliance with the License.<br/>
                You may obtain a copy of the License at
            </p>
            <p>
	           <a href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a>
            </p>
            <p>
                Unless required by applicable law or agreed to in writing, software<br/>
                distributed under the License is distributed on an "AS IS" BASIS,<br/>
                WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br/>
                See the License for the specific language governing permissions and<br/>
                limitations under the License.
            </p>
        </li>
    </ul>
	
</main>

<jsp:include page="includes/footer.jsp" />
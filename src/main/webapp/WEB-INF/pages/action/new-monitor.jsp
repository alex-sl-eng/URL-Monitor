<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="../includes/header.jsp"/>

<main>

    <div class="content-wrapper-small">
        <h3>Create new monitor</h3>
        <form:form method="post" action="action/new-monitor"
                commandName="monitorForm"
                cssClass="section l--pad-v-half l--pad-h-half">
            <ul class="txt--align-left list-no-bullet">
                <li class="l--pad-v-quarter">
                    <div class="input-group">
                        <label for="name" class="form-label label-highlight">
                            <spring:message code="jsp.Name"/>*
                        </label>
                        <form:input id="name" path="name" class="form-control"
                                maxlength="100"
                                onblur="validateName(this.value)"/>
                    </div>
                    <form:errors path="name"
                            cssClass="l--pad-h-eighth l--pad-v-eighth error"/>
                    <span id="name_error"
                            class="l--pad-h-eighth l--pad-v-eighth l--display-none error"></span>
                </li>
                <li class="l--pad-v-quarter">
                    <div class="input-group">
                        <label for="description"
                                class="form-label label-highlight">
                            <spring:message code="jsp.Description"/>
                        </label>
                        <form:input id="description" path="description"
                                class="form-control"
                                maxlength="255"/>
                    </div>
                </li>
                <li class="l--pad-v-quarter">
                    <div class="input-group">
                        <label for="url" class="form-label label-highlight">
                            <spring:message code="jsp.Url"/>*
                        </label>
                        <form:input id="url" path="url" class="form-control"
                                maxlength="2083"
                                onblur="validateUrl(this.value)"/>
                    </div>
                    <form:errors path="url"
                            cssClass="l--pad-h-eighth l--pad-v-eighth error"/>
                    <span id="url_error"
                            class="l--pad-h-eighth l--pad-v-eighth l--display-none error">
                    </span>
                </li>
                <li class="l--pad-v-quarter">
                    <ul class="list-h">
                        <li class="l--w-1-2">
                            <div class="input-group">
                                <label for="interval"
                                        class="form-label label-highlight">
                                    <spring:message code="jsp.CheckEvery"/>
                                </label>
                                <form:select path="cron" id="interval"
                                        cssClass="form-control">
                                    <form:option value="1 Minute"
                                            label="1 Minute"/>
                                    <form:option value="5 Minutes"
                                            label="5 Minutes"/>
                                    <form:option value="10 Minutes"
                                            label="10 Minutes"/>
                                    <form:option value="15 Minutes"
                                            label="15 Minutes"/>
                                </form:select>
                            </div>
                        </li>
                        <li class="l--w-1-2">
                            <div class="input-group">
                                <label for="visibility"
                                        class="form-label label-highlight">
                                    <spring:message code="jsp.Visibility"/>
                                </label>

                                <div class="group-control" id="visibility">
                                    <label for="chk-isPublic">
                                        <spring:message code="jsp.Public"/>

                                    <form:radiobutton path="visibility"
                                            id="chk-isPublic"
                                            value="Public"/>
                                    </label>

                                    <label for="chk-isPrivate">
                                        <spring:message code="jsp.Private"/>

                                    <form:radiobutton path="visibility"
                                            id="chk-isPrivate"
                                            value="Private"/>
                                    </label>
                                </div>

                            </div>
                        </li>
                    </ul>
                </li>

                <li class="l--pad-v-quarter">
                    <div class="input-group">
                        <label for="contentRegex"
                                class="form-label label-highlight">
                            <spring:message code="jsp.ContentToSearch"/>
                        </label>
                        <form:textarea id="contentRegex" path="contentRegex"
                                cssClass="form-control" rows="3"/>
                    </div>
                </li>
                <li class="l--pad-v-quarter">
                    <ul class="list-h">
                        <li class="l--w-1-2">
                            <div class="input-group">
                                <label for="tag"
                                        class="form-label label-highlight">
                                    <spring:message code="jsp.Tag"/>
                                </label>
                                <form:input path="tag" id="tag" maxlength="255"
                                        cssClass="form-control"/>
                            </div>
                        </li>
                        <li class="l--w-1-2">
                            <div class="input-group">
                                <label for="email"
                                        class="form-label label-highlight">
                                    <spring:message code="jsp.EmailList"/>
                                </label>
                                <form:input path="emailToList" id="email"
                                        maxlength="255"
                                        cssClass="form-control"
                                        onblur="validateEmailList(this.value)"/>
                            </div>
                            <form:errors path="emailToList"
                                    cssClass="l--pad-h-eighth l--pad-v-eighth error"/>
                            <span id="emailList_error"
                                    class="l--pad-h-eighth l--pad-v-eighth l--display-none error">
                            </span>
                        </li>
                    </ul>
                </li>
                <li class="l--pad-v-quarter txt--align-right">
                    <button class="button-primary">
                        <spring:message code="jsp.Save"/></button>
                    </button>
                    <button>
                        <spring:message code="jsp.Cancel"/></button>
                    </button>
                </li>
            </ul>
        </form:form>
    </div>
</main>
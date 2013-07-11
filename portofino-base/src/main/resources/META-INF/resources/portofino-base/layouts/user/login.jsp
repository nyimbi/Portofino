<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@ taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<stripes:layout-render name="/skins/${skin}/login.jsp">
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.user.LoginAction"/>
    <stripes:layout-component name="loginBody">
        <stripes:form action="<%= actionBean.getOriginalPath() %>" method="post">
            <input type="text" name="userName" id="userName" class="input-block-level"
                   placeholder="<fmt:message key='skins.default.login.username'/>" />
            <input type="password" name="pwd" id="pwd" class="input-block-level"
                   placeholder="<fmt:message key='skins.default.login.password'/>" />
            <div class="login-buttons">
                <button type="submit" name="login" class="btn btn-primary">Log in</button>
            </div>
            <stripes:hidden name="returnUrl"/>
            <stripes:hidden name="cancelReturnUrl"/>
        </stripes:form>
        <div class="login-links spacingWithDividerTop">
            <div class="login-link">
                <stripes:link href="<%= actionBean.getOriginalPath() %>">
                    <stripes:param name="forgotPassword" value=""/>
                    <stripes:param name="returnUrl" value="${actionBean.returnUrl}"/>
                    <stripes:param name="cancelReturnUrl" value="${actionBean.cancelReturnUrl}"/>
                    <fmt:message key='skins.default.login.forgot.your.password'/>
                </stripes:link>
            </div>
            <div class="login-link">
                Don't have an account?
                <stripes:link href="<%= actionBean.getOriginalPath() %>">
                    <stripes:param name="signUp" value=""/>
                    <stripes:param name="returnUrl"/>
                    <stripes:param name="cancelReturnUrl"/>
                    Sign up now
                </stripes:link>
            </div>
        </div>
        <script type="text/javascript">
            $('#userName').focus();
        </script>
    </stripes:layout-component>
</stripes:layout-render>
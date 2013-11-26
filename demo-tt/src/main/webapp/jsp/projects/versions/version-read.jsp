<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<%@ taglib prefix="mde" uri="/manydesigns-elements"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="portofino"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.pageactions.crud.AbstractCrudAction"/>
<c:set var="version" value="${actionBean.object}"/>
<stripes:layout-render name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
    <stripes:layout-component name="contentHeader">
        <mde:sessionMessages />
        <div class="pull-right">
            <jsp:include page="/m/crud/result-set-navigation.jsp" />
            <jsp:include page="/m/crud/return-to-parent.jsp" />
        </div>
        <jsp:include page="/theme/breadcrumbs.jsp" />
    </stripes:layout-component>
    <stripes:layout-component name="pageHeader">
        <div class="pull-right">
            <stripes:link class="btn btn-small" href="${actionBean.context.actionPath}">
                <stripes:param name="edit"/>
                Edit version
            </stripes:link>
        </div>
        <h3 class="pageTitle">
            <stripes:layout-component name="pageTitle">
                <c:out value="${version.id} - ${version.title}"/>
            </stripes:layout-component>
            <span style="vertical-align: middle" class="<c:out value="${version.fk_version_state.css_class}"/>"><c:out value="${version.fk_version_state.state}"/></span>
        </h3>
        <div><c:out value="${version.description}"/></div>
        <div>
            <c:if test="${not empty version.planned_date}">
                <fmt:message key="planned.date._">
                    <fmt:param value="${version.planned_date}"/>
                </fmt:message>
            </c:if>
            <c:if test="${empty version.planned_date}">
                <fmt:message key="no.planned.date"/>
            </c:if>
        </div>
        <div>
            <c:if test="${not empty version.released_date}">
                <fmt:message key="release.date._">
                    <fmt:param value="${version.released_date}"/>
                </fmt:message>
            </c:if>
            <c:if test="${empty version.released_date}">
                <fmt:message key="no.release.date"/>
            </c:if>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="pageTitle">
        <c:out value="${actionBean.readTitle}"/>
    </stripes:layout-component>
    <stripes:layout-component name="pageBody">
    </stripes:layout-component>
</stripes:layout-render>
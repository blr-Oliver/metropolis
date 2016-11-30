<%@tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="elementId" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="collapsed" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<a href="#<c:out value="${elementId}"/>" role="button" data-toggle="collapse" aria-expanded="<c:out value="${collapsed ne true}"/>" aria-controls="<c:out value="${elementId}"/>"
	class="noselect ${collapsed ne true ? ' ' :' collapsed'}"> <i class="fa fa-chevron-down"></i> <c:out value="${title}" />
</a>
<div id="<c:out value="${elementId}"/>" class="collapse<c:out value="${collapsed ne true ? ' in' : ''}"/>">
	<jsp:doBody />
</div>
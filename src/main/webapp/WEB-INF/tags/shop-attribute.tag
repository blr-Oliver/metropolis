<%@tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@attribute name="name" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="choices" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<li class="attribute-item col-xs-12 col-md-6"><i class="fa fa-check"></i> <c:out value="${name }" />
	<c:if test="${not empty choices}">
		<ul class="choice-list list-inline">
			<c:forTokens items="${choices}" delims="," var="choice">
				<li class="choice-item"><c:out value="${choice}" /></li>
			</c:forTokens>
		</ul>
	</c:if></li>
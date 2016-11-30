<%@tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@attribute name="valign" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="pageHeader" required="false" fragment="true"%>
<%@attribute name="pageFooter" required="false" fragment="true"%>
<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<table id="layout">
	<tr id="layout-header">
		<td><c:choose>
				<c:when test="${pageHeadet ne null}">
					<jsp:invoke fragment="pageHeader" />
				</c:when>
				<c:otherwise>
					<m:top-menu />
				</c:otherwise>
			</c:choose></td>
	</tr>
	<tr id="layout-main">
		<td style="vertical-align: <c:out value="${valign ne null ? valign : 'top'}"/>;"><jsp:doBody /></td>
	</tr>
	<tr id="layout-footer">
		<td><c:choose>
				<c:when test="${pageFooter ne null}">
					<jsp:invoke fragment="pageFooter" />
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose></td>
	</tr>
</table>
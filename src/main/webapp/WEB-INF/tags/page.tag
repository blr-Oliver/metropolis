<%@tag language="java" pageEncoding="UTF-8" dynamic-attributes="da" trimDirectiveWhitespaces="true"%>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="head" required="false" fragment="true"%>
<%@attribute name="script" required="false" type="java.lang.String"%>
<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<c:set var="at"><c:forEach items="${da}" var="a"><c:out value="${a.key}"/>="<c:out value="${a.value}"/>" </c:forEach></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<title><c:out value="${title}" /></title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/bootstrap-navbar-mods.css" />
<link rel="stylesheet" href="/css/bootstrap-checkbox.css" />
<link rel="stylesheet" href="/css/sort-bar.css" />
<base href="${requestScope['javax.servlet.forward.request_uri']}" />
<jsp:invoke fragment="head" />
</head>
<body <c:out value="${at}" escapeXml="false" />>
	<jsp:doBody />
	<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.7/angular.js"></script>
	<script src="/js/angular/service/fragmentLoader.js"></script>
	<c:if test="${not empty script}">
		<c:forTokens items="${script}" delims=",;" var="module">
			<script src="/js/<c:out value="${module}"/>.js"></script>
		</c:forTokens>
	</c:if>
</body>
</html>
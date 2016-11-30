<%@tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<nav class="navbar navbar-default navbar-flat navbar-thin">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">Metropolis</a>
		</div>
		<ul class="nav navbar-nav">
			<li><a href="/">Shops</a></li>
			<li><a href="/">News</a></li>
			<li><a href="/">Map</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<sec:authorize access="isAuthenticated()">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><sec:authentication
							property="principal.entity.name" /><small class="glyphicon glyphicon-chevron-down" style="margin-left: 0.5em;"></small></a>
					<ul class="dropdown-menu">
						<li><div class="dropdown-content">
								<sec:authentication property="principal.entity.email" />
								<form action="/logout" method="POST">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="form-group text-center">
										<a class="btn btn-default" href="#">Add Account</a>
										<button type="submit" class="btn btn-default">Log Out</button>
										<div class="clearfix"></div>
									</div>
								</form>
							</div></li>
					</ul></li>
			</sec:authorize>
			<sec:authorize access="isAnonymous()">
				<li><a href="/login">Log In</a></li>
			</sec:authorize>
		</ul>
	</div>
</nav>

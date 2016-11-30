<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<m:page title="Sign up">
	<m:layout valign="middle">
		<div class="center-block" style="max-width: 24em;">
			<form:form action="${pageContext.request.contextPath}/user/register" commandName="user" method="POST" enctype="utf8" role="form" novalidate="novalidate" cssClass="col-xs-12">
				<spring:bind path="email">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label for="email" class="control-label">E-Mail</label>
						<form:input id="email" path="email" cssClass="form-control" placeholder="E-Mail" required="required" autofocus="autofocus" />
						<form:errors id="error-email" path="email" cssClass="help-block" />
					</div>
				</spring:bind>
				<spring:bind path="name">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label for="name" class="control-label">Name</label>
						<form:input id="name" path="name" cssClass="form-control" placeholder="Name" required="required" />
						<form:errors id="error-name" path="name" cssClass="help-block" />
					</div>
				</spring:bind>
				<spring:bind path="password">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label for="password" class="control-label">Password</label>
						<form:password id="password" path="password" cssClass="form-control" placeholder="Password" required="required" />
						<form:errors id="error-password" path="password" cssClass="help-block" />
					</div>
				</spring:bind>
				<div class="form-group">
					<label for="repeatPassword" class="control-label">Repeat Password</label> <input type="password" id="repeatPassword" class="form-control" placeholder="Repeat Password"
						required="required" />
				</div>
				<div class="form-group text-center">
					<button type="submit" class="btn btn-success">Sign up</button>
				</div>
			</form:form>
		</div>
	</m:layout>
</m:page>
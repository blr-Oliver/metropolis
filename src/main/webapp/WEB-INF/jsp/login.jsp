<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<m:page title="Login">
	<m:layout valign="middle">
		<div class="text-center center-block">
			<h1>Metropolis</h1>
		</div>
		<div class="login-container center-block">
			<div class="row">
				<c:if test="${param.error ne null}">
					<div class="alert alert-danger center-block">Invalid username and password.</div>
				</c:if>
				<c:if test="${param.logout ne null}">
					<div class="alert alert-info center-block">You have been logged out.</div>
				</c:if>
			</div>
			<form action="/login/auth" method="POST">
				<div class="form-group">
					<label for="username" class="control-label">E-Mail</label><input type="text" id="username" name="username" class="form-control" placeholder="E-Mail" autofocus />
				</div>
				<div class="form-group">
					<label for="password" class="control-label">Password</label>
					<div class="input-group">
						<input type="password" id="password" name="password" placeholder="Password" class="form-control" /><a class="input-group-addon" href="/forgot" title="Forgot password?"><span
							class="glyphicon glyphicon-question-sign"></span></a>
					</div>
				</div>
				<div class="form-group text-center">
					<a role="button" data-toggle="collapse" href="#oauth" aria-expanded="false" aria-controls="oauth" style="margin-right: 3em;">more login options</a>
					<button type="submit" class="btn btn-primary">Log In</button>
				</div>
				<div class="collapse" id="oauth">
					<div class="text-center">
						<label>Login with</label> <a class="btn btn-lg btn-default" href="/login/vk">VK</a> <a class="btn btn-lg btn-default" href="/login/fb">Fb</a> <a
							class="btn btn-lg btn-default" href="/login/google">g+</a>
					</div>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />.
			</form>
		</div>
		<div class="center-block text-center">
			Don't have an account? <a href="/user/register">Sign up</a>
		</div>
	</m:layout>
</m:page>
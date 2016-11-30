<%@tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="false"%>
<%@attribute name="shop" required="true" rtexprvalue="true" type="com.metropolis.mvc.model.Shop"%>
<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<div class="panel panel-default shop-item">
	<h4 class="col-xs-12">
		<c:out value="${shop.name}" />
		<small><c:out value="${shop.category.elementDisplayName}" /></small>
	</h4>
	<div class="logo col-xs-4">
		<img src="/img/logo_placeholder.png" alt="Logo" />
	</div>
	<div class="col-xs-8">
		<p>
			<c:out value="${shop.shortDescription}" />
		</p>
		<div>
			<c:choose>
				<c:when test="${not empty shop.tags}">
					<ul>
						<c:forEach items="${shop.tags}" var="tag"><li class="badge tag"><c:out value="${tag.displayName}" /></li></c:forEach>
					</ul>
				</c:when>
				<c:otherwise>No tags</c:otherwise>
			</c:choose>
		</div>
		<div>
			<c:choose>
				<c:when test="${not empty shop.attributes}">
					<ul class="attribute-list list-unstyled row">
						<c:forEach items="${shop.attributes.asBucketMap()}" var="entry">
							<li class="attribute col-xs-12 col-md-6"><i class="fa fa-check"></i> <c:out value="${entry.key.displayName}" />
								<c:if test="${not empty entry.value}">
									<ul class="choice-list list-unstyled">
										<c:forEach items="${entry.value}" var="choice">
											<li><c:out value="${choice.displayName}" /></li>
										</c:forEach>
									</ul>
								</c:if></li>
						</c:forEach>
						<li class="clearfix" />
					</ul>
				</c:when>
				<c:otherwise>No attributes</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
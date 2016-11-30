<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<m:page title="Metropolis" script="angular/search-panel/search-panel,angular/switchable,angular/service/shopService,angular/service/searchService" ng-app="searchPanel"
	ng-controller="searchPanelCtrl">
	<m:layout>
		<div class="col-xs-12">
			<div class="row">
				<aside id="search-options" class="col-sm-4 col-md-3">
					<m:search-panel />
				</aside>
				<div class="panel-group col-sm-8 col-md-9 sort-bar-wrap">
					<ul class="sort-bar">
						<li class="sort-marker desc"><span class="badge">Relevance</span></li>
						<li class="sort-marker"><span class="badge">Popularity</span></li>
						<li class="sort-marker asc"><span class="badge">Name</span></li>
					</ul>
					<div data-switchable>
						<div class="switchable">
							<shop-list items="shops" request="request"></shop-list>
						</div>
						<c:forEach items="${shops}" var="sWrap">
							<c:if test="${sWrap.data ne null}">
								<m:shop-item shop="${sWrap.data}" />
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</m:layout>
</m:page>
<%@tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/jsp/fragments/includes.jspf"%>
<div class="panel panel-default">
	<script type="text/javascript">
		window.categoryRoot = ${j:jsonView(categoryRoot, 'com.metropolis.mvc.model.Views$Full')};
		window.shops = ${j:jsonView(shops, 'com.metropolis.mvc.model.Views$Basic')} || [];
	</script>
	<form action="/" method="GET" autocomplete="off" novalidate>
		<div class="form-group">
			<div class="input-group">
				<input type="search" name="q" class="form-control" />
				<span class="input-group-btn">
					<button class="btn btn-default" type="submit" tabindex="-1">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</div>
		</div>
		<div class="form-group">
			<m:collapsible elementId="category-tree" title="Categories">
				<input type="hidden" name="c" ng-value="request.category.id" />
				<category-tree root="categoryRoot" ng-model="request.category"></category-tree>
			</m:collapsible>
		</div>
		<div class="form-group">
			<m:collapsible elementId="tag-list" title="Tags">
				<ng-switch on="combinedTagList().length">
					<span ng-switch-when="0">No tags</span>
					<span ng-switch-default class="tag noselect" ng-repeat="tag in combinedTagList()">
						<input type="checkbox" name="t" ng-value="tag.id" ng-model="tag.selected" ng-attr-id="t{{tag.id}}"/>
						<label class="badge tag" ng-bind="tag.displayName" ng-attr-for="t{{tag.id}}"></label>
					</span>
				</ng-switch>
			</m:collapsible>
		</div>
		<div class="form-group">
			<m:collapsible elementId="attribute-list" title="Attributes">
				<ng-switch on="combinedAttributeList().length">
					<span ng-switch-when="0">No Attributes</span>
					<ul ng-switch-default class="list-leveled list-unstyled" ng-repeat="attribute in combinedAttributeList()">
						<div class="checkbox">
							<input type="checkbox" ng-attr-id="a{{attribute.id}}" name="a" ng-value="attribute.id" ng-model="attribute.selected"/>
							<label ng-attr-for="a{{attribute.id}}" ng-bind="attribute.displayName"></label>
						</div>
						<ul class="list-leveled list-unstyled" ng-if="attribute.choices.length && attribute.selected">
							<li ng-repeat="choice in attribute.choices">
								<div class="checkbox">
									<input type="checkbox" ng-attr-id="a{{attribute.id}}_{{$index}}" ng-attr-name="a{{attribute.id}}" ng-value="choice.id" ng-model="choice.selected"/>
									<label ng-attr-for="a{{attribute.id}}_{{$index}}" ng-bind="choice.displayName"></label>
								</div>
							</li>
						</ul>
					</ul>
				</ng-switch>
			</m:collapsible>
		</div>
	</form>
</div>


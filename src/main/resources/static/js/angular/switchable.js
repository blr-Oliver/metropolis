(function() {
	angular.module('switchable', []).directive('switchable', [ '$compile', '$timeout', function($compile, $timeout) {
		return {
			restrict : 'A',
			scope : false,
			compile : function($element, $attrs) {
				var content = $element.find('.switchable').remove().html();
				function doSwitch($scope, $element, $attrs) {
					if (content) {
						// TODO remove flickering
						$element.html(content);
						$element.removeAttr($attrs.$attr.switchable);
						$compile($element)($scope);
						content = null;
					}
				}
				return function($scope, $element, $attrs, controller) {
					if (!$attrs.switchable)
						doSwitch($scope, $element, $attrs);
					else
						controller.doSwitch = function() {
							doSwitch($scope, $element, $attrs);
						};
				}
			},
			controller : [ '$scope', '$element', '$attrs', function($scope, $element, $attrs) {
				if ($attrs.switchable)
					$scope[$attrs.switchable] = this;
			} ]
		}
	} ])
}())
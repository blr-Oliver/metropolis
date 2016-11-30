;
(function(angular) {
	angular.module('confirmable', []).directive('confirmable', function() {
		return {
			restrict : 'A',
			link : function(scope, e, attr) {
				var confirmContent = attr.confirmable || 'Confirm';
				var armed = false, arming = false;
				var changeTarget = e.find('.confirmable');
				if (!e.length)
					changeTarget = e;
				var originalContent = changeTarget.html();
				$(document).on('click', function(event) {
					if (armed && !arming) {
						armed = false;
						changeTarget.html(originalContent);
					}
					arming = false;
				});
				$(e).on('click', function(event) {
					if (armed) {
						attr.ngClick && scope.$eval(attr.ngClick);
					} else {
						armed = arming = true;
						changeTarget.html(confirmContent);
					}
				})
			}
		}
	})
}(angular))
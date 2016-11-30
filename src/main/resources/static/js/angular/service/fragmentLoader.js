(function() {
	angular.module('fragmentLoader', []).config([ '$httpProvider', function($httpProvider) {
		$httpProvider.interceptors.push('fragmentLoaderInterceptor');
	} ]).factory('fragmentLoaderInterceptor', [ '$q', '$injector', function($q, $injector) {
		return {
			request : function(config) {
				var hashIndex = config.url.indexOf('#');
				if (hashIndex != -1) {
					return function(config, url, hash) {
						var $http = $injector.get('$http');
						var docRequest = $q.defer();
						$http($.extend({}, config, {
							url : url
						})).then(docRequest.resolve, docRequest.reject);
						return docRequest.promise.then(function() {
							return $.extend({}, config, {
								url : url,
								hash : hash
							});
						});
					}(config, config.url.substr(0, hashIndex), config.url.substr(hashIndex + 1));
				}
				return config;
			},
			response : function(response) {
				if (response.config.hash) {
					var hash = response.config.hash;
					var doc = $(response.data);
					var fragment = doc.filter('#' + hash);
					if (!fragment.length)
						fragment = doc.find('#' + hash)
					response = $.extend({}, response, {
						data : fragment.removeAttr('id').wrap('<p/>').parent().html()
					});
				}
				return response;
			}
		}
	} ]);
}())
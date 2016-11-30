;
(function(angular) {
	angular.module('cacheableLoader', []).factory('cacheableLoaderFactory', [ '$q', function($q) {
		return function(loader) {
			return function(queue, cached, undefined) {
				var load = function load(defer) {
					if (cached !== undefined)
						defer.resolve(cached);
					else {
						queue.push(defer);
						if (queue.length === 1) {
							var $d = $q.defer();
							$d.promise.then(function(data) {
								cached = data;
								queue.forEach(function(defer) {
									defer.resolve(cached);
								});
								queue.length = 0;
							});
							loader($d);
						}
					}
				};
				load.get = function() {
					return cached;
				}
				load.reset = function() {
					cached = undefined;
				}
				return load;
			}([]);
		}
	} ]);
}(angular))
(function() {
	angular.module('searchService', []).service('$shopSearch', [ '$http', function($http) {
		var baseUrl = '/API/search/shops';
		return {
			search : function(request) {
				request.query && (r.q = request.query.trim().toLocaleLowerCase());
				request.category && (r.c = request.category.id);
				request.tags && (r.t = request.tags.map(function(tag) {
					return tag.id;
				}));
				if (request.attributes) {
					// TODO
				}
				request.start && (r.start = request.start);
				request.count && (r.count = request.count);
				request.sort && (r.sort = request.sort.trim().toLowerCase().replace(/\\+/g, '$$asc').replace(/\\-/g, '$$desc'));
				return $http({
					method : 'GET',
					url : baseUrl,
					params : r,
					responseType : 'json'
				});
			}
		}
	} ]);
}())
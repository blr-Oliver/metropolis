(function() {
	angular.module('shopService', []).service('$shops', [ '$http', function($http) {
		return {
			findOne : function(id) {
				if (id !== null && typeof (id) !== 'undefined')
					return $http({
						method : 'GET',
						url : '/API/shops/' + id,
						responseType : 'json'
					})
			},
			findAll : function(id) {
				return $http({
					method : 'GET',
					url : '/API/shops',
					params : {
						id : id
					},
					responseType : 'json'
				})
			}
		}
	} ])
}())
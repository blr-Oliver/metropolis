(function() {
	function Node(node) {
		$.extend(this, node);
	}
	Node.prototype = {
		select : function(select) {
			this.doSelect(!!select, true, true);
		},
		doSelect : function(s, up, down) {
			if (this.selected = s) {
				if (up && this.parent) {
					if (this.parent.selectedChild && this.parent.selectedChild != this.id)
						this.parent.child(this.parent.selectedChild).doSelect(false, false, true);
					this.parent.selectedChild = this.id;
					this.parent.doSelect(true, true, false);
				}
			} else {
				if (up && this.parent)
					if (this.parent.selectedChild == this.id)
						this.parent.selectedChild = null;
				if (down && this.selectedChild)
					this.child(this.selectedChild).doSelect(false, false, true);
				this.selectedChild = null;
			}
		},
		deepest : function() {
			if (this.selectedChild)
				return this.child(this.selectedChild).deepest();
			else if (this.selected)
				return this;
		},
		child : function(id) {
			if (this.children)
				for (var i = 0; i < this.children.length; ++i)
					if (this.children[i].id == id)
						return this.children[i];
		},
		descendantOrSelf : function(id) {
			if (this.id == id)
				return this;
			if (id && this.children)
				for (var i = 0; i < this.children.length; ++i) {
					var result = this.children[i].descendantOrSelf(id);
					if (result)
						return result;
				}
		},
		sChild : function() {
			if (this.selectedChild)
				return this.child(this.selectedChild);
		}
	}
	function prop(a) {
		return function(value) {
			if (!arguments.length)
				return a;
			a = value;
			return this;
		}
	}
	angular.module('searchPanel', [ 'switchable', 'shopService', 'searchService', 'fragmentLoader' ]).config(function($locationProvider) {
		$locationProvider.html5Mode(true);
	}).factory('recursion', [ '$compile', function($compile) {
		// based on http://stackoverflow.com/questions/14430655/recursion-in-angular-directives
		return {
			compile : function(element, link) {
				angular.isFunction(link) && (link = {
					post : link
				});
				var contents = element.contents().remove();
				var compiledContents;
				return {
					pre : (link && link.pre) ? link.pre : null,
					post : function(scope, element) {
						!compiledContents && (compiledContents = $compile(contents));
						compiledContents(scope, function(clone) {
							element.append(clone);
						});
						link && link.post && link.post.apply(null, arguments);
					}
				};
			}
		};
	} ]).directive('categoryTree', function() {
		return {
			restrict : 'E',
			require : '?ngModel',
			scope : {
				root : '='
			},
			link : function($scope, $element, $attrs, ngModel) {
				var root = $scope.root;
				if (ngModel) {
					ngModel.$render = function() {
						var id = ngModel.$viewValue && ngModel.$viewValue.id;
						var node = root.descendantOrSelf(id);
						if (node)
							node.select(true);
					}
					$scope.tree.viewChanged = function() {
						ngModel.$setViewValue(root.deepest());
					}
				}
				$scope.$watch('root.selectedChild', function(newVal, oldVal) {
					// console.log('[' + root.displayName + '].selectedChild: ' + oldVal + ' -> ' + newVal);
					if (oldVal != newVal || oldVal && newVal) {
						var child = root.child(newVal);
						if (child)
							child.select(true);
						else {
							child = root.child(oldVal);
							if (child)
								child.select(false);
						}
						$scope.tree.viewChanged();
					}
				});
			},
			controller : function() {
				this.viewChanged = function() {
				}
			},
			controllerAs : 'tree',
			templateUrl : '/js/angular/search-panel/search-panel.html#categoryTree',
		};
	}).directive('categoryNode', [ 'recursion', '$window', '$timeout', function(recursion, $window, $timeout) {
		return {
			restrict : 'E',
			require : '^categoryTree',
			scope : {
				node : '=',
				level : '=?',
				traversable : '=?'
			},
			link : function($scope, $element, $attrs, tree) {
				var node = $scope.node;
				$scope.$watch('node.selected', function(newVal, oldVal) {
					// console.log('[' + node.displayName + '].selected: ' + oldVal + ' -> ' + newVal);
					if (oldVal != newVal) {
						node.select(newVal);
						tree.viewChanged();
					}
				});
				$scope.$watch('node.selectedChild', function(newVal, oldVal) {
					// console.log('[' + node.displayName + '].selectedChild: ' + oldVal + ' -> ' + newVal);
					if (oldVal != newVal || oldVal && newVal) {
						var child = node.child(newVal);
						if (child)
							child.select(true);
						else {
							child = node.child(oldVal);
							if (child)
								child.select(false);
						}
						tree.viewChanged();
					}
				});
				$scope.handleKeydown = function($event) {
					switch ($event.which) {
					case 37: // left
						node.parent.selectedChild = null;
						$timeout(function(id) {
							$window.document.getElementById(id).focus();
						}, 1, false, 'c' + (node.parent.parent ? node.parent.id : node.id));
						break;
					case 39: // right
						if (node.parent.selectedChild == node.id) {
							if (node.children && node.children.length) {
								node.selectedChild = node.children[0].id;
								$timeout(function(id) {
									$window.document.getElementById(id).focus();
								}, 1, false, 'c' + node.children[0].id);
							}
						} else {
							node.parent.selectedChild = node.id;
							$window.document.getElementById('c' + node.id).focus();
						}
						break;
					default:
						return;
					}
					$event.preventDefault();
				}
			},
			templateUrl : '/js/angular/search-panel/search-panel.html#categoryNode',
			compile : function(element) {
				return recursion.compile(element, this.link);
			}
		}
	} ]).directive('shopList', function() {
		function Pagination(page, count, total) {
			this.page = prop(page || 1);
			this.count = prop(count || 10);
			this.total = prop(total || 0);
			this.pages = [];
		}
		Pagination.prototype = {
			maxBlocks : 11,
			start : function(value) {
				if (!arguments.length)
					return (this.page() - 1) * this.count();
				this.page(value / this.count() + 1);
			},
			generatePages : function() {
				var currentPage, total, numPages, maxPage, minPage, maxPivotPages, pages;
				currentPage = this.page();
				total = this.total();
				numPages = Math.ceil(total / this.count());
				pages = [];
				if (numPages > 1) {
					pages.push({
						type : 'prev',
						number : Math.max(1, currentPage - 1),
						active : currentPage > 1
					});
					pages.push({
						type : 'first',
						number : 1,
						active : currentPage > 1
					});
					maxPivotPages = Math.round((this.maxBlocks - 5) / 2);
					minPage = Math.max(2, currentPage - maxPivotPages);
					maxPage = Math.min(numPages - 1, currentPage + maxPivotPages * 2 - (currentPage - minPage));
					minPage = Math.max(2, minPage - (maxPivotPages * 2 - (maxPage - minPage)));
					var i = minPage;
					while (i <= maxPage) {
						if ((i === minPage && i !== 2) || (i === maxPage && i !== numPages - 1)) {
							pages.push({
								type : 'more',
								active : false
							});
						} else {
							pages.push({
								type : 'page',
								number : i,
								active : currentPage !== i
							});
						}
						i++;
					}
					pages.push({
						type : 'last',
						number : numPages,
						active : currentPage !== numPages
					});
					pages.push({
						type : 'next',
						number : Math.min(numPages, currentPage + 1),
						active : currentPage < numPages
					});
				}
				return this.pages = pages;
			}
		};
		return {
			restrict : 'E',
			scope : {
				items : '=',
				request : '='
			},
			controller : [ '$scope', '$shops', '$shopSearch', function($scope, $shops, $shopSearch) {
				this.pagination = new Pagination(null, $scope.request.count, $scope.items.length);
				if ($scope.request.start)
					this.pagination.start($scope.request.start);
				this.pagination.generatePages();
			} ],
			controllerAs : 'shopList',
			templateUrl : '/js/angular/search-panel/search-panel.html#shopList'
		}
	}).directive('shopItem', [ function() {
		return {
			restrict : 'E',
			scope : {
				item : '='
			},
			replace : true,
			templateUrl : '/js/angular/search-panel/search-panel.html#shopItem'
		}
	} ]).controller('searchPanelCtrl', [ '$scope', '$shops', '$location', function($scope, $shops, $location) {
		function setupParent(category, parent) {
			parent && (category.parent = parent);
			category = new Node(category);
			category.children && (category.children = category.children.map(function(child) {
				return setupParent(child, this);
			}, category));
			return category;
		}
		function recursiveHash(property, root) {
			var hash = root[property] && root[property].reduce(function(hash, item) {
				return hash[item.id] = item, hash;
			}, {}) || {};
			return root.children && root.children.reduce(function(hash, child) {
				return $.extend(hash, recursiveHash(property, child));
			}, hash) || hash;
		}
		function sweepItems(property, root, hash) {
			root[property] && root[property].forEach(function(item, i, list) {
				list[i] = hash[list[i].id];
			})
			root.children && root.children.forEach(function(child) {
				sweepItems(property, child, hash);
			});
		}
		function combinedList(property) {
			var selected = $scope.request.category;
			var hash = selected ? recursiveHash(property, selected) : {};
			var list = Object.keys(hash).map(function(id) {
				return hash[id];
			});
			list.sort(function(a, b) {
				return a.displayName.localeCompare(b.displayName);
			});
			if (list.length > 20)
				list.length = 20;
			return list;
		}
		function transformAttributes(shop) {
			if (shop.attributeSelections) {
				var hash = {};
				var attributes = shop.attributes = [];
				shop.attributeSelections.forEach(function(selection) {
					var attribute = hash[selection.attribute.id];
					if (!attribute) {
						attributes.push(selection.attribute);
						attribute = hash[selection.attribute.id] = selection.attribute;
					}
					selection.value && (attribute.values || (attribute.values = [])).push(selection.value);
				});
				delete shop.attributeSelections;
			}
		}
		function lookupCategory(root, id) {
			if (root.id == id)
				return root;
			if (id && root.children) {
				for (var i = 0; i < root.children.length; ++i) {
					var found = lookupCategory(root.children[i], id);
					if (found)
						return found;
				}
			}
			return null;
		}
		var tagList = null;
		var attributeList = null;
		$scope.categoryRoot = setupParent(window.categoryRoot);
		$scope.shops = window.shops;
		$scope.shops.forEach(function(item) {
			item.data && transformAttributes(item.data);
		});
		sweepItems('tags', $scope.categoryRoot, recursiveHash('tags', $scope.categoryRoot));
		sweepItems('attributes', $scope.categoryRoot, recursiveHash('attributes', $scope.categoryRoot));
		$scope.request = {
			category : lookupCategory($scope.categoryRoot, $location.search().c)
		};
		$scope.$watch('request.category.id', function(newVal) {
			tagList = null;
			attributeList = null;
		});
		$scope.combinedTagList = function() {
			return tagList || (tagList = combinedList('tags'));
		}
		$scope.combinedAttributeList = function() {
			return attributeList || (attributeList = combinedList('attributes'));
		}
		$scope.collectData = function() {
			var data = {};
			function addValue(key, value) {
				if (key) {
					if (value !== null && typeof (value) !== 'undefined')
						(data[key] || (data[key] = [])).push(value);
				}
			}
			$('#search-options form').find('input').each(function(i, input) {
				if (!input.name.startsWith('c')) {
					if (input.type == 'checkbox' || input.type == 'radio') {
						if (input.checked)
							addValue(input.name, input.value);
					} else
						addValue(input.name, input.value);
				}
			});
			var selected = $scope.request.category;
			if (selected)
				addValue('c', selected.id);
			Object.keys(data).forEach(function(key) {
				if (data[key].length == 1)
					data[key] = data[key][0];
			});
			if (!data.q || !data.q || !(data.q = data.q.trim()))
				delete data.q;
		}
	} ]);
}())
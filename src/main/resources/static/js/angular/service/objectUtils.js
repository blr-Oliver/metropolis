;(function(angular) {
	angular.module('gameplanUtils', []).service('$compare', [ function() {
		var $get = function(obj, path) {
			path = path || [];
			path = typeof (path) === 'string' ? path.split('.') : path;
			return path.reduce(function(obj, prop) {
				return (obj === null || typeof (obj) === 'undefined') ? obj : obj[prop];
			}, obj)
		}
		var natural = function(a, b) {
			return a === null || typeof (a) === 'undefined' ? (b === null || typeof (b) === 'undefined' ? 0 : -1) : (a < b ? -1 : a == b ? 0 : 1);
		}
		return function() {
			this.$get = $get;
			this.natural = natural;
			this.reversed = function(comparator) {
				return function(a, b) {
					return -(comparator || natural)(a, b);
				}
			}
			this.property = function(path, comparator) {
				return function(a, b) {
					return (comparator || natural)($get(a, path), $get(b, path))
				}
			}
			/**
			 * Creates comparator of lists of comparable elements
			 * 
			 * @param comparator
			 *            optional comparator of elements
			 */
			this.list = function(comparator) {
				return function(a, b) {
					if (!a || !a.length)
						return b && b.length ? -1 : 0;
					if (!b || !b.length)
						return 1;
					for (var i = 0; i < a.length && i < b.length; ++i) {
						var r = (comparator || natural)(a[i], b[i]);
						if (r !== 0)
							return r;
					}
					return i == a.length ? (i == b.length ? 0 : -1) : 1;
				}
			}
			/**
			 * Creates composite comparator
			 * 
			 * @param comparators
			 */
			this.composite = function(comparators) {
				return function(a, b) {
					return comparators ? comparators.reduce(function(r, comparator) {
						return r ? r : (comparator || natural)(a, b);
					}, 0) : 0;
				}
			}
			/**
			 * Creates comparator of objects that checks successively properties
			 * for difference
			 * 
			 * @param comparators
			 *            list of properties to be checked
			 * @param comparators
			 *            optional list of comparators for each property
			 */
			this.hash = function(properties, comparators) {
				return this.composite(properties.map(function(property, i) {
					return this.property(property, comparators && comparators[i])
				}, this));
			}
		}
	}() ]).factory('$sorter', function() {
		function StackSorter(initial) {
			this.order = {};
			this.topPriority = 0;
			this.sort(initial);
		}
		StackSorter.prototype = {
			maxPriority : 1000,
			pack : function() {
				var sorting = this.getSorting();
				this.clear();
				this.sort(sorting);
			},
			sort : function(prop, asc) {
				if (typeof (prop) === 'string') {
					this.order[prop] = (arguments.length <= 1 || !!asc) ? ++this.topPriority : -++this.topPriority;
					if (this.topPriority > this.maxPriority)
						this.pack();
				} else {
					if (this.topPriority + prop.length > this.maxPriority)
						this.pack();
					for (var i = prop.length - 1; i >= 0; --i)
						this.sort((prop[i][0] === '-' || prop[i][0] === '+') ? prop[i].slice(1) : prop[i], !(prop[i][0] === '-'))
				}
			},
			clear : function(prop) {
				if (prop) {
					if (this.isPrimary(prop)) {
						delete this.order[prop];
						this.pack();
					} else
						delete this.order[prop];
				} else {
					this.order = {};
					this.topPriority = 0;
				}
			},
			toggle : function(prop) {
				if (prop)
					this.sort(prop, !this.getSorting(prop));
			},
			getSorting : function(prop) {
				if (prop)
					return this.order[prop] && this.order[prop] > 0;
				else {
					return Object.keys(this.order).map(function(key) {
						return {
							key : key,
							priority : Math.abs(this.order[key]),
							asc : this.order[key] > 0
						}
					}, this).sort(function(a, b) {
						return b.priority - a.priority
					}).reduce(function(sorting, value) {
						return sorting.push((value.asc ? '+' : '-') + value.key), sorting;
					}, []);
				}
			},
			isSorting : function(prop) {
				return !!this.order[prop];
			},
			isPrimary : function(prop) {
				return Math.abs(this.order[prop]) == this.topPriority;
			},
			getPrimary : function() {
				return Object.keys(this.order).reduce(function(r, key) {
					return r || (Math.abs(this.order[key]) == this.topPriority) && key;
				}.bind(this), null)
			}
		}
		return function(props, directions) {
			return new StackSorter(props, directions);
		}
	}).filter('property', [ '$compare', function($compare) {
		return function(array, expected) {
			return !angular.isDefined(expected) || array && array.filter(function(item) {
				for ( var i in expected)
					if ($compare.$get(item, i) != expected[i])
						return false;
				return true;
			});
		}
	} ])
}(angular))

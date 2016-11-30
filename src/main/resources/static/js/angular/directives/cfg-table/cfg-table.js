(function() {
	angular.module('cfgTable', [ 'gameplanUtils', 'fragmentLoader' ]).filter('tableSort', [ '$compare', function($compare) {
		return function(data, columns, sorting) {
			return data.sort($compare.composite(sorting.map(function(marker) {
				var asc = true;
				if (marker[0] === '+' || marker[0] === '-') {
					asc = marker[0] === '+';
					marker = marker.slice(1);
				}
				var c = columns[marker].compare.bind(columns[marker]);
				return asc ? c : $compare.reversed(c);
			}))), data;
		};
	} ]).directive('cfgTableColgroup', function() {
		return {
			restrict : 'A',
			replace : true,
			scope : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgTableColgroup);
			},
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTableColgroup'
		};
	}).directive('cfgTableThead', function() {
		return {
			restrict : 'A',
			replace : true,
			scope : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgTableThead);
			},
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTableThead'
		};
	}).directive('cfgTableColumnHeader', function() {
		return {
			restrict : 'A',
			scope : true,
			replace : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgController || '$table');
				$scope.$parent.$watch($attrs.cfgTableColumnHeader, function(newValue) {
					$scope.column = newValue;
				});
				$scope.$watchGroup([ 'column.sortable', 'column.state.sorting', 'column.state.primarySort' ], function(newValue) {
					var classes = $scope.$sortableClasses = [];
					var column = $scope.column;
					if (column && column.sortable) {
						classes.push('sortable');
						if (column.state.sorting) {
							classes.push('sort-' + column.state.sorting);
							if (column.state.primarySort)
								classes.push('sort-primary');
						}
					}
				});
			},
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTableColumnHeader'
		};
	}).directive('cfgTableGroupRow', function() {
		return {
			restrict : 'A',
			scope : true,
			replace : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgController || '$table');
				$scope.$parent.$watch($attrs.cfgTableGroupRow, function(newValue) {
					$scope.group = newValue;
				});
			},
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTableGroupRow'
		};
	}).directive('cfgTableRow', function() {
		return {
			restrict : 'A',
			scope : true,
			replace : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgController || '$table');
				$scope.$parent.$watch($attrs.cfgTableRow, function(newValue) {
					$scope.item = newValue;
				});
			},
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTableRow'
		};
	}).directive('cfgCell', [ '$compile', function($compile) {
		return {
			restrict : 'A',
			scope : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgController || '$table');
				$scope.$parent.$watch($attrs.cfgCell, function(newValue) {
					$scope.item = newValue;
				});
				$scope.$parent.$watch($attrs.cfgColumn, function(newValue) {
					$scope.column = newValue;
				});
				$scope.$watch('column.cellTemplate', function(newValue) {
					e.html(newValue);
					$compile(e.contents())($scope);
				});
			}
		};
	} ]).directive('cfgGroup', [ '$compile', function($compile) {
		return {
			restrict : 'A',
			scope : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgController || '$table');
				$scope.$parent.$watch($attrs.cfgGroup, function(newValue) {
					$scope.group = newValue;
				});
				$scope.$parent.$watch($attrs.cfgColumn, function(newValue) {
					$scope.column = newValue;
				});
				$scope.$watch('column.groupTemplate', function(newValue) {
					e.html(newValue);
					$compile(e.contents())($scope);
				});
			}
		};
	} ]).directive('cfgHeader', [ '$compile', function($compile) {
		return {
			restrict : 'A',
			scope : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgController || '$table');
				$scope.$parent.$watch($attrs.cfgHeader, function(newValue) {
					$scope.column = newValue;
				});
				$scope.$watch('column.headerTemplate', function(newValue) {
					e.html(newValue);
					$compile(e.contents())($scope);
				});
			}
		};
	} ]).directive('cfgCounter', [ '$compile', function($compile) {
		return {
			restrict : 'E',
			template : '<span class="badge">{{group.data.length}}</span>'
		};
	} ]).directive('cfgTablePagination', function() {
		return {
			restrict : 'E',
			scope : {
				pagination : '='
			},
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTablePagination'
		};
	}).directive('cfgTableMenu', function() {
		return {
			restrict : 'E',
			scope : true,
			replace : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgController || '$table');
			},
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTableMenu'
		};
	}).directive('cfgTableMarkup', function() {
		return {
			restrict : 'E',
			scope : true,
			replace : true,
			link : function($scope, e, $attrs) {
				$scope.$table = $scope.$eval($attrs.cfgController || '$table');
				$scope.loading = $attrs.loading || '/images/loading2.gif';
				$scope.title = $attrs.title;
			},
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTableMarkup'
		}
	}).directive('cfgTableFilter', function() {
		return {
			restrict : 'A',
			scope : true,
			compile : function() {
				// some tricks to emulate replace: true
				return function($scope, e, $attrs) {
					e.find('thead tr').unwrap();
					e.addClass('cfg-table-filter');
				}
			},
			controller : [ '$scope', '$attrs', function($scope, $attrs) {
				var table = $scope.$table = $scope.$eval($attrs.cfgTableFilter)
				table.filterCtrl = this;
				$scope.$watchGroup(Object.keys(table.columns).reduce(function(list, name) {
					var column = table.columns[name];
					return column.filterable && list.push(function() {
						return column.state.filter;
					}), list;
				}, []), function() {
					table.reload();
				});
				function getDefaultActiveColumn() {
					if (table.settings.grouping && table.columns[table.settings.grouping].filterable)
						return table.settings.grouping;
					for (var i = 0; i < table.visibleColumns.length; ++i)
						if (table.visibleColumns[i].filterable)
							return table.visibleColumns[i].name;
				}
				this.activate = function(name) {
					name = name || this.current || getDefaultActiveColumn();
					if (table.columns[name] && table.columns[name].filterable) {
						if (!this.active) {
							this.active = true;
							this.current = name;
						} else {
							this.active = this.current != name;
							this.current = name;
						}

					}
				}
				this.scroll = function(shift) {
					var fs = table.visibleColumns.reduce(function(list, column) {
						return column.filterable && !column.state.grouping && list.push(column.name), list;
					}, []);
					if (table.settings.grouping && table.columns[table.settings.grouping].filterable)
						fs.unshift(table.settings.grouping);
					if (fs.length > 1)
						this.activate(fs[(fs.indexOf(this.current) + shift + fs.length) % fs.length]);
				}
				this.current = null;
				this.active = false;
				this.getCompositeFilter = function() {
					var activeColumns = Object.keys(table.columns).filter(function(name) {
						var column = this.columns[name];
						return column.filterable && column.state.filter;
					}, table).map(function(name) {
						return this.columns[name];
					}, table);
					if (activeColumns.length) {
						// 2. run prefilters on all parts
						var preFiltered = activeColumns.map(function(column) {
							return column.preFilter(column.state.filter);
						});
						// 3. create composite filter predicate
						return function(predicates) {
							return function(item) {
								for (var i = 0; i < predicates.length; ++i)
									if (!predicates[i](item))
										return false;
								return true;
							};
						}(activeColumns.map(function(column, i) {
							return column.filter.bind(column, preFiltered[i]);
						}));
					}
					// returning no-op
					return function(item) {
						return true;
					}
				}
			} ],
			templateUrl : '/js/page/angular/directives/cfg-table/cfg-table.html#cfgTableFilter'
		}
	}).directive('cfgTable', [ '$q', '$compare', '$filter', function($q, $compare, $filter) {
		String.prototype.sentenceCase = function() {
			return this.trim().toLowerCase().split(/\s+/).map(function(s) {
				return s && s.length ? s.charAt(0).toUpperCase() + s.substring(1) : s;
			}).join(" ");
		};

		var prop = function(thisArg, a) {
			return function() {
				return function(value) {
					if (!arguments.length)
						return a;
					a = value;
					return this;
				}
			}().bind(thisArg);
		}

		function ColumnState(options) {
			$.extend(this, options);
		}
		ColumnState.prototype = {
			hidden : false,
			sorting : false,
			primarySort : false,
			grouping : false,
			single : false,
			width : 'auto'
		};

		function Column(options, name) {
			$.extend(this, options);
			this.state = new ColumnState(this.state);
			this.name = name;
		}
		Column.prototype = {
			title : '',
			sortable : true,
			defaultSort : 'asc',
			groupable : false,
			field : '',
			weight : 1.0,
			order : 100,
			cellValue : function(item) {
				return $compare.$get(item, this.field);
			},
			groupId : function(item) {
				if (!this.multiGroup) {
					var value = this.cellValue(item);
					if (!value)
						return '[N/A]';
					return value.toString().sentenceCase();
				} else {
					return item && item.id;
				}
			},
			groupValue : function(id, item) {
				return this.multiGroup ? (id && item.name || '[N/A]') : id;
			},
			multiGroup : false,
			cellTemplate : '{{column.cellValue(item)}}',
			groupTemplate : '{{group.value}} <cfg-counter></cfg-counter>',
			headerTemplate : '{{column.title}}',
			compare : function(a, b) {
				return $compare.natural(this.cellValue(a), this.cellValue(b))
			},
			preFilter : function(filter) {
				return new RegExp(filter, 'i');
			},
			filter : function(filter, item) {
				return filter.test(this.cellValue(item));
			},
			filterable : false
		};

		function Selection(initial) {
			if (!arguments.length) {
				this.enabled = true;
				this.items = {};
			} else {
				if (initial && typeof (initial) === 'object') {
					if (initial.hasOwnProperty('enabled')) {
						this.enabled = !!initial.enabled;
						initial = initial.items;
					} else {
						this.enabled = true;
						initial = initial.items || initial;
					}
					this.items = this.enabled && Object.keys(initial || []).reduce(function(r, id) {
						return r[id] = true, r;
					}, {});
				} else {
					this.enabled = !!initial;
					this.items = {};
				}
			}
		}
		Selection.prototype = {
			toggle : function(id) {
				this.select(id, !this.items[id]);
			},
			select : function(id, select) {
				if (arguments.length < 2 || select)
					this.items[id] = true;
				else if (this.items[id])
					delete this.items[id];
			},
			clear : function() {
				this.items = {};
			},
			size : function() {
				return Object.keys(this.items).length;
			}
		};

		function Settings(initial) {
			this.load(initial);
		}
		Settings.prototype = function() {
			var $keys = [ 'visible', 'sorting', 'grouping', 'pageSize' ];
			return {
				visible : true,
				sorting : false,
				grouping : false,
				pageSize : 10,
				load : function(settings) {
					settings && $keys.forEach(function(key) {
						settings[key] && (this[key] = settings[key]);
					}, this);
				}
			}
		}();

		function Pagination(initial) {
			if (initial && angular.isDefined(initial.enabled))
				this.enabled = !!initial.enabled;
			if (initial && angular.isDefined(initial.counts))
				this.counts = initial.counts;
			if (initial && angular.isDefined(initial.maxBlocks))
				this.maxBlocks = initial.maxBlocks;
			this.page = prop(this, initial && initial.page || 1);
			this.count = prop(this, initial && initial.count || 10);
			this.total = prop(this, initial && initial.total || 0);
			this.pages = [];
		}
		Pagination.prototype = {
			enabled : true,
			counts : [ 10, 25, 50, 100 ],
			maxBlocks : 11,
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

		function Controller($scope, $attrs, $q, $filter, $compare, $sorter) {
			this.init($scope.$eval($attrs.cfgTable), $sorter);
			var self = this;

			// ---watchers---
			if (this.settings.key)
				$scope.$watch(function() {
					return self.settings;
				}, function(newValue) {
					localStorage.setItem(self.settings.key, JSON.stringify(newValue));
				}, true);

			$scope.$watchCollection(function() {
				return self.settings.visible;
			}, function(newValue) {
				Object.keys(self.columns).forEach(function(key) {
					self.columns[key].state.hidden = true;
				});
				newValue.forEach(function(key) {
					self.columns[key].state.hidden = false;
				});
				self.updateVisibleColumns();
			});

			$scope.$watch(function() {
				return self.settings.grouping;
			}, function(newValue) {
				Object.keys(self.columns).forEach(function(key) {
					self.columns[key].state.grouping = key == newValue;
				});
				self.updateVisibleColumns();
				self.reload();
			});

			$scope.$watchCollection(function() {
				return self.settings.sorting;
			}, function(newValue) {
				self.sorter.clear();
				self.sorter.sort(newValue);
				Object.keys(self.columns).forEach(function(name) {
					self.columns[name].state.sorting = self.sorter.isSorting(name) && (self.sorter.getSorting(name) ? 'asc' : 'desc');
					self.columns[name].state.primarySort = self.sorter.isSorting(name) && self.sorter.isPrimary(name);
				});
				self.reload();
			});

			if (self.selection.enabled) {
				$scope.$watch(function() {
					return self.$plainData;
				}, function(newVal) {
					self.$plainData && self.$plainData.forEach(function(item) {
						this.selection.select(item.id, item.selected);
					}, self);
				}, true);
				$scope.$watchCollection(function() {
					return self.selection.items;
				}, function(newVal) {
					self.$plainData && self.$plainData.forEach(function(item) {
						item.selected = !!newVal[item.id];
					});
				});
			}

			$scope.$watch(function() {
				return self.pagination.page();
			}, function() {
				self.reload();
			});

			$scope.$watch(function() {
				return self.pagination.count();
			}, function(newValue) {
				self.settings.pageSize = newValue;
				self.pagination.page(1);
				self.reload();
			});
		}
		Controller.$getDataWrapper = function(delegate) {
			return function(defer, self) {
				var $defer = $q.defer();
				$defer.promise.then(function(allData) {
					if (self.filterCtrl)
						allData = allData.filter(self.filterCtrl.getCompositeFilter());
					var sorted = $filter('tableSort')(allData, self.columns, self.settings.sorting);
					self.pagination.total(sorted.length);
					var page = sorted.slice((self.pagination.page() - 1) * self.pagination.count(), self.pagination.page() * self.pagination.count());
					defer.resolve(page);
				});
				delegate($defer, self);
			}
		};
		Controller.$id = Controller.$id || 1;
		Controller.prototype = {
			init : function(config, $sorter) {
				this.$$id = ++Controller.$id;
				this.columns = Object.keys(config.columns).reduce(function(columns, name) {
					return columns[name] = new Column(config.columns[name], name), columns;
				}, {});
				this.settings = new Settings(config.settings);
				if (config.settings && config.settings.key)
					this.settings.load(JSON.parse(localStorage.getItem(config.settings.key)));
				if (this.settings.visible === true)
					this.settings.visible = Object.keys(this.columns);
				this.settings.key = config.settings.key;
				this.sorter = $sorter(this.settings.sorting || []);
				this.pagination = new Pagination(config.pagination);
				this.pagination.count(this.settings.pageSize);
				this.selection = new Selection(config.selection);
				this.getData = config.partialLoad ? config.getData : Controller.$getDataWrapper(config.getData);
			},
			toggleGrouping : function(name) {
				this.settings.grouping = (name != this.settings.grouping) && name;
			},
			toggleHidden : function(name) {
				var column = this.columns[name];
				var index = this.settings.visible.indexOf(name);
				var show = column.state.hidden;
				if (column.state.grouping || column.state.single)
					return;
				if (show && index < 0)
					this.settings.visible.push(name);
				if (!show && index >= 0)
					this.settings.visible.splice(index, 1);
			},
			toggleSorting : function(name) {
				var column = this.columns[name];
				if (!column.sortable)
					return;
				this.sorter.sort(column.name, function(state, columnDefault, globalDefault) {
					if (state === 'asc')
						return false;
					if (state === 'desc')
						return true;
					if (columnDefault === 'asc' || columnDefault === '+' || columnDefault === true)
						return true;
					if (columnDefault === 'desc' || columnDefault === '-' || columnDefault === false)
						return false;
					return globalDefault;
				}(column.state.sorting, column.defaultSort, true));
				this.settings.sorting = this.sorter.getSorting();
			},
			updateVisibleColumns : function() {
				var columns = this.visibleColumns = this.settings.visible.map(function(name) {
					return this.columns[name];
				}, this);
				var totals = columns.reduce(function(totals, column) {
					if (!column.state.grouping) {
						totals.count++;
						totals.weight += column.weight;
					}
					return totals;
				}, {
					weight : 0.0,
					count : 0
				});
				columns.forEach(function(column) {
					if (column.state.grouping) {
						column.state.width = '0%';
						column.state.single = false;
					} else {
						column.state.width = totals.weight == 0.0 ? 'auto' : (100.0 * column.weight / totals.weight).toFixed(1) + '%';
						column.state.single = totals.count == 1;
					}
				});
				columns.sort(function(order) {
					return function(a, b) {
						return a.order == b.order ? (order[a.name] - order[b.name]) : (a.order - b.order);
					}
				}(columns.reduce(function(hash, column, i) {
					return hash[column.name] = i, hash;
				}, {})));
			},
			getGroups : function(defer, self) {
				var $defer = $q.defer();
				$defer.promise.then(function(data) {
					var groups = {}, groupList;
					function distributeItem(item) {
						var groupId = column.groupId(item);
						var group = groups[groupId] || (groups[groupId] = {
							id : groupId,
							value : column.groupValue(groupId, item),
							data : []
						});
						group.data.push(item);
					}
					function distributeMulti(item, groupItem) {
						var groupId = column.groupId(groupItem);
						var group = groups[groupId] || (groups[groupId] = {
							id : groupId,
							value : column.groupValue(groupId, groupItem),
							data : []
						});
						group.data.push(item);
					}
					if (this.settings.grouping) {
						var column = this.columns[this.settings.grouping];
						data.forEach(column.multiGroup ? function(item) {
							var groupItems = column.cellValue(item) || [];
							if (!groupItems.length)
								distributeMulti(item, null);
							else
								groupItems.forEach(function(groupItem) {
									distributeMulti(item, groupItem);
								});
						} : function(item) {
							// cannot just pass callback since forEach will add
							// index as second argument
							distributeItem(item);
						}, this);
						groupList = Object.keys(groups).map(function(id) {
							return groups[id];
						});
						if (this.sorter.isSorting(column.name))
							groupList.sort(this.sorter.getSorting(column.name) ? $compare.natural : $compare.reversed());
					} else {
						groupList = [ {
							id : null,
							value : null,
							data : data
						} ];
					}
					defer.resolve(groupList);
				}.bind(this));
				this.getData($defer, this);
			},
			reload : function() {
				if (this.$loading)
					return;
				var $defer = $q.defer();
				this.$loading = true;
				$defer.promise.then(function(groups) {
					this.groups = groups;
					this.$plainData = null;
					if (this.pagination.enabled)
						this.pagination.generatePages();
					if (this.selection.enabled)
						this.getPlainData().forEach(function(item) {
							item.selected = !!this[item.id];
						}, this.selection.items);
					this.$loading = false;
				}.bind(this));
				this.getGroups($defer, this);
			},
			groups : [],
			getPlainData : function() {
				return this.$plainData || (this.$plainData = this.groups.reduce(function(r, group) {
					return group.data.reduce(function(r, item) {
						return r.push(item), r;
					}, r);
				}, []));
			}
		};

		return {
			restrict : 'A',
			priority : 10,
			controller : [ '$scope', '$attrs', '$q', '$filter', '$compare', '$sorter', Controller ],
			link : function($scope, e, $attrs, controller) {
				$scope[$attrs.cfgController || '$table'] = controller;
			}
		};
	} ]);
}())
(function (angular) { 
	
	'use strict';

	var userModule = angular.module('userModule');
	userModule.filter('paginationLogic', function () {
		return function (input, start) {
			if (input) {
				start = +start;
				return input.slice(start);
			}
			return [];
		};
	});
	
})(window.angular);
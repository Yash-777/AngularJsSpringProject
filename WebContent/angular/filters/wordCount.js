/*wordCount.js*/
var strength = angular.module('loginModule');

var passwordLength = 7;

/* https://docs.angularjs.org/error/$injector/unpr?p0=$scopeProvider%20%3C-%20$scope%20%3C-%20scopeinjection_otherthan_controller_direct
 * strength.filter('passwordCount', ['$scope', function($scope) {
 * 
 * Attempting to inject a scope object into anything that's not a controller or a directive, for example a service,
 * will also throw an Unknown provider: $scopeProvider <- $scope error.*/
strength.filter('passwordCountFilter', [function() {
	return function(value, peak) {
		value = angular.isString(value) ? value : '';
		peak = isFinite(peak) ? peak : passwordLength;
		var retrunVal = value && (value.length > peak ? peak + '+' : value.length);
		return retrunVal;
	};
}]);
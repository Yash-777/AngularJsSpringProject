/*wordCount.js 
 * - https://docs.angularjs.org/guide/filter
 * - https://toddmotto.com/everything-about-custom-filters-in-angular-js/*/
var loginModule = angular.module('loginModule');

/* https://docs.angularjs.org/error/$injector/unpr?p0=$scopeProvider%20%3C-%20$scope%20%3C-%20scopeinjection_otherthan_controller_direct
 * strength.filter('passwordCount', ['$scope', function($scope) {
 * 
 * Attempting to inject a scope object into anything that's not a controller or a directive,
 * for example a service, will also throw an Unknown provider: $scopeProvider <- $scope error.*/
loginModule.filter('passwordCountFilter', [ function() {
	var passwordLengthDefault = 7;
	return function( passwordModelVal ) {
		//console.log( "passwordModelVal : ", passwordModelVal );
		passwordModelVal = angular.isString(passwordModelVal) ? passwordModelVal : '';
		var retrunVal = passwordModelVal && 
			(passwordModelVal.length > passwordLengthDefault ? passwordLengthDefault + '+' : passwordModelVal.length);
		return retrunVal;
	};
} ]);
/*angularMainPage.js*/
(function( ng ){
	
	"use strict";
var userModule = ng.module('userModule', ['ngRoute'])
userModule.constant('USERDETAILS', (function() {
		console.log('EMAIL_ID : ', sessionStorage.getItem("userName"));
		return {
			EMAIL_ID: sessionStorage.getItem("userName")
		}
}));
userModule.config( function($routeProvider) {
	$routeProvider
		.when('/dashboard', {
			template : '<div> <p> Informaiton Regarding Dashoard </p> </div>',
			/*controller  : 'homeController'*/
		})
		.when('/support', {
			template : '<div> <p> Informaiton Regarding Support </p> </div>',
			/*controller  : 'supportCtrl'*/
		})
		.otherwise({ redirectTo: '/dashboard' });
}).run(['$rootScope', '$location', function($rootScope, $location){
	console.log('userModule '+userModule);
	var path = function() {
		return $location.path();
	};
	$rootScope.$watch(path, function(newVal, oldVal){
		$rootScope.activetab = newVal;
		console.log('rootscope value  '+newVal);
	});
}]);

})(window.angular);
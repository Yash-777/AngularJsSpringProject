(function (angular) { 
	
	'use strict';

	var userModule = angular.module('userModule');
	
	// 
	userModule.controller('dashboardController', 
			['$rootScope', '$scope','$http', '$window', 'USERDETAILS', 'dashboardJSONService', dashboardControllerFun]);
	function dashboardControllerFun($rootScope, $scope, $http, $window, USERDETAILS, dashboardJSONService) {
		
		console.log('Dash Board Controller : email', USERDETAILS.EMAIL_ID);
		dashboardJSONService.serverReq( function(resp) {
			$rootScope.dashboardLinkData =  resp;
			console.log('Dash Board Controller Callback Fun : ', $rootScope.dashboardLinkData );
			
			// pagination controls
			$scope.currentPage = 1;
			$scope.totalItems = $rootScope.dashboardLinkData.length;
			$scope.entryLimit = 5; // items per page
			$scope.noOfPages = Math.ceil($scope.totalItems / $scope.entryLimit);
			
		});
		
		
		/*$scope.getImages = function() {
				console.log('getImages :: .click - function()');
				$(document).on('click','.getSrc',function (){
				//$('.getSrc').click(function(){
					console.log('data..........')
					var src = $(this).attr('src'); 
				   $('.showPic').attr('src', src);
				});
		}*/
	}
	userModule.filter('passwordCountFilter', [ function() {
		console.log('passwordCountFilter');
		var passwordLengthDefault = 7;
		return function( expressionModelVal, argument1) {
			console.log("expressionModelVal : ", expressionModelVal, "\t Provided Value : ", argument1);
			expressionModelVal = angular.isString(expressionModelVal) ? expressionModelVal : '';
			argument1 = isFinite(argument1) ? argument1 : passwordLengthDefault;
			var retrunVal = expressionModelVal && (expressionModelVal.length > argument1 ? argument1 + '+' : expressionModelVal.length);
			return retrunVal;
		};
	} ]);
})(window.angular);
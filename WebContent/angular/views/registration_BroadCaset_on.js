/**
 * http://embed.plnkr.co/am6IDw/
 * 
 * USING EVENT LISTENERS «
 * https://docs.angularjs.org/api/ng/directive
 * 	$scope.$broadcast('eventBroadcastedName', $element.val() );
 * 	scope.$on('eventBroadcastedName', function(event, data) {
			console.log('eventBroadcastedName : ', data);
			ngModel.$validate();
		});
		
	Events : $broadcast - can't be canceled, but $emit can be canceled 'event.stopPropagation();'
	https://stackoverflow.com/a/20181396/5081877
 */
(function() {

var loginModule = angular.module('loginModule', []);
loginModule.constant('USERCONSTANTS', (function() {
	return {
		PASSWORD_LENGTH: 7
	}
})());

loginModule.controller('registerController', ['$scope','$http', '$window', '$location', registerControllerFun]);
function registerControllerFun($scope, $http, $window, $location) {
	console.log('================= registerControllerFun...');
	
	$scope.showAlert = function(ev) {
		console.log('===== ***** BUTTON CLICKED ***** =====');
	};
}
loginModule.factory('myfactory', [function() {
	return {
		score: function() {
			//console.log('arguments List : ', arguments);
			var score = 0, value = arguments[0], passwordLength = arguments[1];
			var containsLetter = /[a-zA-Z]/.test(value), containsDigit = /\d/.test(value), containsSpecial = /[^a-zA-Z\d]/.test(value);
			var containsAll = containsLetter && containsDigit && containsSpecial;
			
			console.log(" containsLetter - ", containsLetter,
					" : containsDigit - ", containsDigit,
					" : containsSpecial - ", containsSpecial);
			
			if( value.length == 0 ) {
				score = 0;
			} else {
				if( containsAll ) {
					score += 3;
				} else {
					if( containsLetter ) score += 1;
					if( containsDigit ) score += 1;
					if( containsSpecial ) score += 1;
				}
				if(value.length >= passwordLength ) score += 1;
			}
			/*console.log('Factory Arguments : ', value, " « Score : ", score);*/
			return score;
		}
	};
}]);

loginModule.directive('okPasswordDirective', ['myfactory', 'USERCONSTANTS', '$rootScope', function(myfactory, USERCONSTANTS, $rootScope) {
	return {
		// restrict to only attribute and class [AC]
		restrict: 'AC',
		priority: 2000,
		// use the NgModelController
		require: 'ngModel',
		
		// add the NgModelController as a dependency to your link function
		link: function($scope, $element, $attrs, ngPasswordModel) {
			console.log('Directive - USERCONSTANTS.PASSWORD_LENGTH : ', USERCONSTANTS.PASSWORD_LENGTH);

// https://docs.angularjs.org/api/ng/directive
			//$element.on('focus blur change keypress mouseover', function( evt ) {
$element.bind('change', function( evt ) {
	$scope.$broadcast('eventBroadcastedName', $element.val() );
				$scope.$evalAsync(function($scope) {
					var pwd = $scope.password = $element.val();
					// update the $scope.password with the element's value
					
					/*Password Strength Meter Conditions:
						valid password must be more than 7 characters
						Factory score must have a minimum score of 3. [Letter, Digit, Special Char, CharLength > 7]*/
					$scope.myModulePasswordMeter = pwd ? (pwd.length > USERCONSTANTS.PASSWORD_LENGTH 
							&& myfactory.score(pwd, USERCONSTANTS.PASSWORD_LENGTH) || 0) : null;
					ngPasswordModel.$setValidity('okPasswordController', $scope.myModulePasswordMeter > 3);
				});
				if( ngPasswordModel.$valid ) {
					$scope.passwordVal = ngPasswordModel.$viewValue;
					console.log('Updated Val : ', $scope.passwordVal);
					//$scope.updatePass();
				}
			});
		}
	};
}]);

loginModule.filter('passwordCountFilter', [ function() {
	var passwordLengthDefault = 7;
	return function( passwordModelVal ) {
		passwordModelVal = angular.isString(passwordModelVal) ? passwordModelVal : '';
		var retrunVal = passwordModelVal && 
			(passwordModelVal.length > passwordLengthDefault ? passwordLengthDefault + '+' : passwordModelVal.length);
		return retrunVal;
	};
} ]);

var compareTo = function() {
	return {
		require: "ngModel",
		priority: 2000,
		// directive defines an isolate scope property (using the = mode) two-way data-binding
		scope: {
			passwordEleWatcher: "=compareTo"
		},
		
		link: function(scope, element, attributes, ngModel) {
			console.log('Confirm Password Link Function call.');
			
			var pswd = scope.passwordEleWatcher;
			
			ngModel.$validators.compareTo = function( compareTo_ModelValue ) {
				//console.log('scope:',scope);
				
				if( (pswd != 'undefined' && pswd.$$rawModelValue != 'undefined') && (pswd.$valid && pswd.$touched) ) {
					var pswdModelValue = pswd.$modelValue;
					var isVlauesEqual = ngModel.$viewValue == pswdModelValue;
					return isVlauesEqual;
				} else {
					console.log('Please enter valid password, before conforming the password.');
					return false;
				}
			};
			
			/*scope.$watch("passwordEleWatcher", function() {
				console.log('$watch « Confirm-Password Element Watcher.')
				ngModel.$validate();
			});*/
			
		scope.$on('eventBroadcastedName', function(event, data) {
			console.log('eventBroadcastedName : ', data);
			ngModel.$validate();
		});

			/*scope.$parent.updatePass = function() {
				console.log('$watch « Password Element Watcher.')
				console.log('Pswd: ',scope.$parent.passwordVal, '\t Cnfirm:', ngModel.$modelValue);
				//scope.registerForm.confirm.$invalid = true;
			}*/
		},
	};
};
loginModule.directive("compareTo", compareTo);
})();

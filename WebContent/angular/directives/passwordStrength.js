/*passwordStrength.js*/
var strength = angular.module('loginModule');
/*var resetPassword = angular.module('resetModule');
var changePassword = angular.module('userModule');*/

strength.factory('myfactory', [ myFactoryFun ])
        .directive('okPasswordDirective', ['myfactory', 'USERCONSTANTS', okPasswordFun]);
/*
resetPassword.factory('myfactory', [ myFactoryFun ])
             .directive('okPasswordDirective', ['myfactory', 'USERCONSTANTS', okPasswordFun]);
*/
function myFactoryFun() {
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
}
function okPasswordFun(myfactory, USERCONSTANTS) {
	return {
		// restrict to only attribute and class [AC]
		restrict: 'AC',
		priority: 2000,
		// use the NgModelController
		require: 'ngModel',
		
		// add the NgModelController as a dependency to your link function
		link: function($scope, $element, $attrs, ngPasswordModel) {
			console.log('Directive - USERCONSTANTS.PASSWORD_LENGTH : ', USERCONSTANTS.PASSWORD_LENGTH);
			
			$element.on('blur change keydown', function( evt ) {
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
					$scope.updatePass();
				}
			});
		}
	};
}
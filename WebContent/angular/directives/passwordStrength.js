/*passwordStrength.js*/
var strength = angular.module('loginModule');

var passwordLength = 7;
strength.factory('myfactory', [function() {
	return {
		score: function() {
			var score = 0, value = arguments[0];
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
			console.log('Factory Arguments : ', value, " « Score : ", score);
			return score;
		}
	};
}])

.directive('okPasswordDirective', ['myfactory', function(myfactory) {
	return {
		// restrict to only attribute and class [AC]
		restrict: 'AC',

		// use the NgModelController
		require: 'ngModel',

		// add the NgModelController as a dependency to your link function
		link: function($scope, $element, $attrs, ngModelCtrl) {
			$element.on('blur change keydown', function(evt) {
				$scope.$evalAsync(function($scope) {
					// update the $scope.password with the element's value
					var pwd = $scope.password = $element.val();
					
					// resolve password strength score using zxcvbn service
					$scope.loginModule = pwd ? (pwd.length > passwordLength && myfactory.score(pwd) || 0) : null;

					// define the validity criterion for okPassword constraint
					ngModelCtrl.$setValidity('okPassword', $scope.loginModule > 3);
				});
			});
		}
	};
}]);
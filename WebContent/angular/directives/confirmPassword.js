/*A Custom Directive
We could put logic into a model to confirm a password, but it is probably better 
to separate the logic into a reusable directive.

* https://github.com/angular/angular.js/blob/10644432ca9d5da69ce790a8d9e691640f333711/src/ng/directive/input.js#L2522
*/

var compareTo = function() {
	return {
		require: "ngModel",
		scope: {
			password: "=compareTo"
		},
		link: function(scope, element, attributes, ngModel) {
			 
			ngModel.$validators.compareTo = function( confirmPassword ) {
				console.log("Password : ", scope.password.$modelValue,"\tconfirmPassword : ", confirmPassword);
				var result = confirmPassword == scope.password.$modelValue;;
				console.log("Confirm password : ", result);
				return result;
			};
 
			scope.$watch("password", function() {
				ngModel.$validate();
			});
		}
	};
};
var myModule = angular.module('loginModule');
myModule.directive("compareTo", compareTo);
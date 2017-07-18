/*A Custom Directive - "reusable component" directive
We could put logic into a model to confirm a password, but it is probably better 
to separate the logic into a reusable directive.

The `ngController` directive attaches a controller class to the view.
MVC components in angular:
 *
 * * Model — Models are the properties of a scope; scopes are attached to the DOM where scope properties
 *   are accessed through bindings.
 * * View — The template (HTML with data bindings) that is rendered into the View.
 * * Controller — The `ngController` directive specifies a Controller class; the class contains business
 *   logic behind the application to decorate the scope with functions and values
 * https://www.youtube.com/watch?v=0r5QvzjjKDc
 * https://github.com/angular/angular.js/blob/10644432ca9d5da69ce790a8d9e691640f333711/src/ng/directive/input.js#L2522
 */

var compareTo = function() {
	return {
		require: "ngModel",
		priority: 2000,
		/*
		 * https://docs.angularjs.org/api/ng/service/$compile#-scope-
		 * only one new scope is createdIf multiple directives on the same element request a new scope,
		 * only one new scope is created.
			$$isolateBindings:Object
				passwordEleWatcher:Object
					attrName:"compareTo"
					collection:false
					mode:"="
					optional:false
			
			passwordEleWatcher:hh
				$dirty:true | false
				$modelValue:"Yash@123" | undefined
				$valid:true | false
		 */
		// directive defines an isolate scope property (using the = mode) two-way data-binding
		scope: {
			passwordEleWatcher: "=compareTo"
		},
		/*scope - not as - $scope as it is not dependency injection*/
		link: function(scope, element, attributes, ngModel) {
			console.log('Confirm Password Link Function call.');
			
			var pswd = scope.passwordEleWatcher;
			
			ngModel.$validators.compareTo = function( compareTo_ModelValue ) {
				//console.log('scope:',scope);
				
				if( (pswd != 'undefined' && pswd.$$rawModelValue != 'undefined')
						&& (pswd.$valid && pswd.$touched) ) {
					var pswdModelValue = pswd.$modelValue;
					// arguments List of PSWD :  ["Yash@123", 7]
					// Element : [input#confirmedID.form-control, ...] element.val()="Yash@123"
					// ngModel is CONFIRM Password Model
					var isVlauesEqual = ngModel.$viewValue == pswdModelValue;
					return isVlauesEqual;
				} else {
					console.log('Please enter valid password, before conforming the password.');
					return false;
				}
			};
			
			// pswd = hh {$viewValue: "Yash@123", $modelValue: "Yash@123", $$rawModelValue: "Yash@123", $validators: Object, …}
			scope.$watch("passwordEleWatcher", function() {
				console.log('$watch « Confirm-Password Element Watcher.')
				ngModel.$validate();
			});
			
			scope.$parent.updatePass = function() {
				console.log('$watch « Password Element Watcher.')
				console.log('Pswd: ',scope.$parent.passwordVal, '\t Cnfirm:', ngModel.$modelValue);
				//alert('inside updateMap()');
				if( scope.$parent.passwordVal != ngModel.$modelValue ) {
					console.log(' =====================Password Changed when Confirm password Matched.')
					//scope.registerForm.confirm.$invalid = true;
				}
			}
			// hh {$viewValue: undefined, $modelValue: undefined, $$rawModelValue: undefined, $validators: Object, $asyncValidators: Object…}
			// hh {$viewValue: "Yash@123n", $modelValue: "Yash@123", $$rawModelValue: "Yash@123n", $validators: Object, $asyncValidators: Object…}
			/*scope.$evalAsync(function(scope) {
				//console.log('E:',element,'\nAtt:',attributes,'\nModel:',ngModel);
				console.log('$evalAsync « Password Element Watcher calls on.')
				ngModel.$validate();
				//ngModel.$setValidity('myController', false);
			});*/
		},
		controller: function myController($scope) {
			console.log("compareTo - Custom directive controller function called.");
		}
	};
};
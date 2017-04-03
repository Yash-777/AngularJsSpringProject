/* Self executing functions
 * (function (angular) { ... })(window.angular);
 * (function () { ... })();
 * */

(function (angular) { 
	
	'use strict';
	
	/* https://docs.angularjs.org/error/$injector/nomod?p0=loginModule
	 * create the module and naming it as 'loginModule'
	 * var myModule = angular.module('loginModule', []);
	 * 
	 * we are accessing an already created module
	 * var myModule = angular.module('loginModule');
	 * */
	var myModule = angular.module('loginModule');
	
	/* create the controller with name 'loginController'
	 * and loginControllerFun is controllers main function name.
	 * 
	 * we can pass|inject the required parameters in 2 ways
	 * 1) .controller('loginController', ['$scope','$window', loginControllerFun]);
	 * 2) Predefined Object « loginController.$inject - ['$scope', '$window'];
	 * */
	
	// https://docs.angularjs.org/error/$injector/unpr?p0=$scopeProvider%20%3C-%20$scope%20%3C-%20passwordCountFilter
	myModule.controller('loginController', ['$rootScope', '$scope','$http', '$window', loginControllerFun]);
	
	myModule.controller('registerController', ['$scope','$http', '$window', '$location', registerControllerFun]);
	
	myModule.controller('forgotPasswordController', ['$rootScope', '$scope','$http', '$location', forgotPasswordControllerFun]);
	
	function loginControllerFun($rootScope, $scope, $http, $window) {
		
		/*$scope.loadScript('http://localhost:8080/MyAngularSpringProject/js/file.js', 'text/javascript', 'utf-8');*/
		
		console.log('loginController : RootScope Vlaue = ', $rootScope.forgotMessage);
		
		if( $rootScope.forgotMessage != null && $rootScope.forgotMessage != 'undefined' && $rootScope.forgotMessage == 'success' ) {
			var msg = "We've sent you an email with instructions on how to reset your password.";
			$('#resentPassword').html( msg );
			$('#resentPassword').addClass('alert alert-success');
			/*$().role="alert"*/
			$('#resentPassword').show();
		}
		$scope.formSubmit = function () {
			/*$rootScope.forgotMessage =  '';*/
			console.log('loginController formSubmit1 : RootScope Vlaue = ', $rootScope.forgotMessage);
			
			if( $rootScope.forgotMessage != null && $rootScope.forgotMessage != 'undefined' && $rootScope.forgotMessage == 'success' ) {
				$('#resentPassword').html( '' );
				$('#resentPassword').addClass('');
				$('#resentPassword').hide();
			}
			
			for (var prop in $rootScope) {
				if (prop.substring(0,1) !== '$') {
					delete $rootScope[prop];
				}
			}
			console.log('loginController formSubmit2 : RootScope Vlaue = ', $rootScope.forgotMessage);
			
			var username = $scope.email77;
			var password = $scope.password;
			console.log("User: ", username, " Pass: ", password);
			var targetRequestPath = '../account/userLogin.form';
			var targetRequestParams = { 'username': username, 'password': password };
			var isAuthenticated = false;
			$http({
				method: 'POST',
				url: targetRequestPath,
				headers: {'Content-Type': 'application/json'},
				data: targetRequestParams
			}).success( function(data) {
				console.log('Response Data : ', data);
				/*$('#email').addClass('form-control');*/
				if(data == 'valid') {
					isAuthenticated = true;
					/* $scope.error = ''; $scope.email77 = ''; $scope.password = ''; */
					console.log("Authentication Status : Pass « ", isAuthenticated);
					
					$window.location.href = '../account/loginSucess.form';
				} else {
					$scope.password = '';
					console.log("Authentication Status : Fail « ", isAuthenticated);
					$scope.error = "Invalid email or password!";
					
					/*  http://api.jquery.com/html/ */
					$('#error').html($scope.error);
					$('#error').addClass('alert alert-danger');
					
					$('#error').show();
				} 
			}).error(function(data){
				console.log('Error message. ', data);
			});
		};
	}
	function registerControllerFun($scope, $http, $window, $location) {
		
		/*$scope.loadScript('http://localhost:8080/DemoAngularJs/js/fileuploadstyle.js', 'text/javascript', 'utf-8');*/
		
		console.log('registerControllerFun');
		$scope.formSubmit = function () {
			
			var username = $scope.username, email =  $scope.email77, password = $scope.password
			,firstName = $scope.firstName, lastName = $scope.lastName;
			console.log("User: ", username, " Pass: ", password, " Email: ", email,
					" F: ", firstName, " L: ", lastName);
			var targetRequestPath = '../account/register';
			var targetRequestParamsREQ = 
			{ 'username': username, 'password': password, 'email' : email, 'firstName': firstName, 'lastName': lastName };
			var isAuthenticated = false;
			$http({
				method: 'POST',
				url: targetRequestPath,
				headers: {'Content-Type': 'application/json'},
				data: targetRequestParamsREQ
			}).success( function(data) {
				console.log('Response Data : ', data);
				$scope.password = '';
				$scope.confirm77 = '';
				
				if(data == 'valid') {
					isAuthenticated = true;
					$scope.error = '';
					$scope.email77 = '';
					console.log("Authentication Status : Pass « ", isAuthenticated);
					
					$location.path('/login');
				} else {
					console.log("Authentication Status : Fail « ", isAuthenticated);
					/* This Email ID already Registered, Either use another Email ID or click on Forget password.*/
					$scope.error = "This Email ID already Registered.";
					/*  http://api.jquery.com/html/ */
					$('#error').html($scope.error);
					$('#error').addClass('alert alert-danger');
					
					$('#error').show();
				}
			}).error(function(data){
				console.log('Error message. ', data);
			});
		};
	}
	
	function forgotPasswordControllerFun($rootScope, $scope, $http, $location) {
		
		console.log('forgotPasswordControllerFun');
		$scope.formSubmit = function () {
			var email = $scope.email77;
			console.log("forgotPasswordControllerFun « User: ", email);
			var targetRequestPath = '../account/forgot';
			var targetRequestParams = { 'email': email };
			var isAuthenticated = false;
			$http({
				method: 'POST',
				url: targetRequestPath,
				headers: {'Content-Type': 'application/json'},
				data: targetRequestParams
			}).success( function(data) {
				console.log('Response Data : ', data);
				$scope.password = '';
				if(data == 'valid') {
					/* Successfully sent the password to your registered email.
					 * Your password reset email was sent - check your mail!*/
					isAuthenticated = true;
					$scope.error = '';
					$scope.email77 = '';
					console.log("Authentication Status : Pass « ", isAuthenticated);
					
					$rootScope.forgotMessage = "success";
					// template login with success message.
					$location.path('/login');
					/*$window.location.href = '../account/loginSucess';*/
				} else {
					/* That e-mail address doesn't have an associated user account. Are you sure you've registered?
					 * Account does not exist | Can't find that email, sorry.
					 * Email or Mobile no. entered is not registered with us. */
					console.log("Authentication Status : Fail « ", isAuthenticated);
					$scope.error = "Entered Email is not registered with us.";
					
					/*  http://api.jquery.com/html/ */
					$('#error').html($scope.error);
					$('#error').addClass('alert alert-danger');
					
					$('#error').show();
				} 
			}).error(function(data){
				console.log('Error message. ', data);
			});
		};
	}
})(window.angular);
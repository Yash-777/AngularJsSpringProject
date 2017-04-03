<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js" ng-app="loginModule">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<!-- load angular and angular route via CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.9/angular.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.2.18/angular-ui-router.js"></script>

<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">

<title>SignIn Page</title>
<style type="text/css">
[ng\:cloak],
[ng-cloak],
[data-ng-cloak],
[x-ng-cloak],
.ng-cloak,
.x-ng-cloak {
    display: none !important;
}

body {
    background: white;
    margin: 0;
    padding: 0;
}

.main-container {
    display: table;
    width: 400px;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
}

.form-container {
    position: relative;
    bottom: 0;
    display: table-cell;
    vertical-align: middle;
}

.form-container form > div {
    padding: 0 15px;
}

.form-container form > button {
    margin-left: 15px;
}

legend.form-label {
    font-size: 24pt;
    padding: 0 15px;
}

.form-control.error {
    border-color: red;
}

.form-hint {
    font-size: 7pt;
    line-height: 9pt;
    margin: -5px auto 5px;
    color: #999;
}

.form-hint.error {
    color: #C00;
    font-weight: bold;
    font-size: 8pt;
}

.password-count {
    float: right;
    position: relative;
    bottom: 24px;
    right: 10px;
}

.strength-meter {
    position: relative;
    height: 3px;
    background: #DDD;
    margin: 10px auto 20px;
    border-radius: 3px;
}

.strength-meter:before,
.strength-meter:after {
    content: '';
    height: inherit;
    background: transparent;
    display: block;
    border-color: #FFF;
    border-style: solid;
    border-width: 0 5px 0 5px;
    position: absolute;
    width: 80px;
    z-index: 10;
}

.strength-meter:before {
    left: 70px;
}

.strength-meter:after {
    right: 70px;
}

.strength-meter-fill {
    background: transparent;
    height: inherit;
    position: absolute;
    width: 0;
    border-radius: inherit;
    transition: width 0.5s ease-in-out, background 0.25s;
}

.strength-meter-fill[data-strength='0'] {
    background: darkred;
    width: 20%;
}

.strength-meter-fill[data-strength='1'] {
    background: orangered;
    width: 40%;
}

.strength-meter-fill[data-strength='2'] {
    background: orange;
    width: 60%;
}

.strength-meter-fill[data-strength='3'] {
    background: yellowgreen;
    width: 80%;
}

.strength-meter-fill[data-strength='4'] {
    background: green;
    width: 100%;
}

</style>
</head>
<body>
<!-- https://scotch.io/tutorials/password-strength-meter-in-angularjs -->
	<div class="main-container">
		<div class="form-container">

			<form  name="joinTeamForm" role="form" data-ng-controller="FormController" ng-submit="formSubmit()">
				<legend class="form-label">Sign In</legend>

				<div class="form-group">
					<div id = "invalidDetails" role="alert">
					
					</div>
				</div>
				
				<div class="form-group">
					<label for="email">Email</label>

					<div class="error form-hint" ng-show="joinTeamForm.email.$dirty && joinTeamForm.email.$error.required" ng-cloak>{{"This field is required."}}</div>

					<div class="error form-hint" ng-show="joinTeamForm.email.$dirty && joinTeamForm.email.$error.email" ng-cloak>{{"Email is invalid."}}</div>

					<input type="email" class="form-control" ng-class="(joinTeamForm.email.$dirty && joinTeamForm.email.$invalid) ? 'error' : ''" id="email" name="email" placeholder="Enter Email Address" ng-required="true" ng-model="email">
				</div>

				<div class="form-group">
					<label for="password">Password</label>

					<div class="form-hint">To conform with our Strong Password policy, you are required to use a sufficiently strong password. Password must be more than 5 characters.</div>

					<input type="password" class="form-control ok-password" ng-class="(joinTeamForm.password.$dirty && joinTeamForm.password.$invalid) ? 'error' : ''" id="password" name="password" placeholder="Enter Password" ng-required="true" ng-model="password">

					<div class="label password-count" ng-class="password.length > 5 ? 'label-success' : 'label-danger'" ng-cloak>{{ password | passwordCount:5 }}</div>

					<div class="strength-meter">
						<div class="strength-meter-fill" data-strength="{{loginModule}}"></div>
					</div>
				</div>

				<button type="submit" class="btn btn-primary" ng-disabled="joinTeamForm.$invalid">Submit</button>
				
			</form>

		</div>
	</div>
	<script type="text/javascript" src="https://rawgit.com/dropbox/zxcvbn/master/dist/zxcvbn.js"></script>
</body>

<script type="text/javascript">// ${pageContext.servletContext.contextPath}/index.jsp
window.onload = function() {
	var passwordCount = 5;
(function() {

	angular.module('loginModule', [])

	.factory('LoginService', function () {
	    var admin = 'yashwanth.m@gmail.com';
	    var pass = 'Yash777';
	    var isAuthenticated = false;
	    
	    return {
	      login : function(username, password) {
	    	 // ajax request - return message
	    	 // DemoAngularjs -testpages - fileuploadangular.jsp
	        isAuthenticated = username === admin && password === pass;
	        return isAuthenticated;
	      },
	      isAuthenticated : function() {
	        return isAuthenticated;
	      }
	    };
	})
    

    .controller('FormController', function($scope, LoginService) {
    	$scope.formSubmit = function() {
    	console.log($scope.email+" ::"+ $scope.password);
   	      if(LoginService.login($scope.email, $scope.password)) {
   	    	console.log("Authentication Status : Pass");
   	        $scope.error = '';
   	        $scope.email = '';
   	        $scope.password = '';
   	        $state.transitionTo('home');
   	      } else {
   	        $scope.error = "Invalid username or password!";
   	        var eleError = '<span class="text-danger">{{ error }}</span>';
   	      }   
   	    };
    })

    .filter('passwordCount', [function() {
        return function(value, peak) {
            value = angular.isString(value) ? value : '';
            peak = isFinite(peak) ? peak : passwordCount;

            return value && (value.length > peak ? peak + '+' : value.length);
        };
    }])

    
    .factory('zxcvbn', [function() {
        return {
            score: function() {
                var compute = zxcvbn.apply(null, arguments);
                return compute && compute.score;
            }
        };
    }])

    .directive('okPassword', ['zxcvbn', function(zxcvbn) {
        return {
            // restrict to only attribute and class
            restrict: 'AC',

            // use the NgModelController
            require: 'ngModel',

            // add the NgModelController as a dependency to your link function
            link: function($scope, $element, $attrs, ngModelCtrl) {
                $element.on('blur change keydown', function(evt) {
                    $scope.$evalAsync(function($scope) {
                        // update the $scope.password with the element's value
                        var pwd = $scope.password = $element.val();
						
                        //http://stackoverflow.com/a/13946696/5081877
                        var pattern = /^[a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};:\\|,.<>\/?]*$/;
                        
                        // resolve password strength score using zxcvbn service
                        $scope.loginModule = 
                        pwd ? (pwd.length > passwordCount && zxcvbn.score(pwd) || 0) : null;

                        // define the validity criterion for okPassword constraint
                        ngModelCtrl.$setValidity('okPassword', $scope.loginModule >= 2);
                    });
                });
            }
        };
    }]);

})();

}

</script>
</html>
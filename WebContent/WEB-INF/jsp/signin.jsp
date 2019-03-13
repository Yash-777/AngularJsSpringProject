<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<!-- load required files in order -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.9/angular.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/angularLogin.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/confirmPassword.css">
<script type="text/javascript">
$('#error').hide();
$('#resentPassword').hide();
/* You can make the dependency of the "user directives like ['ngRoute', 'passwordStrength.js', 'confirmPassword.js']" to the page Model. */
var loginRouteProvider = angular.module('loginModule', ['ngRoute']);
loginRouteProvider.constant('USERCONSTANTS', (function() {
	// Define your variable
	var resourceName = '${pageContext.request.contextPath}';
	var resorceFullPath = '${pageContext.request.getScheme()}://${pageContext.request.getServerName()}:${pageContext.request.getServerPort()}'+
	'${pageContext.request.getContextPath()}';
	console.log('resourceName : ', resourceName);
	console.log('resorceFullPath : ',resorceFullPath);
	console.log('getLocalAddr : ', '${pageContext.request.getLocalAddr()}');
	console.log('getRequestURL : ', '${pageContext.request.getRequestURL()}');
	// Use the variable in your constants
	return {
		USERS_DOMAIN: resorceFullPath,
		USERS_API: resorceFullPath + '/users',
		BASIC_INFO: resorceFullPath + '/api/info',
		PASSWORD_LENGTH: 7
	}
})());
loginRouteProvider.config( function($routeProvider) {
	$routeProvider

	// route for the home page
	.when('/login', {
	templateUrl : '${pageContext.servletContext.contextPath}/angular/views/login.html',
	controller  : 'loginController'
	})

	// route for the about page
	.when('/forgotPassword', {
	templateUrl : '${pageContext.servletContext.contextPath}/angular/views/forgotPassword.html',
	controller  : 'forgotPasswordController'
	})

	// route for the contact page
	.when('/register', {
	templateUrl : '${pageContext.servletContext.contextPath}/angular/views/register.html',
	controller  : 'registerController'
	})
	.otherwise({ redirectTo: '/login' });
});

</script>
	<script src="${pageContext.servletContext.contextPath}/angular/controllers/loginModuleControler.js"></script>
	<script src="${pageContext.servletContext.contextPath}/angular/filters/wordCount.js"></script>
	<script src="${pageContext.servletContext.contextPath}/angular/directives/passwordStrength.js"></script>
	<script src="${pageContext.servletContext.contextPath}/angular/directives/confirmPassword.js"></script>
	<%-- <script src="${pageContext.servletContext.contextPath}/angular/services/loginService.js"></script> --%>
	
<script type="text/javascript">
var loginModule = angular.module('loginModule');
loginModule.directive("compareTo", compareTo);
</script>
<title>SignIn Page</title>

<style>
body #bg {
    /* bottom: 0; */
    left: 0;
    position: fixed;
    z-index: 0;
    width: 100%;
}
</style>
	
</head>

<body data-ng-app="loginModule">
	<%-- <img id="bg" src="${pageContext.servletContext.contextPath}/images/globe.jpg" alt="application logo"> --%>
<!-- defining angular app for the HTML Element -->
<div class="container">

	<div class="row">
		<div class="login-panel"><!-- col-md-5 col-md-offset-3 -->
			<!-- angular template - this is where content will be injected -->
			<div data-ng-view></div>
		</div>
	</div>
	
</div>
</body>
</html>
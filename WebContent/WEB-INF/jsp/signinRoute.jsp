<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<!-- load required files in order -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.9/angular.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js"></script>
	
	
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/angularLogin.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/confirmPassword.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
	
<script type="text/javascript">
$('#error').hide();
$('#resentPassword').hide();
var loginRouteProvider = angular.module('loginModule', ['ngRoute']).config( function($routeProvider) {
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
	<title>SignIn Page</title>
	
</head>

<body data-ng-app="loginModule">

<!-- defining angular app for the HTML Element -->
<div class="main-container" >

	<!-- angular template - this is where content will be injected -->
	<div data-ng-view></div>
	
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- CDN's URL : https://www.bootstrapcdn.com/ -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Defects</title>

<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/bootstrapModified.css" type="text/css">
<!-- <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/js/custom.js"></script>

<script type="text/javascript">
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
</head>
<body>
<header class="white-bg">
<!-- Start Logo and User icon part -->
<div class="top-header">   
 <div class="container-fluid">
   <div class="row">
     <div class="col-md-10 col-xs-12">
    <img src="${pageContext.servletContext.contextPath}/images/spring.png" alt="Spring" style="width: 32px;">
    </div>
     <div class="col-md-2 col-xs-12 pull-right">
    <ul class="nav navbar-right">
         <li class="dropdown">
             <a href="#" class="dropdown-toggle profile-name" data-toggle="dropdown" aria-expanded="false">
             <i class="fa fa-user"></i>${sessionScope.userName}<b class="caret" style="color:#333;"></b></a>
             <%-- <core:out value="${username}"></core:out> --%>
             <ul class="dropdown-menu animated flipInX">
                 <li>
                     <a><i class="fa"><B>IP : <core:out value="${IP}"/> </B></i></a>
                 </li>
                 <li class="divider"></li>
                 <li><!-- class - fa-gear[setting symbol] fa-power-off[log out symbol] -->
                     <a href="account-details.html"><i class="fa fa-fw fa-gear"></i>Settings</a>
                 </li>
                
                 <li>
                     <a href="${pageContext.servletContext.contextPath}/account/logout">
                     <i class="fa fa-fw fa-power-off"></i>Sign out</a>
                 </li>
             </ul>
         </li>
     </ul>
    </div>
    </div>
  </div>
</div>
<!-- End Logo and User icon part --> 
</header>
<div data-ng-app="userModule">

<!-- defining angular app for the HTML Element -->
<div class="main-container" >

	<!-- angular template - this is where content will be injected -->
	<div data-ng-view></div>
	
</div>
</div>
</body>
</html>
</header>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- CDN's URL : https://www.bootstrapcdn.com/ -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Defects</title>

<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">

	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.9/angular.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js"></script>
	
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/angular/angularMainPage.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/js/custom.js"></script>

<script type="text/javascript">
/* You can make the dependency of the "user directives" to the page Model. */
var userRouteProvider = angular.module('userModule', ['ui.bootstrap', 'ngRoute']);
userRouteProvider.constant('USERDETAILS', (function() {
	var resorceFullPath = '${pageContext.request.getScheme()}://${pageContext.request.getServerName()}:${pageContext.request.getServerPort()}'+
	'${pageContext.request.getContextPath()}';
	console.log('resorceFullPath : ',resorceFullPath);
	console.log('USERS_NAME : ', '${sessionScope.userName}');
	console.log('EMAIL_ID : ', '${sessionScope.email}');
	console.log('PASSWORD : ', '${sessionScope.password}');
	// Use the variable in your constants
	return {
		USERS_DOMAIN: resorceFullPath,
		USER_NAME: '${sessionScope.userName}',
		EMAIL_ID: '${sessionScope.email}',
		PASSWORD: '${sessionScope.password}',
		PASSWORD_LENGTH: 7
	}
})());
userRouteProvider.config( function($routeProvider) {

	$routeProvider
	.when('/dashboard', {
		templateUrl : '${pageContext.servletContext.contextPath}/angular/views/dashboard.html',
		controller  : 'dashboardController'
	})
	/* .when('/Scenarioreports/ScenarioName/:ScenarioName/id/:ScenarioSessionID', {
	templateUrl : '${pageContext.servletContext.contextPath}/angular/views/scenarioReports.html',
	controller  : 'ScenarioreportsController'
	}) */
	.when('/support', {
	templateUrl : '${pageContext.servletContext.contextPath}/angular/views/support.html',
	controller  : 'supportController'
	})
	.otherwise({ redirectTo: '/dashboard' });
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
     <!-- It's good practice to use either the spring:url tag or the JSTL c:url tag to wrap URLs -->
     <%-- https://stackoverflow.com/a/2206865/5081877
 taglib xmlns:spring="http://www.springframework.org/tags"
 xmlns:c="http://java.sun.com/jsp/jstl/core"
<img src="<spring:url value='/images/logo.png'/>"/>
<spring:url value="/testsuccess" var="myurl" htmlEscape="true"/>
<a href="${myurl}">...</a>

     <c:url value='/testsuccess'/>
     <img src="<%=request.getContextPath()%>/images/logo.png"/> --%>
    <img style="height: 60px;" src="${pageContext.servletContext.contextPath}/images/chrome-logo_2x.png">
    <div style="width: 130px;">
    <!-- https://www.w3schools.com/js/js_timing.asp
    This example executes a function called "myTimer" once every second (like a digital watch). -->
<!-- <button onclick="clearTimeout(myVar)">Stop it</button>
<p id="demo"></p>
<script>
var myVar = setInterval(myTimer, 1000);

function myTimer() {
    var d = new Date();
    document.getElementById("demo").innerHTML = d.toLocaleTimeString();
}
</script> -->
    </div>
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
               
               <li> <a href="#/profile"><i class="fa fa-fw fa-user-o"></i> Profile</a> </li>
               <li> <a href="#/settings"><i class="fa fa-fw fa-gear"></i> Settings</a> </li>
               <li> <a href="${pageContext.servletContext.contextPath}/account/logout">
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

<!-- Start wrapper -->

<div id="wrapper"> 

	<!-- Side Bar Wrapper -->
	<div id="sidebar-wrapper" class="side-menu">
		<ul class="sidebar-nav nav-stacked" id="menu">
			<li data-ng-class="{'active':(activetab == '/dashboard')}"><a href="#/dashboard"> <span class="fa-stack fa-lg pull-left">
				<i class="fa fa-dashboard fa-stack-1x "></i></span> Dashboard</a>
			</li>
			<li data-ng-class="{'active':(activetab == '/support')}"><a href="#/support"><span class="fa-stack fa-lg pull-left">
				<i class="fa fa-support fa-stack-1x "></i></span> Support</a>
			</li>
		</ul>
	</div>
	<!-- /Side Bar Wrapper --> 
  
	<!-- angular template - this is where content will be injected -->
	<div data-ng-view></div>
	
</div>
</div>
</body>
</html>
</header>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<!-- load required files in order -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.9/angular.min.js"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.2.18/angular-ui-router.js"></script>
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/angularLogin.css">
	<script src="${pageContext.servletContext.contextPath}/angular/loginApp.js"></script>
	<script src="${pageContext.servletContext.contextPath}/angular/controllers/loginModuleControler.js"></script>
	<script src="${pageContext.servletContext.contextPath}/angular/thirdParty/passwordStrength.js"></script>
	
	<title>SignIn Page</title>
	
</head>
<body>
<!-- https://scotch.io/tutorials/password-strength-meter-in-angularjs -->
	<!-- defining angular app for the HTML Element -->
	<div class="main-container" data-ng-app="loginModule">
	<!-- define angular controller -->
		<div class="form-container"  data-ng-controller="loginController" >
			
			<legend class="form-label">Sign In</legend>
			<form name="formName" role="form" ng-submit="formSubmit()">
			
				<div id='error' ></div>
				
				<!-- We used some of the validation state properties - $dirty, $invalid, $error, provided by the NgModelController API 
				to determine if our form is fully validated. The ng-class directive was also used to dynamically add an error class 
				to the input elements based on the validation criteria. -->
				
				<!-- if any error | empty the class="form-group has-error".
				and Submit button having [class="btn btn-primary" ng-disabled="formName.$invalid"] will be in disabled mode.
				-->
				<div class="form-group">
					<label for="email">Email</label>
					<!-- ng-required="true" | required for $error.required -->
					<div class="error form-hint" 
					data-ng-show="formName.email.$dirty && formName.email.$error.required" 
					data-ng-cloak>{{"This field is required."}}</div>
					<!--
					name="email" - $error.email for validation state properties. then ng
					ng-model="email77" to transfer data among MVC. 
					-->
					<div class="error form-hint" 
					data-ng-show="formName.email.$dirty && formName.email.$error.email" 
					data-ng-cloak>{{"Email is invalid."}}</div>

					<input type="email" class="form-control" id="email2" name="email" placeholder="Enter Email Address" 
					data-ng-class="(formName.email.$dirty && formName.email.$invalid) ? 'error' : ''"
					data-ng-required="true" ng-model="email77">
				</div>

				<div class="form-group">
					<label for="password">Password</label>

					<div class="form-hint">
					To conform with our Strong Password policy,
					you are required to use a sufficiently strong password. 
					Password must be more than 7 characters.</div>

					<input type="password" class="form-control ok-password" 
					data-ng-class="(formName.password.$dirty && formName.password.$invalid) ? 'error' : ''" 
					id="password" name="password" placeholder="Enter Password" data-ng-required="true" data-ng-model="password">
					
					<!-- In password to show count in color [success-green : danger-red] -->
					<div class="label password-count" 
					data-ng-class="password.length > 7 ? 'label-success' : 'label-danger'" 
					ng-cloak>{{ password | passwordCount:7 }}</div>

					<div class="strength-meter">
						<div class="strength-meter-fill" data-strength="{{loginModule}}"></div>
					</div>
				</div>
				<!--  btn-primary - bootstrap.min.css removing to change button color -->
				<button id='siginButton' type="submit" class="btn" data-ng-disabled="formName.$invalid">Sign In</button>
				
			</form>
		</div>
	</div>
</body>
</html>
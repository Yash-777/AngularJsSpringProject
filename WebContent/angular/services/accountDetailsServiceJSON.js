/*accountDetailsServiceJSON.js
 * http://stackoverflow.com/a/20751328/5081877
 * http://jimhoskins.com/2012/12/14/nested-scopes-in-angularjs.html
 * https://docs.angularjs.org/guide/services
Creating Services «
===================
Application developers are free to define their own services by registering the service's name and service factory function,
with an AngularJS module.

The service factory function generates the single object or function that represents the service to the rest of the application.
The object or function returned by the service is injected into any component (controller, service, filter or directive) 
that specifies a dependency on the service.

Dependencies «
==============
Services can have their own dependencies. Just like declaring dependencies in a controller,
you declare dependencies by specifying them in the service's factory function signature.*/
(function () {
	angular
	.module('userModule')
	.factory('userJSONService', userJSONServiceFun);
	
	/*userJSONServiceFun.$inject = ['$rootScope'];*/
	
	function userJSONServiceFun() {
		/*var jsonData = {
			userName:"Yash77",
			userEmail:"Yashwanth.merugu@gmail.com",
			designation:"Tester"
		};
		
		return jsonData;*/
		
		
		function serverReq( callback ) {
			
			var targetRequestPath = '../user/getAllScenarioDetailsOfUser';
			var targetRequestParamsREQ = { 'email': USERDETAILS.EMAIL_ID };
			$http({
				method: 'POST',
				url: targetRequestPath,
				headers: {'Content-Type': 'application/json'},
				data: targetRequestParamsREQ
			/*}).then(function (response) {
				console.log('Response Data : ', response.data);
				callback( response.data );
			})*/
			}).success( function(data) {
				console.log('Response Data : ', data);
				callback( response.data );
				if( data == 'valid' ) {
					console.log("Authentication Status : Pass « ");
				} else {
					console.log("Authentication Status : Fail « DB Problem");
				}
			}).error(function(data){
				console.log('Error message. ', data);
				callback( response.data );
			});
		}
		function pojoData() {
			
			var manageLinkData = [
				["Yash","26"],
				["Sam","25"]
			];
			return manageLinkData;
		}
		
		return {
			pojoData : pojoData,
			serverReq : serverReq
		};
	}
	
	
})();
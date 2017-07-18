/**
 * encodeURI.js - To encode and decode parameters of an URL
 * 
 */
var app = angular.module('userModule');
app.filter('encode', function( ) {
	return function (data){
		console.log('Encoded data « ',btoa(data) );
		return  btoa(data);
	} ;
});
app.filter('decode', function( ) {
	return function (data){
		console.log('Decoded data « ',btoa(data) );
		return  atob(data);
	};
});
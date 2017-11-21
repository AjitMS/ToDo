var todo = angular.module('todo');
todo.factory('registrationService', function($http) {
	var methodChain = {};
	methodChain.registeruser = function(user) {
		return $http({
			method : 'POST',
			url : 'register',
			data : user

		});
		
	}
	methodChain.userexists = function(user) {
		return $http({
			method : 'POST',
			url : 'userexists',
			data : user

		});
		
	}	
	return methodChain;	
	
});
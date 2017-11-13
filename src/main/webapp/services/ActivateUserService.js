var todo = angular.module('todo');
todo.factory('activateUserService', function($http) {
	var methodChain = {};
	methodChain.activateuser = function(userId) {
		console.log((userId));
		return $http({
			method : 'GET',
			url : 'register/activateuser',
			headers : {
				'userId' : userId
			}
		});

	}
	return methodChain;

})
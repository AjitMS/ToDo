var todo = angular.module('todo');
todo.factory('resetPasswordService', function($http) {
	var methodChain = {};
	methodChain.validatetoken = function(token) {
		return $http({
			method : 'POST',
			data : token,
			url : 'resetpasswordtoken'
		});
	};
	return methodChain;
});
var todo = angular.module('todo');
todo.factory('resetPasswordService', function($http) {
	var methodChain = {};
	methodChain.resetpassword = function(user) {
		return $http({
			method : 'POST',
			data : user,
			url : 'resetpassword'
		});
	};
	return methodChain;
});
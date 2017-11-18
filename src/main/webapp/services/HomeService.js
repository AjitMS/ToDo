var todo = angular.module('todo');
todo.factory('homeService', function($http) {
	var methodChain = {};
	methodChain.getnotes = function(user, accessToken, refreshToken) {
		return $http({
			method : 'GET',
			data : user,
			headers :{
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes'
		});
	}
	return methodChain;
})

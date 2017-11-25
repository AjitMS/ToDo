var todo = angular.module('todo');
todo.factory('dummyService', function($http) {
	var methodChain = {};
	methodChain.dummyhit = function(userId) {
		return $http({
			method : 'POST',
			url : 'dummyhit',
			data : userId
			
		})

	}
	return methodChain;
})
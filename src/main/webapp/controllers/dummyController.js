var todo = angular.module('todo');
todo.controller('dummyController', function($scope, $location, dummyService,
		$stateParams) {
	console.log('inside dummy');
	var userId = $stateParams.id;
	var user = {};
	console.log('got id as: ' + userId);
	var dummyHit = dummyService.dummyhit(userId);
	dummyHit.then(function(response) {
		var accessToken = JSON.stringify(response.data[0]);
		var refreshToken = JSON.stringify(response.data[1]);
		localStorage.setItem('accessToken', accessToken);
		localStorage.setItem('refreshToken', refreshToken);
		$location.path('/home');
	}, function(response) {
		console.log('token not received');
		$location.path('/login');
	})
});
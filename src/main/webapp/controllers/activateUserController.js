var todo = angular.module('todo');
todo.controller('activateUserController', function($location, $scope,
		activateUserService, $stateParams) {
	userId = $stateParams.userId;
	console.log('got user id: ' + userId);
	var httpresult = activateUserService.activateuser(userId);
	httpresult.then(function(response) {
		console.log('User activated successfully');
		$location.path('home');
	}, function(response) {
		console.log('User activation failed');
		$location.path('/register');
	});

});
var todo = angular.module('todo');
todo.controller('homeController', function($location, $scope, homeService) {

	var accessToken = JSON.parse(localStorage.getItem('accessToken')).tokenValue;
	var refreshToken = JSON.parse(localStorage.getItem('refreshToken')).tokenValue;
	
	var getNotes = homeService.getnotes($scope.user, accessToken, refreshToken);
	getNotes.then(function(response) {
		console.log('notes loaded');
		console.log(response.data);
		$scope.notes =(response.data);
	}, function(response) {
		console.log('error loading notes');
	});
});
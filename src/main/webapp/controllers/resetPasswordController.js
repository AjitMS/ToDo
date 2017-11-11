var todo = angular.module('todo');
todo.controller('resetPasswordController', function($scope,
		resetPasswordService, $location, $stateParams) {
	console.log('into reset password service');

	var token = {};
	token.userId = $stateParams.userId;
	token.tokenValue = $stateParams.token;

	console.log('token: ' + token.tokenValue + ' userId: ' + token.userId);

	$scope.validateToken = function() {
		$scope.error = false;
		token.userId = $stateParams.userId;
		token.tokenValue = $stateParams.token;

		var tokenvalidation = resetPasswordService.validatetoken(token);

		httpresult.then(function(response) {
			console.log('Token validated successfully');
			$scope.error = false;

			/* $location.path('/login'); */
		}, function(response) {
			$scope.error = true;
			console.log('Token validation failed');
			/* $location.path('/resetPassword'); */
		});
	};

});
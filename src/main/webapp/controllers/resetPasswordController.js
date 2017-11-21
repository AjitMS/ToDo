var todo = angular.module('todo');
todo.controller('resetPasswordController', function($scope,
		resetPasswordService, $location, $stateParams) {
	
	
	
	$scope.reset = function() {
		console.log('hello');
		$scope.user.id = JSON.parse(localStorage.getItem('forgotToken')).userId;
		var httpResponse = resetPasswordService.resetpassword($scope.user);
		httpResponse.then(function(response) {
			$scope.passwordError = false;
			console.log('password reset success !');
			alert('password has been reset. please login with new password');
			$location.path('/login');

		}, {
			function(response){
			console.log('passwords do not match !');
			$scope.passwordError = true;
			}
		});
	}
	
	
	console.log('into reset password service');
	$scope.error = false;
	var userToken = {};
	userToken.userId = $stateParams.userId;
	userToken.tokenValue = $stateParams.token;
	console.log('user token: ' + userToken.tokenValue + ' userId: '
			+ userToken.userId);
	var forgotToken = JSON.parse(localStorage.getItem('forgotToken'));
	console.log('localstorage forgotToken: ' + forgotToken.tokenValue
			+ ' userId: ' + forgotToken['userId']);
	if (userToken.tokenValue == forgotToken.tokenValue) {
		console.log('tokenValue validated')
		if (userToken.userId == forgotToken.userId) {
			console.log('token userID validated')
			console.log('Token validated successfully');
			$scope.error = false;
		}
	}

	/* $location.path('/login'); */
	else {
		$scope.error = true;
		console.log('Token validation failed');
		/* $location.path('/resetPassword'); */
	}
	;

	

});
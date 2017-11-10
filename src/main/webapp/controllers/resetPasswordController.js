var todo = angular.module('todo');
todo.controller('resetPasswordController', function($scope,
		forgotPasswordService, $location) {

	$scope.email = {};

	$scope.forgotPassword = function() {
		// console.log('got email is: '+$scope.user.email);
		var httpresult = forgotPasswordService.getToken($scope.user);

		httpresult.then(function(response) {
			console.log('Password changed successfully');
			$location.path('/resetPassword');
		}, function(response) {
			console.log('password change failed');
			$scope.error = true;
			function toggleError() {
				$scope.error === true ? false : true;
			}
			$location.path('/forgotPassword');
		});
	};

});
var todo = angular.module('todo');
todo.controller('forgotPasswordController', function($scope,
		forgotPasswordService, $location) {

	$scope.email = {};

	$scope.forgotPassword = function() {
		//console.log('got email is: '+$scope.user.email);
		var httpresult = forgotPasswordService.getToken($scope.user);

		httpresult.then(function(response) {
			console.log('Mail sent successfully');
			/*$location.path('/resetPassword');*/
			$scope.success = true; 
			$scope.error = false;
			
		}, function(response) {
			console.log('Mail not sent');
			$scope.error = true;
			$scope.success = false;

			/*$location.path('/forgotPassword');*/
		});
	};
	
/*	$scope.resetPassword = function() {
		//console.log('got email is: '+$scope.user.email);
		var httpresult = forgotPasswordService.getToken($scope.user);

		httpresult.then(function(response) {
			console.log('Token verified successfully');
			$location.path('/resetPassword');
		}, function(response) {
			console.log('Token not verified');
			$scope.error = false;
			function toggleError() {
				$scope.error === true ? false : true;
			}
			$location.path('/resetPassword');
		});
	};
*/
})

var todo = angular.module('todo');
todo.controller('forgotPasswordController', function($scope,
		forgotPasswordService, $location) {

	//$scope.email = {};

	$scope.forgotPassword = function() {
		$scope.waiting = true;
		// console.log('got email is: '+$scope.user.email);
		var httpresult = forgotPasswordService.getToken($scope.user);

		httpresult.then(function(response) {
			localStorage.setItem('forgotToken', JSON.stringify(response.data));
			console.log('Mail sent successfully');
			var forgotToken = JSON.parse(localStorage.getItem('forgotToken'));
			console.log('localstorage forgotToken: ' + forgotToken.tokenValue
					+ ' userId: ' + forgotToken['userId']);

			$scope.waiting = false;
			$scope.success = true;
			$scope.error = false;

		}, function(response) {
			console.log('Mail not sent');
			$scope.waiting = false;
			$scope.success = false;
			$scope.error = true;

			/* $location.path('/forgotPassword'); */
		});
	};

	/*
	 * $scope.resetPassword = function() { //console.log('got email is:
	 * '+$scope.user.email); var httpresult =
	 * forgotPasswordService.getToken($scope.user);
	 * 
	 * httpresult.then(function(response) { console.log('Token verified
	 * successfully'); $location.path('/resetPassword'); }, function(response) {
	 * console.log('Token not verified'); $scope.error = false; function
	 * toggleError() { $scope.error === true ? false : true; }
	 * $location.path('/resetPassword'); }); };
	 */
})

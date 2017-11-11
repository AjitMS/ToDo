var todo = angular.module('todo');
todo.factory('forgotPasswordService', function($http){
	console.log('in forgot password service');
	var passwordChain = {};
	
	passwordChain.getToken = function(user){
		return $http({
			method : 'POST',
			url : 'forgotpassword',
			data : user
		});
	}
	return passwordChain;
	/*passwordChain.sendPassword = function(user){
		return $http({
			method : POST,
			url : resetpassword,
			data : user
		});
	}*/
	
	
});
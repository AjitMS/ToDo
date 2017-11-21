var todo = angular.module('todo');
todo.controller('registrationController', function($scope, $location, registrationService, $stateParams){
	console.log('into registration controller');
	
	$scope.emailexists = false;
	$scope.passwordmismatch = false;
	$scope.passwordmatch = function($event){
		if($scope.user.password == $scope.user.confirmPassword){
			$scope.passwordmismatch = false;
		}
			
		else
			$scope.passwordmismatch = true;
	}
	
	$scope.register = function(){
		console.log('registering...')
		var registerResult = registrationService.registeruser($scope.user);
		registerResult.then(function(response){
			console.log('user registered successfully');
			$location.path('home');
		},{
			function(response){
				console.log('user registration failed');	
			}
			
		});
	}
	$scope.userExist = function($event){
		console.log('into userExists()');
		var registerResult = registrationService.userexists($scope.user);
		registerResult.then(function(response){
			console.log('user registered successfully');
			/*$location.path('home');*/
		},{
			function(response){
				$scope.emailexists = true;
				console.log('user registration failed');	
			}
		});
	}
	
	
	
});
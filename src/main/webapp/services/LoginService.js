var todo = angular.module('todo');
todo.factory('loginService', function($http){
    console.log('in service');    
var login = {};

login.loginuser = function(user){
    return $http({
        method : "POST",
        url : 'login',
        data : user
    });
    console.log('user is: '+user)
}
//return login response from backend

login.fbLogin = function(user){
	return $http({
		url : 'fbconnect',
		method : 'GET',
		data : 'user'
	});
}

login.gLogin = function(user){
	return $http({
		url : 'gconnect',
		method : 'GET',
		data : 'user'
	});
}
return login;
});
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
return login;
});
var todo = angular.module('todo');
todo.controller('loginController', function ($scope, loginService, $location) {
    console.log('In controller');
    $scope.user = {};

    //for logging user
    $scope.loginUser = function () {
        var httpLogin = loginService.loginuser($scope.user);
        httpLogin.then(function (response) {
            //success logic
            console.log('Login success');
            $location.path('/home');
        }, function (response) {
            //failure logic
            console.log('Login failed');
            $location.path('/login');
        });

    }

    //for registration
    $scope.redirectToRegistration = function () {
        $location.path('/registration');

    }

    //for forgot passoword
    $scope.redirectToForgotPassword = function () {
        $location.path('/forgotpassword');
    }

})
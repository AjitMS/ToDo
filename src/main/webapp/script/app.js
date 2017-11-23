var todo = angular.module('todo', [ 'ui.router' ]);

todo.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider.state('registration', {
		url : '/registration',
		controller : 'registrationController',
		templateUrl : 'templates/registration.html'
	}).state('login', {
		url : '/login',
		controller : 'loginController',
		templateUrl : 'templates/login.html'
	}).state('forgotPassword', {
		url : '/forgotpassword',
		controller : 'forgotPasswordController',
		templateUrl : 'templates/forgotPassword.html'
	}).state('home', {
		url : '/home',
		controller : 'homeController',
		templateUrl : 'templates/homepage.html'
	}).state('trash', {
		url : '/trash',
		controller : 'homeController',
		templateUrl : 'templates/trash.html'
	}).state('archive', {
		url : '/archive',
		controller : 'homeController',
		templateUrl : 'templates/archives.html'
	}).state('resetPassword', {
		url : '/resetPassword/:userId/:token',
		controller : 'resetPasswordController',
		templateUrl : 'templates/resetPassword.html'
	}).state('activateUser', {
		url : '/register/activateuser/:userId',
		controller : 'activateUserController',
		templateUrl : 'templates/registration.html'
	}).state('dummy', {
		url : '/dummy/:id',
		controller : 'dummyController',
		templateUrl : 'templates/dummy.html'
	})

	$urlRouterProvider.otherwise('login');

});
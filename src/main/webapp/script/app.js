var todo = angular
	.module('todo', ['ui.router', 'ngSanitize', 'ui.bootstrap']);

todo.config(function ($stateProvider, $urlRouterProvider) {
	$stateProvider.state('registration', {
		url: '/registration',
		controller: 'registrationController',
		templateUrl: 'templates/registration.html'
	}).state('login', {
		url: '/login',
		controller: 'loginController',
		templateUrl: 'templates/login.html'
	}).state('forgotPassword', {
		url: '/forgotpassword',
		controller: 'forgotPasswordController',
		templateUrl: 'templates/forgotPassword.html'
	}).state('home', {
		url: '/home',
		controller: 'homeController',
		templateUrl: 'homepage.html'
	}).state('trash', {
		url: '/trash',
		controller: 'homeController',
		templateUrl: 'templates/trash.html'
	}).state('archive', {
		url: '/archive',
		controller: 'homeController',
		templateUrl: 'templates/archives.html'
	})

	$urlRouterProvider.otherwise('home');

});
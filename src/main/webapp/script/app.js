var toDo = angular
	.module('toDo', ['ui.router', 'ngSanitize', 'ui.bootstrap']);

toDo.config(function ($stateProvider, $urlRouterProvider) {
	$stateProvider.state('registration', {
		url: '/registration',
		controller: 'registrationController',
		templateUrl: 'templates/registration.html'
	}).state('login', {
		url: '/login',
		controller: 'loginController',
		templateUrl: 'templates/login.html'
	}).state('forgotPassword', {
		url: '/forgotPassword',
		controller: 'forgotPasswordController',
		templateUrl: 'templates/forgotPassword.html'
	}).state('home', {
		url: '/home',
		controller: 'homeController',
		templateUrl: 'templates/home.html'
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
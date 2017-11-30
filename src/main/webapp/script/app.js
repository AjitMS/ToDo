var todo = angular.module('todo', [ 'ui.router', 'ngFileUpload',
		'tb-color-picker', '720kb.datepicker', 'ui.bootstrap.datetimepicker',
		'toastr' ]);

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
		templateUrl : 'templates/archive.html'
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
	}).state('reminder', {
		url : '/reminder',
		controller : 'homeController',
		templateUrl : 'templates/reminder.html'
	}).state('label/:labelName', {
		url : '/label/:labelName',
		controller : 'homeController',
		templateUrl : 'templates/userLabel.html'
	})

	$urlRouterProvider.otherwise('login');

});
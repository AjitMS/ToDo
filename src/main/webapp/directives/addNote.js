var todo = angular.module('todo');
todo.directive('addNote', function() {
	return {
		templateUrl : 'templates/addNote.html'
	}
});
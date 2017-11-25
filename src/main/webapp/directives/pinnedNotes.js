var todo = angular.module('todo');
todo.directive('pinnedNotes', function() {
	return {
		templateUrl : 'templates/pinnedNotes.html'
	}
});
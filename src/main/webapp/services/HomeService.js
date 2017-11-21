var todo = angular.module('todo');
todo.factory('homeService', function($http) {
	var methodChain = {};
	methodChain.getnotes = function(accessToken, refreshToken) {
		return $http({
			method : 'GET',
			data : {
				noteCategory : null
			},
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes'
		});
	}

	methodChain.createnote = function(note, accessToken, refreshToken) {
		return $http({
			method : 'POST',
			data : note,
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes/createnote'
		});
	}

	methodChain.updatenote = function(updatedNote, accessToken, refreshToken) {
		return $http({
			method : 'PUT',
			data : updatedNote,
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes/updatenote'
		});
	}

	methodChain.deletenote = function(noteId, accessToken, refreshToken) {
		return $http({
			method : 'DELETE',
			data : noteId,
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes/deletenote'
		});
	}

	methodChain.trashnote = function(note, accessToken, refreshToken) {
		return $http({
			method : 'PUT',
			data : note,
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes/movetotrash'
		});
	}

	methodChain.archivenote = function(note, accessToken, refreshToken) {
		return $http({
			method : 'PUT',
			data : note,
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes/update'
		});
	}

	methodChain.pinnote = function(note, accessToken, refreshToken) {
		return $http({
			method : 'PUT',
			data : note,
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes/updatenote'
		});
	}

	methodChain.getpinnednotes = function(accessToken, refreshToken) {
		return $http({
			method : 'GET',
			data : {
				noteCategory = 'pinned'
			},
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes'
		});
	}

	methodChain.getarchivednotes = function(accessToken, refreshToken) {
		return $http({
			method : 'GET',
			data : {
				noteCategory = 'archived'
			},
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes'
		});
	}

	methodChain.gettrashednotes = function(note, accessToken, refreshToken) {
		return $http({
			method : 'GET',
			data : {
				noteCategory = 'trashed'
			},
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes'
		});
	}

	return methodChain;
})

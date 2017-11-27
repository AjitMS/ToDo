var todo = angular.module('todo');
todo.factory('homeService', function($http) {
	var methodChain = {};

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
			url : 'usernotes/updatenote'
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

	methodChain.color = function(note, accessToken, refreshToken) {
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

	methodChain.getnotes = function(accessToken, refreshToken) {
		return $http({
			method : 'GET',
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes'
		});
	}

	methodChain.getuserbyid = function(accessToken, refreshToken) {
		return $http({
			method : 'GET',
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken
			},
			url : 'usernotes/getuserbyid'
		});
	}

	methodChain.logout = function(accessTokenObject, refreshTokenObject) {
		var accessString = JSON.stringify(accessTokenObject);
		var refreshString = JSON.stringify(refreshTokenObject);
		console.log('tokens are: ' + accessString + 'and :' + refreshString);
		return $http({
			method : 'POST',
			headers : {
				'accessTokenString' : accessString,
				'refreshTokenString' : refreshString
			},
			url : 'logout'
		});
	}

	methodChain.collaborateuser = function(accessToken, refreshToken, user,
			note) {
		return $http({
			method : 'POST',
			headers : {
				'accessToken' : accessToken,
				'refreshToken' : refreshToken,
				'userEmail' : user.email
			},
			data : note,
			url : 'usernotes/collaborate'
		});
	}

	methodChain.uncollaborateuser = function(note, accessToken, refreshToken) {
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

	methodChain.userexists = function(note) {
		return $http({
			method : POST,
			data : note,
			url : 'userexists'
		});
	}

	return methodChain;
})

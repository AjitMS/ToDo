var todo = angular.module('todo');
todo
		.controller(
				'homeController',
				function($state, $location, $scope, homeService) {
					$scope.gridView = "card note col-xl-4 col-lg-4 col-md-8 col-sm-8 col-xs-12";
					$scope.listView = "card note col-xs-12 col-lg-12";
					$scope.view = $scope.gridView;
					$scope.grid = false;
					$scope.list = true;
					var accessToken = JSON.parse(localStorage
							.getItem('accessToken')).tokenValue;
					var refreshToken = JSON.parse(localStorage
							.getItem('refreshToken')).tokenValue;
					$scope.notes = [];
					$scope.trashedNotes = [];
					$scope.pinnedNotes = [];
					$scope.archivedNotes = [];

					$scope.toggleView = function() {
						$scope.grid = !$scope.grid;
						$scope.list = !$scope.list;
						if ($scope.view == $scope.gridView)
							$scope.view = $scope.listView;
						else
							$scope.view = $scope.gridView;
					}

					$scope.createNote = function(newNote) {
						document.getElementById("takenote").reset();
						console.log('creating note...');
						var httpresponse = homeService.createnote(newNote,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							$scope.notes.push(response.data);
							console.log('note created');
							return;

						}, function(response) {
							console.log('note cannot be created');
							$scope.notes.push($scope.note);
							document.getElementById("takenote").reset();
							return;
						});

					}

					$scope.showNote = function(note) {
						note = angular.copy(note);
						console.log(note);
						$scope.updatedNote = note;
						$('#showNote').modal('show');
					}

					var getNoteById = function(noteId) {
						for (var i = 0; i < $scope.notes.length; i++)
							if ($scope.notes[i].noteId == noteId)
								return $scope.notes[i];

					}

					$scope.updateNote = function(updatedNote) {
						console.log('updating note...'
								+ JSON.stringify(updatedNote));
						var httpresponse = homeService.updatenote(updatedNote,
								accessToken, refreshToken);
						httpresponse
								.then(
										function(response) {
											for (var i = 0; i < $scope.notes.length; i++)
												if ($scope.notes[i].noteId == updatedNote.noteId) {
													$scope.notes[i] = updatedNote;
												}

											console.log('note updated');
											document.getElementById("takenote")
													.reset();
											/* $state.reload(); */
											return;
										},
										function(response) {
											console
													.log('note cannot be updated');
											document.getElementById("takenote")
													.reset();
											return;
										});
					}

					$scope.deleteNote = function(noteId) {
						console.log('deleting note...' + noteId);
						var httpresponse = homeService.deletenote(noteId,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							var index = $scope.notes.indexOf(note);
							$scope.notes.splice(index, 1);
							console.log('note deleted');
							return;
						}, function(response) {
							console.log('note cannot be deleted');
							return;
						});
					}

					$scope.trashNote = function(note) {
						console.log('deleting note...' + note);

						var httpresponse = homeService.trashnote(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							var index = $scope.notes.indexOf(note);
							$scope.notes.splice(index, 1);
							$scope.trashedNote.push(note);
							console.log('note deleted');
							return;
						}, function(response) {
							console.log('note cannot be deleted');
							return;
						});
					}

					$scope.copyNote = function(note) {
						console.log('copying note...' + note);
						var httpresponse = homeService.createnote(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							$scope.notes.push(note);
							console.log('note copied');
							return;
						}, function(response) {
							console.log('note cannot be deleted');
							return;
						});
					}

					$scope.archiveNote = function(note) {
						console.log('archiving note...' + note);
						var httpresponse = homeService.movetoarchive(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							var index = $scope.notes.indexOf(note);
							$scope.notes.splice(index, 1);
							$scope.archivedNotes.push(note);
							console.log('note archived');
							/* $state.reload(); */
							return;
						}, function(response) {
							console.log('note cannot be deleted');
							return;
						});
					}

					$scope.pinNote = function(note) {
						console.log('pinning note...' + note);
						note.isPinned = true;
						var httpresponse = homeService.movetoarchive(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							var index = $scope.notes.indexOf(note);
							$scope.notes.splice(index, 1);
							$scope.pinnedNotes.push(note);
							console.log('note archived');
							return;
						}, function(response) {
							console.log('note cannot be deleted');
							return;
						});
					}

					var getNotes = homeService.getnotes($scope.user,
							accessToken, refreshToken);
					getNotes.then(function(response) {
						console.log('notes loaded');
						console.log(response.data);
						$scope.notes = (response.data);
					}, function(response) {
						console.log('error loading notes');
					});

					var getTrashedNotes = homeService.gettrashednotes(
							$scope.user, accessToken, refreshToken);
					getTrashedNotes.then(function(response) {
						console.log('notes loaded');
						console.log(response.data);
						$scope.trashedNotes = (response.data);
					}, function(response) {
						console.log('error loading trashed notes');
					});

					var getPinnedNotes = homeService.getpinnednotes(
							$scope.user, accessToken, refreshToken);
					getPinnedNotes.then(function(response) {
						$scope.pinnedNotes = (response.data);
					}, function(response) {
						console.log('error loading pinned notes');
					});

					var getArchivedNotes = homeService.getarchivednotes(
							$scope.user, accessToken, refreshToken);
					getArchivedNotes.then(function(response) {
						$scope.archivedNotes = (response.data);
					}, function(response) {
						console.log('error archived loading notes');
					});

				});
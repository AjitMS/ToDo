var todo = angular.module('todo');
todo
		.controller(
				'homeController',
				function($state, $location, $scope, homeService) {
					$scope.gridView = "card note col-xl-3 col-lg-4 col-md-8 col-sm-8 col-xs-12";
					$scope.listView = "card note col-xs-12 col-lg-12";
					$scope.view = $scope.gridView;
					$scope.grid = false;
					$scope.list = true;
					var accessToken = JSON.parse(localStorage
							.getItem('accessToken')).tokenValue;
					var refreshToken = JSON.parse(localStorage
							.getItem('refreshToken')).tokenValue;
					$scope.notes = [];

					$scope.colors = {
						color1 : "#FFFFFF",
						color2 : "#FF8981",
						color3 : "#FFD086",
						color4 : "#FFFF95",
						color5 : "#C9FF97",
						color6 : "#9EFFEB",
						color7 : "#74D7FD",
						color8 : "#7AB0FB",
						color9 : "#B188FA",
						color10 : "#FBBACE",
						color11 : "#D7CBC7",
						color12 : "#CDD7DB"
					};

					var getUserById = homeService.getuserbyid(accessToken, refreshToken);
						getUserById.then(function(response) {
							console.log('logged in user is: ' +JSON.stringify(response.data));
							$scope.loggedUser = response.data;
						}, function(response) {
							console.log('Error getting user');
						});

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
							note.inTrash = true;
							var index = $scope.notes.indexOf(note);
							if (note.isPinned) {
								var index = $scope.pinnedNotes.indexOf(note);
								$scope.pinnedNotes.splice(index, 1);
								note.isPinned = false;

								$scope.trashedNotes.push(note);
							} else {
								$scope.trashedNote = [];
								console.log($scope.trashedNotes);
								var index = $scope.notes.indexOf(note);
								$scope.notes.splice(index, 1);

							}

							console.log('note deleted');
							note.inTrash = true;
							return;
						}, function(response) {
							console.log('note cannot be deleted');
							return;
						});
					}

					$scope.copyNote = function(note) {
						console.log('copying note...' + note);
						note.isPinned = false;
						var httpresponse = homeService.createnote(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							$scope.notes.push(response.data);
							console.log('note copied');
							return;
						}, function(response) {
							console.log('note cannot be copied');
							return;
						});
					}

					$scope.archiveNote = function(note) {
						console.log('archiving note...' + note);
						note.isPinned = !note.isPinned;
						note.archived = !note.archived;
						var httpresponse = homeService.archivenote(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							console.log('note archived');
							/* $state.reload(); */
							return;
						}, function(response) {
							note.isPinned = !note.isPinned;
							note.archived = !note.archived;
							console.log('note cannot be archived');
							return;
						});
					}

					$scope.pinNote = function(note) {
						console.log('pinning note...' + note);
						note.pinned = !note.pinned;
						var httpresponse = homeService.pinnote(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {

							console.log('note pinned');
							return;
						}, function(response) {
							note.pinned = !note.pinned;
							console.log('note cannot be pinned');
							return;
						});
					}

					var getNotes = homeService.getnotes(accessToken,
							refreshToken);
					getNotes.then(function(response) {
						console.log('notes loaded');
						console.log(response.data);
						$scope.notes = (response.data);
					}, function(response) {
						console.log('error loading notes');
					});

					$scope.colorNote = function(note, color) {
						var temp = color;
						note.color = color;
						console.log('coloring note: ' + note);
						var httpresponse = homeService.color(note, accessToken,
								refreshToken);
						httpresponse.then(function(response) {
							var id = note.noteId;

							console.log('note colored with: ' + color);
						}, function(response) {
							console.log('note cannot be colored');
							note.color = temp;
						})

					}
				});
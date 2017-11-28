var todo = angular.module('todo');
todo
		.controller(
				'homeController',
				function($state, $location, $scope, homeService, uploadService) {
					$scope.gridView = "card note col-xl-3 col-lg-3 col-md-3 col-sm-3 col-xs-3";
					$scope.listView = "card note col-xs-8 col-lg-8";
					$scope.view = $scope.gridView;
					$scope.grid = false;
					$scope.list = true;
					var accessToken = JSON.parse(localStorage
							.getItem('accessToken')).tokenValue;
					var accessTokenObject = JSON.parse(localStorage
							.getItem('accessToken'));
					var refreshToken = JSON.parse(localStorage
							.getItem('refreshToken')).tokenValue;
					var refreshTokenObject = JSON.parse(localStorage
							.getItem('refreshToken'));
					$scope.notes = [];
					$scope.collabNote = {};
					$scope.collabUsers = {};
					$scope.uploadingImageTo = {};

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

					$scope.sidebarOpened = true;

					$scope.refresh = function() {
						$state.reload();
					}

					var getUserById = homeService.getuserbyid(accessToken,
							refreshToken);
					getUserById.then(function(response) {
						console.log('logged in user');
						$scope.loggedUser = response.data;
						if ($scope.loggedUser.image == null)
							$scope.userPicExists = false;
						else
							$scope.userPicExists = true;
					}, function(response) {
						console.log('Error getting user');
					});

					$scope.toggleSidebar = function() {
						if ($scope.sidebarOpened)
							openNav();
						else
							closeNav();
					}

					var openNav = function() {
						document.getElementById("main").style.marginLeft = "10px";
						document.getElementById("mySidenav").style.width = "250px";

					}

					var closeNav = function() {
						document.getElementById("mySidenav").style.width = "0px";
						document.getElementById("main").style.marginLeft = "0px";
					}

					$scope.toggleView = function() {
						$scope.grid = !$scope.grid;
						$scope.list = !$scope.list;
						if ($scope.view == $scope.gridView)
							$scope.view = $scope.listView;
						else
							$scope.view = $scope.gridView;
					}

					$scope.redirectToTrash = function() {
						$location.path('/trash');
					}

					$scope.redirectToArchive = function() {
						$location.path('/archive');
					}

					$scope.redirectToHome = function() {
						$location.path('/home');
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
											/* $state.reload(); */
											return;
										},
										function(response) {
											console
													.log('note cannot be updated');

											return;
										});
					}

					$scope.deleteNote = function(note) {
						console.log('deleting note...' + note.noteId);
						var httpresponse = homeService.deletenote(note.noteId,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							console.log('note cannot be deleted');
							return;
						}, function(response) {
							var index = $scope.notes.indexOf(note);
							$scope.notes.splice(index, 1);
							console.log('note deleted');
							return;

						});
					}

					$scope.trashNote = function(note) {
						console.log('trashing note...' + note);

						note.inTrash = !note.inTrash;
						if (note.pinned) {
							note.pinned = false;
						}
						var httpresponse = homeService.trashnote(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {

							console.log('note trashed');
							return;
						}, function(response) {
							console.log('note cannot be trashed');
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
						note.archived = !note.archived;
						if (note.pinned == true)
							note.pinned = false;
						var httpresponse = homeService.archivenote(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							console.log('note archived');
							return;
						}, function(response) {
							console.log('note cannot be archived');
							return;
						});
					}

					$scope.pinNote = function(note) {
						console.log('pinning note...' + note);
						note.pinned = !note.pinned;
						if (note.archived == true)
							note.archived = false;
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

					$scope.logout = function() {
						console
								.log('logging out user: '
										+ $scope.loggedUser.id);
						var httpresponse = homeService.logout(
								accessTokenObject, refreshTokenObject);
						httpresponse.then(function(response) {
							console.log('user logged out successfully');
							$location.path('/login');
							localStorage.clear();
						}, function(response) {
							console.log('user cannot be logged out');
							$location.path('/login');
						})
					}

					$scope.collaborateModal = function(note) {
						console.log('collaborating note: ' + note.noteId);
						$scope.collabNote = note;
						$scope.collabUsers = note.collabUsers;
						console.log('setting collabNote');
						$('#collaborate-modal').modal('show');
					}

					$scope.collaborateUser = function(user) {
						if (user == null)
							return;
						console.log('collaborating note: '
								+ $scope.collabNote.noteId);
						var note = $scope.collabNote;
						var httpresponse = homeService.collaborateuser(
								accessToken, refreshToken, user, note);
						httpresponse.then(function(response) {
							for (var i = 0; i < $scope.notes.length; i++)
								if ($scope.notes[i] == note.noteId)
									notes[i].collabUsers.push(response.data);
							$scope.collabUsers.push(response.data);

							return;
							console.log('collaborated user successfully');
						}, function(response) {
							console.log('collaborated user failed');
						});
					}

					$scope.unCollaborateUser = function(user, note) {
						console.log('un-collaborating note: ' + note.noteId
								+ ' with user: ' + user.id);
						for (var i = 0; i < note.collabUsers.length; i++)
							if (note.collabUsers[i].id == user.id)
								var index = note.collabUsers.indexOf(user);
							else
								return;
						note.collabUsers.splice(index, 1);

						var httpresponse = homeService.uncollaborateuser(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							console.log('uncollaborated success!');
							var index = $scope.notes.indexOf(note);
							$scope.notes.splice(index, 1);
						}, function(response) {
							// $scope.noSuchEmail = true;
							window.alert('Email is not Registered with us');
							console.log('uncollaborated failed');
						});
					}

					$scope.openFileUpload = function(note) {
						$scope.uploadingImageTo = note;
						$('input[type=file').click();
					}

					$scope.temp = {};
					$scope.upload = function(file) {
						var base64image;
						console.log('uploading image: ');
						console.log(file);
						var reader = new window.FileReader();
						reader.readAsDataURL(file);
						reader.onloadend = function() {
							base64image = reader.result;
						}
						console.log(base64image);
						var newNote = $scope.uploadingImageTo;
						newNote.image = base64image;
						console.log(newNote.image instanceof String);
						console.log(newNote.image);
						var httpresponse = homeService.updatenote(newNote,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							console.log('Image upload success');
						}, function(response) {
							console.log('Image upload failure');
						});
					};

					$scope.$watch('file', function(newfile, oldfile) {
						var newImage = $scope.filepreview;
						$scope.uploadingImageTo.image = newImage;
						var newNote = $scope.uploadingImageTo;
						console.log(newNote);
						var httpresponse = homeService.updatenote(newNote,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							console.log('Image upload success');
						}, function(response) {
							console.log('Image upload failure');
						});
					});

					$scope.removeImage = function(Note) {
						console.log('removing image');
						Note.image = null;
						var httpresponse = homeService.updatenote(Note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							console.log('Image removal success');
						}, function(response) {
							console.log('Image removal failure');
							$state.reload();
						});
					}

					$scope.fbAsyncSocialShare = function(note) {
						console.log(note.title);
						console.log('inside fbAsyncInit');
						FB.init({
							appId : '303734456782451',
							status : true,
							cookie : true,
							xfbml : true,
							version : 'v2.4'
						});

						FB.ui({
							method : 'share_open_graph',
							action_type : 'og.likes',
							action_properties : JSON.stringify({
								object : {
									'og:title' : note.title,
									'og:description' : note.description,
								}
							})
						}, function(response) {
							if (response && !response.error_message) {
								console.log('Posting completed.');
							} else {
								console.log('Error while posting');
							}
						});
					};

				});
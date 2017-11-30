var todo = angular.module('todo');
todo
		.controller(
				'homeController',
				function($state, $location, $scope, $timeout, homeService,
						uploadService, toastr, $stateParams) {
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
					$scope.labels = [];
					$scope.collabNote = {};
					$scope.collabUsers = {};
					$scope.uploadingImageTo = {};
					$scope.labellingNote = {};
					$scope.labelName = {};
					$scope.labelObject = {};

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

					$('.grid').isotope({
						itemSelector : '.note',
						percentPosition : true,
						layoutMode : 'masonry',
						masonry : {
							// use outer width of grid-sizer for columnWidth
							columnWidth : '.grid-sizer'
						}
					});

					$scope.labelName = $stateParams.labelName
					for (var i = 0; i < $scope.labels.length; i++)
						if ($scope.labels[i].name == labelName) {
							$scope.labelObject = $scope.labels[i];
							break;
						}

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
						$state.go('trash');
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
							toastr.success('Note created', 'Success');
							return;

						}, function(response) {
							console.log('note cannot be created');
							$scope.notes.push($scope.note);
							document.getElementById("takenote").reset();
							toastr.error('Note could not be created', 'Error');
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
								+ JSON.stringify(updatedNote.noteId));
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
											toastr.success('Note updated',
													'Success');
											/* $state.reload(); */
											return;
										},
										function(response) {
											console
													.log('note cannot be updated');
											toastr.error(
													'note cannot be updated',
													'error');
											return;
										});
					}

					$scope.deleteNote = function(note) {
						console.log('deleting note...' + note.noteId);
						var httpresponse = homeService.deletenote(note.noteId,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							console.log('note cannot be deleted');
							toastr.error('note cannot be deleted', 'Failure');
							return;
						}, function(response) {
							var index = $scope.notes.indexOf(note);
							$scope.notes.splice(index, 1);
							console.log('note deleted');
							toastr.success('Note deleted forever', 'Success');
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
							toastr.success('Note moved to trash', 'Success');
							console.log('note trashed');
							return;
						}, function(response) {
							toastr.error('note cannot be trashed', 'Failure');
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
							toastr.success('note copied', 'success');
							console.log('note copied');
							return;
						}, function(response) {
							toastr.error('note cannot be copied', 'Error');
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
							toastr.success('note archived', 'success');
							console.log('note archived');
							return;
						}, function(response) {
							toastr.error('note cannot be archived', 'error');
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
							toastr.success('note pinned', 'success');
							console.log('note pinned');
							return;
						}, function(response) {
							note.pinned = !note.pinned;
							toastr.error('note cannot be pinned', 'error');
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
						/* toastr.success('Notes are ready', 'Welcome !'); */

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
							console.log('note colored with: ' + color);
							toastr.success('note colored', 'success')
						}, function(response) {
							toastr.error('note cannot be colored', 'error');
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
							toastr.success('user logged out successfully',
									'success');
							console.log('user logged out successfully');
							$location.path('/login');
							localStorage.clear();
						}, function(response) {
							toastr.error('user cannot be logged out', 'error');
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

					var getEmails;

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
							toastr.success('collaboration complete', 'success')

							return;
							console.log('collaborated user successfully');
						}, function(response) {
							console.log('collaborated user failed');
							toastr.error('collaboration failed', 'error');
						});
					}

					$scope.unCollaborateUser = function(user, note) {
						console.log('un-collaborating note: ' + note.noteId
								+ ' with user: ' + user.id);
						for (var i = 0; i < note.collabUsers.length; i++) {
							console.log('note.collabUsers[i].id: '
									+ note.collabUsers[i].id + ', user.id: '
									+ user.id);
							if (note.collabUsers[i].id == user.id) {

								var index = note.collabUsers.indexOf(user);
								note.collabUsers.splice(index, 1);
							}
						}
						var httpresponse = homeService.uncollaborateuser(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							toastr.success('Note uncollaborated', 'success');
							console.log('uncollaborated success!');
						}, function(response) {
							// $scope.noSuchEmail = true;
							$state.reload();
							toastr
									.error('note cannot be collaborated',
											'error');
							console.log('uncollaborated failed');
						});
					}

					$scope.openFileUpload = function(note) {
						$scope.uploadingImageTo = note;
						$('input[type=file').click();
					}
					$scope.filepreview = "";
					$scope.$watch('filepreview', function(newfile, oldfile) {
						console.log($scope.filepreview == "")
						if ($scope.filepreview != "") {
							var newImage = $scope.filepreview;
							$scope.uploadingImageTo.image = newImage;
							var newNote = $scope.uploadingImageTo;
							$scope.filepreview = "";
							var httpresponse = homeService.updatenote(newNote,
									accessToken, refreshToken);
							$scope.filepreview = "";
							httpresponse.then(function(response) {

								toastr.success('Image uploaded', 'success');
								console.log('Image upload success');
							}, function(response) {

								console.log('Image upload failure');
							});
						}
					});

					$scope.removeImage = function(Note) {
						console.log('removing image');
						Note.image = null;
						var httpresponse = homeService.updatenote(Note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							toastr.success('Image removed', 'success');
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
								toastr.success('Note Shared', 'success')
							} else {
								console.log('Error while posting');
								toastr.error('Note could not be shared',
										'error');
							}
						});
					};

					$scope.setReminder = function(note) {
						console.log('setting reminder for: ' + note.noteId);
						var httpresponse = homeService.updatenote(note,
								accessToken, refreshToken);
						httpresponse.then(function(response) {
							var today = new Date();
							var todaymilli = today.getTime();
							var remindermilli = note.reminder.getTime();
							var alarm = remindermilli - todaymilli;
							if (alarm < 0) {
								toastr.warning('invalid date-time', 'Warning');
								return;
							}
							console.log('todaymilli: ' + todaymilli
									+ ' remindermilli: ' + remindermilli
									+ ' alarm: ' + alarm);

							$timeout(function() {
								console.log('reminding user: ' + note);
								alert(note.title + '__ ' + note.description
										+ '__' + ' set at' + '__'
										+ note.reminder);

								$scope.removeReminder(note);
							}, alarm);

							toastr.success('reminder set', 'success')
						}, function(response) {
							console.log('reminder set failure');
							toastr.error('reminder could not be set', 'error')
							$state.reload();
						});
					}

					$scope.removeReminder = function(note) {

						console.log('removing reminder for: ' + note.noteId);
						var httpresponse = homeService.updatenote(note,
								accessToken, refreshToken);
						note.reminder = null;
						httpresponse.then(function(response) {
							toastr.success('reminder removed', 'success')
							console.log('reminder removal success');
						}, function(response) {
							toastr.error('reminder could not be removed',
									'error');
							console.log('reminder removal failure');
							$state.reload();
						});
					}

					$scope.getProperConverter = function(reminder) {
						var d = new Date(reminder[0], reminder[1], reminder[2],
								reminder[3], reminder[4], 0, 0);
						alert(d);
					}

					$scope.createLabel = function(label) {
						console.log('creating label: ' + JSON.stringify(label));
						var httpresponse = homeService.createlabel(accessToken,
								refreshToken, label);
						httpresponse.then(function(response) {
							console.log('label created: ' + response.data);
							$scope.labels.push(response.data);
						}, function(response) {
							console.log('label cannot be created');
						});
					}

					var getLabels = homeService.getlabels(accessToken,
							refreshToken);
					getLabels.then(function(response) {
						console.log('labels loaded');
						$scope.labels = response.data;
						console.log(response.data);
					}, function(response) {
						console.log('error loading labels');
					});

					$scope.redirectToLabel = function(label) {
						console.log('redirecting to label: ' + label.name);
						$location.path('/label/' + label.name);
					}

					$scope.labelNote = function(label, isLabelled) {
						console.log('isLabelled: ' + isLabelled);
						console.log('isl label null ' + label);
						if (isLabelled) {

							console.log(JSON.stringify(label));
							var note = $scope.labellingNote;
							console.log(note);
							note.label.push(label);
							console.log(note);
							var httpresponse = homeService.updatenote(note,
									accessToken, refreshToken);
							httpresponse.then(function(response) {
								console.log('note labelled');
								console.log(note);

								toastr.success('Note Labelled', 'success');
							}, function(response) {
								/* $state.reload(); */
								toastr.error('Note could not be Labelled',
										'error');
								console.log(note);
							});
						}
					}

					$scope.labelNoteModal = function(note) {
						$('#labelNoteModal').modal('show');
						$scope.labellingNote = note;
					}

					$scope.isLabelExists = function(label) {
						
						console.log($scope.labellingNote);
						if ($scope.labellingNote.label == undefined)
							return false;
						for (var i = 0; i < $scope.labellingNote.label.length; i++)
							if ($scope.labellingNote.label[i] == label)
								return true;
						return false;
					}

				});
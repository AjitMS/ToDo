
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ToDo</title>

<!-- Bootstrap References -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">

<!-- JQuery References  -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
	integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
	integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
	crossorigin="anonymous"></script>


<link rel="stylesheet" type="text/css" href="CSS/signin.css" />
<link rel="stylesheet" type="text/css" href="CSS/homepage.css" />
<link rel="stylesheet" type="text/css" href="CSS/resetPassword.css" />
<link rel="stylesheet" type="text/css" href="CSS/forgotPassword.css" />
<link rel="stylesheet" type="text/css" href="CSS/register.css" />
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">



<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.3/angular-ui-router.min.js"></script>
<script src="script/app.js"></script>

<script src="services/LoginService.js"></script>
<script src="services/HomeService.js"></script>
<script src="services/ForgotPasswordService.js"></script>
<script src="services/ResetPasswordService.js"></script>
<script src="services/RegistrationService.js"></script>
<script src="services/ActivateUserService.js"></script>
<script src="services/DummyService.js"></script>

<script src="controllers/loginController.js"></script>
<script src="controllers/homeController.js"></script>
<script src="controllers/forgotPasswordController.js"></script>
<script src="controllers/resetPasswordController.js"></script>
<script src="controllers/registrationController.js"></script>
<script src="controllers/activateUserController.js"></script>
<script src="controllers/dummyController.js"></script>

<script type="text/javascript" src="directives/navbar.js"></script>
<script type="text/javascript" src="directives/addNote.js"></script>
<script type="text/javascript" src="directives/sidebar.js"></script>
<script type="text/javascript" src="directives/pinnedNotes.js"></script>
<script type="text/javascript" src="directives/otherNotes.js"></script>


</head>
<style>
body{
padding-top : 70px;
}
</style>
<body ng-app="todo">

	<div ui-view></div>
</body>
</html>

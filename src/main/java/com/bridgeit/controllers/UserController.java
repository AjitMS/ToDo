package com.bridgeit.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;
import com.bridgeit.entity.UserLoginPair;
import com.bridgeit.service.TokenService;
import com.bridgeit.service.UserService;
import com.bridgeit.utilities.Encryption;

/**
 * @author Ajit Shikalgar
 *
 */
@RestController
public class UserController {

	Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	UserService userService;

	User user;

	@Autowired
	TokenService tokenService;
	@Autowired
	Encryption encryption;

	/**
	 * @return welcome screen (default)
	 */
	/*
	 * @GetMapping("/") public ResponseEntity<String> welcomeUser() { String welcome
	 * =
	 * "**Welcome to ToDo App**<br> use /login to login, \t<br> use /register to register,<br> \n use /fbconnect to login social<br>"
	 * ; return new ResponseEntity<String>(welcome, HttpStatus.ACCEPTED); }
	 */
	/**
	 * @param loginPair
	 * @param request
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 *             API required to login and authentication of user 2 step
	 *             verification is implemented while login after login success, user
	 *             is assigned tokens
	 */
	@PostMapping("/login")
	public ResponseEntity<List<Token>> loginUser(@RequestBody UserLoginPair loginPair, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		if (loginPair.getEmail() == null || loginPair.getPassword() == null || loginPair.getEmail() == ""
				|| loginPair.getPassword() == "") {
			logger.info("Empty Creds !");
			return new ResponseEntity<List<Token>>(HttpStatus.BAD_REQUEST);
		}
		logger.info("Only an activated user can log in");
		logger.info("login pair is: " + loginPair);
		String email = loginPair.getEmail();
		String password = loginPair.getPassword();
		// grab entire user by email if proper credentials
		try {
			user = userService.getUserByEmail(email, user);
		} catch (Exception E) {
			logger.info("Invalid User credentials");
			return new ResponseEntity<List<Token>>(HttpStatus.BAD_REQUEST);
		}
		System.out.println("user is valid " + user.getIsValid());
		if (!user.getIsValid()) {
			logger.info("Please acivate user");
			return new ResponseEntity<List<Token>>(HttpStatus.FORBIDDEN);
		}
		if (userService.loginUser(email, password)) {
			System.out.println("login success");
			user = userService.getUserByEmail(email, user);

			// TokenGenerator generator = new TokenGenerator();

			// stop making objects. instead use @Autowired
			// make sure user prototype type of @Autowired instead of singleton
			// mistakenly @Autowired token and it returned the same token always for both
			// refresh and access

			Token accessToken = tokenService.generateTokenAndPushIntoRedis(user.getId(), "accesstoken");
			Token refreshToken = tokenService.generateTokenAndPushIntoRedis(user.getId(), "refreshtoken");
			List<Token> tokenList = new ArrayList<>();
			tokenList.add(accessToken);
			tokenList.add(refreshToken);

			// generate token for specific user id and store it in REDIS
			// send token link to user email
			// userService.sendLoginVerificationToken(user, accessToken, request);

			logger.info("Email has been sent to  " + user.getEmail() + " .please check");
			return new ResponseEntity<List<Token>>(tokenList, HttpStatus.OK);
		}
		logger.error("User does not exist. Login failed");
		return new ResponseEntity<List<Token>>(HttpStatus.FORBIDDEN);

	}

	/**
	 * @param userId
	 * @param userTokenId
	 * @return an email is sent to user while log in with a accessToken in it this
	 *         API checks if token in mail matches accessToken stored in redis for
	 *         same user
	 */
	@GetMapping("#!/login/{userId}/{tokenId}")
	public ResponseEntity<String> verifyLoginToken(@PathVariable("userId") Integer userId,
			@PathVariable("tokenId") String userTokenId, HttpServletResponse response) {

		// first validate access token is intact, if yes login

		if (tokenService.verifyUserToken(userTokenId).compareTo(userId) == 0) {
			logger.info("Congratulations ! Access Token validation sucess");
			return new ResponseEntity<String>("Token authenticated ! Redirecting...", HttpStatus.ACCEPTED);
		} else {
			// else validate refresh token

			logger.error("Access token validation failed, starting refresh token validation");
			if (tokenService.verifyUserToken(userTokenId).compareTo(userId) == 0) {

				// and generate another new access token and login
				Token newToken = tokenService.generateTokenAndPushIntoRedis(userId, "accessToken");

				logger.info("New access token is generated as: +" + newToken + " for user " + user.getId());
				ResponseEntity.ok();
				return new ResponseEntity<String>(HttpStatus.OK);
			}
			// if refresh token fails, login fails
			else
				System.out.println("Refresh token validation failed");

			return new ResponseEntity<String>("Token not authenticated !", HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * @param user
	 * @param request
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 *             this API will be hit when user clicks on forgot password user
	 *             will be redirected to a page where user must enter email. if
	 *             email exists, user will be sent a verification mail with a token
	 *             in it.
	 */
	@PostMapping("/forgotpassword")
	public ResponseEntity<String> forgotPassword(@RequestBody User user, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		try {
			user = userService.getUserByEmail(user.getEmail(), user);
			if (user == null) {
				logger.debug("No such email registered");
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			logger.info("email is: " + user.getEmail());
			logger.info("user is: " + user);
		} catch (Exception E) {
			logger.debug("***No such email registered***");
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		}

		// generating user token for forgot password
		// generator is autowired
		String tokenType = "forgottoken";
		Token forgotToken = tokenService.generateTokenAndPushIntoRedis(user.getId(), tokenType);
		userService.sendResetPasswordMail(user, request, forgotToken);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @param userTokenId
	 * @return if user has proper forgot password token, as in email sent, user will
	 *         be redirected to another page in which user should enter a new
	 *         password
	 * @throws IOException 
	 * 
	 */
	@GetMapping("/resetpasswordtoken/{uId}/{token}")
	public ResponseEntity<String> validateResetPasswordToken(@PathVariable("uId") Integer uId,
			@PathVariable("token") String tokenValue, HttpServletResponse response) throws IOException {
		logger.info(" userTokenId: " + uId);
		if (tokenService.verifyUserToken(tokenValue).compareTo(uId) == 0) {
			response.addIntHeader("userId", uId);
			
			response.sendRedirect("#!/resetPassword");
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		logger.info("token validaion failed");
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/resetpassword")
	public ResponseEntity<String> resetPassword(@RequestBody User user) {
		try {
			if (user.getPassword().equals(user.getConfirmPassword())) {
				userService.resetPassword(user.getEmail(), user.getPassword());
				return new ResponseEntity<String>(HttpStatus.OK);

			}
		} catch (Exception E) {
			E.printStackTrace();
		}
		return new ResponseEntity<>("passwords do not match", HttpStatus.NO_CONTENT);
	}

	/**
	 * @param user
	 * @param bindingResult
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 *             API that deals with registering user.
	 */
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid User user, BindingResult bindingResult)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		System.out.println("WOOHOO !");
		if (bindingResult.hasErrors()) {
			logger.info("Errors are: " + bindingResult);
			logger.info("User details: " + user);
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
		logger.info("User details: " + user);

		// saving user if not exists
		if (!userService.userExists(user)) {
			userService.registerUser(user);
			logger.info("Register Success");
		} else {
			return new ResponseEntity<String>("User Exists, please login. or forgot password ?",
					HttpStatus.NOT_ACCEPTABLE);
		}

		// Email verification
		userService.sendRegistrationVerificationLink(user.getId(), user.getEmail());

		String greeting = "Thank you! \n A verification email has been sent to " + user.getEmail()
				+ ". confirm registration by accessing link in the mail";

		return new ResponseEntity<String>(greeting, HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return registered user must be activated by accessing link provided in the
	 *         email.
	 */
	@GetMapping("/register/activateuser/{id}")
	public ResponseEntity<String> activateUser(@PathVariable("id") Integer id) {
		userService.activateUser(id);
		logger.info("User verified successfully !");
		return new ResponseEntity<String>("Verified Successfully ! Redirecting to homepage...", HttpStatus.ACCEPTED);

	}
}
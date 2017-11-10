package com.bridgeit.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;
import com.bridgeit.service.TokenService;
import com.bridgeit.service.UserService;
import com.bridgeit.socialUtility.GoogleConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ajit Shikalgar
 *
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class GoogleLoginController {

	Logger logger = Logger.getLogger(GoogleLoginController.class);

	@Autowired
	UserService userService;

	@Autowired
	TokenService tokenService;

	/**
	 * @param response
	 *            redirecting user to google via link googleLoginPageUrl
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/gconnect", method = RequestMethod.GET)
	public void initialConnect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String googleLoginPageUrl = GoogleConnection.generateLoginUrl();
		System.out.println("Got access header as:  " + request.getHeader("Access-Control-Allow-Origin"));
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Access-Control-Allow-Origin"));
		try {
			/*
			 * RequestDispatcher dispatcher =
			 * request.getRequestDispatcher(googleLoginPageUrl); dispatcher.forward(request,
			 * response);
			 */
			response.sendRedirect(googleLoginPageUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 *             google provides access token as URL parameter. using this, we ge
	 *             request for profile data in JSON format. parsing and mapping this
	 *             JSON into User Object directly using Object Mapper. finally
	 *             saving object with appropriate tokens and setters.
	 */
	@RequestMapping(value = "/glogin", method = RequestMethod.GET)
	public ResponseEntity<String> googleLogin(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException {
		if (request.getParameter("error") != null) {
			String error = request.getParameter("error");
			logger.info("Error in Google Connection are: " + error);
		} else {
			String code = request.getParameter("code");

			String googleAccessToken = GoogleConnection.getAccessToken(code);

			String profileData = GoogleConnection.getProfileData(googleAccessToken);

			ObjectMapper objectMapper = new ObjectMapper();
			Integer userId = null;
			try {
				String email = objectMapper.readTree(profileData).get("email").asText();
				User user = new User();
				logger.info("profile from google: " + profileData);
				user = userService.getUserByEmail(email, user);
				System.out.println("User from getEmail(): " + user);
				if (user.getId() == null) {

					logger.info("new User is logged in by Google. Let's Register first");
					// if user is null, user is not registered in database
					user = new User();

					user.setEmail(email);

					String firstName = objectMapper.readTree(profileData).get("given_name").asText();
					user.setFirstName(firstName);

					String lastName = objectMapper.readTree(profileData).get("family_name").asText();
					user.setLastName(lastName);

					user.setIsValid(true);

					userId = userService.registerUser(user);
					System.out.println("User ID generated: " + userId);
					if (userId.compareTo(-1) == 0) {
						logger.info("User cannot be registered");
						return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
					} else
						logger.info("User registered");
					return new ResponseEntity<String>(HttpStatus.OK);

				} else {

					logger.info("existing user is logging thru' google. let's allocate tokens to user");
					Token accessToken = tokenService.generateTokenAndPushIntoRedis(user.getId(), "accesstoken");
					Token refreshToken = tokenService.generateTokenAndPushIntoRedis(user.getId(), "refreshtoken");
					List<Token> tokenList = new ArrayList<>();
					tokenList.add(accessToken);
					tokenList.add(refreshToken);
					return new ResponseEntity<String>(HttpStatus.OK);
					/*
					 * RequestDispatcher dispatcher =
					 * request.getRequestDispatcher("templates/googlesuccess.jsp");
					 * request.setAttribute("user", user); request.setAttribute("token", tokenList);
					 * dispatcher.forward(request, response);
					 */
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		return new ResponseEntity<String>(HttpStatus.OK);

	}
}

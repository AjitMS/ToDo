package com.bridgeit.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.User;
import com.bridgeit.service.TokenService;
import com.bridgeit.service.UserService;
import com.bridgeit.socialUtility.GoogleConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GoogleLoginController {

	Logger logger = Logger.getLogger(GoogleLoginController.class);

	@Autowired
	UserService userService;

	@Autowired
	TokenService tokenService;

	@RequestMapping(value = "/gconnect", method = RequestMethod.GET)
	public void initialConnect(HttpServletResponse response) {
		String googleLoginPageUrl = GoogleConnection.generateLoginUrl();
		try {
			response.sendRedirect(googleLoginPageUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/glogin", method = RequestMethod.GET)
	public void googleLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException {
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
					} else
						logger.info("User registered");
					return;

				} else {

					logger.info("existing user is logging thru' google. let's allocate tokens to user");
					tokenService.generateTokenAndPushIntoRedis(user.getId(), "accesstoken");
					tokenService.generateTokenAndPushIntoRedis(user.getId(), "refreshtoken");
					RequestDispatcher dispatcher = request.getRequestDispatcher("googlesuccess.jsp");
					request.setAttribute("user", user);
					dispatcher.forward(request, response);
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}

	}
}

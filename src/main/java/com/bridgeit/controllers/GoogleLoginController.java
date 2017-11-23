package com.bridgeit.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

				System.out.println("GOT DATA FROM GOOGLE: " + objectMapper.readTree(profileData));
				String email = objectMapper.readTree(profileData).get("email").asText();
				User user = new User();
				logger.info("profile from google: " + profileData);
				user = userService.getUserByEmail(email, user);
				System.out.println("User from getEmail(): " + user);
				String pic = objectMapper.readTree(profileData).get("picture").asText();
				URL url = new URL(pic);
				if (user.getId() == null) {
					InputStream in = new BufferedInputStream(url.openStream());
					ByteArrayOutputStream out = new ByteArrayOutputStream();

					byte[] image = new byte[1024];
					int n = 0;
					while (-1 != (n = in.read(image))) {
						out.write(image, 0, n);
					}

					logger.info("new User is logged in by Google. Let's Register first");
					// if user is null, user is not registered in database
					user = new User();
					user.setImage("data:image/png;base64," + new String(Base64.getEncoder().encode(out.toByteArray())));
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
						return;
					} else
						logger.info("User registered");

					response.sendRedirect("http://localhost:8080/ToDo/#!/dummy/" + user.getId());

				} else {

					logger.info("existing user is logging thru' google. let's allocate tokens to user");
					response.sendRedirect("http://localhost:8080/ToDo/#!/dummy/" + user.getId());
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		return;

	}
}

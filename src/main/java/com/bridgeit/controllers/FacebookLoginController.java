package com.bridgeit.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.User;
import com.bridgeit.service.TokenService;
import com.bridgeit.service.UserService;
import com.bridgeit.socialUtility.FBConnection;
import com.bridgeit.socialUtility.FBGraph;

/**
 * @author Ajit Shikalgar
 *
 */
@RestController
public class FacebookLoginController {

	Logger logger = Logger.getLogger(FacebookLoginController.class);

	@Autowired
	UserService userService;
	User user;

	@Autowired
	FBConnection fbConnection;



	@Autowired
	TokenService tokenService;

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 *             intial request to facebook to provide accessToken by redirecting
	 *             user to facebook and let user enter credentials
	 */
	@GetMapping("/fbconnect")
	public void initialConnect(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("Initial login");
		String fbLoginURL = fbConnection.getFBAuthUrl();
		logger.info("FBLoginURL: " + fbLoginURL);
		response.sendRedirect(fbLoginURL);
	}

	/**
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 *             After we get the access token we must use it further to get the
	 *             graph data graph data provides user profile in JSON format.
	 *             parsing JSON and mapping into User object and saving into DB with
	 *             appropriate credentials and Tokens. finally redirect toi display
	 *             page.
	 */
	@GetMapping("/fblogin")
	public void fbLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String code;
		code = req.getParameter("code");
		logger.info("Code is: " + code);
		fbConnection = new FBConnection();
		String fbAccessToken = fbConnection.getAccessToken(code);// Facebook's access token
		FBGraph fbGraph = new FBGraph(fbAccessToken);
		String graph = fbGraph.getFBGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		User user = new User();
		user.setFirstName(fbProfileData.get("name"));
		user.setIsValid(true);
		Integer userId = userService.registerUser(user);

		tokenService.generateTokenAndPushIntoRedis(userId, "accesstoken");
		tokenService.generateTokenAndPushIntoRedis(userId, "refreshtoken");

		// for debugging
		logger.info("Fb Profile Data: " + fbProfileData);

		logger.info("Homepage");
		RequestDispatcher dispatcher = req.getRequestDispatcher("fbsuccess.jsp");
		req.setAttribute("user", user);
		dispatcher.forward(req, res);
	}
}

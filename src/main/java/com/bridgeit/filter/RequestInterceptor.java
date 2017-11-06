package com.bridgeit.filter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgeit.service.TokenService;

/**
 * @author Ajit Shikalgar 
 * Each Request to controller is intercepted by
 *         interceptor. HandlerInterceptor is registered at
 *         DefaultAnnotationHandlerAdapter. hence it knows which classes are
 *         controllers and appropriately intercepts them
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
	Logger logger = Logger.getLogger(RequestInterceptor.class);

	@Autowired
	TokenService tokenService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object) before request from
	 * user hits controller, it hits preHandle() to verify user is aunthenticated
	 */

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		logger.info("Prehandle is called. Request is: " + req + " . Response is: " + res + " . handler is: " + handler);
		String accessToken = req.getHeader("accessToken");
		String refreshToken = req.getHeader("refreshToken");
		if (accessToken == null || refreshToken == null) {
			logger.info("Token is null. authentication is failed");
			return false;
		}
		Integer rUserId;
		// first validate accessToken
		Integer aUserId = tokenService.verifyUserToken(accessToken);
		if (!(aUserId == 0)) {
			// if accessToken matches
			// out.write("Access Token Validated");
			req.setAttribute("userId", aUserId);
			logger.info("Access Token Validated");
			return true;
		} else
		// if accessToken fails, validate refreshToken
		{
			logger.info("Access Token Validation failed. validating refresh token");
			rUserId = tokenService.verifyUserToken(refreshToken);
			req.setAttribute("userId", rUserId);
			if (!(rUserId.compareTo(-1) == 0))
			// if refreshToken correct, generate new access token
			{
				// out.print("only refresh tokens valid. new Access token le " + message);
				logger.info("Refresh Token Validated ! generating new Access token");
				return false;
			} else
				logger.info("Both token failed to validate");
			// if refresh token also fails
			PrintWriter out = res.getWriter();
			out.print("both token failed");
			res.setStatus(HttpStatus.BAD_REQUEST.value());
			out.close();
			return false;
		}

	}

}

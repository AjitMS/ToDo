package com.bridgeit.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.bridgeit.entity.Token;
import com.bridgeit.entity.User;

/**
 * @author Ajit Shikalgar
 *
 */
public interface UserService {

	/**
	 * @param user
	 * @return service layer operation to perform registering user
	 */
	public Integer registerUser(User user);

	/**
	 * @param email
	 * @param password
	 * @return service layer operation to perform registering user
	 */
	public boolean loginUser(String email, String password);

	/**
	 * @param user
	 * @return returns true if user exists in DB by calling UserDao
	 */
	public boolean userExists(User user);

	/**
	 * @param email
	 * @param user
	 * @return returns user object for specified email returns null if user not
	 *         found
	 */
	public User getUserByEmail(String email, User user);

	/**
	 * @param id
	 * @param user
	 * @return returns user object for specified id returns null if user not found
	 */
	public User getUserById(Integer id, User user);

	/**
	 * @param id
	 *            activates registered user.
	 */
	public void activateUser(Integer id);

	/**
	 * @param id
	 * @param email
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 *             sends a activation link to registered user
	 */
	public void sendRegistrationVerificationLink(Integer id, String email)
			throws FileNotFoundException, ClassNotFoundException, IOException;

	/**
	 * @param user
	 * @param accessToken
	 * @param request
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 *             sends a login token with accessToken
	 */
	public void sendLoginVerificationToken(User user, Token accessToken, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException;

	/**
	 * @param user
	 * @param request
	 * @param token
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 *             sends a mail with link+forgotToken in it
	 */
	public void sendResetPasswordMail(User user, HttpServletRequest request, Token forgotToken)
			throws FileNotFoundException, ClassNotFoundException, IOException;

	/**
	 * @param email
	 * @param password
	 *            resets user password in DB with new one
	 */
	public void resetPassword(String email, String password);

}

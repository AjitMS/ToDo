package com.bridgeit.emailUtility;

import java.io.Serializable;

/**
 * @author Ajit Shikalgar
 *
 */
public class EmailInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	private String password;

	/**
	 * @param email
	 * @param password
	 * using constructor instead to set credentials
	 */
	public EmailInfo(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/**
	 * @return
	 * getter for email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @retur
	 * getter for password
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "EmailInfo [email=" + email + ", password=" + password + "]";
	}

}

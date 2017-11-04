package com.bridgeit.emailUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * @author Ajit Shikalgar
 *
 */
public class EmailCredentialSerializer {
	public static final String credentialFilePath = "/home/bridgelabz3/ajit/AjitMiscFIles/myEmail.dat";
	static Logger logger = Logger.getLogger(EmailCredentialSerializer.class);

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * driver method needed to serialize private credentials into .dat file.
	 * must run only once to generate .dat file
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		String workingDirectory = System.getProperty("user.dir");
		logger.info("*****Working directory is " + workingDirectory);
		serializeCredentials("email", "password");
		EmailInfo emailInfo = getEmailInfo();
		logger.info("Email Info is " + emailInfo);
	}

	/**
	 * @param email
	 * @param password
	 * @throws FileNotFoundException
	 * @throws IOException
	 *             serializes Email Info into .dat file for security purposes
	 */
	public static void serializeCredentials(String email, String password) throws FileNotFoundException, IOException {
		EmailInfo emailInfo = new EmailInfo(email, password);
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(credentialFilePath))) {
			output.writeObject(emailInfo);
			output.close();

		}
	}

	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 *             deserializes dat file to provide EmailInfo Object from .dat file
	 */
	public static EmailInfo getEmailInfo() throws FileNotFoundException, IOException, ClassNotFoundException {

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(credentialFilePath))) {
			logger.info("Inside getEmailInfo() method");
			EmailInfo emailInfo = (EmailInfo) input.readObject();
			return emailInfo;
		}
	}
}

package com.bridgeit.service;

import com.bridgeit.entity.Token;

/**
 * @author Ajit Shikalgar
 *
 */
public interface TokenService {

	/**
	 * @param userId
	 * @param tokenType
	 * @return generates a random UUID and pushes into redis the entire Token entity
	 *         for a particular user
	 */
	public Token generateTokenAndPushIntoRedis(Integer userId, String tokenType);

	/**
	 * @param userTokenId
	 * @return verifies whether user supplied token and token stored in redis for
	 *         same user is same or not
	 *         returns userId if verified
	 *         else -1
	 */
	public Integer verifyUserToken(String userTokenId);
	
	public void destroyUserToken(Token accessToken, Token refreshToken);
}

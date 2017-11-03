package com.bridgeit.service;

import com.bridgeit.entity.Token;

public interface TokenService {

	public Token generateTokenAndPushIntoRedis(Integer userId, String tokenType);

	public Integer verifyUserToken(String userTokenId);
}

package com.bridgeit.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.Token;
import com.bridgeit.service.TokenService;

@RestController
public class DummyController {

	@Autowired
	TokenService tokenService;

	@PostMapping("/dummyhit")
	public ResponseEntity<List<Token>> dummyAPI(@RequestBody String uId, HttpServletRequest request) {
		System.out.println("Reached in Dummy with id: "+uId);
		Integer userId = Integer.parseInt(uId);
		Token accessToken = tokenService.generateTokenAndPushIntoRedis(userId, "accesstoken");
		Token refreshToken = tokenService.generateTokenAndPushIntoRedis(userId, "refreshtoken");
		List<Token> tokenList = new ArrayList<Token>();
		tokenList.add(accessToken);
		tokenList.add(refreshToken);
		return new ResponseEntity<List<Token>>(tokenList, HttpStatus.OK);
	}
}

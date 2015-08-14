package com.wcecil.webservice.service

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service

import com.wcecil.data.repositiories.UsersRepository;

@Service
class AuthenticationService {
	@Autowired
	UsersRepository usersRepo;
	
	public String getUserFromToken(String token) {
		def userToken = usersRepo.getUserTokenForTokenId(token)
		if(!userToken){
			throw new IllegalAccessError("Unknown token [$token]");
		}
		return userToken?.getUserId();
	}

	public String getUserFromToken(String token, HttpServletResponse response) {
		try {
			return getUserFromToken(token);
		} catch (Exception e) {
			e.printStackTrace()
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		}
	}
}

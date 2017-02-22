package com.pivotal.io.client;
import com.pivotal.io.results.LoginResult;

import org.springframework.cloud.netflix.feign.FeignClient;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


	@FeignClient(name = "AuthClient")
	public interface AuthClient {
		
		   @RequestLine("POST /oauth/token")
		   @Headers({ "authorization: Basic Y2Y6","content-type: application/x-www-form-urlencoded;charset=utf-8","accept: application/json;charset=utf-8" })
		   @Body("username={userName}&password={password}&grant_type=password")
		   LoginResult login(@Param("userName") String userName, @Param("password") String password);
	}



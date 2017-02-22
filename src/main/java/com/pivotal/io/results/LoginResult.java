package com.pivotal.io.results;

public class LoginResult {
	public String access_token;
	public String token_type;
	public String refresh_token;
	public long expires_in;
	public String scope;
	public String jti;
	
	public String toString()
	{
		return "<LoginResult>" + "\r\n" +
				"access_token: " + access_token +  "\r\n" +
				"token_type: " + token_type +  "\r\n" +
				"refresh_token: " + refresh_token +  "\r\n" +
				"expires_in: " + expires_in +  "\r\n" +
				"scope: " + scope +  "\r\n" +
				"jti: " + jti +  "\r\n" +
				"</LoginResult>\n";
	}

}

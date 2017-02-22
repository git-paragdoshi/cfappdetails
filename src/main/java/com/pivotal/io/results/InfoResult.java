package com.pivotal.io.results;

public class InfoResult {
	public String name;
	public String build;
	public String support;
	public Integer version;
	public String description;
	public String authorization_endpoint;
	public String token_endpoint;
	public String min_cli_version;
	public String min_recommended_cli_version;
	public String api_version;
	public String app_ssh_endpoint;
	public String app_ssh_host_key_fingerprint;
	public String app_ssh_oauth_client;
	public String routing_endpoint;
	public String logging_endpoint;
	public String doppler_logging_endpoint;
	
	public String toString()
	{
		String newLine = "\r\n";
		return
			 "<InfoResult>" + newLine +
			 "name: " + name + newLine +
			 "build: " + build + newLine +
			 "support: " + support + newLine +
			 "version: " + version + newLine +
			 "description: " + description + newLine +
			 "authorization_endpoint: " + authorization_endpoint + newLine +
			 "token_endpoint: " + token_endpoint + newLine +
			 "min_cli_version: " + min_cli_version + newLine +
			 "min_recommended_cli_version: " + min_recommended_cli_version + newLine +
			 "api_version: " + api_version + newLine +
			 "app_ssh_endpoint: " + app_ssh_endpoint + newLine +
			 "app_ssh_host_key_fingerprint: " + app_ssh_host_key_fingerprint + newLine +
			 "app_ssh_oauth_client: " + app_ssh_oauth_client + newLine +
			 "routing_endpoint: " + routing_endpoint + newLine +
			 "logging_endpoint: " + logging_endpoint + newLine +
			 "doppler_logging_endpoint: " + doppler_logging_endpoint + newLine +
			 "</InfoResult>";	
	}
}

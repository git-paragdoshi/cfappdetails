package com.pivotal.io.results;

public class UserProvidedServiceResource {
	public Metadata metadata;
	public UserProvidedServiceEntity entity;
		
	@Override
	public String toString() {
		return "UserProvidedServiceResource [metadata=" + metadata
				+ ", entity=" + entity + "]";
	}
	
	
}

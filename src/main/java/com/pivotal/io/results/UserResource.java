package com.pivotal.io.results;

public class UserResource {
	public Metadata metadata;
	public UserEntity entity;
	
	@Override
	public String toString() {
		return "UserResource [metadata=" + metadata + ", entity=" + entity
				+ "]";
	}
}

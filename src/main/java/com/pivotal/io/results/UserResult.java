package com.pivotal.io.results;

import java.util.List;

public class UserResult {
	
	public int total_results;
	public int total_pages;
	public String prev_url;
	public String next_url;
	public List<UserResource> resources;
	
	@Override
	public String toString() {
		return "UserResult [total_results=" + total_results + ", total_pages="
				+ total_pages + ", prev_url=" + prev_url + ", next_url="
				+ next_url + ", resources=" + resources + "]";
	}
	
	
}

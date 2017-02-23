package com.pivotal.io.results;

import java.util.List;

public class SpaceQuotaResult {
	public int total_results;
	public int total_pages;
	public String prev_url;
	public String next_url;
	public List<SpaceQuotaResource> resources;
}

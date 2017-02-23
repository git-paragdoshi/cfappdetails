package com.pivotal.io.results;

public class SpaceQuotaEntity {
	public String name;
	public String organization_guid;
	public boolean non_basic_services_allowed;
	public int total_services;
	public int total_routes;
	public long memory_limit;
	public int instance_memory_limit;
	public int app_instance_limit;
	public String organization_url;
	public String spaces_url;
}

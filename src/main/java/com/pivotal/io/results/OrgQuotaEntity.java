package com.pivotal.io.results;

public class OrgQuotaEntity {
	public String name;
	public boolean non_basic_services_allowed;
	public int total_services;
	public int total_routes;
	public int total_private_domains;
	public long memory_limit;
	public boolean trial_db_allowed;
	public int instance_memory_limit;
	public int app_instance_limit;
}

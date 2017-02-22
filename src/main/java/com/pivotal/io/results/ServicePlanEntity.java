package com.pivotal.io.results;

public class ServicePlanEntity {
	public String name;
	public boolean free;
	public String description;
	public String service_guid;
	// public <TBD> extra; // Commented out, 
	public String unique_id;
	// public boolean public; // TODO need to figure out how to reference a field that is a keyword
	public boolean active;
	public String service_url;
	public String service_instances_url;
}

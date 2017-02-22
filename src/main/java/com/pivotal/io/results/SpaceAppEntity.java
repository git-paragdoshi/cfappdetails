package com.pivotal.io.results;

import java.util.List;

public class SpaceAppEntity {
	public String name;
	public boolean production;
	public String space_guid;
	public String stack_guid;
	public String buildpack;
	public String detected_buildpack;
	// public <TBD> environment_json; // this is another json object. Commented out for now
	public int memory;
	public int instances;
	public int disk_quota;
	public String state;
	public String version;
	public String command;
	public boolean console;
	public String debug;
	public String staging_task_id;
	public String packaged_state;
	public String health_check_type;
	public String health_check_timeout;
	public String staging_failed_reason;
	public String staging_failed_description;
	public boolean diego;
	public String docker_image;
	public String package_updated_at;
	public String detected_start_command;
	public boolean enable_ssh;
	// public <TBD> docker_credentials_json; // this is another json object. Commented out for now. 
	// Note: ports changed to a list in 1.7 - Will need a better strategy for going against different versions of the API
	public List<String> ports;
	public String space_url;
	public String stack_url;
	public String events_url;
	public String service_bindings_url;
	public String routes_url;
}

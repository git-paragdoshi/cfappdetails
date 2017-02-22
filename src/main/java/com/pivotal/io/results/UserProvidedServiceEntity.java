package com.pivotal.io.results;

public class UserProvidedServiceEntity {
	public String name;
	// public <TBD> credentials; // Commented out for now
	public String space_guid;
	public String type;
	public String syslog_drain_url;
	public String route_service_url;
	public String space_url;
	public String service_bindings_url;
	public String routes_url;
	
	@Override
	public String toString() {
		return "UserProvidedServiceEntity [name=" + name + ", space_guid="
				+ space_guid + ", type=" + type + ", syslog_drain_url="
				+ syslog_drain_url + ", route_service_url=" + route_service_url
				+ ", space_url=" + space_url + ", service_bindings_url="
				+ service_bindings_url + ", routes_url=" + routes_url + "]";
	}
	
	
}

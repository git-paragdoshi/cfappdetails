package com.pivotal.io.results;

public class SpaceEntity {
	public String name;
	public String organization_guid;
	public String space_quota_definition_guid;
	public boolean allow_ssh;
	public String organization_url;
	public String developers_url;
	public String managers_url;
	public String auditors_url;
	public String apps_url;
	public String routes_url;
	public String domains_url;
	public String service_instances_url;
	public String app_events_url;
	public String events_url;
	public String security_groups_url;
	
	@Override
	public String toString() {
		return "SpaceEntity [name=" + name + ", organization_guid="
				+ organization_guid + ", space_quota_definition_guid="
				+ space_quota_definition_guid + ", allow_ssh=" + allow_ssh
				+ ", organization_url=" + organization_url
				+ ", developers_url=" + developers_url + ", managers_url="
				+ managers_url + ", auditors_url=" + auditors_url
				+ ", apps_url=" + apps_url + ", routes_url=" + routes_url
				+ ", domains_url=" + domains_url + ", service_instances_url="
				+ service_instances_url + ", app_events_url=" + app_events_url
				+ ", events_url=" + events_url + ", security_groups_url="
				+ security_groups_url + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}

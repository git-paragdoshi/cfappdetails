package com.pivotal.io.results;

public class OrgEntity {
	public String name;
	public boolean billing_enabled;
	public String quota_definition_guid;
	public String status;
	public String quota_definition_url;
	public String spaces_url;
	public String domains_url;
	public String private_domains_url;
	public String users_url;
	public String managers_url;
	public String billing_managers_url;
	public String auditors_url;
	public String app_events_url;
	public String space_quota_definitions_url;
	@Override
	public String toString() {
		return "OrgEntity [name=" + name + ", billing_enabled="
				+ billing_enabled + ", quota_definition_guid="
				+ quota_definition_guid + ", status=" + status
				+ ", quota_definition_url=" + quota_definition_url
				+ ", spaces_url=" + spaces_url + ", domains_url=" + domains_url
				+ ", private_domains_url=" + private_domains_url
				+ ", users_url=" + users_url + ", managers_url=" + managers_url
				+ ", billing_managers_url=" + billing_managers_url
				+ ", auditors_url=" + auditors_url + ", app_events_url="
				+ app_events_url + ", space_quota_definitions_url="
				+ space_quota_definitions_url + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}

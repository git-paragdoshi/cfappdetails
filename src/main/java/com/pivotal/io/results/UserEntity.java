package com.pivotal.io.results;

public class UserEntity {
	public boolean admin;
	public boolean active;
	public String default_space_guid;
	public String username;
	public String spaces_url;
	public String organizations_url;
	public String managed_organizations_url;
	public String billing_managed_organizations_url;
	public String audited_organizations_url;
	public String managed_spaces_url;
	public String audited_spaces_url;
	
	@Override
	public String toString() {
		return "UserEntity [admin=" + admin + ", active=" + active
				+ ", default_space_guid=" + default_space_guid + ", username="
				+ username + ", spaces_url=" + spaces_url
				+ ", organizations_url=" + organizations_url
				+ ", managed_organizations_url=" + managed_organizations_url
				+ ", billing_managed_organizations_url="
				+ billing_managed_organizations_url
				+ ", audited_organizations_url=" + audited_organizations_url
				+ ", managed_spaces_url=" + managed_spaces_url
				+ ", audited_spaces_url=" + audited_spaces_url + "]";
	}
	
	
}

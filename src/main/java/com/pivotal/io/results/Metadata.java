package com.pivotal.io.results;

public class Metadata {
	public String guid;
	public String url;
	public String created_at;
	public String updated_at;
	
	public String toString()
	{
		return "<Metadata>" + "\n" +
				"guid: " + guid + "\n" +
				"url: " + url + "\n" +
				"created_at: " + created_at + "\n" + 
				"updated_at: " + updated_at + "\n" +
				"</Metadata>" + "\n";
	}
}

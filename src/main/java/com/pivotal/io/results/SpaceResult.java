package com.pivotal.io.results;

import java.util.List;

public class SpaceResult {
	public int total_results;
	public int total_pages;
	public String prev_url;
	public String next_url;
	public List<SpaceResource> resources;
	
	public String toString()
	{
		String returnValue = 
				"<SpaceResult>\n" +
				"total_results: " + total_results + "\n" + 
				"total_pages: " + total_pages + "\n" +
				"prev_url: " + prev_url + "\n" +
				"next_url: " + next_url + "\n";
		
		for(int i=0;i<resources.size();i++)
		{
			returnValue = returnValue + resources.get(i).toString() + "\n";
		}
		
		return returnValue;
	}
}

package com.pivotal.io;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;



@SpringBootApplication
public class CfappsdetailsApplication {
	
	private static final String REQUIRED_ARGS = "requiredArgs";
	

	public static void main(String[] args) {
	//	SpringApplication.run(CfappsdetailsApplication.class, args);
		SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);
		boolean skipSSL = ps.containsProperty("skip-ssl-validation");
		boolean hideProgress = ps.containsProperty("hideProgress");
		ps.setNonOptionArgsPropertyName(REQUIRED_ARGS);
		
		String requiredArgs = ps.getProperty(REQUIRED_ARGS);
		if (requiredArgs == null)
		{
			printUsage("ERROR: Missing arguments");
			return;
		}
		
		String[] commandLineArgs = requiredArgs.split(",");
		if (commandLineArgs.length < 3)
		{
			printUsage("ERROR: Missing arguments");
			return;
		}
		
		System.out.println("Starting CF App Details: " + currentTime());
		
		String apiEndpoint = commandLineArgs[0];
		String userName = commandLineArgs[1];
		String password = commandLineArgs[2];
		
		CFInstance cfInstance = new CFInstance(apiEndpoint, skipSSL, !hideProgress);
		if (!cfInstance.login(userName, password))
		{
			System.err.println("ERROR: Invalid credentials");
		}
		cfInstance.generateCFDetails();
		System.out.println("Completed generating CF App Details: " + currentTime());
	}
	
	private static void printUsage(String msg)
	{
		System.err.println("CFAppDetails API_ENDPOINT UserName Password [--hideProgress] [--skip-ssl-validation]");
		System.err.println();
		System.err.println("Generates a list of applications and services that are running in an existing Cloud Foundry instance.");
		System.err.println();
		System.err.println("Options");
		System.err.println("\t--hideProgress       \tHides the progress of traversing the Cloud Foundry Instance");
		System.err.println("\t--skip-ssl-validation\tSkip verification of the API endpoint.");
		System.err.println();
		System.err.println(msg);	
	}
	
	public static String currentTime()
	{
		Date now = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
		return df.format(now);		
	}
}

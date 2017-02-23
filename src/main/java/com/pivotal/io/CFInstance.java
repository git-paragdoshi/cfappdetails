package com.pivotal.io;

import java.io.FileWriter;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.pivotal.io.client.AuthClient;
import com.pivotal.io.client.CFClient;
import com.pivotal.io.results.InfoResult;
import com.pivotal.io.results.LoginResult;
import com.pivotal.io.results.OrgQuotaResult;
import com.pivotal.io.results.OrgResource;
import com.pivotal.io.results.ServiceBindingsResult;
import com.pivotal.io.results.ServicePlanResult;
import com.pivotal.io.results.ServiceResult;
import com.pivotal.io.results.SpaceResult;
import com.pivotal.io.results.SpaceServiceInstanceResult;
import com.pivotal.io.results.UserResult;

import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.slf4j.Slf4jLogger;

import com.pivotal.io.results.UserProvidedServiceResult;

import com.pivotal.io.results.SpaceAppResult;
import com.pivotal.io.results.SpaceQuotaResult;

public class CFInstance {

	private HashMap<String,String> orgQuotas = new HashMap<String,String>();
	private HashMap<String,String> spaceApps = new HashMap<String,String>();
	private HashMap<String,String> spaceQuotas = new HashMap<String, String>();
	private static String[] quotasToUpdate = { "default", "runaway", "p-spring-cloud-services" };

	private String apiEndpoint;
	private String authEndpoint;
	private String accessToken;
	private CFClient cfClient;
	private static boolean showProgress;
	FileWriter fileWriter=null;

	public CFInstance(String apiEndpoint, boolean skipSslValidation, boolean displayProgress) {
		// TODO Auto-generated constructor stub
		this.apiEndpoint = apiEndpoint;
		showProgress = displayProgress;
		if (skipSslValidation) {
			setupNonTrustedSSLSupport();
		}
		createFileToWriteFoundationDetails(apiEndpoint);
	}

	

	private static void setupNonTrustedSSLSupport() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			System.out.println("Exception while installing the all-trusting trust manager");
			e.printStackTrace(System.err);
		}
	}
	
	private void createFileToWriteFoundationDetails(String apiEndpoint2) {
		try {
			System.out.println("apiEndpoint is " + apiEndpoint);
			String api = apiEndpoint;
			if (apiEndpoint.startsWith("https://")) {
				api = apiEndpoint.substring(8);
			}
			System.out.println("api is " + api);
			fileWriter = new FileWriter("FoundationDetails_" + api + ".txt");
		} catch (Exception e) {
			System.err.println("could not create file: " + e.getMessage());
		}

	}

	public boolean login(String userName, String password) {
		// first set up the API Endpoint
		cfClient = Feign.builder().decoder(new GsonDecoder()).target(CFClient.class, apiEndpoint);

		// grab the "info"
		InfoResult infoResult = cfClient.details();

		// get the authorization endpoint
		this.authEndpoint = infoResult.authorization_endpoint;
		progressMessage("Retrieved Authentication Endpoint: " +authEndpoint);

		// now login
		AuthClient authClient = Feign.builder().logger(new Slf4jLogger()).decoder(new GsonDecoder())
				.target(AuthClient.class, this.authEndpoint);

		LoginResult loginResult;
		try {
			loginResult = authClient.login(userName, password);
			this.accessToken = loginResult.access_token;
			progressMessage("Logged In!");
		} catch (FeignException fe) {
			System.err.println("exception during login: " + fe.getMessage());
			return false;
		}

		return true;
	}

	public void generateCFDetails() {
		
		processOrgQuotas();
		// retrieve all Orgs and process them
		processOrgs();
	}
	public void processOrgQuotas()
	{
		OrgQuotaResult orgQuotaResult = cfClient.retrieveOrgQuotas(accessToken);
		orgQuotaResult.resources.forEach(( resource) -> {
			
			String quotaGuid = resource.metadata.guid;
			String quotaName = resource.entity.name;

			long totalMemory = resource.entity.memory_limit;
			long instanceMemory = resource.entity.instance_memory_limit;
			int routes = resource.entity.total_routes;
			int serviceInstances = resource.entity.total_services;
			boolean allowPaidServicePlans = resource.entity.non_basic_services_allowed;
			progressMessage("Quota Details for: " + quotaName + ":\r\n"
							+ " Total Memory:" + totalMemory
							+ " Instance Memory: " + instanceMemory
							+ " Number of Routes: " + routes
							+ " Number of Service Instances: " + serviceInstances
							);
			
			
			orgQuotas.put(quotaGuid,  quotaName);
		});
	}
	public void processOrgs() {
		progressMessage("Retrieving All Organizations");
		com.pivotal.io.results.OrganizationResult orgResult = cfClient.retrieveOrganizations(this.accessToken);
		progressMessage("Number of Orgs: " + orgResult.total_results);
		orgResult.resources.forEach((resource) -> {
			processOrg(resource);
		});
		progressMessage("Processing All Organization Complete");
		if(fileWriter!=null)
		{
			try
			{
				fileWriter.close();
			}
			catch(Exception e)
			{
				//do nothing
			}
		}
	}

	private void processOrg(com.pivotal.io.results.OrgResource resource) {
		String guid = resource.metadata.guid;
		String orgName = resource.entity.name;

		
		processOrgEntityDetails(resource);

		processOrgUsers(orgName, guid);

		processSpaces(orgName, guid);

		progressMessage("Finished processing Organization: " + orgName);
	}
	private String getOrgQuotaName(String orgQuotaGuid)
	{
		String quotaName = orgQuotas.get(orgQuotaGuid);
		if ((quotaName != null) && !CFInstance.updateQuota( quotaName ))
		{
			return quotaName;
		}
		
		return null;
	}
	public static boolean updateQuota(String quotaName)
	{
		for(int x=0;x<quotasToUpdate.length;x++)
		{
			if (quotasToUpdate[x].equals(quotaName))
				return true;
		}
		
		return false;
	}

	private void processOrgEntityDetails(OrgResource resource) {
		
		progressMessage("Processing Org: " + resource.entity.name + "\r\n"
						+ " Using Quota : " + getOrgQuotaName(resource.entity.quota_definition_guid)
						);
		
	}



	private void processSpaces(String orgName, String orgGuid) {
		progressMessage("Processing spaces in Org: " + orgName);

		// now get the spaces...
		SpaceResult spaceResult = cfClient.retrieveSpaces(accessToken, orgGuid);
		progressMessage("Number of Spaces in Org " + orgName + " are: " + spaceResult.resources.size());
		processSpaceQuotas(orgGuid);
		spaceResult.resources.forEach((spaceResource) -> {
			String spaceName = spaceResource.entity.name;
			String spaceGuid = spaceResource.metadata.guid;
			progressMessage("Processing space: " + spaceName+ " using space quota: " + spaceQuotas.get(spaceResource.entity.space_quota_definition_guid));
			processSpaceApps(spaceGuid, spaceName);
			processSpaceServices(spaceGuid, spaceName);
			processUserProvidedServices(spaceGuid, spaceName);
			
			progressMessage("Processing for Space: " + spaceName + " complete.");
		});

	}
	
	private void processSpaceQuotas(String orgGuid)
	{
		progressMessage("Processing Space Quotas");
		
		SpaceQuotaResult spaceQuotaResult = cfClient.retrieveSpaceQuotas(accessToken, orgGuid);
		spaceQuotaResult.resources.forEach((spaceQuotaResource) -> 
		{
			String quotaGuid = spaceQuotaResource.metadata.guid;
			String quotaName = spaceQuotaResource.entity.name;
			progressMessage("Processing space quota " + quotaName);
			int instanceMemory =  spaceQuotaResource.entity.instance_memory_limit;
			long totalMemory = spaceQuotaResource.entity.memory_limit;
			int routes = spaceQuotaResource.entity.total_routes;
			int serviceInstances = spaceQuotaResource.entity.total_services;
			boolean allowPaidServicePlans = spaceQuotaResource.entity.non_basic_services_allowed;
			progressMessage("Space Quota Details for: " + quotaName + ":\r\n"
					+ " Total Memory:" + totalMemory
					+ " Instance Memory: " + instanceMemory
					+ " Number of Routes: " + routes
					+ " Number of Service Instances: " + serviceInstances
					);
			spaceQuotas.put(quotaGuid, quotaName);

		});
	}

	private void processSpaceApps(String spaceGuid, String spaceName) {
		progressMessage("Processing Apps for Space: " + spaceName);

		SpaceAppResult result = cfClient.retrieveSpaceApps(this.accessToken, spaceGuid);
		progressMessage("Number of apps are " + result.resources.size());

		result.resources.forEach((resource) -> {
			String appGuid = resource.metadata.guid;
			String appName = resource.entity.name;
			progressMessage("App Name is: " + appName);
			progressMessage("App instances are: " + resource.entity.instances);
			progressMessage("App memory allocated is: " + resource.entity.memory);
			progressMessage("Disk quota allocated is: " + resource.entity.disk_quota);
			progressMessage("Build Pack is: " + resource.entity.detected_buildpack);
			// add it to hashmap so that it can tied to the service instance in
			// the next call.
			spaceApps.put(appGuid, appName);

		});

	}

	private void processSpaceServices(String spaceGuid, String spaceName) {
		progressMessage("Processing Space Services for: " + spaceName);

		SpaceServiceInstanceResult result = cfClient.retrieveSpaceServiceInstances(this.accessToken, spaceGuid);

		progressMessage("Number of Services Instances are: " + result.resources.size());

		result.resources.forEach((resource) -> {
			String serviceInstanceName = resource.entity.name;
			progressMessage("Service Instance Name is: " + serviceInstanceName);
			progressMessage("Service type is: " + resource.entity.type);
			String serviceInstanceGuid = resource.metadata.guid;

			String servicePlanGuid = resource.entity.service_plan_guid;

			// get the service plan details...
			ServicePlanResult spResult = cfClient.retrieveServicePlan(this.accessToken, servicePlanGuid);

			String serviceGuid = spResult.entity.service_guid;

			// get the service details
			ServiceResult serviceResult = cfClient.retrieveService(this.accessToken, serviceGuid);

			String serviceName = serviceResult.entity.label;
			progressMessage("Service name is: " + serviceName);
			String plan = spResult.entity.name;
			progressMessage("Service plan is: " + plan);
			progressMessage("Service description is: " + spResult.entity.description);

			// now get all the bindings for this service
			ServiceBindingsResult serviceBindingsResult = cfClient.retrieveServiceBindings(this.accessToken,
					serviceInstanceGuid);

			serviceBindingsResult.resources.forEach((bindingResource) -> {
				String appGuid = bindingResource.entity.app_guid;

				String appName = spaceApps.get(appGuid);
				progressMessage("App Bound to Service is: " + appName);
			});

		});

	}

	private void processUserProvidedServices(String spaceGuid, String spaceName) {
		progressMessage("Processing User Provided Services for space: " + spaceName);

		// now get the user provided services for this space
		UserProvidedServiceResult userProvidedServiceResult = cfClient
				.retrieveUserProvidedServicesForSpace(this.accessToken, spaceGuid);
		userProvidedServiceResult.resources.forEach((userProvidedServiceResource) -> {
			boolean firstTime = true;

			String upsGuid = userProvidedServiceResource.metadata.guid;
			String upsServiceInstanceName = userProvidedServiceResource.entity.name;
			String upsSpaceGuid = userProvidedServiceResource.entity.space_guid;

			// make sure that this UPS is in the space we're processing
			if (upsSpaceGuid.equals(spaceGuid)) {
				if (firstTime) {
					// only add the comment if we have a UPS
					progressMessage("User Provided Service Instance name is: " + upsServiceInstanceName);
					firstTime = false;
				}

				// now get the bindings for this service

				ServiceBindingsResult upsServiceBindingsResult = cfClient
						.retrieveUserProvidedServiceBindings(this.accessToken, upsGuid);

				upsServiceBindingsResult.resources.forEach((upsBindingResource) -> {
					String appGuid = upsBindingResource.entity.app_guid;
					String appName = spaceApps.get(appGuid);
					progressMessage("App Bound to User Provided Service is: " + appName);
					progressMessage("App URL is: " + upsBindingResource.entity.app_url);
					progressMessage("Service URL is: " + upsBindingResource.entity.service_instance_url);
				});
			}
		});

	}

	private void processOrgUsers(String orgName, String guid) {
		// process users
		UserResult userResult = cfClient.retrieveUsers(accessToken, guid);

		progressMessage("Number of Users in Org " + orgName + " are: " + userResult.resources.size());

		userResult.resources.forEach((userResource) -> {
			String username = userResource.entity.username;

			progressMessage("UserName is: " + username);

		});

	}

	private void progressMessage(String msg) {
		if (showProgress) {
			System.out.println(msg);
		}
		
		if(fileWriter!=null)
		{
			try{
			fileWriter.write(msg+"\r\n");
			}
			catch (Exception e)
			{
				//do nothing
			}
		}
	}

}

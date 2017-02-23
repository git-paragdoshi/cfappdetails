package com.pivotal.io.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.pivotal.io.results.InfoResult;
import com.pivotal.io.results.OrgQuotaResult;
import com.pivotal.io.results.OrganizationResult;
import com.pivotal.io.results.ServiceBindingsResult;
import com.pivotal.io.results.ServicePlanResult;
import com.pivotal.io.results.ServiceResult;
import com.pivotal.io.results.SpaceResult;
import com.pivotal.io.results.UserResult;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import com.pivotal.io.results.UserProvidedServiceResult;

import com.pivotal.io.results.SpaceServiceInstanceResult;

import com.pivotal.io.results.SpaceAppResult;
import com.pivotal.io.results.SpaceQuotaResult;

@FeignClient(name = "Client")
public interface CFClient {
	
	  @RequestLine("GET /v2/info")
	   InfoResult details();
	  
	  // Org Quotas
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/quota_definitions")
	   OrgQuotaResult retrieveOrgQuotas(@Param("accessToken") String accessToken);
	   
	  
	  // Orgs
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/organizations")
	   OrganizationResult retrieveOrganizations(@Param("accessToken") String accessToken);

	   
	// Org Users
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/organizations/{guid}/users")
	   UserResult retrieveUsers(@Param("accessToken") String accessToken,  @Param("guid") String orgGuid);
	   
	// Space Quotas
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/organizations/{guid}/space_quota_definitions")
	   SpaceQuotaResult retrieveSpaceQuotas(@Param("accessToken") String accessToken, @Param("guid") String orgGuid);
	   
	   
	// Org Spaces
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/organizations/{guid}/spaces")
	   SpaceResult retrieveSpaces(@Param("accessToken") String accessToken, @Param("guid") String orgGuid);
	   
	// Space Apps
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/spaces/{guid}/apps")
	   SpaceAppResult retrieveSpaceApps(@Param("accessToken") String accessToken, @Param("guid") String spaceGuid);
	   
	// Space Service Instances
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/spaces/{guid}/service_instances")
	   SpaceServiceInstanceResult retrieveSpaceServiceInstances(@Param("accessToken") String accessToken, @Param("guid") String spaceGuid);
	   
	// ServicePlan 
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/service_plans/{guid}")
	   ServicePlanResult retrieveServicePlan(@Param("accessToken") String accessToken, @Param("guid") String servicePlanGuid);
	   
	   // Service
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET /v2/services/{guid}")
	   ServiceResult retrieveService(@Param("accessToken") String accessToken, @Param("guid") String serviceGuid);
	   
	   // ServiceBindings
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET v2/service_instances/{guid}/service_bindings")
	   ServiceBindingsResult retrieveServiceBindings(@Param("accessToken") String accessToken, @Param("guid") String serviceInstanceGuid);
	   

	   // User Provided Services for a Space
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET v2/user_provided_service_instances?space={guid}")
	   UserProvidedServiceResult retrieveUserProvidedServicesForSpace(@Param("accessToken") String accessToken, @Param("guid") String spaceGuid);
	   
	   // User Provided Service Bindings
	   @Headers({"Authorization: bearer {accessToken}", "Cookie: "})
	   @RequestLine("GET v2/user_provided_service_instances/{guid}/service_bindings")
	   ServiceBindingsResult retrieveUserProvidedServiceBindings(@Param("accessToken") String accessToken, @Param("guid") String serviceInstanceGuid);
	   
	   
}

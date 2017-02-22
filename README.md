# cfappdetails
CFAppDetails generates application and service instance information from an existing CF installation. You point at an API, provide your login credentials and it generates the output on standard console. This application leverages most of its code from 

Introduction

One question that has seemed to come up a lot at Ford is how to backup Pivotal Cloud Foundry. There's the official method which is documented here. Another tedious method would be to keep track of every CF CLI command executed against an installation and then simply re-install and run those commands again. There is also a community supported cfops which currently relies on Ops Manager.

This got me thinking... How hard would it be to point to a PCF instance and generate a backup CF CLI script?

After doing some experimentation, I created a Java application that uses Feign to go against the CF API and spit out a script.

Currently the script generates the following:

Orgs
Org Quotas
Org Roles
Spaces
Space Quotas
Space Roles
I also generate the following but comment them out:

Org Users
Applications (e.g. cf push)
Services (e.g. cf create-service and bind-service)
User Provided Services (without any parameters)
Building

Requires

Java 1.8
Maven
mvn clean package
Usage

java -jar gencfscript-0.0.1-SNAPSHOT.jar API_ENDPOINT UserName Password [--hideProgress] [--skip-ssl-validation]

Options
   --hideProgress             Hides the progress of traversing the Cloud Foundry Instance
   --skip-ssl-validation      Skip verification of the API endpoint. Not recommended! 
The "progress" messages are printed to standard error (stderr) and the script output is sent to standard out (stdout)

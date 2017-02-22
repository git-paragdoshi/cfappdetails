# cfappdetails
CFAppDetails generates application and service instance information from an existing CF installation. You point at an API, provide your login credentials and it generates the output on standard console. This application leverages most of its code from https://github.com/rossr3-pivotal application https://github.com/Pivotal-Field-Engineering/cf-GenerateCFBackupScript


Currently the following information is generated :

* Orgs
* Orgs Users
* Spaces
* Space Users
* Space Applications
* Space Services
* Space User Provided Services

.Requires

* Java 1.8
* Maven

Building
* mvn clean package

Usage
* java -jar ./target/cfappsdetails-0.0.1-SNAPSHOT.jar API_ENDPOINT UserName Password [--hideProgress] [--skip-ssl-validation]


Options
   --hideProgress             Hides the progress of traversing the Cloud Foundry Instance
   --skip-ssl-validation      Skip verification of the API endpoint. Not recommended! 


The "progress" messages are printed to standard out (stdout)

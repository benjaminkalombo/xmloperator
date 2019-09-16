READ ME:

ARCHITECTURE:

MODEL :

Accounts

REPOSITORY:
AccountRepository

SERVICE: 
AccountService

CONFIG =====
MYSQL:
when you build project the entity will get mapped to db.
please change login credentials in application.properties for mysql


XML template:
please XML template used:

<?xml version="1.0"?>
<accounts>
  	<account id="ABCD-02">
  	    <amount>30</amount>
  	    <type>Saving</type>
  	    <firstname>Benjamin</firstname>
		<lastname>Kalombo</lastname>
		<demographic>johannesburg</demographic>
  	  </account>
  	<account id="ABCD-02">
  	    <amount>180</amount>
  	    <type>Cheque</type>
  	    <firstname>Franck</firstname>
		<lastname>Lampard</lastname>
		<demographic>Pretoria</demographic>
  	  </account>
</accounts>

please change the path of your xml file in: bootstrap/BootStrap.Java

NB:
Bean => bootstrap/BootStrap.Java. initializes the data, load xml into db

JUNIT test using MockMvc,Mocki
class AccountControllerTest 


TO DEPLOY:
1.please run mvn to build .war   RUN to dploy mvn tomcat7:redeploy -Dmaven.test.skip=true
2.then deploy in glassfish.
3.filter.Filter.java is used to test response of the REST => USED IN AccountControllerTest

PLEASE NOTE THIS WAS TESTED ON TOMCAT Container.

FUNCTIONALITIES COVRED:
show all account => controller.AccountController
show detail of one account => controller.AccountController
update demography => controller.AccountController
load xml file  =>  bootstrap/BootStrap.Java


===============. END ==========================










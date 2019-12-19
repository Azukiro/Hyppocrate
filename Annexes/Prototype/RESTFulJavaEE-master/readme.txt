Steps to complete the project:
1. Technologies used:
	- wildfly 10.1.0 server
	- Apache for mysql db
	- Java EE for Restful web service
	- Maven
	- Angular 2 for Client
	- npm
	- connectorJava for connecting with db mysql(Creating a datasource at standalone.xml for connection to db)
	
2. myslq part:
dbname: project1
privileges of db(user: project1user, passw: helloworld)

Project Time:
1. First I have created an Dynamic Web Project
2. Convert to Maven
3. Install Angular 2 For Eclipse
4. Create new angular 2 Project.
5. JAVA EE part with RestFul web service took me about 2 hours.
6. Angular 2 Client has taken 4 days about 4 hours in day.

To Run this project:
1.(add this part to standalone.xml)
	<datasource jta="true" jndi-name="java:/MTITestProject" pool-name="MTITestProject" enabled="true" use-ccm="true">
	  <connection-url>jdbc:mysql://localhost:3306/project1</connection-url>
                    
	   <driver-class>com.mysql.jdbc.Driver</driver-class>
 
               <driver>mysql</driver>
		<security>
 
                       <user-name>project1user</user-name>
  
                       <password>helloworld</password>
   
               </security>
	       <validation>
		 <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
		 <background-validation>true</background-validation>
		 <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
		</validation>
                
	</datasource>
2. First you run this project in Wildfly server( you should see an Hello World)
3. After this you should run Angular2Client project as ng serve.
4. you should see an login page:
username is: Nermina
password is: 123
	

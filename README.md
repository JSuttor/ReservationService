# SETUP DATABASE
To set up the environment in NetBeans so that the database can be accessed, complete the following steps:
1. Go to services -> databases. 
2. right click on "Java DB", and select "properties". 
3. Set the "Java DB Installation" folder to be the /database/db folder in this repository.  
4. Set the Database Location to be the /database folder in this repository. Click OK.  
5. Right click on "Java DB" again, and this time, select "Start Server".  

# SETUP TOMCAT
To setup Tomcat in order to host the webservices, follow the following steps.
1. Open up Netbeans and go to Tools>Plugins..
2. Install Java EE Base
3. Open the Services menu.
4. Right click on servers and then add server.
5. Set the Tomcat directory to the Tomcat dir located in the repo
6. Set the username and password of the tomcat gui, default is admin/admin
7. Select OK and start the Tomcat server. Right click on the server to open the log and output.

# First_Project
  In this project i created a form based authentication to enter the application it allows only users who are all specified in tomcat-user.xml file which is present in conf folder inside tomcat application. After that it will redirect to index.html page once page is loaded a mail has been sent to the user if they logged in using gmail by calling Mail.java(servlet).And the index.html page aks you some information about you and by clicking show table button it will go to My.java(servlet) to insert the data inside database and display all the records in that database.In this project i used 3 databases(Mysql,Mssql,postgresql) .To Know which database should be used for insert and display i have given a specified database name in conf.txt file by reading the file the servlet identify which database it should use for the process .In this project i used an ORM(Object Relation Mapping) tool hibernate for getting connection with the database , insert records and to select records and also to exchange records between database. In index.html i also placed a another feature to change between databases eg.first if you are in mysql database you like to change the database to mssql there is a radio button ask which data base you like to change select one of the database and another button ask with or without data if you select with data then all the records present in the mysql will be sent to mssql database and the database name in the conf.txt file will be changed to mssql and then the tomcat will restart. If you select without data then name of the database in the conf.txt file will be changed and the tomcat will restart.

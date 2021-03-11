Data Migration

This project takes a CSV file holding records of a companies employees and inserts it into a MySQL Database. 
The java files makes use of DAO and DTO design patterns and also handles incorrect data the CSV file may have. 
The incorrect data the java file handles are where 2 or more employees have the same employee id or if 2 or more employees have the same email. 
The incorrect data is written out to an csv file. To achieve this in an efficient multi thread programming is used. The time taken to insert the data into the database and the overall time for the program to finish running is printed to the console. 

How to run the project 

Please download the package and open it in your preferred IDE. 
Insert your user name and password in the properties file for your MySQl database. The MySQL database must have the following table in the schema company: <br>
CREATE TABLE `company`.``employee`` (<br>
  `emp_Id` int NOT NULL,<br>
  `name_prefix` varchar(45) DEFAULT NULL,<br>
  `first_name` varchar(45) DEFAULT NULL,<br>
  `middle_initial` char(1) DEFAULT NULL,<br>
  `last_name` varchar(45) DEFAULT NULL,<br>
  `gender` varchar(45) DEFAULT NULL,<br>
  `email` varchar(45) DEFAULT NULL,<br>
  `date_of_birth` date DEFAULT NULL,<br>
  `date_of_joining` date DEFAULT NULL,<br>
  `salary` int DEFAULT NULL,<br>
  PRIMARY KEY (`emp_Id`)<br>
)<br>
To start the program run the App class with your own url to your local MySQL database in place of mine in the JDBCTask class. 

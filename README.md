Data Migration

This project takes a CSV file holding records of a companies employees and inserts it into a MySql Database. 
The java files makes use of DAO and DTO design patterns and also handles incorrect data the CSV file. 
The incorrect data the java file handles are where 2 or more employees have the same employeeId or if 2 or more employees have the same email. 
The incorrect data is written out to an txt file. To achieve this in an efficient manner Collection classes are used as well as multi threading.

How to run the project 

Please download the package and open it in your preferred IDE. 
Insert your user name and password in the properties file for your MySQl database.
To start the program run the App class with your own url to your local MySQL database in place of mine and also change the url to yours in thc JDBCTask class. 

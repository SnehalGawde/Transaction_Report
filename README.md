# Transaction_Report
Transaction_Report is a Java process to fetch daily transaction summary report for each client per product. The process takes a fixed width text file as an input and generates a CSV file as an output.

Getting Started
---------------------------------------------------------------
These steps will get you a copy of the project up and running on your local machine. 

Step 1 - 

Copy complete Transaction_Report folder on your local machine.

Step 2 -

Edit application.properties (Transaction_Report\src\main\resources\application.properties) file and update Input and Output files paths.

Step 3 -

Place an appropriate Input.txt file on the input file path mentioned in the application.properties file.

Step 4 -

Open command prompt to build the project using Maven build command. In the command prompt, change the current working directory to the root directory of the project and execute the following command:

mvn clean install

Step 5 -

Succesful execution of the build command will generate an executable jar file in Transaction_Report\target directory. Execute the following command to run the process:

java -jar target\Transaction_Report-0.0.1-SNAPSHOT.jar

Step 6 -

Succesful execution of the command mentioned in the Step 5 will generate a CSV file on the output file path mentioned in the application.properties file and process execution log file will be generated in Transaction_Report\logs directory.

Prerequisites
---------------------------------------------------------------
JDK 1.8, Maven

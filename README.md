# SPIDA_API_Challenge
A RESTful client for reading a job board, posting a job, and checking the status of the posting.

## Compilation Instructions:
To compile the program, download/unzip the archive and then navigate to the "src" folder.

With "src" as the working directory enter the following command.
```
$ javac -cp "../lib/json-20200518.jar" ./com/Batey/JobBoard/*.java ./com/Batey/Enums/*.java ./com/Batey/Utilities/*.java
```
## **To run the program:**
From the "src" directory enter the following command:
```
$ java -cp .;../lib/json-20200518.jar com/Batey/JobBoard/Main
```
*You may also load the project in your desired IDE and run it.*

## JUnit
Junit tests are included under the JunitTests folder.   

##Project Structure

SPIDA_API_CHALLENGE  
├───bin  
│   └───com  
│       └───Batey  
│           ├───Enums  
│           ├───JobBoard  
│           ├───JunitTests  
│           └───Utilities  
├───lib  
└───src  
    └───com  
        └───Batey  
            ├───Enums  
            ├───JobBoard  
            ├───JunitTests  
            └───Utilities  

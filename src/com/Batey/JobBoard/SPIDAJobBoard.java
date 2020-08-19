/**
 * 
 */
package com.Batey.JobBoard;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

import com.Batey.Enums.*;
import com.Batey.Utilities.*;


/**
 * A BBS type client program that interfaces with a RESTful API and displays job posting information.
 * Allows for application to a posting and monitoring of the application status.
 * @author Brian Batey
 *
 */
public class SPIDAJobBoard {
	
	//Create Needed Objects
	JSONUtilities jSONUtility = new JSONUtilities();
	StringUtilities stringUtility = new StringUtilities();
	UIPrinter printUtility = new UIPrinter();
	NetworkModule networkUtility = new NetworkModule();
	Scanner console = new Scanner(System.in);
	
	//Endpoints
	public static final String JOBS_ENDPOINT = "https://dev.spidasoftware.com/apply/jobs";
	public static final String APPS_ENDPOINT = "https://dev.spidasoftware.com/apply/applications";
	
	//Menu array for user menu choices
	private final String[] userChoiceArray = {"View All Job Postings", "Apply to a Job Posting", "Check Application Status", "Exit Program"};
	
	
	/**
	 * Runs the program loop until user chooses to exit program.
	 */
	public void run() {
		
		//Loop variable for User Input
		int userMenuChoice = -1;	
		
		//Get User Choice and Perform Appropriate Operation
		while(userMenuChoice != 4) {
			//Print UI/Menu Options
			printUtility.printIntroUI();
			printUtility.printUserMenu(userChoiceArray);
			
			try {
			//Get User choice to control program loop		
			userMenuChoice = printUtility.getMenuInput(console);		
			//Select Function
			selectFunction(userMenuChoice);
			
			}catch(InputMismatchException e) {
				System.out.println("ERROR: Input must be numeric and in range!!");
				console.nextLine();
			}		
		}		
	}
	
	/**
	 * Takes an integer as input and returns the function associated with that integer.
	 * Non-integer input and out of range input will result in an error message being displayed.
	 * @param input
	 * 			- an integer value in the range of [1 - 4]
	 */
	private void selectFunction(int input) {
		
		switch(input) {
		
		case 1://Display all job postings			
			displayAllJobPostings();
			break;
			
		case 2: //Begin Job Posting Procedure			
			applyToPosting();
			break;
			
		case 3: // Perform Job Status Lookup Procedue
			checkApplicationStatus();
			break;
			
		case 4: // User has chose to exit, release resources and display exit message			
			cleanup();			
			System.out.println("Goodbye!!");			
			break;
			
		default: //Input is invalid
			System.out.println("ERROR: Selection is out of Range. Please try again.");
			System.out.println();			
		}
	}
	
	/**
	 * Performs a GET request to the endpoint specified at @JOBS_ENDPOINT . 
	 * Retrieves all postings with the designated schema and prints them to the console.
	 */
	private void displayAllJobPostings() {
		
		//Clear Network Object Response Data
		networkUtility.clearResponseData();
		
		//Request all jobs from API Endpoint
		networkUtility.performGETRequest(JOBS_ENDPOINT);
		
		//Process data from request if successful
		List<JSONObject> jobList = jSONUtility.parseJSONObjectFromArray(networkUtility.getResponseData());			
				
		//Ensure Posts Exist and print to console
		if(jobList.size() > 0) {			
			//Print header
			printUtility.printJobPostingHeader();
			
			//Verify Schema	and Print Valid Results	
			for(JSONObject ele : jobList) {
				if(jSONUtility.verifyJobPostingSchema(ele)) {					
					displayJobPosting(ele);
				}else {//Report incorrect schema and continue
					System.out.println("ERROR: An element with an incorrect schema was found!! \n Discarding element!");
				}
			}			
		}else{ // If no jobs exist
			System.out.print("ERROR: No positions are currently available.");
		}
	}
	
	/**
	 * Takes a jobID from user input. Performs a get request to the specified endpoint to ensure job posting exists and
	 * begins a user data collection procedure. Creates a JSON object and posts it to the server using the appropriate endpoint. Prints
	 * response data to the console.  
	 */
	private void applyToPosting() {
		
		//Generate user map for storing application key/value atrribute data
		Map<String,String> userApplicationMap = generateApplicationMap();
		
		//Clear Network Object Response Data
		networkUtility.clearResponseData();
		
		//Print User Input Prompt and store returned value
		userApplicationMap.put(schemaMembers.JOBID.toString(), printUtility.promptUserWithMessage(console, userMessages.ID.toString()));
		
		
		//Check that id is of valid format (24 Hex characters)
		if(stringUtility.verifyIDString(userApplicationMap.get(schemaMembers.JOBID.toString()))) {			
			
			//Check to make sure job posting exists with given ID
			networkUtility.performGETRequest(JOBS_ENDPOINT, userApplicationMap.get(schemaMembers.JOBID.toString()));
			
			//If response is not empty, ID exists
			if(networkUtility.getResponseData().length() > 0) {
				
				//Display Job Post Selected
				System.out.println();
				System.out.println("You have selected the following position: ");
				displayJobPosting(new JSONObject(networkUtility.getResponseData()));
				
				//Display prompts to get user inputs for posting attributes	
				userApplicationMap.put(schemaMembers.NAME.toString(), printUtility.promptUserWithMessage(console, userMessages.NAME.toString()));
				userApplicationMap.put(schemaMembers.JUSTIFICATION.toString(), printUtility.promptUserWithMessage(console, userMessages.JUSTIFICATION.toString()));	
				userApplicationMap.put(schemaMembers.CODE.toString(),printUtility.promptUserWithMessage(console, userMessages.CODE.toString()));
				
				//TODO: Remove this and make it another method that produces a string and make another method to append it to the JSON object
				userApplicationMap.put(schemaMembers.ADDITIONAL.toString(), printUtility.promptUserWithMessage(console, userMessages.ADDITIONAL.toString()));
				
				//Create JSON Object
				JSONObject application = jSONUtility.createJSONObjectFromMap(userApplicationMap);
															
				//POST JSON Object
				networkUtility.performPOSTRequest(APPS_ENDPOINT, application.toString());
				
				//Display Return headers/ Success
				System.out.println("Posting Successful!!");
				System.out.print("Server Response: ");
				System.out.println(networkUtility.getResponseData());
				
			}else {
				System.out.println("ERROR: No Job Posting found with that ID!!");
			}
		}else {
			System.out.println("ERROR: ID is an invalid format. Must be 24 Hex Characters!");
		}
	}
	
	/**
	 * Performs a GET request for a specified application ID and prints the results to the console. 
	 */
	private void checkApplicationStatus() {
		
		//Setup Variables
		String applicationID = printUtility.promptUserWithMessage(console, userMessages.STATUS.toString());
		
		//Ensure properID is given
		if(stringUtility.verifyIDString(applicationID)){
			
			//Clear Network Object Response Data
			networkUtility.clearResponseData();
			
			//Get application Status
			networkUtility.performGETRequest(APPS_ENDPOINT, applicationID);
			
			//If application return data is not empty
			if(networkUtility.getResponseData().length() > 0) {
				
				//Create JSON Object
				JSONObject applicationJSON = new JSONObject(networkUtility.getResponseData());
				
				//Verify Application Schema
				if(jSONUtility.verifyApplicationSchema(applicationJSON)) {
															
					//Populate array
					Map<String,String> userAppMap = jSONUtility.generateApplicationMap(applicationJSON);
					
					//Display application Status
					displayApplicationStatus(userAppMap);					
				}else {
					System.out.println("ERROR: Application Object does not follow given schema!!");
				}
			}else {
				System.out.println("ERROR: No Application was found with the given ID!!");
			}
		}else {
			System.out.println("ERROR: ID is an invalid format. Must be 24 Hex Characters!");
		}
	}
	
	
	/**
	 * Perform cleanup procedures. Closes open resources.
	 */
	private void cleanup() {
		console.close();
	}
	
	/**
	 * Creates a Map for storing user values associated with creating an job application posting. The keys represent
	 * all attributes necessary for creating an application JSON Object as defined by the schema at : https://dev.spidasoftware.com/apply/api .	 * 
	 *
	 * Key Values added are as follows: 
	 * jobId
	 * name
	 * justification
	 * code
	 * additional links
	 * 
	 * 
	 * @return
	 * 		- a Map<String,String> with Application attributes as keys and blank strings as values.
	 */
	private Map<String,String> generateApplicationMap(){
		
		//List of possible attributes
		String[] userKeyArr = {"jobId", "name", "justification", "code", "additionalLinks"};
		
		//Create output object
		Map<String, String> output = new HashMap<String,String>();
		
		//Iterate userKeyArr and put each item in the map
		for(String item: userKeyArr) {
			output.put(item, "");
		}
		//Return resultant output
		return output;
	}
	
	/**
	 * Prints the attributes and values of a well-formed job posting JSON Object to the console. Utilizes the schema
	 * defined at https://dev.spidasoftware.com/apply/api
	 * @param obj
	 * 		- a well formed JSON Object representing a Job Posting 
	 */
	private void displayJobPosting(JSONObject obj) {		
		//Pass Object Values to UI Printer for formatting
		String id = obj.getString(schemaMembers.ID.toString());
		String pos = obj.getString(schemaMembers.POSITION.toString());				
		String desc = obj.getString(schemaMembers.DESCRIPTION.toString());
		
		//Requirements is an optional attribute per the schema, check if it exists and act appropriately
		if(obj.has("requirements")) {
			//Get requirements from JSON object
			List<String> reqList = jSONUtility.getArrayFromJSONObject(obj, schemaMembers.REQUIREMENTS.toString());
			
			//Call Printer Class for job element
			printUtility.printJobPosting(id, pos, reqList,  desc);
			
		}else {//If Requirements attribute is not present			
			//Call Printer Class for job element
			printUtility.printJobPosting(id, pos, desc);
		}
	}
	
	/**
	 * Prints an application posting status stored in the given map. Format is based on the schema found at: https://dev.spidasoftware.com/apply/api
	 * @param mapIn
	 * 		- a Map<String,String> conforming to the given schema
	 */
	private void displayApplicationStatus(Map<String, String>mapIn) {
		//Ensure keys exist
		if(mapIn.containsKey(schemaMembers.JOBID.toString())
			&&mapIn.containsKey(schemaMembers.NAME.toString())
			&&mapIn.containsKey(schemaMembers.JUSTIFICATION.toString())
			&&mapIn.containsKey(schemaMembers.CODE.toString())) {
			//Call Print Utility with values
			printUtility.printApplicationStatus(mapIn.get(schemaMembers.JOBID.toString()),
					mapIn.get(schemaMembers.NAME.toString()), 
					mapIn.get(schemaMembers.JUSTIFICATION.toString()), 
					mapIn.get(schemaMembers.CODE.toString()));		
		}else { //Something is wrong with the given Map print error
			System.out.println("ERROR: Necessary keys not found in collection object! Unable to print application status!!");
		}		
	}
}

/**
 * 
 */
package com.Batey.JobBoard;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
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
	
	//Endpoints
	public static final String JOBS_ENDPOINT = "https://dev.spidasoftware.com/apply/jobs";
	public static final String APPS_ENDPOINT = "https://dev.spidasoftware.com/apply/applications";
	
	//Menu array for user menu choices
	private final String[] userChoiceArray = {"View All Job Postings", "Apply to a Job Posting", "Check Application Status", "Exit Program"};
	
	
	/**
	 * Runs the program loop until user chooses to exit program.
	 */
	public void run() {
		
		//Create Objects
		JSONUtilities jSONUtility = new JSONUtilities();
		StringUtilities stringUtility = new StringUtilities();
		UIPrinter printUtility = new UIPrinter();
		NetworkModule networkUtility = new NetworkModule();
		Scanner console = new Scanner(System.in);
		
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
			selectFunction(userMenuChoice, jSONUtility, stringUtility, printUtility, networkUtility, console);
			
			}catch(InputMismatchException e) {
				System.out.println("ERROR: Input must be numeric and in range!!");
				console.nextLine();
			}		
		}		
	}
	
	/**
	 * Takes an integer as input and returns the function associated with that integer.
	 * Non-integer input and out of range input will result in an error message being displayed.
	 * 
	 * @param input
	 * 			- an integer value in the range of [1 - 4]
	 * @param console
	 * 			-input stream
	 * @param netMod
	 * 			- NetworkModule object
	 * @param printUtil
	 * 			- UIPrinter object
	 * @param stringUtil 
	 * 			- StringUtilities object
	 * @param jsonUtil
	 * 			- JSONUtilities object
	 */
	private void selectFunction(int input, JSONUtilities jsonUtil, StringUtilities stringUtil, UIPrinter printUtil, NetworkModule netMod, Scanner console) {
		
		switch(input) {
		
		case 1://Display all job postings			
			displayAllJobPostings(jsonUtil, netMod, printUtil);
			break;
			
		case 2: //Begin Job Posting Procedure			
			applyToPosting(jsonUtil, netMod, printUtil, stringUtil, console);
			break;
			
		case 3: // Perform Job Status Lookup Procedue
			checkApplicationStatus(jsonUtil, netMod, printUtil, console, stringUtil);
			break;
			
		case 4: // User has chose to exit, release resources and display exit message			
			cleanup(console);			
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
	 * 
	 * @param networkUtility
	 * 			- NetworkModule object
	 * @param printUtility
	 * 			- UIPrinter object	
	 * @param jSONUtility
	 * 			- JSONUtilities object
	 */
	private void displayAllJobPostings(JSONUtilities jSONUtility, NetworkModule networkUtility, UIPrinter printUtility) {
		
		//Clear Network Object Response Data
		networkUtility.clearResponseData();
		
		//Request all jobs from API Endpoint
		networkUtility.performGETRequest(JOBS_ENDPOINT);
		
		//Process data from request if successful
		List<JSONObject> jobList = jSONUtility.parseJSONObjectFromJSONArray(networkUtility.getResponseData());			
				
		//Ensure Posts Exist and print to console
		if(jobList.size() > 0) {			
			//Print header
			printUtility.printJobPostingHeader();
			
			//Verify Schema	and Print Valid Results	
			for(JSONObject ele : jobList) {
				if(jSONUtility.verifyJobPostingSchema(ele)) {					
					displayJobPosting(ele, jSONUtility, printUtility);
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
	 * 
	 * @param networkUtility
	 * 			- NetworkModule object
	 * @param printUtility
	 * 			- UIPrinter object
	 * @param stringUtility 
	 * 			- StringUtilities object
	 * @param jSONUtility
	 * 			- JSONUtilities object
	 * @param console
	 * 			- input stream
	 */
	private void applyToPosting(JSONUtilities jSONUtility, NetworkModule networkUtility, UIPrinter printUtility, StringUtilities stringUtility, Scanner console) {
		
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
				displayJobPosting(new JSONObject(networkUtility.getResponseData()),jSONUtility, printUtility);
				
				//Display prompts to get user inputs for posting attributes	
				userApplicationMap.put(schemaMembers.NAME.toString(), printUtility.promptUserWithMessage(console, userMessages.NAME.toString()));
				userApplicationMap.put(schemaMembers.JUSTIFICATION.toString(), printUtility.promptUserWithMessage(console, userMessages.JUSTIFICATION.toString()));	
				userApplicationMap.put(schemaMembers.CODE.toString(),printUtility.promptUserWithMessage(console, userMessages.CODE.toString()));							
				String additionalLinks = printUtility.promptUserWithMessage(console, userMessages.ADDITIONAL.toString());
				
				//Create JSON Object
				JSONObject userAppJSON = jSONUtility.createJSONObjectFromMap(userApplicationMap);
				
				//If additionalLinks exist, create JSONArray and append to application Object
				if(!additionalLinks.toLowerCase().equals(userMessages.NA.toString())) {
					JSONArray addArr = new JSONArray(additionalLinks.split(","));
					userAppJSON.put(schemaMembers.ADDITIONAL.toString(), addArr);
				}
				
				//Clear Network Module Response Data
				networkUtility.clearResponseData();
															
				//POST JSON Object
				networkUtility.performPOSTRequest(APPS_ENDPOINT, userAppJSON.toString());
				
				//Display Return headers/ Success
				System.out.println("Posting Successful!!");
				System.out.println();
				
				//Check if response is empty
				if(!networkUtility.getResponseData().isEmpty()) {
				//Create a representation of the server response and display
					Map<String,String> userAppMap = jSONUtility.generateApplicationMap(new JSONObject(networkUtility.getResponseData()));
					printUtility.displayApplicationStatus(userAppMap);
				}else {
					System.out.println("ERROR: No Response from Server!!");
				}				
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
	private void checkApplicationStatus(JSONUtilities jSONUtility, NetworkModule networkUtility, UIPrinter printUtility, Scanner console, StringUtilities stringUtility) {
		
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
					printUtility.displayApplicationStatus(userAppMap);					
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
	private void cleanup(Scanner console) {
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
	private void displayJobPosting(JSONObject obj, JSONUtilities jSONUtility, UIPrinter printUtility) {		
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
}

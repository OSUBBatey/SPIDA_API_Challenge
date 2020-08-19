/**
 * 
 */
package com.Batey.JobBoard;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import com.Batey.Enums.schemaMembers;
import com.Batey.Utilities.*;


/**
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
	
	final String JOBS_ENDPOINT = "https://dev.spidasoftware.com/apply/jobs";
	final String APPS_ENDPOINT = "https://dev.spidasoftware.com/apply/applications";
	
	
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
			printUtility.printUserMenu();
			
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
		
		//Display Exit Message
		System.out.print("Goodbye!!!");
		
		//Cleanup - release resources
		cleanup();
	}
	
	/**
	 * 
	 * @param input
	 */
	private void selectFunction(int input) {
		
		if(input == 1) {
			//Display all job postings
			displayAllJobPostings();
			
		}else if(input == 2) {
			//Begin Job Posting Procedure
			applyToPosting();
			
			
		}else if(input == 3) {
			//Perform Job Posting Status Lookup Procedure
			checkApplicationStatus();
		}else {//Input is invalid or user has chosen to exit the program
			if(input !=4) {
				System.out.println("ERROR: Selection is out of Range. Please try again.");
				System.out.println();
			}			
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
					
					//Pass Object Values to UI Printer for formatting
					String id = ele.getString(schemaMembers.ID.toString());
					String pos = ele.getString(schemaMembers.POSITION.toString());				
					String desc = ele.getString(schemaMembers.DESCRIPTION.toString());
					
					//Requirements is an optional attribute per the schema, check if it exists and act appropriately
					if(ele.has("requirements")) {
						//Get requirements from JSON object
						List<String> reqList = jSONUtility.getArrayFromJSONObject(ele, schemaMembers.REQUIREMENTS.toString());
						
						//Call Printer Class for job element
						printUtility.printJobPosting(id, pos, reqList,  desc);
						
					}else {//If Requirements attribute is not present
						
						//Call Printer Class for job element
						printUtility.printJobPosting(id, pos, desc);
					}
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
		/*
		 * userValueArr - String Array Representation -
		 * 0 - job ID
		 * 1 - user name
		 * 2 - user justification
		 * 3 - user project repo link
		 * 4 - user additional links
		 */
		String[] userValueArr = new String[5];
		String[] userKeyArr = {"jobId", "name", "justification", "code", "additionalLinks"}; //TODO: Move this out				
		
		//Clear Network Object Response Data
		networkUtility.clearResponseData();
		
		//Print User Input Prompt
		userValueArr[0] = printUtility.promptUserForJobID(console);		
		
		//Check that id is of valid format (24 Hex characters)
		if(stringUtility.verifyIDString(userValueArr[0])) {			
			
			//Check to make sure job posting exists with given ID
			networkUtility.performGETRequest(JOBS_ENDPOINT, userValueArr[0]);
			
			//If response is not empty, ID exists
			if(networkUtility.getResponseData().length() > 0) {
				
				//TODO:Display prompt with job posting and confirm with user that it is correct
				
				//Display prompts to get user inputs for posting attributes								
				userValueArr[1] = printUtility.promptUserForName(console);
				userValueArr[2] = printUtility.promptUserForJustification(console);	
				userValueArr[3] = printUtility.promptUserForProjectLink(console);
				
				//TODO: Create array with these values
				userValueArr[4] = printUtility.promptUserForAdditionalLink(console);
				
				//Create JSON Object
				JSONObject application = jSONUtility.createJSONObjectFromArray(userValueArr, userKeyArr);
								
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
		String applicationID = printUtility.promptUserForStatusID(console);
		
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
					
					//Create array for attributes
					String[] appValueArr = new String[4];
					
					//Populate array
					jSONUtility.populateApplicationArr(appValueArr, applicationJSON);
					
					//Display application Status
					printUtility.printApplicationStatus(appValueArr);
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
			
	private void cleanup() {
		console.close();
	}
	
}

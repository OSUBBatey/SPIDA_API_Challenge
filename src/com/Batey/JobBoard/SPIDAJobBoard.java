/**
 * 
 */
package com.Batey.JobBoard;

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
	
	JSONUtilities jSONUtility = new JSONUtilities();
	StringUtilities stringUtility = new StringUtilities();
	UIPrinter sysPrinter = new UIPrinter();
	NetworkModule networkUtility = new NetworkModule();
	Scanner console = new Scanner(System.in);
	final String JOBS_ENDPOINT = "https://dev.spidasoftware.com/apply/jobs";
	final String APPS_ENDPOINT = "https://dev.spidasoftware.com/apply/applications";
	
	
	/**
	 * 
	 */
	public void run() {
		
		//Variables
		int userMenuChoice = -1;	
		
		//Print UI/Menu Options
		sysPrinter.printIntroUI();
		sysPrinter.printUserMenu();
		
		
		//Get User choice to control program loop		
		userMenuChoice = sysPrinter.getMenuInput(console); //Returns an int .. fix this to use it for control loop		
		
		//Build this as a function with a switch
		if(userMenuChoice == 1) {
			//Display results if successful, prompt user for another action if not
			displayAllJobPostings();
			
		}else if(userMenuChoice == 2) {
			//Begin Job Posting Procedure
			applyToPosting();
			
			
		}else if(userMenuChoice == 3) {
			//Perform Job Posting Status Lookup Procedure
			checkApplicationStatus();
		}
		
		
		//Cleanup - release resources
		cleanup();
	}
	
	
	/**
	 * Write this stuff.
	 */
	private void displayAllJobPostings() {
		
		//Clear Network Object Response Data
		networkUtility.clearResponseData();
		
		//Request all jobs from API Endpoint
		networkUtility.performGETRequest(JOBS_ENDPOINT);
		
		//Process data from request if successful
		List<JSONObject> jobList = jSONUtility.parseJSONObjectFromArray(networkUtility.getResponseData());			
		//Print header
		sysPrinter.printJobPostingHeader();
		
		//Ensure Posts Exist and print to console
		if(jobList.size() > 0) {
			//Verify Schema	and Print Valid Results	
			for(JSONObject ele : jobList) {
				if(jSONUtility.verifyJobPostingSchema(ele)) {
					//Pass Object to UI Printer for formatting
					String id = ele.getString(schemaMembers.ID.toString());
					String pos = ele.getString(schemaMembers.POSITION.toString());				
					String desc = ele.getString(schemaMembers.DESCRIPTION.toString());
					
					if(ele.has("requirements")) {
						//Get requirements from object
						List<String> reqList = jSONUtility.getArrayFromJSONObject(ele, schemaMembers.REQUIREMENTS.toString());
						
						//Call Printer Class for job element
						sysPrinter.printJobPosting(id, pos, reqList,  desc);
					}else {
						//Call Printer Class for job element
						sysPrinter.printJobPosting(id, pos, desc);
					}
				}
			}
			
		}else{ // If no jobs exist
			System.out.print("No positions are currently available.");
		}
	}
	
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
		userValueArr[0] = sysPrinter.promptUserForJobID(console);		
		
		//Check that id is of valid format (24 Hex characters)
		if(stringUtility.verifyIDString(userValueArr[0])) {			
			
			//Check to make sure job posting exists with given ID
			networkUtility.performGETRequest(JOBS_ENDPOINT, userValueArr[0]);
			
			//If response is not empty, ID exists
			if(networkUtility.getResponseData().length() > 0) {
				
				//TODO:Display prompt with job posting and confirm with user that it is correct
				
				//Display prompts to get user inputs for posting attributes								
				userValueArr[1] = sysPrinter.promptUserForName(console);
				userValueArr[2] = sysPrinter.promptUserForJustification(console);	
				userValueArr[3] = sysPrinter.promptUserForProjectLink(console);
				
				//TODO: Create array with these values
				userValueArr[4] = sysPrinter.promptUserForAdditionalLink(console);
				
				//Create JSON Object
				JSONObject application = jSONUtility.createJSONObjectFromArray(userValueArr, userKeyArr);
								
				//POST JSON Object
				networkUtility.performPOSTRequest(APPS_ENDPOINT, application.toString());
				
				//Display Return headers/ Success
				System.out.println(networkUtility.getResponseData());
			}
		}
	}
	
	/**
	 * 
	 */
	private void checkApplicationStatus() {
		
		//Setup Variables
		String applicationID = sysPrinter.promptUserForStatusID(console);
		
		//Ensure properID is given
		if(stringUtility.verifyIDString(applicationID)){			
			String[] applicationAttributeArr = {"name", "justification", "code", "jobId", "additionalLinks"}; //TODO:MOVE THIS ELSEWHERE
			
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
					sysPrinter.printApplicationStatus(appValueArr);
				}
			}
		}
	}
			
	private void cleanup() {
		console.close();
	}
	
}

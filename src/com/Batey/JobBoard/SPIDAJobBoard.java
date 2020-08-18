/**
 * 
 */
package com.Batey.JobBoard;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import com.Batey.Utilities.NetworkModule;
import com.Batey.Utilities.UIPrinter;
import com.Batey.Enums.schemaMembers;
import com.Batey.Utilities.JSONUtilities;

/**
 * @author Brian Batey
 *
 */
public class SPIDAJobBoard {
	
	JSONUtilities testJSON = new JSONUtilities();	
	UIPrinter printer = new UIPrinter();
	NetworkModule netMod = new NetworkModule();
	Scanner console = new Scanner(System.in);
	final String JOBS_ENDPOINT = "https://dev.spidasoftware.com/apply/jobs";
	
	
	/**
	 * 
	 */
	public void run() {
		
		//Variables
		int userMenuChoice = -1;	
		
		//Print UI/Menu Options
		printer.printIntroUI();
		printer.printUserMenu();
		
		
		//Get User choice to control program loop		
		userMenuChoice = printer.getMenuInput(console); //Returns an int .. fix this to use it for control loop		
		
		//Build this as a function with a switch
		if(userMenuChoice == 1) {
			//Display results if successful, prompt user for another action if not
			displayAllJobPostings();
			
		}else if(userMenuChoice == 2) {
			//Begin Job Posting Procedure
			printer.promptUserForJobID(console);
			
		}else if(userMenuChoice == 3) {
			//Perform Job Posting Status Lookup Procedure
		}
		
		
		//Cleanup - release resources
		cleanup();
	}
	
	
	/**
	 * Write this stuff.
	 */
	private void displayAllJobPostings() {
		
		//Request all jobs from API Endpoint
		netMod.performGETRequest(JOBS_ENDPOINT);
		
		//Process data from request if successful
		List<JSONObject> jobList = testJSON.parseJSONObjectFromArray(netMod.getResponseData());			
		//Print header
		printer.printJobPostingHeader();
		
		//Ensure Posts Exist and print to console
		if(jobList.size() > 0) {
			//Verify Schema	and Print Valid Results	
			for(JSONObject ele : jobList) {
				if(testJSON.verifyObjectSchema(ele)) {
					//Pass Object to UI Printer for formatting
					String id = ele.getString(schemaMembers.ID.toString());
					String pos = ele.getString(schemaMembers.POSITION.toString());				
					String desc = ele.getString(schemaMembers.DESCRIPTION.toString());
					
					if(ele.has("requirements")) {
						//Get requirements from object
						List<String> reqList = testJSON.getArrayFromJSONObject(ele, schemaMembers.REQUIREMENTS.toString());
						
						//Call Printer Class for job element
						printer.printJobPosting(id, pos, reqList,  desc);
					}else {
						//Call Printer Class for job element
						printer.printJobPosting(id, pos, desc);
					}
				}
			}
			
		}else{ // If no jobs exist
			System.out.print("No positions are currently available.");
		}
	}
	
	private void applyToPosting() {
		
		//Setup variables 
		String jobID;
		String uName;
		String uJustification;
		String uRepoLink; //'code' attribute in JSON
		String additionalLinks;
		
		//Print User Input Prompt
		jobID = printer.promptUserForJobID(console);
		
		//Check to make sure job posting exists with given ID
		
		//Display prompts to get user inputs for posting attributes
		
		//Get name - string
		
		
		
		
	}
	
	private void cleanup() {
		console.close();
	}
}

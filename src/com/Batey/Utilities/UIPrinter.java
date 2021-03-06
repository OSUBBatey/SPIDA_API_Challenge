/**
 * 
 */
package com.Batey.Utilities;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.Batey.Enums.schemaMembers;


/**
 * Printer Class used to print UI elements and user prompts to an output stream. Provides utilities 
 * for printing Job Postings/Application Postings.
 *
 * @author Brian Batey
 *
 */
public class UIPrinter {		
	
	/**
	 * Prints the Intro header for the program to the console.
	 */
	public void printIntroUI() {
		System.out.println("############################################");
		System.out.println("#          SPIDA API CHALLENGE BBS         #");
		System.out.println("############################################");		
	}
	
	/**
	 * Prints each item in the given array in ascending numerical index order. Prepends each element with the index number starting from "1".
	 * @param input
	 * 			- an ordered array of String containing desired menu choices to be displayed
	 */
	public void printUserMenu(String[] arrIn) {		
		for(int i=1; i<=arrIn.length; i++) {
			System.out.println( i +". "+ arrIn[i-1]);
		}		
	}
	
	/**
	 * Prompts the user to input an integer value and then returns the given value. Input must be numeric and within Integer Range.
	 * @param input
	 * 		- the input stream to use
	 * @return
	 * 		- an integer value
	 */
	public int getMenuInput(Scanner input) {		
		//Initialize Variables		
		int output = -1;
										
		System.out.println();
		System.out.print("Please Enter The Number of Your Selection: ");
		
		//Get User Input		
		output = input.nextInt();
		input.nextLine();
		
		return output;
	}
	
	/**
	 * Prints a job posting to the console. Format is based on the schema found at : https://dev.spidasoftware.com/apply/api.
	 * 
	 * @param id
	 * 		- a string value representing the JobID
	 * @param pos
	 * 		- a string value representing the position title	 * 
	 * @param reqList
	 * 		- a List<String> containing desired requirements
	 * @param desc
	 * 		- a string value represeting the job description
	 */
	public void printJobPosting(String id, String pos, List<String> reqList, String desc) {				
		System.out.println("############################################");
		System.out.println("Job ID: " + id);
		System.out.println("Position: " + pos);		
		System.out.println("Job description: " + desc);
		System.out.print("Requirements: ");
		
		//Print all members of requirements list
		for(int i=0; i<reqList.size(); i++) {
			
			System.out.print(" " + reqList.get(i));
			if(i!=reqList.size()-1) {System.out.println(",");} // Print a comma and carriage return for every entry but the last
		}
		System.out.println(); // Print New Line		
		System.out.println("############################################");
		System.out.println();
	}
	
	/**
	 * Prints a job posting to the console. 
	 * Format is based on the schema found at : https://dev.spidasoftware.com/apply/api.
	 * 
	 * 
	 * @param id
	 * 		- a string value representing the JobID
	 * @param pos
	 * 		- a string value representing the position title		
	 * @param desc
	 * 		- a string value represeting the job description
	 */
	public void printJobPosting(String id, String pos, String desc) {
		System.out.println();		
		System.out.println("############################################");
		System.out.println("Job ID: " + id);
		System.out.println("Position: " + pos);		
		System.out.println("Job description: " + desc);		
		System.out.println("############################################");
		System.out.println();
	}
	
	/**
	 * Prints a header for job positings to the console.
	 */
	public void printJobPostingHeader() {
		System.out.println();
		System.out.println("############################################");
		System.out.println("####       All Current Job Postings     ####");
		System.out.println("############################################");
		System.out.println();
	}
	
	/**
	 * Creates a user prompt with the given message and returns a string based on the input stream. Does not allow blank input.
	 * @param input
	 * 		- the input stream 
	 * @param message
	 * 		- a string value representing the message to be displayed to the user
	 * @return
	 * 		- a string value representing the user's response
	 */
	public String promptUserWithMessage(Scanner input, String message){		
		
		String strOut = "";
		while(strOut.isEmpty()){
			System.out.print(message);		
			strOut = input.nextLine();
			strOut.trim();
			if(strOut.isEmpty()) {
				System.out.println("ERROR: Entry must not be empty!!!");
			}
		}
		return strOut;
	}
	
	/**
	 * Prints an application posting status. Format is based on the schema found at: https://dev.spidasoftware.com/apply/api
	 * @param mapIn
	 * 		- a map containing key/value pairs that conform to the given schema
	 */
	public void printApplicationStatus(String appID, String jobID, String name, String justification, String code) {
		
				System.out.println("############################################");
				System.out.println("#             Application Status           #");		
				System.out.println("############################################");
				System.out.println("Application ID: " + appID);
				System.out.println("Job ID: " + jobID);
				System.out.println("Name: " + name);
				System.out.println("Justification: " + justification);
				System.out.println("Repo Link: " + code);
				System.out.println("Additional Links: None ");
				System.out.println("############################################");
				System.out.println();						
	}
	
	/**
	 * Prints an application posting status. Format is based on the schema found at: https://dev.spidasoftware.com/apply/api
	 * @param mapIn
	 * 		- a map containing key/value pairs that conform to the given schema
	 */
	public void printApplicationStatus(String appID, String jobID, String name, String justification, String code, String additional) {
		
				System.out.println("############################################");
				System.out.println("#             Application Status           #");		
				System.out.println("############################################");
				System.out.println("Application ID: " + appID);
				System.out.println("Job ID: " + jobID);
				System.out.println("Name: " + name);
				tokenPrinter(100, "Justification: " + justification, " ");
				System.out.println("Repo Link: " + code);
				System.out.println ("Additional Links: " + additional);
				System.out.println("############################################");
				System.out.println();						
	}
	
	/**
	 * Prints an application posting status stored in the given map. Format is based on the schema found at: https://dev.spidasoftware.com/apply/api
	 * @param mapIn
	 * 		- a Map<String,String> conforming to the given schema
	 */
	public void displayApplicationStatus(Map<String, String>mapIn) {
		//Ensure keys exist
		if(mapIn.containsKey(schemaMembers.JOBID.toString())
			&&mapIn.containsKey(schemaMembers.NAME.toString())
			&&mapIn.containsKey(schemaMembers.JUSTIFICATION.toString())
			&&mapIn.containsKey(schemaMembers.CODE.toString())
			&&mapIn.containsKey(schemaMembers.ID.toString())) {
			//Check for additional links
			if(mapIn.containsKey(schemaMembers.ADDITIONAL.toString())) {
				//Call Print Utility with values
				printApplicationStatus(mapIn.get(schemaMembers.ID.toString()),
						mapIn.get(schemaMembers.JOBID.toString()),
						mapIn.get(schemaMembers.NAME.toString()), 
						mapIn.get(schemaMembers.JUSTIFICATION.toString()), 
						mapIn.get(schemaMembers.CODE.toString()),
						mapIn.get(schemaMembers.ADDITIONAL.toString()));	
				
			}else { //Additional parameters do not exist
				//Call Print Utility with values
				printApplicationStatus(mapIn.get(schemaMembers.ID.toString()),
						mapIn.get(schemaMembers.JOBID.toString()),
						mapIn.get(schemaMembers.NAME.toString()), 
						mapIn.get(schemaMembers.JUSTIFICATION.toString()), 
						mapIn.get(schemaMembers.CODE.toString()));	
			}				
		}else { //Something is wrong with the given Map print error
			System.out.println("ERROR: Necessary keys not found in collection object! Unable to print application status!!");
		}		
	}
	
	/**
	 * Tokenizes a string by the given regular expression and prints tokens with a trailing whitespace until the given character width is reached 
	 * or surpassed. Once width is reached or surpassed, a new line character is printed and the remaining tokens are printed following the same 
	 * procedure.
	 * 
	 * @param width
	 * 		- an integer value of the desired character width
	 * @param input
	 * 		- the string to split printing 
	 * @param regex
	 * 		- a string representing a regex expression for delimiting
	 * 		
	 */
	public void tokenPrinter(int width, String input, String regex) {
				
		//Ensure width is not zero and width is longer than input string.
		if(width > 0 && width <= input.length()) {
			
			//Tokenize string by regular expression
			String[] tokenArr = input.split(regex);
			
			//Setup variables
			StringBuilder sb = new StringBuilder();
			int charCount = 0;
			
			for (int i = 0; i<tokenArr.length;i++) {
				
				//Increment character count by token size plus one for whitespace replacement
				charCount += tokenArr[i].length()+1;
				
				//If charCount is less than desired max character width append the token with trailing whitespace
				if(charCount < width) {
					sb.append(tokenArr[i]+" ");
				}else {//Else token is an end token so append and print line with carriage return
					sb.append(tokenArr[i]);
					System.out.println(sb.toString());
					
					//Clear StringBuilder and reset charCount to Zero
					sb.setLength(0);
					charCount = 0;
				}
			}
		}else {//Else print original string if it is not empty
			if(input.length() > 0) {
				System.out.println(input);
			}			
		}		
		
	}
}

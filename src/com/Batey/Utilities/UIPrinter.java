/**
 * 
 */
package com.Batey.Utilities;

import java.util.List;
import java.util.Scanner;


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
	 * Prompts the user to input an integer value and then returns the given value. Can cause an exception to be thrown if user enters a non-numeric character.
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
			if(i!=reqList.size()-1) {System.out.print(",");} // Print a comma for every entry but the last
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
				System.out.println("Justification: " + justification);
				System.out.println("Repo Link: " + code);
				System.out.println("Additional Links: " + additional);
				System.out.println("############################################");
				System.out.println();						
	}	
}

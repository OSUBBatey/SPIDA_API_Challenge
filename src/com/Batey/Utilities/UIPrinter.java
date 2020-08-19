/**
 * 
 */
package com.Batey.Utilities;

import java.util.List;
import java.util.Scanner;

/**
 * @author Brian Batey
 *
 */
public class UIPrinter {		
	
	/**
	 * 
	 */
	public void printIntroUI() {
		System.out.println("############################################");
		System.out.println("#          SPIDA API CHALLENGE BBS         #");
		System.out.println("############################################");		
	}
	
	/**
	 * 
	 */
	public void printUserMenu() {
		//Move this array outside of this method (make it a parameter)
		String[] userChoiceArray = {"View All Job Postings", "Apply to a Job Posting", "Check Application Status", "Exit Program"};
		
		for(int i=1; i<=userChoiceArray.length; i++) {
			System.out.println( i +". "+userChoiceArray[i-1]);
		}		
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public int getMenuInput(Scanner input) {		
		//Initialize Variables		
		int output = -1;
		
		//TODO: SETUP ERROR CHECKING - make sure numeric and not empty/in range						
		System.out.println();
		System.out.print("Please Enter The Number of Your Selection: ");
		
		//Get User Input
		output = input.nextInt();
		input.nextLine();
		
		return output;
	}
	
	/**
	 * 
	 * @param id
	 * @param pos
	 * @param reqList
	 * @param desc
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
	 * 
	 * @param id
	 * @param pos
	 * @param desc
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
	 * 
	 */
	public void printJobPostingHeader() {
		System.out.println();
		System.out.println("############################################");
		System.out.println("####       All Current Job Postings     ####");
		System.out.println("############################################");
		System.out.println();
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public String promptUserForJobID(Scanner input){		
		//TODO: SETUP ERROR CHECKING - empty string etc
		String strOut = "";
		System.out.print("Please enter the ID for the position you would like to apply for: ");		
		strOut = input.nextLine();
		return strOut;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public String promptUserForName(Scanner input){		
		//TODO: SETUP ERROR CHECKING - empty string etc
		String strOut = "";
		System.out.print("Please enter your Name for the application: ");		
		strOut = input.nextLine();
		return strOut;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public String promptUserForJustification(Scanner input){		
		//TODO: SETUP ERROR CHECKING - empty string etc
		String strOut = "";
		System.out.print("Enter justification as to why you are right for the position: ");		
		strOut = input.nextLine();
		return strOut;
	}
	
	public String promptUserForProjectLink(Scanner input){		
		//TODO: SETUP ERROR CHECKING - empty string etc
		String strOut = "";
		System.out.print("Enter a link to the repository containing this project: ");		
		strOut = input.nextLine();
		return strOut;
	}
	
	public String promptUserForAdditionalLink(Scanner input){		
		//TODO: SETUP ERROR CHECKING - empty string etc
		String strOut = "";
		System.out.print("Enter links to any additional projects that you would like to be viewed: ");		
		strOut = input.nextLine();
		return strOut;
	}
	
	public String promptUserForStatusID(Scanner input){		
		//TODO: SETUP ERROR CHECKING - empty string etc
		String strOut = "";
		System.out.print("Please enter the ID for the application you would like to check the status for: ");				
		strOut = input.nextLine();
		return strOut;
	}
}

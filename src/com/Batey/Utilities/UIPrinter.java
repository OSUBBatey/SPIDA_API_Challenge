/**
 * 
 */
package com.Batey.Utilities;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.Batey.Enums.schemaMembers;

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
	 * @param message
	 * @return
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
	 * 
	 * @param mapIn
	 */
	public void printApplicationStatus(Map<String,String> mapIn) {
		System.out.println("############################################");
		System.out.println("#             Application Status           #");		
		System.out.println("############################################");		
		System.out.println("Job ID: " + mapIn.get(schemaMembers.JOBID.toString()));
		System.out.println("Name: " + mapIn.get(schemaMembers.NAME.toString()));
		System.out.println("Justification: " + mapIn.get(schemaMembers.JUSTIFICATION.toString()));
		System.out.println("Repo Link: " + mapIn.get(schemaMembers.CODE.toString()));
		System.out.println("Additional Links: "); //TODO: Add loop here for array(additional)
		System.out.println("############################################");
		System.out.println();
	}	
}

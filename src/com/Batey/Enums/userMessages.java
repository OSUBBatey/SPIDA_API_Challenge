/**
 * 
 */
package com.Batey.Enums;

/**
 * Enumeration class to store commonly used messages for passing to prompt and print methods.
 * @author Brian Batey
 *
 */
public enum userMessages{
	ID("Please enter the ID for the position you would like to apply for: "),
	NAME("Please enter your Name for the application: "),
	JUSTIFICATION("Enter justification as to why you are right for the position: "),
	CODE("Enter a link to the repository containing this project: "),
	ADDITIONAL("Enter links to any additional projects that you would like to be viewed: "),
	STATUS("Please enter the ID for the application you would like to check the status for: ");
	
	private String type;
	
	private userMessages(String name) {
		this.type = name;
	}
	
	@Override
	/**
	 * Returns the string representation of the Enumeration value.
	 */
	public String toString() {
		return type;
	}
}
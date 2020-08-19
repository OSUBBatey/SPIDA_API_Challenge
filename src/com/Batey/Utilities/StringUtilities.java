/**
 * 
 */
package com.Batey.Utilities;

/**
 * Verifies that a given ID is of the correct format. The format is as follows:
 * String must be exactly 24 characters long and be in the range of Hex values. [0-9,A-F,a-f]
 *
 * @author Brian Batey
 *
 */
public class StringUtilities {
	
	/**
	 *  Verifies that a given ID is of the correct format. The format is as follows:
	 * String must be exactly 24 characters long and be in the range of Hex values. [0-9,A-F,a-f]
	 * 
	 * @param input
	 * 		- the string to test
	 * @return
	 * 		- a Boolean with the value of true if format is correct, false otherwise
	 */	 
	public Boolean verifyIDString(String input) {
		return input.matches("[0-9A-Fa-f]{24}");
	}
	
}

/**
 * 
 */
package com.Batey.Utilities;

/**
 * @author Blood
 *
 */
public class StringUtilities {
	
	public Boolean verifyIDString(String input) {
		return input.matches("[0-9A-Fa-f]{24}");
	}
	
}

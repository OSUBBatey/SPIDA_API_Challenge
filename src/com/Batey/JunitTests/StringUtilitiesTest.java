/**
 * 
 */
package com.Batey.JunitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import com.Batey.Utilities.StringUtilities;

/**
 * @author Brian Batey
 *
 */
class StringUtilitiesTest {
	 StringUtilities classUnderTest;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		classUnderTest = new StringUtilities();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}
	
	/*
	 * Invalid characters in string.
	 */
	@Test
	public final void testValidatorInvalidCharacters() {
		//Setup 
		Boolean expectedVal = false;
		String badString = "abcdefghijklabcdjklyqwer";
		
		//Run test
		Boolean actualVal = classUnderTest.verifyIDString(badString);
		
		assertEquals(expectedVal, actualVal);
	}
	
	/*
	 * String longer than 24 characters.
	 */
	@Test
	public final void testValidatorOversizeString() {
		//Setup 
		Boolean expectedVal = false;
		String badString = "123456789123456789abcdef1";
		
		//Run test
		Boolean actualVal = classUnderTest.verifyIDString(badString);
		
		assertEquals(expectedVal, actualVal);
	}
	
	/*
	 * Empty String.
	 */
	@Test
	public final void testValidatorEmptyString() {
		//Setup 
		Boolean expectedVal = false;
		String badString = "";
		
		//Run test
		Boolean actualVal = classUnderTest.verifyIDString(badString);
		
		assertEquals(expectedVal, actualVal);
	}
	
	/*
	 * Single character string.
	 */
	@Test
	public final void testValidatorShortString() {
		//Setup 
		Boolean expectedVal = false;
		String badString = "a";
		
		//Run test
		Boolean actualVal = classUnderTest.verifyIDString(badString);
		
		assertEquals(expectedVal, actualVal);
	}
	
	/*
	 * Valid String.
	 */
	@Test
	public final void testValidatorValidString() {
		//Setup 
		Boolean expectedVal = true;
		String goodString = "19191919191919191919abcd";
		
		//Run test
		Boolean actualVal = classUnderTest.verifyIDString(goodString);
		
		assertEquals(expectedVal, actualVal);
	}
	
	/*
	 * Valid Random String.
	 */
	@Test
	public final void testValidatorValidRandomString() {
		//Setup 
		Boolean expectedVal = true;
		String stringPool = "abcdef123456789";
		StringBuilder sb = new StringBuilder();
		Random ranGen = new Random();
		String goodString;
				
		//Create Random String
		for(int i=0;i<24;i++) {
			sb.append(stringPool.charAt(ranGen.nextInt(stringPool.length())));
		}
		goodString = sb.toString();
		
		//Run test
		Boolean actualVal = classUnderTest.verifyIDString(goodString);
		
		assertEquals(expectedVal, actualVal);
	}

}

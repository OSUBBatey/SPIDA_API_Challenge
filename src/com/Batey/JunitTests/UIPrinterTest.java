/**
 * 
 */
package com.Batey.JunitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Batey.Enums.schemaMembers;
import com.Batey.Utilities.UIPrinter;

/**
 * Test cases for UIPrinter Class.
 * @author Brian Batey
 *
 */
class UIPrinterTest {
	
	//Setup input/output stream variables
	private final ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
	private final PrintStream originalConsole = System.out;
	private final InputStream sysInOriginal = System.in;
	
	UIPrinter classUnderTest;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		//Set system output to custom byte stream.
		System.setOut(new PrintStream(contentStream));
		classUnderTest = new UIPrinter();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
		//Set output back to console
		System.setOut(originalConsole);
		System.setIn(sysInOriginal);
	}

	/*
	 * Functionality Test for testPrintIntroUI
	 */
	@Test
	final void testPrintIntroUI() {
		
		//Setup
		String expected = "############################################\r\n"  
						+ "#          SPIDA API CHALLENGE BBS         #\r\n" 
						+ "############################################";
		
		
		//Init class under test
		classUnderTest.printIntroUI();
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
	
		//Perform Assertion
		assertEquals(expected,actual);
	}
	
	/*
	 * Functionality Test for printJobPostingHeader
	 */
	@Test
	final void testPrintJobPostingHeader() {
		
		//Setup
		String expected = "\r\n" + 
				"############################################\r\n" + 
				"####       All Current Job Postings     ####\r\n" + 
				"############################################\r\n";
		
		
		//Init class under test
		classUnderTest.printJobPostingHeader();
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
				
		//Perform Assertion
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests for getMenuInput
	 */
	
	/*
	 * Valid Input
	 */
	@Test
	final void testGetMenuInputValid() {
		//Setup
		int expected = 1;		
		ByteArrayInputStream in = new ByteArrayInputStream(("1"+System.lineSeparator()).getBytes());
		System.setIn(in);
		Scanner mock = new Scanner(in);
		
		//Init class under test
		int actual = classUnderTest.getMenuInput(mock);
		
		//Assert
		assertEquals(expected,actual);
	}
	
	/*
	 * Non-numeric input
	 */
	@Test
	final void testGetMenuInputNonNumeric() {
		//Setup		
		ByteArrayInputStream in = new ByteArrayInputStream(("a"+System.lineSeparator()).getBytes());
		System.setIn(in);
		Scanner mock = new Scanner(in);
		
		//Init class under test	
		 Assertions.assertThrows(InputMismatchException.class, () -> {
			 	classUnderTest.getMenuInput(mock);
		    });
	}
	
	/*
	 * No Input
	 */
	@Test
	final void testGetMenuInputNoInput() {
		//Setup		
		ByteArrayInputStream in = new ByteArrayInputStream(System.lineSeparator().getBytes());
		System.setIn(in);
		Scanner mock = new Scanner(in);
		
		//Init class under test	
		 Assertions.assertThrows(NoSuchElementException.class, () -> {
			 	classUnderTest.getMenuInput(mock);
		 	});
	}
	
	/*
	 *  Large Number - (In the int range)
	 */
	@Test
	final void testGetMenuInputValidLargeNumber() {
		//Setup
		int expected = 14758939;		
		ByteArrayInputStream in = new ByteArrayInputStream(("14758939"+System.lineSeparator()).getBytes());
		System.setIn(in);
		Scanner mock = new Scanner(in);
		
		//Init class under test
		int actual = classUnderTest.getMenuInput(mock);
		
		//Assert
		assertEquals(expected,actual);
	}
	
	/*
	 * Numeric input out of integer range
	 */
	@Test
	final void testGetMenuInputOutOfIntegerRange() {
		//Setup		
		ByteArrayInputStream in = new ByteArrayInputStream(("a"+System.lineSeparator()).getBytes());
		System.setIn(in);
		Scanner mock = new Scanner(in);
		
		//Init class under test	
		 Assertions.assertThrows(InputMismatchException.class, () -> {
			 	classUnderTest.getMenuInput(mock);
		    });
	}
	
	/**
	 * Tests for printJobPosting
	 */
	
	/*
	 * PrintJobPosting test with Requirements List
	 */
	@Test
	final void testPrintJobPostingWithReqList() {
		//Setup
		String id = "ID",pos = "POS",desc = "DESC";
		List<String> reqList = new ArrayList<String>();
		reqList.add("something");
		String expected = "############################################\r\n" + 
				"Job ID: ID\r\n" + 
				"Position: POS\r\n" + 
				"Job description: DESC\r\n" + 
				"Requirements:  something\r\n" + 
				"############################################\r\n" + 
				"";
		
		//Init class under test
		classUnderTest.printJobPosting(id, pos, reqList, desc);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
		
		//Assert
		assertEquals(expected,actual);
	}
	
	/*
	 *  PrintJobPosting test with empty Requirements List
	 */
	@Test
	final void testPrintJobPostingWithEmptyReqList() {
		//Setup
		String id = "ID",pos = "POS",desc = "DESC";
		List<String> reqList = new ArrayList<String>();
		
		String expected = "############################################\r\n" + 
				"Job ID: ID\r\n" + 
				"Position: POS\r\n" + 
				"Job description: DESC\r\n" + 
				"Requirements: \r\n" + 
				"############################################";
		
		//Init class under test
		classUnderTest.printJobPosting(id, pos, reqList, desc);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-4);
		
		//Assert
		assertEquals(expected,actual);
	}
	
	/*
	 * PrintJobPosting test with no Requirements List
	 */
	@Test
	final void testPrintJobPostingNoReqList() {
		//Setup
		String id = "ID",pos = "POS",desc = "DESC";
		
		String expected = "\r\n" + 
				"############################################\r\n" + 
				"Job ID: ID\r\n" + 
				"Position: POS\r\n" + 
				"Job description: DESC\r\n" + 
				"############################################";
		
		//Init class under test
		classUnderTest.printJobPosting(id, pos, desc);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-4);
				
		//Assert
		assertEquals(expected,actual);
	}
	
	/**
	 * PrintApplicationStatus tests
	 */
	
	/*
	 * printApplicationStatus with no additionalLinks
	 */
	@Test
	final void testPrintApplicationStatus() {
		//Setup
		String appID = "ID", jobID = "jID", name = "Bam", justification = "just do it", code = "test.com";
		
		String expected = "############################################\r\n" + 
				"#             Application Status           #\r\n" + 
				"############################################\r\n" + 
				"Application ID: ID\r\n" + 
				"Job ID: jID\r\n" + 
				"Name: Bam\r\n" + 
				"Justification: just do it\r\n" + 
				"Repo Link: test.com\r\n" + 
				"Additional Links: None \r\n" + 
				"############################################";
		
		//Init class under test
		classUnderTest.printApplicationStatus(appID, jobID, name, justification, code);;
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-4);
				
		//Assert
		assertEquals(expected,actual);
	}
	
	/*
	 * printApplicationStatus with additionalLinks multiple
	 */
	@Test
	final void testPrintApplicationStatusWithAddLinksMult() {
		//Setup
		String appID = "ID", jobID = "jID", name = "Bam", justification = "just do it", code = "test.com", add = "morestuff.com, otherstuff.com";
		
		String expected = "############################################\r\n" + 
				"#             Application Status           #\r\n" + 
				"############################################\r\n" + 
				"Application ID: ID\r\n" + 
				"Job ID: jID\r\n" + 
				"Name: Bam\r\n" + 
				"Justification: just do it\r\n" + 
				"Repo Link: test.com\r\n" + 
				"Additional Links: morestuff.com, otherstuff.com\r\n" + 
				"############################################";
		
		//Init class under test
		classUnderTest.printApplicationStatus(appID, jobID, name, justification, code, add);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-4);

		//Assert
		assertEquals(expected,actual);
	}
	
	/*
	 * printApplicationStatus with additionalLinks Single
	 */
	@Test
	final void testPrintApplicationStatusWithAddLinksSingle() {
		//Setup
		String appID = "ID", jobID = "jID", name = "Bam", justification = "just do it", code = "test.com", add = "morestuff.com";
		
		String expected = "############################################\r\n" + 
				"#             Application Status           #\r\n" + 
				"############################################\r\n" + 
				"Application ID: ID\r\n" + 
				"Job ID: jID\r\n" + 
				"Name: Bam\r\n" + 
				"Justification: just do it\r\n" + 
				"Repo Link: test.com\r\n" + 
				"Additional Links: morestuff.com\r\n" + 
				"############################################";
		
		//Init class under test
		classUnderTest.printApplicationStatus(appID, jobID, name, justification, code, add);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-4);

		//Assert
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests for printUserMenu
	 */
	
	/*
	 * printUserMenu test Multiple 
	 */
	@Test
	final void testPrintUserMenuMulti() {
		//Setup
		String[] arr = {"Option", "AnotherOption", "Yet Another"};
		String expected = "1. Option\r\n" + 
				"2. AnotherOption\r\n" + 
				"3. Yet Another";
		
		//Init Class Under test
		classUnderTest.printUserMenu(arr);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
						
		//Perform Assertion
		assertEquals(expected,actual);
	}
	
	/*
	 * printUserMenu test Single 
	 */
	@Test
	final void testPrintUserMenuSingle() {
		//Setup
		String[] arr = {"Option"};
		String expected = "1. Option";
		
		//Init Class Under test
		classUnderTest.printUserMenu(arr);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
						
		//Perform Assertion
		assertEquals(expected,actual);
	}
	
	/*
	 * printUserMenu test empty array 
	 */
	@Test
	final void testPrintUserMenuEmpty() {
		//Setup
		String[] arr = {};
		String expected = "";
		
		//Init Class Under test
		classUnderTest.printUserMenu(arr);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length());
						
		//Perform Assertion
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests for promptUserWithMessage
	 */
	
	/*
	 * Test valid input
	 */
	@Test
	final void testPromptUserWithMessage() {
		//Setup
		String expected = "Hello";		
		ByteArrayInputStream in = new ByteArrayInputStream("Hello\n".getBytes());
		System.setIn(in);
		Scanner mock = new Scanner(in);
			
		//Init class under test
		String actual = classUnderTest.promptUserWithMessage(mock, "message");
			
		//Assert
		assertEquals(expected,actual);
	}
	
	/*
	 * Test valid large input
	 */
	@Test
	final void testPromptUserWithMessageLargeString() {
		//Setup
		String expected = "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello"
				+ "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello"
				+ "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello"
				+ "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello";
		
		String toMockStream = "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello"
				+ "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello"
				+ "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello"
				+ "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello\n";
		
		ByteArrayInputStream in = new ByteArrayInputStream(toMockStream.getBytes());
		System.setIn(in);
		Scanner mock = new Scanner(in);
			
		//Init class under test
		String actual = classUnderTest.promptUserWithMessage(mock, "message");
			
		//Assert
		assertEquals(expected,actual);
	}

	/**
	 * Tests for displayApplicationStatus
	 */
	
	/*
	 * Valid Input no Additional
	 */
	@Test
	final void testDisplayApplicationStatusValidNoAdditional() {
		//Setup 
		Map<String,String> expectedMap = new HashMap<String,String>();
		expectedMap.put(schemaMembers.JOBID.toString(), "JID");
		expectedMap.put(schemaMembers.NAME.toString(), "name-wa");
		expectedMap.put(schemaMembers.JUSTIFICATION.toString(), "justify");
		expectedMap.put(schemaMembers.ID.toString(), "ID");
		expectedMap.put(schemaMembers.CODE.toString(), "code");
		
		String expected = "############################################\r\n" + 
				"#             Application Status           #\r\n" + 
				"############################################\r\n" + 
				"Application ID: ID\r\n" + 
				"Job ID: JID\r\n" + 
				"Name: name-wa\r\n" + 
				"Justification: justify\r\n" + 
				"Repo Link: code\r\n" + 
				"Additional Links: None \r\n" + 
				"############################################";
		
		//Call class under test
		classUnderTest.displayApplicationStatus(expectedMap);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-4);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	/*
	 * Valid Input with Additional
	 */
	@Test
	final void testDisplayApplicationStatusValidWithAdditional() {
		//Setup 
		Map<String,String> expectedMap = new HashMap<String,String>();
		expectedMap.put(schemaMembers.JOBID.toString(), "JID");
		expectedMap.put(schemaMembers.NAME.toString(), "name-wa");
		expectedMap.put(schemaMembers.JUSTIFICATION.toString(), "justify");
		expectedMap.put(schemaMembers.ID.toString(), "ID");
		expectedMap.put(schemaMembers.CODE.toString(), "code");
		expectedMap.put(schemaMembers.ADDITIONAL.toString(), "something.com");
		
		String expected = "############################################\r\n" + 
				"#             Application Status           #\r\n" + 
				"############################################\r\n" + 
				"Application ID: ID\r\n" + 
				"Job ID: JID\r\n" + 
				"Name: name-wa\r\n" + 
				"Justification: justify\r\n" + 
				"Repo Link: code\r\n" + 
				"Additional Links: something.com\r\n" + 
				"############################################";
		
		//Call class under test
		classUnderTest.displayApplicationStatus(expectedMap);
		
		//Get actual and remove last line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-4);
					
		//Assert
		assertEquals(expected, actual);
	}
	
	/*
	 * Invalid Input with Additional
	 */
	@Test
	final void testDisplayApplicationStatusInvalidWithAdditional() {
		//Setup 
		Map<String,String> expectedMap = new HashMap<String,String>();
		expectedMap.put(schemaMembers.JOBID.toString(), "JID");
		expectedMap.put(schemaMembers.NAME.toString(), "name-wa");
		expectedMap.put(schemaMembers.JUSTIFICATION.toString(), "justify");
		expectedMap.put(schemaMembers.ID.toString(), "ID");		
		expectedMap.put(schemaMembers.ADDITIONAL.toString(), "something.com");
		
		String expected = "ERROR: Necessary keys not found in collection object! Unable to print application status";
		
		//Call class under test
		classUnderTest.displayApplicationStatus(expectedMap);
		
		//Get actual and remove line endings
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-4);
				
		//Assert
		assertEquals(expected, actual);
	}
	
	/**
	 * tokenPrint tests
	 */
	
	/*
	 * Empty input
	 */
	
	@Test
	final void testTokenPrintEmptyString() {
		
		//Setup
		String expected = "";
		
		//Perform operation
		classUnderTest.tokenPrinter(10, "", " ");
		
		//Get actual
		String actual = contentStream.toString().substring(0, contentStream.toString().length());
		
		assertEquals(expected, actual);
	}
	

	/*
	 * Zero Width Input
	 */
	
	@Test
	final void testTokenPrintZeroWidth() {
		
		//Setup
		String expected = "Some Stuff To Print";
		
		//Perform operation
		classUnderTest.tokenPrinter(0, "Some Stuff To Print", " ");
		
		//Get actual and remove line ending
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
		
		assertEquals(expected, actual);
	}
	
	/*
	 * Empty Regex
	 */
	
	@Test
	final void testTokenPrintEmptyRegex() {
		
		//Setup
		String expected = "Some Stuff To Print";
		
		//Perform operation
		classUnderTest.tokenPrinter(50, "Some Stuff To Print", "");
		
		//Get actual and remove line ending
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
		
		assertEquals(expected, actual);
	}
	
	/*
	 * Single Token less than width
	 */
	
	@Test
	final void testTokenPrintSingleToken() {
		
		//Setup
		String expected = "Some";
		
		//Perform operation
		classUnderTest.tokenPrinter(50, "Some", " ");
		
		//Get actual and remove line ending
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
		
		assertEquals(expected, actual);
	}
	
	/*
	 * Single Token longer than width
	 */
	
	@Test
	final void testTokenPrintSingleTokenlonger() {
		
		//Setup
		String expected = "Something";
		
		//Perform operation
		classUnderTest.tokenPrinter(5, "Something", " ");
		
		//Get actual and remove line ending
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
		
		assertEquals(expected, actual);
	}
	
	/*
	 * Multi Token - some longer than width of 5 some shorter 
	 */
	
	@Test
	final void testTokenPrintMultiToken() {
		
		//Setup
		String expected = "Some\r\n" + 
				"other\r\n" + 
				"text\r\n" + 
				"to display\r\n" + 
				"to the\r\n" + 
				"screen";
		
		//Perform operation
		classUnderTest.tokenPrinter(5, "Some other text to display to the screen", " ");
		
		//Get actual and remove line ending
		String actual = contentStream.toString().substring(0, contentStream.toString().length()-2);
		
		assertEquals(expected, actual);
	}
}

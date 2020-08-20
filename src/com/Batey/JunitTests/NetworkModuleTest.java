/**
 * 
 */
package com.Batey.JunitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintStream;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Batey.Utilities.NetworkModule;

/**
 * Network Module Tests
 * @author Brian Batey
 *
 */

class NetworkModuleTest {
	NetworkModule classUnderTest;	
	PrintStream originalErrorStream = System.err;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		classUnderTest = new NetworkModule();
		//Sets error messages to not show during testing due to network errors being introduced purposefully
		System.setErr(new java.io.PrintStream(new java.io.OutputStream(){public void write(int i){}}));
	}
	
	@AfterEach
	void cleanUp() throws Exception{
		//Sets error message stream back
		System.setErr(originalErrorStream);
	}

	/**
	 * Tests for performGETRequest
	 */
	
	/*
	 * Valid Connection performGETRequest no parameters
	 */
	@Test
	final void testPerformGETRequestValid() {
		//Setup
		int expected = 200;
		String endpointURL = "https://dev.spidasoftware.com/apply/api";
		//Perform test
		int actual = classUnderTest.performGETRequest(endpointURL);
		//Assert
		assertTrue(expected == actual);
	}
	
	/*
	 * Valid Connection performGETRequest with parameters
	 */
	@Test
	final void testPerformGETRequestValidWithParams() {
		//Setup
		int expected = 200;
		String endpointURL = "https://dev.spidasoftware.com/apply/jobs";
		String param = "5f3ae8daead4fc0001be475c";
		//Perform test
		int actual = classUnderTest.performGETRequest(endpointURL, param);
		//Assert
		assertTrue(expected == actual);
	}
	
	/*
	 * invalid Connection performGETRequest no parameters
	 */
	@Test
	final void testPerformGETRequestInvalid() {
		//Setup
		int expected = 200;
		String endpointURL = "https://wonderland";
		//Perform test
		int actual = classUnderTest.performGETRequest(endpointURL);
		//Assert
		assertFalse(expected == actual);
	}
	
	/*
	 * Invalid Connection performGETRequest with parameters
	 */
	@Test
	final void testPerformGETRequestInvalidWithParams() {
		//Setup
		int expected = 200;
		String endpointURL = "https://wonderland";
		String param = "stuff";
		//Perform test
		int actual = classUnderTest.performGETRequest(endpointURL, param);
		//Assert
		assertFalse(expected == actual);
	}
	
	/**
	 * Test getResponseData
	 */
	
	/*
	 * getResponseData on valid request
	 */
	@Test
	final void testGetResponseData() {
		
		//Setup		
		String endpointURL = "https://dev.spidasoftware.com/apply/api";
		classUnderTest.clearResponseData();
		String expected = "{\"jobs\":{\"list\":\"GET @ /jobs\",\"get\":\"GET @ /jobs/:id\",\"create\":\"POST @ /jobs\",\"update\":\"PUT @ /jobs/:id\",\"delete\":\"DELETE @ /jobs/:id\",\"schema\":{\"id\":\"jobs\",\"$schema\""
				+ ":\"http://json-schema.org/draft-04/schema#\",\"description\":\"a short description of the available position\",\"type\":\"object\",\"properties\":{\"position\":{\"description\":\"the title of the job\","
				+ "\"type\":\"string\"},\"description\":{\"description\":\"a brief overview of the job\",\"type\":\"string\"},\"requirements\":{\"description\":\"specific additional things we are looking the candidate to show.\","
				+ "\"type\":\"array\"}},\"additionalProperties\":false,\"required\":[\"position\",\"description\"]}},\"applications\":{\"get\":\"GET @ /applications/:id\",\"create\":\"POST @ /applications\",\"update\":\"PUT"
				+ " @ /applications/:id\",\"delete\":\"DELETE @ /applications/:id\",\"schema\":{\"id\":\"application\",\"$schema\":\"http://json-schema.org/draft-04/schema#\",\"description\":\"an application object to complete and application\","
				+ "\"type\":\"object\",\"properties\":{\"name\":{\"description\":\"your name\",\"type\":\"string\"},\"jobId\":{\"description\":\"the id of the job you applying\",\"type\":\"string\"},\"justification\":{\"description\":"
				+ "\"why we should hire you, we are not looking for your resume, tell us your story and why that makes you good to talk to.\",\"type\":\"string\"},\"code\":{\"description\":\"a link to the code you used to submit this application "
				+ "(ie github, google code etc)\",\"type\":\"string\"},\"additionalLinks\":{\"description\":\"these are additional links you think we should see.\",\"type\":\"array\"}},\"additionalProperties\":false,\"required\":[\"name\",\"justification\",\"code\",\"jobId\"]}}}";
		
		//Perform test
		classUnderTest.performGETRequest(endpointURL);
		
		String actual = classUnderTest.getResponseData();
		
		assertEquals(expected,actual);
	}
	/*
	 * getResponse from Invalid source
	 */
	@Test
	final void testGetResponseDataInvalid() {
		
		//Setup		
		String endpointURL = "https://dev.spidasoftware.com/apply/theMoon";
		classUnderTest.clearResponseData();
		String expected = "";
		
		//Perform test
		classUnderTest.performGETRequest(endpointURL);
		
		String actual = classUnderTest.getResponseData();
		
		assertEquals(expected,actual);
	}
	
	/**
	 * clearResponseData
	 */
	@Test
	final void testClearResponseData() {
		
		//Setup		
		String endpointURL = "https://dev.spidasoftware.com/apply/api";
		classUnderTest.clearResponseData();
		String expected = "";
		
		//Perform valid GET request
		classUnderTest.performGETRequest(endpointURL);
		//Clear data
		classUnderTest.clearResponseData();
		
		String actual = classUnderTest.getResponseData();
		
		
		
		assertEquals(expected,actual);
	}
	
	/**
	 * Test perform POST request
	 */
	@Test
	final void testPerformPOSTRequest() {
		//Setup
		String endpointURL = "https://postman-echo.com/post";
		JSONObject obj = new JSONObject();
		obj.put("hands", "clap");
		
		int expected = 200;
		
		//Perform Test
		int actual = classUnderTest.performPOSTRequest(endpointURL, obj.toString());
			
		assertTrue(expected == actual);
	}
	
	/**
	 * Test perform POST request invalid URL
	 */
	@Test
	final void testPerformPOSTRequestInvalidURL() {
		//Setup
		String endpointURL = "https://postman-echo";
		JSONObject obj = new JSONObject();
		obj.put("hands", "clap");
		
		int expected = 200;
		
		//Perform Test
		int actual = classUnderTest.performPOSTRequest(endpointURL, obj.toString());
			
		assertFalse(expected == actual);
	}
	
	/**
	 * Test perform POST request response
	 */
	@Test
	final void testPerformPOSTRequestCheckResponse() {
		//Setup
		String endpointURL = "https://jsonplaceholder.typicode.com/posts";
		JSONObject obj = new JSONObject();
		obj.put("title", "hey");
		obj.put("body", "sure");
		obj.put("userId", 20);
		
		String expected = "{  \"title\": \"hey\",  \"body\": \"sure\",  \"userId\": 20,  \"id\": 101}";
				
		//Perform Test
		classUnderTest.performPOSTRequest(endpointURL, obj.toString());
		String actual = classUnderTest.getResponseData();
		
		//Assert			
		assertEquals(expected,actual);
	}
}

/**
 * 
 */
package com.Batey.JunitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Batey.Enums.schemaMembers;
import com.Batey.Utilities.JSONUtilities;

/**
 * JSON Utilities Tests
 * @author Brian Batey
 *
 */
class JSONUtilitiesTest {
	
	JSONUtilities classUnderTest; 

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		classUnderTest = new JSONUtilities();
	}

	
	/**
	 *	Tests for ParseJSONObjectFromJSONArray 
	 */
	
	/*
	 * Test method for ParseJSONObjectFromJSONArray() multi object valid
	 */
	@Test
	final void testParseJSONObjectFromArrayMultiObjectValid() {
		
		//Setup Variables
		String goodObj = "[{\"_id\":\"5f3ae8daead4fc0001be475c\",\"position\":\"Vice President of Furniture\",\"description\":\"Furnish the bike shed to company standards.\","
				+ "\"requirements\":[\"No Humor\",\"Long Meetings\",\"Branding\"]},{\"_id\":\"5f3ae987ead4fc0001be475d\","
				+ "\"position\":\"Software Development Manager\",\"description\":\"Pretend to know everything.\",\"requirements\":[\"Best the old Software Development Manager in physical combat\","
				+ "\"Black Belt in Master Ken's Ameri-Do-Te\",\"Jiu Jitsu Isn't Real, Just Stand Up\"]},{\"_id\":\"5f3aea08ead4fc0001be475e\",\"position\":\"Junior Developer\",\"description\":\"Write clean, well-commented, and well-tested code.\","
				+ "\"requirements\":[\"1337 skillz\",\"Humor\",\"Javascript\"]}]";
		
		//Expected Strings
		String exp1 = "{\"requirements\":[\"No Humor\",\"Long Meetings\",\"Branding\"],\"description\":\"Furnish the bike shed to company standards.\",\"_id\":\"5f3ae8daead4fc0001be475c\",\"position\":\"Vice President of Furniture\"}";
		String exp2= "{\"_id\":\"5f3ae987ead4fc0001be475d\",\"position\":\"Software Development Manager\",\"description\":\"Pretend to know everything.\",\"requirements\":[\"Best the old Software Development Manager in physical combat\",\"Black Belt in Master Ken's Ameri-Do-Te\",\"Jiu Jitsu Isn't Real, Just Stand Up\"]}";
		String exp3= "{\"_id\":\"5f3aea08ead4fc0001be475e\",\"position\":\"Junior Developer\",\"description\":\"Write clean, well-commented, and well-tested code.\",\"requirements\":[\"1337 skillz\",\"Humor\",\"Javascript\"]}";
		
		//Create JSON Objects
		List<JSONObject>expectedVal = new ArrayList<JSONObject>();
		expectedVal.add(new JSONObject(exp1));
		expectedVal.add(new JSONObject(exp2));
		expectedVal.add(new JSONObject(exp3));
		
		
		//Perform operation	on class under test
		List<JSONObject> actualVal = classUnderTest.parseJSONObjectFromJSONArray(goodObj);
		
		assertEquals(expectedVal.get(0).toString(), actualVal.get(0).toString());	
		assertEquals(expectedVal.get(1).toString(), actualVal.get(1).toString());
		assertEquals(expectedVal.get(2).toString(), actualVal.get(2).toString());	
	}
	
	/*
	 * Test method for ParseJSONObjectFromJSONArray() single object valid
	 */
	@Test
	final void testParseJSONObjectFromArraySingleObjectValid() {
		
		//Setup Variables
		String goodObj = "[{\"_id\":\"5f3ae8daead4fc0001be475c\",\"position\":\"Vice President of Furniture\",\"description\":\"Furnish the bike shed to company standards.\","
				+ "\"requirements\":[\"No Humor\",\"Long Meetings\",\"Branding\"]}]";
		
		//Expected Strings
		String exp1 = "{\"requirements\":[\"No Humor\",\"Long Meetings\",\"Branding\"],\"description\":\"Furnish the bike shed to company standards.\",\"_id\":\"5f3ae8daead4fc0001be475c\",\"position\":\"Vice President of Furniture\"}";
		
		//Create JSON Objects
		List<JSONObject>expectedVal = new ArrayList<JSONObject>();
		expectedVal.add(new JSONObject(exp1));		
		
		
		//Perform operation	on class under test
		List<JSONObject> actualVal = classUnderTest.parseJSONObjectFromJSONArray(goodObj);
		
		assertEquals(expectedVal.get(0).toString(), actualVal.get(0).toString());		
	}
	
	/*
	 * Test method for ParseJSONObjectFromArray() Empty String
	 */
	@Test
	final void testParseJSONObjectFromJSONArrayEmptyString() {
		
		//Setup Variables
		String empytObj = "";	
			
		List<JSONObject> obj = classUnderTest.parseJSONObjectFromJSONArray(empytObj);	
		
		//List should be empty
		assertTrue(obj.isEmpty());
	}
	
	/*
	 * Test method for ParseJSONObjectFromJSONArray() multi object, one invalid
	 */
	@Test
	final void testParseJSONObjectFromArrayMultiObjectOneInvalid() {
		
		//Setup Variables
		String badObj = "[{\"_id\":\"5f3ae8daead4fc0001be475c\",\"position\":\"Vice President of Furniture\",\"description\":\"Furnish the bike shed to company standards.\","
				+ "\"requirements\":[\"No Humor\",\"Long Meetings\",\"Branding\"]},{\"_id\":\"5f3ae987ead4fc0001be475d\","
				+ "\"position\":\"Software Development Manager\",\"description\":\"Pretend to know everything.\",\"requirements\":[\"Best the old Software Development Manager in physical combat\","
				+ "\"Black Belt in Master Ken's Ameri-Do-Te\",\"Jiu Jitsu Isn't Real, Just Stand Up\"},{\"_id\":\"5f3aea08ead4fc0001be475e\",\"position\":\"Junior Developer\",\"description\":\"Write clean, well-commented, and well-tested code.\","
				+ "\"requirements\":[\"1337 skillz\",\"Humor\",\"Javascript\"]}]";
		
		
		//Perform operation	on class under test
		List<JSONObject> actualVal = classUnderTest.parseJSONObjectFromJSONArray(badObj);
		
		//List should be empty on error
		assertTrue(actualVal.isEmpty());
	}
	
	/*
	 * Test method for ParseJSONObjectFromJSONArray() single object invalid
	 */
	@Test
	final void testParseJSONObjectFromArraySingleObjectInvalid() {
		
		//Setup Variables
		String badObj = "{\"_id\":\"5f3ae8daead4fc0001be475c\",\"position\":\"Vice President of Furniture\",\"description\":\"Furnish the bike shed to company standards.\","
				+ "\"requirements\":[\"No Humor\",\"Long Meetings\",\"Branding\"]}]";
		
		
		//Perform operation	on class under test
		List<JSONObject> actualVal = classUnderTest.parseJSONObjectFromJSONArray(badObj);
		
		//List should be empty on error
		assertTrue(actualVal.isEmpty());
	}
	
	/**
	 * Tests for verifyJobPostingSchema
	 */
	
	/*
	 * Valid schema
	 */
	@Test
	final void testVerifyJobPostingSchemaValid() {
		
		//Setup
		String goodObj = "{\"_id\":\"5f3aea08ead4fc0001be475e\",\"position\":\"Junior Developer\",\"description\":\"Write clean, well-commented, and well-tested code.\",\"requirements\":[\"1337 skillz\",\"Humor\",\"Javascript\"]}";
		
		Boolean expected = true;
		Boolean actual = classUnderTest.verifyJobPostingSchema(new JSONObject(goodObj));
		
		assertEquals(expected,actual);
	}
	
	/*
	 * Empty Entry
	 */
	@Test
	final void testVerifyJobPostingSchemaEmpty() {
		
		//Setup		
		Boolean expected = false;
		Boolean actual = classUnderTest.verifyJobPostingSchema(new JSONObject());
		
		assertEquals(expected,actual);
	}
	
	/*
	 * Invalid schema
	 */
	@Test
	final void testVerifyJobPostingSchemaInvalid() {
		
		//Setup
		String goodObj = "{\"_id\":\"5f3aea08ead4fc0001be475e\",\"description\":\"Write clean, well-commented, and well-tested code.\",\"requirements\":[\"1337 skillz\",\"Humor\",\"Javascript\"]}";
		
		Boolean expected = false;
		Boolean actual = classUnderTest.verifyJobPostingSchema(new JSONObject(goodObj));
		
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests for verifyAppPostingSchema
	 */
	
	
	/*
	 * Valid schema
	 */
	@Test
	final void testVerifyAppPostingSchemaValid() {
		
		//Setup
		String goodObj = "{\"jobId\":\"5f3ae987ead4fc0001be475d\",\"code\":\"test.com\",\"additionalLinks\":[\"testing.com\",\" testw.com\"],\"name\":\"Mock Obj\",\"justification\":\"Because its a mock object and im still awesome\",\"_id\":\"5f3da67eead4fc0001be4774\"}";
		
		Boolean expected = true;
		Boolean actual = classUnderTest.verifyApplicationSchema(new JSONObject(goodObj));
		
		assertEquals(expected,actual);
	}
	
	/*
	 * Empty Entry
	 */
	@Test
	final void testVerifyAppPostingSchemaEmpty() {
		
		//Setup		
		Boolean expected = false;
		Boolean actual = classUnderTest.verifyApplicationSchema(new JSONObject());
		
		assertEquals(expected,actual);
	}
	
	/*
	 * Invalid schema
	 */
	@Test
	final void testVerifyAppPostingSchemaInvalid() {
		
		//Setup
		String badObj = "{\"_id\":\"5f3aea08ead4fc0001be475e\",\"requirements\":[\"1337 skillz\",\"Humor\",\"Javascript\"]}";
		
		Boolean expected = false;
		Boolean actual = classUnderTest.verifyJobPostingSchema(new JSONObject(badObj));
		
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests for getArrayFromJSONObject
	 */
	
	@Test
	final void testGetArrayFromJSONObjectItemInArray() {
		
		//Setup
		String goodObj = "{\"_id\":\"5f3aea08ead4fc0001be475e\",\"position\":\"Junior Developer\",\"description\":\"Write clean, well-commented, and well-tested code.\",\"requirements\":[\"1337 skillz\",\"Humor\",\"Javascript\"]}";
		
		//Expected vals
		List<String> expectedObj = new ArrayList<String>();
		expectedObj.add("1337 skillz");
		expectedObj.add("Humor");
		expectedObj.add("Javascript");
		
		//Get actual vals
		List<String> actualObj = classUnderTest.getArrayFromJSONObject(new JSONObject(goodObj), "requirements");
		
		Collections.sort(expectedObj);
		Collections.sort(actualObj);
		
		Boolean actualVal = expectedObj.equals(actualObj);
		Boolean expectedVal = true;
		
		//Assert comparisons
		assertEquals(expectedVal, actualVal);
	}
	
	@Test
	final void testGetArrayFromJSONObjectEmptyArray() {
		
		//Setup
		String goodObj = "{\"_id\":\"5f3aea08ead4fc0001be475e\",\"position\":\"Junior Developer\",\"description\":\"Write clean, well-commented, and well-tested code.\",\"requirements\":[]}";
		
		//Expected vals
		List<String> expectedObj = new ArrayList<String>();	
		
		//Get actual vals
		List<String> actualObj = classUnderTest.getArrayFromJSONObject(new JSONObject(goodObj), "requirements");
		
		Collections.sort(expectedObj);
		Collections.sort(actualObj);
		
		Boolean actualVal = expectedObj.equals(actualObj);
		Boolean expectedVal = true;
		
		//Assert comparisons
		assertEquals(expectedVal, actualVal);
	}
	
	@Test
	final void testGetArrayFromJSONObjectNoArray() {
		
		//Setup
		String goodObj = "{\"_id\":\"5f3aea08ead4fc0001be475e\",\"position\":\"Junior Developer\",\"description\":\"Write clean, well-commented, and well-tested code.\"}";
		
		//Expected vals
		List<String> expectedObj = new ArrayList<String>();	
		
		//Get actual vals
		List<String> actualObj = classUnderTest.getArrayFromJSONObject(new JSONObject(goodObj), "requirements");
		
		Collections.sort(expectedObj);
		Collections.sort(actualObj);
		
		Boolean actualVal = expectedObj.equals(actualObj);
		Boolean expectedVal = true;
		
		//Assert comparisons
		assertEquals(expectedVal, actualVal);
	}
	
	/**
	 * Tests for createJSONObjectFromMap
	 */
	@Test
	final void testCreateJSONObjectFromMapOneEntry() {
		
		//Setup
		JSONObject expected = new JSONObject();
		Map<String,String> testMap = new HashMap<String,String>();
		testMap.put("Some", "stuff");
		expected.put("Some", "stuff");
		
		//Perform operation
		JSONObject actual = classUnderTest.createJSONObjectFromMap(testMap);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	final void testCreateJSONObjectFromMapMultiEntry() {
		
		//Setup
		JSONObject expected = new JSONObject();
		Map<String,String> testMap = new HashMap<String,String>();
		
		//Fill Map
		testMap.put("Some", "stuff");
		testMap.put("More", "detritus");
		
		//Fill JSON Object
		expected.put("Some", "stuff");
		expected.put("More", "detritus");
		
		//Perform operation
		JSONObject actual = classUnderTest.createJSONObjectFromMap(testMap);
		
		assertEquals(expected.toString(), actual.toString());
		
	}
	
	@Test
	final void testCreateJSONObjectFromMapEmptyMap() {
		//Setup
				JSONObject expected = new JSONObject();
				Map<String,String> testMap = new HashMap<String,String>();
						
				//Perform operation
				JSONObject actual = classUnderTest.createJSONObjectFromMap(testMap);
				
				assertEquals(expected.toString(), actual.toString());
	}
	
	/*
	 * Test with non-similiar inputs
	 */
	@Test
	final void testCreateJSONObjectFromMapEmptyExpectedFail() {
		//Setup
				JSONObject expected = new JSONObject();
				Map<String,String> testMap = new HashMap<String,String>();
				
				//Map put
				testMap.put("More", "detritus");
				
				//Json put
				expected.put("Some", "stuff");
						
				//Perform operation
				JSONObject actual = classUnderTest.createJSONObjectFromMap(testMap);
				
				assertNotEquals(expected.toString(), actual.toString());
	}
	
	/**
	 * Tests for generateApplicationMap
	 */
	
	@Test
	final void testGenerateApplicationMapValidObjNoAdditional() {
		//Setup
		Boolean expected = true;
		JSONObject obj = new JSONObject();
		
		obj.put(schemaMembers.JOBID.toString(), "values");
		obj.put(schemaMembers.ID.toString(), "values1");
		obj.put(schemaMembers.NAME.toString(), "values2");
		obj.put(schemaMembers.JUSTIFICATION.toString(), "values3");
		obj.put(schemaMembers.CODE.toString(), "values4");
		
		
		//Perform operation
		Map<String,String> actualMap = classUnderTest.generateApplicationMap(obj);
		
		
		
		Iterator<String> objKeys = obj.keys();
		while(objKeys.hasNext()) {
			String expectedKey = objKeys.next();
			String expectedVal = obj.getString(expectedKey);
			
			assertTrue(actualMap.containsKey(expectedKey));
			assertEquals(expectedVal, actualMap.get(expectedKey));
		}		
	}
	
	@Test
	final void testGenerateApplicationMapValidObjWithAdditional() {
		//Setup
		Boolean expected = true;
		String expectedVal;
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray("[values5]");
		
		obj.put(schemaMembers.JOBID.toString(), "values");
		obj.put(schemaMembers.ID.toString(), "values1");
		obj.put(schemaMembers.NAME.toString(), "values2");
		obj.put(schemaMembers.JUSTIFICATION.toString(), "values3");
		obj.put(schemaMembers.CODE.toString(), "values4");
		obj.put(schemaMembers.ADDITIONAL.toString(), arr);
		
		//Perform operation
		Map<String,String> actualMap = classUnderTest.generateApplicationMap(obj);
		
		//Create Iterator
		Iterator<String> objKeys = obj.keys();
		while(objKeys.hasNext()) {
			String expectedKey = objKeys.next();
			
			//Iterate through keys and pull out the array for testing
			if(!expectedKey.equals(schemaMembers.ADDITIONAL.toString())) {
				expectedVal = obj.getString(expectedKey);
			}else {
				JSONArray addArr = obj.optJSONArray(schemaMembers.ADDITIONAL.toString());
				expectedVal = addArr.toString();
			}
			
			//Perform assertions	
			assertTrue(actualMap.containsKey(expectedKey));
			if(actualMap.containsKey(expectedKey)) {
				assertEquals(expectedVal, actualMap.get(expectedKey));
			}			
		}		
	}
	
	@Test
	final void testGenerateApplicationMapInvalidObj() {
		
		//Setup
		JSONObject obj = new JSONObject();
		Boolean expected = true;
		
		obj.put("SOMEKEY", "somevalue");
		obj.put("SOMEOTHERKEY", "someothervalue");
		
		//Perform operation
		Map<String,String> actualMap = classUnderTest.generateApplicationMap(obj);
		
		//If object is invalid, empty map is returned.
		Boolean actual = actualMap.isEmpty();
		
		assertEquals(expected,actual);
	}
	
	@Test
	final void testGenerateApplicationMapEmptyObj() {
		//Setup
		JSONObject obj = new JSONObject();
		Boolean expected = true;
		
		//Perform operation
		Map<String,String> actualMap = classUnderTest.generateApplicationMap(obj);
		
		
		//If object is empty, empty map is returned.
		Boolean actual = actualMap.isEmpty();
		
		assertEquals(expected,actual);
	}
}

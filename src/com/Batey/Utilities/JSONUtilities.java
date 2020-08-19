/**
 * 
 */
package com.Batey.Utilities;

import com.Batey.Enums.schemaMembers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Brian Batey
 *
 */
public class JSONUtilities {
	
	/**
	 * Takes a String representation of a valid JSON Array that contains one or more JSON Objects and
	 * parses the JSON objects into an ArrayList for return. 
	 * 
	 * @throws
	 *  	- Throws JSONException with malformed JSON input.
	 * @param input
	 * 		- A well formed String representing a JSON Array containing one or more JSON objects
	 * @return
	 * 		- ArrayList<JSONObject> containing the JSON Objects parsed from the given input
	 */
	public List<JSONObject> parseJSONObjectFromArray(String input){
		//Create output list
		List<JSONObject> outputArr = new ArrayList<JSONObject>();
				
		try {
			//Create a JSON array object
			JSONArray objectArr = new JSONArray(input);
		
			//Add each JSON object in array to output array			
			for(int i = 0; i<objectArr.length(); i++) {
				outputArr.add(objectArr.optJSONObject(i));
			}
		}catch (org.json.JSONException e){			
			System.err.printf("Invalid JSON structure recieved: %s", e.getMessage());			
		}
		//Return result
		return outputArr;
	}
	
	/**
	 * Verifies the JSON schema of a given JSON Object for a Job Posting.
	 * Schema members required are : "position, description".
	 * Returns a boolean value.
	 * Schema was adapted from: https://dev.spidasoftware.com/apply/api 
	 * 
	 * @param obj
	 * 		- The JSON 
	 * @return
	 * 		- Boolean value of true if schema is valid, false otherwise.
	 */
	public Boolean verifyJobPostingSchema(JSONObject obj) {		
		//Setup output variable
		Boolean result = false;
		
		//Position and description are required by given schema		
		if(obj.has(schemaMembers.POSITION.toString())&& obj.has(schemaMembers.DESCRIPTION.toString())) {
			result = true;			
		}
		return result;
	}
	
	/**
	 * Verifies the JSON schema of a given JSON Object for an Application posting.
	 * Schema members required are : "jobId, justification, code, and name".
	 * Returns a boolean value.
	 * Schema was adapted from: https://dev.spidasoftware.com/apply/api 
	 * 
	 * @param obj
	 * 		- The JSON 
	 * @return
	 * 		- Boolean value of true if schema is valid, false otherwise.
	 */
	public Boolean verifyApplicationSchema(JSONObject obj) {		
		//Setup output variable
		Boolean result = false;
		
		//Name, justification, code and jobId are defined as necessary by schema		
		if(obj.has(schemaMembers.JOBID.toString()) && obj.has(schemaMembers.NAME.toString())
				&& obj.has(schemaMembers.CODE.toString()) 
				&& obj.has(schemaMembers.JUSTIFICATION.toString())) {
			result = true;			
		}
		return result;
	}
	
	/**
	 * Parses a JSON array from a JSON Object. Returns a List<String> for each element retrieved.
	 * @param obj
	 * 		- JSONObject containing an array to be parsed
	 * @param arrName
	 * 		- String value representing the attribute name of the array in the given JSONObject @obj
	 * @return
	 * 		- a List<String> containing all parsed values found in the array
	 */
	public List<String> getArrayFromJSONObject(JSONObject obj, String arrName) {
		
		//Create list for output return
		List<String> outputList = new ArrayList<String>();
		
		//Parse JSONObject for specified member
		try {
			JSONArray buffer = obj.getJSONArray(arrName);
			
			//Add each element of JSON array to output array
			for(int i=0; i < buffer.length(); i++) {
				outputList.add(buffer.getString(i));
			}
		}catch (org.json.JSONException e){			
			System.err.printf("Invalid JSON array structure received: %s", e.getMessage());			
		}
		//Return result
		return outputList;
	}
	
	/**
	 * Creates a JSON Object from a given Map. JSONObject key/values will reflect the key/value pairs found in the map with no nesting.
	 * @param userMap
	 * 		- a map containg key/value pairs to be transferred to a JSONObject
	 * @return
	 * 		- a JSONObject representing the values stored in the given map
	 */
	public JSONObject createJSONObjectFromMap(Map<String,String>userMap) {
		
		//Create JSON object and populate with map entries
		JSONObject temp = new JSONObject();
		for(Map.Entry<String,String> ele : userMap.entrySet()) {			
			temp.put(ele.getKey(), ele.getValue());
		}
		return temp;
	}
	
	/**
	 * Generates a map representing an application post based on the schema at: https://dev.spidasoftware.com/apply/api
	 * @param obj
	 * 		- a well formed JSONObject based on the given schema
	 * @returns
	 * 		- a map representing the given JSONObject
	 */
	public Map<String,String> generateApplicationMap(JSONObject obj) {
		Map<String,String> outputMap = new HashMap<String,String>();
		
		//Populate Map with attributes/values
		outputMap.put(schemaMembers.JOBID.toString(), obj.getString(schemaMembers.JOBID.toString()));	
		outputMap.put(schemaMembers.NAME.toString(), obj.getString(schemaMembers.NAME.toString()));
		outputMap.put(schemaMembers.JUSTIFICATION.toString(), obj.getString(schemaMembers.JUSTIFICATION.toString()));
		outputMap.put(schemaMembers.CODE.toString(), obj.getString(schemaMembers.CODE.toString()));	
		
		return outputMap;
	}
}

/**
 * 
 */
package com.Batey.Utilities;

import com.Batey.Enums.schemaMembers;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Brian Batey
 *
 */
public class JSONUtilities {
	
	/**
	 * 
	 * @param input
	 * @return
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
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean verifyObjectSchema(JSONObject obj) {
		
		//Setup output variable
		Boolean result = false;
		
		//Position and description are required by given schema		
		if(obj.has(schemaMembers.ID.toString()) && obj.has(schemaMembers.POSITION.toString())
				&& obj.has(schemaMembers.DESCRIPTION.toString())) {
			result = true;			
		}
		return result;
	}
	
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
			System.err.printf("Invalid JSON array structure recieved: %s", e.getMessage());			
		}
		//Return result
		return outputList;
	}

}

package com.Batey.Utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;


/**
 * Network module containing utilites for GET with and without parameters, POST with parameters and clearing/retrieving the 
 * response data.
 * @author Brian Batey
 *
 */
public class NetworkModule {
	
	// Connection/Read timeout value
	private final int TIMEOUT = 5000;
	
	//Class object Variables
	private String data = "";

	/**
	 * Takes a buffered input stream and converts it to a String.
	 * @param readerIn
	 * 		- the buffered input stream
	 * @return
	 * 		- a string value
	 */
	private static String responseToString(BufferedReader readerIn){
	    StringBuilder sb = new StringBuilder();
	    String currentLine;
	    try {
			while ((currentLine = readerIn.readLine()) != null) {
			  sb.append(currentLine);
			}
		} catch (IOException e) {
			System.err.printf ("Failed while reading from input stream @responseToString: %s", e.getMessage());	
			e.printStackTrace();
		}
	    return sb.toString();
	}	
	
	/**
	 * Returns the stored response data for @this. Response data is updated after GET/POST operations and clear operations.
	 * @return
	 */
	public String getResponseData() {
		return this.data;
	}
		
	/**
	 * Perform a GET request to the specified URL with no parameters.
	 * @param endpointURL
	 * 		- a string representing the desired endpoint to request from
	 * @return
	 * 		- an integer representing the responseCode received from the endpoint
	 * @updates
	 * 		- updates @this.data with the body data received from the GET request
	 */
	public int performGETRequest(String endpointURL){
		
		//Setup Variables
		int responseCode = -1;	
		
		//Cleanup before attempt
		clearResponseData();
		
		try {
			//Setup Endpoint Connection
			URL url = new URL(endpointURL);
			HttpURLConnection endpointConnection = (HttpURLConnection) url.openConnection();
			
			//Set timeout to connect
			endpointConnection.setConnectTimeout(TIMEOUT);
			
			//Set property to GET
			endpointConnection.setRequestMethod("GET");
			
			//Populate Response Variable
			responseCode = endpointConnection.getResponseCode();
			
			//Set timeout to read
			endpointConnection.setReadTimeout(TIMEOUT);
			
			//Create data streams 
			BufferedReader dataReader = new BufferedReader(new InputStreamReader(endpointConnection.getInputStream()));			
			
			//Convert data stream to string and store in class object
			this.data = responseToString(dataReader);
			
			//Cleanup
			dataReader.close();
			endpointConnection.disconnect();
			
		} catch (MalformedURLException e) {
			System.err.printf("Connection failed, possible invalid URL at %s @performGETRequest", e.getMessage());	
			System.out.println();
		} catch (IOException e) {
			System.err.printf ("Failed while reading bytes from Input Stream @performGETRequest: %s", e.getMessage());			
			e.printStackTrace();			
		}
		
		//Return http connection response code
		return responseCode;
	}
	
	/**
	 * Perform a GET request to the specified URL with given parameters.
	 * @param endpointURL
	 * 		- a string representing the desired endpoint to request from
	 * @return
	 * 		- an integer representing the responseCode received from the endpoint
	 * @updates
	 * 		- updates @this.data with the body data received from the GET request
	 */
	public int performGETRequest(String endpointURL, String param){
		
		//Setup Variables
		int responseCode = -1;
		
		//Cleanup before attempt
		clearResponseData();
		
		try {
			//Setup Endpoint Connection
			URL url = new URL(endpointURL + "/" + param);
			HttpURLConnection endpointConnection = (HttpURLConnection) url.openConnection();
			
			//Set timeout to connect
			endpointConnection.setConnectTimeout(TIMEOUT);
			
			//Set property to GET
			endpointConnection.setRequestMethod("GET");		
			
			//Populate Response Variable
			responseCode = endpointConnection.getResponseCode();
			
			//Set timeout to read
			endpointConnection.setReadTimeout(TIMEOUT);
			
			//Create data streams 
			BufferedReader dataReader = new BufferedReader(new InputStreamReader(endpointConnection.getInputStream()));	
			
			//Get response						
			this.data = responseToString(dataReader);
			
			//Cleanup
			dataReader.close();
			endpointConnection.disconnect();
			
		} catch (MalformedURLException e) {
			System.err.printf("Connection failed, possible invalid URL at %s @performGETRequest", e.getMessage());	
			e.printStackTrace();
		} catch (IOException e) {
			System.err.printf ("Failed while reading bytes from Input Stream @performGETRequest: %s", e.getMessage());			
			e.printStackTrace();			
		}
		
		//Return http connection response code
		return responseCode;
	}
	
	/**
	 * Perform a POST request to the specified URL using the given data and JSON encoding.
	 * 
	 * @param endpointURL
	 * 		- a string representing the desired endpoint to request from
	 * @param jsonData
	 * 		- a string representing a JSON object to be POSTed to the endpoint
	 * @return
	 * 		- an integer representing the responseCode received from the endpoint
	 * @updates
	 * 		- updates @this.data with the response data received from the POST request
	 */
	public int performPOSTRequest(String endpointURL, String jsonData){
		
		//Setup Variables
		int responseCode = -1;
		
		//Cleanup before attempt
		clearResponseData();
		
		try {
			//Setup Endpoint Connection
			URL url = new URL(endpointURL);
			HttpURLConnection endpointConnection = (HttpURLConnection) url.openConnection();
			
			//Set timeout to connect
			endpointConnection.setConnectTimeout(TIMEOUT);
			
			//Set property to POST
			endpointConnection.setRequestMethod("POST");
			
			//Set header
			endpointConnection.setRequestProperty("Content-Type", "application/json");
			
			//Setup connection for output
			endpointConnection.setDoOutput(true);
						
			//Create output stream and send data
			DataOutputStream dataOut = new DataOutputStream(endpointConnection.getOutputStream());
			dataOut.writeBytes(jsonData);
			
			//Cleanup
			dataOut.close();
			try {
				//Set timeout to read
				endpointConnection.setReadTimeout(TIMEOUT);
				
				//Get Response			
				BufferedReader dataReader = new BufferedReader(new InputStreamReader(endpointConnection.getInputStream()));			
			
				//Convert data stream to string and store in class object
				this.data = responseToString(dataReader);
				
				//Close stream
				dataReader.close();
			} catch (IOException e) {
				System.err.printf ("Failed while reading from Input Stream @performPOSTRequest: %s", e.getMessage());
				System.out.println();
			}
			//Populate Response Variable
			responseCode = endpointConnection.getResponseCode();
			
			//Cleanup						
			endpointConnection.disconnect();
			
		} catch (MalformedURLException e) {
			System.err.printf("Connection failed, possible invalid URL at %s @performPOSTRequest", e.getMessage());
			System.out.println();
		} catch (IOException e) {
			System.err.printf ("Failed while reading bytes to Output Stream @performPOSTRequest: %s", e.getMessage());
			System.out.println();
		} 
		//Return http connection response code
		return responseCode;
	}
	
	/**
	 * Clears the stored response data from the calling NetworkModule object.
	 * 
	 * @clears
	 * 		this.data
	 */
	public void clearResponseData() {
		this.data = "";
	}
}

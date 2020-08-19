package com.Batey.Utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class NetworkModule {
	
	//Class object Variables
	private String data = "";

	/**
	 * 
	 * @param readerIn
	 * @return
	 */
	private static String responseToString(BufferedReader readerIn){
	    StringBuilder sb = new StringBuilder();
	    String currentLine;
	    try {
			while ((currentLine = readerIn.readLine()) != null) {
			  sb.append(currentLine);
			}
		} catch (IOException e) {
			System.err.printf ("Failed while reading from input stream: %s", e.getMessage());	
			e.printStackTrace();
		}
	    return sb.toString();
	}	
	
	/**
	 * 
	 * @return
	 */
	public String getResponseData() {
		return this.data;
	}
		
	/**
	 * 
	 * @param endpointURL
	 * @return
	 */
	public int performGETRequest(String endpointURL){
		
		//Setup Variables
		int responseCode = -1;		
		
		try {
			//Setup Endpoint Connection
			URL url = new URL(endpointURL);
			HttpURLConnection endpointConnection = (HttpURLConnection) url.openConnection();
			
			//Set property to GET
			endpointConnection.setRequestMethod("GET");
			
			//Populate Response Variable
			responseCode = endpointConnection.getResponseCode();
			
			//Create data streams 
			BufferedReader dataReader = new BufferedReader(new InputStreamReader(endpointConnection.getInputStream()));			
			
			//Convert data stream to string and store in class object
			this.data = responseToString(dataReader);
			
			//Cleanup
			dataReader.close();
			endpointConnection.disconnect();
			
		} catch (MalformedURLException e) {
			System.err.printf("Connection failed, possible invalid URL at %s", e.getMessage());	
			e.printStackTrace();
		} catch (IOException e) {
			System.err.printf ("Failed while reading bytes from Input Stream: %s", e.getMessage());			
			e.printStackTrace();			
		}
		
		//Return http connection response code
		return responseCode;
	}
	
	/**
	 * 
	 * @param endpointURL
	 * @return
	 */
	public int performGETRequest(String endpointURL, String param){
		
		//Setup Variables
		int responseCode = -1;		
		
		try {
			//Setup Endpoint Connection
			URL url = new URL(endpointURL + "/" + param);
			HttpURLConnection endpointConnection = (HttpURLConnection) url.openConnection();
			
			//Set property to GET
			endpointConnection.setRequestMethod("GET");		
			
			//Populate Response Variable
			responseCode = endpointConnection.getResponseCode();
			
			//Create data streams 
			BufferedReader dataReader = new BufferedReader(new InputStreamReader(endpointConnection.getInputStream()));	
			
			//Get response						
			this.data = responseToString(dataReader);
			
			//Cleanup
			dataReader.close();
			endpointConnection.disconnect();
			
		} catch (MalformedURLException e) {
			System.err.printf("Connection failed, possible invalid URL at %s", e.getMessage());	
			e.printStackTrace();
		} catch (IOException e) {
			System.err.printf ("Failed while reading bytes from Input Stream: %s", e.getMessage());			
			e.printStackTrace();			
		}
		
		//Return http connection response code
		return responseCode;
	}
	
	/**
	 * 
	 * @param endpointURL
	 * @return
	 */
	public int performPOSTRequest(String endpointURL, String jsonData){
		
		//Setup Variables
		int responseCode = -1;		
		
		try {
			//Setup Endpoint Connection
			URL url = new URL(endpointURL);
			HttpURLConnection endpointConnection = (HttpURLConnection) url.openConnection();
			
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
			
			//TODO: ADD ANOTHER TRY/CATCH HERE
			//Get Response			
			BufferedReader dataReader = new BufferedReader(new InputStreamReader(endpointConnection.getInputStream()));			
			
			//Convert data stream to string and store in class object
			this.data = responseToString(dataReader);
			
			//Populate Response Variable
			responseCode = endpointConnection.getResponseCode();
			
			//Cleanup
			dataReader.close();			
			endpointConnection.disconnect();
			
		} catch (MalformedURLException e) {
			System.err.printf("Connection failed, possible invalid URL at %s", e.getMessage());	
			e.printStackTrace();
		} catch (IOException e) {
			System.err.printf ("Failed while reading bytes from Input Stream: %s", e.getMessage());			
			e.printStackTrace();			
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

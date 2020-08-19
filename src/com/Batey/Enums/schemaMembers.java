package com.Batey.Enums;

/**
 * An Enumeration set containing schema attribute values used in verifying and creating JSON objects 
 * associated with the SPIDA API. Schema attributes were taken from: https://dev.spidasoftware.com/apply/api. 
 * @author Brian Batey
 *
 */
public enum schemaMembers {
	ID("_id"), POSITION("position"), REQUIREMENTS("requirements"), DESCRIPTION("description"),
	NAME("name"), JOBID("jobId"), JUSTIFICATION("justification"), CODE("code"), ADDITIONAL("additionalLinks"); 
	
	private String type;
	
	private schemaMembers(String name) {
		this.type = name;
	}
	
	@Override
	/**
	 * Returns the string representation of the Enumeration value.
	 */
	public String toString() {
		return type;
	}
}

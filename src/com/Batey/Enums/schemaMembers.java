package com.Batey.Enums;

/**
 * 
 * @author Brian Batey
 *
 */
public enum schemaMembers {
	ID("_id"), POSITION("position"), REQUIREMENTS("requirements"), DESCRIPTION("description"),
	NAME("name"), JOBID("jobId"), JUSTIFICATION("justification"), CODE("code"); 
	
	private String type;
	
	private schemaMembers(String name) {
		this.type = name;
	}
	
	@Override
	public String toString() {
		return type;
	}
}

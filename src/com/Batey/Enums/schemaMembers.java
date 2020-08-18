package com.Batey.Enums;

public enum schemaMembers {
	ID("_id"), POSITION("position"), REQUIREMENTS("requirements"), DESCRIPTION("description"); 
	
	private String type;
	
	private schemaMembers(String name) {
		this.type = name;
	}
	
	@Override
	public String toString() {
		return type;
	}
}

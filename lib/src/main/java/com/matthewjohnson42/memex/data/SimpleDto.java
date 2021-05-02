package com.matthewjohnson42.memex.data;

public class SimpleDto {
	private final String simpleClassMemberVariable;
	public SimpleDto() { 
		this.simpleClassMemberVariable = "aSimpleValueForSimpleClassMemberVariable";
	}
	public String getSimpleClassMemberVariable() {
		return this.simpleClassMemberVariable;	
	}
}

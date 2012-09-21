package com.trsvax.jacquard.environment;

public interface GridEnvironment {
	
	public Boolean getLean();
	public GridEnvironment withLean(Boolean lean);
	
	
	Boolean includeCellDatatypes();

}

package com.trsvax.jacquard.environment;


public interface PagedLoopEnviroment<T> {
	
	public Iterable<T> getSource();
	
	public Iterable<T> getPage();
	
	public Integer getCurrentPage();
	
	public String getParameterName();
	
	public Integer getTotalPages();

}

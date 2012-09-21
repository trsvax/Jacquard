package com.trsvax.jacquard.environment;

public class BeanEditDefaults implements BeanEditEnvironment {
	
	private final String order;
	
	public BeanEditDefaults(String order) {
		this.order = order;
	}
	
	public String getOrder() {
		return order;
	}

}

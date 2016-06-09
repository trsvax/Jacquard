package com.trsvax.jacquard.services;

public interface ObjectSecurityService<T> {
	
	public boolean hasAccess(T o);

}

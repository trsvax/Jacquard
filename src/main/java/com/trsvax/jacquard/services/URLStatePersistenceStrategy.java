package com.trsvax.jacquard.services;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.PersistentFieldStrategy;

public interface  URLStatePersistenceStrategy extends PersistentFieldStrategy {
	
	public void updatePage(Link link);
	public void updateEvent(Link link);
}

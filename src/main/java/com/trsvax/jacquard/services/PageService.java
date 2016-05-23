package com.trsvax.jacquard.services;

import java.util.Collection;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Request;

import com.trsvax.jacquard.Page;

public interface PageService {
	
	public Page newPage(Request request);
	
	public Collection<Page> getPages();

	public Page getPage(String pageSequence);
	
	public StreamResponse runJob(Runnable job);

}

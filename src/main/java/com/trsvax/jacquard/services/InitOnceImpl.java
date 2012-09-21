package com.trsvax.jacquard.services;

import java.util.HashSet;
import java.util.Set;

import org.apache.tapestry5.services.Request;


public class InitOnceImpl implements InitOnce {
	private final String key = "com.trsvax.init";
	private final Request request;
	
	public InitOnceImpl(Request request) {
		this.request = request;
	}
	
	public void addScript(String script) {
		getSet().add(script);
	}

	public Set<String> getScripts() {
		return getSet();
	}
	
	
	@SuppressWarnings("unchecked")
	Set<String> getSet() {
		if ( request.getAttribute(key) == null ) {
			Set<String> scripts = new HashSet<String>();
			request.setAttribute(key, scripts);
		}
		return (Set<String>) request.getAttribute(key);
	}
}

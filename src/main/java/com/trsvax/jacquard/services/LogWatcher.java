package com.trsvax.jacquard.services;

import java.util.List;

public interface LogWatcher {
	
	public void start(String name);
	
	public List<String> logs();

}

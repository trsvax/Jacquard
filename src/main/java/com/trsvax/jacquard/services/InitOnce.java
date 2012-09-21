package com.trsvax.jacquard.services;

import java.util.Set;

public interface InitOnce {	
	public void addScript(String script);
	public Set<String> getScripts();
}

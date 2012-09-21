package com.trsvax.jacquard.components;

import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;

import com.trsvax.jacquard.environment.GridEnvironment;
import com.trsvax.jacquard.environment.GridEnvironmentImpl;


public class Multi {
	
	@Inject
	private Environment environment;
	
	@SetupRender
	void setupRender() {
		environment.push(GridEnvironment.class, new GridEnvironmentImpl());
	}
	
	@CleanupRender
	void cleanupRender() {
		environment.pop(GridEnvironment.class);
	}
	

}

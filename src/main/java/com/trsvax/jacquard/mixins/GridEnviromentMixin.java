package com.trsvax.jacquard.mixins;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.slf4j.Logger;

import com.trsvax.jacquard.environment.GridEnvironment;
import com.trsvax.jacquard.environment.GridEnvironmentImpl;




public class GridEnviromentMixin {
	@BindParameter
	private Boolean lean;
	
	@Inject
	private Environment environment;
	
	@Inject
	private Logger logger;
	
	@Inject
	private ComponentResources resources;
	
	@SetupRender
	void setupRender() {
		environment.push(GridEnvironment.class, new GridEnvironmentImpl().withLean(lean));
	}
	
	@CleanupRender
	void cleanupRender() {
		environment.pop(GridEnvironment.class);
	}

}

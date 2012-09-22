package com.trsvax.jacquard.mixins;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;

import com.trsvax.jacquard.environment.PersistEnvironment;
import com.trsvax.jacquard.environment.PersistEnvironmentImpl;

public class Persist {
	
	@Parameter(defaultPrefix="literal")
	private String include;
	
	@Inject
	private Environment environment;
	
	@SetupRender
	void setupRender() {
		environment.push(PersistEnvironment.class, new PersistEnvironmentImpl(include));
	}

	@AfterRender
	void afterRender() {
		environment.pop(PersistEnvironment.class);
	}
}

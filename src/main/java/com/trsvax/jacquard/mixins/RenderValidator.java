package com.trsvax.jacquard.mixins;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.AfterRenderBody;
import org.apache.tapestry5.annotations.AfterRenderTemplate;
import org.apache.tapestry5.annotations.BeforeRenderTemplate;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

public class RenderValidator {
	
	final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	
	@Inject
	ComponentResources componentResources;

	@SetupRender
	void setupRender() {
		factory.getValidator().validate(componentResources.getContainer(),SetupRender.class);
	}
	
	@BeginRender
	void beginRender() {
		factory.getValidator().validate(componentResources.getContainer(),BeginRender.class);

	}
	
	@BeforeRenderTemplate
	void beforeRenderTemplate() {
		factory.getValidator().validate(componentResources.getContainer(),BeforeRenderTemplate.class);

	}
	
	@AfterRenderBody
	void afterRenderBody() {
		factory.getValidator().validate(componentResources.getContainer(),AfterRenderBody.class);

	}
	
	@AfterRenderTemplate
	void afterRenderTemplate() {
		factory.getValidator().validate(componentResources.getContainer(),AfterRenderTemplate.class);

	}
	
	
	@AfterRender
	void afterRender() {
		factory.getValidator().validate(componentResources.getContainer(),AfterRender.class);
	}
	
	@CleanupRender
	void cleanupRender() {
		factory.getValidator().validate(componentResources.getContainer(),CleanupRender.class);
	}

}

package com.trsvax.jacquard.mixins;

import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.AfterRenderBody;
import org.apache.tapestry5.annotations.AfterRenderTemplate;
import org.apache.tapestry5.annotations.BeforeRenderTemplate;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beanvalidator.BeanValidatorSource;
import org.apache.tapestry5.ioc.annotations.Inject;

public class RenderValidator {
	
	
	@Inject
	BeanValidatorSource beanValidatorSource;
	
	@InjectContainer
	Object container;
	
	@SuppressWarnings("rawtypes")
	Set constraintViolations;
	
	@SuppressWarnings({"unchecked" })
	private void validate(Class<?> group) {
		constraintViolations = beanValidatorSource.getValidatorFactory().getValidator().validate(container,group);
		if ( constraintViolations.size() > 0 ) {
			throw new ConstraintViolationException(String.format("Validate %s Event Failed",group.getSimpleName()),constraintViolations);
		}
	}

	@SetupRender
	void setupRender() {
		validate(SetupRender.class);
	}
	
	@BeginRender
	void beginRender() {
		validate(BeginRender.class);
	}
	
	@BeforeRenderTemplate
	void beforeRenderTemplate() {
		validate(BeforeRenderTemplate.class);
	}
	
	@AfterRenderBody
	void afterRenderBody() {
		validate(AfterRenderBody.class);

	}
	
	@AfterRenderTemplate
	void afterRenderTemplate() {
		validate(AfterRenderTemplate.class);

	}
		
	@AfterRender
	void afterRender() {
		validate(AfterRender.class);
	}
	
	@CleanupRender
	void cleanupRender() {
		validate(CleanupRender.class);
	}

}

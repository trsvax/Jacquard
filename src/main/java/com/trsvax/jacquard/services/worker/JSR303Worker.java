package com.trsvax.jacquard.services.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.runtime.ComponentEvent;
import org.apache.tapestry5.services.ComponentEventHandler;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;
import org.slf4j.Logger;

import com.trsvax.jacquard.anotations.ValidateBeforeSuccessEvent;
import com.trsvax.jacquard.mixins.RenderValidator;

public class JSR303Worker implements ComponentClassTransformWorker2 {
	
	private final Logger logger;
	
	public JSR303Worker(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
			
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		final List<String> properties = new ArrayList<String>();
		
		for ( PlasticField field : plasticClass.getFieldsWithAnnotation(ValidateBeforeSuccessEvent.class)) {
			properties.add(field.getName());
		}
		
		
		if ( model.isPage() ) {
			logger.info("name {}",model.getComponentClassName());
			//plasticClass.introduceInterface(IsPage.class);
			model.addMixinClassName(RenderValidator.class.getName(), "after:*");		
						
			support.addEventHandler(EventConstants.ACTIVATE, 0, "Validate Activation Event", new ComponentEventHandler() {
				
				@Override
				public void handleEvent(Component instance, ComponentEvent event) {
						
					Set constraintViolations = factory.getValidator().validate(instance, 
							ActivationRequestParameter.class, PageActivationContext.class);
					//logger.info("activate {} {}",instance,constraintViolations);
					if ( constraintViolations.size() > 0 ) {
						throw new ConstraintViolationException("Validate Activation Event Failed",constraintViolations);
					}
				}
			});
			
		
		
		
		support.addEventHandler(EventConstants.SUCCESS, 0, "Form Validate", new ComponentEventHandler() {
			
			@Override
			public void handleEvent(Component instance, ComponentEvent event) {
				
					
					Set constraintViolations = factory.getValidator().validate(instance,ValidateBeforeSuccessEvent.class);
					//logger.info("validate {} {}",instance,constraintViolations);

					
					for ( String property : properties ) {
						//logger.info("validate property {} {}",instance,property);
						constraintViolations.addAll(factory.getValidator().validateProperty(instance,property,ValidateBeforeSuccessEvent.class));
					}
					
					if ( constraintViolations.size() > 0 ) {
						throw new ConstraintViolationException("Form Validate",constraintViolations);
					}
				
			}
		});
		
		}
		
		
	}

}

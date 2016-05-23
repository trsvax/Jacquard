package com.trsvax.jacquard.services.worker;

import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.runtime.ComponentEvent;
import org.apache.tapestry5.services.ComponentEventHandler;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;
import org.slf4j.Logger;

public class JSR303PassivateWorker implements ComponentClassTransformWorker2 {
	
	private final Logger logger;
	
	public JSR303PassivateWorker(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
			
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		
		if ( model.isPage() ) {
			logger.info("name {}",model.getComponentClassName());
			
			support.addEventHandler(EventConstants.PASSIVATE, 0, "Validate Passivate Event", new ComponentEventHandler() {
				
				@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
				public void handleEvent(Component instance, ComponentEvent event) {					
					Set constraintViolations = factory.getValidator().validate(instance, 
							ActivationRequestParameter.class, PageActivationContext.class);
					logger.info("passivate {} {}",instance,constraintViolations);
					if ( constraintViolations.size() > 0 ) {
						throw new ConstraintViolationException("Validate Passivate Event Failed",constraintViolations);
					}				
				}
			});
		}
		
		/*
		support.addEventHandler(EventConstants.VALIDATE, 0, "Form Validate"
				+ "", new ComponentEventHandler() {
			
			@Override
			public void handleEvent(Component instance, ComponentEvent event) {
				for ( String propertyName : propertyNames ) {
					

					Validator validator = factory.getValidator();
					Set constraintViolations = validator.validateProperty(instance, propertyName);
					logger.info("handle {}",constraintViolations);
					if ( constraintViolations.size() > 0 ) {
						throw new ConstraintViolationException("Bad Context",constraintViolations);
					}
				}
			}
		});
		*/
		
		
		
	}

}

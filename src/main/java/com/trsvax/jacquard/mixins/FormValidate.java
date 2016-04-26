package com.trsvax.jacquard.mixins;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beanvalidator.BeanValidatorSource;
import org.apache.tapestry5.beanvalidator.ClientConstraintDescriptor;
import org.apache.tapestry5.beanvalidator.ClientConstraintDescriptorSource;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.slf4j.Logger;


public class FormValidate {
	
	@BindParameter
	Object object;
	
	@BindParameter
	BeanModel<?> model;
	
	@InjectContainer
	BeanEditForm beanEditForm;
	
	@Inject
	ClientConstraintDescriptorSource clientConstraintDescriptorSource;
	
	@Inject
	BeanValidatorSource beanValidatorSource;
	
	@Inject
	ValidatorFactory validatorFactory;
	
	@Inject
	private Logger logger;
	
	@AfterRender
	void beginRender(MarkupWriter writer) {
		Class<?> beanType = model.getBeanType();
		
		if ( beanType == null ) {
			return;
		}
		
		final Validator validator = validatorFactory.getValidator();
		BeanDescriptor beanDescriptor = validator.getConstraintsForClass(beanType);
		
		for ( ConstraintDescriptor<?> d : beanDescriptor.getConstraintDescriptors()) {
			logger.info("ConstraintDescriptor {}",d.getAttributes());
			
			
		}	
		
		Annotation[] annotations = beanType.getAnnotations();
		if ( annotations == null ) {
			return;
		}
		for ( Annotation a : beanType.getAnnotations() ) {
			logger.info("annotaion {}",a);
			ClientConstraintDescriptor clientConstraintDescriptor = clientConstraintDescriptorSource.getConstraintDescriptor(a.annotationType());
						
			if ( clientConstraintDescriptor != null ) {
				Map<String, Object> attributes = CollectionFactory.newMap();
	
	            for (String attribute : clientConstraintDescriptor.getAttributes())
	            {
	            		logger.info("attribute {}",attribute);
	            		 	            		
	            }
	            clientConstraintDescriptor.applyClientValidation(writer, "FindMe", attributes);

			}
		}
	}
	
	@OnEvent(EventConstants.VALIDATE)
	boolean onValidate() {
		
		Set<ConstraintViolation<Object>> constraintViolations = beanValidatorSource.getValidatorFactory().getValidator().validate(object);
		for ( ConstraintViolation<Object> violation : constraintViolations ) {
			beanEditForm.recordError(violation.getMessage());
		}
		return false;
	}

}

package com.trsvax.jacquard.mixins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.MessageInterpolator.Context;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BindParameter;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beanvalidator.BeanValidatorSource;
import org.apache.tapestry5.beanvalidator.ClientConstraintDescriptor;
import org.apache.tapestry5.beanvalidator.ClientConstraintDescriptorSource;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

@MixinAfter
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
	
		
	@AfterRender
	void afterRender(MarkupWriter writer) {
		Class<?> beanType = model.getBeanType();
		
		if ( beanType == null ) {
			return;
		}
				 
		BeanDescriptor beanDescriptor = beanValidatorSource.getValidatorFactory().getValidator().getConstraintsForClass(beanType);
		Set<ConstraintDescriptor<?>> constraintDescriptors = beanDescriptor.getConstraintDescriptors();

				
		if ( constraintDescriptors == null || constraintDescriptors.size() == 0 ) {
			return;
		}
		
		Map<ClientConstraintDescriptor,List<ConstraintDescriptor<?>>> contraints = new HashMap<>();


		for ( ConstraintDescriptor<?>  constraintDescriptor: constraintDescriptors ) {
			ClientConstraintDescriptor clientConstraintDescriptor = clientConstraintDescriptorSource.getConstraintDescriptor(constraintDescriptor.getAnnotation().annotationType());

			if ( clientConstraintDescriptor != null ) {
				if ( ! contraints.containsKey(clientConstraintDescriptor)) {
					contraints.put(clientConstraintDescriptor, new ArrayList<>());
				}
				contraints.get(clientConstraintDescriptor).add(constraintDescriptor);				
			}
		}
		if ( ! contraints.isEmpty() ) {
			for ( Entry<ClientConstraintDescriptor,List<ConstraintDescriptor<?>>> e : contraints.entrySet() ) {
				e.getKey().applyClientValidation(writer, null, attributes(e.getKey(),e.getValue()));
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
	
	
	Map<String, Object> attributes(ClientConstraintDescriptor clientConstraintDescriptor, List<ConstraintDescriptor<?>> list) {
		Map<String, Object> attributes = new HashMap<>();
		JSONObject json = new JSONObject();
		JSONArray constraints = new JSONArray();
		json.accumulate("constraints", constraints);
		attributes.put("json", json);
		
		
		for ( ConstraintDescriptor<?> constraintDescriptor : list ) {
			JSONObject constraint = new JSONObject();
			constraints.put(constraint);
			for ( String key : clientConstraintDescriptor.getAttributes()) {
				if ( key.equals("message")) {
					constraint.accumulate(key, interpolateMessage(constraintDescriptor));
				} else {
					constraint.accumulate(key, constraintDescriptor.getAttributes().get(key));	
				}
			}
		}
		
		return attributes;
	}
	
	
    private String interpolateMessage(final ConstraintDescriptor<?> descriptor)
    {
        String messageTemplate = (String) descriptor.getAttributes().get("message");

        MessageInterpolator messageInterpolator = beanValidatorSource.getValidatorFactory().getMessageInterpolator();

        return messageInterpolator.interpolate(messageTemplate, new Context()
        {

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor()
            {
                return descriptor;
            }

            @Override
            public Object getValidatedValue()
            {
                return object;
            }
        });
    }

}

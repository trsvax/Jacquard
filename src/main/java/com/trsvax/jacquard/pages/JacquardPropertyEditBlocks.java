package com.trsvax.jacquard.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.FieldTranslator;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Mixins;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PropertyEditContext;
import org.slf4j.Logger;

import com.trsvax.jacquard.components.DateTime;

public class JacquardPropertyEditBlocks {
	
	@Property
    @Environmental
    private PropertyEditContext context;
	
    @Inject
    private ComponentResources resources;
    
    @Inject
    private Logger logger;
	
	@Component(parameters =
	    { "value=context.propertyValue", "label=prop:context.label", "type=literal:datetimepicker",
	            "translate=prop:dateTimeTranslator", "validate=prop:dateTimeValidator",
	            "clientId=prop:context.propertyId", "annotationProvider=context" })
    private DateTime datetime;


    public FieldValidator getDateTimeValidator()
    {
      return context.getValidator(datetime);
    }
    
    public FieldTranslator getDateTimeTranslator()
    {
    	
    	return context.getTranslator(datetime);
    }
    
	@Component(parameters =
	    { "value=context.propertyValue", "label=prop:context.label", 
	            "translate=prop:HTMLTranslator", "validate=prop:HTMLValidator",
	            "clientId=prop:context.propertyId", "annotationProvider=context" })
	@Mixins({"tj/MarkItUp"})
    private TextArea html;


    public FieldValidator getHTMLValidator()
    {
      return context.getValidator(datetime);
    }
    
    public FieldTranslator getHTMLTranslator()
    {
    	
    	return context.getTranslator(datetime);
    }

}

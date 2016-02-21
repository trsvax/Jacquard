package com.trsvax.jacquard.components;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(stack = "core-datefield", library={"DateTime.js"})
//@ImportJQueryUI(value = {"jquery.ui.widget", "jquery.ui.mouse", "jquery.ui.slider"})
public class DateTime extends AbstractField {
	@Parameter
	private Long value;
	
	@Parameter(defaultPrefix="literal")
	private JSONObject args;
	
	@Parameter(defaultPrefix="literal")
	private DateFormat format;
	
	@Parameter(defaultPrefix="literal")
	private String type;
		
	@Inject
	private Request request;
	
	@Inject
	private ComponentResources resources;
	
	@Environmental
	private ValidationTracker tracker;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;


	@BeginRender
	void beginRender(MarkupWriter writer) {
		String value = this.value == null ? "" : formatter().format(new Date(this.value));
		writer.element("input", "type","text","name",getControlName(),"id",getClientId(),"value",value);
		
	}
	
	void afterRender(MarkupWriter writer) {
		if ( type == null ) {
			type = "datepicker";
		}
		writer.end();
		javaScriptSupport.addScript("$('#%s').%s(%s);", getClientId(),type,args == null ? "" : args.toString());
	}

	@Override
	protected void processSubmission(String controlName) {
		DateFormat format = formatter();
		String value = request.getParameter(controlName);
		tracker.recordInput(this, value);
				
		if ( value == null || value.length() == 0) {
			this.value = null;
		} else {
				ParsePosition pos = new ParsePosition(0);
				Date date = format.parse(value,pos);
				if ( !format.isLenient() && pos.getErrorIndex() > 0 ) {
					tracker.recordError(this, "extra stuff");
				} else {
					this.value = date.getTime();
				}
		}
		
	}
	
	DateFormat formatter() {
		DateFormat format = this.format;
		if ( format == null ) {
			format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			format.setLenient(false);
		} else {
			if ( ! DateFormat.class.isAssignableFrom(resources.getBoundType("format")) ) {
				format.setLenient(false);
			} 
		}
		return format;
	}

}

package com.trsvax.jacquard.app.pages.date;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.Environment;

import com.trsvax.jacquard.environment.DateTranslatorEnv;
import com.trsvax.jacquard.environment.DateTranslatorImpl;

public class DateIndex {
	
	@Property
	@ActivationRequestParameter
	Date date;
	
	@Property
	@ActivationRequestParameter
	private Calendar calendar;
	
	@Inject
	Environment environment;
	
	void setupRender() {
		DateTranslatorEnv dateTranslatorEnv = new DateTranslatorImpl();
		Map<String,String> attributes = new HashMap<String,String>();
		attributes.put("data-provide", "datepicker");
		attributes.put("data-date-format","yyyy-mm-dd");
		attributes.put("data-date-autoclose", "true");
		dateTranslatorEnv.setTypeOverride("date");
		dateTranslatorEnv.setFormatString("yyyy-MM-dd");
		dateTranslatorEnv.setStackName("webjars/datepicker");
		dateTranslatorEnv.setAttributes(attributes);
		environment.push(DateTranslatorEnv.class, dateTranslatorEnv);
		
	}
	
	void cleanupRender() {
		environment.pop(DateTranslatorEnv.class);
	}

}

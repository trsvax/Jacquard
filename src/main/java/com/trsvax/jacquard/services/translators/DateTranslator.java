package com.trsvax.jacquard.services.translators;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.Translator;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.Html5Support;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.trsvax.jacquard.environment.DateTranslatorEnv;

public class DateTranslator implements Translator<Object> {
	private final Environment environment;
	private final Html5Support html5Support;	
	@NotNull
	private final String name;	
	@NotNull
	private final Class<? extends Object> type;;
	
	private String formatString = "MM/dd/yyyy";
	private String moduleName;
	private String typeOverride;
	private Map<String,String> attributes = new HashMap<String,String>();
	
	/**
	 * 
	 * @param environment
	 * @param html5Support
	 */
	private DateTranslator(Builder builder) {
		this.environment = builder.environment;
		this.html5Support = builder.htmlSupport;
		this.name = builder.name;
		this.type = builder.type;
	}
	
	public DateTranslator setFormatString(String formatString) {
		this.formatString = formatString;
		return this;
	}
	
	public DateTranslator setModuleName(String moduleName) {
		this.moduleName = moduleName;
		return this;
	}
	
	public DateTranslator setTypeOverride(String typeOverride) {
		this.typeOverride = typeOverride;
		return this;
	}
	
	public DateTranslator addAttribute(String name, String value) {
		attributes.put(name, value);
		return this;
	}
	

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toClient(Object value) {
		return new SimpleDateFormat(getFormatString()).format(value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getType() {
		return  type;
	}

	@Override
	public String getMessageKey() {
		return getName() + "-translator-" + getFormatString();
	}

	@Override
	public Date parseClient(Field field, String clientValue, String message) throws ValidationException {
		ParsePosition parsePosition = new ParsePosition(0);
		DateFormat format = new SimpleDateFormat(getFormatString());
		format.setLenient(false);
		
		Date date = format.parse(clientValue,parsePosition);
		if ( parsePosition.getIndex() == clientValue.length() ) {
			return date;
		} 
		
		// If the browser supports type="date" might return this format
		parsePosition = new ParsePosition(0);
		format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		
		date = format.parse(clientValue,parsePosition);
		if ( parsePosition.getIndex() == clientValue.length() ) {
			return date;
		}
		
		throw new ValidationException(String.format(message,clientValue));	
	}

	@Override
	public void render(Field field, String message, MarkupWriter writer, FormSupport formSupport) {
		Element element = writer.getElement();

		if (formSupport.isClientValidationEnabled())
        {
			element.attributes("data-validation", "true",
	                "data-translation", "date",
	                "data-translation-message", message);
        }		

		if ( getTypeOverride() != null && html5Support.isHtml5SupportEnabled() ) {
			element.forceAttributes("type",getTypeOverride());
		}
				
		if ( ! getAttributes().isEmpty()) {
			for ( Entry<String,String> a : getAttributes().entrySet() ) {
				if ( element.getAttribute( a.getKey()) == null) {
					element.attribute(a.getKey(), a.getValue());
				}
			}
		}
		
		JavaScriptSupport javaScriptSupport = environment.peek(JavaScriptSupport.class);
		if ( javaScriptSupport != null && getModuleName() != null ) {
			javaScriptSupport.require(getModuleName());
		}
		
	}
	
	private String getModuleName() {
		DateTranslatorEnv dateTranslator = getEnvironment();
		if ( dateTranslator != null && dateTranslator.getModuleName() != null ) {
			return dateTranslator.getModuleName();
		}
		return moduleName;
	}
	
	private String getFormatString() {
		DateTranslatorEnv dateTranslator = getEnvironment();
		if ( dateTranslator != null && dateTranslator.getFormatString() != null ) {
			return dateTranslator.getFormatString();
		}
		return formatString;
	}
	
	private String getTypeOverride() {
		DateTranslatorEnv dateTranslator = getEnvironment();
		if ( dateTranslator != null && dateTranslator.getTypeOverride() != null ) {
			return dateTranslator.getTypeOverride();
		}
		return typeOverride;
	}
	
	private Map<String,String> getAttributes() {
		DateTranslatorEnv dateTranslator = getEnvironment();
		if ( dateTranslator != null && dateTranslator.getAttributes() != null ) {
			return dateTranslator.getAttributes();
		}
		return attributes;
	}
	
	private DateTranslatorEnv getEnvironment() {
		return  environment.peek(DateTranslatorEnv.class);		
	}
	
	public static class Builder {
		private final ValidatorFactory factory;
		private final Environment environment;
		private final Html5Support htmlSupport;
		
		private String name;
		private Class<? extends Object> type;
		
		public Builder(Environment environment, Html5Support html5Support,ValidatorFactory validatorFactory) {
			this.environment = environment;
			this.htmlSupport = html5Support;
			this.factory = validatorFactory;
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setType(Class<? extends Object> type) {
			this.type = type;
			return this;
		}
		
		public DateTranslator build() {
			DateTranslator dateTranslator = new DateTranslator(this);
			Set<ConstraintViolation<DateTranslator>> constraintViolations = factory.getValidator().validate(dateTranslator);
			if ( ! constraintViolations.isEmpty() ) {
				throw new ConstraintViolationException( new HashSet<ConstraintViolation<?>>(constraintViolations));
			}
			return dateTranslator;
		}
	}

}
